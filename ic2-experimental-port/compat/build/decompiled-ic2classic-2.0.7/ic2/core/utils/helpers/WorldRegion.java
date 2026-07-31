/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Holder
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.LightLayer
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.biome.BiomeManager
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.border.WorldBorder
 *  net.minecraft.world.level.chunk.ChunkAccess
 *  net.minecraft.world.level.chunk.ChunkStatus
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.dimension.DimensionType
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.level.lighting.LevelLightEngine
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.utils.helpers;

import ic2.core.platform.corehacks.mixins.server.ChunkMixin;
import ic2.core.utils.math.geometry.Box;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WorldRegion
implements LevelReader {
    protected final int chunkX;
    protected final int chunkZ;
    protected final int height;
    protected final Long2ObjectMap<LevelChunk> chunks;
    protected final Level world;
    protected final boolean loadChunks;
    protected final boolean offthread;

    public WorldRegion(Level worldIn, Box box, boolean loadChunks) {
        this(worldIn, box, loadChunks, false);
    }

    public WorldRegion(Level worldIn, Box box, boolean loadChunks, boolean offthread) {
        this.world = worldIn;
        this.chunkX = box.getMinX() >> 4;
        this.chunkZ = box.getMinZ() >> 4;
        int width = (box.getMaxX() >> 4) + 1 - this.chunkX;
        this.height = (box.getMaxZ() >> 4) + 1 - this.chunkZ;
        this.loadChunks = loadChunks;
        this.offthread = offthread;
        this.chunks = new Long2ObjectLinkedOpenHashMap();
        for (int x = 0; x < width; ++x) {
            for (int z = 0; z < this.height; ++z) {
                this.chunks.put(ChunkPos.m_45589_((int)(this.chunkX + x), (int)(this.chunkZ + z)), (Object)this.world.m_6325_(this.chunkX + x, this.chunkZ + z));
            }
        }
    }

    public int m_45524_(BlockPos pos, int amount) {
        return this.world.m_45524_(pos, amount);
    }

    public ChunkAccess m_6522_(int x, int z, ChunkStatus requiredStatus, boolean nonnull) {
        long pos = ChunkPos.m_45589_((int)x, (int)z);
        LevelChunk chunk = (LevelChunk)this.chunks.get(pos);
        if (chunk == null && this.loadChunks && !this.offthread) {
            chunk = this.world.m_6325_(x, z);
            this.chunks.put(pos, (Object)chunk);
        }
        return chunk;
    }

    public boolean m_7232_(int x, int z) {
        if (this.loadChunks && !this.offthread) {
            return true;
        }
        return this.chunks.containsKey(ChunkPos.m_45589_((int)x, (int)z));
    }

    public BlockPos m_5452_(Heightmap.Types heightMapType, BlockPos pos) {
        return new BlockPos(pos.m_123341_(), this.m_6924_(heightMapType, pos.m_123341_(), pos.m_123343_()), pos.m_123343_());
    }

    public int m_6924_(Heightmap.Types heightMapType, int x, int z) {
        ChunkAccess chunk = this.m_6325_(x >> 4, z >> 4);
        return chunk == null ? 0 : chunk.m_5885_(heightMapType, x & 0xF, z & 0xF) + 1;
    }

    public int m_7445_() {
        return this.world.m_7445_();
    }

    public WorldBorder m_6857_() {
        return this.world.m_6857_();
    }

    public boolean m_5450_(@Nullable Entity entityIn, VoxelShape shape) {
        return true;
    }

    public boolean m_5776_() {
        return false;
    }

    public int m_5736_() {
        return this.world.m_5736_();
    }

    public DimensionType m_6042_() {
        return this.world.m_6042_();
    }

    @Nullable
    public BlockEntity m_7702_(BlockPos pos) {
        ChunkAccess chunk = this.m_46865_(pos);
        return chunk == null ? null : chunk.m_7702_(pos);
    }

    public BlockState m_8055_(BlockPos pos) {
        if (this.world.m_151570_(pos)) {
            return Blocks.f_50016_.m_49966_();
        }
        ChunkAccess chunk = this.m_46865_(pos);
        return chunk == null ? Blocks.f_50016_.m_49966_() : chunk.m_8055_(pos);
    }

    public FluidState m_6425_(BlockPos pos) {
        if (this.world.m_151570_(pos)) {
            return Fluids.f_76191_.m_76145_();
        }
        ChunkAccess chunk = this.m_46865_(pos);
        return chunk == null ? Fluids.f_76191_.m_76145_() : chunk.m_6425_(pos);
    }

    public List<VoxelShape> m_183134_(Entity p_186427_, AABB p_186428_) {
        return this.world.m_183134_(p_186427_, p_186428_);
    }

    @OnlyIn(value=Dist.CLIENT)
    public float m_7717_(Direction p_230487_1_, boolean p_230487_2_) {
        return 1.0f;
    }

    public LevelLightEngine m_5518_() {
        return this.world.m_5518_();
    }

    public BiomeManager m_7062_() {
        return this.world.m_7062_();
    }

    public Holder<Biome> m_203675_(int x, int y, int z) {
        return this.world.m_203495_(x, y, z);
    }

    public int m_45517_(LightLayer type, BlockPos pos) {
        return this.world.m_45517_(type, pos);
    }

    public boolean hasInvalidChunks() {
        for (LevelChunk chunk : this.chunks.values()) {
            if (((ChunkMixin)chunk).isLoaded()) continue;
            return true;
        }
        return false;
    }

    public boolean isBlockPresent(BlockPos pos) {
        return !this.world.m_151570_(pos) && this.m_7232_(pos.m_123341_() >> 4, pos.m_123343_() >> 4);
    }
}

