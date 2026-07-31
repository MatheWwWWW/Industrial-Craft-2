/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 */
package ic2.core.item.tool;

import ic2.api.item.IBoxable;
import ic2.core.block.misc.RubberLogBlock;
import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2GameEvents;
import ic2.core.ref.Ic2Items;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.StackUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class ItemTreetap
extends Item
implements IBoxable {
    public ItemTreetap(Item.Properties properties) {
        super(properties);
    }

    public InteractionResult m_6225_(UseOnContext useOnContext) {
        BlockPos blockPos;
        Level level = useOnContext.m_43725_();
        BlockState blockState = level.m_8055_(blockPos = useOnContext.m_8083_());
        Block block = blockState.m_60734_();
        if (block == Ic2Blocks.RUBBER_LOG) {
            Player player = useOnContext.m_43723_();
            if (ItemTreetap.attemptExtract(player, level, blockPos, useOnContext.m_43719_(), blockState, null, false)) {
                StackUtil.damage(player, useOnContext.m_43724_(), StackUtil.anyStack, 1);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }

    public static boolean attemptExtract(Player player, Level level, BlockPos blockPos, Direction direction, BlockState blockState, List<ItemStack> list, boolean bl) {
        assert (blockState.m_60734_() == Ic2Blocks.RUBBER_LOG);
        RubberLogBlock.RubberWoodState rubberWoodState = (RubberLogBlock.RubberWoodState)((Object)blockState.m_61143_(RubberLogBlock.stateProperty));
        if (rubberWoodState.isPlain() || rubberWoodState.facing != direction) {
            return false;
        }
        if (rubberWoodState.wet) {
            if (!level.f_46443_) {
                level.m_46597_(blockPos, (BlockState)blockState.m_61124_(RubberLogBlock.stateProperty, (Comparable)((Object)rubberWoodState.getDry())));
                if (list != null) {
                    list.add(StackUtil.copyWithSize(new ItemStack((ItemLike)Ic2Items.RESIN), level.f_46441_.m_188503_(3) + 1));
                } else {
                    ItemTreetap.ejectResin(level, blockPos, direction, level.f_46441_.m_188503_(3) + 1);
                }
            }
            ItemTreetap.triggerToolUseEvent(level, blockPos, player, blockState, bl);
            return true;
        }
        boolean bl2 = false;
        if (!level.f_46443_) {
            if (level.f_46441_.m_188503_(5) == 0) {
                level.m_46597_(blockPos, (BlockState)blockState.m_61124_(RubberLogBlock.stateProperty, (Comparable)((Object)RubberLogBlock.RubberWoodState.plain)));
                ItemTreetap.triggerToolUseEvent(level, blockPos, player, blockState, bl);
                bl2 = true;
            }
            if (level.f_46441_.m_188503_(5) == 0) {
                ItemTreetap.ejectResin(level, blockPos, direction, 1);
                if (list != null) {
                    list.add(new ItemStack((ItemLike)Ic2Items.RESIN));
                } else {
                    ItemTreetap.ejectResin(level, blockPos, direction, 1);
                }
                ItemTreetap.triggerToolUseEvent(level, blockPos, player, blockState, bl);
                bl2 = true;
            }
        }
        return bl2;
    }

    private static void triggerToolUseEvent(Level level, BlockPos blockPos, Player player, BlockState blockState, boolean bl) {
        player.m_6330_(ItemTreetap.getToolUseSound(bl), SoundSource.PLAYERS, 1.0f, 1.0f);
        level.m_220407_(GameEvent.f_157792_, blockPos, GameEvent.Context.m_223719_((Entity)player, (BlockState)blockState));
        level.m_220407_(Ic2GameEvents.TOOL_USE, blockPos, GameEvent.Context.m_223719_((Entity)player, null));
    }

    private static void ejectResin(Level level, BlockPos blockPos, Direction direction, int n) {
        double d = (double)blockPos.m_123341_() + 0.5 + (double)direction.m_122429_() * 0.3;
        double d2 = (double)blockPos.m_123342_() + 0.5 + (double)direction.m_122430_() * 0.3;
        double d3 = (double)blockPos.m_123343_() + 0.5 + (double)direction.m_122431_() * 0.3;
        for (int i = 0; i < n; ++i) {
            ItemEntity itemEntity = new ItemEntity(level, d, d2, d3, new ItemStack((ItemLike)Ic2Items.RESIN));
            itemEntity.m_32060_();
            level.m_7967_((Entity)itemEntity);
        }
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack itemStack) {
        return true;
    }

    public static SoundEvent getToolUseSound(boolean bl) {
        return bl ? Ic2SoundEvents.ITEM_TREETAP_ELECTRIC_USE : Ic2SoundEvents.ITEM_TREETAP_USE;
    }
}

