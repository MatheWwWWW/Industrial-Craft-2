/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterators
 */
package ic2.core.util;

import com.google.common.collect.Iterators;
import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PriorityExecutor
extends ThreadPoolExecutor {
    public PriorityExecutor(int threadCount) {
        super(threadCount, threadCount, 0L, TimeUnit.MILLISECONDS, new FixedPriorityQueue<Runnable>(), new ThreadFactoryImpl());
    }

    public <E> List<? extends Future<E>> submitAll(List<Callable<E>> tasks) {
        ArrayList<RunnableFuture<E>> ret = new ArrayList<RunnableFuture<E>>(tasks.size());
        for (Callable<E> task : tasks) {
            if (task == null) {
                throw new NullPointerException();
            }
            ret.add(this.newTaskFor(task));
        }
        this.executeAll(ret);
        return ret;
    }

    public <E> RunnableFuture<E> makeTask(Callable<E> callable) {
        return this.newTaskFor(callable);
    }

    public void executeAll(List<? extends Runnable> tasks) {
        if (this.isShutdown()) {
            throw new RejectedExecutionException("Tasks " + tasks + " rejected from " + this + ".");
        }
        while (this.prestartCoreThread()) {
        }
        this.getQueue().addAll(tasks);
    }

    private static class ThreadFactoryImpl
    implements ThreadFactory {
        private final ThreadGroup group = Thread.currentThread().getThreadGroup();
        private static final AtomicInteger number = new AtomicInteger(1);

        ThreadFactoryImpl() {
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(this.group, r, "ic2-poolthread-" + number.getAndIncrement(), 0L);
            thread.setDaemon(true);
            thread.setPriority(5);
            return thread;
        }
    }

    private static class FixedPriorityQueue<E>
    extends AbstractQueue<E>
    implements BlockingQueue<E> {
        private final Map<Priority, Queue<E>> queues = new EnumMap<Priority, Queue<E>>(Priority.class);

        public FixedPriorityQueue() {
            for (Priority priority : Priority.values()) {
                this.queues.put(priority, new ArrayDeque());
            }
        }

        @Override
        public synchronized E poll() {
            for (Queue<E> queue : this.queues.values()) {
                E ret = queue.poll();
                if (ret == null) continue;
                return ret;
            }
            return null;
        }

        @Override
        public synchronized E peek() {
            for (Queue<E> queue : this.queues.values()) {
                E ret = queue.peek();
                if (ret == null) continue;
                return ret;
            }
            return null;
        }

        @Override
        public synchronized int size() {
            int ret = 0;
            for (Queue<E> queue : this.queues.values()) {
                ret += queue.size();
            }
            return ret;
        }

        @Override
        public synchronized Iterator<E> iterator() {
            ArrayList iterators = new ArrayList(this.queues.size());
            for (Queue<E> queue : this.queues.values()) {
                iterators.add(queue.iterator());
            }
            return Iterators.concat(iterators.iterator());
        }

        @Override
        public synchronized boolean offer(E e) {
            Queue<E> queue = this.queues.get((Object)this.getPriority(e));
            queue.offer(e);
            this.notify();
            return true;
        }

        @Override
        public void put(E e) throws InterruptedException {
            this.offer(e);
        }

        @Override
        public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
            return this.offer(e);
        }

        @Override
        public synchronized E take() throws InterruptedException {
            E ret = this.poll();
            while (ret == null) {
                this.wait();
                ret = this.poll();
            }
            return ret;
        }

        @Override
        public synchronized E poll(long timeout, TimeUnit unit) throws InterruptedException {
            long duration;
            E ret = this.poll();
            if (ret != null) {
                return ret;
            }
            long endTime = System.nanoTime() + unit.toNanos(timeout);
            while ((duration = endTime - System.nanoTime()) > 0L) {
                this.wait(duration / 1000000L, (int)(duration % 1000000L));
                ret = this.poll();
                if (ret == null) continue;
            }
            return ret;
        }

        @Override
        public int remainingCapacity() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int drainTo(Collection<? super E> c) {
            return this.drainTo(c, Integer.MAX_VALUE);
        }

        @Override
        public synchronized int drainTo(Collection<? super E> c, int maxElements) {
            int ret = 0;
            for (Queue<E> queue : this.queues.values()) {
                E x;
                while (ret < maxElements && (x = queue.poll()) != null) {
                    c.add(x);
                    ++ret;
                }
            }
            return ret;
        }

        @Override
        public synchronized void clear() {
            for (Queue<E> queue : this.queues.values()) {
                queue.clear();
            }
        }

        @Override
        public synchronized boolean contains(Object o) {
            for (Queue<E> queue : this.queues.values()) {
                if (!queue.contains(o)) continue;
                return true;
            }
            return false;
        }

        @Override
        public synchronized boolean removeAll(Collection<?> c) {
            boolean ret = false;
            for (Queue<E> queue : this.queues.values()) {
                if (!queue.removeAll(c)) continue;
                ret = true;
            }
            return ret;
        }

        @Override
        public synchronized boolean retainAll(Collection<?> c) {
            boolean ret = false;
            for (Queue<E> queue : this.queues.values()) {
                if (!queue.retainAll(c)) continue;
                ret = true;
            }
            return ret;
        }

        @Override
        public synchronized Object[] toArray() {
            return super.toArray();
        }

        @Override
        public synchronized <T> T[] toArray(T[] a) {
            return super.toArray(a);
        }

        @Override
        public synchronized String toString() {
            return super.toString();
        }

        @Override
        public synchronized boolean addAll(Collection<? extends E> c) {
            if (c == null) {
                throw new NullPointerException();
            }
            if (c == this) {
                throw new IllegalArgumentException();
            }
            for (E e : c) {
                Queue<E> queue = this.queues.get((Object)this.getPriority(e));
                queue.offer(e);
            }
            this.notifyAll();
            return !c.isEmpty();
        }

        private Priority getPriority(E x) {
            if (x instanceof CustomPriority) {
                return ((CustomPriority)x).getPriority();
            }
            return Priority.Default;
        }
    }

    public static enum Priority {
        High,
        Default,
        Low;

    }

    public static interface CustomPriority {
        public Priority getPriority();
    }
}

