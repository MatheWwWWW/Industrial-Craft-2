/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.login;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.login.ServerLoginPacketListener;
import net.minecraft.world.entity.player.ProfilePublicKey;

public record ServerboundHelloPacket(String f_238040_, Optional<ProfilePublicKey.Data> f_238041_, Optional<UUID> f_240375_) implements Packet<ServerLoginPacketListener>
{
    public ServerboundHelloPacket(FriendlyByteBuf p_179827_) {
        this(p_179827_.m_130136_(16), p_179827_.m_236860_(ProfilePublicKey.Data::new), p_179827_.m_236860_(FriendlyByteBuf::m_130259_));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134851_) {
        p_134851_.m_130072_(this.f_238040_, 16);
        p_134851_.m_236835_(this.f_238041_, (p_238047_, p_238048_) -> p_238048_.m_219815_(p_134851_));
        p_134851_.m_236835_(this.f_240375_, FriendlyByteBuf::m_130077_);
    }

    @Override
    public void m_5797_(ServerLoginPacketListener p_134848_) {
        p_134848_.m_5990_(this);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ServerboundHelloPacket.class, "name;publicKey;profileId", "f_238040_", "f_238041_", "f_240375_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ServerboundHelloPacket.class, "name;publicKey;profileId", "f_238040_", "f_238041_", "f_240375_"}, this);
    }

    @Override
    public final boolean equals(Object p_238052_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ServerboundHelloPacket.class, "name;publicKey;profileId", "f_238040_", "f_238041_", "f_240375_"}, this, p_238052_);
    }
}

