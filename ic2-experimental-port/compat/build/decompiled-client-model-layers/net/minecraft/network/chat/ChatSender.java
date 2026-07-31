/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.world.entity.player.ProfilePublicKey;

public record ChatSender(UUID f_240364_, @Nullable ProfilePublicKey f_240874_) {
    public static final ChatSender f_240891_ = new ChatSender(Util.f_137441_, null);

    public boolean m_241002_() {
        return f_240891_.equals(this);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ChatSender.class, "profileId;profilePublicKey", "f_240364_", "f_240874_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ChatSender.class, "profileId;profilePublicKey", "f_240364_", "f_240874_"}, this);
    }

    @Override
    public final boolean equals(Object p_237002_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ChatSender.class, "profileId;profilePublicKey", "f_240364_", "f_240874_"}, this, p_237002_);
    }
}

