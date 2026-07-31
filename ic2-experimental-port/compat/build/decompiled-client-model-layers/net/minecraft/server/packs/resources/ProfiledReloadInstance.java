/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Stopwatch
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.server.packs.resources;

import com.google.common.base.Stopwatch;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.Util;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleReloadInstance;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.ActiveProfiler;
import net.minecraft.util.profiling.ProfileResults;
import org.slf4j.Logger;

public class ProfiledReloadInstance
extends SimpleReloadInstance<State> {
    private static final Logger f_10645_ = LogUtils.getLogger();
    private final Stopwatch f_10646_ = Stopwatch.createUnstarted();

    public ProfiledReloadInstance(ResourceManager p_10649_, List<PreparableReloadListener> p_10650_, Executor p_10651_, Executor p_10652_, CompletableFuture<Unit> p_10653_) {
        super(p_10651_, p_10652_, p_10649_, p_10650_, (p_10668_, p_10669_, p_10670_, p_10671_, p_10672_) -> {
            AtomicLong $$6 = new AtomicLong();
            AtomicLong $$7 = new AtomicLong();
            ActiveProfiler $$8 = new ActiveProfiler(Util.f_137440_, () -> 0, false);
            ActiveProfiler $$9 = new ActiveProfiler(Util.f_137440_, () -> 0, false);
            CompletableFuture<Void> $$10 = p_10670_.m_5540_(p_10668_, p_10669_, $$8, $$9, p_143927_ -> p_10671_.execute(() -> {
                long $$2 = Util.m_137569_();
                p_143927_.run();
                $$6.addAndGet(Util.m_137569_() - $$2);
            }), p_143920_ -> p_10672_.execute(() -> {
                long $$2 = Util.m_137569_();
                p_143920_.run();
                $$7.addAndGet(Util.m_137569_() - $$2);
            }));
            return $$10.thenApplyAsync(p_143913_ -> {
                f_10645_.debug("Finished reloading " + p_10670_.m_7812_());
                return new State(p_10670_.m_7812_(), $$8.m_5948_(), $$9.m_5948_(), $$6, $$7);
            }, p_10652_);
        }, p_10653_);
        this.f_10646_.start();
        this.f_10800_ = this.f_10800_.thenApplyAsync(this::m_215483_, p_10652_);
    }

    private List<State> m_215483_(List<State> p_215484_) {
        this.f_10646_.stop();
        int $$1 = 0;
        f_10645_.info("Resource reload finished after {} ms", (Object)this.f_10646_.elapsed(TimeUnit.MILLISECONDS));
        for (State $$2 : p_215484_) {
            ProfileResults $$3 = $$2.f_10687_;
            ProfileResults $$4 = $$2.f_10688_;
            int $$5 = (int)((double)$$2.f_10689_.get() / 1000000.0);
            int $$6 = (int)((double)$$2.f_10690_.get() / 1000000.0);
            int $$7 = $$5 + $$6;
            String $$8 = $$2.f_10686_;
            f_10645_.info("{} took approximately {} ms ({} ms preparing, {} ms applying)", new Object[]{$$8, $$7, $$5, $$6});
            $$1 += $$6;
        }
        f_10645_.info("Total blocking time: {} ms", (Object)$$1);
        return p_215484_;
    }

    public static class State {
        final String f_10686_;
        final ProfileResults f_10687_;
        final ProfileResults f_10688_;
        final AtomicLong f_10689_;
        final AtomicLong f_10690_;

        State(String p_10692_, ProfileResults p_10693_, ProfileResults p_10694_, AtomicLong p_10695_, AtomicLong p_10696_) {
            this.f_10686_ = p_10692_;
            this.f_10687_ = p_10693_;
            this.f_10688_ = p_10694_;
            this.f_10689_ = p_10695_;
            this.f_10690_ = p_10696_;
        }
    }
}

