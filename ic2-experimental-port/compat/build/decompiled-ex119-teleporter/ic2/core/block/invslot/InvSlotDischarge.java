/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 */
package ic2.core.block.invslot;

import ic2.api.energy.tile.IDischargingSlot;
import ic2.api.info.Info;
import ic2.api.item.ElectricItem;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.util.StackUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class InvSlotDischarge
extends InvSlot
implements IDischargingSlot {
    public int tier;
    public boolean allowRedstoneDust = true;

    public InvSlotDischarge(IInventorySlotHolder<?> iInventorySlotHolder, InvSlot.Access access, int n) {
        this(iInventorySlotHolder, access, n, InvSlot.InvSide.ANY);
    }

    public InvSlotDischarge(IInventorySlotHolder<?> iInventorySlotHolder, InvSlot.Access access, int n, InvSlot.InvSide invSide) {
        this(iInventorySlotHolder, access, n, true, invSide);
    }

    public InvSlotDischarge(IInventorySlotHolder<?> iInventorySlotHolder, InvSlot.Access access, int n, boolean bl, InvSlot.InvSide invSide) {
        super(iInventorySlotHolder, "discharge", access, 1, invSide);
        this.tier = n;
        this.allowRedstoneDust = bl;
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        if (itemStack.m_41720_() == Items.f_42451_ && !this.allowRedstoneDust) {
            return false;
        }
        return Info.getItemInfo().getEnergyValue(itemStack) > 0.0 || ElectricItem.manager.discharge(itemStack, Double.POSITIVE_INFINITY, this.tier, true, true, true) > 0.0;
    }

    @Override
    public double discharge(double d, boolean bl) {
        if (d <= 0.0) {
            throw new IllegalArgumentException("Amount must be > 0.");
        }
        ItemStack itemStack = this.get(0);
        if (StackUtil.isEmpty(itemStack)) {
            return 0.0;
        }
        double d2 = ElectricItem.manager.discharge(itemStack, d, this.tier, bl, true, false);
        if (d2 <= 0.0) {
            d2 = Info.getItemInfo().getEnergyValue(itemStack);
            if (d2 <= 0.0) {
                return 0.0;
            }
            this.put(0, StackUtil.decSize(itemStack));
        }
        return d2;
    }

    public void setTier(int n) {
        this.tier = n;
    }
}

