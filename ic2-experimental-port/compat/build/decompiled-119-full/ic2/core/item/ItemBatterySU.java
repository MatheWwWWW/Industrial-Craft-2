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
 *  net.minecraft.world.level.Level
 */
package ic2.core.item;

import ic2.api.item.ElectricItem;
import ic2.api.item.IBoxable;
import ic2.api.item.IItemHudInfo;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemBatterySU
extends Item
implements IBoxable,
IItemHudInfo {
    public int capacity;
    public int tier;

    public ItemBatterySU(Item.Properties properties, int n, int n2) {
        super(properties);
        this.capacity = n;
        this.tier = n2;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = StackUtil.get(player, interactionHand);
        double d = this.capacity;
        for (int i = 0; i < 9 && d > 0.0; ++i) {
            ItemStack itemStack2 = (ItemStack)player.m_150109_().f_35974_.get(i);
            if (itemStack2 == null || itemStack2 == itemStack) continue;
            d -= ElectricItem.manager.charge(itemStack2, d, this.tier, true, false);
        }
        if (!Util.isSimilar(d, (double)this.capacity)) {
            itemStack = StackUtil.decSize(itemStack);
            return new InteractionResultHolder(InteractionResult.SUCCESS, (Object)itemStack);
        }
        return new InteractionResultHolder(InteractionResult.PASS, (Object)itemStack);
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack itemStack) {
        return true;
    }

    @Override
    public List<String> getHudInfo(ItemStack itemStack, boolean bl) {
        LinkedList<String> linkedList = new LinkedList<String>();
        linkedList.add(this.capacity + " EU");
        return linkedList;
    }
}

