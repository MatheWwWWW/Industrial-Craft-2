/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public record ClientboundSetSimulationDistancePacket(int f_195796_) implements Packet<ClientGamePacketListener>
{
    public ClientboundSetSimulationDistancePacket(FriendlyByteBuf p_195800_) {
        this(p_195800_.m_130242_());
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_195802_) {
        p_195802_.m_130130_(this.f_195796_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_195806_) {
        p_195806_.m_183623_(this);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ClientboundSetSimulationDistancePacket.class, "simulationDistance", "f_195796_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ClientboundSetSimulationDistancePacket.class, "simulationDistance", "f_195796_"}, this);
    }

    @Override
    public final boolean equals(Object p_195809_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ClientboundSetSimulationDistancePacket.class, "simulationDistance", "f_195796_"}, this, p_195809_);
    }
}

