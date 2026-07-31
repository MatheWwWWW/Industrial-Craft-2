/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Either
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.gui.task;

import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.gui.task.RepeatedDelayStrategy;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.util.TimeSource;
import org.slf4j.Logger;

public class DataFetcher {
    static final Logger f_238747_ = LogUtils.getLogger();
    final Executor f_238658_;
    final TimeUnit f_238755_;
    final TimeSource f_238834_;

    public DataFetcher(Executor p_239381_, TimeUnit p_239382_, TimeSource p_239383_) {
        this.f_238658_ = p_239381_;
        this.f_238755_ = p_239382_;
        this.f_238834_ = p_239383_;
    }

    public <T> Task<T> m_239622_(String p_239623_, Callable<T> p_239624_, Duration p_239625_, RepeatedDelayStrategy p_239626_) {
        long $$4 = this.f_238755_.convert(p_239625_);
        if ($$4 == 0L) {
            throw new IllegalArgumentException("Period of " + p_239625_ + " too short for selected resolution of " + this.f_238755_);
        }
        return new Task<T>(p_239623_, p_239624_, $$4, p_239626_);
    }

    public Subscription m_239139_() {
        return new Subscription();
    }

    public class Task<T> {
        private final String f_238608_;
        private final Callable<T> f_238640_;
        private final long f_238571_;
        private final RepeatedDelayStrategy f_238639_;
        @Nullable
        private CompletableFuture<ComputationResult<T>> f_238827_;
        @Nullable
        SuccessfulComputationResult<T> f_238610_;
        private long f_238812_ = -1L;

        Task(String p_239074_, Callable<T> p_239075_, long p_239076_, RepeatedDelayStrategy p_239077_) {
            this.f_238608_ = p_239074_;
            this.f_238640_ = p_239075_;
            this.f_238571_ = p_239076_;
            this.f_238639_ = p_239077_;
        }

        void m_239709_(long p_239710_) {
            if (this.f_238827_ != null) {
                ComputationResult $$1 = this.f_238827_.getNow(null);
                if ($$1 == null) {
                    return;
                }
                this.f_238827_ = null;
                long $$2 = $$1.f_238664_;
                $$1.f_238822_().ifLeft(p_239691_ -> {
                    this.f_238610_ = new SuccessfulComputationResult<Object>(p_239691_, $$2);
                    this.f_238812_ = $$2 + this.f_238571_ * this.f_238639_.m_239029_();
                }).ifRight(p_239281_ -> {
                    long $$2 = this.f_238639_.m_239153_();
                    f_238747_.warn("Failed to process task {}, will repeat after {} cycles", new Object[]{this.f_238608_, $$2, p_239281_});
                    this.f_238812_ = $$2 + this.f_238571_ * $$2;
                });
            }
            if (this.f_238812_ <= p_239710_) {
                this.f_238827_ = CompletableFuture.supplyAsync(() -> {
                    try {
                        T $$0 = this.f_238640_.call();
                        long $$1 = DataFetcher.this.f_238834_.m_239336_(DataFetcher.this.f_238755_);
                        return new ComputationResult(Either.left($$0), $$1);
                    }
                    catch (Exception $$2) {
                        long $$3 = DataFetcher.this.f_238834_.m_239336_(DataFetcher.this.f_238755_);
                        return new ComputationResult(Either.right((Object)$$2), $$3);
                    }
                }, DataFetcher.this.f_238658_);
            }
        }

        void m_239964_() {
            this.f_238827_ = null;
            this.f_238610_ = null;
            this.f_238812_ = -1L;
        }
    }

    public class Subscription {
        private final List<SubscribedTask<?>> f_238520_ = new ArrayList();

        public <T> void m_239441_(Task<T> p_239442_, Consumer<T> p_239443_) {
            SubscribedTask<T> $$2 = new SubscribedTask<T>(p_239442_, p_239443_);
            this.f_238520_.add($$2);
            $$2.m_240045_();
        }

        public void m_240009_() {
            for (SubscribedTask<?> $$0 : this.f_238520_) {
                $$0.m_240119_();
            }
        }

        public void m_239355_() {
            for (SubscribedTask<?> $$0 : this.f_238520_) {
                $$0.m_239225_(DataFetcher.this.f_238834_.m_239336_(DataFetcher.this.f_238755_));
            }
        }

        public void m_240120_() {
            for (SubscribedTask<?> $$0 : this.f_238520_) {
                $$0.m_239278_();
            }
        }
    }

    class SubscribedTask<T> {
        private final Task<T> f_238778_;
        private final Consumer<T> f_238835_;
        private long f_238534_ = -1L;

        SubscribedTask(Task<T> p_239959_, Consumer<T> p_239960_) {
            this.f_238778_ = p_239959_;
            this.f_238835_ = p_239960_;
        }

        void m_239225_(long p_239226_) {
            this.f_238778_.m_239709_(p_239226_);
            this.m_240045_();
        }

        void m_240045_() {
            SuccessfulComputationResult $$0 = this.f_238778_.f_238610_;
            if ($$0 != null && this.f_238534_ < $$0.f_238539_) {
                this.f_238835_.accept($$0.f_238529_);
                this.f_238534_ = $$0.f_238539_;
            }
        }

        void m_240119_() {
            SuccessfulComputationResult $$0 = this.f_238778_.f_238610_;
            if ($$0 != null) {
                this.f_238835_.accept($$0.f_238529_);
                this.f_238534_ = $$0.f_238539_;
            }
        }

        void m_239278_() {
            this.f_238778_.m_239964_();
            this.f_238534_ = -1L;
        }
    }

    record SuccessfulComputationResult<T>(T f_238529_, long f_238539_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{SuccessfulComputationResult.class, "value;time", "f_238529_", "f_238539_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{SuccessfulComputationResult.class, "value;time", "f_238529_", "f_238539_"}, this);
        }

        @Override
        public final boolean equals(Object p_240174_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{SuccessfulComputationResult.class, "value;time", "f_238529_", "f_238539_"}, this, p_240174_);
        }
    }

    record ComputationResult<T>(Either<T, Exception> f_238822_, long f_238664_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ComputationResult.class, "value;time", "f_238822_", "f_238664_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ComputationResult.class, "value;time", "f_238822_", "f_238664_"}, this);
        }

        @Override
        public final boolean equals(Object p_239550_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ComputationResult.class, "value;time", "f_238822_", "f_238664_"}, this, p_239550_);
        }
    }
}

