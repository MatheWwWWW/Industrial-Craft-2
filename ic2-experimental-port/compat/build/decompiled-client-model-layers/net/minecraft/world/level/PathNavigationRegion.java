/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Suppliers
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level;

import com.google.common.base.Suppliers;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.EmptyLevelChunk;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PathNavigationRegion
implements BlockGetter,
CollisionGetter {
    protected final int f_47158_;
    protected final int f_47159_;
    protected final ChunkAccess[][] f_47160_;
    protected boolean f_47161_;
    protected final Level f_47162_;
    private final Supplier<Holder<Biome>> f_204180_;

    public PathNavigationRegion(Level p_47164_, BlockPos p_47165_, BlockPos p_47166_) {
        this.f_47162_ = p_47164_;
        this.f_204180_ = Suppliers.memoize(() -> p_47164_.m_5962_().m_175515_(Registry.f_122885_).m_206081_(Biomes.f_48202_));
        this.f_47158_ = SectionPos.m_123171_(p_47165_.m_123341_());
        this.f_47159_ = SectionPos.m_123171_(p_47165_.m_123343_());
        int $$3 = SectionPos.m_123171_(p_47166_.m_123341_());
        int $$4 = SectionPos.m_123171_(p_47166_.m_123343_());
        this.f_47160_ = new ChunkAccess[$$3 - this.f_47158_ + 1][$$4 - this.f_47159_ + 1];
        ChunkSource $$5 = p_47164_.m_7726_();
        this.f_47161_ = true;
        for (int $$6 = this.f_47158_; $$6 <= $$3; ++$$6) {
            for (int $$7 = this.f_47159_; $$7 <= $$4; ++$$7) {
                this.f_47160_[$$6 - this.f_47158_][$$7 - this.f_47159_] = $$5.m_7131_($$6, $$7);
            }
        }
        for (int $$8 = SectionPos.m_123171_(p_47165_.m_123341_()); $$8 <= SectionPos.m_123171_(p_47166_.m_123341_()); ++$$8) {
            for (int $$9 = SectionPos.m_123171_(p_47165_.m_123343_()); $$9 <= SectionPos.m_123171_(p_47166_.m_123343_()); ++$$9) {
                ChunkAccess $$10 = this.f_47160_[$$8 - this.f_47158_][$$9 - this.f_47159_];
                if ($$10 == null || $$10.m_5566_(p_47165_.m_123342_(), p_47166_.m_123342_())) continue;
                this.f_47161_ = false;
                return;
            }
        }
    }

    private ChunkAccess m_47185_(BlockPos p_47186_) {
        return this.m_47167_(SectionPos.m_123171_(p_47186_.m_123341_()), SectionPos.m_123171_(p_47186_.m_123343_()));
    }

    private ChunkAccess m_47167_(int p_47168_, int p_47169_) {
        int $$2 = p_47168_ - this.f_47158_;
        int $$3 = p_47169_ - this.f_47159_;
        if ($$2 < 0 || $$2 >= this.f_47160_.length || $$3 < 0 || $$3 >= this.f_47160_[$$2].length) {
            return new EmptyLevelChunk(this.f_47162_, new ChunkPos(p_47168_, p_47169_), this.f_204180_.get());
        }
        ChunkAccess $$4 = this.f_47160_[$$2][$$3];
        return $$4 != null ? $$4 : new EmptyLevelChunk(this.f_47162_, new ChunkPos(p_47168_, p_47169_), this.f_204180_.get());
    }

    @Override
    public WorldBorder m_6857_() {
        return this.f_47162_.m_6857_();
    }

    @Override
    public BlockGetter m_7925_(int p_47173_, int p_47174_) {
        return this.m_47167_(p_47173_, p_47174_);
    }

    @Override
    public List<VoxelShape> m_183134_(@Nullable Entity p_186557_, AABB p_186558_) {
        return List.of();
    }

    @Override
    @Nullable
    public BlockEntity m_7702_(BlockPos p_47180_) {
        ChunkAccess $$1 = this.m_47185_(p_47180_);
        return $$1.m_7702_(p_47180_);
    }

    @Override
    public BlockState m_8055_(BlockPos p_47188_) {
        if (this.m_151570_(p_47188_)) {
            return Blocks.f_50016_.m_49966_();
        }
        ChunkAccess $$1 = this.m_47185_(p_47188_);
        return $$1.m_8055_(p_47188_);
    }

    @Override
    public FluidState m_6425_(BlockPos p_47171_) {
        if (this.m_151570_(p_47171_)) {
            return Fluids.f_76191_.m_76145_();
        }
        ChunkAccess $$1 = this.m_47185_(p_47171_);
        return $$1.m_6425_(p_47171_);
    }

    @Override
    public int m_141937_() {
        return this.f_47162_.m_141937_();
    }

    @Override
    public int m_141928_() {
        return this.f_47162_.m_141928_();
    }

    public ProfilerFiller m_151625_() {
        return this.f_47162_.m_46473_();
    }
}

