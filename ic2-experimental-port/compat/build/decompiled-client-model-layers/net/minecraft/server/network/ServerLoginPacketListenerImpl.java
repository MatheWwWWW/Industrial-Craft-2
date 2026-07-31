/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.primitives.Ints
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.exceptions.AuthenticationUnavailableException
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 *  org.slf4j.Logger
 */
package net.minecraft.server.network;

import com.google.common.primitives.Ints;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.logging.LogUtils;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.PrivateKey;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.TickablePacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
import net.minecraft.network.protocol.login.ClientboundHelloPacket;
import net.minecraft.network.protocol.login.ClientboundLoginCompressionPacket;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.network.protocol.login.ServerLoginPacketListener;
import net.minecraft.network.protocol.login.ServerboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.network.protocol.login.ServerboundKeyPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;
import net.minecraft.util.RandomSource;
import net.minecraft.util.SignatureValidator;
import net.minecraft.world.entity.player.ProfilePublicKey;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

public class ServerLoginPacketListenerImpl
implements TickablePacketListener,
ServerLoginPacketListener {
    private static final AtomicInteger f_10014_ = new AtomicInteger(0);
    static final Logger f_10015_ = LogUtils.getLogger();
    private static final int f_143698_ = 600;
    private static final RandomSource f_10016_ = RandomSource.m_216327_();
    private final byte[] f_10017_;
    final MinecraftServer f_10018_;
    public final Connection f_10013_;
    State f_10019_ = State.HELLO;
    private int f_10020_;
    @Nullable
    GameProfile f_10021_;
    private final String f_10022_ = "";
    @Nullable
    private ServerPlayer f_10024_;
    @Nullable
    private ProfilePublicKey.Data f_240234_;

    public ServerLoginPacketListenerImpl(MinecraftServer p_10027_, Connection p_10028_) {
        this.f_10018_ = p_10027_;
        this.f_10013_ = p_10028_;
        this.f_10017_ = Ints.toByteArray((int)f_10016_.m_188502_());
    }

    @Override
    public void m_9933_() {
        ServerPlayer $$0;
        if (this.f_10019_ == State.READY_TO_ACCEPT) {
            this.m_10055_();
        } else if (this.f_10019_ == State.DELAY_ACCEPT && ($$0 = this.f_10018_.m_6846_().m_11259_(this.f_10021_.getId())) == null) {
            this.f_10019_ = State.READY_TO_ACCEPT;
            this.m_143699_(this.f_10024_);
            this.f_10024_ = null;
        }
        if (this.f_10020_++ == 600) {
            this.m_10053_(Component.m_237115_("multiplayer.disconnect.slow_login"));
        }
    }

    @Override
    public Connection m_6198_() {
        return this.f_10013_;
    }

    public void m_10053_(Component p_10054_) {
        try {
            f_10015_.info("Disconnecting {}: {}", (Object)this.m_10056_(), (Object)p_10054_.getString());
            this.f_10013_.m_129512_(new ClientboundLoginDisconnectPacket(p_10054_));
            this.f_10013_.m_129507_(p_10054_);
        }
        catch (Exception $$1) {
            f_10015_.error("Error whilst disconnecting player", (Throwable)$$1);
        }
    }

    public void m_10055_() {
        ProfilePublicKey $$0;
        block11: {
            $$0 = null;
            if (!this.f_10021_.isComplete()) {
                this.f_10021_ = this.m_10038_(this.f_10021_);
            } else {
                try {
                    SignatureValidator $$1 = this.f_10018_.m_236739_();
                    $$0 = ServerLoginPacketListenerImpl.m_215256_(this.f_240234_, this.f_10021_.getId(), $$1, this.f_10018_.m_214005_());
                }
                catch (ProfilePublicKey.ValidationException $$2) {
                    f_10015_.error("Failed to validate profile key: {}", (Object)$$2.getMessage());
                    if (this.f_10013_.m_129531_()) break block11;
                    this.m_10053_($$2.m_237308_());
                    return;
                }
            }
        }
        Component $$3 = this.f_10018_.m_6846_().m_6418_(this.f_10013_.m_129523_(), this.f_10021_);
        if ($$3 != null) {
            this.m_10053_($$3);
        } else {
            this.f_10019_ = State.ACCEPTED;
            if (this.f_10018_.m_6328_() >= 0 && !this.f_10013_.m_129531_()) {
                this.f_10013_.m_243124_(new ClientboundLoginCompressionPacket(this.f_10018_.m_6328_()), PacketSendListener.m_243092_(() -> this.f_10013_.m_129484_(this.f_10018_.m_6328_(), true)));
            }
            this.f_10013_.m_129512_(new ClientboundGameProfilePacket(this.f_10021_));
            ServerPlayer $$4 = this.f_10018_.m_6846_().m_11259_(this.f_10021_.getId());
            try {
                ServerPlayer $$5 = this.f_10018_.m_6846_().m_215624_(this.f_10021_, $$0);
                if ($$4 != null) {
                    this.f_10019_ = State.DELAY_ACCEPT;
                    this.f_10024_ = $$5;
                } else {
                    this.m_143699_($$5);
                }
            }
            catch (Exception $$6) {
                f_10015_.error("Couldn't place player in world", (Throwable)$$6);
                MutableComponent $$7 = Component.m_237115_("multiplayer.disconnect.invalid_player_data");
                this.f_10013_.m_129512_(new ClientboundDisconnectPacket($$7));
                this.f_10013_.m_129507_($$7);
            }
        }
    }

    private void m_143699_(ServerPlayer p_143700_) {
        this.f_10018_.m_6846_().m_11261_(this.f_10013_, p_143700_);
    }

    @Override
    public void m_7026_(Component p_10043_) {
        f_10015_.info("{} lost connection: {}", (Object)this.m_10056_(), (Object)p_10043_.getString());
    }

    public String m_10056_() {
        if (this.f_10021_ != null) {
            return this.f_10021_ + " (" + this.f_10013_.m_129523_() + ")";
        }
        return String.valueOf(this.f_10013_.m_129523_());
    }

    @Nullable
    private static ProfilePublicKey m_215256_(@Nullable ProfilePublicKey.Data p_240244_, UUID p_240245_, SignatureValidator p_240246_, boolean p_240247_) throws ProfilePublicKey.ValidationException {
        if (p_240244_ == null) {
            if (p_240247_) {
                throw new ProfilePublicKey.ValidationException(ProfilePublicKey.f_243347_);
            }
            return null;
        }
        return ProfilePublicKey.m_243358_(p_240246_, p_240245_, p_240244_, Duration.ZERO);
    }

    @Override
    public void m_5990_(ServerboundHelloPacket p_10047_) {
        Validate.validState((this.f_10019_ == State.HELLO ? 1 : 0) != 0, (String)"Unexpected hello packet", (Object[])new Object[0]);
        Validate.validState((boolean)ServerLoginPacketListenerImpl.m_203792_(p_10047_.f_238040_()), (String)"Invalid characters in username", (Object[])new Object[0]);
        this.f_240234_ = p_10047_.f_238041_().orElse(null);
        GameProfile $$1 = this.f_10018_.m_236731_();
        if ($$1 != null && p_10047_.f_238040_().equalsIgnoreCase($$1.getName())) {
            this.f_10021_ = $$1;
            this.f_10019_ = State.READY_TO_ACCEPT;
            return;
        }
        this.f_10021_ = new GameProfile(null, p_10047_.f_238040_());
        if (this.f_10018_.m_129797_() && !this.f_10013_.m_129531_()) {
            this.f_10019_ = State.KEY;
            this.f_10013_.m_129512_(new ClientboundHelloPacket("", this.f_10018_.m_129790_().getPublic().getEncoded(), this.f_10017_));
        } else {
            this.f_10019_ = State.READY_TO_ACCEPT;
        }
    }

    public static boolean m_203792_(String p_203793_) {
        return p_203793_.chars().filter(p_203791_ -> p_203791_ <= 32 || p_203791_ >= 127).findAny().isEmpty();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void m_8072_(ServerboundKeyPacket p_10049_) {
        void $$8;
        Validate.validState((this.f_10019_ == State.KEY ? 1 : 0) != 0, (String)"Unexpected key packet", (Object[])new Object[0]);
        try {
            ProfilePublicKey $$2;
            PrivateKey $$1 = this.f_10018_.m_129790_().getPrivate();
            if (this.f_240234_ != null ? !p_10049_.m_238071_(this.f_10017_, $$2 = new ProfilePublicKey(this.f_240234_)) : !p_10049_.m_238074_(this.f_10017_, $$1)) {
                throw new IllegalStateException("Protocol error");
            }
            SecretKey $$3 = p_10049_.m_134859_($$1);
            Cipher $$4 = Crypt.m_13583_(2, $$3);
            Cipher $$5 = Crypt.m_13583_(1, $$3);
            String $$6 = new BigInteger(Crypt.m_13590_("", this.f_10018_.m_129790_().getPublic(), $$3)).toString(16);
            this.f_10019_ = State.AUTHENTICATING;
            this.f_10013_.m_129495_($$4, $$5);
        }
        catch (CryptException $$7) {
            throw new IllegalStateException("Protocol error", $$7);
        }
        Thread $$9 = new Thread("User Authenticator #" + f_10014_.incrementAndGet(), (String)$$8){
            final /* synthetic */ String f_10058_;
            {
                this.f_10058_ = string;
                super(p_10062_);
            }

            @Override
            public void run() {
                GameProfile $$0 = ServerLoginPacketListenerImpl.this.f_10021_;
                try {
                    ServerLoginPacketListenerImpl.this.f_10021_ = ServerLoginPacketListenerImpl.this.f_10018_.m_129925_().hasJoinedServer(new GameProfile(null, $$0.getName()), this.f_10058_, this.m_10064_());
                    if (ServerLoginPacketListenerImpl.this.f_10021_ != null) {
                        f_10015_.info("UUID of player {} is {}", (Object)ServerLoginPacketListenerImpl.this.f_10021_.getName(), (Object)ServerLoginPacketListenerImpl.this.f_10021_.getId());
                        ServerLoginPacketListenerImpl.this.f_10019_ = State.READY_TO_ACCEPT;
                    } else if (ServerLoginPacketListenerImpl.this.f_10018_.m_129792_()) {
                        f_10015_.warn("Failed to verify username but will let them in anyway!");
                        ServerLoginPacketListenerImpl.this.f_10021_ = $$0;
                        ServerLoginPacketListenerImpl.this.f_10019_ = State.READY_TO_ACCEPT;
                    } else {
                        ServerLoginPacketListenerImpl.this.m_10053_(Component.m_237115_("multiplayer.disconnect.unverified_username"));
                        f_10015_.error("Username '{}' tried to join with an invalid session", (Object)$$0.getName());
                    }
                }
                catch (AuthenticationUnavailableException $$1) {
                    if (ServerLoginPacketListenerImpl.this.f_10018_.m_129792_()) {
                        f_10015_.warn("Authentication servers are down but will let them in anyway!");
                        ServerLoginPacketListenerImpl.this.f_10021_ = $$0;
                        ServerLoginPacketListenerImpl.this.f_10019_ = State.READY_TO_ACCEPT;
                    }
                    ServerLoginPacketListenerImpl.this.m_10053_(Component.m_237115_("multiplayer.disconnect.authservers_down"));
                    f_10015_.error("Couldn't verify username because servers are unavailable");
                }
            }

            @Nullable
            private InetAddress m_10064_() {
                SocketAddress $$0 = ServerLoginPacketListenerImpl.this.f_10013_.m_129523_();
                return ServerLoginPacketListenerImpl.this.f_10018_.m_129798_() && $$0 instanceof InetSocketAddress ? ((InetSocketAddress)$$0).getAddress() : null;
            }
        };
        $$9.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_10015_));
        $$9.start();
    }

    @Override
    public void m_7223_(ServerboundCustomQueryPacket p_10045_) {
        this.m_10053_(Component.m_237115_("multiplayer.disconnect.unexpected_query_response"));
    }

    protected GameProfile m_10038_(GameProfile p_10039_) {
        UUID $$1 = UUIDUtil.m_235879_(p_10039_.getName());
        return new GameProfile($$1, p_10039_.getName());
    }

    static final class State
    extends Enum<State> {
        public static final /* enum */ State HELLO = new State();
        public static final /* enum */ State KEY = new State();
        public static final /* enum */ State AUTHENTICATING = new State();
        public static final /* enum */ State NEGOTIATING = new State();
        public static final /* enum */ State READY_TO_ACCEPT = new State();
        public static final /* enum */ State DELAY_ACCEPT = new State();
        public static final /* enum */ State ACCEPTED = new State();
        private static final /* synthetic */ State[] $VALUES;

        public static State[] values() {
            return (State[])$VALUES.clone();
        }

        public static State valueOf(String p_10079_) {
            return Enum.valueOf(State.class, p_10079_);
        }

        private static /* synthetic */ State[] m_143701_() {
            return new State[]{HELLO, KEY, AUTHENTICATING, NEGOTIATING, READY_TO_ACCEPT, DELAY_ACCEPT, ACCEPTED};
        }

        static {
            $VALUES = State.m_143701_();
        }
    }
}

