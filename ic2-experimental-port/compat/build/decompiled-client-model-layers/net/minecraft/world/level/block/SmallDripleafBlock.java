/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BigDripleafBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmallDripleafBlock
extends DoublePlantBlock
implements BonemealableBlock,
SimpleWaterloggedBlock {
    private static final BooleanProperty f_154580_ = BlockStateProperties.f_61362_;
    public static final DirectionProperty f_154577_ = BlockStateProperties.f_61374_;
    protected static final float f_154578_ = 6.0f;
    protected static final VoxelShape f_154579_ = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public SmallDripleafBlock(BlockBehaviour.Properties p_154583_) {
        super(p_154583_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_52858_, DoubleBlockHalf.LOWER)).m_61124_(f_154580_, false)).m_61124_(f_154577_, Direction.NORTH));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_154610_, BlockGetter p_154611_, BlockPos p_154612_, CollisionContext p_154613_) {
        return f_154579_;
    }

    @Override
    protected boolean m_6266_(BlockState p_154636_, BlockGetter p_154637_, BlockPos p_154638_) {
        return p_154636_.m_204336_(BlockTags.f_144278_) || p_154637_.m_6425_(p_154638_.m_7494_()).m_164512_(Fluids.f_76193_) && super.m_6266_(p_154636_, p_154637_, p_154638_);
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_154592_) {
        BlockState $$1 = super.m_5573_(p_154592_);
        if ($$1 != null) {
            return SmallDripleafBlock.m_182453_(p_154592_.m_43725_(), p_154592_.m_8083_(), (BlockState)$$1.m_61124_(f_154577_, p_154592_.m_8125_().m_122424_()));
        }
        return null;
    }

    @Override
    public void m_6402_(Level p_154599_, BlockPos p_154600_, BlockState p_154601_, LivingEntity p_154602_, ItemStack p_154603_) {
        if (!p_154599_.m_5776_()) {
            BlockPos $$5 = p_154600_.m_7494_();
            BlockState $$6 = DoublePlantBlock.m_182453_(p_154599_, $$5, (BlockState)((BlockState)this.m_49966_().m_61124_(f_52858_, DoubleBlockHalf.UPPER)).m_61124_(f_154577_, p_154601_.m_61143_(f_154577_)));
            p_154599_.m_7731_($$5, $$6, 3);
        }
    }

    @Override
    public FluidState m_5888_(BlockState p_154634_) {
        if (p_154634_.m_61143_(f_154580_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_154634_);
    }

    @Override
    public boolean m_7898_(BlockState p_154615_, LevelReader p_154616_, BlockPos p_154617_) {
        if (p_154615_.m_61143_(f_52858_) == DoubleBlockHalf.UPPER) {
            return super.m_7898_(p_154615_, p_154616_, p_154617_);
        }
        BlockPos $$3 = p_154617_.m_7495_();
        BlockState $$4 = p_154616_.m_8055_($$3);
        return this.m_6266_($$4, p_154616_, $$3);
    }

    @Override
    public BlockState m_7417_(BlockState p_154625_, Direction p_154626_, BlockState p_154627_, LevelAccessor p_154628_, BlockPos p_154629_, BlockPos p_154630_) {
        if (p_154625_.m_61143_(f_154580_).booleanValue()) {
            p_154628_.m_186469_(p_154629_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_154628_));
        }
        return super.m_7417_(p_154625_, p_154626_, p_154627_, p_154628_, p_154629_, p_154630_);
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_154632_) {
        p_154632_.m_61104_(f_52858_, f_154580_, f_154577_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_154594_, BlockPos p_154595_, BlockState p_154596_, boolean p_154597_) {
        return true;
    }

    @Override
    public boolean m_214167_(Level p_222438_, RandomSource p_222439_, BlockPos p_222440_, BlockState p_222441_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_222433_, RandomSource p_222434_, BlockPos p_222435_, BlockState p_222436_) {
        if (p_222436_.m_61143_(DoublePlantBlock.f_52858_) == DoubleBlockHalf.LOWER) {
            BlockPos $$4 = p_222435_.m_7494_();
            p_222433_.m_7731_($$4, p_222433_.m_6425_($$4).m_76188_(), 18);
            BigDripleafBlock.m_220792_(p_222433_, p_222434_, p_222435_, p_222436_.m_61143_(f_154577_));
        } else {
            BlockPos $$5 = p_222435_.m_7495_();
            this.m_214148_(p_222433_, p_222434_, $$5, p_222433_.m_8055_($$5));
        }
    }

    @Override
    public BlockState m_6843_(BlockState p_154622_, Rotation p_154623_) {
        return (BlockState)p_154622_.m_61124_(f_154577_, p_154623_.m_55954_(p_154622_.m_61143_(f_154577_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_154619_, Mirror p_154620_) {
        return p_154619_.m_60717_(p_154620_.m_54846_(p_154619_.m_61143_(f_154577_)));
    }

    @Override
    public float m_142627_() {
        return 0.1f;
    }
}

