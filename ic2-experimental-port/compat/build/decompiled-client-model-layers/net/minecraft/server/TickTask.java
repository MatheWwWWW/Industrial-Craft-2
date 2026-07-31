/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server;

public class TickTask
implements Runnable {
    private final int f_136249_;
    private final Runnable f_136250_;

    public TickTask(int p_136252_, Runnable p_136253_) {
        this.f_136249_ = p_136252_;
        this.f_136250_ = p_136253_;
    }

    public int m_136254_() {
        return this.f_136249_;
    }

    @Override
    public void run() {
        this.f_136250_.run();
    }
}

