/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.Hash$Strategy
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.world.inventory.tooltip.TooltipComponent
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.renders.tooltip;

import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.NBTUtils;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public class InventoryTooltip
implements TooltipComponent {
    List<ItemStack> items;

    public InventoryTooltip(List<ItemStack> items) {
        this.items = items;
    }

    public List<ItemStack> getItems() {
        return this.items;
    }

    public static InventoryTooltip create(ItemStack stack) {
        return InventoryTooltip.create(NBTUtils.getItems(stack));
    }

    public static InventoryTooltip create(List<ItemStack> list) {
        Object2IntLinkedOpenCustomHashMap mapped = new Object2IntLinkedOpenCustomHashMap((Hash.Strategy)ItemStackStrategy.SIMPLE_INSTANCE);
        for (ItemStack stack : list) {
            mapped.addTo((Object)stack, stack.m_41613_());
        }
        ObjectList result = CollectionUtils.createList();
        for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable((Object2IntMap)mapped)) {
            ItemStack stack = (ItemStack)entry.getKey();
            stack.m_41764_(entry.getIntValue());
            result.add((ItemStack)stack);
        }
        return new InventoryTooltip((List<ItemStack>)result);
    }
}

