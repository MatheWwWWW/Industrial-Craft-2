/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.EntityGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelSimulatedRW;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface CommonLevelAccessor
extends EntityGetter,
LevelReader,
LevelSimulatedRW {
    @Override
    default public <T extends BlockEntity> Optional<T> m_141902_(BlockPos p_151452_, BlockEntityType<T> p_151453_) {
        return LevelReader.super.m_141902_(p_151452_, p_151453_);
    }

    @Override
    default public List<VoxelShape> m_183134_(@Nullable Entity p_186447_, AABB p_186448_) {
        return EntityGetter.super.m_183134_(p_186447_, p_186448_);
    }

    @Override
    default public boolean m_5450_(@Nullable Entity p_45828_, VoxelShape p_45829_) {
        return EntityGetter.super.m_5450_(p_45828_, p_45829_);
    }

    @Override
    default public BlockPos m_5452_(Heightmap.Types p_45831_, BlockPos p_45832_) {
        return LevelReader.super.m_5452_(p_45831_, p_45832_);
    }

    public RegistryAccess m_5962_();
}

