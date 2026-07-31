/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 */
package net.minecraft.server.packs.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.Util;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ProfiledReloadInstance;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.InactiveProfiler;

public class SimpleReloadInstance<S>
implements ReloadInstance {
    private static final int f_143937_ = 2;
    private static final int f_143938_ = 2;
    private static final int f_143939_ = 1;
    protected final CompletableFuture<Unit> f_10799_ = new CompletableFuture();
    protected CompletableFuture<List<S>> f_10800_;
    final Set<PreparableReloadListener> f_10801_;
    private final int f_10802_;
    private int f_10803_;
    private int f_10804_;
    private final AtomicInteger f_10805_ = new AtomicInteger();
    private final AtomicInteger f_10806_ = new AtomicInteger();

    public static SimpleReloadInstance<Void> m_10815_(ResourceManager p_10816_, List<PreparableReloadListener> p_10817_, Executor p_10818_, Executor p_10819_, CompletableFuture<Unit> p_10820_) {
        return new SimpleReloadInstance<Void>(p_10818_, p_10819_, p_10816_, p_10817_, (p_10829_, p_10830_, p_10831_, p_10832_, p_10833_) -> p_10831_.m_5540_(p_10829_, p_10830_, InactiveProfiler.f_18554_, InactiveProfiler.f_18554_, p_10818_, p_10833_), p_10820_);
    }

    protected SimpleReloadInstance(Executor p_10808_, final Executor p_10809_, ResourceManager p_10810_, List<PreparableReloadListener> p_10811_, StateFactory<S> p_10812_, CompletableFuture<Unit> p_10813_) {
        this.f_10802_ = p_10811_.size();
        this.f_10805_.incrementAndGet();
        p_10813_.thenRun(this.f_10806_::incrementAndGet);
        ArrayList $$6 = Lists.newArrayList();
        CompletableFuture<Unit> $$7 = p_10813_;
        this.f_10801_ = Sets.newHashSet(p_10811_);
        for (final PreparableReloadListener $$8 : p_10811_) {
            final CompletableFuture<Unit> $$9 = $$7;
            CompletableFuture<S> $$10 = p_10812_.m_10863_(new PreparableReloadListener.PreparationBarrier(){

                @Override
                public <T> CompletableFuture<T> m_6769_(T p_10858_) {
                    p_10809_.execute(() -> {
                        SimpleReloadInstance.this.f_10801_.remove($$8);
                        if (SimpleReloadInstance.this.f_10801_.isEmpty()) {
                            SimpleReloadInstance.this.f_10799_.complete(Unit.INSTANCE);
                        }
                    });
                    return SimpleReloadInstance.this.f_10799_.thenCombine((CompletionStage)$$9, (p_10861_, p_10862_) -> p_10858_);
                }
            }, p_10810_, $$8, p_10842_ -> {
                this.f_10805_.incrementAndGet();
                p_10808_.execute(() -> {
                    p_10842_.run();
                    this.f_10806_.incrementAndGet();
                });
            }, p_10836_ -> {
                ++this.f_10803_;
                p_10809_.execute(() -> {
                    p_10836_.run();
                    ++this.f_10804_;
                });
            });
            $$6.add($$10);
            $$7 = $$10;
        }
        this.f_10800_ = Util.m_143840_($$6);
    }

    @Override
    public CompletableFuture<?> m_7237_() {
        return this.f_10800_;
    }

    @Override
    public float m_7750_() {
        int $$0 = this.f_10802_ - this.f_10801_.size();
        float $$1 = this.f_10806_.get() * 2 + this.f_10804_ * 2 + $$0 * 1;
        float $$2 = this.f_10805_.get() * 2 + this.f_10803_ * 2 + this.f_10802_ * 1;
        return $$1 / $$2;
    }

    public static ReloadInstance m_203834_(ResourceManager p_203835_, List<PreparableReloadListener> p_203836_, Executor p_203837_, Executor p_203838_, CompletableFuture<Unit> p_203839_, boolean p_203840_) {
        if (p_203840_) {
            return new ProfiledReloadInstance(p_203835_, p_203836_, p_203837_, p_203838_, p_203839_);
        }
        return SimpleReloadInstance.m_10815_(p_203835_, p_203836_, p_203837_, p_203838_, p_203839_);
    }

    protected static interface StateFactory<S> {
        public CompletableFuture<S> m_10863_(PreparableReloadListener.PreparationBarrier var1, ResourceManager var2, PreparableReloadListener var3, Executor var4, Executor var5);
    }
}

