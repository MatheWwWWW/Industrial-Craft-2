package ru.mot.ic2exfidelity.gravisuit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;

/** Stable NBT representation used by GraviSuite 2.2 relocators and portals. */
public record LegacyRelocatorData(long position, String dimension, String name) {
    public CompoundTag write() {
        CompoundTag tag = new CompoundTag();
        tag.m_128356_("pos", position);
        tag.m_128359_("id", dimension);
        return tag;
    }

    public static LegacyRelocatorData read(CompoundTag tag, String name) {
        return new LegacyRelocatorData(
                tag.m_128454_("pos"), tag.m_128461_("id"), name);
    }

    public BlockPos blockPosition() {
        return BlockPos.m_122022_(position);
    }

    public ServerLevel resolve(ServerPlayer context) {
        return resolve(context.m_20194_());
    }

    public ServerLevel resolve(MinecraftServer server) {
        if (server == null) {
            return null;
        }
        ResourceKey<Level> key = ResourceKey.m_135785_(
                Registry.f_122819_, new ResourceLocation(dimension));
        return server.m_129880_(key);
    }
}
