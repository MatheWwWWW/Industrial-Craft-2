/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NetherWartBlock
extends BushBlock {
    public static final int f_153989_ = 3;
    public static final IntegerProperty f_54967_ = BlockStateProperties.f_61407_;
    private static final VoxelShape[] f_54968_ = new VoxelShape[]{Block.m_49796_(0.0, 0.0, 0.0, 16.0, 5.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 11.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 14.0, 16.0)};

    protected NetherWartBlock(BlockBehaviour.Properties p_54971_) {
        super(p_54971_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54967_, 0));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_54986_, BlockGetter p_54987_, BlockPos p_54988_, CollisionContext p_54989_) {
        return f_54968_[p_54986_.m_61143_(f_54967_)];
    }

    @Override
    protected boolean m_6266_(BlockState p_54991_, BlockGetter p_54992_, BlockPos p_54993_) {
        return p_54991_.m_60713_(Blocks.f_50135_);
    }

    @Override
    public boolean m_6724_(BlockState p_54979_) {
        return p_54979_.m_61143_(f_54967_) < 3;
    }

    @Override
    public void m_213898_(BlockState p_221806_, ServerLevel p_221807_, BlockPos p_221808_, RandomSource p_221809_) {
        int $$4 = p_221806_.m_61143_(f_54967_);
        if ($$4 < 3 && p_221809_.m_188503_(10) == 0) {
            p_221806_ = (BlockState)p_221806_.m_61124_(f_54967_, $$4 + 1);
            p_221807_.m_7731_(p_221808_, p_221806_, 2);
        }
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_54973_, BlockPos p_54974_, BlockState p_54975_) {
        return new ItemStack(Items.f_42588_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54977_) {
        p_54977_.m_61104_(f_54967_);
    }
}

