/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.api.info.Info;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import net.minecraft.world.item.ItemStack;

public class InvSlotConsumableFuel
extends InvSlotConsumable {
    public final boolean allowLava;

    public InvSlotConsumableFuel(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n, boolean bl) {
        super(iInventorySlotHolder, string, InvSlot.Access.I, n, InvSlot.InvSide.SIDE);
        this.allowLava = bl;
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        return Info.getItemInfo().getFuelValue(itemStack, this.allowLava) > 0;
    }

    public int consumeFuel() {
        ItemStack itemStack = this.consume(1);
        if (itemStack == null) {
            return 0;
        }
        return Info.getItemInfo().getFuelValue(itemStack, this.allowLava);
    }
}

