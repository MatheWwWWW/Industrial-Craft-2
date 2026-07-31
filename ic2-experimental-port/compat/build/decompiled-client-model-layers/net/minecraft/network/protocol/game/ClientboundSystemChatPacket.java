/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public record ClientboundSystemChatPacket(Component f_237849_, boolean f_240374_) implements Packet<ClientGamePacketListener>
{
    public ClientboundSystemChatPacket(FriendlyByteBuf p_237852_) {
        this(p_237852_.m_130238_(), p_237852_.readBoolean());
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_237860_) {
        p_237860_.m_130083_(this.f_237849_);
        p_237860_.writeBoolean(this.f_240374_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_237864_) {
        p_237864_.m_213990_(this);
    }

    @Override
    public boolean m_6588_() {
        return true;
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ClientboundSystemChatPacket.class, "content;overlay", "f_237849_", "f_240374_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ClientboundSystemChatPacket.class, "content;overlay", "f_237849_", "f_240374_"}, this);
    }

    @Override
    public final boolean equals(Object p_237868_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ClientboundSystemChatPacket.class, "content;overlay", "f_237849_", "f_240374_"}, this, p_237868_);
    }
}

