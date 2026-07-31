/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.ticks.BlackholeTickAccess;
import net.minecraft.world.ticks.TickContainerAccess;

public class ImposterProtoChunk
extends ProtoChunk {
    private final LevelChunk f_62685_;
    private final boolean f_187918_;

    public ImposterProtoChunk(LevelChunk p_187920_, boolean p_187921_) {
        super(p_187920_.m_7697_(), UpgradeData.f_63320_, p_187920_.f_187611_, p_187920_.m_62953_().m_5962_().m_175515_(Registry.f_122885_), p_187920_.m_183407_());
        this.f_62685_ = p_187920_;
        this.f_187918_ = p_187921_;
    }

    @Override
    @Nullable
    public BlockEntity m_7702_(BlockPos p_62744_) {
        return this.f_62685_.m_7702_(p_62744_);
    }

    @Override
    public BlockState m_8055_(BlockPos p_62749_) {
        return this.f_62685_.m_8055_(p_62749_);
    }

    @Override
    public FluidState m_6425_(BlockPos p_62736_) {
        return this.f_62685_.m_6425_(p_62736_);
    }

    @Override
    public int m_7469_() {
        return this.f_62685_.m_7469_();
    }

    @Override
    public LevelChunkSection m_183278_(int p_187932_) {
        if (this.f_187918_) {
            return this.f_62685_.m_183278_(p_187932_);
        }
        return super.m_183278_(p_187932_);
    }

    @Override
    @Nullable
    public BlockState m_6978_(BlockPos p_62722_, BlockState p_62723_, boolean p_62724_) {
        if (this.f_187918_) {
            return this.f_62685_.m_6978_(p_62722_, p_62723_, p_62724_);
        }
        return null;
    }

    @Override
    public void m_142169_(BlockEntity p_156358_) {
        if (this.f_187918_) {
            this.f_62685_.m_142169_(p_156358_);
        }
    }

    @Override
    public void m_6286_(Entity p_62692_) {
        if (this.f_187918_) {
            this.f_62685_.m_6286_(p_62692_);
        }
    }

    @Override
    public void m_7150_(ChunkStatus p_62698_) {
        if (this.f_187918_) {
            super.m_7150_(p_62698_);
        }
    }

    @Override
    public LevelChunkSection[] m_7103_() {
        return this.f_62685_.m_7103_();
    }

    @Override
    public void m_6511_(Heightmap.Types p_62706_, long[] p_62707_) {
    }

    private Heightmap.Types m_62741_(Heightmap.Types p_62742_) {
        if (p_62742_ == Heightmap.Types.WORLD_SURFACE_WG) {
            return Heightmap.Types.WORLD_SURFACE;
        }
        if (p_62742_ == Heightmap.Types.OCEAN_FLOOR_WG) {
            return Heightmap.Types.OCEAN_FLOOR;
        }
        return p_62742_;
    }

    @Override
    public Heightmap m_6005_(Heightmap.Types p_187928_) {
        return this.f_62685_.m_6005_(p_187928_);
    }

    @Override
    public int m_5885_(Heightmap.Types p_62702_, int p_62703_, int p_62704_) {
        return this.f_62685_.m_5885_(this.m_62741_(p_62702_), p_62703_, p_62704_);
    }

    @Override
    public Holder<Biome> m_203495_(int p_204430_, int p_204431_, int p_204432_) {
        return this.f_62685_.m_203495_(p_204430_, p_204431_, p_204432_);
    }

    @Override
    public ChunkPos m_7697_() {
        return this.f_62685_.m_7697_();
    }

    @Override
    @Nullable
    public StructureStart m_213652_(Structure p_223400_) {
        return this.f_62685_.m_213652_(p_223400_);
    }

    @Override
    public void m_213792_(Structure p_223405_, StructureStart p_223406_) {
    }

    @Override
    public Map<Structure, StructureStart> m_6633_() {
        return this.f_62685_.m_6633_();
    }

    @Override
    public void m_8040_(Map<Structure, StructureStart> p_62726_) {
    }

    @Override
    public LongSet m_213649_(Structure p_223408_) {
        return this.f_62685_.m_213649_(p_223408_);
    }

    @Override
    public void m_213843_(Structure p_223402_, long p_223403_) {
    }

    @Override
    public Map<Structure, LongSet> m_62769_() {
        return this.f_62685_.m_62769_();
    }

    @Override
    public void m_62737_(Map<Structure, LongSet> p_62738_) {
    }

    @Override
    public void m_8092_(boolean p_62730_) {
        this.f_62685_.m_8092_(p_62730_);
    }

    @Override
    public boolean m_6344_() {
        return false;
    }

    @Override
    public ChunkStatus m_6415_() {
        return this.f_62685_.m_6415_();
    }

    @Override
    public void m_8114_(BlockPos p_62747_) {
    }

    @Override
    public void m_8113_(BlockPos p_62752_) {
    }

    @Override
    public void m_5604_(CompoundTag p_62728_) {
    }

    @Override
    @Nullable
    public CompoundTag m_8049_(BlockPos p_62757_) {
        return this.f_62685_.m_8049_(p_62757_);
    }

    @Override
    @Nullable
    public CompoundTag m_8051_(BlockPos p_62760_) {
        return this.f_62685_.m_8051_(p_62760_);
    }

    @Override
    public Stream<BlockPos> m_6267_() {
        return this.f_62685_.m_6267_();
    }

    @Override
    public TickContainerAccess<Block> m_183531_() {
        if (this.f_187918_) {
            return this.f_62685_.m_183531_();
        }
        return BlackholeTickAccess.m_193144_();
    }

    @Override
    public TickContainerAccess<Fluid> m_183526_() {
        if (this.f_187918_) {
            return this.f_62685_.m_183526_();
        }
        return BlackholeTickAccess.m_193144_();
    }

    @Override
    public ChunkAccess.TicksToSave m_183568_() {
        return this.f_62685_.m_183568_();
    }

    @Override
    @Nullable
    public BlendingData m_183407_() {
        return this.f_62685_.m_183407_();
    }

    @Override
    public void m_183400_(BlendingData p_187930_) {
        this.f_62685_.m_183400_(p_187930_);
    }

    @Override
    public CarvingMask m_183612_(GenerationStep.Carving p_187926_) {
        if (this.f_187918_) {
            return super.m_183612_(p_187926_);
        }
        throw Util.m_137570_(new UnsupportedOperationException("Meaningless in this context"));
    }

    @Override
    public CarvingMask m_183613_(GenerationStep.Carving p_187934_) {
        if (this.f_187918_) {
            return super.m_183613_(p_187934_);
        }
        throw Util.m_137570_(new UnsupportedOperationException("Meaningless in this context"));
    }

    public LevelChunk m_62768_() {
        return this.f_62685_;
    }

    @Override
    public boolean m_6332_() {
        return this.f_62685_.m_6332_();
    }

    @Override
    public void m_8094_(boolean p_62740_) {
        this.f_62685_.m_8094_(p_62740_);
    }

    @Override
    public void m_183442_(BiomeResolver p_187923_, Climate.Sampler p_187924_) {
        if (this.f_187918_) {
            this.f_62685_.m_183442_(p_187923_, p_187924_);
        }
    }
}

