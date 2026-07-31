/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.rcon.thread;

import com.mojang.logging.LogUtils;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import net.minecraft.DefaultUncaughtExceptionHandlerWithName;
import org.slf4j.Logger;

public abstract class GenericThread
implements Runnable {
    private static final Logger f_11518_ = LogUtils.getLogger();
    private static final AtomicInteger f_11519_ = new AtomicInteger(0);
    private static final int f_144023_ = 5;
    protected volatile boolean f_11515_;
    protected final String f_11516_;
    @Nullable
    protected Thread f_11517_;

    protected GenericThread(String p_11522_) {
        this.f_11516_ = p_11522_;
    }

    public synchronized boolean m_7528_() {
        if (this.f_11515_) {
            return true;
        }
        this.f_11515_ = true;
        this.f_11517_ = new Thread((Runnable)this, this.f_11516_ + " #" + f_11519_.incrementAndGet());
        this.f_11517_.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandlerWithName(f_11518_));
        this.f_11517_.start();
        f_11518_.info("Thread {} started", (Object)this.f_11516_);
        return true;
    }

    public synchronized void m_7530_() {
        this.f_11515_ = false;
        if (null == this.f_11517_) {
            return;
        }
        int $$0 = 0;
        while (this.f_11517_.isAlive()) {
            try {
                this.f_11517_.join(1000L);
                if (++$$0 >= 5) {
                    f_11518_.warn("Waited {} seconds attempting force stop!", (Object)$$0);
                    continue;
                }
                if (!this.f_11517_.isAlive()) continue;
                f_11518_.warn("Thread {} ({}) failed to exit after {} second(s)", new Object[]{this, this.f_11517_.getState(), $$0, new Exception("Stack:")});
                this.f_11517_.interrupt();
            }
            catch (InterruptedException interruptedException) {}
        }
        f_11518_.info("Thread {} stopped", (Object)this.f_11516_);
        this.f_11517_ = null;
    }

    public boolean m_11523_() {
        return this.f_11515_;
    }
}

