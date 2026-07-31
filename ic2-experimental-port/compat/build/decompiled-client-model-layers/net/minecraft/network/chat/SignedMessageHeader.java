/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.security.SignatureException;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.util.SignatureUpdater;

public record SignedMessageHeader(@Nullable MessageSignature f_240892_, UUID f_240866_) {
    public SignedMessageHeader(FriendlyByteBuf p_241381_) {
        this((MessageSignature)p_241381_.m_236868_(MessageSignature::new), p_241381_.m_130259_());
    }

    public void m_240942_(FriendlyByteBuf p_241567_) {
        p_241567_.m_236821_(this.f_240892_, (p_241348_, p_241289_) -> p_241289_.m_241011_((FriendlyByteBuf)((Object)p_241348_)));
        p_241567_.m_130077_(this.f_240866_);
    }

    public void m_240997_(SignatureUpdater.Output p_241383_, byte[] p_241564_) throws SignatureException {
        if (this.f_240892_ != null) {
            p_241383_.m_216346_(this.f_240892_.f_240884_());
        }
        p_241383_.m_216346_(UUIDUtil.m_241191_(this.f_240866_));
        p_241383_.m_216346_(p_241564_);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{SignedMessageHeader.class, "previousSignature;sender", "f_240892_", "f_240866_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{SignedMessageHeader.class, "previousSignature;sender", "f_240892_", "f_240866_"}, this);
    }

    @Override
    public final boolean equals(Object p_241495_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{SignedMessageHeader.class, "previousSignature;sender", "f_240892_", "f_240866_"}, this, p_241495_);
    }
}

