/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class InvSlotConsumableId
extends InvSlotConsumable {
    private final Set<Item> items = new HashSet<Item>();

    public InvSlotConsumableId(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n, Item ... itemArray) {
        this(iInventorySlotHolder, string, InvSlot.Access.I, n, InvSlot.InvSide.TOP, itemArray);
    }

    public InvSlotConsumableId(IInventorySlotHolder<?> iInventorySlotHolder, String string, InvSlot.Access access, int n, InvSlot.InvSide invSide, Item ... itemArray) {
        super(iInventorySlotHolder, string, access, n, invSide);
        this.items.addAll(Arrays.asList(itemArray));
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        return this.items.contains(itemStack.m_41720_());
    }
}

