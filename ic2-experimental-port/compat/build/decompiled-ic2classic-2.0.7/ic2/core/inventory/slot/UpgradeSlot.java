/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.slot;

import ic2.api.items.IUpgradeItem;
import ic2.api.tiles.IMachine;
import ic2.core.IC2;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.slot.IGhostSlot;
import ic2.core.inventory.slot.SlotBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class UpgradeSlot
extends SlotBase
implements IGhostSlot {
    IMachine machine;

    public UpgradeSlot(IMachine machine, IHasInventory inv, int index, int xPosition, int yPosition) {
        super(inv, index, xPosition, yPosition);
        this.machine = machine;
        this.setBackground(new ResourceLocation("ic2", "misc/gui/upgrade"));
    }

    public <T extends IHasInventory & IMachine> UpgradeSlot(T inv, int index, int xPosition, int yPosition) {
        super(inv, index, xPosition, yPosition);
        this.machine = inv;
        this.setBackground(new ResourceLocation("ic2", "misc/gui/upgrade"));
    }

    @Override
    public void m_6654_() {
        if (IC2.PLATFORM.isSimulating()) {
            this.machine.onUpgradesChanged();
        }
    }

    public boolean m_5857_(ItemStack stack) {
        if (stack.m_41720_() instanceof IUpgradeItem) {
            return this.machine.getSupportedUpgradeTypes().contains((Object)((IUpgradeItem)stack.m_41720_()).getType(stack));
        }
        return false;
    }

    public boolean m_8010_(Player playerIn) {
        ItemStack stack = this.m_7993_();
        if (stack.m_41720_() instanceof IUpgradeItem && ((IUpgradeItem)stack.m_41720_()).getExtraTier(stack, this.machine) > 0) {
            return IC2.KEYBOARD.isAltKeyDown(playerIn);
        }
        return super.m_8010_(playerIn);
    }

    public IMachine getMachine() {
        return this.machine;
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
    }
}

