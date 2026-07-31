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
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EnderChestBlock
extends AbstractChestBlock<EnderChestBlockEntity>
implements SimpleWaterloggedBlock {
    public static final DirectionProperty f_53115_ = HorizontalDirectionalBlock.f_54117_;
    public static final BooleanProperty f_53116_ = BlockStateProperties.f_61362_;
    protected static final VoxelShape f_53117_ = Block.m_49796_(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
    private static final Component f_53118_ = Component.m_237115_("container.enderchest");

    protected EnderChestBlock(BlockBehaviour.Properties p_53121_) {
        super(p_53121_, () -> BlockEntityType.f_58920_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_53115_, Direction.NORTH)).m_61124_(f_53116_, false));
    }

    @Override
    public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> m_5641_(BlockState p_53149_, Level p_53150_, BlockPos p_53151_, boolean p_53152_) {
        return DoubleBlockCombiner.Combiner::m_6502_;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_53171_, BlockGetter p_53172_, BlockPos p_53173_, CollisionContext p_53174_) {
        return f_53117_;
    }

    @Override
    public RenderShape m_7514_(BlockState p_53169_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_53128_) {
        FluidState $$1 = p_53128_.m_43725_().m_6425_(p_53128_.m_8083_());
        return (BlockState)((BlockState)this.m_49966_().m_61124_(f_53115_, p_53128_.m_8125_().m_122424_())).m_61124_(f_53116_, $$1.m_76152_() == Fluids.f_76193_);
    }

    @Override
    public InteractionResult m_6227_(BlockState p_53137_, Level p_53138_, BlockPos p_53139_, Player p_53140_, InteractionHand p_53141_, BlockHitResult p_53142_) {
        PlayerEnderChestContainer $$6 = p_53140_.m_36327_();
        BlockEntity $$7 = p_53138_.m_7702_(p_53139_);
        if ($$6 == null || !($$7 instanceof EnderChestBlockEntity)) {
            return InteractionResult.m_19078_(p_53138_.f_46443_);
        }
        BlockPos $$8 = p_53139_.m_7494_();
        if (p_53138_.m_8055_($$8).m_60796_(p_53138_, $$8)) {
            return InteractionResult.m_19078_(p_53138_.f_46443_);
        }
        if (p_53138_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        EnderChestBlockEntity $$9 = (EnderChestBlockEntity)$$7;
        $$6.m_40105_($$9);
        p_53140_.m_5893_(new SimpleMenuProvider((p_53124_, p_53125_, p_53126_) -> ChestMenu.m_39237_(p_53124_, p_53125_, $$6), f_53118_));
        p_53140_.m_36220_(Stats.f_12963_);
        PiglinAi.m_34873_(p_53140_, true);
        return InteractionResult.CONSUME;
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_153208_, BlockState p_153209_) {
        return new EnderChestBlockEntity(p_153208_, p_153209_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_153199_, BlockState p_153200_, BlockEntityType<T> p_153201_) {
        return p_153199_.f_46443_ ? EnderChestBlock.m_152132_(p_153201_, BlockEntityType.f_58920_, EnderChestBlockEntity::m_155517_) : null;
    }

    @Override
    public void m_214162_(BlockState p_221117_, Level p_221118_, BlockPos p_221119_, RandomSource p_221120_) {
        for (int $$4 = 0; $$4 < 3; ++$$4) {
            int $$5 = p_221120_.m_188503_(2) * 2 - 1;
            int $$6 = p_221120_.m_188503_(2) * 2 - 1;
            double $$7 = (double)p_221119_.m_123341_() + 0.5 + 0.25 * (double)$$5;
            double $$8 = (float)p_221119_.m_123342_() + p_221120_.m_188501_();
            double $$9 = (double)p_221119_.m_123343_() + 0.5 + 0.25 * (double)$$6;
            double $$10 = p_221120_.m_188501_() * (float)$$5;
            double $$11 = ((double)p_221120_.m_188501_() - 0.5) * 0.125;
            double $$12 = p_221120_.m_188501_() * (float)$$6;
            p_221118_.m_7106_(ParticleTypes.f_123760_, $$7, $$8, $$9, $$10, $$11, $$12);
        }
    }

    @Override
    public BlockState m_6843_(BlockState p_53157_, Rotation p_53158_) {
        return (BlockState)p_53157_.m_61124_(f_53115_, p_53158_.m_55954_(p_53157_.m_61143_(f_53115_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_53154_, Mirror p_53155_) {
        return p_53154_.m_60717_(p_53155_.m_54846_(p_53154_.m_61143_(f_53115_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53167_) {
        p_53167_.m_61104_(f_53115_, f_53116_);
    }

    @Override
    public FluidState m_5888_(BlockState p_53177_) {
        if (p_53177_.m_61143_(f_53116_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_53177_);
    }

    @Override
    public BlockState m_7417_(BlockState p_53160_, Direction p_53161_, BlockState p_53162_, LevelAccessor p_53163_, BlockPos p_53164_, BlockPos p_53165_) {
        if (p_53160_.m_61143_(f_53116_).booleanValue()) {
            p_53163_.m_186469_(p_53164_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_53163_));
        }
        return super.m_7417_(p_53160_, p_53161_, p_53162_, p_53163_, p_53164_, p_53165_);
    }

    @Override
    public boolean m_7357_(BlockState p_53132_, BlockGetter p_53133_, BlockPos p_53134_, PathComputationType p_53135_) {
        return false;
    }

    @Override
    public void m_213897_(BlockState p_221112_, ServerLevel p_221113_, BlockPos p_221114_, RandomSource p_221115_) {
        BlockEntity $$4 = p_221113_.m_7702_(p_221114_);
        if ($$4 instanceof EnderChestBlockEntity) {
            ((EnderChestBlockEntity)$$4).m_155524_();
        }
    }
}

