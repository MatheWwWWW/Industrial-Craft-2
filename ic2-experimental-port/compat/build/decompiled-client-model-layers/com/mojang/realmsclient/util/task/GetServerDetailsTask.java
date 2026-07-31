/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.booleans.BooleanConsumer
 *  org.slf4j.Logger
 */
package com.mojang.realmsclient.util.task;

import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerAddress;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.gui.screens.RealmsBrokenWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.gui.screens.RealmsTermsScreen;
import com.mojang.realmsclient.util.task.ConnectTask;
import com.mojang.realmsclient.util.task.LongRunningTask;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.net.URL;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

public class GetServerDetailsTask
extends LongRunningTask {
    private static final Logger f_202337_ = LogUtils.getLogger();
    private final RealmsServer f_90327_;
    private final Screen f_90328_;
    private final RealmsMainScreen f_90329_;
    private final ReentrantLock f_90330_;

    public GetServerDetailsTask(RealmsMainScreen p_90332_, Screen p_90333_, RealmsServer p_90334_, ReentrantLock p_90335_) {
        this.f_90328_ = p_90333_;
        this.f_90329_ = p_90332_;
        this.f_90327_ = p_90334_;
        this.f_90330_ = p_90335_;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void run() {
        void $$6;
        this.m_90409_(Component.m_237115_("mco.connect.connecting"));
        try {
            RealmsServerAddress $$0 = this.m_167653_();
        }
        catch (CancellationException $$1) {
            f_202337_.info("User aborted connecting to realms");
            return;
        }
        catch (RealmsServiceException $$2) {
            switch ($$2.m_200945_(-1)) {
                case 6002: {
                    GetServerDetailsTask.m_90405_(new RealmsTermsScreen(this.f_90328_, this.f_90329_, this.f_90327_));
                    return;
                }
                case 6006: {
                    boolean $$3 = this.f_90327_.f_87479_.equals(Minecraft.m_91087_().m_91094_().m_92545_());
                    GetServerDetailsTask.m_90405_($$3 ? new RealmsBrokenWorldScreen(this.f_90328_, this.f_90329_, this.f_90327_.f_87473_, this.f_90327_.f_87485_ == RealmsServer.WorldType.MINIGAME) : new RealmsGenericErrorScreen(Component.m_237115_("mco.brokenworld.nonowner.title"), Component.m_237115_("mco.brokenworld.nonowner.error"), this.f_90328_));
                    return;
                }
            }
            this.m_87791_($$2.toString());
            f_202337_.error("Couldn't connect to world", (Throwable)$$2);
            return;
        }
        catch (TimeoutException $$4) {
            this.m_5673_(Component.m_237115_("mco.errorMessage.connectionFailure"));
            return;
        }
        catch (Exception $$5) {
            f_202337_.error("Couldn't connect to world", (Throwable)$$5);
            this.m_87791_($$5.getLocalizedMessage());
            return;
        }
        boolean $$7 = $$6.f_87566_ != null && $$6.f_87567_ != null;
        RealmsLongRunningMcoTaskScreen $$8 = $$7 ? this.m_167639_((RealmsServerAddress)$$6, this::m_167637_) : this.m_167637_((RealmsServerAddress)$$6);
        GetServerDetailsTask.m_90405_($$8);
    }

    private RealmsServerAddress m_167653_() throws RealmsServiceException, TimeoutException, CancellationException {
        RealmsClient $$0 = RealmsClient.m_87169_();
        for (int $$1 = 0; $$1 < 40; ++$$1) {
            if (this.m_90411_()) {
                throw new CancellationException();
            }
            try {
                return $$0.m_87207_(this.f_90327_.f_87473_);
            }
            catch (RetryCallException $$2) {
                GetServerDetailsTask.m_167655_($$2.f_87787_);
                continue;
            }
        }
        throw new TimeoutException();
    }

    public RealmsLongRunningMcoTaskScreen m_167637_(RealmsServerAddress p_167638_) {
        return new RealmsLongRunningMcoTaskScreen(this.f_90328_, new ConnectTask(this.f_90328_, this.f_90327_, p_167638_));
    }

    private RealmsLongConfirmationScreen m_167639_(RealmsServerAddress p_167640_, Function<RealmsServerAddress, Screen> p_167641_) {
        BooleanConsumer $$2 = p_167645_ -> {
            try {
                if (!p_167645_) {
                    GetServerDetailsTask.m_90405_(this.f_90328_);
                    return;
                }
                ((CompletableFuture)this.m_167651_(p_167640_).thenRun(() -> GetServerDetailsTask.m_90405_((Screen)p_167641_.apply(p_167640_)))).exceptionally(p_202341_ -> {
                    Minecraft.m_91087_().m_91100_().m_235009_();
                    f_202337_.error("Failed to download resource pack from {}", (Object)p_167640_, p_202341_);
                    GetServerDetailsTask.m_90405_(new RealmsGenericErrorScreen(Component.m_237113_("Failed to download resource pack!"), this.f_90328_));
                    return null;
                });
            }
            finally {
                if (this.f_90330_.isHeldByCurrentThread()) {
                    this.f_90330_.unlock();
                }
            }
        };
        return new RealmsLongConfirmationScreen($$2, RealmsLongConfirmationScreen.Type.Info, Component.m_237115_("mco.configure.world.resourcepack.question.line1"), Component.m_237115_("mco.configure.world.resourcepack.question.line2"), true);
    }

    private CompletableFuture<?> m_167651_(RealmsServerAddress p_167652_) {
        try {
            return Minecraft.m_91087_().m_91100_().m_235005_(new URL(p_167652_.f_87566_), p_167652_.f_87567_, false);
        }
        catch (Exception $$1) {
            CompletableFuture $$2 = new CompletableFuture();
            $$2.completeExceptionally($$1);
            return $$2;
        }
    }
}

