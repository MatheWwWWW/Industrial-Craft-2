/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public record ClientboundBlockChangedAckPacket(int f_237578_) implements Packet<ClientGamePacketListener>
{
    public ClientboundBlockChangedAckPacket(FriendlyByteBuf p_237582_) {
        this(p_237582_.m_130242_());
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_237584_) {
        p_237584_.m_130130_(this.f_237578_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_237588_) {
        p_237588_.m_214108_(this);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ClientboundBlockChangedAckPacket.class, "sequence", "f_237578_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ClientboundBlockChangedAckPacket.class, "sequence", "f_237578_"}, this);
    }

    @Override
    public final boolean equals(Object p_237591_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ClientboundBlockChangedAckPacket.class, "sequence", "f_237578_"}, this, p_237591_);
    }
}

