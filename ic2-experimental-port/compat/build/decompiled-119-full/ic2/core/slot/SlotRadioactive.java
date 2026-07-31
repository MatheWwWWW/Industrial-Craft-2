/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotRadioactive
extends Slot {
    public SlotRadioactive(Container container, int n, int n2, int n3) {
        super(container, n, n2, n3);
    }

    public boolean m_5857_(ItemStack itemStack) {
        return this.f_40218_.m_7013_(this.m_150661_(), itemStack);
    }
}

