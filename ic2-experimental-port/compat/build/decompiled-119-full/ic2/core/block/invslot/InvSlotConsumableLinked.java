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
import ic2.core.util.StackUtil;
import net.minecraft.world.item.ItemStack;

public class InvSlotConsumableLinked
extends InvSlotConsumable {
    public final InvSlot linkedSlot;

    public InvSlotConsumableLinked(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n, InvSlot invSlot) {
        super(iInventorySlotHolder, string, n);
        this.linkedSlot = invSlot;
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        ItemStack itemStack2 = this.linkedSlot.get();
        if (StackUtil.isEmpty(itemStack2)) {
            return false;
        }
        return StackUtil.checkItemEqualityStrict(itemStack2, itemStack);
    }

    public ItemStack consumeLinked(boolean bl) {
        ItemStack itemStack = this.linkedSlot.get();
        if (StackUtil.isEmpty(itemStack)) {
            return null;
        }
        int n = StackUtil.getSize(itemStack);
        ItemStack itemStack2 = this.consume(n, true, true);
        if (!StackUtil.isEmpty(itemStack2) && StackUtil.getSize(itemStack2) == n) {
            return this.consume(n, bl, true);
        }
        return null;
    }
}

