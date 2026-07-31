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
    public PriorityExecutor(int n) {
        super(n, n, 0L, TimeUnit.MILLISECONDS, new FixedPriorityQueue<Runnable>(), new ThreadFactoryImpl());
    }

    public <E> List<? extends Future<E>> submitAll(List<Callable<E>> list) {
        ArrayList<RunnableFuture<E>> arrayList = new ArrayList<RunnableFuture<E>>(list.size());
        for (Callable<E> callable : list) {
            if (callable == null) {
                throw new NullPointerException();
            }
            arrayList.add(this.newTaskFor(callable));
        }
        this.executeAll(arrayList);
        return arrayList;
    }

    public <E> RunnableFuture<E> makeTask(Callable<E> callable) {
        return this.newTaskFor(callable);
    }

    public void executeAll(List<? extends Runnable> list) {
        if (this.isShutdown()) {
            throw new RejectedExecutionException("Tasks " + list + " rejected from " + this + ".");
        }
        while (this.prestartCoreThread()) {
        }
        this.getQueue().addAll(list);
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
                E e = queue.poll();
                if (e == null) continue;
                return e;
            }
            return null;
        }

        @Override
        public synchronized E peek() {
            for (Queue<E> queue : this.queues.values()) {
                E e = queue.peek();
                if (e == null) continue;
                return e;
            }
            return null;
        }

        @Override
        public synchronized int size() {
            int n = 0;
            for (Queue<E> queue : this.queues.values()) {
                n += queue.size();
            }
            return n;
        }

        @Override
        public synchronized Iterator<E> iterator() {
            ArrayList arrayList = new ArrayList(this.queues.size());
            for (Queue<E> queue : this.queues.values()) {
                arrayList.add(queue.iterator());
            }
            return Iterators.concat(arrayList.iterator());
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
        public boolean offer(E e, long l, TimeUnit timeUnit) throws InterruptedException {
            return this.offer(e);
        }

        @Override
        public synchronized E take() throws InterruptedException {
            E e = this.poll();
            while (e == null) {
                this.wait();
                e = this.poll();
            }
            return e;
        }

        @Override
        public synchronized E poll(long l, TimeUnit timeUnit) throws InterruptedException {
            long l2;
            E e = this.poll();
            if (e != null) {
                return e;
            }
            long l3 = System.nanoTime() + timeUnit.toNanos(l);
            while ((l2 = l3 - System.nanoTime()) > 0L) {
                this.wait(l2 / 1000000L, (int)(l2 % 1000000L));
                e = this.poll();
                if (e == null) continue;
            }
            return e;
        }

        @Override
        public int remainingCapacity() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int drainTo(Collection<? super E> collection) {
            return this.drainTo(collection, Integer.MAX_VALUE);
        }

        @Override
        public synchronized int drainTo(Collection<? super E> collection, int n) {
            int n2 = 0;
            for (Queue<E> queue : this.queues.values()) {
                E e;
                while (n2 < n && (e = queue.poll()) != null) {
                    collection.add(e);
                    ++n2;
                }
            }
            return n2;
        }

        @Override
        public synchronized void clear() {
            for (Queue<E> queue : this.queues.values()) {
                queue.clear();
            }
        }

        @Override
        public synchronized boolean contains(Object object) {
            for (Queue<E> queue : this.queues.values()) {
                if (!queue.contains(object)) continue;
                return true;
            }
            return false;
        }

        @Override
        public synchronized boolean removeAll(Collection<?> collection) {
            boolean bl = false;
            for (Queue<E> queue : this.queues.values()) {
                if (!queue.removeAll(collection)) continue;
                bl = true;
            }
            return bl;
        }

        @Override
        public synchronized boolean retainAll(Collection<?> collection) {
            boolean bl = false;
            for (Queue<E> queue : this.queues.values()) {
                if (!queue.retainAll(collection)) continue;
                bl = true;
            }
            return bl;
        }

        @Override
        public synchronized Object[] toArray() {
            return super.toArray();
        }

        @Override
        public synchronized <T> T[] toArray(T[] TArray) {
            return super.toArray(TArray);
        }

        @Override
        public synchronized String toString() {
            return super.toString();
        }

        @Override
        public synchronized boolean addAll(Collection<? extends E> collection) {
            if (collection == null) {
                throw new NullPointerException();
            }
            if (collection == this) {
                throw new IllegalArgumentException();
            }
            for (E e : collection) {
                Queue<E> queue = this.queues.get((Object)this.getPriority(e));
                queue.offer(e);
            }
            this.notifyAll();
            return !collection.isEmpty();
        }

        private Priority getPriority(E e) {
            if (e instanceof CustomPriority) {
                return ((CustomPriority)e).getPriority();
            }
            return Priority.Default;
        }
    }

    private static class ThreadFactoryImpl
    implements ThreadFactory {
        private final ThreadGroup group = Thread.currentThread().getThreadGroup();
        private static final AtomicInteger number = new AtomicInteger(1);

        ThreadFactoryImpl() {
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(this.group, runnable, "ic2-poolthread-" + number.getAndIncrement(), 0L);
            thread.setDaemon(true);
            thread.setPriority(5);
            return thread;
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

