/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client;

import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.exception.RealmsServiceException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.Realms32bitWarningScreen;
import org.slf4j.Logger;

public class Realms32BitWarningStatus {
    private static final Logger f_232198_ = LogUtils.getLogger();
    private final Minecraft f_232199_;
    @Nullable
    private CompletableFuture<Boolean> f_232200_;
    private boolean f_232201_;

    public Realms32BitWarningStatus(Minecraft p_232204_) {
        this.f_232199_ = p_232204_;
    }

    public void m_232208_(Screen p_232209_) {
        if (!this.f_232199_.m_91103_() && !this.f_232199_.f_91066_.f_210816_ && !this.f_232201_ && this.m_232205_().booleanValue()) {
            this.f_232199_.m_91152_(new Realms32bitWarningScreen(p_232209_));
            this.f_232201_ = true;
        }
    }

    private Boolean m_232205_() {
        if (this.f_232200_ == null) {
            this.f_232200_ = CompletableFuture.supplyAsync(this::m_232210_, Util.m_183991_());
        }
        try {
            return this.f_232200_.getNow(false);
        }
        catch (CompletionException $$0) {
            f_232198_.warn("Failed to retrieve realms subscriptions", (Throwable)$$0);
            this.f_232201_ = true;
            return false;
        }
    }

    private boolean m_232210_() {
        try {
            return RealmsClient.m_239151_((Minecraft)this.f_232199_).m_87235_().f_87573_.stream().anyMatch(p_232207_ -> p_232207_.f_87479_ != null && !p_232207_.f_87482_ && p_232207_.f_87479_.equals(this.f_232199_.m_91094_().m_92545_()));
        }
        catch (RealmsServiceException $$0) {
            return false;
        }
    }
}

