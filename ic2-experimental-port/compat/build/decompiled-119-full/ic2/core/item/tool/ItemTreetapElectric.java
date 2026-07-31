/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.item.tool;

import ic2.core.item.tool.ItemElectricTool;
import ic2.core.item.tool.ItemTreetap;
import ic2.core.ref.Ic2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ItemTreetapElectric
extends ItemElectricTool {
    public ItemTreetapElectric(Item.Properties properties) {
        super(properties, 50);
        this.maxCharge = 10000;
        this.transferLimit = 100;
        this.tier = 1;
    }

    @Override
    public InteractionResult m_6225_(UseOnContext useOnContext) {
        Level level = useOnContext.m_43725_();
        BlockPos blockPos = useOnContext.m_8083_();
        BlockState blockState = level.m_8055_(blockPos);
        Block block = blockState.m_60734_();
        ItemStack itemStack = useOnContext.m_43722_();
        if (block != Ic2Blocks.RUBBER_LOG || !this.canUse(itemStack)) {
            return InteractionResult.PASS;
        }
        Player player = useOnContext.m_43723_();
        if (player == null) {
            return InteractionResult.PASS;
        }
        if (ItemTreetap.attemptExtract(player, level, blockPos, useOnContext.m_43719_(), blockState, null, true) && this.consumeEnergy(itemStack, this.operationEnergyCost, (LivingEntity)player)) {
            return InteractionResult.SUCCESS;
        }
        return super.m_6225_(useOnContext);
    }
}

