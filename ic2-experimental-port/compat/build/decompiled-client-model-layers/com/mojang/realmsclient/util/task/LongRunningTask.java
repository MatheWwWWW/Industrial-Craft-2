/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.util.task;

import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.gui.ErrorCallback;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

public abstract class LongRunningTask
implements ErrorCallback,
Runnable {
    protected static final int f_167654_ = 25;
    private static final Logger f_90394_ = LogUtils.getLogger();
    protected RealmsLongRunningMcoTaskScreen f_90395_;

    protected static void m_167655_(long p_167656_) {
        try {
            Thread.sleep(p_167656_ * 1000L);
        }
        catch (InterruptedException $$1) {
            Thread.currentThread().interrupt();
            f_90394_.error("", (Throwable)$$1);
        }
    }

    public static void m_90405_(Screen p_90406_) {
        Minecraft $$1 = Minecraft.m_91087_();
        $$1.execute(() -> $$1.m_91152_(p_90406_));
    }

    public void m_90400_(RealmsLongRunningMcoTaskScreen p_90401_) {
        this.f_90395_ = p_90401_;
    }

    @Override
    public void m_5673_(Component p_90408_) {
        this.f_90395_.m_5673_(p_90408_);
    }

    public void m_90409_(Component p_90410_) {
        this.f_90395_.m_88796_(p_90410_);
    }

    public boolean m_90411_() {
        return this.f_90395_.m_88779_();
    }

    public void m_5519_() {
    }

    public void m_90412_() {
    }

    public void m_5520_() {
    }
}

