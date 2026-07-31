/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.util.task;

import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.util.task.LongRunningTask;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

public class OpenServerTask
extends LongRunningTask {
    private static final Logger f_202342_ = LogUtils.getLogger();
    private final RealmsServer f_90413_;
    private final Screen f_90414_;
    private final boolean f_90415_;
    private final RealmsMainScreen f_90416_;
    private final Minecraft f_181342_;

    public OpenServerTask(RealmsServer p_181344_, Screen p_181345_, RealmsMainScreen p_181346_, boolean p_181347_, Minecraft p_181348_) {
        this.f_90413_ = p_181344_;
        this.f_90414_ = p_181345_;
        this.f_90415_ = p_181347_;
        this.f_90416_ = p_181346_;
        this.f_181342_ = p_181348_;
    }

    @Override
    public void run() {
        this.m_90409_(Component.m_237115_("mco.configure.world.opening"));
        RealmsClient $$0 = RealmsClient.m_87169_();
        for (int $$1 = 0; $$1 < 25; ++$$1) {
            if (this.m_90411_()) {
                return;
            }
            try {
                boolean $$2 = $$0.m_87236_(this.f_90413_.f_87473_);
                if (!$$2) continue;
                this.f_181342_.execute(() -> {
                    if (this.f_90414_ instanceof RealmsConfigureWorldScreen) {
                        ((RealmsConfigureWorldScreen)this.f_90414_).m_88413_();
                    }
                    this.f_90413_.f_87477_ = RealmsServer.State.OPEN;
                    if (this.f_90415_) {
                        this.f_90416_.m_86515_(this.f_90413_, this.f_90414_);
                    } else {
                        this.f_181342_.m_91152_(this.f_90414_);
                    }
                });
                break;
            }
            catch (RetryCallException $$3) {
                if (this.m_90411_()) {
                    return;
                }
                OpenServerTask.m_167655_($$3.f_87787_);
                continue;
            }
            catch (Exception $$4) {
                if (this.m_90411_()) {
                    return;
                }
                f_202342_.error("Failed to open server", (Throwable)$$4);
                this.m_87791_("Failed to open the server");
            }
        }
    }
}

