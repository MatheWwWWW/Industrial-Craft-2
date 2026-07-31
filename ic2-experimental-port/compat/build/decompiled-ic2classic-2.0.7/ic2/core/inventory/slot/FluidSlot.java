/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidUtil
 */
package ic2.core.inventory.slot;

import ic2.core.inventory.base.IFluidInventory;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.slot.IFluidSlot;
import ic2.core.inventory.slot.IGhostSlot;
import ic2.core.inventory.slot.SlotBase;
import ic2.core.utils.helpers.FluidHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class FluidSlot
extends SlotBase
implements IGhostSlot,
IFluidSlot {
    IFluidInventory fluidInv;

    public <T extends IHasInventory & IFluidInventory> FluidSlot(T inv, int index, int xPosition, int yPosition) {
        super(inv, index, xPosition, yPosition);
        this.fluidInv = inv;
    }

    public boolean m_5857_(ItemStack stack) {
        return this.fluidInv.canInsert(this.f_40217_, FluidUtil.getFluidContained((ItemStack)stack).orElse(FluidStack.EMPTY).getFluid());
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
        Fluid fluid = FluidHelper.getDisplayFluid(stack);
        if (this.fluidInv.canInsert(this.f_40217_, fluid)) {
            this.fluidInv.setFluidInSlot(this.f_40217_, fluid);
        }
    }

    @Override
    public IGhostSlot.GhostType getType() {
        return IGhostSlot.GhostType.FILTER;
    }
}

