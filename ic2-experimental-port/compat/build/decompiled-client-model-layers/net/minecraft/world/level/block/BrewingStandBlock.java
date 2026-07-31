/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BrewingStandBlock
extends BaseEntityBlock {
    public static final BooleanProperty[] f_50905_ = new BooleanProperty[]{BlockStateProperties.f_61436_, BlockStateProperties.f_61437_, BlockStateProperties.f_61438_};
    protected static final VoxelShape f_50906_ = Shapes.m_83110_(Block.m_49796_(1.0, 0.0, 1.0, 15.0, 2.0, 15.0), Block.m_49796_(7.0, 0.0, 7.0, 9.0, 14.0, 9.0));

    public BrewingStandBlock(BlockBehaviour.Properties p_50909_) {
        super(p_50909_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_50905_[0], false)).m_61124_(f_50905_[1], false)).m_61124_(f_50905_[2], false));
    }

    @Override
    public RenderShape m_7514_(BlockState p_50950_) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_152698_, BlockState p_152699_) {
        return new BrewingStandBlockEntity(p_152698_, p_152699_);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152694_, BlockState p_152695_, BlockEntityType<T> p_152696_) {
        return p_152694_.f_46443_ ? null : BrewingStandBlock.m_152132_(p_152696_, BlockEntityType.f_58927_, BrewingStandBlockEntity::m_155285_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_50952_, BlockGetter p_50953_, BlockPos p_50954_, CollisionContext p_50955_) {
        return f_50906_;
    }

    @Override
    public InteractionResult m_6227_(BlockState p_50930_, Level p_50931_, BlockPos p_50932_, Player p_50933_, InteractionHand p_50934_, BlockHitResult p_50935_) {
        if (p_50931_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        BlockEntity $$6 = p_50931_.m_7702_(p_50932_);
        if ($$6 instanceof BrewingStandBlockEntity) {
            p_50933_.m_5893_((BrewingStandBlockEntity)$$6);
            p_50933_.m_36220_(Stats.f_12948_);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void m_6402_(Level p_50913_, BlockPos p_50914_, BlockState p_50915_, LivingEntity p_50916_, ItemStack p_50917_) {
        BlockEntity $$5;
        if (p_50917_.m_41788_() && ($$5 = p_50913_.m_7702_(p_50914_)) instanceof BrewingStandBlockEntity) {
            ((BrewingStandBlockEntity)$$5).m_58638_(p_50917_.m_41786_());
        }
    }

    @Override
    public void m_214162_(BlockState p_220883_, Level p_220884_, BlockPos p_220885_, RandomSource p_220886_) {
        double $$4 = (double)p_220885_.m_123341_() + 0.4 + (double)p_220886_.m_188501_() * 0.2;
        double $$5 = (double)p_220885_.m_123342_() + 0.7 + (double)p_220886_.m_188501_() * 0.3;
        double $$6 = (double)p_220885_.m_123343_() + 0.4 + (double)p_220886_.m_188501_() * 0.2;
        p_220884_.m_7106_(ParticleTypes.f_123762_, $$4, $$5, $$6, 0.0, 0.0, 0.0);
    }

    @Override
    public void m_6810_(BlockState p_50937_, Level p_50938_, BlockPos p_50939_, BlockState p_50940_, boolean p_50941_) {
        if (p_50937_.m_60713_(p_50940_.m_60734_())) {
            return;
        }
        BlockEntity $$5 = p_50938_.m_7702_(p_50939_);
        if ($$5 instanceof BrewingStandBlockEntity) {
            Containers.m_19002_(p_50938_, p_50939_, (BrewingStandBlockEntity)$$5);
        }
        super.m_6810_(p_50937_, p_50938_, p_50939_, p_50940_, p_50941_);
    }

    @Override
    public boolean m_7278_(BlockState p_50919_) {
        return true;
    }

    @Override
    public int m_6782_(BlockState p_50926_, Level p_50927_, BlockPos p_50928_) {
        return AbstractContainerMenu.m_38918_(p_50927_.m_7702_(p_50928_));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_50948_) {
        p_50948_.m_61104_(f_50905_[0], f_50905_[1], f_50905_[2]);
    }

    @Override
    public boolean m_7357_(BlockState p_50921_, BlockGetter p_50922_, BlockPos p_50923_, PathComputationType p_50924_) {
        return false;
    }
}

