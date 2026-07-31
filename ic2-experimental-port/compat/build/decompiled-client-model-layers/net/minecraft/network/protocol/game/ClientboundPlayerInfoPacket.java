/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.collect.Lists
 *  com.mojang.authlib.GameProfile
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.GameType;

public class ClientboundPlayerInfoPacket
implements Packet<ClientGamePacketListener> {
    private final Action f_132717_;
    private final List<PlayerUpdate> f_132718_;

    public ClientboundPlayerInfoPacket(Action p_132724_, ServerPlayer ... p_132725_) {
        this.f_132717_ = p_132724_;
        this.f_132718_ = Lists.newArrayListWithCapacity((int)p_132725_.length);
        for (ServerPlayer $$2 : p_132725_) {
            this.f_132718_.add(ClientboundPlayerInfoPacket.m_237772_($$2));
        }
    }

    public ClientboundPlayerInfoPacket(Action p_179083_, Collection<ServerPlayer> p_179084_) {
        this.f_132717_ = p_179083_;
        this.f_132718_ = Lists.newArrayListWithCapacity((int)p_179084_.size());
        for (ServerPlayer $$2 : p_179084_) {
            this.f_132718_.add(ClientboundPlayerInfoPacket.m_237772_($$2));
        }
    }

    public ClientboundPlayerInfoPacket(FriendlyByteBuf p_179081_) {
        this.f_132717_ = p_179081_.m_130066_(Action.class);
        this.f_132718_ = p_179081_.m_236845_(this.f_132717_::m_142553_);
    }

    private static PlayerUpdate m_237772_(ServerPlayer p_237773_) {
        ProfilePublicKey $$1 = p_237773_.m_219760_();
        ProfilePublicKey.Data $$2 = $$1 != null ? $$1.f_219781_() : null;
        return new PlayerUpdate(p_237773_.m_36316_(), p_237773_.f_8943_, p_237773_.f_8941_.m_9290_(), p_237773_.m_8957_(), $$2);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132734_) {
        p_132734_.m_130068_(this.f_132717_);
        p_132734_.m_236828_(this.f_132718_, this.f_132717_::m_142214_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132731_) {
        p_132731_.m_7039_(this);
    }

    public List<PlayerUpdate> m_132732_() {
        return this.f_132718_;
    }

    public Action m_132735_() {
        return this.f_132717_;
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object)this).add("action", (Object)this.f_132717_).add("entries", this.f_132718_).toString();
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static abstract class Action
    extends Enum<Action> {
        public static final /* enum */ Action ADD_PLAYER = new Action(){

            @Override
            protected PlayerUpdate m_142553_(FriendlyByteBuf p_179101_) {
                GameProfile $$1 = p_179101_.m_236875_();
                GameType $$2 = GameType.m_46393_(p_179101_.m_130242_());
                int $$3 = p_179101_.m_130242_();
                Component $$4 = (Component)p_179101_.m_236868_(FriendlyByteBuf::m_130238_);
                ProfilePublicKey.Data $$5 = (ProfilePublicKey.Data)p_179101_.m_236868_(ProfilePublicKey.Data::new);
                return new PlayerUpdate($$1, $$3, $$2, $$4, $$5);
            }

            @Override
            protected void m_142214_(FriendlyByteBuf p_179106_, PlayerUpdate p_179107_) {
                p_179106_.m_236803_(p_179107_.m_132763_());
                p_179106_.m_130130_(p_179107_.m_132765_().m_46392_());
                p_179106_.m_130130_(p_179107_.m_132764_());
                p_179106_.m_236821_(p_179107_.m_132766_(), FriendlyByteBuf::m_130083_);
                p_179106_.m_236821_(p_179107_.m_237784_(), (p_237775_, p_237776_) -> p_237776_.m_219815_((FriendlyByteBuf)((Object)p_237775_)));
            }
        };
        public static final /* enum */ Action UPDATE_GAME_MODE = new Action(){

            @Override
            protected PlayerUpdate m_142553_(FriendlyByteBuf p_179112_) {
                GameProfile $$1 = new GameProfile(p_179112_.m_130259_(), null);
                GameType $$2 = GameType.m_46393_(p_179112_.m_130242_());
                return new PlayerUpdate($$1, 0, $$2, null, null);
            }

            @Override
            protected void m_142214_(FriendlyByteBuf p_179114_, PlayerUpdate p_179115_) {
                p_179114_.m_130077_(p_179115_.m_132763_().getId());
                p_179114_.m_130130_(p_179115_.m_132765_().m_46392_());
            }
        };
        public static final /* enum */ Action UPDATE_LATENCY = new Action(){

            @Override
            protected PlayerUpdate m_142553_(FriendlyByteBuf p_179120_) {
                GameProfile $$1 = new GameProfile(p_179120_.m_130259_(), null);
                int $$2 = p_179120_.m_130242_();
                return new PlayerUpdate($$1, $$2, null, null, null);
            }

            @Override
            protected void m_142214_(FriendlyByteBuf p_179122_, PlayerUpdate p_179123_) {
                p_179122_.m_130077_(p_179123_.m_132763_().getId());
                p_179122_.m_130130_(p_179123_.m_132764_());
            }
        };
        public static final /* enum */ Action UPDATE_DISPLAY_NAME = new Action(){

            @Override
            protected PlayerUpdate m_142553_(FriendlyByteBuf p_179128_) {
                GameProfile $$1 = new GameProfile(p_179128_.m_130259_(), null);
                Component $$2 = (Component)p_179128_.m_236868_(FriendlyByteBuf::m_130238_);
                return new PlayerUpdate($$1, 0, null, $$2, null);
            }

            @Override
            protected void m_142214_(FriendlyByteBuf p_179130_, PlayerUpdate p_179131_) {
                p_179130_.m_130077_(p_179131_.m_132763_().getId());
                p_179130_.m_236821_(p_179131_.m_132766_(), FriendlyByteBuf::m_130083_);
            }
        };
        public static final /* enum */ Action REMOVE_PLAYER = new Action(){

            @Override
            protected PlayerUpdate m_142553_(FriendlyByteBuf p_179136_) {
                GameProfile $$1 = new GameProfile(p_179136_.m_130259_(), null);
                return new PlayerUpdate($$1, 0, null, null, null);
            }

            @Override
            protected void m_142214_(FriendlyByteBuf p_179138_, PlayerUpdate p_179139_) {
                p_179138_.m_130077_(p_179139_.m_132763_().getId());
            }
        };
        private static final /* synthetic */ Action[] $VALUES;

        public static Action[] values() {
            return (Action[])$VALUES.clone();
        }

        public static Action valueOf(String p_132750_) {
            return Enum.valueOf(Action.class, p_132750_);
        }

        protected abstract PlayerUpdate m_142553_(FriendlyByteBuf var1);

        protected abstract void m_142214_(FriendlyByteBuf var1, PlayerUpdate var2);

        private static /* synthetic */ Action[] m_179090_() {
            return new Action[]{ADD_PLAYER, UPDATE_GAME_MODE, UPDATE_LATENCY, UPDATE_DISPLAY_NAME, REMOVE_PLAYER};
        }

        static {
            $VALUES = Action.m_179090_();
        }
    }

    public static class PlayerUpdate {
        private final int f_132753_;
        private final GameType f_132754_;
        private final GameProfile f_132755_;
        @Nullable
        private final Component f_132756_;
        @Nullable
        private final ProfilePublicKey.Data f_237777_;

        public PlayerUpdate(GameProfile p_237779_, int p_237780_, @Nullable GameType p_237781_, @Nullable Component p_237782_, @Nullable ProfilePublicKey.Data p_237783_) {
            this.f_132755_ = p_237779_;
            this.f_132753_ = p_237780_;
            this.f_132754_ = p_237781_;
            this.f_132756_ = p_237782_;
            this.f_237777_ = p_237783_;
        }

        public GameProfile m_132763_() {
            return this.f_132755_;
        }

        public int m_132764_() {
            return this.f_132753_;
        }

        public GameType m_132765_() {
            return this.f_132754_;
        }

        @Nullable
        public Component m_132766_() {
            return this.f_132756_;
        }

        @Nullable
        public ProfilePublicKey.Data m_237784_() {
            return this.f_237777_;
        }

        public String toString() {
            return MoreObjects.toStringHelper((Object)this).add("latency", this.f_132753_).add("gameMode", (Object)this.f_132754_).add("profile", (Object)this.f_132755_).add("displayName", this.f_132756_ == null ? null : Component.Serializer.m_130703_(this.f_132756_)).add("profilePublicKey", (Object)this.f_237777_).toString();
        }
    }
}

