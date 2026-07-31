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
import com.mojang.realmsclient.dto.WorldTemplate;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.util.task.LongRunningTask;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

public class SwitchMinigameTask
extends LongRunningTask {
    private static final Logger f_202348_ = LogUtils.getLogger();
    private final long f_90447_;
    private final WorldTemplate f_90448_;
    private final RealmsConfigureWorldScreen f_90449_;

    public SwitchMinigameTask(long p_90451_, WorldTemplate p_90452_, RealmsConfigureWorldScreen p_90453_) {
        this.f_90447_ = p_90451_;
        this.f_90448_ = p_90452_;
        this.f_90449_ = p_90453_;
    }

    @Override
    public void run() {
        RealmsClient $$0 = RealmsClient.m_87169_();
        this.m_90409_(Component.m_237115_("mco.minigame.world.starting.screen.title"));
        for (int $$1 = 0; $$1 < 25; ++$$1) {
            try {
                if (this.m_90411_()) {
                    return;
                }
                if (!$$0.m_87232_(this.f_90447_, this.f_90448_.f_87726_).booleanValue()) continue;
                SwitchMinigameTask.m_90405_(this.f_90449_);
                break;
            }
            catch (RetryCallException $$2) {
                if (this.m_90411_()) {
                    return;
                }
                SwitchMinigameTask.m_167655_($$2.f_87787_);
                continue;
            }
            catch (Exception $$3) {
                if (this.m_90411_()) {
                    return;
                }
                f_202348_.error("Couldn't start mini game!");
                this.m_87791_($$3.toString());
            }
        }
    }
}

