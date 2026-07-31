/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.Shapes;

public class KelpPlantBlock
extends GrowingPlantBodyBlock
implements LiquidBlockContainer {
    protected KelpPlantBlock(BlockBehaviour.Properties p_54323_) {
        super(p_54323_, Direction.UP, Shapes.m_83144_(), true);
    }

    @Override
    protected GrowingPlantHeadBlock m_7272_() {
        return (GrowingPlantHeadBlock)Blocks.f_50575_;
    }

    @Override
    public FluidState m_5888_(BlockState p_54336_) {
        return Fluids.f_76193_.m_76068_(false);
    }

    @Override
    protected boolean m_142209_(BlockState p_153457_) {
        return this.m_7272_().m_142209_(p_153457_);
    }

    @Override
    public boolean m_6044_(BlockGetter p_54325_, BlockPos p_54326_, BlockState p_54327_, Fluid p_54328_) {
        return false;
    }

    @Override
    public boolean m_7361_(LevelAccessor p_54330_, BlockPos p_54331_, BlockState p_54332_, FluidState p_54333_) {
        return false;
    }
}

