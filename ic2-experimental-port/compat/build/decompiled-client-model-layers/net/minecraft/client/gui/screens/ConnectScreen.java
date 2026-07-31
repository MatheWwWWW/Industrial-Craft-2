/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerNameResolver;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.world.entity.player.ProfilePublicKey;
import org.slf4j.Logger;

public class ConnectScreen
extends Screen {
    private static final AtomicInteger f_95682_ = new AtomicInteger(0);
    static final Logger f_95683_ = LogUtils.getLogger();
    private static final long f_169261_ = 2000L;
    public static final Component f_169260_ = Component.m_237110_("disconnect.genericReason", Component.m_237115_("disconnect.unknownHost"));
    @Nullable
    volatile Connection f_95684_;
    volatile boolean f_95685_;
    final Screen f_95686_;
    private Component f_95687_ = Component.m_237115_("connect.connecting");
    private long f_95688_ = -1L;

    private ConnectScreen(Screen p_169263_) {
        super(GameNarrator.f_93310_);
        this.f_95686_ = p_169263_;
    }

    public static void m_169267_(Screen p_169268_, Minecraft p_169269_, ServerAddress p_169270_, @Nullable ServerData p_169271_) {
        ConnectScreen $$4 = new ConnectScreen(p_169268_);
        p_169269_.m_91399_();
        p_169269_.m_193588_();
        p_169269_.m_91158_(p_169271_);
        p_169269_.m_91152_($$4);
        $$4.m_169264_(p_169269_, p_169270_);
    }

    private void m_169264_(final Minecraft p_169265_, final ServerAddress p_169266_) {
        final CompletableFuture<Optional<ProfilePublicKey.Data>> $$2 = p_169265_.m_231465_().m_243369_();
        f_95683_.info("Connecting to {}, {}", (Object)p_169266_.m_171863_(), (Object)p_169266_.m_171866_());
        Thread $$3 = new Thread("Server Connector #" + f_95682_.incrementAndGet()){

            @Override
            public void run() {
                InetSocketAddress $$0 = null;
                try {
                    if (ConnectScreen.this.f_95685_) {
                        return;
                    }
                    Optional<InetSocketAddress> $$1 = ServerNameResolver.f_171881_.m_171890_(p_169266_).map(ResolvedServerAddress::m_142641_);
                    if (ConnectScreen.this.f_95685_) {
                        return;
                    }
                    if (!$$1.isPresent()) {
                        p_169265_.execute(() -> p_169265_.m_91152_(new DisconnectedScreen(ConnectScreen.this.f_95686_, CommonComponents.f_130661_, f_169260_)));
                        return;
                    }
                    $$0 = $$1.get();
                    ConnectScreen.this.f_95684_ = Connection.m_178300_($$0, p_169265_.f_91066_.m_92175_());
                    ConnectScreen.this.f_95684_.m_129505_(new ClientHandshakePacketListenerImpl(ConnectScreen.this.f_95684_, p_169265_, ConnectScreen.this.f_95686_, ConnectScreen.this::m_95717_));
                    ConnectScreen.this.f_95684_.m_129512_(new ClientIntentionPacket($$0.getHostName(), $$0.getPort(), ConnectionProtocol.LOGIN));
                    ConnectScreen.this.f_95684_.m_129512_(new ServerboundHelloPacket(p_169265_.m_91094_().m_92546_(), (Optional)$$2.join(), Optional.ofNullable(p_169265_.m_91094_().m_240411_())));
                }
                catch (Exception $$22) {
                    Exception $$5;
                    if (ConnectScreen.this.f_95685_) {
                        return;
                    }
                    Throwable throwable = $$22.getCause();
                    if (throwable instanceof Exception) {
                        Exception $$3;
                        Exception $$4 = $$3 = (Exception)throwable;
                    } else {
                        $$5 = $$22;
                    }
                    f_95683_.error("Couldn't connect to server", (Throwable)$$22);
                    String $$6 = $$0 == null ? $$5.getMessage() : $$5.getMessage().replaceAll($$0.getHostName() + ":" + $$0.getPort(), "").replaceAll($$0.toString(), "");
                    p_169265_.execute(() -> p_169265_.m_91152_(new DisconnectedScreen(ConnectScreen.this.f_95686_, CommonComponents.f_130661_, Component.m_237110_("disconnect.genericReason", $$6))));
                }
            }
        };
        $$3.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_95683_));
        $$3.start();
    }

    private void m_95717_(Component p_95718_) {
        this.f_95687_ = p_95718_;
    }

    @Override
    public void m_86600_() {
        if (this.f_95684_ != null) {
            if (this.f_95684_.m_129536_()) {
                this.f_95684_.m_129483_();
            } else {
                this.f_95684_.m_129541_();
            }
        }
    }

    @Override
    public boolean m_6913_() {
        return false;
    }

    @Override
    protected void m_7856_() {
        this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 120 + 12, 200, 20, CommonComponents.f_130656_, p_95705_ -> {
            this.f_95685_ = true;
            if (this.f_95684_ != null) {
                this.f_95684_.m_129507_(Component.m_237115_("connect.aborted"));
            }
            this.f_96541_.m_91152_(this.f_95686_);
        }));
    }

    @Override
    public void m_6305_(PoseStack p_95700_, int p_95701_, int p_95702_, float p_95703_) {
        this.m_7333_(p_95700_);
        long $$4 = Util.m_137550_();
        if ($$4 - this.f_95688_ > 2000L) {
            this.f_95688_ = $$4;
            this.f_96541_.m_240477_().m_168785_(Component.m_237115_("narrator.joining"));
        }
        ConnectScreen.m_93215_(p_95700_, this.f_96547_, this.f_95687_, this.f_96543_ / 2, this.f_96544_ / 2 - 50, 0xFFFFFF);
        super.m_6305_(p_95700_, p_95701_, p_95702_, p_95703_);
    }
}

