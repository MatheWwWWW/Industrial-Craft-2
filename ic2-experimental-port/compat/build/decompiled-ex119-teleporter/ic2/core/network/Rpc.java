/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.network;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Rpc<V>
implements Future<V> {
    private final CountDownLatch latch = new CountDownLatch(1);
    private volatile boolean cancelled;
    private volatile V result;

    @Override
    public boolean cancel(boolean bl) {
        if (this.isDone()) {
            return false;
        }
        this.cancelled = true;
        this.latch.countDown();
        return true;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public boolean isDone() {
        return this.latch.getCount() == 0L;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        try {
            return this.get(-1L, TimeUnit.NANOSECONDS);
        }
        catch (TimeoutException timeoutException) {
            throw new RuntimeException(timeoutException);
        }
    }

    @Override
    public V get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        if (l < 0L) {
            this.latch.await();
        } else {
            boolean bl = this.latch.await(l, timeUnit);
            if (!bl) {
                throw new TimeoutException();
            }
        }
        if (this.cancelled) {
            throw new CancellationException();
        }
        return this.result;
    }

    public void finish(Object object) {
        this.result = object;
        this.latch.countDown();
    }
}

