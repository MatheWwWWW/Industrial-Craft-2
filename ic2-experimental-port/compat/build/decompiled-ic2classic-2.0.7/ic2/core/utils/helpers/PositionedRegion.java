/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  it.unimi.dsi.fastutil.longs.LongList
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.chunk.LevelChunkSection
 */
package ic2.core.utils.helpers;

import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.geometry.Box;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;

public class PositionedRegion {
    int chunkX;
    int chunkZ;
    LevelChunkSection section;

    public PositionedRegion(ChunkPos pos, LevelChunkSection section) {
        this.chunkX = pos.f_45578_;
        this.chunkZ = pos.f_45579_;
        this.section = section;
    }

    public PositionedRegion(int chunkX, int chunkZ, LevelChunkSection section) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.section = section;
    }

    public static List<PositionedRegion> create(Level level, Box box) {
        int chunkX = box.getMinX() >> 4;
        int chunkZ = box.getMinZ() >> 4;
        int width = (box.getMaxX() >> 4) + 1 - chunkX;
        int height = (box.getMaxZ() >> 4) + 1 - chunkZ;
        ObjectList regions = CollectionUtils.createList();
        for (int x = 0; x < width; ++x) {
            for (int z = 0; z < height; ++z) {
                for (LevelChunkSection section : level.m_6325_(chunkX + x, chunkZ + z).m_7103_()) {
                    if (section.m_188008_()) continue;
                    regions.add((PositionedRegion)new PositionedRegion(chunkX + x, chunkZ + z, section));
                }
            }
        }
        return regions;
    }

    public static List<PositionedRegion> create(LevelChunk chunk) {
        ObjectList regions = CollectionUtils.createList();
        for (LevelChunkSection section : chunk.m_7103_()) {
            if (section.m_188008_()) continue;
            regions.add((PositionedRegion)new PositionedRegion(chunk.m_7697_(), section));
        }
        return regions;
    }

    public Map<BlockState, LongList> getStatePositions() {
        Object2ObjectSortedMap<BlockState, LongList> maps = CollectionUtils.createLinkedMap();
        int posX = this.chunkX << 4;
        int posY = this.section.m_63017_();
        int posZ = this.chunkZ << 4;
        for (int x = 0; x < 16; ++x) {
            for (int y = 0; y < 16; ++y) {
                for (int z = 0; z < 16; ++z) {
                    maps.computeIfAbsent(this.section.m_62982_(x, y, z), this::create).add(BlockPos.m_121882_((int)(x + posX), (int)(y + posY), (int)(z + posZ)));
                }
            }
        }
        return maps;
    }

    public Map<Block, LongList> getBlockPositions() {
        Object2ObjectSortedMap<Block, LongList> maps = CollectionUtils.createLinkedMap();
        int posX = this.chunkX << 4;
        int posY = this.section.m_63017_();
        int posZ = this.chunkZ << 4;
        for (int x = 0; x < 16; ++x) {
            for (int y = 0; y < 16; ++y) {
                for (int z = 0; z < 16; ++z) {
                    maps.computeIfAbsent(this.section.m_62982_(x, y, z).m_60734_(), this::create).add(BlockPos.m_121882_((int)(x + posX), (int)(y + posY), (int)(z + posZ)));
                }
            }
        }
        return maps;
    }

    private LongList create(Block block) {
        return new LongArrayList();
    }

    private LongList create(BlockState state) {
        return new LongArrayList();
    }
}

