/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Queues
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.datafixers.util.Either
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ByteMap
 *  it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2LongMap
 *  it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableBoolean
 *  org.apache.commons.lang3.mutable.MutableObject
 *  org.slf4j.Logger
 */
package net.minecraft.server.level;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkTaskPriorityQueue;
import net.minecraft.server.level.ChunkTaskPriorityQueueSorter;
import net.minecraft.server.level.PlayerMap;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.TickingTracker;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.util.CsvOutput;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import net.minecraft.world.level.chunk.storage.ChunkStorage;
import net.minecraft.world.level.entity.ChunkStatusUpdateListener;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;

public class ChunkMap
extends ChunkStorage
implements ChunkHolder.PlayerProvider {
    private static final byte f_143034_ = -1;
    private static final byte f_143035_ = 0;
    private static final byte f_143036_ = 1;
    private static final Logger f_140128_ = LogUtils.getLogger();
    private static final int f_143037_ = 200;
    private static final int f_198789_ = 20;
    private static final int f_202982_ = 10000;
    private static final int f_143038_ = 3;
    public static final int f_143032_ = 33;
    public static final int f_140127_ = 33 + ChunkStatus.m_62421_();
    public static final int f_143033_ = 31;
    private final Long2ObjectLinkedOpenHashMap<ChunkHolder> f_140129_ = new Long2ObjectLinkedOpenHashMap();
    private volatile Long2ObjectLinkedOpenHashMap<ChunkHolder> f_140130_ = this.f_140129_.clone();
    private final Long2ObjectLinkedOpenHashMap<ChunkHolder> f_140131_ = new Long2ObjectLinkedOpenHashMap();
    private final LongSet f_140132_ = new LongOpenHashSet();
    final ServerLevel f_140133_;
    private final ThreadedLevelLightEngine f_140134_;
    private final BlockableEventLoop<Runnable> f_140135_;
    private ChunkGenerator f_140136_;
    private RandomState f_214834_;
    private final Supplier<DimensionDataStorage> f_140137_;
    private final PoiManager f_140138_;
    final LongSet f_140139_ = new LongOpenHashSet();
    private boolean f_140140_;
    private final ChunkTaskPriorityQueueSorter f_140141_;
    private final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> f_140142_;
    private final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> f_140143_;
    private final ChunkProgressListener f_140144_;
    private final ChunkStatusUpdateListener f_143031_;
    private final DistanceManager f_140145_;
    private final AtomicInteger f_140146_ = new AtomicInteger();
    private final StructureTemplateManager f_214833_;
    private final String f_182284_;
    private final PlayerMap f_140149_ = new PlayerMap();
    private final Int2ObjectMap<TrackedEntity> f_140150_ = new Int2ObjectOpenHashMap();
    private final Long2ByteMap f_140151_ = new Long2ByteOpenHashMap();
    private final Long2LongMap f_202981_ = new Long2LongOpenHashMap();
    private final Queue<Runnable> f_140125_ = Queues.newConcurrentLinkedQueue();
    int f_140126_;

    public ChunkMap(ServerLevel p_214836_, LevelStorageSource.LevelStorageAccess p_214837_, DataFixer p_214838_, StructureTemplateManager p_214839_, Executor p_214840_, BlockableEventLoop<Runnable> p_214841_, LightChunkGetter p_214842_, ChunkGenerator p_214843_, ChunkProgressListener p_214844_, ChunkStatusUpdateListener p_214845_, Supplier<DimensionDataStorage> p_214846_, int p_214847_, boolean p_214848_) {
        super(p_214837_.m_197394_(p_214836_.m_46472_()).resolve("region"), p_214838_, p_214848_);
        this.f_214833_ = p_214839_;
        Path $$13 = p_214837_.m_197394_(p_214836_.m_46472_());
        this.f_182284_ = $$13.getFileName().toString();
        this.f_140133_ = p_214836_;
        this.f_140136_ = p_214843_;
        if (p_214843_ instanceof NoiseBasedChunkGenerator) {
            NoiseBasedChunkGenerator $$14 = (NoiseBasedChunkGenerator)p_214843_;
            this.f_214834_ = RandomState.m_224570_($$14.m_224341_().m_203334_(), p_214836_.m_5962_().m_175515_(Registry.f_194568_), p_214836_.m_7328_());
        } else {
            this.f_214834_ = RandomState.m_224570_(NoiseGeneratorSettings.m_238396_(), p_214836_.m_5962_().m_175515_(Registry.f_194568_), p_214836_.m_7328_());
        }
        this.f_140135_ = p_214841_;
        ProcessorMailbox<Runnable> $$15 = ProcessorMailbox.m_18751_(p_214840_, "worldgen");
        ProcessorHandle<Runnable> $$16 = ProcessorHandle.m_18714_("main", p_214841_::m_6937_);
        this.f_140144_ = p_214844_;
        this.f_143031_ = p_214845_;
        ProcessorMailbox<Runnable> $$17 = ProcessorMailbox.m_18751_(p_214840_, "light");
        this.f_140141_ = new ChunkTaskPriorityQueueSorter((List<ProcessorHandle<?>>)ImmutableList.of($$15, $$16, $$17), p_214840_, Integer.MAX_VALUE);
        this.f_140142_ = this.f_140141_.m_140604_($$15, false);
        this.f_140143_ = this.f_140141_.m_140604_($$16, false);
        this.f_140134_ = new ThreadedLevelLightEngine(p_214842_, this, this.f_140133_.m_6042_().f_223549_(), $$17, this.f_140141_.m_140604_($$17, false));
        this.f_140145_ = new DistanceManager(p_214840_, p_214841_);
        this.f_140137_ = p_214846_;
        this.f_140138_ = new PoiManager($$13.resolve("poi"), p_214838_, p_214848_, p_214836_.m_5962_(), p_214836_);
        this.m_140167_(p_214847_);
    }

    protected ChunkGenerator m_183719_() {
        return this.f_140136_;
    }

    protected RandomState m_214914_() {
        return this.f_214834_;
    }

    public void m_183825_() {
        DataResult $$0 = ChunkGenerator.f_62136_.encodeStart((DynamicOps)JsonOps.INSTANCE, (Object)this.f_140136_);
        DataResult $$1 = $$0.flatMap(p_183804_ -> ChunkGenerator.f_62136_.parse((DynamicOps)JsonOps.INSTANCE, p_183804_));
        $$1.result().ifPresent(p_183808_ -> {
            this.f_140136_ = p_183808_;
        });
    }

    private static double m_140226_(ChunkPos p_140227_, Entity p_140228_) {
        double $$2 = SectionPos.m_175554_(p_140227_.f_45578_, 8);
        double $$3 = SectionPos.m_175554_(p_140227_.f_45579_, 8);
        double $$4 = $$2 - p_140228_.m_20185_();
        double $$5 = $$3 - p_140228_.m_20189_();
        return $$4 * $$4 + $$5 * $$5;
    }

    public static boolean m_200878_(int p_200879_, int p_200880_, int p_200881_, int p_200882_, int p_200883_) {
        int $$10;
        int $$11;
        int $$5 = Math.max(0, Math.abs(p_200879_ - p_200881_) - 1);
        int $$6 = Math.max(0, Math.abs(p_200880_ - p_200882_) - 1);
        long $$7 = Math.max(0, Math.max($$5, $$6) - 1);
        long $$8 = Math.min($$5, $$6);
        long $$9 = $$8 * $$8 + $$7 * $$7;
        return $$9 <= (long)($$11 = ($$10 = p_200883_ - 1) * $$10);
    }

    private static boolean m_183828_(int p_183829_, int p_183830_, int p_183831_, int p_183832_, int p_183833_) {
        if (!ChunkMap.m_200878_(p_183829_, p_183830_, p_183831_, p_183832_, p_183833_)) {
            return false;
        }
        if (!ChunkMap.m_200878_(p_183829_ + 1, p_183830_, p_183831_, p_183832_, p_183833_)) {
            return true;
        }
        if (!ChunkMap.m_200878_(p_183829_, p_183830_ + 1, p_183831_, p_183832_, p_183833_)) {
            return true;
        }
        if (!ChunkMap.m_200878_(p_183829_ - 1, p_183830_, p_183831_, p_183832_, p_183833_)) {
            return true;
        }
        return !ChunkMap.m_200878_(p_183829_, p_183830_ - 1, p_183831_, p_183832_, p_183833_);
    }

    protected ThreadedLevelLightEngine m_140166_() {
        return this.f_140134_;
    }

    @Nullable
    protected ChunkHolder m_140174_(long p_140175_) {
        return (ChunkHolder)this.f_140129_.get(p_140175_);
    }

    @Nullable
    protected ChunkHolder m_140327_(long p_140328_) {
        return (ChunkHolder)this.f_140130_.get(p_140328_);
    }

    protected IntSupplier m_140371_(long p_140372_) {
        return () -> {
            ChunkHolder $$1 = this.m_140327_(p_140372_);
            if ($$1 == null) {
                return ChunkTaskPriorityQueue.f_140508_ - 1;
            }
            return Math.min($$1.m_140094_(), ChunkTaskPriorityQueue.f_140508_ - 1);
        };
    }

    public String m_140204_(ChunkPos p_140205_) {
        ChunkHolder $$1 = this.m_140327_(p_140205_.m_45588_());
        if ($$1 == null) {
            return "null";
        }
        String $$2 = $$1.m_140093_() + "\n";
        ChunkStatus $$3 = $$1.m_140088_();
        ChunkAccess $$4 = $$1.m_140089_();
        if ($$3 != null) {
            $$2 = $$2 + "St: \u00a7" + $$3.m_62445_() + $$3 + "\u00a7r\n";
        }
        if ($$4 != null) {
            $$2 = $$2 + "Ch: \u00a7" + $$4.m_6415_().m_62445_() + $$4.m_6415_() + "\u00a7r\n";
        }
        ChunkHolder.FullChunkStatus $$5 = $$1.m_140091_();
        $$2 = $$2 + "\u00a7" + $$5.ordinal() + $$5;
        return $$2 + "\u00a7r";
    }

    private CompletableFuture<Either<List<ChunkAccess>, ChunkHolder.ChunkLoadingFailure>> m_140210_(ChunkPos p_140211_, final int p_140212_, IntFunction<ChunkStatus> p_140213_) {
        ArrayList<CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> $$3 = new ArrayList<CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>>();
        ArrayList<ChunkHolder> $$4 = new ArrayList<ChunkHolder>();
        final int $$5 = p_140211_.f_45578_;
        final int $$6 = p_140211_.f_45579_;
        for (int $$7 = -p_140212_; $$7 <= p_140212_; ++$$7) {
            for (int $$8 = -p_140212_; $$8 <= p_140212_; ++$$8) {
                int $$9 = Math.max(Math.abs($$8), Math.abs($$7));
                final ChunkPos $$10 = new ChunkPos($$5 + $$8, $$6 + $$7);
                long $$11 = $$10.m_45588_();
                ChunkHolder $$12 = this.m_140174_($$11);
                if ($$12 == null) {
                    return CompletableFuture.completedFuture(Either.right((Object)new ChunkHolder.ChunkLoadingFailure(){

                        public String toString() {
                            return "Unloaded " + $$10;
                        }
                    }));
                }
                ChunkStatus $$13 = p_140213_.apply($$9);
                CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> $$14 = $$12.m_140049_($$13, this);
                $$4.add($$12);
                $$3.add($$14);
            }
        }
        CompletableFuture $$15 = Util.m_137567_($$3);
        CompletionStage $$16 = $$15.thenApply(p_183730_ -> {
            ArrayList $$4 = Lists.newArrayList();
            int $$5 = 0;
            for (final Either $$6 : p_183730_) {
                if ($$6 == null) {
                    throw this.m_203751_(new IllegalStateException("At least one of the chunk futures were null"), "n/a");
                }
                Optional $$7 = $$6.left();
                if (!$$7.isPresent()) {
                    final int $$8 = $$5;
                    return Either.right((Object)new ChunkHolder.ChunkLoadingFailure(){

                        public String toString() {
                            return "Unloaded " + new ChunkPos($$5 + $$8 % (p_140212_ * 2 + 1), $$6 + $$8 / (p_140212_ * 2 + 1)) + " " + $$6.right().get();
                        }
                    });
                }
                $$4.add((ChunkAccess)$$7.get());
                ++$$5;
            }
            return Either.left((Object)$$4);
        });
        for (ChunkHolder $$17 : $$4) {
            $$17.m_200416_("getChunkRangeFuture " + p_140211_ + " " + p_140212_, (CompletableFuture<?>)$$16);
        }
        return $$16;
    }

    public ReportedException m_203751_(IllegalStateException p_203752_, String p_203753_) {
        StringBuilder $$2 = new StringBuilder();
        Consumer<ChunkHolder> $$3 = p_203756_ -> p_203756_.m_202980_().forEach(p_203760_ -> {
            ChunkStatus $$3 = (ChunkStatus)p_203760_.getFirst();
            CompletableFuture $$4 = (CompletableFuture)p_203760_.getSecond();
            if ($$4 != null && $$4.isDone() && $$4.join() == null) {
                $$2.append(p_203756_.m_140092_()).append(" - status: ").append($$3).append(" future: ").append($$4).append(System.lineSeparator());
            }
        });
        $$2.append("Updating:").append(System.lineSeparator());
        this.f_140129_.values().forEach($$3);
        $$2.append("Visible:").append(System.lineSeparator());
        this.f_140130_.values().forEach($$3);
        CrashReport $$4 = CrashReport.m_127521_(p_203752_, "Chunk loading");
        CrashReportCategory $$5 = $$4.m_127514_("Chunk loading");
        $$5.m_128159_("Details", p_203753_);
        $$5.m_128159_("Futures", $$2);
        return new ReportedException($$4);
    }

    public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> m_143117_(ChunkPos p_143118_) {
        return this.m_140210_(p_143118_, 2, p_203078_ -> ChunkStatus.f_62326_).thenApplyAsync(p_203086_ -> p_203086_.mapLeft(p_203092_ -> (LevelChunk)p_203092_.get(p_203092_.size() / 2)), (Executor)this.f_140135_);
    }

    @Nullable
    ChunkHolder m_140176_(long p_140177_, int p_140178_, @Nullable ChunkHolder p_140179_, int p_140180_) {
        if (p_140180_ > f_140127_ && p_140178_ > f_140127_) {
            return p_140179_;
        }
        if (p_140179_ != null) {
            p_140179_.m_140027_(p_140178_);
        }
        if (p_140179_ != null) {
            if (p_140178_ > f_140127_) {
                this.f_140139_.add(p_140177_);
            } else {
                this.f_140139_.remove(p_140177_);
            }
        }
        if (p_140178_ <= f_140127_ && p_140179_ == null) {
            p_140179_ = (ChunkHolder)this.f_140131_.remove(p_140177_);
            if (p_140179_ != null) {
                p_140179_.m_140027_(p_140178_);
            } else {
                p_140179_ = new ChunkHolder(new ChunkPos(p_140177_), p_140178_, this.f_140133_, this.f_140134_, this.f_140141_, this);
            }
            this.f_140129_.put(p_140177_, (Object)p_140179_);
            this.f_140140_ = true;
        }
        return p_140179_;
    }

    @Override
    public void close() throws IOException {
        try {
            this.f_140141_.close();
            this.f_140138_.close();
        }
        finally {
            super.close();
        }
    }

    protected void m_140318_(boolean p_140319_) {
        if (p_140319_) {
            List $$1 = this.f_140130_.values().stream().filter(ChunkHolder::m_140095_).peek(ChunkHolder::m_140096_).collect(Collectors.toList());
            MutableBoolean $$2 = new MutableBoolean();
            do {
                $$2.setFalse();
                $$1.stream().map(p_203102_ -> {
                    CompletableFuture<ChunkAccess> $$1;
                    do {
                        $$1 = p_203102_.m_140090_();
                        this.f_140135_.m_18701_($$1::isDone);
                    } while ($$1 != p_203102_.m_140090_());
                    return $$1.join();
                }).filter(p_203088_ -> p_203088_ instanceof ImposterProtoChunk || p_203088_ instanceof LevelChunk).filter(this::m_140258_).forEach(p_203051_ -> $$2.setTrue());
            } while ($$2.isTrue());
            this.m_140353_(() -> true);
            this.m_63514_();
        } else {
            this.f_140130_.values().forEach(this::m_198874_);
        }
    }

    protected void m_140280_(BooleanSupplier p_140281_) {
        ProfilerFiller $$1 = this.f_140133_.m_46473_();
        $$1.m_6180_("poi");
        this.f_140138_.m_6202_(p_140281_);
        $$1.m_6182_("chunk_unload");
        if (!this.f_140133_.m_7441_()) {
            this.m_140353_(p_140281_);
        }
        $$1.m_7238_();
    }

    public boolean m_201907_() {
        return this.f_140134_.m_75643_() || !this.f_140131_.isEmpty() || !this.f_140129_.isEmpty() || this.f_140138_.m_202164_() || !this.f_140139_.isEmpty() || !this.f_140125_.isEmpty() || this.f_140141_.m_201909_() || this.f_140145_.m_201911_();
    }

    private void m_140353_(BooleanSupplier p_140354_) {
        Runnable $$6;
        LongIterator $$1 = this.f_140139_.iterator();
        int $$2 = 0;
        while ($$1.hasNext() && (p_140354_.getAsBoolean() || $$2 < 200 || this.f_140139_.size() > 2000)) {
            long $$3 = $$1.nextLong();
            ChunkHolder $$4 = (ChunkHolder)this.f_140129_.remove($$3);
            if ($$4 != null) {
                this.f_140131_.put($$3, (Object)$$4);
                this.f_140140_ = true;
                ++$$2;
                this.m_140181_($$3, $$4);
            }
            $$1.remove();
        }
        for (int $$5 = Math.max(0, this.f_140125_.size() - 2000); (p_140354_.getAsBoolean() || $$5 > 0) && ($$6 = this.f_140125_.poll()) != null; --$$5) {
            $$6.run();
        }
        int $$7 = 0;
        ObjectIterator $$8 = this.f_140130_.values().iterator();
        while ($$7 < 20 && p_140354_.getAsBoolean() && $$8.hasNext()) {
            if (!this.m_198874_((ChunkHolder)$$8.next())) continue;
            ++$$7;
        }
    }

    private void m_140181_(long p_140182_, ChunkHolder p_140183_) {
        CompletableFuture<ChunkAccess> $$2 = p_140183_.m_140090_();
        ((CompletableFuture)$$2.thenAcceptAsync(p_203002_ -> {
            CompletableFuture<ChunkAccess> $$4 = p_140183_.m_140090_();
            if ($$4 != $$2) {
                this.m_140181_(p_140182_, p_140183_);
                return;
            }
            if (this.f_140131_.remove(p_140182_, (Object)p_140183_) && p_203002_ != null) {
                if (p_203002_ instanceof LevelChunk) {
                    ((LevelChunk)p_203002_).m_62913_(false);
                }
                this.m_140258_((ChunkAccess)p_203002_);
                if (this.f_140132_.remove(p_140182_) && p_203002_ instanceof LevelChunk) {
                    LevelChunk $$5 = (LevelChunk)p_203002_;
                    this.f_140133_.m_8712_($$5);
                }
                this.f_140134_.m_9330_(p_203002_.m_7697_());
                this.f_140134_.m_9409_();
                this.f_140144_.m_5511_(p_203002_.m_7697_(), null);
                this.f_202981_.remove(p_203002_.m_7697_().m_45588_());
            }
        }, this.f_140125_::add)).whenComplete((p_202996_, p_202997_) -> {
            if (p_202997_ != null) {
                f_140128_.error("Failed to save chunk {}", (Object)p_140183_.m_140092_(), p_202997_);
            }
        });
    }

    protected boolean m_140324_() {
        if (!this.f_140140_) {
            return false;
        }
        this.f_140130_ = this.f_140129_.clone();
        this.f_140140_ = false;
        return true;
    }

    public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_140292_(ChunkHolder p_140293_, ChunkStatus p_140294_) {
        Optional $$3;
        ChunkPos $$2 = p_140293_.m_140092_();
        if (p_140294_ == ChunkStatus.f_62314_) {
            return this.m_140417_($$2);
        }
        if (p_140294_ == ChunkStatus.f_62323_) {
            this.f_140145_.m_140792_(TicketType.f_9446_, $$2, 33 + ChunkStatus.m_62370_(ChunkStatus.f_62323_), $$2);
        }
        if (($$3 = p_140293_.m_140049_(p_140294_.m_62482_(), this).getNow(ChunkHolder.f_139995_).left()).isPresent() && ((ChunkAccess)$$3.get()).m_6415_().m_62427_(p_140294_)) {
            CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> $$4 = p_140294_.m_223244_(this.f_140133_, this.f_214833_, this.f_140134_, p_203081_ -> this.m_140383_(p_140293_), (ChunkAccess)$$3.get());
            this.f_140144_.m_5511_($$2, p_140294_);
            return $$4;
        }
        return this.m_140360_(p_140293_, p_140294_);
    }

    private CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_140417_(ChunkPos p_140418_) {
        return ((CompletableFuture)((CompletableFuture)this.m_214963_(p_140418_).thenApply(p_214925_ -> p_214925_.filter(p_214928_ -> {
            boolean $$2 = ChunkMap.m_214940_(p_214928_);
            if (!$$2) {
                f_140128_.error("Chunk file at {} is missing level data, skipping", (Object)p_140418_);
            }
            return $$2;
        }))).thenApplyAsync(p_214891_ -> {
            this.f_140133_.m_46473_().m_6174_("chunkLoad");
            if (p_214891_.isPresent()) {
                ProtoChunk $$2 = ChunkSerializer.m_188230_(this.f_140133_, this.f_140138_, p_140418_, (CompoundTag)p_214891_.get());
                this.m_140229_(p_140418_, ((ChunkAccess)$$2).m_6415_().m_62494_());
                return Either.left((Object)$$2);
            }
            return Either.left((Object)this.m_214961_(p_140418_));
        }, (Executor)this.f_140135_)).exceptionallyAsync(p_214888_ -> this.m_214901_((Throwable)p_214888_, p_140418_), (Executor)this.f_140135_);
    }

    private static boolean m_214940_(CompoundTag p_214941_) {
        return p_214941_.m_128425_("Status", 8);
    }

    /*
     * Enabled aggressive block sorting
     */
    private Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure> m_214901_(Throwable p_214902_, ChunkPos p_214903_) {
        if (!(p_214902_ instanceof ReportedException)) {
            if (!(p_214902_ instanceof IOException)) return Either.left((Object)this.m_214961_(p_214903_));
            f_140128_.error("Couldn't load chunk {}", (Object)p_214903_, (Object)p_214902_);
            return Either.left((Object)this.m_214961_(p_214903_));
        }
        ReportedException $$2 = (ReportedException)p_214902_;
        Throwable $$3 = $$2.getCause();
        if ($$3 instanceof IOException) {
            f_140128_.error("Couldn't load chunk {}", (Object)p_214903_, (Object)$$3);
            return Either.left((Object)this.m_214961_(p_214903_));
        }
        this.m_140422_(p_214903_);
        throw $$2;
    }

    private ChunkAccess m_214961_(ChunkPos p_214962_) {
        this.m_140422_(p_214962_);
        return new ProtoChunk(p_214962_, UpgradeData.f_63320_, this.f_140133_, this.f_140133_.m_5962_().m_175515_(Registry.f_122885_), null);
    }

    private void m_140422_(ChunkPos p_140423_) {
        this.f_140151_.put(p_140423_.m_45588_(), (byte)-1);
    }

    private byte m_140229_(ChunkPos p_140230_, ChunkStatus.ChunkType p_140231_) {
        return this.f_140151_.put(p_140230_.m_45588_(), p_140231_ == ChunkStatus.ChunkType.PROTOCHUNK ? (byte)-1 : 1);
    }

    private CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_140360_(ChunkHolder p_140361_, ChunkStatus p_140362_) {
        ChunkPos $$2 = p_140361_.m_140092_();
        CompletableFuture<Either<List<ChunkAccess>, ChunkHolder.ChunkLoadingFailure>> $$3 = this.m_140210_($$2, p_140362_.m_62488_(), p_214935_ -> this.m_140262_(p_140362_, p_214935_));
        this.f_140133_.m_46473_().m_6525_(() -> "chunkGenerate " + p_140362_.m_62467_());
        Executor $$4 = p_214958_ -> this.f_140142_.m_6937_(ChunkTaskPriorityQueueSorter.m_140642_(p_140361_, p_214958_));
        return $$3.thenComposeAsync(p_214873_ -> (CompletionStage)p_214873_.map(p_214879_ -> {
            try {
                CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> $$5 = p_140362_.m_223279_($$4, this.f_140133_, this.f_140136_, this.f_214833_, this.f_140134_, p_214919_ -> this.m_140383_(p_140361_), (List<ChunkAccess>)p_214879_, false);
                this.f_140144_.m_5511_($$2, p_140362_);
                return $$5;
            }
            catch (Exception $$6) {
                $$6.getStackTrace();
                CrashReport $$7 = CrashReport.m_127521_($$6, "Exception generating new chunk");
                CrashReportCategory $$8 = $$7.m_127514_("Chunk to be generated");
                $$8.m_128159_("Location", String.format(Locale.ROOT, "%d,%d", p_242116_.f_45578_, p_242116_.f_45579_));
                $$8.m_128159_("Position hash", ChunkPos.m_45589_(p_242116_.f_45578_, p_242116_.f_45579_));
                $$8.m_128159_("Generator", this.f_140136_);
                this.f_140135_.execute(() -> {
                    throw new ReportedException($$7);
                });
                throw new ReportedException($$7);
            }
        }, p_214867_ -> {
            this.m_140375_($$2);
            return CompletableFuture.completedFuture(Either.right((Object)p_214867_));
        }), $$4);
    }

    protected void m_140375_(ChunkPos p_140376_) {
        this.f_140135_.m_6937_(Util.m_137474_(() -> this.f_140145_.m_140823_(TicketType.f_9446_, p_140376_, 33 + ChunkStatus.m_62370_(ChunkStatus.f_62323_), p_140376_), () -> "release light ticket " + p_140376_));
    }

    private ChunkStatus m_140262_(ChunkStatus p_140263_, int p_140264_) {
        ChunkStatus $$3;
        if (p_140264_ == 0) {
            ChunkStatus $$2 = p_140263_.m_62482_();
        } else {
            $$3 = ChunkStatus.m_156185_(ChunkStatus.m_62370_(p_140263_) + p_140264_);
        }
        return $$3;
    }

    private static void m_143064_(ServerLevel p_143065_, List<CompoundTag> p_143066_) {
        if (!p_143066_.isEmpty()) {
            p_143065_.m_143327_(EntityType.m_147045_(p_143066_, p_143065_));
        }
    }

    private CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_140383_(ChunkHolder p_140384_) {
        CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> $$1 = p_140384_.m_140047_(ChunkStatus.f_62326_.m_62482_());
        return $$1.thenApplyAsync(p_214853_ -> {
            ChunkStatus $$2 = ChunkHolder.m_140074_(p_140384_.m_140093_());
            if (!$$2.m_62427_(ChunkStatus.f_62326_)) {
                return ChunkHolder.f_139995_;
            }
            return p_214853_.mapLeft(p_214856_ -> {
                LevelChunk $$5;
                ChunkPos $$2 = p_140384_.m_140092_();
                ProtoChunk $$3 = (ProtoChunk)p_214856_;
                if ($$3 instanceof ImposterProtoChunk) {
                    LevelChunk $$4 = ((ImposterProtoChunk)$$3).m_62768_();
                } else {
                    $$5 = new LevelChunk(this.f_140133_, $$3, p_214900_ -> ChunkMap.m_143064_(this.f_140133_, $$3.m_63293_()));
                    p_140384_.m_140052_(new ImposterProtoChunk($$5, false));
                }
                $$5.m_62879_(() -> ChunkHolder.m_140083_(p_140384_.m_140093_()));
                $$5.m_62952_();
                if (this.f_140132_.add($$2.m_45588_())) {
                    $$5.m_62913_(true);
                    $$5.m_156369_();
                    $$5.m_187958_(this.f_140133_);
                }
                return $$5;
            });
        }, p_214951_ -> this.f_140143_.m_6937_(ChunkTaskPriorityQueueSorter.m_140624_(p_214951_, p_140384_.m_140092_().m_45588_(), p_140384_::m_140093_)));
    }

    public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> m_143053_(ChunkHolder p_143054_) {
        ChunkPos $$1 = p_143054_.m_140092_();
        CompletableFuture<Either<List<ChunkAccess>, ChunkHolder.ChunkLoadingFailure>> $$2 = this.m_140210_($$1, 1, p_214916_ -> ChunkStatus.f_62326_);
        CompletionStage $$3 = ((CompletableFuture)$$2.thenApplyAsync(p_212878_ -> p_212878_.mapLeft(p_214939_ -> (LevelChunk)p_214939_.get(p_214939_.size() / 2)), p_214944_ -> this.f_140143_.m_6937_(ChunkTaskPriorityQueueSorter.m_140642_(p_143054_, p_214944_)))).thenApplyAsync(p_214930_ -> p_214930_.ifLeft(p_214960_ -> {
            p_214960_.m_62812_();
            this.f_140133_.m_184102_((LevelChunk)p_214960_);
        }), (Executor)this.f_140135_);
        ((CompletableFuture)$$3).thenAcceptAsync(p_214882_ -> p_214882_.ifLeft(p_214885_ -> {
            this.f_140146_.getAndIncrement();
            MutableObject $$2 = new MutableObject();
            this.m_183262_($$1, false).forEach(p_214911_ -> this.m_183760_((ServerPlayer)p_214911_, (MutableObject<ClientboundLevelChunkWithLightPacket>)$$2, (LevelChunk)p_214885_));
        }), p_214922_ -> this.f_140143_.m_6937_(ChunkTaskPriorityQueueSorter.m_140642_(p_143054_, p_214922_)));
        return $$3;
    }

    public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> m_143109_(ChunkHolder p_143110_) {
        return this.m_140210_(p_143110_.m_140092_(), 1, ChunkStatus::m_156185_).thenApplyAsync(p_214893_ -> p_214893_.mapLeft(p_214905_ -> {
            LevelChunk $$1 = (LevelChunk)p_214905_.get(p_214905_.size() / 2);
            return $$1;
        }), p_214859_ -> this.f_140143_.m_6937_(ChunkTaskPriorityQueueSorter.m_140642_(p_143110_, p_214859_)));
    }

    public int m_140368_() {
        return this.f_140146_.get();
    }

    private boolean m_198874_(ChunkHolder p_198875_) {
        if (!p_198875_.m_140095_()) {
            return false;
        }
        ChunkAccess $$1 = p_198875_.m_140090_().getNow(null);
        if ($$1 instanceof ImposterProtoChunk || $$1 instanceof LevelChunk) {
            long $$2 = $$1.m_7697_().m_45588_();
            long $$3 = this.f_202981_.getOrDefault($$2, -1L);
            long $$4 = System.currentTimeMillis();
            if ($$4 < $$3) {
                return false;
            }
            boolean $$5 = this.m_140258_($$1);
            p_198875_.m_140096_();
            if ($$5) {
                this.f_202981_.put($$2, $$4 + 10000L);
            }
            return $$5;
        }
        return false;
    }

    private boolean m_140258_(ChunkAccess p_140259_) {
        this.f_140138_.m_63796_(p_140259_.m_7697_());
        if (!p_140259_.m_6344_()) {
            return false;
        }
        p_140259_.m_8092_(false);
        ChunkPos $$1 = p_140259_.m_7697_();
        try {
            ChunkStatus $$2 = p_140259_.m_6415_();
            if ($$2.m_62494_() != ChunkStatus.ChunkType.LEVELCHUNK) {
                if (this.m_140425_($$1)) {
                    return false;
                }
                if ($$2 == ChunkStatus.f_62314_ && p_140259_.m_6633_().values().stream().noneMatch(StructureStart::m_73603_)) {
                    return false;
                }
            }
            this.f_140133_.m_46473_().m_6174_("chunkSave");
            CompoundTag $$3 = ChunkSerializer.m_63454_(this.f_140133_, p_140259_);
            this.m_63502_($$1, $$3);
            this.m_140229_($$1, $$2.m_62494_());
            return true;
        }
        catch (Exception $$4) {
            f_140128_.error("Failed to save chunk {},{}", new Object[]{$$1.f_45578_, $$1.f_45579_, $$4});
            return false;
        }
    }

    /*
     * WARNING - void declaration
     */
    private boolean m_140425_(ChunkPos p_140426_) {
        void $$4;
        byte $$1 = this.f_140151_.get(p_140426_.m_45588_());
        if ($$1 != 0) {
            return $$1 == 1;
        }
        try {
            CompoundTag $$2 = this.m_214963_(p_140426_).join().orElse(null);
            if ($$2 == null) {
                this.m_140422_(p_140426_);
                return false;
            }
        }
        catch (Exception $$3) {
            f_140128_.error("Failed to read chunk {}", (Object)p_140426_, (Object)$$3);
            this.m_140422_(p_140426_);
            return false;
        }
        ChunkStatus.ChunkType $$5 = ChunkSerializer.m_63485_((CompoundTag)$$4);
        return this.m_140229_(p_140426_, $$5) == 1;
    }

    protected void m_140167_(int p_140168_) {
        int $$1 = Mth.m_14045_(p_140168_ + 1, 3, 33);
        if ($$1 != this.f_140126_) {
            int $$2 = this.f_140126_;
            this.f_140126_ = $$1;
            this.f_140145_.m_140777_(this.f_140126_ + 1);
            for (ChunkHolder $$3 : this.f_140129_.values()) {
                ChunkPos $$4 = $$3.m_140092_();
                MutableObject $$5 = new MutableObject();
                this.m_183262_($$4, false).forEach(p_214864_ -> {
                    SectionPos $$4 = p_214864_.m_8965_();
                    boolean $$5 = ChunkMap.m_200878_(p_214861_.f_45578_, p_214861_.f_45579_, $$4.m_123170_(), $$4.m_123222_(), $$2);
                    boolean $$6 = ChunkMap.m_200878_(p_214861_.f_45578_, p_214861_.f_45579_, $$4.m_123170_(), $$4.m_123222_(), this.f_140126_);
                    this.m_183754_((ServerPlayer)p_214864_, $$4, (MutableObject<ClientboundLevelChunkWithLightPacket>)$$5, $$5, $$6);
                });
            }
        }
    }

    protected void m_183754_(ServerPlayer p_183755_, ChunkPos p_183756_, MutableObject<ClientboundLevelChunkWithLightPacket> p_183757_, boolean p_183758_, boolean p_183759_) {
        ChunkHolder $$5;
        if (p_183755_.f_19853_ != this.f_140133_) {
            return;
        }
        if (p_183759_ && !p_183758_ && ($$5 = this.m_140327_(p_183756_.m_45588_())) != null) {
            LevelChunk $$6 = $$5.m_140085_();
            if ($$6 != null) {
                this.m_183760_(p_183755_, p_183757_, $$6);
            }
            DebugPackets.m_133676_(this.f_140133_, p_183756_);
        }
        if (!p_183759_ && p_183758_) {
            p_183755_.m_9088_(p_183756_);
        }
    }

    public int m_140394_() {
        return this.f_140130_.size();
    }

    public net.minecraft.server.level.DistanceManager m_143145_() {
        return this.f_140145_;
    }

    protected Iterable<ChunkHolder> m_140416_() {
        return Iterables.unmodifiableIterable((Iterable)this.f_140130_.values());
    }

    void m_140274_(Writer p_140275_) throws IOException {
        CsvOutput $$1 = CsvOutput.m_13619_().m_13630_("x").m_13630_("z").m_13630_("level").m_13630_("in_memory").m_13630_("status").m_13630_("full_status").m_13630_("accessible_ready").m_13630_("ticking_ready").m_13630_("entity_ticking_ready").m_13630_("ticket").m_13630_("spawning").m_13630_("block_entity_count").m_13630_("ticking_ticket").m_13630_("ticking_level").m_13630_("block_ticks").m_13630_("fluid_ticks").m_13628_(p_140275_);
        TickingTracker $$2 = this.f_140145_.m_183915_();
        for (Long2ObjectMap.Entry $$3 : this.f_140130_.long2ObjectEntrySet()) {
            long $$4 = $$3.getLongKey();
            ChunkPos $$5 = new ChunkPos($$4);
            ChunkHolder $$6 = (ChunkHolder)$$3.getValue();
            Optional<ChunkAccess> $$7 = Optional.ofNullable($$6.m_140089_());
            Optional<Object> $$8 = $$7.flatMap(p_214932_ -> p_214932_ instanceof LevelChunk ? Optional.of((LevelChunk)p_214932_) : Optional.empty());
            $$1.m_13624_($$5.f_45578_, $$5.f_45579_, $$6.m_140093_(), $$7.isPresent(), $$7.map(ChunkAccess::m_6415_).orElse(null), $$8.map(LevelChunk::m_6708_).orElse(null), ChunkMap.m_140278_($$6.m_140082_()), ChunkMap.m_140278_($$6.m_140026_()), ChunkMap.m_140278_($$6.m_140073_()), this.f_140145_.m_140838_($$4), this.m_183879_($$5), $$8.map(p_214953_ -> p_214953_.m_62954_().size()).orElse(0), $$2.m_184175_($$4), $$2.m_6172_($$4), $$8.map(p_214946_ -> p_214946_.m_183531_().m_183574_()).orElse(0), $$8.map(p_214937_ -> p_214937_.m_183526_().m_183574_()).orElse(0));
        }
    }

    private static String m_140278_(CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> p_140279_) {
        try {
            Either $$1 = p_140279_.getNow(null);
            if ($$1 != null) {
                return (String)$$1.map(p_214897_ -> "done", p_214850_ -> "unloaded");
            }
            return "not completed";
        }
        catch (CompletionException $$2) {
            return "failed " + $$2.getCause().getMessage();
        }
        catch (CancellationException $$3) {
            return "cancelled";
        }
    }

    private CompletableFuture<Optional<CompoundTag>> m_214963_(ChunkPos p_214964_) {
        return this.m_223454_(p_214964_).thenApplyAsync(p_214907_ -> p_214907_.map(this::m_214947_), (Executor)Util.m_183991_());
    }

    private CompoundTag m_214947_(CompoundTag p_214948_) {
        return this.m_188288_(this.f_140133_.m_46472_(), this.f_140137_, p_214948_, this.f_140136_.m_187743_());
    }

    boolean m_183879_(ChunkPos p_183880_) {
        long $$1 = p_183880_.m_45588_();
        if (!this.f_140145_.m_140847_($$1)) {
            return false;
        }
        for (ServerPlayer $$2 : this.f_140149_.m_183926_($$1)) {
            if (!this.m_183751_($$2, p_183880_)) continue;
            return true;
        }
        return false;
    }

    public List<ServerPlayer> m_183888_(ChunkPos p_183889_) {
        long $$1 = p_183889_.m_45588_();
        if (!this.f_140145_.m_140847_($$1)) {
            return List.of();
        }
        ImmutableList.Builder $$2 = ImmutableList.builder();
        for (ServerPlayer $$3 : this.f_140149_.m_183926_($$1)) {
            if (!this.m_183751_($$3, p_183889_)) continue;
            $$2.add((Object)$$3);
        }
        return $$2.build();
    }

    private boolean m_183751_(ServerPlayer p_183752_, ChunkPos p_183753_) {
        if (p_183752_.m_5833_()) {
            return false;
        }
        double $$2 = ChunkMap.m_140226_(p_183753_, p_183752_);
        return $$2 < 16384.0;
    }

    private boolean m_140329_(ServerPlayer p_140330_) {
        return p_140330_.m_5833_() && !this.f_140133_.m_46469_().m_46207_(GameRules.f_46146_);
    }

    void m_140192_(ServerPlayer p_140193_, boolean p_140194_) {
        boolean $$2 = this.m_140329_(p_140193_);
        boolean $$3 = this.f_140149_.m_8260_(p_140193_);
        int $$4 = SectionPos.m_123171_(p_140193_.m_146903_());
        int $$5 = SectionPos.m_123171_(p_140193_.m_146907_());
        if (p_140194_) {
            this.f_140149_.m_8252_(ChunkPos.m_45589_($$4, $$5), p_140193_, $$2);
            this.m_140373_(p_140193_);
            if (!$$2) {
                this.f_140145_.m_140802_(SectionPos.m_235861_(p_140193_), p_140193_);
            }
        } else {
            SectionPos $$6 = p_140193_.m_8965_();
            this.f_140149_.m_8249_($$6.m_123251_().m_45588_(), p_140193_);
            if (!$$3) {
                this.f_140145_.m_140828_($$6, p_140193_);
            }
        }
        for (int $$7 = $$4 - this.f_140126_ - 1; $$7 <= $$4 + this.f_140126_ + 1; ++$$7) {
            for (int $$8 = $$5 - this.f_140126_ - 1; $$8 <= $$5 + this.f_140126_ + 1; ++$$8) {
                if (!ChunkMap.m_200878_($$7, $$8, $$4, $$5, this.f_140126_)) continue;
                ChunkPos $$9 = new ChunkPos($$7, $$8);
                this.m_183754_(p_140193_, $$9, (MutableObject<ClientboundLevelChunkWithLightPacket>)new MutableObject(), !p_140194_, p_140194_);
            }
        }
    }

    private SectionPos m_140373_(ServerPlayer p_140374_) {
        SectionPos $$1 = SectionPos.m_235861_(p_140374_);
        p_140374_.m_9119_($$1);
        p_140374_.f_8906_.m_9829_(new ClientboundSetChunkCacheCenterPacket($$1.m_123170_(), $$1.m_123222_()));
        return $$1;
    }

    public void m_140184_(ServerPlayer p_140185_) {
        boolean $$10;
        for (TrackedEntity $$1 : this.f_140150_.values()) {
            if ($$1.f_140472_ == p_140185_) {
                $$1.m_140487_(this.f_140133_.m_6907_());
                continue;
            }
            $$1.m_140497_(p_140185_);
        }
        int $$2 = SectionPos.m_123171_(p_140185_.m_146903_());
        int $$3 = SectionPos.m_123171_(p_140185_.m_146907_());
        SectionPos $$4 = p_140185_.m_8965_();
        SectionPos $$5 = SectionPos.m_235861_(p_140185_);
        long $$6 = $$4.m_123251_().m_45588_();
        long $$7 = $$5.m_123251_().m_45588_();
        boolean $$8 = this.f_140149_.m_8262_(p_140185_);
        boolean $$9 = this.m_140329_(p_140185_);
        boolean bl = $$10 = $$4.m_123252_() != $$5.m_123252_();
        if ($$10 || $$8 != $$9) {
            this.m_140373_(p_140185_);
            if (!$$8) {
                this.f_140145_.m_140828_($$4, p_140185_);
            }
            if (!$$9) {
                this.f_140145_.m_140802_($$5, p_140185_);
            }
            if (!$$8 && $$9) {
                this.f_140149_.m_8256_(p_140185_);
            }
            if ($$8 && !$$9) {
                this.f_140149_.m_8258_(p_140185_);
            }
            if ($$6 != $$7) {
                this.f_140149_.m_8245_($$6, $$7, p_140185_);
            }
        }
        int $$11 = $$4.m_123170_();
        int $$12 = $$4.m_123222_();
        if (Math.abs($$11 - $$2) <= this.f_140126_ * 2 && Math.abs($$12 - $$3) <= this.f_140126_ * 2) {
            int $$13 = Math.min($$2, $$11) - this.f_140126_ - 1;
            int $$14 = Math.min($$3, $$12) - this.f_140126_ - 1;
            int $$15 = Math.max($$2, $$11) + this.f_140126_ + 1;
            int $$16 = Math.max($$3, $$12) + this.f_140126_ + 1;
            for (int $$17 = $$13; $$17 <= $$15; ++$$17) {
                for (int $$18 = $$14; $$18 <= $$16; ++$$18) {
                    boolean $$19 = ChunkMap.m_200878_($$17, $$18, $$11, $$12, this.f_140126_);
                    boolean $$20 = ChunkMap.m_200878_($$17, $$18, $$2, $$3, this.f_140126_);
                    this.m_183754_(p_140185_, new ChunkPos($$17, $$18), (MutableObject<ClientboundLevelChunkWithLightPacket>)new MutableObject(), $$19, $$20);
                }
            }
        } else {
            for (int $$21 = $$11 - this.f_140126_ - 1; $$21 <= $$11 + this.f_140126_ + 1; ++$$21) {
                for (int $$22 = $$12 - this.f_140126_ - 1; $$22 <= $$12 + this.f_140126_ + 1; ++$$22) {
                    if (!ChunkMap.m_200878_($$21, $$22, $$11, $$12, this.f_140126_)) continue;
                    boolean $$23 = true;
                    boolean $$24 = false;
                    this.m_183754_(p_140185_, new ChunkPos($$21, $$22), (MutableObject<ClientboundLevelChunkWithLightPacket>)new MutableObject(), true, false);
                }
            }
            for (int $$25 = $$2 - this.f_140126_ - 1; $$25 <= $$2 + this.f_140126_ + 1; ++$$25) {
                for (int $$26 = $$3 - this.f_140126_ - 1; $$26 <= $$3 + this.f_140126_ + 1; ++$$26) {
                    if (!ChunkMap.m_200878_($$25, $$26, $$2, $$3, this.f_140126_)) continue;
                    boolean $$27 = false;
                    boolean $$28 = true;
                    this.m_183754_(p_140185_, new ChunkPos($$25, $$26), (MutableObject<ClientboundLevelChunkWithLightPacket>)new MutableObject(), false, true);
                }
            }
        }
    }

    @Override
    public List<ServerPlayer> m_183262_(ChunkPos p_183801_, boolean p_183802_) {
        Set<ServerPlayer> $$2 = this.f_140149_.m_183926_(p_183801_.m_45588_());
        ImmutableList.Builder $$3 = ImmutableList.builder();
        for (ServerPlayer $$4 : $$2) {
            SectionPos $$5 = $$4.m_8965_();
            if ((!p_183802_ || !ChunkMap.m_183828_(p_183801_.f_45578_, p_183801_.f_45579_, $$5.m_123170_(), $$5.m_123222_(), this.f_140126_)) && (p_183802_ || !ChunkMap.m_200878_(p_183801_.f_45578_, p_183801_.f_45579_, $$5.m_123170_(), $$5.m_123222_(), this.f_140126_))) continue;
            $$3.add((Object)$$4);
        }
        return $$3.build();
    }

    protected void m_140199_(Entity p_140200_) {
        if (p_140200_ instanceof EnderDragonPart) {
            return;
        }
        EntityType<?> $$1 = p_140200_.m_6095_();
        int $$2 = $$1.m_20681_() * 16;
        if ($$2 == 0) {
            return;
        }
        int $$3 = $$1.m_20682_();
        if (this.f_140150_.containsKey(p_140200_.m_19879_())) {
            throw Util.m_137570_(new IllegalStateException("Entity is already tracked!"));
        }
        TrackedEntity $$4 = new TrackedEntity(p_140200_, $$2, $$3, $$1.m_20683_());
        this.f_140150_.put(p_140200_.m_19879_(), (Object)$$4);
        $$4.m_140487_(this.f_140133_.m_6907_());
        if (p_140200_ instanceof ServerPlayer) {
            ServerPlayer $$5 = (ServerPlayer)p_140200_;
            this.m_140192_($$5, true);
            for (TrackedEntity $$6 : this.f_140150_.values()) {
                if ($$6.f_140472_ == $$5) continue;
                $$6.m_140497_($$5);
            }
        }
    }

    protected void m_140331_(Entity p_140332_) {
        TrackedEntity $$3;
        if (p_140332_ instanceof ServerPlayer) {
            ServerPlayer $$1 = (ServerPlayer)p_140332_;
            this.m_140192_($$1, false);
            for (TrackedEntity $$2 : this.f_140150_.values()) {
                $$2.m_140485_($$1);
            }
        }
        if (($$3 = (TrackedEntity)this.f_140150_.remove(p_140332_.m_19879_())) != null) {
            $$3.m_140482_();
        }
    }

    protected void m_140421_() {
        ArrayList $$0 = Lists.newArrayList();
        List<ServerPlayer> $$1 = this.f_140133_.m_6907_();
        for (TrackedEntity $$2 : this.f_140150_.values()) {
            boolean $$5;
            SectionPos $$3 = $$2.f_140474_;
            SectionPos $$4 = SectionPos.m_235861_($$2.f_140472_);
            boolean bl = $$5 = !Objects.equals($$3, $$4);
            if ($$5) {
                $$2.m_140487_($$1);
                Entity $$6 = $$2.f_140472_;
                if ($$6 instanceof ServerPlayer) {
                    $$0.add((ServerPlayer)$$6);
                }
                $$2.f_140474_ = $$4;
            }
            if (!$$5 && !this.f_140145_.m_183913_($$4.m_123251_().m_45588_())) continue;
            $$2.f_140471_.m_8533_();
        }
        if (!$$0.isEmpty()) {
            for (TrackedEntity $$7 : this.f_140150_.values()) {
                $$7.m_140487_($$0);
            }
        }
    }

    public void m_140201_(Entity p_140202_, Packet<?> p_140203_) {
        TrackedEntity $$2 = (TrackedEntity)this.f_140150_.get(p_140202_.m_19879_());
        if ($$2 != null) {
            $$2.m_140489_(p_140203_);
        }
    }

    protected void m_140333_(Entity p_140334_, Packet<?> p_140335_) {
        TrackedEntity $$2 = (TrackedEntity)this.f_140150_.get(p_140334_.m_19879_());
        if ($$2 != null) {
            $$2.m_140499_(p_140335_);
        }
    }

    private void m_183760_(ServerPlayer p_183761_, MutableObject<ClientboundLevelChunkWithLightPacket> p_183762_, LevelChunk p_183763_) {
        if (p_183762_.getValue() == null) {
            p_183762_.setValue((Object)new ClientboundLevelChunkWithLightPacket(p_183763_, this.f_140134_, null, null, true));
        }
        p_183761_.m_184135_(p_183763_.m_7697_(), (Packet)p_183762_.getValue());
        DebugPackets.m_133676_(this.f_140133_, p_183763_.m_7697_());
        ArrayList $$3 = Lists.newArrayList();
        ArrayList $$4 = Lists.newArrayList();
        for (TrackedEntity $$5 : this.f_140150_.values()) {
            Entity $$6 = $$5.f_140472_;
            if ($$6 == p_183761_ || !$$6.m_146902_().equals(p_183763_.m_7697_())) continue;
            $$5.m_140497_(p_183761_);
            if ($$6 instanceof Mob && ((Mob)$$6).m_21524_() != null) {
                $$3.add($$6);
            }
            if ($$6.m_20197_().isEmpty()) continue;
            $$4.add($$6);
        }
        if (!$$3.isEmpty()) {
            for (Entity $$7 : $$3) {
                p_183761_.f_8906_.m_9829_(new ClientboundSetEntityLinkPacket($$7, ((Mob)$$7).m_21524_()));
            }
        }
        if (!$$4.isEmpty()) {
            for (Entity $$8 : $$4) {
                p_183761_.f_8906_.m_9829_(new ClientboundSetPassengersPacket($$8));
            }
        }
    }

    protected PoiManager m_140424_() {
        return this.f_140138_;
    }

    public String m_182285_() {
        return this.f_182284_;
    }

    void m_143075_(ChunkPos p_143076_, ChunkHolder.FullChunkStatus p_143077_) {
        this.f_143031_.m_156794_(p_143076_, p_143077_);
    }

    class DistanceManager
    extends net.minecraft.server.level.DistanceManager {
        protected DistanceManager(Executor p_140459_, Executor p_140460_) {
            super(p_140459_, p_140460_);
        }

        @Override
        protected boolean m_7009_(long p_140462_) {
            return ChunkMap.this.f_140139_.contains(p_140462_);
        }

        @Override
        @Nullable
        protected ChunkHolder m_7316_(long p_140469_) {
            return ChunkMap.this.m_140174_(p_140469_);
        }

        @Override
        @Nullable
        protected ChunkHolder m_7288_(long p_140464_, int p_140465_, @Nullable ChunkHolder p_140466_, int p_140467_) {
            return ChunkMap.this.m_140176_(p_140464_, p_140465_, p_140466_, p_140467_);
        }
    }

    class TrackedEntity {
        final ServerEntity f_140471_;
        final Entity f_140472_;
        private final int f_140473_;
        SectionPos f_140474_;
        private final Set<ServerPlayerConnection> f_140475_ = Sets.newIdentityHashSet();

        public TrackedEntity(Entity p_140478_, int p_140479_, int p_140480_, boolean p_140481_) {
            this.f_140471_ = new ServerEntity(ChunkMap.this.f_140133_, p_140478_, p_140480_, p_140481_, this::m_140489_);
            this.f_140472_ = p_140478_;
            this.f_140473_ = p_140479_;
            this.f_140474_ = SectionPos.m_235861_(p_140478_);
        }

        public boolean equals(Object p_140506_) {
            if (p_140506_ instanceof TrackedEntity) {
                return ((TrackedEntity)p_140506_).f_140472_.m_19879_() == this.f_140472_.m_19879_();
            }
            return false;
        }

        public int hashCode() {
            return this.f_140472_.m_19879_();
        }

        public void m_140489_(Packet<?> p_140490_) {
            for (ServerPlayerConnection $$1 : this.f_140475_) {
                $$1.m_9829_(p_140490_);
            }
        }

        public void m_140499_(Packet<?> p_140500_) {
            this.m_140489_(p_140500_);
            if (this.f_140472_ instanceof ServerPlayer) {
                ((ServerPlayer)this.f_140472_).f_8906_.m_9829_(p_140500_);
            }
        }

        public void m_140482_() {
            for (ServerPlayerConnection $$0 : this.f_140475_) {
                this.f_140471_.m_8534_($$0.m_142253_());
            }
        }

        public void m_140485_(ServerPlayer p_140486_) {
            if (this.f_140475_.remove(p_140486_.f_8906_)) {
                this.f_140471_.m_8534_(p_140486_);
            }
        }

        public void m_140497_(ServerPlayer p_140498_) {
            boolean $$5;
            if (p_140498_ == this.f_140472_) {
                return;
            }
            Vec3 $$1 = p_140498_.m_20182_().m_82546_(this.f_140472_.m_20182_());
            double $$3 = $$1.f_82479_ * $$1.f_82479_ + $$1.f_82481_ * $$1.f_82481_;
            double $$2 = Math.min(this.m_140496_(), (ChunkMap.this.f_140126_ - 1) * 16);
            double $$4 = $$2 * $$2;
            boolean bl = $$5 = $$3 <= $$4 && this.f_140472_.m_6459_(p_140498_);
            if ($$5) {
                if (this.f_140475_.add(p_140498_.f_8906_)) {
                    this.f_140471_.m_8541_(p_140498_);
                }
            } else if (this.f_140475_.remove(p_140498_.f_8906_)) {
                this.f_140471_.m_8534_(p_140498_);
            }
        }

        private int m_140483_(int p_140484_) {
            return ChunkMap.this.f_140133_.m_7654_().m_7186_(p_140484_);
        }

        private int m_140496_() {
            int $$0 = this.f_140473_;
            for (Entity $$1 : this.f_140472_.m_146897_()) {
                int $$2 = $$1.m_6095_().m_20681_() * 16;
                if ($$2 <= $$0) continue;
                $$0 = $$2;
            }
            return this.m_140483_($$0);
        }

        public void m_140487_(List<ServerPlayer> p_140488_) {
            for (ServerPlayer $$1 : p_140488_) {
                this.m_140497_($$1);
            }
        }
    }
}

