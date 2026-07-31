/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 */
package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.BlockSourceImpl;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.PositionImpl;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class DispenserBlock
extends BaseEntityBlock {
    public static final DirectionProperty f_52659_ = DirectionalBlock.f_52588_;
    public static final BooleanProperty f_52660_ = BlockStateProperties.f_61360_;
    private static final Map<Item, DispenseItemBehavior> f_52661_ = (Map)Util.m_137469_(new Object2ObjectOpenHashMap(), p_52723_ -> p_52723_.defaultReturnValue((Object)new DefaultDispenseItemBehavior()));
    private static final int f_153160_ = 4;

    public static void m_52672_(ItemLike p_52673_, DispenseItemBehavior p_52674_) {
        f_52661_.put(p_52673_.m_5456_(), p_52674_);
    }

    protected DispenserBlock(BlockBehaviour.Properties p_52664_) {
        super(p_52664_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_52659_, Direction.NORTH)).m_61124_(f_52660_, false));
    }

    @Override
    public InteractionResult m_6227_(BlockState p_52693_, Level p_52694_, BlockPos p_52695_, Player p_52696_, InteractionHand p_52697_, BlockHitResult p_52698_) {
        if (p_52694_.f_46443_) {
            return InteractionResult.SUCCESS;
        }
        BlockEntity $$6 = p_52694_.m_7702_(p_52695_);
        if ($$6 instanceof DispenserBlockEntity) {
            p_52696_.m_5893_((DispenserBlockEntity)$$6);
            if ($$6 instanceof DropperBlockEntity) {
                p_52696_.m_36220_(Stats.f_12956_);
            } else {
                p_52696_.m_36220_(Stats.f_12958_);
            }
        }
        return InteractionResult.CONSUME;
    }

    protected void m_5824_(ServerLevel p_52665_, BlockPos p_52666_) {
        BlockSourceImpl $$2 = new BlockSourceImpl(p_52665_, p_52666_);
        DispenserBlockEntity $$3 = (DispenserBlockEntity)$$2.m_8118_();
        int $$4 = $$3.m_222761_(p_52665_.f_46441_);
        if ($$4 < 0) {
            p_52665_.m_46796_(1001, p_52666_, 0);
            p_52665_.m_142346_(null, GameEvent.f_157804_, p_52666_);
            return;
        }
        ItemStack $$5 = $$3.m_8020_($$4);
        DispenseItemBehavior $$6 = this.m_7216_($$5);
        if ($$6 != DispenseItemBehavior.f_123393_) {
            $$3.m_6836_($$4, $$6.m_6115_($$2, $$5));
        }
    }

    protected DispenseItemBehavior m_7216_(ItemStack p_52667_) {
        return f_52661_.get(p_52667_.m_41720_());
    }

    @Override
    public void m_6861_(BlockState p_52700_, Level p_52701_, BlockPos p_52702_, Block p_52703_, BlockPos p_52704_, boolean p_52705_) {
        boolean $$6 = p_52701_.m_46753_(p_52702_) || p_52701_.m_46753_(p_52702_.m_7494_());
        boolean $$7 = p_52700_.m_61143_(f_52660_);
        if ($$6 && !$$7) {
            p_52701_.m_186460_(p_52702_, this, 4);
            p_52701_.m_7731_(p_52702_, (BlockState)p_52700_.m_61124_(f_52660_, true), 4);
        } else if (!$$6 && $$7) {
            p_52701_.m_7731_(p_52702_, (BlockState)p_52700_.m_61124_(f_52660_, false), 4);
        }
    }

    @Override
    public void m_213897_(BlockState p_221075_, ServerLevel p_221076_, BlockPos p_221077_, RandomSource p_221078_) {
        this.m_5824_(p_221076_, p_221077_);
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_153162_, BlockState p_153163_) {
        return new DispenserBlockEntity(p_153162_, p_153163_);
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_52669_) {
        return (BlockState)this.m_49966_().m_61124_(f_52659_, p_52669_.m_7820_().m_122424_());
    }

    @Override
    public void m_6402_(Level p_52676_, BlockPos p_52677_, BlockState p_52678_, LivingEntity p_52679_, ItemStack p_52680_) {
        BlockEntity $$5;
        if (p_52680_.m_41788_() && ($$5 = p_52676_.m_7702_(p_52677_)) instanceof DispenserBlockEntity) {
            ((DispenserBlockEntity)$$5).m_58638_(p_52680_.m_41786_());
        }
    }

    @Override
    public void m_6810_(BlockState p_52707_, Level p_52708_, BlockPos p_52709_, BlockState p_52710_, boolean p_52711_) {
        if (p_52707_.m_60713_(p_52710_.m_60734_())) {
            return;
        }
        BlockEntity $$5 = p_52708_.m_7702_(p_52709_);
        if ($$5 instanceof DispenserBlockEntity) {
            Containers.m_19002_(p_52708_, p_52709_, (DispenserBlockEntity)$$5);
            p_52708_.m_46717_(p_52709_, this);
        }
        super.m_6810_(p_52707_, p_52708_, p_52709_, p_52710_, p_52711_);
    }

    public static Position m_52720_(BlockSource p_52721_) {
        Direction $$1 = p_52721_.m_6414_().m_61143_(f_52659_);
        double $$2 = p_52721_.m_7096_() + 0.7 * (double)$$1.m_122429_();
        double $$3 = p_52721_.m_7098_() + 0.7 * (double)$$1.m_122430_();
        double $$4 = p_52721_.m_7094_() + 0.7 * (double)$$1.m_122431_();
        return new PositionImpl($$2, $$3, $$4);
    }

    @Override
    public boolean m_7278_(BlockState p_52682_) {
        return true;
    }

    @Override
    public int m_6782_(BlockState p_52689_, Level p_52690_, BlockPos p_52691_) {
        return AbstractContainerMenu.m_38918_(p_52690_.m_7702_(p_52691_));
    }

    @Override
    public RenderShape m_7514_(BlockState p_52725_) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState m_6843_(BlockState p_52716_, Rotation p_52717_) {
        return (BlockState)p_52716_.m_61124_(f_52659_, p_52717_.m_55954_(p_52716_.m_61143_(f_52659_)));
    }

    @Override
    public BlockState m_6943_(BlockState p_52713_, Mirror p_52714_) {
        return p_52713_.m_60717_(p_52714_.m_54846_(p_52713_.m_61143_(f_52659_)));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_52719_) {
        p_52719_.m_61104_(f_52659_, f_52660_);
    }
}

