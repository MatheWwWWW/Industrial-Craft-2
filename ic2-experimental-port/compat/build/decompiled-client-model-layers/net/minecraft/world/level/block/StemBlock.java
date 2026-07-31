/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StemBlock
extends BushBlock
implements BonemealableBlock {
    public static final int f_154724_ = 7;
    public static final IntegerProperty f_57013_ = BlockStateProperties.f_61409_;
    protected static final float f_154725_ = 1.0f;
    protected static final VoxelShape[] f_57014_ = new VoxelShape[]{Block.m_49796_(7.0, 0.0, 7.0, 9.0, 2.0, 9.0), Block.m_49796_(7.0, 0.0, 7.0, 9.0, 4.0, 9.0), Block.m_49796_(7.0, 0.0, 7.0, 9.0, 6.0, 9.0), Block.m_49796_(7.0, 0.0, 7.0, 9.0, 8.0, 9.0), Block.m_49796_(7.0, 0.0, 7.0, 9.0, 10.0, 9.0), Block.m_49796_(7.0, 0.0, 7.0, 9.0, 12.0, 9.0), Block.m_49796_(7.0, 0.0, 7.0, 9.0, 14.0, 9.0), Block.m_49796_(7.0, 0.0, 7.0, 9.0, 16.0, 9.0)};
    private final StemGrownBlock f_57015_;
    private final Supplier<Item> f_154726_;

    protected StemBlock(StemGrownBlock p_154728_, Supplier<Item> p_154729_, BlockBehaviour.Properties p_154730_) {
        super(p_154730_);
        this.f_57015_ = p_154728_;
        this.f_154726_ = p_154729_;
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_57013_, 0));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_57047_, BlockGetter p_57048_, BlockPos p_57049_, CollisionContext p_57050_) {
        return f_57014_[p_57047_.m_61143_(f_57013_)];
    }

    @Override
    protected boolean m_6266_(BlockState p_57053_, BlockGetter p_57054_, BlockPos p_57055_) {
        return p_57053_.m_60713_(Blocks.f_50093_);
    }

    @Override
    public void m_213898_(BlockState p_222538_, ServerLevel p_222539_, BlockPos p_222540_, RandomSource p_222541_) {
        if (p_222539_.m_45524_(p_222540_, 0) < 9) {
            return;
        }
        float $$4 = CropBlock.m_52272_(this, p_222539_, p_222540_);
        if (p_222541_.m_188503_((int)(25.0f / $$4) + 1) == 0) {
            int $$5 = p_222538_.m_61143_(f_57013_);
            if ($$5 < 7) {
                p_222538_ = (BlockState)p_222538_.m_61124_(f_57013_, $$5 + 1);
                p_222539_.m_7731_(p_222540_, p_222538_, 2);
            } else {
                Direction $$6 = Direction.Plane.HORIZONTAL.m_235690_(p_222541_);
                BlockPos $$7 = p_222540_.m_121945_($$6);
                BlockState $$8 = p_222539_.m_8055_($$7.m_7495_());
                if (p_222539_.m_8055_($$7).m_60795_() && ($$8.m_60713_(Blocks.f_50093_) || $$8.m_204336_(BlockTags.f_144274_))) {
                    p_222539_.m_46597_($$7, this.f_57015_.m_49966_());
                    p_222539_.m_46597_(p_222540_, (BlockState)this.f_57015_.m_7810_().m_49966_().m_61124_(HorizontalDirectionalBlock.f_54117_, $$6));
                }
            }
        }
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_57026_, BlockPos p_57027_, BlockState p_57028_) {
        return new ItemStack(this.f_154726_.get());
    }

    @Override
    public boolean m_7370_(BlockGetter p_57030_, BlockPos p_57031_, BlockState p_57032_, boolean p_57033_) {
        return p_57032_.m_61143_(f_57013_) != 7;
    }

    @Override
    public boolean m_214167_(Level p_222533_, RandomSource p_222534_, BlockPos p_222535_, BlockState p_222536_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_222528_, RandomSource p_222529_, BlockPos p_222530_, BlockState p_222531_) {
        int $$4 = Math.min(7, p_222531_.m_61143_(f_57013_) + Mth.m_216271_(p_222528_.f_46441_, 2, 5));
        BlockState $$5 = (BlockState)p_222531_.m_61124_(f_57013_, $$4);
        p_222528_.m_7731_(p_222530_, $$5, 2);
        if ($$4 == 7) {
            $$5.m_222972_(p_222528_, p_222530_, p_222528_.f_46441_);
        }
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57040_) {
        p_57040_.m_61104_(f_57013_);
    }

    public StemGrownBlock m_57056_() {
        return this.f_57015_;
    }
}

