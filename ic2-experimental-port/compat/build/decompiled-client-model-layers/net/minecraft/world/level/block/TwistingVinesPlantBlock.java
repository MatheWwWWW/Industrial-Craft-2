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

public class TwistingVinesPlantBlock
extends GrowingPlantBodyBlock {
    public static final VoxelShape f_154870_ = Block.m_49796_(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

    public TwistingVinesPlantBlock(BlockBehaviour.Properties p_154873_) {
        super(p_154873_, Direction.UP, f_154870_, false);
    }

    @Override
    protected GrowingPlantHeadBlock m_7272_() {
        return (GrowingPlantHeadBlock)Blocks.f_50704_;
    }
}

