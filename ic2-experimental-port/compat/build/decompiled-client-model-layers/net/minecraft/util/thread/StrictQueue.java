/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Queues
 *  javax.annotation.Nullable
 */
package net.minecraft.util.thread;

import com.google.common.collect.Queues;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

public interface StrictQueue<T, F> {
    @Nullable
    public F m_6610_();

    public boolean m_6944_(T var1);

    public boolean m_7263_();

    public int m_142732_();

    public static final class FixedPriorityQueue
    implements StrictQueue<IntRunnable, Runnable> {
        private final Queue<Runnable>[] f_185865_;
        private final AtomicInteger f_185866_ = new AtomicInteger();

        public FixedPriorityQueue(int p_18773_) {
            this.f_185865_ = new Queue[p_18773_];
            for (int $$1 = 0; $$1 < p_18773_; ++$$1) {
                this.f_185865_[$$1] = Queues.newConcurrentLinkedQueue();
            }
        }

        @Override
        @Nullable
        public Runnable m_6610_() {
            for (Queue<Runnable> $$0 : this.f_185865_) {
                Runnable $$1 = $$0.poll();
                if ($$1 == null) continue;
                this.f_185866_.decrementAndGet();
                return $$1;
            }
            return null;
        }

        @Override
        public boolean m_6944_(IntRunnable p_18778_) {
            int $$1 = p_18778_.f_18783_;
            if ($$1 >= this.f_185865_.length || $$1 < 0) {
                throw new IndexOutOfBoundsException(String.format(Locale.ROOT, "Priority %d not supported. Expected range [0-%d]", $$1, this.f_185865_.length - 1));
            }
            this.f_185865_[$$1].add(p_18778_);
            this.f_185866_.incrementAndGet();
            return true;
        }

        @Override
        public boolean m_7263_() {
            return this.f_185866_.get() == 0;
        }

        @Override
        public int m_142732_() {
            return this.f_185866_.get();
        }

        @Override
        @Nullable
        public /* synthetic */ Object m_6610_() {
            return this.m_6610_();
        }
    }

    public static final class IntRunnable
    implements Runnable {
        final int f_18783_;
        private final Runnable f_18784_;

        public IntRunnable(int p_18786_, Runnable p_18787_) {
            this.f_18783_ = p_18786_;
            this.f_18784_ = p_18787_;
        }

        @Override
        public void run() {
            this.f_18784_.run();
        }

        public int m_18788_() {
            return this.f_18783_;
        }
    }

    public static final class QueueStrictQueue<T>
    implements StrictQueue<T, T> {
        private final Queue<T> f_18790_;

        public QueueStrictQueue(Queue<T> p_18792_) {
            this.f_18790_ = p_18792_;
        }

        @Override
        @Nullable
        public T m_6610_() {
            return this.f_18790_.poll();
        }

        @Override
        public boolean m_6944_(T p_18795_) {
            return this.f_18790_.add(p_18795_);
        }

        @Override
        public boolean m_7263_() {
            return this.f_18790_.isEmpty();
        }

        @Override
        public int m_142732_() {
            return this.f_18790_.size();
        }
    }
}

