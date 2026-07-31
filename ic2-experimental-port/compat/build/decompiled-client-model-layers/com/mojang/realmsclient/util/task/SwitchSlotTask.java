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
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.util.task.LongRunningTask;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

public class SwitchSlotTask
extends LongRunningTask {
    private static final Logger f_202350_ = LogUtils.getLogger();
    private final long f_90455_;
    private final int f_90456_;
    private final Runnable f_90457_;

    public SwitchSlotTask(long p_90459_, int p_90460_, Runnable p_90461_) {
        this.f_90455_ = p_90459_;
        this.f_90456_ = p_90460_;
        this.f_90457_ = p_90461_;
    }

    @Override
    public void run() {
        RealmsClient $$0 = RealmsClient.m_87169_();
        this.m_90409_(Component.m_237115_("mco.minigame.world.slot.screen.title"));
        for (int $$1 = 0; $$1 < 25; ++$$1) {
            try {
                if (this.m_90411_()) {
                    return;
                }
                if (!$$0.m_87176_(this.f_90455_, this.f_90456_)) continue;
                this.f_90457_.run();
                break;
            }
            catch (RetryCallException $$2) {
                if (this.m_90411_()) {
                    return;
                }
                SwitchSlotTask.m_167655_($$2.f_87787_);
                continue;
            }
            catch (Exception $$3) {
                if (this.m_90411_()) {
                    return;
                }
                f_202350_.error("Couldn't switch world!");
                this.m_87791_($$3.toString());
            }
        }
    }
}

