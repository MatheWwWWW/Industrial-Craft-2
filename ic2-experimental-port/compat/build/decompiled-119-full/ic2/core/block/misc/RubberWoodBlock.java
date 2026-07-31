/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.AxeItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 */
package ic2.core.block.misc;

import ic2.core.ref.Ic2Blocks;
import ic2.core.util.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class RubberWoodBlock
extends Block {
    public RubberWoodBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public InteractionResult m_6227_(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ItemStack itemStack = player.m_21205_();
        if (itemStack.m_41720_() instanceof AxeItem) {
            WorldUtil.strip(blockState, level, blockPos, player, itemStack, Ic2Blocks.STRIPPED_RUBBER_WOOD.m_49966_());
            return InteractionResult.m_19078_((boolean)level.f_46443_);
        }
        return super.m_6227_(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }
}

