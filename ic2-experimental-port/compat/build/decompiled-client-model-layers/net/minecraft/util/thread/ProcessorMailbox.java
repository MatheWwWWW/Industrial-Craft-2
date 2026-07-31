/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.ints.Int2BooleanFunction
 *  org.slf4j.Logger
 */
package net.minecraft.util.thread;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.Util;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.MetricsRegistry;
import net.minecraft.util.profiling.metrics.ProfilerMeasured;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.util.thread.StrictQueue;
import org.slf4j.Logger;

public class ProcessorMailbox<T>
implements ProfilerMeasured,
ProcessorHandle<T>,
AutoCloseable,
Runnable {
    private static final Logger f_18735_ = LogUtils.getLogger();
    private static final int f_146353_ = 1;
    private static final int f_146354_ = 2;
    private final AtomicInteger f_18736_ = new AtomicInteger(0);
    private final StrictQueue<? super T, ? extends Runnable> f_18734_;
    private final Executor f_18737_;
    private final String f_18738_;

    public static ProcessorMailbox<Runnable> m_18751_(Executor p_18752_, String p_18753_) {
        return new ProcessorMailbox<Runnable>(new StrictQueue.QueueStrictQueue(new ConcurrentLinkedQueue()), p_18752_, p_18753_);
    }

    public ProcessorMailbox(StrictQueue<? super T, ? extends Runnable> p_18741_, Executor p_18742_, String p_18743_) {
        this.f_18737_ = p_18742_;
        this.f_18734_ = p_18741_;
        this.f_18738_ = p_18743_;
        MetricsRegistry.f_146067_.m_146072_(this);
    }

    private boolean m_18744_() {
        int $$0;
        do {
            if ((($$0 = this.f_18736_.get()) & 3) == 0) continue;
            return false;
        } while (!this.f_18736_.compareAndSet($$0, $$0 | 2));
        return true;
    }

    private void m_18754_() {
        int $$0;
        while (!this.f_18736_.compareAndSet($$0 = this.f_18736_.get(), $$0 & 0xFFFFFFFD)) {
        }
    }

    private boolean m_18756_() {
        if ((this.f_18736_.get() & 1) != 0) {
            return false;
        }
        return !this.f_18734_.m_7263_();
    }

    @Override
    public void close() {
        int $$0;
        while (!this.f_18736_.compareAndSet($$0 = this.f_18736_.get(), $$0 | 1)) {
        }
    }

    private boolean m_18758_() {
        return (this.f_18736_.get() & 2) != 0;
    }

    private boolean m_18759_() {
        if (!this.m_18758_()) {
            return false;
        }
        Runnable $$0 = this.f_18734_.m_6610_();
        if ($$0 == null) {
            return false;
        }
        Util.m_143787_(this.f_18738_, $$0).run();
        return true;
    }

    @Override
    public void run() {
        try {
            this.m_18747_(p_18746_ -> p_18746_ == 0);
        }
        finally {
            this.m_18754_();
            this.m_18760_();
        }
    }

    public void m_182329_() {
        try {
            this.m_18747_(p_182331_ -> true);
        }
        finally {
            this.m_18754_();
            this.m_18760_();
        }
    }

    @Override
    public void m_6937_(T p_18750_) {
        this.f_18734_.m_6944_(p_18750_);
        this.m_18760_();
    }

    private void m_18760_() {
        if (this.m_18756_() && this.m_18744_()) {
            try {
                this.f_18737_.execute(this);
            }
            catch (RejectedExecutionException $$0) {
                try {
                    this.f_18737_.execute(this);
                }
                catch (RejectedExecutionException $$1) {
                    f_18735_.error("Cound not schedule mailbox", (Throwable)$$1);
                }
            }
        }
    }

    private int m_18747_(Int2BooleanFunction p_18748_) {
        int $$1 = 0;
        while (p_18748_.get($$1) && this.m_18759_()) {
            ++$$1;
        }
        return $$1;
    }

    public int m_146355_() {
        return this.f_18734_.m_142732_();
    }

    public boolean m_201938_() {
        return this.m_18758_() && !this.f_18734_.m_7263_();
    }

    public String toString() {
        return this.f_18738_ + " " + this.f_18736_.get() + " " + this.f_18734_.m_7263_();
    }

    @Override
    public String m_7326_() {
        return this.f_18738_;
    }

    @Override
    public List<MetricSampler> m_142754_() {
        return ImmutableList.of((Object)MetricSampler.m_146009_(this.f_18738_ + "-queue-size", MetricCategory.MAIL_BOXES, this::m_146355_));
    }
}

