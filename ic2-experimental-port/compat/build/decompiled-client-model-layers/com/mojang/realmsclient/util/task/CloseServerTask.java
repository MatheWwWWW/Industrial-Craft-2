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
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.util.task.LongRunningTask;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

public class CloseServerTask
extends LongRunningTask {
    private static final Logger f_202333_ = LogUtils.getLogger();
    private final RealmsServer f_90299_;
    private final RealmsConfigureWorldScreen f_90300_;

    public CloseServerTask(RealmsServer p_90302_, RealmsConfigureWorldScreen p_90303_) {
        this.f_90299_ = p_90302_;
        this.f_90300_ = p_90303_;
    }

    @Override
    public void run() {
        this.m_90409_(Component.m_237115_("mco.configure.world.closing"));
        RealmsClient $$0 = RealmsClient.m_87169_();
        for (int $$1 = 0; $$1 < 25; ++$$1) {
            if (this.m_90411_()) {
                return;
            }
            try {
                boolean $$2 = $$0.m_87242_(this.f_90299_.f_87473_);
                if (!$$2) continue;
                this.f_90300_.m_88413_();
                this.f_90299_.f_87477_ = RealmsServer.State.CLOSED;
                CloseServerTask.m_90405_(this.f_90300_);
                break;
            }
            catch (RetryCallException $$3) {
                if (this.m_90411_()) {
                    return;
                }
                CloseServerTask.m_167655_($$3.f_87787_);
                continue;
            }
            catch (Exception $$4) {
                if (this.m_90411_()) {
                    return;
                }
                f_202333_.error("Failed to close server", (Throwable)$$4);
                this.m_87791_("Failed to close the server");
            }
        }
    }
}

