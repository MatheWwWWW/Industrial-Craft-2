/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.thread;

import net.minecraft.util.thread.BlockableEventLoop;

public abstract class ReentrantBlockableEventLoop<R extends Runnable>
extends BlockableEventLoop<R> {
    private int f_18763_;

    public ReentrantBlockableEventLoop(String p_18765_) {
        super(p_18765_);
    }

    @Override
    public boolean m_5660_() {
        return this.m_18767_() || super.m_5660_();
    }

    protected boolean m_18767_() {
        return this.f_18763_ != 0;
    }

    @Override
    public void m_6367_(R p_18769_) {
        ++this.f_18763_;
        try {
            super.m_6367_(p_18769_);
        }
        finally {
            --this.f_18763_;
        }
    }
}

