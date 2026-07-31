/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CropBlock
extends BushBlock
implements BonemealableBlock {
    public static final int f_153107_ = 7;
    public static final IntegerProperty f_52244_ = BlockStateProperties.f_61409_;
    private static final VoxelShape[] f_52243_ = new VoxelShape[]{Block.m_49796_(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 10.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 12.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 14.0, 16.0), Block.m_49796_(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)};

    protected CropBlock(BlockBehaviour.Properties p_52247_) {
        super(p_52247_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(this.m_7959_(), 0));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_52297_, BlockGetter p_52298_, BlockPos p_52299_, CollisionContext p_52300_) {
        return f_52243_[p_52297_.m_61143_(this.m_7959_())];
    }

    @Override
    protected boolean m_6266_(BlockState p_52302_, BlockGetter p_52303_, BlockPos p_52304_) {
        return p_52302_.m_60713_(Blocks.f_50093_);
    }

    public IntegerProperty m_7959_() {
        return f_52244_;
    }

    public int m_7419_() {
        return 7;
    }

    protected int m_52305_(BlockState p_52306_) {
        return p_52306_.m_61143_(this.m_7959_());
    }

    public BlockState m_52289_(int p_52290_) {
        return (BlockState)this.m_49966_().m_61124_(this.m_7959_(), p_52290_);
    }

    public boolean m_52307_(BlockState p_52308_) {
        return p_52308_.m_61143_(this.m_7959_()) >= this.m_7419_();
    }

    @Override
    public boolean m_6724_(BlockState p_52288_) {
        return !this.m_52307_(p_52288_);
    }

    @Override
    public void m_213898_(BlockState p_221050_, ServerLevel p_221051_, BlockPos p_221052_, RandomSource p_221053_) {
        float $$5;
        int $$4;
        if (p_221051_.m_45524_(p_221052_, 0) >= 9 && ($$4 = this.m_52305_(p_221050_)) < this.m_7419_() && p_221053_.m_188503_((int)(25.0f / ($$5 = CropBlock.m_52272_(this, p_221051_, p_221052_))) + 1) == 0) {
            p_221051_.m_7731_(p_221052_, this.m_52289_($$4 + 1), 2);
        }
    }

    public void m_52263_(Level p_52264_, BlockPos p_52265_, BlockState p_52266_) {
        int $$4;
        int $$3 = this.m_52305_(p_52266_) + this.m_7125_(p_52264_);
        if ($$3 > ($$4 = this.m_7419_())) {
            $$3 = $$4;
        }
        p_52264_.m_7731_(p_52265_, this.m_52289_($$3), 2);
    }

    protected int m_7125_(Level p_52262_) {
        return Mth.m_216271_(p_52262_.f_46441_, 2, 5);
    }

    protected static float m_52272_(Block p_52273_, BlockGetter p_52274_, BlockPos p_52275_) {
        boolean $$14;
        float $$3 = 1.0f;
        BlockPos $$4 = p_52275_.m_7495_();
        for (int $$5 = -1; $$5 <= 1; ++$$5) {
            for (int $$6 = -1; $$6 <= 1; ++$$6) {
                float $$7 = 0.0f;
                BlockState $$8 = p_52274_.m_8055_($$4.m_7918_($$5, 0, $$6));
                if ($$8.m_60713_(Blocks.f_50093_)) {
                    $$7 = 1.0f;
                    if ($$8.m_61143_(FarmBlock.f_53243_) > 0) {
                        $$7 = 3.0f;
                    }
                }
                if ($$5 != 0 || $$6 != 0) {
                    $$7 /= 4.0f;
                }
                $$3 += $$7;
            }
        }
        BlockPos $$9 = p_52275_.m_122012_();
        BlockPos $$10 = p_52275_.m_122019_();
        BlockPos $$11 = p_52275_.m_122024_();
        BlockPos $$12 = p_52275_.m_122029_();
        boolean $$13 = p_52274_.m_8055_($$11).m_60713_(p_52273_) || p_52274_.m_8055_($$12).m_60713_(p_52273_);
        boolean bl = $$14 = p_52274_.m_8055_($$9).m_60713_(p_52273_) || p_52274_.m_8055_($$10).m_60713_(p_52273_);
        if ($$13 && $$14) {
            $$3 /= 2.0f;
        } else {
            boolean $$15;
            boolean bl2 = $$15 = p_52274_.m_8055_($$11.m_122012_()).m_60713_(p_52273_) || p_52274_.m_8055_($$12.m_122012_()).m_60713_(p_52273_) || p_52274_.m_8055_($$12.m_122019_()).m_60713_(p_52273_) || p_52274_.m_8055_($$11.m_122019_()).m_60713_(p_52273_);
            if ($$15) {
                $$3 /= 2.0f;
            }
        }
        return $$3;
    }

    @Override
    public boolean m_7898_(BlockState p_52282_, LevelReader p_52283_, BlockPos p_52284_) {
        return (p_52283_.m_45524_(p_52284_, 0) >= 8 || p_52283_.m_45527_(p_52284_)) && super.m_7898_(p_52282_, p_52283_, p_52284_);
    }

    @Override
    public void m_7892_(BlockState p_52277_, Level p_52278_, BlockPos p_52279_, Entity p_52280_) {
        if (p_52280_ instanceof Ravager && p_52278_.m_46469_().m_46207_(GameRules.f_46132_)) {
            p_52278_.m_46953_(p_52279_, true, p_52280_);
        }
        super.m_7892_(p_52277_, p_52278_, p_52279_, p_52280_);
    }

    protected ItemLike m_6404_() {
        return Items.f_42404_;
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_52254_, BlockPos p_52255_, BlockState p_52256_) {
        return new ItemStack(this.m_6404_());
    }

    @Override
    public boolean m_7370_(BlockGetter p_52258_, BlockPos p_52259_, BlockState p_52260_, boolean p_52261_) {
        return !this.m_52307_(p_52260_);
    }

    @Override
    public boolean m_214167_(Level p_221045_, RandomSource p_221046_, BlockPos p_221047_, BlockState p_221048_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_221040_, RandomSource p_221041_, BlockPos p_221042_, BlockState p_221043_) {
        this.m_52263_(p_221040_, p_221042_, p_221043_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_52286_) {
        p_52286_.m_61104_(f_52244_);
    }
}

