/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public record ClientboundDeleteChatPacket(MessageSignature f_240904_) implements Packet<ClientGamePacketListener>
{
    public ClientboundDeleteChatPacket(FriendlyByteBuf p_241415_) {
        this(new MessageSignature(p_241415_));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_241358_) {
        this.f_240904_.m_241011_(p_241358_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_241426_) {
        p_241426_.m_241037_(this);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ClientboundDeleteChatPacket.class, "messageSignature", "f_240904_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ClientboundDeleteChatPacket.class, "messageSignature", "f_240904_"}, this);
    }

    @Override
    public final boolean equals(Object p_241454_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ClientboundDeleteChatPacket.class, "messageSignature", "f_240904_"}, this, p_241454_);
    }
}

