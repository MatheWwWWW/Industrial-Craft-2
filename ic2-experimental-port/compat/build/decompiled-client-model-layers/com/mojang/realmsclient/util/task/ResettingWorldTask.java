/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.util.task;

import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.util.task.LongRunningTask;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

public abstract class ResettingWorldTask
extends LongRunningTask {
    private static final Logger f_202344_ = LogUtils.getLogger();
    private final long f_90427_;
    private final Component f_90428_;
    private final Runnable f_90429_;

    public ResettingWorldTask(long p_167676_, Component p_167677_, Runnable p_167678_) {
        this.f_90427_ = p_167676_;
        this.f_90428_ = p_167677_;
        this.f_90429_ = p_167678_;
    }

    protected abstract void m_142381_(RealmsClient var1, long var2) throws RealmsServiceException;

    @Override
    public void run() {
        RealmsClient $$0 = RealmsClient.m_87169_();
        this.m_90409_(this.f_90428_);
        for (int $$1 = 0; $$1 < 25; ++$$1) {
            try {
                if (this.m_90411_()) {
                    return;
                }
                this.m_142381_($$0, this.f_90427_);
                if (this.m_90411_()) {
                    return;
                }
                this.f_90429_.run();
                return;
            }
            catch (RetryCallException $$2) {
                if (this.m_90411_()) {
                    return;
                }
                ResettingWorldTask.m_167655_($$2.f_87787_);
                continue;
            }
            catch (Exception $$3) {
                if (this.m_90411_()) {
                    return;
                }
                f_202344_.error("Couldn't reset world");
                this.m_87791_($$3.toString());
                return;
            }
        }
    }
}

