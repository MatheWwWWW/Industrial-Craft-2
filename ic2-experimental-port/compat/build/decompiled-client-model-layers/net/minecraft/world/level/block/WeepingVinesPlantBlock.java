/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WeepingVinesPlantBlock
extends GrowingPlantBodyBlock {
    public static final VoxelShape f_154972_ = Block.m_49796_(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public WeepingVinesPlantBlock(BlockBehaviour.Properties p_154975_) {
        super(p_154975_, Direction.DOWN, f_154972_, false);
    }

    @Override
    protected GrowingPlantHeadBlock m_7272_() {
        return (GrowingPlantHeadBlock)Blocks.f_50702_;
    }
}

