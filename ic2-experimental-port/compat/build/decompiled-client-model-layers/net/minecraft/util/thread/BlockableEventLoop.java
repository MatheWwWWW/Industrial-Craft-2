/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Queues
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.util.thread;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Queues;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.MetricsRegistry;
import net.minecraft.util.profiling.metrics.ProfilerMeasured;
import net.minecraft.util.thread.ProcessorHandle;
import org.slf4j.Logger;

public abstract class BlockableEventLoop<R extends Runnable>
implements ProfilerMeasured,
ProcessorHandle<R>,
Executor {
    private final String f_18680_;
    private static final Logger f_18681_ = LogUtils.getLogger();
    private final Queue<R> f_18682_ = Queues.newConcurrentLinkedQueue();
    private int f_18683_;

    protected BlockableEventLoop(String p_18686_) {
        this.f_18680_ = p_18686_;
        MetricsRegistry.f_146067_.m_146072_(this);
    }

    protected abstract R m_6681_(Runnable var1);

    protected abstract boolean m_6362_(R var1);

    public boolean m_18695_() {
        return Thread.currentThread() == this.m_6304_();
    }

    protected abstract Thread m_6304_();

    protected boolean m_5660_() {
        return !this.m_18695_();
    }

    public int m_18696_() {
        return this.f_18682_.size();
    }

    @Override
    public String m_7326_() {
        return this.f_18680_;
    }

    public <V> CompletableFuture<V> m_18691_(Supplier<V> p_18692_) {
        if (this.m_5660_()) {
            return CompletableFuture.supplyAsync(p_18692_, this);
        }
        return CompletableFuture.completedFuture(p_18692_.get());
    }

    private CompletableFuture<Void> m_18689_(Runnable p_18690_) {
        return CompletableFuture.supplyAsync(() -> {
            p_18690_.run();
            return null;
        }, this);
    }

    public CompletableFuture<Void> m_18707_(Runnable p_18708_) {
        if (this.m_5660_()) {
            return this.m_18689_(p_18708_);
        }
        p_18708_.run();
        return CompletableFuture.completedFuture(null);
    }

    public void m_18709_(Runnable p_18710_) {
        if (!this.m_18695_()) {
            this.m_18689_(p_18710_).join();
        } else {
            p_18710_.run();
        }
    }

    @Override
    public void m_6937_(R p_18712_) {
        this.f_18682_.add(p_18712_);
        LockSupport.unpark(this.m_6304_());
    }

    @Override
    public void execute(Runnable p_18706_) {
        if (this.m_5660_()) {
            this.m_6937_(this.m_6681_(p_18706_));
        } else {
            p_18706_.run();
        }
    }

    public void m_201446_(Runnable p_201937_) {
        this.execute(p_201937_);
    }

    protected void m_18698_() {
        this.f_18682_.clear();
    }

    protected void m_18699_() {
        while (this.m_7245_()) {
        }
    }

    public boolean m_7245_() {
        Runnable $$0 = (Runnable)this.f_18682_.peek();
        if ($$0 == null) {
            return false;
        }
        if (this.f_18683_ == 0 && !this.m_6362_($$0)) {
            return false;
        }
        this.m_6367_((Runnable)this.f_18682_.remove());
        return true;
    }

    public void m_18701_(BooleanSupplier p_18702_) {
        ++this.f_18683_;
        try {
            while (!p_18702_.getAsBoolean()) {
                if (this.m_7245_()) continue;
                this.m_5667_();
            }
        }
        finally {
            --this.f_18683_;
        }
    }

    protected void m_5667_() {
        Thread.yield();
        LockSupport.parkNanos("waiting for tasks", 100000L);
    }

    protected void m_6367_(R p_18700_) {
        try {
            p_18700_.run();
        }
        catch (Exception $$1) {
            f_18681_.error(LogUtils.FATAL_MARKER, "Error executing task on {}", (Object)this.m_7326_(), (Object)$$1);
        }
    }

    @Override
    public List<MetricSampler> m_142754_() {
        return ImmutableList.of((Object)MetricSampler.m_146009_(this.f_18680_ + "-pending-tasks", MetricCategory.EVENT_LOOPS, this::m_18696_));
    }

    @Override
    public /* synthetic */ void m_6937_(Object object) {
        this.m_6937_((R)((Runnable)object));
    }
}

