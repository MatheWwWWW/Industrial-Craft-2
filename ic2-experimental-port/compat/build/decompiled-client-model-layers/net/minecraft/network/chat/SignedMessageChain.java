/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.network.chat.ChatMessageContent;
import net.minecraft.network.chat.FilterMask;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.MessageSigner;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.SignedMessageBody;
import net.minecraft.network.chat.SignedMessageHeader;
import net.minecraft.util.Signer;

public class SignedMessageChain {
    @Nullable
    private MessageSignature f_240861_;

    private Link m_241902_(Signer p_242326_, MessageSigner p_242397_, ChatMessageContent p_242431_, LastSeenMessages p_242419_) {
        MessageSignature $$4;
        this.f_240861_ = $$4 = SignedMessageChain.m_241977_(p_242326_, p_242397_, this.f_240861_, p_242431_, p_242419_);
        return new Link($$4);
    }

    private static MessageSignature m_241977_(Signer p_242255_, MessageSigner p_242258_, @Nullable MessageSignature p_242378_, ChatMessageContent p_242185_, LastSeenMessages p_242456_) {
        SignedMessageHeader $$5 = new SignedMessageHeader(p_242378_, p_242258_.f_240864_());
        SignedMessageBody $$6 = new SignedMessageBody(p_242185_, p_242258_.f_237170_(), p_242258_.f_237171_(), p_242456_);
        byte[] $$7 = $$6.m_241131_().asBytes();
        return new MessageSignature(p_242255_.m_216395_(p_241520_ -> $$5.m_240997_(p_241520_, $$7)));
    }

    private PlayerChatMessage m_241871_(Link p_242429_, MessageSigner p_242380_, ChatMessageContent p_242233_, LastSeenMessages p_242352_) {
        PlayerChatMessage $$4 = SignedMessageChain.m_241781_(p_242429_, this.f_240861_, p_242380_, p_242233_, p_242352_);
        this.f_240861_ = p_242429_.f_240871_;
        return $$4;
    }

    private static PlayerChatMessage m_241781_(Link p_242261_, @Nullable MessageSignature p_242207_, MessageSigner p_242248_, ChatMessageContent p_242304_, LastSeenMessages p_242200_) {
        SignedMessageHeader $$5 = new SignedMessageHeader(p_242207_, p_242248_.f_240864_());
        SignedMessageBody $$6 = new SignedMessageBody(p_242304_, p_242248_.f_237170_(), p_242248_.f_237171_(), p_242200_);
        return new PlayerChatMessage($$5, p_242261_.f_240871_, $$6, Optional.empty(), FilterMask.f_242999_);
    }

    public Decoder m_241180_() {
        return this::m_241871_;
    }

    public Encoder m_241042_() {
        return this::m_241902_;
    }

    public record Link(MessageSignature f_240871_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Link.class, "signature", "f_240871_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Link.class, "signature", "f_240871_"}, this);
        }

        @Override
        public final boolean equals(Object p_241278_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Link.class, "signature", "f_240871_"}, this, p_241278_);
        }
    }

    @FunctionalInterface
    public static interface Decoder {
        public static final Decoder f_243004_ = (p_243332_, p_243220_, p_243212_, p_243282_) -> PlayerChatMessage.m_243126_(p_243220_, p_243212_);

        public PlayerChatMessage m_240945_(Link var1, MessageSigner var2, ChatMessageContent var3, LastSeenMessages var4);
    }

    @FunctionalInterface
    public static interface Encoder {
        public Link m_240988_(Signer var1, MessageSigner var2, ChatMessageContent var3, LastSeenMessages var4);
    }
}

