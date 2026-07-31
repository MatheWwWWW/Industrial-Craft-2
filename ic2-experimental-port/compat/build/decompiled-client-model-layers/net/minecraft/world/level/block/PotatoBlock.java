/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PotatoBlock
extends CropBlock {
    private static final VoxelShape[] f_55195_ = new VoxelShape[]{Block.m_49796_(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 5.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 7.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 9.0, 16.0)};

    public PotatoBlock(BlockBehaviour.Properties p_55198_) {
        super(p_55198_);
    }

    @Override
    protected ItemLike m_6404_() {
        return Items.f_42620_;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_55200_, BlockGetter p_55201_, BlockPos p_55202_, CollisionContext p_55203_) {
        return f_55195_[p_55200_.m_61143_(this.m_7959_())];
    }
}

