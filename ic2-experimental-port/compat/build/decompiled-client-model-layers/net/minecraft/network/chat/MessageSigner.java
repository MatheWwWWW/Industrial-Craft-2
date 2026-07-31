/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.chat;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.time.Instant;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Crypt;

public record MessageSigner(UUID f_240864_, Instant f_237170_, long f_237171_) {
    public MessageSigner(FriendlyByteBuf p_241430_) {
        this(p_241430_.m_130259_(), p_241430_.m_236873_(), p_241430_.readLong());
    }

    public static MessageSigner m_237183_(UUID p_237184_) {
        return new MessageSigner(p_237184_, Instant.now(), Crypt.SaltSupplier.m_216113_());
    }

    public static MessageSigner m_241182_() {
        return MessageSigner.m_237183_(Util.f_137441_);
    }

    public void m_241143_(FriendlyByteBuf p_241475_) {
        p_241475_.m_130077_(this.f_240864_);
        p_241475_.m_236826_(this.f_237170_);
        p_241475_.writeLong(this.f_237171_);
    }

    public boolean m_241005_() {
        return this.f_240864_.equals(Util.f_137441_);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{MessageSigner.class, "profileId;timeStamp;salt", "f_240864_", "f_237170_", "f_237171_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{MessageSigner.class, "profileId;timeStamp;salt", "f_240864_", "f_237170_", "f_237171_"}, this);
    }

    @Override
    public final boolean equals(Object p_237191_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{MessageSigner.class, "profileId;timeStamp;salt", "f_240864_", "f_237170_", "f_237171_"}, this, p_237191_);
    }
}

