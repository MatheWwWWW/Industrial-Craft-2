/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 */
package net.minecraft.client.multiplayer.chat;

import com.mojang.authlib.GameProfile;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.chat.ChatTrustLevel;
import net.minecraft.client.multiplayer.chat.LoggedChatEvent;
import net.minecraft.client.multiplayer.chat.LoggedChatMessageLink;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.SignedMessageHeader;

public interface LoggedChatMessage
extends LoggedChatEvent {
    public static Player m_241912_(GameProfile p_242244_, Component p_242277_, PlayerChatMessage p_242412_, ChatTrustLevel p_242155_) {
        return new Player(p_242244_, p_242277_, p_242412_, p_242155_);
    }

    public static System m_241821_(Component p_242325_, Instant p_242334_) {
        return new System(p_242325_, p_242334_);
    }

    public Component m_241831_();

    default public Component m_241813_() {
        return this.m_241831_();
    }

    public boolean m_241866_(UUID var1);

    public record Player(GameProfile f_241668_, Component f_241677_, PlayerChatMessage f_241690_, ChatTrustLevel f_241609_) implements LoggedChatMessage,
    LoggedChatMessageLink
    {
        private static final DateTimeFormatter f_241693_ = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

        @Override
        public Component m_241831_() {
            if (!this.f_241690_.f_242992_().m_243095_()) {
                Component $$0 = this.f_241690_.f_242992_().m_243081_(this.f_241690_.m_241775_());
                return Objects.requireNonNullElse($$0, CommonComponents.f_237098_);
            }
            return this.f_241690_.m_237220_();
        }

        @Override
        public Component m_241813_() {
            Component $$0 = this.m_241831_();
            Component $$1 = this.m_241827_();
            return Component.m_237110_("gui.chatSelection.message.narrate", this.f_241677_, $$0, $$1);
        }

        public Component m_241865_() {
            Component $$0 = this.m_241827_();
            return Component.m_237110_("gui.chatSelection.heading", this.f_241677_, $$0);
        }

        private Component m_241827_() {
            LocalDateTime $$0 = LocalDateTime.ofInstant(this.f_241690_.m_241109_(), ZoneOffset.systemDefault());
            return Component.m_237113_($$0.format(f_241693_)).m_130944_(ChatFormatting.ITALIC, ChatFormatting.GRAY);
        }

        @Override
        public boolean m_241866_(UUID p_242210_) {
            return this.f_241690_.m_243088_(p_242210_);
        }

        @Override
        public SignedMessageHeader m_241887_() {
            return this.f_241690_.f_240875_();
        }

        @Override
        public byte[] m_241770_() {
            return this.f_241690_.f_240885_().m_241131_().asBytes();
        }

        @Override
        public MessageSignature m_241834_() {
            return this.f_241690_.f_240893_();
        }

        public UUID m_241803_() {
            return this.f_241668_.getId();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Player.class, "profile;displayName;message;trustLevel", "f_241668_", "f_241677_", "f_241690_", "f_241609_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Player.class, "profile;displayName;message;trustLevel", "f_241668_", "f_241677_", "f_241690_", "f_241609_"}, this);
        }

        @Override
        public final boolean equals(Object p_242245_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Player.class, "profile;displayName;message;trustLevel", "f_241668_", "f_241677_", "f_241690_", "f_241609_"}, this, p_242245_);
        }
    }

    public record System(Component f_241673_, Instant f_241622_) implements LoggedChatMessage
    {
        @Override
        public Component m_241831_() {
            return this.f_241673_;
        }

        @Override
        public boolean m_241866_(UUID p_242173_) {
            return false;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{System.class, "message;timeStamp", "f_241673_", "f_241622_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{System.class, "message;timeStamp", "f_241673_", "f_241622_"}, this);
        }

        @Override
        public final boolean equals(Object p_242394_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{System.class, "message;timeStamp", "f_241673_", "f_241622_"}, this, p_242394_);
        }
    }
}

