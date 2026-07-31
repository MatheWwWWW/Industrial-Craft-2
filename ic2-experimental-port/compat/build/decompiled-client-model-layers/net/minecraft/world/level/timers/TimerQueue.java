/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashBasedTable
 *  com.google.common.collect.Table
 *  com.google.common.primitives.UnsignedLong
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.timers;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.primitives.UnsignedLong;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.timers.TimerCallback;
import net.minecraft.world.level.timers.TimerCallbacks;
import org.slf4j.Logger;

public class TimerQueue<T> {
    private static final Logger f_82240_ = LogUtils.getLogger();
    private static final String f_165876_ = "Callback";
    private static final String f_165877_ = "Name";
    private static final String f_165878_ = "TriggerTime";
    private final TimerCallbacks<T> f_82241_;
    private final Queue<Event<T>> f_82242_ = new PriorityQueue<Event<T>>(TimerQueue.m_82270_());
    private UnsignedLong f_82243_ = UnsignedLong.ZERO;
    private final Table<String, Long, Event<T>> f_82244_ = HashBasedTable.create();

    private static <T> Comparator<Event<T>> m_82270_() {
        return Comparator.comparingLong(p_82272_ -> p_82272_.f_82273_).thenComparing(p_82269_ -> p_82269_.f_82274_);
    }

    public TimerQueue(TimerCallbacks<T> p_82249_, Stream<Dynamic<Tag>> p_82250_) {
        this(p_82249_);
        this.f_82242_.clear();
        this.f_82244_.clear();
        this.f_82243_ = UnsignedLong.ZERO;
        p_82250_.forEach(p_82253_ -> {
            if (!(p_82253_.getValue() instanceof CompoundTag)) {
                f_82240_.warn("Invalid format of events: {}", p_82253_);
                return;
            }
            this.m_82265_((CompoundTag)p_82253_.getValue());
        });
    }

    public TimerQueue(TimerCallbacks<T> p_82247_) {
        this.f_82241_ = p_82247_;
    }

    public void m_82256_(T p_82257_, long p_82258_) {
        Event<T> $$2;
        while (($$2 = this.f_82242_.peek()) != null && $$2.f_82273_ <= p_82258_) {
            this.f_82242_.remove();
            this.f_82244_.remove((Object)$$2.f_82275_, (Object)p_82258_);
            $$2.f_82276_.m_5821_(p_82257_, this, p_82258_);
        }
    }

    public void m_82261_(String p_82262_, long p_82263_, TimerCallback<T> p_82264_) {
        if (this.f_82244_.contains((Object)p_82262_, (Object)p_82263_)) {
            return;
        }
        this.f_82243_ = this.f_82243_.plus(UnsignedLong.ONE);
        Event<T> $$3 = new Event<T>(p_82263_, this.f_82243_, p_82262_, p_82264_);
        this.f_82244_.put((Object)p_82262_, (Object)p_82263_, $$3);
        this.f_82242_.add($$3);
    }

    public int m_82259_(String p_82260_) {
        Collection $$1 = this.f_82244_.row((Object)p_82260_).values();
        $$1.forEach(this.f_82242_::remove);
        int $$2 = $$1.size();
        $$1.clear();
        return $$2;
    }

    public Set<String> m_82251_() {
        return Collections.unmodifiableSet(this.f_82244_.rowKeySet());
    }

    private void m_82265_(CompoundTag p_82266_) {
        CompoundTag $$1 = p_82266_.m_128469_(f_165876_);
        TimerCallback<T> $$2 = this.f_82241_.m_82238_($$1);
        if ($$2 != null) {
            String $$3 = p_82266_.m_128461_(f_165877_);
            long $$4 = p_82266_.m_128454_(f_165878_);
            this.m_82261_($$3, $$4, $$2);
        }
    }

    private CompoundTag m_82254_(Event<T> p_82255_) {
        CompoundTag $$1 = new CompoundTag();
        $$1.m_128359_(f_165877_, p_82255_.f_82275_);
        $$1.m_128356_(f_165878_, p_82255_.f_82273_);
        $$1.m_128365_(f_165876_, this.f_82241_.m_82234_(p_82255_.f_82276_));
        return $$1;
    }

    public ListTag m_82267_() {
        ListTag $$0 = new ListTag();
        this.f_82242_.stream().sorted(TimerQueue.m_82270_()).map(this::m_82254_).forEach($$0::add);
        return $$0;
    }

    public static class Event<T> {
        public final long f_82273_;
        public final UnsignedLong f_82274_;
        public final String f_82275_;
        public final TimerCallback<T> f_82276_;

        Event(long p_82278_, UnsignedLong p_82279_, String p_82280_, TimerCallback<T> p_82281_) {
            this.f_82273_ = p_82278_;
            this.f_82274_ = p_82279_;
            this.f_82275_ = p_82280_;
            this.f_82276_ = p_82281_;
        }
    }
}

