/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Direction$AxisDirection
 *  net.minecraft.core.Registry
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.item.tool;

import ic2.api.item.IBoxable;
import ic2.api.tile.IWrenchable;
import ic2.core.IC2;
import ic2.core.init.MainConfig;
import ic2.core.item.PriorityUsableItem;
import ic2.core.ref.Ic2ItemTags;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class ItemToolWrench
extends Item
implements PriorityUsableItem,
IBoxable {
    private static final boolean logEmptyWrenchDrops = ConfigUtil.getBool(MainConfig.get(), "debug/logEmptyWrenchDrops");
    public static final int wrenchUseFailed = -1;
    public static final int wrenchUsePass = -2;

    public ItemToolWrench(Item.Properties properties) {
        super(properties);
    }

    public boolean canTakeDamage(ItemStack itemStack, int n) {
        return true;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext useOnContext) {
        if (!this.canTakeDamage(itemStack, 1)) {
            return InteractionResult.FAIL;
        }
        Player player = useOnContext.m_43723_();
        if (player == null) {
            return InteractionResult.PASS;
        }
        int n = ItemToolWrench.onWrenchUse(player, itemStack, useOnContext, this.canTakeDamage(itemStack, 10));
        switch (n) {
            case -2: {
                return InteractionResult.PASS;
            }
            case -1: {
                return InteractionResult.FAIL;
            }
        }
        this.damage(itemStack, n, player, useOnContext.m_43724_());
        return InteractionResult.SUCCESS;
    }

    public static int onWrenchUse(Player player, ItemStack itemStack, UseOnContext useOnContext, boolean bl) {
        WrenchResult wrenchResult = ItemToolWrench.wrenchBlock(useOnContext.m_43725_(), useOnContext.m_8083_(), useOnContext.m_43719_(), player, bl);
        if (wrenchResult != WrenchResult.Nothing) {
            if (!useOnContext.m_43725_().f_46443_) {
                return wrenchResult == WrenchResult.Rotated ? 1 : 10;
            }
            player.m_5496_(Ic2SoundEvents.ITEM_WRENCH_USE, 1.0f, 1.0f);
            return -2;
        }
        return -1;
    }

    public static WrenchResult wrenchBlock(Level level, BlockPos blockPos, Direction direction, Player player, boolean bl) {
        BlockState blockState = level.m_8055_(blockPos);
        Block block = blockState.m_60734_();
        if (blockState.m_60795_()) {
            return WrenchResult.Nothing;
        }
        if (block instanceof IWrenchable) {
            Direction.Axis axis;
            Direction direction2;
            IWrenchable iWrenchable = (IWrenchable)block;
            Direction direction3 = direction2 = iWrenchable.getFacing(level, blockPos);
            if (IC2.keyboard.isAltKeyDown(player)) {
                axis = direction.m_122434_();
                direction3 = ItemToolWrench.isAltRotationClockwise(direction, player) ? direction3.m_175362_(axis) : direction3.m_175364_(axis);
            } else {
                direction3 = ItemToolWrench.getNewFacing(direction, player);
            }
            if (direction3 != direction2 && iWrenchable.setFacing(level, blockPos, direction3, player)) {
                return WrenchResult.Rotated;
            }
            if (bl && iWrenchable.wrenchCanRemove(level, blockPos, player)) {
                Object object;
                if (level.f_46443_) {
                    return WrenchResult.Removed;
                }
                if (player.m_36187_(level, blockPos, ((ServerPlayer)player).f_8941_.m_9290_())) {
                    return WrenchResult.Nothing;
                }
                axis = level.m_7702_(blockPos);
                if (ConfigUtil.getBool(MainConfig.get(), "protection/wrenchLogging")) {
                    object = player.m_36316_().getName() + "/" + player.m_36316_().getId();
                    IC2.log.info(LogCategory.PlayerActivity, "Player %s used a wrench to remove the block %s (te %s) at %s.", object, blockState, ItemToolWrench.getTeName((BlockEntity)axis), Util.formatPosition((BlockGetter)level, blockPos));
                }
                block.m_5707_(level, blockPos, blockState, player);
                if (level.m_7471_(blockPos, false)) {
                    block.m_6786_((LevelAccessor)level, blockPos, blockState);
                }
                if ((object = iWrenchable.getWrenchDrops(level, blockPos, blockState, (BlockEntity)axis, player, 0)) == null || object.isEmpty()) {
                    if (logEmptyWrenchDrops) {
                        IC2.log.warn(LogCategory.General, "The block %s (te %s) at %s didn't yield any wrench drops.", blockState, ItemToolWrench.getTeName((BlockEntity)axis), Util.formatPosition((BlockGetter)level, blockPos));
                    }
                } else {
                    Iterator iterator = object.iterator();
                    while (iterator.hasNext()) {
                        ItemStack itemStack = (ItemStack)iterator.next();
                        StackUtil.dropAsEntity(level, blockPos, itemStack);
                    }
                }
                if (!player.m_150110_().f_35937_) {
                    blockState.m_222967_((ServerLevel)level, blockPos, player.m_21211_(), false);
                }
                return WrenchResult.Removed;
            }
        } else {
            Direction direction4;
            Direction direction5;
            Property property;
            Rotation rotation = null;
            if (IC2.keyboard.isAltKeyDown(player)) {
                rotation = ItemToolWrench.isAltRotationClockwise(direction, player) ? Rotation.CLOCKWISE_90 : Rotation.COUNTERCLOCKWISE_90;
            } else if (direction.m_122434_().m_122479_() && (property = blockState.m_60734_().m_49965_().m_61081_("facing")) != null && property.m_61709_() == Direction.class && (direction5 = (Direction)blockState.m_61143_(property)) != null && direction5.m_122434_().m_122479_() && (direction4 = ItemToolWrench.getNewFacing(direction, player)) != direction5 && property.m_6908_().contains(direction4)) {
                rotation = direction5.m_122424_() == direction4 ? Rotation.CLOCKWISE_180 : (direction5.m_175362_(Direction.Axis.Y) == direction4 ? Rotation.CLOCKWISE_90 : Rotation.COUNTERCLOCKWISE_90);
            }
            if (rotation != null && (property = IC2.envProxy.rotate(blockState, (LevelAccessor)level, blockPos, rotation)) != blockState) {
                level.m_46597_(blockPos, (BlockState)property);
                return WrenchResult.Rotated;
            }
        }
        return WrenchResult.Nothing;
    }

    private static boolean isAltRotationClockwise(Direction direction, Player player) {
        return direction.m_122421_() == Direction.AxisDirection.POSITIVE != player.m_6144_();
    }

    private static Direction getNewFacing(Direction direction, Player player) {
        return player.m_6144_() ? direction.m_122424_() : direction;
    }

    private static String getTeName(BlockEntity blockEntity) {
        return blockEntity != null ? Registry.f_122830_.m_7981_((Object)blockEntity.m_58903_()).toString() : "none";
    }

    public void damage(ItemStack itemStack, int n, Player player2, InteractionHand interactionHand) {
        itemStack.m_41622_(n, (LivingEntity)player2, player -> player.m_21190_(interactionHand));
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack itemStack) {
        return true;
    }

    public boolean m_6832_(ItemStack itemStack, ItemStack itemStack2) {
        return itemStack2 != null && itemStack2.m_204117_(Ic2ItemTags.BRONZE_INGOTS);
    }

    private static enum WrenchResult {
        Rotated,
        Removed,
        Nothing;

    }
}

