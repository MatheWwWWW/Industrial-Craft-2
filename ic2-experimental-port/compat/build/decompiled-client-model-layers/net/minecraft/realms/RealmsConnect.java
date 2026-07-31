/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.realms;

import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.dto.RealmsServer;
import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.world.entity.player.ProfilePublicKey;
import org.slf4j.Logger;

public class RealmsConnect {
    static final Logger f_120687_ = LogUtils.getLogger();
    final Screen f_120688_;
    volatile boolean f_120689_;
    @Nullable
    Connection f_120690_;

    public RealmsConnect(Screen p_120693_) {
        this.f_120688_ = p_120693_;
    }

    public void m_175031_(final RealmsServer p_175032_, ServerAddress p_175033_) {
        final Minecraft $$2 = Minecraft.m_91087_();
        $$2.m_91372_(true);
        $$2.m_193588_();
        $$2.m_240477_().m_168785_(Component.m_237115_("mco.connect.success"));
        final CompletableFuture<Optional<ProfilePublicKey.Data>> $$3 = $$2.m_231465_().m_243369_();
        final String $$4 = p_175033_.m_171863_();
        final int $$5 = p_175033_.m_171866_();
        new Thread("Realms-connect-task"){

            @Override
            public void run() {
                InetSocketAddress $$0 = null;
                try {
                    $$0 = new InetSocketAddress($$4, $$5);
                    if (RealmsConnect.this.f_120689_) {
                        return;
                    }
                    RealmsConnect.this.f_120690_ = Connection.m_178300_($$0, $$2.f_91066_.m_92175_());
                    if (RealmsConnect.this.f_120689_) {
                        return;
                    }
                    RealmsConnect.this.f_120690_.m_129505_(new ClientHandshakePacketListenerImpl(RealmsConnect.this.f_120690_, $$2, RealmsConnect.this.f_120688_, p_120726_ -> {}));
                    if (RealmsConnect.this.f_120689_) {
                        return;
                    }
                    RealmsConnect.this.f_120690_.m_129512_(new ClientIntentionPacket($$4, $$5, ConnectionProtocol.LOGIN));
                    if (RealmsConnect.this.f_120689_) {
                        return;
                    }
                    String $$1 = $$2.m_91094_().m_92546_();
                    UUID $$22 = $$2.m_91094_().m_240411_();
                    RealmsConnect.this.f_120690_.m_129512_(new ServerboundHelloPacket($$1, (Optional)$$3.join(), Optional.ofNullable($$22)));
                    $$2.m_239026_(p_175032_, $$4);
                }
                catch (Exception $$32) {
                    $$2.m_91100_().m_235009_();
                    if (RealmsConnect.this.f_120689_) {
                        return;
                    }
                    f_120687_.error("Couldn't connect to world", (Throwable)$$32);
                    String $$42 = $$32.toString();
                    if ($$0 != null) {
                        String $$52 = $$0 + ":" + $$5;
                        $$42 = $$42.replaceAll($$52, "");
                    }
                    DisconnectedRealmsScreen $$6 = new DisconnectedRealmsScreen(RealmsConnect.this.f_120688_, CommonComponents.f_130661_, Component.m_237110_("disconnect.genericReason", $$42));
                    $$2.execute(() -> $$2.m_91152_($$6));
                }
            }
        }.start();
    }

    public void m_120694_() {
        this.f_120689_ = true;
        if (this.f_120690_ != null && this.f_120690_.m_129536_()) {
            this.f_120690_.m_129507_(Component.m_237115_("disconnect.genericReason"));
            this.f_120690_.m_129541_();
        }
    }

    public void m_120704_() {
        if (this.f_120690_ != null) {
            if (this.f_120690_.m_129536_()) {
                this.f_120690_.m_129483_();
            } else {
                this.f_120690_.m_129541_();
            }
        }
    }
}

