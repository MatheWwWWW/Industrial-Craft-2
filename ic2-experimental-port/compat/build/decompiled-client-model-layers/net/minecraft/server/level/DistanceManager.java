/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.util.Either
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.longs.Long2ByteMap
 *  it.unimi.dsi.fastutil.longs.Long2ByteMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2IntMap
 *  it.unimi.dsi.fastutil.longs.Long2IntMaps
 *  it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ChunkTaskPriorityQueueSorter;
import net.minecraft.server.level.ChunkTracker;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.Ticket;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.TickingTracker;
import net.minecraft.util.SortedArraySet;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import org.slf4j.Logger;

public abstract class DistanceManager {
    static final Logger f_140758_ = LogUtils.getLogger();
    private static final int f_143205_ = 2;
    static final int f_140759_ = 33 + ChunkStatus.m_62370_(ChunkStatus.f_62326_) - 2;
    private static final int f_143206_ = 4;
    private static final int f_183899_ = 32;
    private static final int f_183900_ = 33;
    final Long2ObjectMap<ObjectSet<ServerPlayer>> f_140760_ = new Long2ObjectOpenHashMap();
    final Long2ObjectOpenHashMap<SortedArraySet<Ticket<?>>> f_140761_ = new Long2ObjectOpenHashMap();
    private final ChunkTicketTracker f_140762_ = new ChunkTicketTracker();
    private final FixedPlayerDistanceChunkTracker f_140763_ = new FixedPlayerDistanceChunkTracker(8);
    private final TickingTracker f_183901_ = new TickingTracker();
    private final PlayerTicketTracker f_140764_ = new PlayerTicketTracker(33);
    final Set<ChunkHolder> f_140765_ = Sets.newHashSet();
    final ChunkTaskPriorityQueueSorter f_140766_;
    final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> f_140767_;
    final ProcessorHandle<ChunkTaskPriorityQueueSorter.Release> f_140768_;
    final LongSet f_140769_ = new LongOpenHashSet();
    final Executor f_140770_;
    private long f_140771_;
    private int f_183902_ = 10;

    protected DistanceManager(Executor p_140774_, Executor p_140775_) {
        ChunkTaskPriorityQueueSorter $$3;
        ProcessorHandle<Runnable> $$2 = ProcessorHandle.m_18714_("player ticket throttler", p_140775_::execute);
        this.f_140766_ = $$3 = new ChunkTaskPriorityQueueSorter((List<ProcessorHandle<?>>)ImmutableList.of($$2), p_140774_, 4);
        this.f_140767_ = $$3.m_140604_($$2, true);
        this.f_140768_ = $$3.m_140567_($$2);
        this.f_140770_ = p_140775_;
    }

    protected void m_140776_() {
        ++this.f_140771_;
        ObjectIterator $$0 = this.f_140761_.long2ObjectEntrySet().fastIterator();
        while ($$0.hasNext()) {
            Long2ObjectMap.Entry $$1 = (Long2ObjectMap.Entry)$$0.next();
            Iterator $$2 = ((SortedArraySet)$$1.getValue()).iterator();
            boolean $$3 = false;
            while ($$2.hasNext()) {
                Ticket $$4 = (Ticket)$$2.next();
                if (!$$4.m_9434_(this.f_140771_)) continue;
                $$2.remove();
                $$3 = true;
                this.f_183901_.m_184165_($$1.getLongKey(), $$4);
            }
            if ($$3) {
                this.f_140762_.m_140715_($$1.getLongKey(), DistanceManager.m_140797_((SortedArraySet)$$1.getValue()), false);
            }
            if (!((SortedArraySet)$$1.getValue()).isEmpty()) continue;
            $$0.remove();
        }
    }

    private static int m_140797_(SortedArraySet<Ticket<?>> p_140798_) {
        return !p_140798_.isEmpty() ? p_140798_.m_14262_().m_9433_() : ChunkMap.f_140127_ + 1;
    }

    protected abstract boolean m_7009_(long var1);

    @Nullable
    protected abstract ChunkHolder m_7316_(long var1);

    @Nullable
    protected abstract ChunkHolder m_7288_(long var1, int var3, @Nullable ChunkHolder var4, int var5);

    public boolean m_140805_(ChunkMap p_140806_) {
        boolean $$2;
        this.f_140763_.m_6410_();
        this.f_183901_.m_184145_();
        this.f_140764_.m_6410_();
        int $$1 = Integer.MAX_VALUE - this.f_140762_.m_140877_(Integer.MAX_VALUE);
        boolean bl = $$2 = $$1 != 0;
        if ($$2) {
            // empty if block
        }
        if (!this.f_140765_.isEmpty()) {
            this.f_140765_.forEach(p_183908_ -> p_183908_.m_143003_(p_140806_, this.f_140770_));
            this.f_140765_.clear();
            return true;
        }
        if (!this.f_140769_.isEmpty()) {
            LongIterator $$3 = this.f_140769_.iterator();
            while ($$3.hasNext()) {
                long $$4 = $$3.nextLong();
                if (!this.m_140857_($$4).stream().anyMatch(p_183910_ -> p_183910_.m_9428_() == TicketType.f_9444_)) continue;
                ChunkHolder $$5 = p_140806_.m_140174_($$4);
                if ($$5 == null) {
                    throw new IllegalStateException();
                }
                CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> $$6 = $$5.m_140073_();
                $$6.thenAccept(p_183905_ -> this.f_140770_.execute(() -> this.f_140768_.m_6937_(ChunkTaskPriorityQueueSorter.m_140628_(() -> {}, $$4, false))));
            }
            this.f_140769_.clear();
        }
        return $$2;
    }

    void m_140784_(long p_140785_, Ticket<?> p_140786_) {
        SortedArraySet<Ticket<?>> $$2 = this.m_140857_(p_140785_);
        int $$3 = DistanceManager.m_140797_($$2);
        Ticket<?> $$4 = $$2.m_14253_(p_140786_);
        $$4.m_9429_(this.f_140771_);
        if (p_140786_.m_9433_() < $$3) {
            this.f_140762_.m_140715_(p_140785_, p_140786_.m_9433_(), true);
        }
    }

    void m_140818_(long p_140819_, Ticket<?> p_140820_) {
        SortedArraySet<Ticket<?>> $$2 = this.m_140857_(p_140819_);
        if ($$2.remove(p_140820_)) {
            // empty if block
        }
        if ($$2.isEmpty()) {
            this.f_140761_.remove(p_140819_);
        }
        this.f_140762_.m_140715_(p_140819_, DistanceManager.m_140797_($$2), false);
    }

    public <T> void m_140792_(TicketType<T> p_140793_, ChunkPos p_140794_, int p_140795_, T p_140796_) {
        this.m_140784_(p_140794_.m_45588_(), new Ticket<T>(p_140793_, p_140795_, p_140796_));
    }

    public <T> void m_140823_(TicketType<T> p_140824_, ChunkPos p_140825_, int p_140826_, T p_140827_) {
        Ticket<T> $$4 = new Ticket<T>(p_140824_, p_140826_, p_140827_);
        this.m_140818_(p_140825_.m_45588_(), $$4);
    }

    public <T> void m_140840_(TicketType<T> p_140841_, ChunkPos p_140842_, int p_140843_, T p_140844_) {
        Ticket<T> $$4 = new Ticket<T>(p_140841_, 33 - p_140843_, p_140844_);
        long $$5 = p_140842_.m_45588_();
        this.m_140784_($$5, $$4);
        this.f_183901_.m_184151_($$5, $$4);
    }

    public <T> void m_140849_(TicketType<T> p_140850_, ChunkPos p_140851_, int p_140852_, T p_140853_) {
        Ticket<T> $$4 = new Ticket<T>(p_140850_, 33 - p_140852_, p_140853_);
        long $$5 = p_140851_.m_45588_();
        this.m_140818_($$5, $$4);
        this.f_183901_.m_184165_($$5, $$4);
    }

    private SortedArraySet<Ticket<?>> m_140857_(long p_140858_) {
        return (SortedArraySet)this.f_140761_.computeIfAbsent(p_140858_, p_183923_ -> SortedArraySet.m_14246_(4));
    }

    protected void m_140799_(ChunkPos p_140800_, boolean p_140801_) {
        Ticket<ChunkPos> $$2 = new Ticket<ChunkPos>(TicketType.f_9445_, 31, p_140800_);
        long $$3 = p_140800_.m_45588_();
        if (p_140801_) {
            this.m_140784_($$3, $$2);
            this.f_183901_.m_184151_($$3, $$2);
        } else {
            this.m_140818_($$3, $$2);
            this.f_183901_.m_184165_($$3, $$2);
        }
    }

    public void m_140802_(SectionPos p_140803_, ServerPlayer p_140804_) {
        ChunkPos $$2 = p_140803_.m_123251_();
        long $$3 = $$2.m_45588_();
        ((ObjectSet)this.f_140760_.computeIfAbsent($$3, p_183921_ -> new ObjectOpenHashSet())).add((Object)p_140804_);
        this.f_140763_.m_140715_($$3, 0, true);
        this.f_140764_.m_140715_($$3, 0, true);
        this.f_183901_.m_184154_(TicketType.f_9444_, $$2, this.m_183918_(), $$2);
    }

    public void m_140828_(SectionPos p_140829_, ServerPlayer p_140830_) {
        ChunkPos $$2 = p_140829_.m_123251_();
        long $$3 = $$2.m_45588_();
        ObjectSet $$4 = (ObjectSet)this.f_140760_.get($$3);
        $$4.remove((Object)p_140830_);
        if ($$4.isEmpty()) {
            this.f_140760_.remove($$3);
            this.f_140763_.m_140715_($$3, Integer.MAX_VALUE, false);
            this.f_140764_.m_140715_($$3, Integer.MAX_VALUE, false);
            this.f_183901_.m_184168_(TicketType.f_9444_, $$2, this.m_183918_(), $$2);
        }
    }

    private int m_183918_() {
        return Math.max(0, 31 - this.f_183902_);
    }

    public boolean m_183913_(long p_183914_) {
        return this.f_183901_.m_6172_(p_183914_) < 32;
    }

    public boolean m_183916_(long p_183917_) {
        return this.f_183901_.m_6172_(p_183917_) < 33;
    }

    protected String m_140838_(long p_140839_) {
        SortedArraySet $$1 = (SortedArraySet)this.f_140761_.get(p_140839_);
        if ($$1 == null || $$1.isEmpty()) {
            return "no_ticket";
        }
        return ((Ticket)$$1.m_14262_()).toString();
    }

    protected void m_140777_(int p_140778_) {
        this.f_140764_.m_140912_(p_140778_);
    }

    public void m_183911_(int p_183912_) {
        if (p_183912_ != this.f_183902_) {
            this.f_183902_ = p_183912_;
            this.f_183901_.m_184146_(this.m_183918_());
        }
    }

    public int m_140816_() {
        this.f_140763_.m_6410_();
        return this.f_140763_.f_140886_.size();
    }

    public boolean m_140847_(long p_140848_) {
        this.f_140763_.m_6410_();
        return this.f_140763_.f_140886_.containsKey(p_140848_);
    }

    public String m_140837_() {
        return this.f_140766_.m_140558_();
    }

    private void m_143207_(String p_143208_) {
        try (FileOutputStream $$1 = new FileOutputStream(new File(p_143208_));){
            for (Long2ObjectMap.Entry $$2 : this.f_140761_.long2ObjectEntrySet()) {
                ChunkPos $$3 = new ChunkPos($$2.getLongKey());
                for (Ticket $$4 : (SortedArraySet)$$2.getValue()) {
                    $$1.write(($$3.f_45578_ + "\t" + $$3.f_45579_ + "\t" + $$4.m_9428_() + "\t" + $$4.m_9433_() + "\t\n").getBytes(StandardCharsets.UTF_8));
                }
            }
        }
        catch (IOException $$5) {
            f_140758_.error("Failed to dump tickets to {}", (Object)p_143208_, (Object)$$5);
        }
    }

    @VisibleForTesting
    TickingTracker m_183915_() {
        return this.f_183901_;
    }

    public void m_201910_() {
        ImmutableSet $$0 = ImmutableSet.of(TicketType.f_9449_, TicketType.f_9448_, TicketType.f_9446_);
        ObjectIterator $$1 = this.f_140761_.long2ObjectEntrySet().fastIterator();
        while ($$1.hasNext()) {
            Long2ObjectMap.Entry $$2 = (Long2ObjectMap.Entry)$$1.next();
            Iterator $$3 = ((SortedArraySet)$$2.getValue()).iterator();
            boolean $$4 = false;
            while ($$3.hasNext()) {
                Ticket $$5 = (Ticket)$$3.next();
                if ($$0.contains($$5.m_9428_())) continue;
                $$3.remove();
                $$4 = true;
                this.f_183901_.m_184165_($$2.getLongKey(), $$5);
            }
            if ($$4) {
                this.f_140762_.m_140715_($$2.getLongKey(), DistanceManager.m_140797_((SortedArraySet)$$2.getValue()), false);
            }
            if (!((SortedArraySet)$$2.getValue()).isEmpty()) continue;
            $$1.remove();
        }
    }

    public boolean m_201911_() {
        return !this.f_140761_.isEmpty();
    }

    class ChunkTicketTracker
    extends ChunkTracker {
        public ChunkTicketTracker() {
            super(ChunkMap.f_140127_ + 2, 16, 256);
        }

        @Override
        protected int m_7031_(long p_140883_) {
            SortedArraySet $$1 = (SortedArraySet)DistanceManager.this.f_140761_.get(p_140883_);
            if ($$1 == null) {
                return Integer.MAX_VALUE;
            }
            if ($$1.isEmpty()) {
                return Integer.MAX_VALUE;
            }
            return ((Ticket)$$1.m_14262_()).m_9433_();
        }

        @Override
        protected int m_6172_(long p_140885_) {
            ChunkHolder $$1;
            if (!DistanceManager.this.m_7009_(p_140885_) && ($$1 = DistanceManager.this.m_7316_(p_140885_)) != null) {
                return $$1.m_140093_();
            }
            return ChunkMap.f_140127_ + 1;
        }

        @Override
        protected void m_7351_(long p_140880_, int p_140881_) {
            int $$3;
            ChunkHolder $$2 = DistanceManager.this.m_7316_(p_140880_);
            int n = $$3 = $$2 == null ? ChunkMap.f_140127_ + 1 : $$2.m_140093_();
            if ($$3 == p_140881_) {
                return;
            }
            if (($$2 = DistanceManager.this.m_7288_(p_140880_, p_140881_, $$2, $$3)) != null) {
                DistanceManager.this.f_140765_.add($$2);
            }
        }

        public int m_140877_(int p_140878_) {
            return this.m_75588_(p_140878_);
        }
    }

    class FixedPlayerDistanceChunkTracker
    extends ChunkTracker {
        protected final Long2ByteMap f_140886_;
        protected final int f_140887_;

        protected FixedPlayerDistanceChunkTracker(int p_140891_) {
            super(p_140891_ + 2, 16, 256);
            this.f_140886_ = new Long2ByteOpenHashMap();
            this.f_140887_ = p_140891_;
            this.f_140886_.defaultReturnValue((byte)(p_140891_ + 2));
        }

        @Override
        protected int m_6172_(long p_140901_) {
            return this.f_140886_.get(p_140901_);
        }

        @Override
        protected void m_7351_(long p_140893_, int p_140894_) {
            byte $$3;
            if (p_140894_ > this.f_140887_) {
                byte $$2 = this.f_140886_.remove(p_140893_);
            } else {
                $$3 = this.f_140886_.put(p_140893_, (byte)p_140894_);
            }
            this.m_8002_(p_140893_, $$3, p_140894_);
        }

        protected void m_8002_(long p_140895_, int p_140896_, int p_140897_) {
        }

        @Override
        protected int m_7031_(long p_140899_) {
            return this.m_140902_(p_140899_) ? 0 : Integer.MAX_VALUE;
        }

        private boolean m_140902_(long p_140903_) {
            ObjectSet $$1 = (ObjectSet)DistanceManager.this.f_140760_.get(p_140903_);
            return $$1 != null && !$$1.isEmpty();
        }

        public void m_6410_() {
            this.m_75588_(Integer.MAX_VALUE);
        }

        private void m_143212_(String p_143213_) {
            try (FileOutputStream $$1 = new FileOutputStream(new File(p_143213_));){
                for (Long2ByteMap.Entry $$2 : this.f_140886_.long2ByteEntrySet()) {
                    ChunkPos $$3 = new ChunkPos($$2.getLongKey());
                    String $$4 = Byte.toString($$2.getByteValue());
                    $$1.write(($$3.f_45578_ + "\t" + $$3.f_45579_ + "\t" + $$4 + "\n").getBytes(StandardCharsets.UTF_8));
                }
            }
            catch (IOException $$5) {
                f_140758_.error("Failed to dump chunks to {}", (Object)p_143213_, (Object)$$5);
            }
        }
    }

    class PlayerTicketTracker
    extends FixedPlayerDistanceChunkTracker {
        private int f_140905_;
        private final Long2IntMap f_140906_;
        private final LongSet f_140907_;

        protected PlayerTicketTracker(int p_140910_) {
            super(p_140910_);
            this.f_140906_ = Long2IntMaps.synchronize((Long2IntMap)new Long2IntOpenHashMap());
            this.f_140907_ = new LongOpenHashSet();
            this.f_140905_ = 0;
            this.f_140906_.defaultReturnValue(p_140910_ + 2);
        }

        @Override
        protected void m_8002_(long p_140915_, int p_140916_, int p_140917_) {
            this.f_140907_.add(p_140915_);
        }

        public void m_140912_(int p_140913_) {
            for (Long2ByteMap.Entry $$1 : this.f_140886_.long2ByteEntrySet()) {
                byte $$2 = $$1.getByteValue();
                long $$3 = $$1.getLongKey();
                this.m_140918_($$3, $$2, this.m_140932_($$2), $$2 <= p_140913_ - 2);
            }
            this.f_140905_ = p_140913_;
        }

        private void m_140918_(long p_140919_, int p_140920_, boolean p_140921_, boolean p_140922_) {
            if (p_140921_ != p_140922_) {
                Ticket<ChunkPos> $$4 = new Ticket<ChunkPos>(TicketType.f_9444_, f_140759_, new ChunkPos(p_140919_));
                if (p_140922_) {
                    DistanceManager.this.f_140767_.m_6937_(ChunkTaskPriorityQueueSorter.m_140624_(() -> DistanceManager.this.f_140770_.execute(() -> {
                        if (this.m_140932_(this.m_6172_(p_140919_))) {
                            DistanceManager.this.m_140784_(p_140919_, $$4);
                            DistanceManager.this.f_140769_.add(p_140919_);
                        } else {
                            DistanceManager.this.f_140768_.m_6937_(ChunkTaskPriorityQueueSorter.m_140628_(() -> {}, p_140919_, false));
                        }
                    }), p_140919_, () -> p_140920_));
                } else {
                    DistanceManager.this.f_140768_.m_6937_(ChunkTaskPriorityQueueSorter.m_140628_(() -> DistanceManager.this.f_140770_.execute(() -> DistanceManager.this.m_140818_(p_140919_, $$4)), p_140919_, true));
                }
            }
        }

        @Override
        public void m_6410_() {
            super.m_6410_();
            if (!this.f_140907_.isEmpty()) {
                LongIterator $$0 = this.f_140907_.iterator();
                while ($$0.hasNext()) {
                    int $$3;
                    long $$1 = $$0.nextLong();
                    int $$2 = this.f_140906_.get($$1);
                    if ($$2 == ($$3 = this.m_6172_($$1))) continue;
                    DistanceManager.this.f_140766_.m_6250_(new ChunkPos($$1), () -> this.f_140906_.get($$1), $$3, p_140928_ -> {
                        if (p_140928_ >= this.f_140906_.defaultReturnValue()) {
                            this.f_140906_.remove($$1);
                        } else {
                            this.f_140906_.put($$1, p_140928_);
                        }
                    });
                    this.m_140918_($$1, $$3, this.m_140932_($$2), this.m_140932_($$3));
                }
                this.f_140907_.clear();
            }
        }

        private boolean m_140932_(int p_140933_) {
            return p_140933_ <= this.f_140905_ - 2;
        }
    }
}

