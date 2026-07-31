/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.slot;

import ic2.core.block.invslot.InvSlot;
import ic2.core.slot.SlotInvSlot;
import ic2.core.util.StackUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SlotInvSlotReadOnly
extends SlotInvSlot {
    public SlotInvSlotReadOnly(InvSlot invSlot, int n, int n2, int n3) {
        super(invSlot, n, n2, n3);
    }

    @Override
    public boolean m_5857_(ItemStack itemStack) {
        return false;
    }

    @Override
    public void m_142406_(Player player, ItemStack itemStack) {
    }

    public boolean m_8010_(Player player) {
        return false;
    }

    @Override
    public ItemStack m_6201_(int n) {
        return StackUtil.emptyStack;
    }
}

