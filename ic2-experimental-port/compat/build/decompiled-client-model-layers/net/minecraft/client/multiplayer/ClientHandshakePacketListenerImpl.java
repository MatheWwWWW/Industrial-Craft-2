/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.primitives.Longs
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.exceptions.AuthenticationException
 *  com.mojang.authlib.exceptions.AuthenticationUnavailableException
 *  com.mojang.authlib.exceptions.InsufficientPrivilegesException
 *  com.mojang.authlib.exceptions.InvalidCredentialsException
 *  com.mojang.authlib.exceptions.UserBannedException
 *  com.mojang.authlib.minecraft.MinecraftSessionService
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.multiplayer;

import com.google.common.primitives.Longs;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InsufficientPrivilegesException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.exceptions.UserBannedException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.logging.LogUtils;
import java.math.BigInteger;
import java.security.PublicKey;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.login.ClientLoginPacketListener;
import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
import net.minecraft.network.protocol.login.ClientboundHelloPacket;
import net.minecraft.network.protocol.login.ClientboundLoginCompressionPacket;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.network.protocol.login.ServerboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ServerboundKeyPacket;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.Crypt;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.Signer;
import org.slf4j.Logger;

public class ClientHandshakePacketListenerImpl
implements ClientLoginPacketListener {
    private static final Logger f_104518_ = LogUtils.getLogger();
    private final Minecraft f_104519_;
    @Nullable
    private final Screen f_104520_;
    private final Consumer<Component> f_104521_;
    private final Connection f_104522_;
    private GameProfile f_104523_;

    public ClientHandshakePacketListenerImpl(Connection p_104526_, Minecraft p_104527_, @Nullable Screen p_104528_, Consumer<Component> p_104529_) {
        this.f_104522_ = p_104526_;
        this.f_104519_ = p_104527_;
        this.f_104520_ = p_104528_;
        this.f_104521_ = p_104529_;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void m_7318_(ClientboundHelloPacket p_104549_) {
        void $$14;
        void $$13;
        void $$16;
        void $$15;
        try {
            SecretKey $$1 = Crypt.m_13578_();
            PublicKey $$2 = p_104549_.m_134794_();
            String $$3 = new BigInteger(Crypt.m_13590_(p_104549_.m_134791_(), $$2, $$1)).toString(16);
            Cipher $$4 = Crypt.m_13583_(2, $$1);
            Cipher $$5 = Crypt.m_13583_(1, $$1);
            byte[] $$6 = p_104549_.m_134795_();
            Signer $$7 = this.f_104519_.m_231465_().m_233775_();
            if ($$7 == null) {
                ServerboundKeyPacket $$8 = new ServerboundKeyPacket($$1, $$2, $$6);
            } else {
                long $$9 = Crypt.SaltSupplier.m_216113_();
                byte[] $$10 = $$7.m_216395_(p_233598_ -> {
                    p_233598_.m_216346_($$6);
                    p_233598_.m_216346_(Longs.toByteArray((long)$$9));
                });
                ServerboundKeyPacket $$11 = new ServerboundKeyPacket($$1, $$2, $$9, $$10);
            }
        }
        catch (Exception $$12) {
            throw new IllegalStateException("Protocol error", $$12);
        }
        this.f_104521_.accept(Component.m_237115_("connect.authorizing"));
        HttpUtil.f_13936_.submit(() -> this.m_233586_((String)$$15, (ServerboundKeyPacket)$$16, (Cipher)$$13, (Cipher)$$14));
    }

    @Nullable
    private Component m_104531_(String p_104532_) {
        try {
            this.m_104554_().joinServer(this.f_104519_.m_91094_().m_92548_(), this.f_104519_.m_91094_().m_92547_(), p_104532_);
        }
        catch (AuthenticationUnavailableException $$1) {
            return Component.m_237110_("disconnect.loginFailedInfo", Component.m_237115_("disconnect.loginFailedInfo.serversUnavailable"));
        }
        catch (InvalidCredentialsException $$2) {
            return Component.m_237110_("disconnect.loginFailedInfo", Component.m_237115_("disconnect.loginFailedInfo.invalidSession"));
        }
        catch (InsufficientPrivilegesException $$3) {
            return Component.m_237110_("disconnect.loginFailedInfo", Component.m_237115_("disconnect.loginFailedInfo.insufficientPrivileges"));
        }
        catch (UserBannedException $$4) {
            return Component.m_237110_("disconnect.loginFailedInfo", Component.m_237115_("disconnect.loginFailedInfo.userBanned"));
        }
        catch (AuthenticationException $$5) {
            return Component.m_237110_("disconnect.loginFailedInfo", $$5.getMessage());
        }
        return null;
    }

    private MinecraftSessionService m_104554_() {
        return this.f_104519_.m_91108_();
    }

    @Override
    public void m_7056_(ClientboundGameProfilePacket p_104547_) {
        this.f_104521_.accept(Component.m_237115_("connect.joining"));
        this.f_104523_ = p_104547_.m_134774_();
        this.f_104522_.m_129498_(ConnectionProtocol.PLAY);
        this.f_104522_.m_129505_(new ClientPacketListener(this.f_104519_, this.f_104520_, this.f_104522_, this.f_104523_, this.f_104519_.m_193590_()));
    }

    @Override
    public void m_7026_(Component p_104543_) {
        if (this.f_104520_ != null && this.f_104520_ instanceof RealmsScreen) {
            this.f_104519_.m_91152_(new DisconnectedRealmsScreen(this.f_104520_, CommonComponents.f_130661_, p_104543_));
        } else {
            this.f_104519_.m_91152_(new DisconnectedScreen(this.f_104520_, CommonComponents.f_130661_, p_104543_));
        }
    }

    @Override
    public Connection m_6198_() {
        return this.f_104522_;
    }

    @Override
    public void m_5800_(ClientboundLoginDisconnectPacket p_104553_) {
        this.f_104522_.m_129507_(p_104553_.m_134819_());
    }

    @Override
    public void m_5693_(ClientboundLoginCompressionPacket p_104551_) {
        if (!this.f_104522_.m_129531_()) {
            this.f_104522_.m_129484_(p_104551_.m_134806_(), false);
        }
    }

    @Override
    public void m_7254_(ClientboundCustomQueryPacket p_104545_) {
        this.f_104521_.accept(Component.m_237115_("connect.negotiating"));
        this.f_104522_.m_129512_(new ServerboundCustomQueryPacket(p_104545_.m_134755_(), null));
    }

    private /* synthetic */ void m_233586_(String p_233587_, ServerboundKeyPacket p_233588_, Cipher p_233589_, Cipher p_233590_) {
        Component $$4 = this.m_104531_(p_233587_);
        if ($$4 != null) {
            if (this.f_104519_.m_91089_() != null && this.f_104519_.m_91089_().m_105389_()) {
                f_104518_.warn($$4.getString());
            } else {
                this.f_104522_.m_129507_($$4);
                return;
            }
        }
        this.f_104521_.accept(Component.m_237115_("connect.encrypting"));
        this.f_104522_.m_243124_(p_233588_, PacketSendListener.m_243092_(() -> this.f_104522_.m_129495_(p_233589_, p_233590_)));
    }
}

