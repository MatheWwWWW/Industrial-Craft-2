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
import com.mojang.realmsclient.dto.Backup;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.util.task.LongRunningTask;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

public class RestoreTask
extends LongRunningTask {
    private static final Logger f_202346_ = LogUtils.getLogger();
    private final Backup f_90439_;
    private final long f_90440_;
    private final RealmsConfigureWorldScreen f_90441_;

    public RestoreTask(Backup p_90443_, long p_90444_, RealmsConfigureWorldScreen p_90445_) {
        this.f_90439_ = p_90443_;
        this.f_90440_ = p_90444_;
        this.f_90441_ = p_90445_;
    }

    @Override
    public void run() {
        this.m_90409_(Component.m_237115_("mco.backup.restoring"));
        RealmsClient $$0 = RealmsClient.m_87169_();
        for (int $$1 = 0; $$1 < 25; ++$$1) {
            try {
                if (this.m_90411_()) {
                    return;
                }
                $$0.m_87224_(this.f_90440_, this.f_90439_.f_87389_);
                RestoreTask.m_167655_(1L);
                if (this.m_90411_()) {
                    return;
                }
                RestoreTask.m_90405_(this.f_90441_.m_88486_());
                return;
            }
            catch (RetryCallException $$2) {
                if (this.m_90411_()) {
                    return;
                }
                RestoreTask.m_167655_($$2.f_87787_);
                continue;
            }
            catch (RealmsServiceException $$3) {
                if (this.m_90411_()) {
                    return;
                }
                f_202346_.error("Couldn't restore backup", (Throwable)$$3);
                RestoreTask.m_90405_(new RealmsGenericErrorScreen($$3, (Screen)this.f_90441_));
                return;
            }
            catch (Exception $$4) {
                if (this.m_90411_()) {
                    return;
                }
                f_202346_.error("Couldn't restore backup", (Throwable)$$4);
                this.m_87791_($$4.getLocalizedMessage());
                return;
            }
        }
    }
}

