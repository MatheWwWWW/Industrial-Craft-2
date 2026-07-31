/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BeetrootBlock
extends CropBlock {
    public static final int f_152186_ = 3;
    public static final IntegerProperty f_49657_ = BlockStateProperties.f_61407_;
    private static final VoxelShape[] f_49658_ = new VoxelShape[]{Block.m_49796_(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)};

    public BeetrootBlock(BlockBehaviour.Properties p_49661_) {
        super(p_49661_);
    }

    @Override
    public IntegerProperty m_7959_() {
        return f_49657_;
    }

    @Override
    public int m_7419_() {
        return 3;
    }

    @Override
    protected ItemLike m_6404_() {
        return Items.f_42733_;
    }

    @Override
    public void m_213898_(BlockState p_220778_, ServerLevel p_220779_, BlockPos p_220780_, RandomSource p_220781_) {
        if (p_220781_.m_188503_(3) != 0) {
            super.m_213898_(p_220778_, p_220779_, p_220780_, p_220781_);
        }
    }

    @Override
    protected int m_7125_(Level p_49663_) {
        return super.m_7125_(p_49663_) / 3;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_49665_) {
        p_49665_.m_61104_(f_49657_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_49672_, BlockGetter p_49673_, BlockPos p_49674_, CollisionContext p_49675_) {
        return f_49658_[p_49672_.m_61143_(this.m_7959_())];
    }
}

