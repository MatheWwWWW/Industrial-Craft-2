/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.util.thread;

import com.mojang.logging.LogUtils;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;

public class NamedThreadFactory
implements ThreadFactory {
    private static final Logger f_146340_ = LogUtils.getLogger();
    private final ThreadGroup f_146341_;
    private final AtomicInteger f_146342_ = new AtomicInteger(1);
    private final String f_146343_;

    public NamedThreadFactory(String p_146346_) {
        SecurityManager $$1 = System.getSecurityManager();
        this.f_146341_ = $$1 != null ? $$1.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.f_146343_ = p_146346_ + "-";
    }

    @Override
    public Thread newThread(Runnable p_146352_) {
        Thread $$1 = new Thread(this.f_146341_, p_146352_, this.f_146343_ + this.f_146342_.getAndIncrement(), 0L);
        $$1.setUncaughtExceptionHandler((p_146349_, p_146350_) -> {
            f_146340_.error("Caught exception in thread {} from {}", (Object)p_146349_, (Object)p_146352_);
            f_146340_.error("", p_146350_);
        });
        if ($$1.getPriority() != 5) {
            $$1.setPriority(5);
        }
        return $$1;
    }
}

