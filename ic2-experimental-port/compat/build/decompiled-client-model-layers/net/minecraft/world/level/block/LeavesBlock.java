/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LeavesBlock
extends Block
implements SimpleWaterloggedBlock {
    public static final int f_153563_ = 7;
    public static final IntegerProperty f_54418_ = BlockStateProperties.f_61414_;
    public static final BooleanProperty f_54419_ = BlockStateProperties.f_61447_;
    public static final BooleanProperty f_221367_ = BlockStateProperties.f_61362_;
    private static final int f_153564_ = 1;

    public LeavesBlock(BlockBehaviour.Properties p_54422_) {
        super(p_54422_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54418_, 7)).m_61124_(f_54419_, false)).m_61124_(f_221367_, false));
    }

    @Override
    public VoxelShape m_7947_(BlockState p_54456_, BlockGetter p_54457_, BlockPos p_54458_) {
        return Shapes.m_83040_();
    }

    @Override
    public boolean m_6724_(BlockState p_54449_) {
        return p_54449_.m_61143_(f_54418_) == 7 && p_54449_.m_61143_(f_54419_) == false;
    }

    @Override
    public void m_213898_(BlockState p_221379_, ServerLevel p_221380_, BlockPos p_221381_, RandomSource p_221382_) {
        if (this.m_221385_(p_221379_)) {
            LeavesBlock.m_49950_(p_221379_, p_221380_, p_221381_);
            p_221380_.m_7471_(p_221381_, false);
        }
    }

    protected boolean m_221385_(BlockState p_221386_) {
        return p_221386_.m_61143_(f_54419_) == false && p_221386_.m_61143_(f_54418_) == 7;
    }

    @Override
    public void m_213897_(BlockState p_221369_, ServerLevel p_221370_, BlockPos p_221371_, RandomSource p_221372_) {
        p_221370_.m_7731_(p_221371_, LeavesBlock.m_54435_(p_221369_, p_221370_, p_221371_), 3);
    }

    @Override
    public int m_7753_(BlockState p_54460_, BlockGetter p_54461_, BlockPos p_54462_) {
        return 1;
    }

    @Override
    public BlockState m_7417_(BlockState p_54440_, Direction p_54441_, BlockState p_54442_, LevelAccessor p_54443_, BlockPos p_54444_, BlockPos p_54445_) {
        int $$6;
        if (p_54440_.m_61143_(f_221367_).booleanValue()) {
            p_54443_.m_186469_(p_54444_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_54443_));
        }
        if (($$6 = LeavesBlock.m_54463_(p_54442_) + 1) != 1 || p_54440_.m_61143_(f_54418_) != $$6) {
            p_54443_.m_186460_(p_54444_, this, 1);
        }
        return p_54440_;
    }

    private static BlockState m_54435_(BlockState p_54436_, LevelAccessor p_54437_, BlockPos p_54438_) {
        int $$3 = 7;
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        for (Direction $$5 : Direction.values()) {
            $$4.m_122159_(p_54438_, $$5);
            $$3 = Math.min($$3, LeavesBlock.m_54463_(p_54437_.m_8055_($$4)) + 1);
            if ($$3 == 1) break;
        }
        return (BlockState)p_54436_.m_61124_(f_54418_, $$3);
    }

    private static int m_54463_(BlockState p_54464_) {
        if (p_54464_.m_204336_(BlockTags.f_13106_)) {
            return 0;
        }
        if (p_54464_.m_60734_() instanceof LeavesBlock) {
            return p_54464_.m_61143_(f_54418_);
        }
        return 7;
    }

    @Override
    public FluidState m_5888_(BlockState p_221384_) {
        if (p_221384_.m_61143_(f_221367_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_221384_);
    }

    @Override
    public void m_214162_(BlockState p_221374_, Level p_221375_, BlockPos p_221376_, RandomSource p_221377_) {
        if (!p_221375_.m_46758_(p_221376_.m_7494_())) {
            return;
        }
        if (p_221377_.m_188503_(15) != 1) {
            return;
        }
        BlockPos $$4 = p_221376_.m_7495_();
        BlockState $$5 = p_221375_.m_8055_($$4);
        if ($$5.m_60815_() && $$5.m_60783_(p_221375_, $$4, Direction.UP)) {
            return;
        }
        double $$6 = (double)p_221376_.m_123341_() + p_221377_.m_188500_();
        double $$7 = (double)p_221376_.m_123342_() - 0.05;
        double $$8 = (double)p_221376_.m_123343_() + p_221377_.m_188500_();
        p_221375_.m_7106_(ParticleTypes.f_123803_, $$6, $$7, $$8, 0.0, 0.0, 0.0);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54447_) {
        p_54447_.m_61104_(f_54418_, f_54419_, f_221367_);
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_54424_) {
        FluidState $$1 = p_54424_.m_43725_().m_6425_(p_54424_.m_8083_());
        BlockState $$2 = (BlockState)((BlockState)this.m_49966_().m_61124_(f_54419_, true)).m_61124_(f_221367_, $$1.m_76152_() == Fluids.f_76193_);
        return LeavesBlock.m_54435_($$2, p_54424_.m_43725_(), p_54424_.m_8083_());
    }
}

