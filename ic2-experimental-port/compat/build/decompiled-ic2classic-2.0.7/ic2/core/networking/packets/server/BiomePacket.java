/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Registry
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.chunk.ChunkAccess
 *  net.minecraft.world.level.chunk.LevelChunkSection
 *  net.minecraft.world.level.chunk.PalettedContainer
 *  net.minecraft.world.level.chunk.PalettedContainerRO
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.networking.packets.server;

import ic2.core.IC2;
import ic2.core.networking.packets.IC2Packet;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BiomePacket
extends IC2Packet {
    BlockPos pos;
    ResourceLocation biomeKey;

    public BiomePacket() {
    }

    public BiomePacket(BlockPos pos, ResourceLocation biomeKey) {
        this.pos = pos;
        this.biomeKey = biomeKey;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeLong(this.pos.m_121878_());
        buffer.m_130085_(this.biomeKey);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        this.pos = BlockPos.m_122022_((long)buffer.readLong());
        this.biomeKey = buffer.m_130281_();
    }

    @Override
    public void handlePacket(Player source) {
        this.handleClient(source);
    }

    @OnlyIn(value=Dist.CLIENT)
    public void handleClient(Player source) {
        ClientLevel world = (ClientLevel)source.f_19853_;
        ChunkAccess chunk = world.m_46865_(this.pos);
        if (chunk == null) {
            return;
        }
        Optional biomeRef = IC2.PLATFORM.getBiomeRegistry((Level)world).m_203636_(ResourceKey.m_135785_((ResourceKey)Registry.f_122885_, (ResourceLocation)this.biomeKey));
        if (biomeRef.isEmpty()) {
            return;
        }
        Holder biome = (Holder)biomeRef.get();
        int m = world.m_151559_();
        for (int i = 0; i < m; ++i) {
            LevelChunkSection section = chunk.m_183278_(i);
            PalettedContainerRO palette = section.m_187996_();
            if (!(palette instanceof PalettedContainer)) continue;
            PalettedContainer chunkPalette = (PalettedContainer)palette;
            for (int y = 0; y < 4; ++y) {
                if (((Holder)chunkPalette.m_63091_(this.pos.m_123341_() & 3, y, this.pos.m_123343_() & 3, (Object)biome)).get() != biome.get()) continue;
            }
        }
        Minecraft.m_91087_().f_91060_.m_109494_(this.pos.m_123341_() - 5, world.m_141937_(), this.pos.m_123343_() - 5, this.pos.m_123341_() + 5, world.m_151558_(), this.pos.m_123343_() + 5);
    }
}

