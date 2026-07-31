/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.bytes.ByteArrays
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import it.unimi.dsi.fastutil.bytes.ByteArrays;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Base64;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.SignedMessageBody;
import net.minecraft.network.chat.SignedMessageHeader;
import net.minecraft.util.SignatureValidator;

public record MessageSignature(byte[] f_240884_) {
    public static final MessageSignature f_240860_ = new MessageSignature(ByteArrays.EMPTY_ARRAY);

    public MessageSignature(FriendlyByteBuf p_241519_) {
        this(p_241519_.m_130052_());
    }

    public void m_241011_(FriendlyByteBuf p_241393_) {
        p_241393_.m_130087_(this.f_240884_);
    }

    public boolean m_241096_(SignatureValidator p_241501_, SignedMessageHeader p_241273_, SignedMessageBody p_241556_) {
        if (!this.m_241004_()) {
            byte[] $$3 = p_241556_.m_241131_().asBytes();
            return p_241501_.m_216378_(p_241242_ -> p_241273_.m_240997_(p_241242_, $$3), this.f_240884_);
        }
        return false;
    }

    public boolean m_241124_(SignatureValidator p_241537_, SignedMessageHeader p_241482_, byte[] p_241502_) {
        if (!this.m_241004_()) {
            return p_241537_.m_216378_(p_241245_ -> p_241482_.m_240997_(p_241245_, p_241502_), this.f_240884_);
        }
        return false;
    }

    public boolean m_241004_() {
        return this.f_240884_.length == 0;
    }

    @Nullable
    public ByteBuffer m_241929_() {
        if (!this.m_241004_()) {
            return ByteBuffer.wrap(this.f_240884_);
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object p_237166_) {
        if (this == p_237166_) return true;
        if (!(p_237166_ instanceof MessageSignature)) return false;
        MessageSignature $$1 = (MessageSignature)p_237166_;
        if (!Arrays.equals(this.f_240884_, $$1.f_240884_)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.f_240884_);
    }

    @Override
    public String toString() {
        if (!this.m_241004_()) {
            return Base64.getEncoder().encodeToString(this.f_240884_);
        }
        return "empty";
    }
}

