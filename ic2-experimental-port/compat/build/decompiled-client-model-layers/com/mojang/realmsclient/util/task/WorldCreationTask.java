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
import com.mojang.realmsclient.util.task.LongRunningTask;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

public class WorldCreationTask
extends LongRunningTask {
    private static final Logger f_202352_ = LogUtils.getLogger();
    private final String f_90463_;
    private final String f_90464_;
    private final long f_90465_;
    private final Screen f_90466_;

    public WorldCreationTask(long p_90468_, String p_90469_, String p_90470_, Screen p_90471_) {
        this.f_90465_ = p_90468_;
        this.f_90463_ = p_90469_;
        this.f_90464_ = p_90470_;
        this.f_90466_ = p_90471_;
    }

    @Override
    public void run() {
        this.m_90409_(Component.m_237115_("mco.create.world.wait"));
        RealmsClient $$0 = RealmsClient.m_87169_();
        try {
            $$0.m_87191_(this.f_90465_, this.f_90463_, this.f_90464_);
            WorldCreationTask.m_90405_(this.f_90466_);
        }
        catch (RealmsServiceException $$1) {
            f_202352_.error("Couldn't create world");
            this.m_87791_($$1.toString());
        }
        catch (Exception $$2) {
            f_202352_.error("Could not create world");
            this.m_87791_($$2.getLocalizedMessage());
        }
    }
}

