/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.api.energy.tile.IChargingSlot;
import ic2.api.item.ElectricItem;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.util.StackUtil;
import net.minecraft.world.item.ItemStack;

public class InvSlotCharge
extends InvSlot
implements IChargingSlot {
    public int tier;

    public InvSlotCharge(IInventorySlotHolder<?> iInventorySlotHolder, int n) {
        super(iInventorySlotHolder, "charge", InvSlot.Access.IO, 1, InvSlot.InvSide.TOP);
        this.tier = n;
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        return ElectricItem.manager.charge(itemStack, Double.POSITIVE_INFINITY, this.tier, true, true) > 0.0;
    }

    @Override
    public double charge(double d) {
        if (d <= 0.0) {
            throw new IllegalArgumentException("Amount must be > 0.");
        }
        ItemStack itemStack = this.get(0);
        if (StackUtil.isEmpty(itemStack)) {
            return 0.0;
        }
        return ElectricItem.manager.charge(itemStack, d, this.tier, false, false);
    }

    public void setTier(int n) {
        this.tier = n;
    }
}

