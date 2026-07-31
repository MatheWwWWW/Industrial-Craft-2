/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.compat;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory
implements ThreadFactory {
    AtomicInteger threadNumber = new AtomicInteger(1);
    String name;
    ThreadGroup group;

    public NamedThreadFactory(String name) {
        this.name = name;
        this.group = Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(this.group, r, this.name + this.threadNumber.getAndIncrement(), 0L);
    }
}

