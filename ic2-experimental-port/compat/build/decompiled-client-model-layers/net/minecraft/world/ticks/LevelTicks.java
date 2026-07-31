/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2LongMap
 *  it.unimi.dsi.fastutil.longs.Long2LongMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2LongMaps
 *  it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet
 */
package net.minecraft.world.ticks;

import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongMaps;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.ticks.LevelChunkTicks;
import net.minecraft.world.ticks.LevelTickAccess;
import net.minecraft.world.ticks.ScheduledTick;
import net.minecraft.world.ticks.TickAccess;

public class LevelTicks<T>
implements LevelTickAccess<T> {
    private static final Comparator<LevelChunkTicks<?>> f_193199_ = (p_193246_, p_193247_) -> ScheduledTick.f_193374_.compare(p_193246_.m_193189_(), p_193247_.m_193189_());
    private final LongPredicate f_193200_;
    private final Supplier<ProfilerFiller> f_193201_;
    private final Long2ObjectMap<LevelChunkTicks<T>> f_193202_ = new Long2ObjectOpenHashMap();
    private final Long2LongMap f_193203_ = (Long2LongMap)Util.m_137469_(new Long2LongOpenHashMap(), p_193262_ -> p_193262_.defaultReturnValue(Long.MAX_VALUE));
    private final Queue<LevelChunkTicks<T>> f_193204_ = new PriorityQueue(f_193199_);
    private final Queue<ScheduledTick<T>> f_193205_ = new ArrayDeque<ScheduledTick<T>>();
    private final List<ScheduledTick<T>> f_193206_ = new ArrayList<ScheduledTick<T>>();
    private final Set<ScheduledTick<?>> f_193207_ = new ObjectOpenCustomHashSet(ScheduledTick.f_193375_);
    private final BiConsumer<LevelChunkTicks<T>, ScheduledTick<T>> f_193208_ = (p_193249_, p_193250_) -> {
        if (p_193250_.equals(p_193249_.m_193189_())) {
            this.m_193279_((ScheduledTick<T>)p_193250_);
        }
    };

    public LevelTicks(LongPredicate p_193211_, Supplier<ProfilerFiller> p_193212_) {
        this.f_193200_ = p_193211_;
        this.f_193201_ = p_193212_;
    }

    public void m_193231_(ChunkPos p_193232_, LevelChunkTicks<T> p_193233_) {
        long $$2 = p_193232_.m_45588_();
        this.f_193202_.put($$2, p_193233_);
        ScheduledTick<T> $$3 = p_193233_.m_193189_();
        if ($$3 != null) {
            this.f_193203_.put($$2, $$3.f_193378_());
        }
        p_193233_.m_193181_(this.f_193208_);
    }

    public void m_193229_(ChunkPos p_193230_) {
        long $$1 = p_193230_.m_45588_();
        LevelChunkTicks $$2 = (LevelChunkTicks)this.f_193202_.remove($$1);
        this.f_193203_.remove($$1);
        if ($$2 != null) {
            $$2.m_193181_(null);
        }
    }

    @Override
    public void m_183393_(ScheduledTick<T> p_193252_) {
        long $$1 = ChunkPos.m_151388_(p_193252_.f_193377_());
        LevelChunkTicks $$2 = (LevelChunkTicks)this.f_193202_.get($$1);
        if ($$2 == null) {
            Util.m_137570_(new IllegalStateException("Trying to schedule tick in not loaded position " + p_193252_.f_193377_()));
            return;
        }
        $$2.m_183393_(p_193252_);
    }

    public void m_193225_(long p_193226_, int p_193227_, BiConsumer<BlockPos, T> p_193228_) {
        ProfilerFiller $$3 = this.f_193201_.get();
        $$3.m_6180_("collect");
        this.m_193221_(p_193226_, p_193227_, $$3);
        $$3.m_6182_("run");
        $$3.m_183275_("ticksToRun", this.f_193205_.size());
        this.m_193272_(p_193228_);
        $$3.m_6182_("cleanup");
        this.m_193284_();
        $$3.m_7238_();
    }

    private void m_193221_(long p_193222_, int p_193223_, ProfilerFiller p_193224_) {
        this.m_193216_(p_193222_);
        p_193224_.m_183275_("containersToTick", this.f_193204_.size());
        this.m_193218_(p_193222_, p_193223_);
        this.m_193278_();
    }

    private void m_193216_(long p_193217_) {
        ObjectIterator $$1 = Long2LongMaps.fastIterator((Long2LongMap)this.f_193203_);
        while ($$1.hasNext()) {
            Long2LongMap.Entry $$2 = (Long2LongMap.Entry)$$1.next();
            long $$3 = $$2.getLongKey();
            long $$4 = $$2.getLongValue();
            if ($$4 > p_193217_) continue;
            LevelChunkTicks $$5 = (LevelChunkTicks)this.f_193202_.get($$3);
            if ($$5 == null) {
                $$1.remove();
                continue;
            }
            ScheduledTick $$6 = $$5.m_193189_();
            if ($$6 == null) {
                $$1.remove();
                continue;
            }
            if ($$6.f_193378_() > p_193217_) {
                $$2.setValue($$6.f_193378_());
                continue;
            }
            if (!this.f_193200_.test($$3)) continue;
            $$1.remove();
            this.f_193204_.add($$5);
        }
    }

    private void m_193218_(long p_193219_, int p_193220_) {
        LevelChunkTicks<T> $$2;
        while (this.m_193214_(p_193220_) && ($$2 = this.f_193204_.poll()) != null) {
            ScheduledTick<T> $$3 = $$2.m_193195_();
            this.m_193285_($$3);
            this.m_193267_(this.f_193204_, $$2, p_193219_, p_193220_);
            ScheduledTick<T> $$4 = $$2.m_193189_();
            if ($$4 == null) continue;
            if ($$4.f_193378_() <= p_193219_ && this.m_193214_(p_193220_)) {
                this.f_193204_.add($$2);
                continue;
            }
            this.m_193279_($$4);
        }
    }

    private void m_193278_() {
        for (LevelChunkTicks levelChunkTicks : this.f_193204_) {
            this.m_193279_(levelChunkTicks.m_193189_());
        }
    }

    private void m_193279_(ScheduledTick<T> p_193280_) {
        this.f_193203_.put(ChunkPos.m_151388_(p_193280_.f_193377_()), p_193280_.f_193378_());
    }

    private void m_193267_(Queue<LevelChunkTicks<T>> p_193268_, LevelChunkTicks<T> p_193269_, long p_193270_, int p_193271_) {
        ScheduledTick<T> $$6;
        ScheduledTick<T> $$5;
        if (!this.m_193214_(p_193271_)) {
            return;
        }
        LevelChunkTicks<T> $$4 = p_193268_.peek();
        ScheduledTick<T> scheduledTick = $$5 = $$4 != null ? $$4.m_193189_() : null;
        while (this.m_193214_(p_193271_) && ($$6 = p_193269_.m_193189_()) != null && $$6.f_193378_() <= p_193270_ && ($$5 == null || ScheduledTick.f_193374_.compare($$6, $$5) <= 0)) {
            p_193269_.m_193195_();
            this.m_193285_($$6);
        }
    }

    private void m_193285_(ScheduledTick<T> p_193286_) {
        this.f_193205_.add(p_193286_);
    }

    private boolean m_193214_(int p_193215_) {
        return this.f_193205_.size() < p_193215_;
    }

    private void m_193272_(BiConsumer<BlockPos, T> p_193273_) {
        while (!this.f_193205_.isEmpty()) {
            ScheduledTick<T> $$1 = this.f_193205_.poll();
            if (!this.f_193207_.isEmpty()) {
                this.f_193207_.remove($$1);
            }
            this.f_193206_.add($$1);
            p_193273_.accept($$1.f_193377_(), (BlockPos)$$1.f_193376_());
        }
    }

    private void m_193284_() {
        this.f_193205_.clear();
        this.f_193204_.clear();
        this.f_193206_.clear();
        this.f_193207_.clear();
    }

    @Override
    public boolean m_183582_(BlockPos p_193254_, T p_193255_) {
        LevelChunkTicks $$2 = (LevelChunkTicks)this.f_193202_.get(ChunkPos.m_151388_(p_193254_));
        return $$2 != null && $$2.m_183582_(p_193254_, p_193255_);
    }

    @Override
    public boolean m_183588_(BlockPos p_193282_, T p_193283_) {
        this.m_193287_();
        return this.f_193207_.contains(ScheduledTick.m_193397_(p_193283_, p_193282_));
    }

    private void m_193287_() {
        if (this.f_193207_.isEmpty() && !this.f_193205_.isEmpty()) {
            this.f_193207_.addAll(this.f_193205_);
        }
    }

    private void m_193236_(BoundingBox p_193237_, PosAndContainerConsumer<T> p_193238_) {
        int $$2 = SectionPos.m_175552_(p_193237_.m_162395_());
        int $$3 = SectionPos.m_175552_(p_193237_.m_162398_());
        int $$4 = SectionPos.m_175552_(p_193237_.m_162399_());
        int $$5 = SectionPos.m_175552_(p_193237_.m_162401_());
        for (int $$6 = $$2; $$6 <= $$4; ++$$6) {
            for (int $$7 = $$3; $$7 <= $$5; ++$$7) {
                long $$8 = ChunkPos.m_45589_($$6, $$7);
                LevelChunkTicks $$9 = (LevelChunkTicks)this.f_193202_.get($$8);
                if ($$9 == null) continue;
                p_193238_.m_193288_($$8, $$9);
            }
        }
    }

    public void m_193234_(BoundingBox p_193235_) {
        Predicate<ScheduledTick> $$1 = p_193241_ -> p_193235_.m_71051_(p_193241_.f_193377_());
        this.m_193236_(p_193235_, (p_193276_, p_193277_) -> {
            ScheduledTick $$3 = p_193277_.m_193189_();
            p_193277_.m_193183_($$1);
            ScheduledTick $$4 = p_193277_.m_193189_();
            if ($$4 != $$3) {
                if ($$4 != null) {
                    this.m_193279_($$4);
                } else {
                    this.f_193203_.remove(p_193276_);
                }
            }
        });
        this.f_193206_.removeIf($$1);
        this.f_193205_.removeIf($$1);
    }

    public void m_193242_(BoundingBox p_193243_, Vec3i p_193244_) {
        ArrayList $$2 = new ArrayList();
        Predicate<ScheduledTick> $$3 = p_200922_ -> p_193243_.m_71051_(p_200922_.f_193377_());
        this.f_193206_.stream().filter($$3).forEach($$2::add);
        this.f_193205_.stream().filter($$3).forEach($$2::add);
        this.m_193236_(p_193243_, (p_200931_, p_200932_) -> p_200932_.m_193196_().filter($$3).forEach($$2::add));
        LongSummaryStatistics $$4 = $$2.stream().mapToLong(ScheduledTick::f_193380_).summaryStatistics();
        long $$5 = $$4.getMin();
        long $$6 = $$4.getMax();
        $$2.forEach(p_193260_ -> this.m_183393_(new ScheduledTick(p_193260_.f_193376_(), p_193260_.f_193377_().m_121955_(p_193244_), p_193260_.f_193378_(), p_193260_.f_193379_(), p_193260_.f_193380_() - $$5 + $$6 + 1L)));
    }

    @Override
    public int m_183574_() {
        return this.f_193202_.values().stream().mapToInt(TickAccess::m_183574_).sum();
    }

    @FunctionalInterface
    static interface PosAndContainerConsumer<T> {
        public void m_193288_(long var1, LevelChunkTicks<T> var3);
    }
}

