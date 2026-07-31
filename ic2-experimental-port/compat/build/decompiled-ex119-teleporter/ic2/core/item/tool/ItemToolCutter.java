/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.item.tool;

import ic2.api.item.IEnhancedOverlayProvider;
import ic2.core.block.wiring.CableBlock;
import ic2.core.item.tool.ItemToolCrafting;
import ic2.core.ref.Ic2Items;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.StackUtil;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ItemToolCutter
extends ItemToolCrafting
implements IEnhancedOverlayProvider {
    public ItemToolCutter(Item.Properties properties) {
        super(properties);
    }

    public InteractionResult m_6225_(UseOnContext useOnContext) {
        BlockPos blockPos;
        Level level = useOnContext.m_43725_();
        BlockState blockState = level.m_8055_(blockPos = useOnContext.m_8083_());
        Block block = blockState.m_60734_();
        if (block instanceof CableBlock) {
            CableBlock cableBlock = (CableBlock)block;
            Player player = useOnContext.m_43723_();
            Predicate<ItemStack> predicate = StackUtil.sameItem(Ic2Items.RUBBER);
            if (player == null) {
                return InteractionResult.PASS;
            }
            if (StackUtil.consumeFromPlayerInventory(player, predicate, 1, true) && cableBlock.tryAddInsulation(blockState, level, blockPos)) {
                StackUtil.consumeFromPlayerInventory(player, predicate, 1, false);
                StackUtil.damageOrError(player, useOnContext.m_43724_(), 1);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public boolean removeInsulation(Player player, InteractionHand interactionHand, BlockState blockState, Level level, BlockPos blockPos) {
        CableBlock cableBlock = (CableBlock)blockState.m_60734_();
        if (cableBlock.tryRemoveInsulation(blockState, level, blockPos, true) && StackUtil.damage(player, interactionHand, StackUtil.sameItem(this), 3)) {
            cableBlock.tryRemoveInsulation(blockState, level, blockPos, false);
            if (level.f_46443_) {
                player.m_5496_(Ic2SoundEvents.ITEM_CUTTER_USE, 1.0f, 1.0f);
            } else {
                StackUtil.dropAsEntity(level, blockPos, new ItemStack((ItemLike)Ic2Items.RUBBER));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean providesEnhancedOverlay(Level level, BlockPos blockPos, Direction direction, Player player, ItemStack itemStack) {
        return false;
    }
}

