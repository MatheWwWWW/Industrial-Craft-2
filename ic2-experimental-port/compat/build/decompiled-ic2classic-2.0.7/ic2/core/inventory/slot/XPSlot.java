/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.slot;

import ic2.core.block.base.features.IXPMachine;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.slot.SlotBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class XPSlot
extends SlotBase {
    IXPMachine machine;

    public <T extends IXPMachine & IHasInventory> XPSlot(T inv, int index, int xPosition, int yPosition) {
        super(inv, index, xPosition, yPosition);
        this.machine = inv;
    }

    public boolean m_5857_(ItemStack stack) {
        return false;
    }

    public void m_142406_(Player player, ItemStack stack) {
        player.m_6756_(this.machine.getCreatedXP(true));
        super.m_142406_(player, stack);
    }
}

