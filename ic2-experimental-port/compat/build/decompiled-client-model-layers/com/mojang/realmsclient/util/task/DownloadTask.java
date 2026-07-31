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
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsDownloadLatestWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.util.task.LongRunningTask;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

public class DownloadTask
extends LongRunningTask {
    private static final Logger f_202335_ = LogUtils.getLogger();
    private final long f_90315_;
    private final int f_90316_;
    private final Screen f_90317_;
    private final String f_90318_;

    public DownloadTask(long p_90320_, int p_90321_, String p_90322_, Screen p_90323_) {
        this.f_90315_ = p_90320_;
        this.f_90316_ = p_90321_;
        this.f_90317_ = p_90323_;
        this.f_90318_ = p_90322_;
    }

    @Override
    public void run() {
        this.m_90409_(Component.m_237115_("mco.download.preparing"));
        RealmsClient $$0 = RealmsClient.m_87169_();
        for (int $$1 = 0; $$1 < 25; ++$$1) {
            try {
                if (this.m_90411_()) {
                    return;
                }
                WorldDownload $$2 = $$0.m_87209_(this.f_90315_, this.f_90316_);
                DownloadTask.m_167655_(1L);
                if (this.m_90411_()) {
                    return;
                }
                DownloadTask.m_90405_(new RealmsDownloadLatestWorldScreen(this.f_90317_, $$2, this.f_90318_, p_90325_ -> {}));
                return;
            }
            catch (RetryCallException $$3) {
                if (this.m_90411_()) {
                    return;
                }
                DownloadTask.m_167655_($$3.f_87787_);
                continue;
            }
            catch (RealmsServiceException $$4) {
                if (this.m_90411_()) {
                    return;
                }
                f_202335_.error("Couldn't download world data");
                DownloadTask.m_90405_(new RealmsGenericErrorScreen($$4, this.f_90317_));
                return;
            }
            catch (Exception $$5) {
                if (this.m_90411_()) {
                    return;
                }
                f_202335_.error("Couldn't download world data", (Throwable)$$5);
                this.m_87791_($$5.getLocalizedMessage());
                return;
            }
        }
    }
}

