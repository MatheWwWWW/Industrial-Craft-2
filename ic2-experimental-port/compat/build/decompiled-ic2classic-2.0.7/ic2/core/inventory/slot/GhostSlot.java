/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.slot;

import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.slot.IGhostSlot;
import ic2.core.inventory.slot.IQuickMoveBlocker;
import ic2.core.inventory.slot.SlotBase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GhostSlot
extends SlotBase
implements IGhostSlot,
IQuickMoveBlocker {
    IFilter filter;

    public GhostSlot(IHasInventory inv, int index, int xPosition, int yPosition, IFilter filter) {
        super(inv, index, xPosition, yPosition);
        this.filter = filter;
    }

    public boolean m_5857_(ItemStack stack) {
        return this.filter == null || this.filter.matches(stack);
    }

    public boolean m_8010_(Player playerIn) {
        return false;
    }

    @Override
    public int getSlotID() {
        return this.f_40219_;
    }

    @Override
    public int getXPos() {
        return this.f_40220_;
    }

    @Override
    public int getYPos() {
        return this.f_40221_;
    }

    @Override
    public boolean isStackValid(ItemStack stack) {
        return this.m_5857_(stack);
    }

    @Override
    public void setFilter(ItemStack stack) {
        this.m_5852_(stack);
    }

    @Override
    public IGhostSlot.GhostType getType() {
        return IGhostSlot.GhostType.FILTER;
    }
}

