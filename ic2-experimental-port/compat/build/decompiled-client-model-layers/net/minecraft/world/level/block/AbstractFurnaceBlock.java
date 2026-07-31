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
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractFurnaceBlock
extends BaseEntityBlock {
    public static final DirectionProperty f_48683_ = HorizontalDirectionalBlock.f_54117_;
    public static final BooleanProperty f_48684_ = BlockStateProperties.f_61443_;

    protected AbstractFurnaceBlock(BlockBehaviour.Properties p_48687_) {
        super(p_48687_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_48683_, Direction.NORTH)).m_61124_(f_48684_, false));
    }

    @Override
    public InteractionResult m_6227_(BlockState p_48706_, Level p_48707_, BlockPos p_48708_, Player p_48709_, InteractionHand p_48710_, BlockHitResult p_48711_) {
        if (p_48707_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        this.m_7137_(p_48707_, p_48708_, p_48709_);
        return InteractionResult.CONSUME;
    }

    protected abstract void m_7137_(Level var1, BlockPos var2, Player var3);

    @Override
    public BlockState m_5573_(BlockPlaceContext p_48689_) {
        return (BlockState)this.m_49966_().m_61124_(f_48683_, p_48689_.m_8125_().m_122424_());
    }

    @Override
    public void m_6402_(Level p_48694_, BlockPos p_48695_, BlockState p_48696_, LivingEntity p_48697_, ItemStack p_48698_) {
        BlockEntity $$5;
        if (p_48698_.m_41788_() && ($$5 = p_48694_.m_7702_(p_48695_)) instanceof AbstractFurnaceBlockEntity) {
            ((AbstractFurnaceBlockEntity)$$5).m_58638_(p_48698_.m_41786_());
        }
    }

    @Override
    public void m_6810_(BlockState p_48713_, Level p_48714_, BlockPos p_48715_, BlockState p_48716_, boolean p_48717_) {
        if (p_48713_.m_60713_(p_48716_.m_60734_())) {
            return;
        }
        BlockEntity $$5 = p_48714_.m_7702_(p_48715_);
        if ($$5 instanceof AbstractFurnaceBlockEntity) {
            if (p_48714_ instanceof ServerLevel) {
                Containers.m_19002_(p_48714_, p_48715_, (AbstractFurnaceBlockEntity)$$5);
                ((AbstractFurnaceBlockEntity)$$5).m_154995_((ServerLevel)p_48714_, Vec3.m_82512_(p_48715_));
            }
            p_48714_.m_46717_(p_48715_, this);
        }
        super.m_6810_(p_48713_, p_48714_, p_48715_, p_48716_, p_48717_);
    }

    @Override
    public boolean m_7278_(BlockState p_48700_) {
        return true;
    }

    @Override
    public int m_6782_(BlockState p_48702_, Level p_48703_, BlockPos p_48704_) {
        return AbstractContainerMenu.m_38918_(p_48703_.m_7702_(p_48704_));
    }

    @Override
    public RenderShape m_7514_(BlockState p_48727_) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState m_6843_(BlockState p_48722_, Rotation p_48723_) {
        return (BlockState)p_48722_.m_61124_(f_48683_, p_48723_.m_55954_(p_48722_.m_61143_(f_48683_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_48719_, Mirror p_48720_) {
        return p_48719_.m_60717_(p_48720_.m_54846_(p_48719_.m_61143_(f_48683_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_48725_) {
        p_48725_.m_61104_(f_48683_, f_48684_);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> m_151987_(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<? extends AbstractFurnaceBlockEntity> p_151990_) {
        return p_151988_.f_46443_ ? null : AbstractFurnaceBlock.m_152132_(p_151989_, p_151990_, AbstractFurnaceBlockEntity::m_155013_);
    }
}

