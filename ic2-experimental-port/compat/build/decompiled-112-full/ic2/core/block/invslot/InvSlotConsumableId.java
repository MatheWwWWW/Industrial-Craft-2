/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InvSlotConsumableId
extends InvSlotConsumable {
    private final Set<Item> items = new HashSet<Item>();

    public InvSlotConsumableId(IInventorySlotHolder<?> base1, String name1, int count, Item ... items) {
        this(base1, name1, InvSlot.Access.I, count, InvSlot.InvSide.TOP, items);
    }

    public InvSlotConsumableId(IInventorySlotHolder<?> base1, String name1, InvSlot.Access access1, int count, InvSlot.InvSide preferredSide1, Item ... items) {
        super(base1, name1, access1, count, preferredSide1);
        this.items.addAll(Arrays.asList(items));
    }

    @Override
    public boolean accepts(ItemStack stack) {
        return this.items.contains(stack.func_77973_b());
    }
}

