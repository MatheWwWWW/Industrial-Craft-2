/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 */
package ic2.core.item;

import ic2.core.ref.Ic2Items;
import ic2.core.util.StackUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class ItemTinCan
extends Item {
    public ItemTinCan(Item.Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = StackUtil.get(player, interactionHand);
        if (!level.f_46443_ && player.m_36324_().m_38721_()) {
            return this.onEaten(player, itemStack);
        }
        return new InteractionResultHolder(InteractionResult.PASS, (Object)itemStack);
    }

    public InteractionResultHolder<ItemStack> onEaten(Player player, ItemStack itemStack) {
        int n = Math.min(StackUtil.getSize(itemStack), 20 - player.m_36324_().m_38702_());
        if (n <= 0) {
            return new InteractionResultHolder(InteractionResult.PASS, (Object)itemStack);
        }
        ItemStack itemStack2 = new ItemStack((ItemLike)Ic2Items.TIN_CAN, n);
        if (StackUtil.storeInventoryItem(itemStack2, player, true)) {
            player.m_36324_().m_38707_(n, (float)n);
            itemStack = StackUtil.decSize(itemStack, n);
            StackUtil.storeInventoryItem(itemStack2, player, false);
            return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)itemStack);
        }
        return new InteractionResultHolder(InteractionResult.PASS, (Object)itemStack);
    }
}

