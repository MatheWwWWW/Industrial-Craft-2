/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import com.google.common.collect.Sets;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public record ClientboundLoginPacket(int f_132360_, boolean f_132362_, GameType f_132363_, @Nullable GameType f_132364_, Set<ResourceKey<Level>> f_132365_, RegistryAccess.Frozen f_132366_, ResourceKey<DimensionType> f_132367_, ResourceKey<Level> f_132368_, long f_132361_, int f_132369_, int f_132370_, int f_195761_, boolean f_132371_, boolean f_132372_, boolean f_132373_, boolean f_132374_, Optional<GlobalPos> f_238174_) implements Packet<ClientGamePacketListener>
{
    public ClientboundLoginPacket(FriendlyByteBuf p_178960_) {
        this(p_178960_.readInt(), p_178960_.readBoolean(), GameType.m_46393_(p_178960_.readByte()), GameType.m_151497_(p_178960_.readByte()), p_178960_.m_236838_(Sets::newHashSetWithExpectedSize, p_178965_ -> p_178965_.m_236801_(Registry.f_122819_)), p_178960_.m_130057_(RegistryAccess.f_206151_).m_203557_(), p_178960_.m_236801_(Registry.f_122818_), p_178960_.m_236801_(Registry.f_122819_), p_178960_.readLong(), p_178960_.m_130242_(), p_178960_.m_130242_(), p_178960_.m_130242_(), p_178960_.readBoolean(), p_178960_.readBoolean(), p_178960_.readBoolean(), p_178960_.readBoolean(), p_178960_.m_236860_(FriendlyByteBuf::m_236872_));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132400_) {
        p_132400_.writeInt(this.f_132360_);
        p_132400_.writeBoolean(this.f_132362_);
        p_132400_.writeByte(this.f_132363_.m_46392_());
        p_132400_.writeByte(GameType.m_151495_(this.f_132364_));
        p_132400_.m_236828_(this.f_132365_, FriendlyByteBuf::m_236858_);
        p_132400_.m_130059_(RegistryAccess.f_206151_, this.f_132366_);
        p_132400_.m_236858_(this.f_132367_);
        p_132400_.m_236858_(this.f_132368_);
        p_132400_.writeLong(this.f_132361_);
        p_132400_.m_130130_(this.f_132369_);
        p_132400_.m_130130_(this.f_132370_);
        p_132400_.m_130130_(this.f_195761_);
        p_132400_.writeBoolean(this.f_132371_);
        p_132400_.writeBoolean(this.f_132372_);
        p_132400_.writeBoolean(this.f_132373_);
        p_132400_.writeBoolean(this.f_132374_);
        p_132400_.m_236835_(this.f_238174_, FriendlyByteBuf::m_236814_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132397_) {
        p_132397_.m_5998_(this);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ClientboundLoginPacket.class, "playerId;hardcore;gameType;previousGameType;levels;registryHolder;dimensionType;dimension;seed;maxPlayers;chunkRadius;simulationDistance;reducedDebugInfo;showDeathScreen;isDebug;isFlat;lastDeathLocation", "f_132360_", "f_132362_", "f_132363_", "f_132364_", "f_132365_", "f_132366_", "f_132367_", "f_132368_", "f_132361_", "f_132369_", "f_132370_", "f_195761_", "f_132371_", "f_132372_", "f_132373_", "f_132374_", "f_238174_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ClientboundLoginPacket.class, "playerId;hardcore;gameType;previousGameType;levels;registryHolder;dimensionType;dimension;seed;maxPlayers;chunkRadius;simulationDistance;reducedDebugInfo;showDeathScreen;isDebug;isFlat;lastDeathLocation", "f_132360_", "f_132362_", "f_132363_", "f_132364_", "f_132365_", "f_132366_", "f_132367_", "f_132368_", "f_132361_", "f_132369_", "f_132370_", "f_195761_", "f_132371_", "f_132372_", "f_132373_", "f_132374_", "f_238174_"}, this);
    }

    @Override
    public final boolean equals(Object p_195784_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ClientboundLoginPacket.class, "playerId;hardcore;gameType;previousGameType;levels;registryHolder;dimensionType;dimension;seed;maxPlayers;chunkRadius;simulationDistance;reducedDebugInfo;showDeathScreen;isDebug;isFlat;lastDeathLocation", "f_132360_", "f_132362_", "f_132363_", "f_132364_", "f_132365_", "f_132366_", "f_132367_", "f_132368_", "f_132361_", "f_132369_", "f_132370_", "f_195761_", "f_132371_", "f_132372_", "f_132373_", "f_132374_", "f_238174_"}, this, p_195784_);
    }
}

