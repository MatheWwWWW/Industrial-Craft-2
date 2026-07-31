/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.datafixers.util.Either
 *  javax.annotation.Nullable
 */
package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.DistanceManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.LocalMobCapCalculator;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.storage.ChunkScanAccess;
import net.minecraft.world.level.entity.ChunkStatusUpdateListener;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.LevelStorageSource;

public class ServerChunkCache
extends ChunkSource {
    private static final List<ChunkStatus> f_8326_ = ChunkStatus.m_62349_();
    private final DistanceManager f_8327_;
    final ServerLevel f_8329_;
    final Thread f_8330_;
    final ThreadedLevelLightEngine f_8331_;
    private final MainThreadExecutor f_8332_;
    public final ChunkMap f_8325_;
    private final DimensionDataStorage f_8333_;
    private long f_8334_;
    private boolean f_8335_ = true;
    private boolean f_8336_ = true;
    private static final int f_143226_ = 4;
    private final long[] f_8337_ = new long[4];
    private final ChunkStatus[] f_8338_ = new ChunkStatus[4];
    private final ChunkAccess[] f_8339_ = new ChunkAccess[4];
    @Nullable
    @VisibleForDebug
    private NaturalSpawner.SpawnState f_8340_;

    public ServerChunkCache(ServerLevel p_214982_, LevelStorageSource.LevelStorageAccess p_214983_, DataFixer p_214984_, StructureTemplateManager p_214985_, Executor p_214986_, ChunkGenerator p_214987_, int p_214988_, int p_214989_, boolean p_214990_, ChunkProgressListener p_214991_, ChunkStatusUpdateListener p_214992_, Supplier<DimensionDataStorage> p_214993_) {
        this.f_8329_ = p_214982_;
        this.f_8332_ = new MainThreadExecutor(p_214982_);
        this.f_8330_ = Thread.currentThread();
        File $$12 = p_214983_.m_197394_(p_214982_.m_46472_()).resolve("data").toFile();
        $$12.mkdirs();
        this.f_8333_ = new DimensionDataStorage($$12, p_214984_);
        this.f_8325_ = new ChunkMap(p_214982_, p_214983_, p_214984_, p_214985_, p_214986_, this.f_8332_, this, p_214987_, p_214991_, p_214992_, p_214993_, p_214988_, p_214990_);
        this.f_8331_ = this.f_8325_.m_140166_();
        this.f_8327_ = this.f_8325_.m_143145_();
        this.f_8327_.m_183911_(p_214989_);
        this.m_8488_();
    }

    @Override
    public ThreadedLevelLightEngine m_7827_() {
        return this.f_8331_;
    }

    @Nullable
    private ChunkHolder m_8364_(long p_8365_) {
        return this.f_8325_.m_140327_(p_8365_);
    }

    public int m_8427_() {
        return this.f_8325_.m_140368_();
    }

    private void m_8366_(long p_8367_, ChunkAccess p_8368_, ChunkStatus p_8369_) {
        for (int $$3 = 3; $$3 > 0; --$$3) {
            this.f_8337_[$$3] = this.f_8337_[$$3 - 1];
            this.f_8338_[$$3] = this.f_8338_[$$3 - 1];
            this.f_8339_[$$3] = this.f_8339_[$$3 - 1];
        }
        this.f_8337_[0] = p_8367_;
        this.f_8338_[0] = p_8369_;
        this.f_8339_[0] = p_8368_;
    }

    @Override
    @Nullable
    public ChunkAccess m_7587_(int p_8360_, int p_8361_, ChunkStatus p_8362_, boolean p_8363_) {
        if (Thread.currentThread() != this.f_8330_) {
            return CompletableFuture.supplyAsync(() -> this.m_7587_(p_8360_, p_8361_, p_8362_, p_8363_), this.f_8332_).join();
        }
        ProfilerFiller $$4 = this.f_8329_.m_46473_();
        $$4.m_6174_("getChunk");
        long $$5 = ChunkPos.m_45589_(p_8360_, p_8361_);
        for (int $$6 = 0; $$6 < 4; ++$$6) {
            ChunkAccess $$7;
            if ($$5 != this.f_8337_[$$6] || p_8362_ != this.f_8338_[$$6] || ($$7 = this.f_8339_[$$6]) == null && p_8363_) continue;
            return $$7;
        }
        $$4.m_6174_("getChunkCacheMiss");
        CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> $$8 = this.m_8456_(p_8360_, p_8361_, p_8362_, p_8363_);
        this.f_8332_.m_18701_($$8::isDone);
        ChunkAccess $$9 = (ChunkAccess)$$8.join().map(p_8406_ -> p_8406_, p_8423_ -> {
            if (p_8363_) {
                throw Util.m_137570_(new IllegalStateException("Chunk not there when requested: " + p_8423_));
            }
            return null;
        });
        this.m_8366_($$5, $$9, p_8362_);
        return $$9;
    }

    @Override
    @Nullable
    public LevelChunk m_7131_(int p_8357_, int p_8358_) {
        if (Thread.currentThread() != this.f_8330_) {
            return null;
        }
        this.f_8329_.m_46473_().m_6174_("getChunkNow");
        long $$2 = ChunkPos.m_45589_(p_8357_, p_8358_);
        for (int $$3 = 0; $$3 < 4; ++$$3) {
            if ($$2 != this.f_8337_[$$3] || this.f_8338_[$$3] != ChunkStatus.f_62326_) continue;
            ChunkAccess $$4 = this.f_8339_[$$3];
            return $$4 instanceof LevelChunk ? (LevelChunk)$$4 : null;
        }
        ChunkHolder $$5 = this.m_8364_($$2);
        if ($$5 == null) {
            return null;
        }
        Either $$6 = $$5.m_140080_(ChunkStatus.f_62326_).getNow(null);
        if ($$6 == null) {
            return null;
        }
        ChunkAccess $$7 = $$6.left().orElse(null);
        if ($$7 != null) {
            this.m_8366_($$2, $$7, ChunkStatus.f_62326_);
            if ($$7 instanceof LevelChunk) {
                return (LevelChunk)$$7;
            }
        }
        return null;
    }

    private void m_8488_() {
        Arrays.fill(this.f_8337_, ChunkPos.f_45577_);
        Arrays.fill(this.f_8338_, null);
        Arrays.fill(this.f_8339_, null);
    }

    public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_8431_(int p_8432_, int p_8433_, ChunkStatus p_8434_, boolean p_8435_) {
        CompletionStage $$6;
        boolean $$4;
        boolean bl = $$4 = Thread.currentThread() == this.f_8330_;
        if ($$4) {
            CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> $$5 = this.m_8456_(p_8432_, p_8433_, p_8434_, p_8435_);
            this.f_8332_.m_18701_($$5::isDone);
        } else {
            $$6 = CompletableFuture.supplyAsync(() -> this.m_8456_(p_8432_, p_8433_, p_8434_, p_8435_), this.f_8332_).thenCompose(p_8413_ -> p_8413_);
        }
        return $$6;
    }

    private CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> m_8456_(int p_8457_, int p_8458_, ChunkStatus p_8459_, boolean p_8460_) {
        ChunkPos $$4 = new ChunkPos(p_8457_, p_8458_);
        long $$5 = $$4.m_45588_();
        int $$6 = 33 + ChunkStatus.m_62370_(p_8459_);
        ChunkHolder $$7 = this.m_8364_($$5);
        if (p_8460_) {
            this.f_8327_.m_140792_(TicketType.f_9449_, $$4, $$6, $$4);
            if (this.m_8416_($$7, $$6)) {
                ProfilerFiller $$8 = this.f_8329_.m_46473_();
                $$8.m_6180_("chunkLoad");
                this.m_8489_();
                $$7 = this.m_8364_($$5);
                $$8.m_7238_();
                if (this.m_8416_($$7, $$6)) {
                    throw Util.m_137570_(new IllegalStateException("No chunk holder after ticket has been added"));
                }
            }
        }
        if (this.m_8416_($$7, $$6)) {
            return ChunkHolder.f_139996_;
        }
        return $$7.m_140049_(p_8459_, this.f_8325_);
    }

    private boolean m_8416_(@Nullable ChunkHolder p_8417_, int p_8418_) {
        return p_8417_ == null || p_8417_.m_140093_() > p_8418_;
    }

    @Override
    public boolean m_5563_(int p_8429_, int p_8430_) {
        int $$3;
        ChunkHolder $$2 = this.m_8364_(new ChunkPos(p_8429_, p_8430_).m_45588_());
        return !this.m_8416_($$2, $$3 = 33 + ChunkStatus.m_62370_(ChunkStatus.f_62326_));
    }

    @Override
    public BlockGetter m_6196_(int p_8454_, int p_8455_) {
        long $$2 = ChunkPos.m_45589_(p_8454_, p_8455_);
        ChunkHolder $$3 = this.m_8364_($$2);
        if ($$3 == null) {
            return null;
        }
        int $$4 = f_8326_.size() - 1;
        while (true) {
            ChunkStatus $$5;
            Optional $$6;
            if (($$6 = $$3.m_140047_($$5 = f_8326_.get($$4)).getNow(ChunkHolder.f_139995_).left()).isPresent()) {
                return (BlockGetter)$$6.get();
            }
            if ($$5 == ChunkStatus.f_62323_.m_62482_()) break;
            --$$4;
        }
        return null;
    }

    @Override
    public Level m_7653_() {
        return this.f_8329_;
    }

    public boolean m_8466_() {
        return this.f_8332_.m_7245_();
    }

    boolean m_8489_() {
        boolean $$0 = this.f_8327_.m_140805_(this.f_8325_);
        boolean $$1 = this.f_8325_.m_140324_();
        if ($$0 || $$1) {
            this.m_8488_();
            return true;
        }
        return false;
    }

    public boolean m_143239_(long p_143240_) {
        ChunkHolder $$1 = this.m_8364_(p_143240_);
        if ($$1 == null) {
            return false;
        }
        if (!this.f_8329_.m_183438_(p_143240_)) {
            return false;
        }
        Either $$2 = $$1.m_140026_().getNow(null);
        return $$2 != null && $$2.left().isPresent();
    }

    public void m_8419_(boolean p_8420_) {
        this.m_8489_();
        this.f_8325_.m_140318_(p_8420_);
    }

    @Override
    public void close() throws IOException {
        this.m_8419_(true);
        this.f_8331_.close();
        this.f_8325_.close();
    }

    @Override
    public void m_201698_(BooleanSupplier p_201913_, boolean p_201914_) {
        this.f_8329_.m_46473_().m_6180_("purge");
        this.f_8327_.m_140776_();
        this.m_8489_();
        this.f_8329_.m_46473_().m_6182_("chunks");
        if (p_201914_) {
            this.m_8490_();
        }
        this.f_8329_.m_46473_().m_6182_("unload");
        this.f_8325_.m_140280_(p_201913_);
        this.f_8329_.m_46473_().m_7238_();
        this.m_8488_();
    }

    private void m_8490_() {
        NaturalSpawner.SpawnState $$8;
        long $$0 = this.f_8329_.m_46467_();
        long $$1 = $$0 - this.f_8334_;
        this.f_8334_ = $$0;
        boolean $$2 = this.f_8329_.m_46659_();
        if ($$2) {
            this.f_8325_.m_140421_();
            return;
        }
        LevelData $$3 = this.f_8329_.m_6106_();
        ProfilerFiller $$4 = this.f_8329_.m_46473_();
        $$4.m_6180_("pollingChunks");
        int $$5 = this.f_8329_.m_46469_().m_46215_(GameRules.f_46143_);
        boolean $$6 = $$3.m_6793_() % 400L == 0L;
        $$4.m_6180_("naturalSpawnCount");
        int $$7 = this.f_8327_.m_140816_();
        this.f_8340_ = $$8 = NaturalSpawner.m_186524_($$7, this.f_8329_.m_8583_(), this::m_8370_, new LocalMobCapCalculator(this.f_8325_));
        $$4.m_6182_("filteringLoadedChunks");
        ArrayList $$9 = Lists.newArrayListWithCapacity((int)$$7);
        for (ChunkHolder $$10 : this.f_8325_.m_140416_()) {
            LevelChunk $$11 = $$10.m_140085_();
            if ($$11 == null) continue;
            $$9.add(new ChunkAndHolder($$11, $$10));
        }
        $$4.m_6182_("spawnAndTick");
        boolean $$12 = this.f_8329_.m_46469_().m_46207_(GameRules.f_46134_);
        Collections.shuffle($$9);
        for (ChunkAndHolder $$13 : $$9) {
            LevelChunk $$14 = $$13.f_184028_;
            ChunkPos $$15 = $$14.m_7697_();
            if (!this.f_8329_.m_201916_($$15) || !this.f_8325_.m_183879_($$15)) continue;
            $$14.m_187632_($$1);
            if ($$12 && (this.f_8335_ || this.f_8336_) && this.f_8329_.m_6857_().m_61927_($$15)) {
                NaturalSpawner.m_47029_(this.f_8329_, $$14, $$8, this.f_8336_, this.f_8335_, $$6);
            }
            if (!this.f_8329_.m_183438_($$15.m_45588_())) continue;
            this.f_8329_.m_8714_($$14, $$5);
        }
        $$4.m_6182_("customSpawners");
        if ($$12) {
            this.f_8329_.m_8799_(this.f_8335_, this.f_8336_);
        }
        $$4.m_6182_("broadcast");
        $$9.forEach(p_184022_ -> p_184022_.f_184029_.m_140054_(p_184022_.f_184028_));
        $$4.m_7238_();
        $$4.m_7238_();
        this.f_8325_.m_140421_();
    }

    private void m_8370_(long p_8371_, Consumer<LevelChunk> p_8372_) {
        ChunkHolder $$2 = this.m_8364_(p_8371_);
        if ($$2 != null) {
            $$2.m_140082_().getNow(ChunkHolder.f_139997_).left().ifPresent(p_8372_);
        }
    }

    @Override
    public String m_6754_() {
        return Integer.toString(this.m_8482_());
    }

    @VisibleForTesting
    public int m_8480_() {
        return this.f_8332_.m_18696_();
    }

    public ChunkGenerator m_8481_() {
        return this.f_8325_.m_183719_();
    }

    public RandomState m_214994_() {
        return this.f_8325_.m_214914_();
    }

    @Override
    public int m_8482_() {
        return this.f_8325_.m_140394_();
    }

    public void m_8450_(BlockPos p_8451_) {
        int $$2;
        int $$1 = SectionPos.m_123171_(p_8451_.m_123341_());
        ChunkHolder $$3 = this.m_8364_(ChunkPos.m_45589_($$1, $$2 = SectionPos.m_123171_(p_8451_.m_123343_())));
        if ($$3 != null) {
            $$3.m_140056_(p_8451_);
        }
    }

    @Override
    public void m_6506_(LightLayer p_8403_, SectionPos p_8404_) {
        this.f_8332_.execute(() -> {
            ChunkHolder $$2 = this.m_8364_(p_8404_.m_123251_().m_45588_());
            if ($$2 != null) {
                $$2.m_140036_(p_8403_, p_8404_.m_123206_());
            }
        });
    }

    public <T> void m_8387_(TicketType<T> p_8388_, ChunkPos p_8389_, int p_8390_, T p_8391_) {
        this.f_8327_.m_140840_(p_8388_, p_8389_, p_8390_, p_8391_);
    }

    public <T> void m_8438_(TicketType<T> p_8439_, ChunkPos p_8440_, int p_8441_, T p_8442_) {
        this.f_8327_.m_140849_(p_8439_, p_8440_, p_8441_, p_8442_);
    }

    @Override
    public void m_6692_(ChunkPos p_8400_, boolean p_8401_) {
        this.f_8327_.m_140799_(p_8400_, p_8401_);
    }

    public void m_8385_(ServerPlayer p_8386_) {
        if (!p_8386_.m_213877_()) {
            this.f_8325_.m_140184_(p_8386_);
        }
    }

    public void m_8443_(Entity p_8444_) {
        this.f_8325_.m_140331_(p_8444_);
    }

    public void m_8463_(Entity p_8464_) {
        this.f_8325_.m_140199_(p_8464_);
    }

    public void m_8394_(Entity p_8395_, Packet<?> p_8396_) {
        this.f_8325_.m_140333_(p_8395_, p_8396_);
    }

    public void m_8445_(Entity p_8446_, Packet<?> p_8447_) {
        this.f_8325_.m_140201_(p_8446_, p_8447_);
    }

    public void m_8354_(int p_8355_) {
        this.f_8325_.m_140167_(p_8355_);
    }

    public void m_184026_(int p_184027_) {
        this.f_8327_.m_183911_(p_184027_);
    }

    @Override
    public void m_6707_(boolean p_8425_, boolean p_8426_) {
        this.f_8335_ = p_8425_;
        this.f_8336_ = p_8426_;
    }

    public String m_8448_(ChunkPos p_8449_) {
        return this.f_8325_.m_140204_(p_8449_);
    }

    public DimensionDataStorage m_8483_() {
        return this.f_8333_;
    }

    public PoiManager m_8484_() {
        return this.f_8325_.m_140424_();
    }

    public ChunkScanAccess m_196555_() {
        return this.f_8325_.m_196922_();
    }

    @Nullable
    @VisibleForDebug
    public NaturalSpawner.SpawnState m_8485_() {
        return this.f_8340_;
    }

    public void m_201915_() {
        this.f_8327_.m_201910_();
    }

    @Override
    public /* synthetic */ LevelLightEngine m_7827_() {
        return this.m_7827_();
    }

    @Override
    public /* synthetic */ BlockGetter m_7653_() {
        return this.m_7653_();
    }

    final class MainThreadExecutor
    extends BlockableEventLoop<Runnable> {
        MainThreadExecutor(Level p_8494_) {
            super("Chunk source main thread executor for " + p_8494_.m_46472_().m_135782_());
        }

        @Override
        protected Runnable m_6681_(Runnable p_8506_) {
            return p_8506_;
        }

        @Override
        protected boolean m_6362_(Runnable p_8504_) {
            return true;
        }

        @Override
        protected boolean m_5660_() {
            return true;
        }

        @Override
        protected Thread m_6304_() {
            return ServerChunkCache.this.f_8330_;
        }

        @Override
        protected void m_6367_(Runnable p_8502_) {
            ServerChunkCache.this.f_8329_.m_46473_().m_6174_("runTask");
            super.m_6367_(p_8502_);
        }

        @Override
        protected boolean m_7245_() {
            if (ServerChunkCache.this.m_8489_()) {
                return true;
            }
            ServerChunkCache.this.f_8331_.m_9409_();
            return super.m_7245_();
        }
    }

    record ChunkAndHolder(LevelChunk f_184028_, ChunkHolder f_184029_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ChunkAndHolder.class, "chunk;holder", "f_184028_", "f_184029_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ChunkAndHolder.class, "chunk;holder", "f_184028_", "f_184029_"}, this);
        }

        @Override
        public final boolean equals(Object p_184036_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ChunkAndHolder.class, "chunk;holder", "f_184028_", "f_184029_"}, this, p_184036_);
        }
    }
}

