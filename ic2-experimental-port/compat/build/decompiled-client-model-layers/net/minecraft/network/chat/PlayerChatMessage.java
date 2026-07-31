/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatMessageContent;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FilterMask;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.MessageSigner;
import net.minecraft.network.chat.SignedMessageBody;
import net.minecraft.network.chat.SignedMessageHeader;
import net.minecraft.util.SignatureValidator;
import net.minecraft.world.entity.player.ProfilePublicKey;

public record PlayerChatMessage(SignedMessageHeader f_240875_, MessageSignature f_240893_, SignedMessageBody f_240885_, Optional<Component> f_237215_, FilterMask f_242992_) {
    public static final Duration f_240359_ = Duration.ofMinutes(5L);
    public static final Duration f_240369_ = f_240359_.plus(Duration.ofMinutes(2L));

    public PlayerChatMessage(FriendlyByteBuf p_241419_) {
        this(new SignedMessageHeader(p_241419_), new MessageSignature(p_241419_), new SignedMessageBody(p_241419_), p_241419_.m_236860_(FriendlyByteBuf::m_130238_), FilterMask.m_243104_(p_241419_));
    }

    public static PlayerChatMessage m_242673_(ChatMessageContent p_242910_) {
        return PlayerChatMessage.m_243126_(MessageSigner.m_241182_(), p_242910_);
    }

    public static PlayerChatMessage m_243126_(MessageSigner p_243247_, ChatMessageContent p_243279_) {
        SignedMessageBody $$2 = new SignedMessageBody(p_243279_, p_243247_.f_237170_(), p_243247_.f_237171_(), LastSeenMessages.f_241634_);
        SignedMessageHeader $$3 = new SignedMessageHeader(null, p_243247_.f_240864_());
        return new PlayerChatMessage($$3, MessageSignature.f_240860_, $$2, Optional.empty(), FilterMask.f_242999_);
    }

    public void m_240965_(FriendlyByteBuf p_241490_) {
        this.f_240875_.m_240942_(p_241490_);
        this.f_240893_.m_241011_(p_241490_);
        this.f_240885_.m_241161_(p_241490_);
        p_241490_.m_236835_(this.f_237215_, FriendlyByteBuf::m_130083_);
        FilterMask.m_243105_(p_241490_, this.f_242992_);
    }

    public PlayerChatMessage m_241956_(Component p_242164_) {
        Optional<Component> $$1 = !this.m_241775_().f_241671_().equals(p_242164_) ? Optional.of(p_242164_) : Optional.empty();
        return new PlayerChatMessage(this.f_240875_, this.f_240893_, this.f_240885_, $$1, this.f_242992_);
    }

    public PlayerChatMessage m_239022_() {
        if (this.f_237215_.isPresent()) {
            return new PlayerChatMessage(this.f_240875_, this.f_240893_, this.f_240885_, Optional.empty(), this.f_242992_);
        }
        return this;
    }

    public PlayerChatMessage m_243072_(FilterMask p_243320_) {
        if (this.f_242992_.equals(p_243320_)) {
            return this;
        }
        return new PlayerChatMessage(this.f_240875_, this.f_240893_, this.f_240885_, this.f_237215_, p_243320_);
    }

    public PlayerChatMessage m_243098_(boolean p_243223_) {
        return this.m_243072_(p_243223_ ? this.f_242992_ : FilterMask.f_242999_);
    }

    public boolean m_241121_(SignatureValidator p_241442_) {
        return this.f_240893_.m_241096_(p_241442_, this.f_240875_, this.f_240885_);
    }

    public boolean m_237228_(ProfilePublicKey p_237229_) {
        SignatureValidator $$1 = p_237229_.m_219785_();
        return this.m_241121_($$1);
    }

    public boolean m_241006_(ChatSender p_241394_) {
        ProfilePublicKey $$1 = p_241394_.f_240874_();
        return $$1 != null && this.m_237228_($$1);
    }

    public ChatMessageContent m_241775_() {
        return this.f_240885_.f_240856_();
    }

    public Component m_237220_() {
        return this.f_237215_().orElse(this.m_241775_().f_241671_());
    }

    public Instant m_241109_() {
        return this.f_240885_.f_240863_();
    }

    public long m_241064_() {
        return this.f_240885_.f_240873_();
    }

    public boolean m_240431_(Instant p_240573_) {
        return p_240573_.isAfter(this.m_241109_().plus(f_240359_));
    }

    public boolean m_240414_(Instant p_240629_) {
        return p_240629_.isAfter(this.m_241109_().plus(f_240369_));
    }

    public MessageSigner m_241067_() {
        return new MessageSigner(this.f_240875_.f_240866_(), this.m_241109_(), this.m_241064_());
    }

    @Nullable
    public LastSeenMessages.Entry m_241960_() {
        MessageSigner $$0 = this.m_241067_();
        if (!this.f_240893_.m_241004_() && !$$0.m_241005_()) {
            return new LastSeenMessages.Entry($$0.f_240864_(), this.f_240893_);
        }
        return null;
    }

    public boolean m_243088_(UUID p_243236_) {
        return !this.f_240893_.m_241004_() && this.f_240875_.f_240866_().equals(p_243236_);
    }

    public boolean m_243059_() {
        return this.f_242992_.m_243067_();
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{PlayerChatMessage.class, "signedHeader;headerSignature;signedBody;unsignedContent;filterMask", "f_240875_", "f_240893_", "f_240885_", "f_237215_", "f_242992_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{PlayerChatMessage.class, "signedHeader;headerSignature;signedBody;unsignedContent;filterMask", "f_240875_", "f_240893_", "f_240885_", "f_237215_", "f_242992_"}, this);
    }

    @Override
    public final boolean equals(Object p_237251_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{PlayerChatMessage.class, "signedHeader;headerSignature;signedBody;unsignedContent;filterMask", "f_240875_", "f_240893_", "f_240885_", "f_237215_", "f_242992_"}, this, p_237251_);
    }
}

