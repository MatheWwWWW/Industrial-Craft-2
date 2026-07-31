/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.slot;

import ic2.api.info.Info;
import ic2.api.item.ElectricItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotDischarge
extends Slot {
    public int tier = Integer.MAX_VALUE;

    public SlotDischarge(Container container, int n, int n2, int n3, int n4) {
        super(container, n2, n3, n4);
        this.tier = n;
    }

    public SlotDischarge(Container container, int n, int n2, int n3) {
        super(container, n, n2, n3);
    }

    public boolean m_5857_(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        if (Info.getItemInfo().getEnergyValue(itemStack) > 0.0) {
            return true;
        }
        return ElectricItem.manager.discharge(itemStack, Double.POSITIVE_INFINITY, this.tier, true, true, true) > 0.0;
    }
}

