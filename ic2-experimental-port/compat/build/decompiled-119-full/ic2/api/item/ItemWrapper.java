/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ArrayListMultimap
 *  com.google.common.collect.Multimap
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.item;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import ic2.api.item.IBoxable;
import ic2.api.item.IMetalArmor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemWrapper {
    private static final Multimap<Item, IBoxable> boxableItems = ArrayListMultimap.create();
    private static final Multimap<Item, IMetalArmor> metalArmorItems = ArrayListMultimap.create();

    public static void registerBoxable(Item item, IBoxable iBoxable) {
        boxableItems.put((Object)item, (Object)iBoxable);
    }

    public static boolean canBeStoredInToolbox(ItemStack itemStack) {
        Item item = itemStack.m_41720_();
        for (IBoxable iBoxable : boxableItems.get((Object)item)) {
            if (!iBoxable.canBeStoredInToolbox(itemStack)) continue;
            return true;
        }
        return item instanceof IBoxable && ((IBoxable)item).canBeStoredInToolbox(itemStack);
    }

    public static void registerMetalArmor(Item item, IMetalArmor iMetalArmor) {
        metalArmorItems.put((Object)item, (Object)iMetalArmor);
    }

    public static boolean isMetalArmor(ItemStack itemStack, Player player) {
        Item item = itemStack.m_41720_();
        for (IMetalArmor iMetalArmor : metalArmorItems.get((Object)item)) {
            if (!iMetalArmor.isMetalArmor(itemStack, player)) continue;
            return true;
        }
        return item instanceof IMetalArmor && ((IMetalArmor)item).isMetalArmor(itemStack, player);
    }
}

