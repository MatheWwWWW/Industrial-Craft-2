/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;

public class PlaceOnWaterBlockItem
extends BlockItem {
    public PlaceOnWaterBlockItem(Block p_220226_, Item.Properties p_220227_) {
        super(p_220226_, p_220227_);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_220229_) {
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> m_7203_(Level p_220231_, Player p_220232_, InteractionHand p_220233_) {
        BlockHitResult $$3 = PlaceOnWaterBlockItem.m_41435_(p_220231_, p_220232_, ClipContext.Fluid.SOURCE_ONLY);
        BlockHitResult $$4 = $$3.m_82430_($$3.m_82425_().m_7494_());
        InteractionResult $$5 = super.m_6225_(new UseOnContext(p_220232_, p_220233_, $$4));
        return new InteractionResultHolder<ItemStack>($$5, p_220232_.m_21120_(p_220233_));
    }
}

