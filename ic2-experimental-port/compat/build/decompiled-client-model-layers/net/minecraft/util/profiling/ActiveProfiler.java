/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  it.unimi.dsi.fastutil.longs.LongList
 *  it.unimi.dsi.fastutil.objects.Object2LongMap
 *  it.unimi.dsi.fastutil.objects.Object2LongMaps
 *  it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArraySet
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 */
package net.minecraft.util.profiling;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.util.profiling.FilledProfileResults;
import net.minecraft.util.profiling.ProfileCollector;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.ProfilerPathEntry;
import net.minecraft.util.profiling.metrics.MetricCategory;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;

public class ActiveProfiler
implements ProfileCollector {
    private static final long f_18368_ = Duration.ofMillis(100L).toNanos();
    private static final Logger f_18369_ = LogUtils.getLogger();
    private final List<String> f_18370_ = Lists.newArrayList();
    private final LongList f_18371_ = new LongArrayList();
    private final Map<String, PathEntry> f_18372_ = Maps.newHashMap();
    private final IntSupplier f_18373_;
    private final LongSupplier f_18374_;
    private final long f_18375_;
    private final int f_18376_;
    private String f_18377_ = "";
    private boolean f_18378_;
    @Nullable
    private PathEntry f_18379_;
    private final boolean f_18380_;
    private final Set<Pair<String, MetricCategory>> f_145926_ = new ObjectArraySet();

    public ActiveProfiler(LongSupplier p_18383_, IntSupplier p_18384_, boolean p_18385_) {
        this.f_18375_ = p_18383_.getAsLong();
        this.f_18374_ = p_18383_;
        this.f_18376_ = p_18384_.getAsInt();
        this.f_18373_ = p_18384_;
        this.f_18380_ = p_18385_;
    }

    @Override
    public void m_7242_() {
        if (this.f_18378_) {
            f_18369_.error("Profiler tick already started - missing endTick()?");
            return;
        }
        this.f_18378_ = true;
        this.f_18377_ = "";
        this.f_18370_.clear();
        this.m_6180_("root");
    }

    @Override
    public void m_7241_() {
        if (!this.f_18378_) {
            f_18369_.error("Profiler tick already ended - missing startTick()?");
            return;
        }
        this.m_7238_();
        this.f_18378_ = false;
        if (!this.f_18377_.isEmpty()) {
            f_18369_.error("Profiler tick ended before path was fully popped (remainder: '{}'). Mismatched push/pop?", LogUtils.defer(() -> ProfileResults.m_18575_(this.f_18377_)));
        }
    }

    @Override
    public void m_6180_(String p_18390_) {
        if (!this.f_18378_) {
            f_18369_.error("Cannot push '{}' to profiler if profiler tick hasn't started - missing startTick()?", (Object)p_18390_);
            return;
        }
        if (!this.f_18377_.isEmpty()) {
            this.f_18377_ = this.f_18377_ + "\u001e";
        }
        this.f_18377_ = this.f_18377_ + p_18390_;
        this.f_18370_.add(this.f_18377_);
        this.f_18371_.add(Util.m_137569_());
        this.f_18379_ = null;
    }

    @Override
    public void m_6521_(Supplier<String> p_18392_) {
        this.m_6180_(p_18392_.get());
    }

    @Override
    public void m_142259_(MetricCategory p_145928_) {
        this.f_145926_.add((Pair<String, MetricCategory>)Pair.of((Object)this.f_18377_, (Object)((Object)p_145928_)));
    }

    @Override
    public void m_7238_() {
        if (!this.f_18378_) {
            f_18369_.error("Cannot pop from profiler if profiler tick hasn't started - missing startTick()?");
            return;
        }
        if (this.f_18371_.isEmpty()) {
            f_18369_.error("Tried to pop one too many times! Mismatched push() and pop()?");
            return;
        }
        long $$0 = Util.m_137569_();
        long $$1 = this.f_18371_.removeLong(this.f_18371_.size() - 1);
        this.f_18370_.remove(this.f_18370_.size() - 1);
        long $$2 = $$0 - $$1;
        PathEntry $$3 = this.m_18406_();
        $$3.f_145934_ += $$2;
        ++$$3.f_18410_;
        $$3.f_145932_ = Math.max($$3.f_145932_, $$2);
        $$3.f_145933_ = Math.min($$3.f_145933_, $$2);
        if (this.f_18380_ && $$2 > f_18368_) {
            f_18369_.warn("Something's taking too long! '{}' took aprox {} ms", LogUtils.defer(() -> ProfileResults.m_18575_(this.f_18377_)), LogUtils.defer(() -> (double)$$2 / 1000000.0));
        }
        this.f_18377_ = this.f_18370_.isEmpty() ? "" : this.f_18370_.get(this.f_18370_.size() - 1);
        this.f_18379_ = null;
    }

    @Override
    public void m_6182_(String p_18395_) {
        this.m_7238_();
        this.m_6180_(p_18395_);
    }

    @Override
    public void m_6523_(Supplier<String> p_18397_) {
        this.m_7238_();
        this.m_6521_(p_18397_);
    }

    private PathEntry m_18406_() {
        if (this.f_18379_ == null) {
            this.f_18379_ = this.f_18372_.computeIfAbsent(this.f_18377_, p_18405_ -> new PathEntry());
        }
        return this.f_18379_;
    }

    @Override
    public void m_183275_(String p_185247_, int p_185248_) {
        this.m_18406_().f_18411_.addTo((Object)p_185247_, (long)p_185248_);
    }

    @Override
    public void m_183536_(Supplier<String> p_185250_, int p_185251_) {
        this.m_18406_().f_18411_.addTo((Object)p_185250_.get(), (long)p_185251_);
    }

    @Override
    public ProfileResults m_5948_() {
        return new FilledProfileResults(this.f_18372_, this.f_18375_, this.f_18376_, this.f_18374_.getAsLong(), this.f_18373_.getAsInt());
    }

    @Override
    @Nullable
    public PathEntry m_142431_(String p_145930_) {
        return this.f_18372_.get(p_145930_);
    }

    @Override
    public Set<Pair<String, MetricCategory>> m_142579_() {
        return this.f_145926_;
    }

    public static class PathEntry
    implements ProfilerPathEntry {
        long f_145932_ = Long.MIN_VALUE;
        long f_145933_ = Long.MAX_VALUE;
        long f_145934_;
        long f_18410_;
        final Object2LongOpenHashMap<String> f_18411_ = new Object2LongOpenHashMap();

        @Override
        public long m_7235_() {
            return this.f_145934_;
        }

        @Override
        public long m_142752_() {
            return this.f_145932_;
        }

        @Override
        public long m_7234_() {
            return this.f_18410_;
        }

        @Override
        public Object2LongMap<String> m_7446_() {
            return Object2LongMaps.unmodifiable(this.f_18411_);
        }
    }
}

