/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.util.ItemComparableItemStack;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.world.item.ItemStack;

public class InvSlotConsumableItemStack
extends InvSlotConsumable {
    private final Set<ItemComparableItemStack> stacks = new HashSet<ItemComparableItemStack>();

    public InvSlotConsumableItemStack(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n, ItemStack ... itemStackArray) {
        this(iInventorySlotHolder, string, InvSlot.Access.I, n, InvSlot.InvSide.TOP, itemStackArray);
    }

    public InvSlotConsumableItemStack(IInventorySlotHolder<?> iInventorySlotHolder, String string, InvSlot.Access access, int n, InvSlot.InvSide invSide, ItemStack ... itemStackArray) {
        super(iInventorySlotHolder, string, access, n, invSide);
        for (ItemStack itemStack : itemStackArray) {
            this.stacks.add(new ItemComparableItemStack(itemStack, true));
        }
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        return this.stacks.contains(new ItemComparableItemStack(itemStack, false));
    }
}

