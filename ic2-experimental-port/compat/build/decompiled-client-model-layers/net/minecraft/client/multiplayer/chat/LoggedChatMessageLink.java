/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.multiplayer.chat;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.multiplayer.chat.LoggedChatEvent;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.SignedMessageHeader;

public interface LoggedChatMessageLink
extends LoggedChatEvent {
    public static Header m_241782_(SignedMessageHeader p_242461_, MessageSignature p_242167_, byte[] p_242320_) {
        return new Header(p_242461_, p_242167_, p_242320_);
    }

    public SignedMessageHeader m_241887_();

    public MessageSignature m_241834_();

    public byte[] m_241770_();

    public static final class Header
    extends Record
    implements LoggedChatMessageLink {
        private final SignedMessageHeader f_241691_;
        private final MessageSignature f_241613_;
        private final byte[] f_241619_;

        public Header(SignedMessageHeader f_241691_, MessageSignature f_241613_, byte[] f_241619_) {
            this.f_241691_ = f_241691_;
            this.f_241613_ = f_241613_;
            this.f_241619_ = f_241619_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Header.class, "header;headerSignature;bodyDigest", "f_241691_", "f_241613_", "f_241619_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Header.class, "header;headerSignature;bodyDigest", "f_241691_", "f_241613_", "f_241619_"}, this);
        }

        @Override
        public final boolean equals(Object p_242231_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Header.class, "header;headerSignature;bodyDigest", "f_241691_", "f_241613_", "f_241619_"}, this, p_242231_);
        }

        @Override
        public SignedMessageHeader m_241887_() {
            return this.f_241691_;
        }

        @Override
        public MessageSignature m_241834_() {
            return this.f_241613_;
        }

        @Override
        public byte[] m_241770_() {
            return this.f_241619_;
        }
    }
}

