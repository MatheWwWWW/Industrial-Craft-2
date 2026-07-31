/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Queues
 *  com.google.common.collect.Sets
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.longs.Long2ObjectFunction
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMaps
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.util.CsvOutput;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.entity.ChunkEntities;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntityInLevelCallback;
import net.minecraft.world.level.entity.EntityLookup;
import net.minecraft.world.level.entity.EntityPersistentStorage;
import net.minecraft.world.level.entity.EntitySection;
import net.minecraft.world.level.entity.EntitySectionStorage;
import net.minecraft.world.level.entity.LevelCallback;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.entity.LevelEntityGetterAdapter;
import net.minecraft.world.level.entity.Visibility;
import org.slf4j.Logger;

public class PersistentEntitySectionManager<T extends EntityAccess>
implements AutoCloseable {
    static final Logger f_157490_ = LogUtils.getLogger();
    final Set<UUID> f_157491_ = Sets.newHashSet();
    final LevelCallback<T> f_157492_;
    private final EntityPersistentStorage<T> f_157493_;
    private final EntityLookup<T> f_157494_;
    final EntitySectionStorage<T> f_157495_;
    private final LevelEntityGetter<T> f_157496_;
    private final Long2ObjectMap<Visibility> f_157497_ = new Long2ObjectOpenHashMap();
    private final Long2ObjectMap<ChunkLoadStatus> f_157498_ = new Long2ObjectOpenHashMap();
    private final LongSet f_157499_ = new LongOpenHashSet();
    private final Queue<ChunkEntities<T>> f_157500_ = Queues.newConcurrentLinkedQueue();

    public PersistentEntitySectionManager(Class<T> p_157503_, LevelCallback<T> p_157504_, EntityPersistentStorage<T> p_157505_) {
        this.f_157494_ = new EntityLookup();
        this.f_157495_ = new EntitySectionStorage<T>(p_157503_, (Long2ObjectFunction<Visibility>)this.f_157497_);
        this.f_157497_.defaultReturnValue((Object)Visibility.HIDDEN);
        this.f_157498_.defaultReturnValue((Object)ChunkLoadStatus.FRESH);
        this.f_157492_ = p_157504_;
        this.f_157493_ = p_157505_;
        this.f_157496_ = new LevelEntityGetterAdapter<T>(this.f_157494_, this.f_157495_);
    }

    void m_157509_(long p_157510_, EntitySection<T> p_157511_) {
        if (p_157511_.m_156833_()) {
            this.f_157495_.m_156897_(p_157510_);
        }
    }

    private boolean m_157557_(T p_157558_) {
        if (!this.f_157491_.add(p_157558_.m_20148_())) {
            f_157490_.warn("UUID of added entity already exists: {}", p_157558_);
            return false;
        }
        return true;
    }

    public boolean m_157533_(T p_157534_) {
        return this.m_157538_(p_157534_, false);
    }

    private boolean m_157538_(T p_157539_, boolean p_157540_) {
        Visibility $$4;
        if (!this.m_157557_(p_157539_)) {
            return false;
        }
        long $$2 = SectionPos.m_175568_(p_157539_.m_20183_());
        EntitySection<T> $$3 = this.f_157495_.m_156893_($$2);
        $$3.m_188346_(p_157539_);
        p_157539_.m_141960_(new Callback(this, p_157539_, $$2, $$3));
        if (!p_157540_) {
            this.f_157492_.m_141989_(p_157539_);
        }
        if (($$4 = PersistentEntitySectionManager.m_157535_(p_157539_, $$3.m_156848_())).m_157694_()) {
            this.m_157575_(p_157539_);
        }
        if ($$4.m_157691_()) {
            this.m_157564_(p_157539_);
        }
        return true;
    }

    static <T extends EntityAccess> Visibility m_157535_(T p_157536_, Visibility p_157537_) {
        return p_157536_.m_142389_() ? Visibility.TICKING : p_157537_;
    }

    public void m_157552_(Stream<T> p_157553_) {
        p_157553_.forEach(p_157607_ -> this.m_157538_(p_157607_, true));
    }

    public void m_157559_(Stream<T> p_157560_) {
        p_157560_.forEach(p_157605_ -> this.m_157538_(p_157605_, false));
    }

    void m_157564_(T p_157565_) {
        this.f_157492_.m_141987_(p_157565_);
    }

    void m_157570_(T p_157571_) {
        this.f_157492_.m_141983_(p_157571_);
    }

    void m_157575_(T p_157576_) {
        this.f_157494_.m_156814_(p_157576_);
        this.f_157492_.m_141985_(p_157576_);
    }

    void m_157580_(T p_157581_) {
        this.f_157492_.m_141981_(p_157581_);
        this.f_157494_.m_156822_(p_157581_);
    }

    public void m_157524_(ChunkPos p_157525_, ChunkHolder.FullChunkStatus p_157526_) {
        Visibility $$2 = Visibility.m_157692_(p_157526_);
        this.m_157527_(p_157525_, $$2);
    }

    public void m_157527_(ChunkPos p_157528_, Visibility p_157529_) {
        long $$2 = p_157528_.m_45588_();
        if (p_157529_ == Visibility.HIDDEN) {
            this.f_157497_.remove($$2);
            this.f_157499_.add($$2);
        } else {
            this.f_157497_.put($$2, (Object)p_157529_);
            this.f_157499_.remove($$2);
            this.m_157555_($$2);
        }
        this.f_157495_.m_156888_($$2).forEach(p_157545_ -> {
            Visibility $$2 = p_157545_.m_156838_(p_157529_);
            boolean $$3 = $$2.m_157694_();
            boolean $$4 = p_157529_.m_157694_();
            boolean $$5 = $$2.m_157691_();
            boolean $$6 = p_157529_.m_157691_();
            if ($$5 && !$$6) {
                p_157545_.m_156845_().filter(p_157603_ -> !p_157603_.m_142389_()).forEach(this::m_157570_);
            }
            if ($$3 && !$$4) {
                p_157545_.m_156845_().filter(p_157601_ -> !p_157601_.m_142389_()).forEach(this::m_157580_);
            } else if (!$$3 && $$4) {
                p_157545_.m_156845_().filter(p_157599_ -> !p_157599_.m_142389_()).forEach(this::m_157575_);
            }
            if (!$$5 && $$6) {
                p_157545_.m_156845_().filter(p_157597_ -> !p_157597_.m_142389_()).forEach(this::m_157564_);
            }
        });
    }

    private void m_157555_(long p_157556_) {
        ChunkLoadStatus $$1 = (ChunkLoadStatus)((Object)this.f_157498_.get(p_157556_));
        if ($$1 == ChunkLoadStatus.FRESH) {
            this.m_157562_(p_157556_);
        }
    }

    private boolean m_157512_(long p_157513_, Consumer<T> p_157514_) {
        ChunkLoadStatus $$2 = (ChunkLoadStatus)((Object)this.f_157498_.get(p_157513_));
        if ($$2 == ChunkLoadStatus.PENDING) {
            return false;
        }
        List<T> $$3 = this.f_157495_.m_156888_(p_157513_).flatMap(p_157542_ -> p_157542_.m_156845_().filter(EntityAccess::m_142391_)).collect(Collectors.toList());
        if ($$3.isEmpty()) {
            if ($$2 == ChunkLoadStatus.LOADED) {
                this.f_157493_.m_141971_(new ChunkEntities(new ChunkPos(p_157513_), ImmutableList.of()));
            }
            return true;
        }
        if ($$2 == ChunkLoadStatus.FRESH) {
            this.m_157562_(p_157513_);
            return false;
        }
        this.f_157493_.m_141971_(new ChunkEntities(new ChunkPos(p_157513_), $$3));
        $$3.forEach(p_157514_);
        return true;
    }

    private void m_157562_(long p_157563_) {
        this.f_157498_.put(p_157563_, (Object)ChunkLoadStatus.PENDING);
        ChunkPos $$1 = new ChunkPos(p_157563_);
        ((CompletableFuture)this.f_157493_.m_141930_($$1).thenAccept(this.f_157500_::add)).exceptionally(p_157532_ -> {
            f_157490_.error("Failed to read chunk {}", (Object)$$1, p_157532_);
            return null;
        });
    }

    private boolean m_157568_(long p_157569_) {
        boolean $$1 = this.m_157512_(p_157569_, p_157595_ -> p_157595_.m_142429_().forEach(this::m_157585_));
        if (!$$1) {
            return false;
        }
        this.f_157498_.remove(p_157569_);
        return true;
    }

    private void m_157585_(EntityAccess p_157586_) {
        p_157586_.m_142467_(Entity.RemovalReason.UNLOADED_TO_CHUNK);
        p_157586_.m_141960_(EntityInLevelCallback.f_156799_);
    }

    private void m_157577_() {
        this.f_157499_.removeIf(p_157584_ -> {
            if (this.f_157497_.get(p_157584_) != Visibility.HIDDEN) {
                return true;
            }
            return this.m_157568_(p_157584_);
        });
    }

    private void m_157582_() {
        ChunkEntities<T> $$0;
        while (($$0 = this.f_157500_.poll()) != null) {
            $$0.m_156792_().forEach(p_157593_ -> this.m_157538_(p_157593_, true));
            this.f_157498_.put($$0.m_156791_().m_45588_(), (Object)ChunkLoadStatus.LOADED);
        }
    }

    public void m_157506_() {
        this.m_157582_();
        this.m_157577_();
    }

    private LongSet m_157587_() {
        LongSet $$0 = this.f_157495_.m_156857_();
        for (Long2ObjectMap.Entry $$1 : Long2ObjectMaps.fastIterable(this.f_157498_)) {
            if ($$1.getValue() != ChunkLoadStatus.LOADED) continue;
            $$0.add($$1.getLongKey());
        }
        return $$0;
    }

    public void m_157554_() {
        this.m_157587_().forEach(p_157579_ -> {
            boolean $$1;
            boolean bl = $$1 = this.f_157497_.get(p_157579_) == Visibility.HIDDEN;
            if ($$1) {
                this.m_157568_(p_157579_);
            } else {
                this.m_157512_(p_157579_, p_157591_ -> {});
            }
        });
    }

    public void m_157561_() {
        LongSet $$0 = this.m_157587_();
        while (!$$0.isEmpty()) {
            this.f_157493_.m_182219_(false);
            this.m_157582_();
            $$0.removeIf(p_157574_ -> {
                boolean $$1 = this.f_157497_.get(p_157574_) == Visibility.HIDDEN;
                return $$1 ? this.m_157568_(p_157574_) : this.m_157512_(p_157574_, p_157589_ -> {});
            });
        }
        this.f_157493_.m_182219_(true);
    }

    @Override
    public void close() throws IOException {
        this.m_157561_();
        this.f_157493_.close();
    }

    public boolean m_157550_(UUID p_157551_) {
        return this.f_157491_.contains(p_157551_);
    }

    public LevelEntityGetter<T> m_157567_() {
        return this.f_157496_;
    }

    public boolean m_202167_(BlockPos p_202168_) {
        return ((Visibility)((Object)this.f_157497_.get(ChunkPos.m_151388_(p_202168_)))).m_157691_();
    }

    public boolean m_202165_(ChunkPos p_202166_) {
        return ((Visibility)((Object)this.f_157497_.get(p_202166_.m_45588_()))).m_157691_();
    }

    public boolean m_157507_(long p_157508_) {
        return this.f_157498_.get(p_157508_) == ChunkLoadStatus.LOADED;
    }

    public void m_157548_(Writer p_157549_) throws IOException {
        CsvOutput $$1 = CsvOutput.m_13619_().m_13630_("x").m_13630_("y").m_13630_("z").m_13630_("visibility").m_13630_("load_status").m_13630_("entity_count").m_13628_(p_157549_);
        this.f_157495_.m_156857_().forEach(p_157517_ -> {
            ChunkLoadStatus $$2 = (ChunkLoadStatus)((Object)((Object)this.f_157498_.get(p_157517_)));
            this.f_157495_.m_156861_(p_157517_).forEach(p_157521_ -> {
                EntitySection<T> $$3 = this.f_157495_.m_156895_(p_157521_);
                if ($$3 != null) {
                    try {
                        $$1.m_13624_(new Object[]{SectionPos.m_123213_(p_157521_), SectionPos.m_123225_(p_157521_), SectionPos.m_123230_(p_157521_), $$3.m_156848_(), $$2, $$3.m_156849_()});
                    }
                    catch (IOException $$4) {
                        throw new UncheckedIOException($$4);
                    }
                }
            });
        });
    }

    @VisibleForDebug
    public String m_157572_() {
        return this.f_157491_.size() + "," + this.f_157494_.m_156821_() + "," + this.f_157495_.m_156887_() + "," + this.f_157498_.size() + "," + this.f_157497_.size() + "," + this.f_157500_.size() + "," + this.f_157499_.size();
    }

    static final class ChunkLoadStatus
    extends Enum<ChunkLoadStatus> {
        public static final /* enum */ ChunkLoadStatus FRESH = new ChunkLoadStatus();
        public static final /* enum */ ChunkLoadStatus PENDING = new ChunkLoadStatus();
        public static final /* enum */ ChunkLoadStatus LOADED = new ChunkLoadStatus();
        private static final /* synthetic */ ChunkLoadStatus[] $VALUES;

        public static ChunkLoadStatus[] values() {
            return (ChunkLoadStatus[])$VALUES.clone();
        }

        public static ChunkLoadStatus valueOf(String p_157633_) {
            return Enum.valueOf(ChunkLoadStatus.class, p_157633_);
        }

        private static /* synthetic */ ChunkLoadStatus[] m_157631_() {
            return new ChunkLoadStatus[]{FRESH, PENDING, LOADED};
        }

        static {
            $VALUES = ChunkLoadStatus.m_157631_();
        }
    }

    class Callback
    implements EntityInLevelCallback {
        private final T f_157609_;
        private long f_157610_;
        private EntitySection<T> f_157611_;
        final /* synthetic */ PersistentEntitySectionManager f_157608_;

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        Callback(T t, long p_157616_, EntitySection<T> entitySection) {
            void var3_3;
            void p_157614_;
            this.f_157608_ = (PersistentEntitySectionManager)l;
            this.f_157609_ = p_157614_;
            this.f_157610_ = var3_3;
            this.f_157611_ = (EntitySection)p_157616_;
        }

        @Override
        public void m_142044_() {
            BlockPos $$0 = this.f_157609_.m_20183_();
            long $$1 = SectionPos.m_175568_($$0);
            if ($$1 != this.f_157610_) {
                Visibility $$2 = this.f_157611_.m_156848_();
                if (!this.f_157611_.m_188355_(this.f_157609_)) {
                    f_157490_.warn("Entity {} wasn't found in section {} (moving to {})", new Object[]{this.f_157609_, SectionPos.m_123184_(this.f_157610_), $$1});
                }
                this.f_157608_.m_157509_(this.f_157610_, this.f_157611_);
                EntitySection $$3 = this.f_157608_.f_157495_.m_156893_($$1);
                $$3.m_188346_(this.f_157609_);
                this.f_157611_ = $$3;
                this.f_157610_ = $$1;
                this.m_157620_($$2, $$3.m_156848_());
            }
        }

        private void m_157620_(Visibility p_157621_, Visibility p_157622_) {
            Visibility $$3;
            Visibility $$2 = PersistentEntitySectionManager.m_157535_(this.f_157609_, p_157621_);
            if ($$2 == ($$3 = PersistentEntitySectionManager.m_157535_(this.f_157609_, p_157622_))) {
                if ($$3.m_157694_()) {
                    this.f_157608_.f_157492_.m_214006_(this.f_157609_);
                }
                return;
            }
            boolean $$4 = $$2.m_157694_();
            boolean $$5 = $$3.m_157694_();
            if ($$4 && !$$5) {
                this.f_157608_.m_157580_(this.f_157609_);
            } else if (!$$4 && $$5) {
                this.f_157608_.m_157575_(this.f_157609_);
            }
            boolean $$6 = $$2.m_157691_();
            boolean $$7 = $$3.m_157691_();
            if ($$6 && !$$7) {
                this.f_157608_.m_157570_(this.f_157609_);
            } else if (!$$6 && $$7) {
                this.f_157608_.m_157564_(this.f_157609_);
            }
            if ($$5) {
                this.f_157608_.f_157492_.m_214006_(this.f_157609_);
            }
        }

        @Override
        public void m_142472_(Entity.RemovalReason p_157619_) {
            Visibility $$1;
            if (!this.f_157611_.m_188355_(this.f_157609_)) {
                f_157490_.warn("Entity {} wasn't found in section {} (destroying due to {})", new Object[]{this.f_157609_, SectionPos.m_123184_(this.f_157610_), p_157619_});
            }
            if (($$1 = PersistentEntitySectionManager.m_157535_(this.f_157609_, this.f_157611_.m_156848_())).m_157691_()) {
                this.f_157608_.m_157570_(this.f_157609_);
            }
            if ($$1.m_157694_()) {
                this.f_157608_.m_157580_(this.f_157609_);
            }
            if (p_157619_.m_146965_()) {
                this.f_157608_.f_157492_.m_141986_(this.f_157609_);
            }
            this.f_157608_.f_157491_.remove(this.f_157609_.m_20148_());
            this.f_157609_.m_141960_(f_156799_);
            this.f_157608_.m_157509_(this.f_157610_, this.f_157611_);
        }
    }
}

