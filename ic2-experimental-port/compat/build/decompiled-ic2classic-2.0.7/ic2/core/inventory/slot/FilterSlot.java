/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.inventory.slot;

import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.filter.ClassFilter;
import ic2.core.inventory.filter.FluidFilter;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.filter.special.FuelFilter;
import ic2.core.inventory.slot.IGhostSlot;
import ic2.core.inventory.slot.SlotBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FilterSlot
extends SlotBase
implements IGhostSlot {
    IFilter filter;

    public FilterSlot(IHasInventory inv, int index, int xPosition, int yPosition, IFilter filter) {
        super(inv, index, xPosition, yPosition);
        this.filter = filter;
    }

    public boolean m_5857_(ItemStack stack) {
        return this.filter == null || this.filter.matches(stack);
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

    public static Slot createClassSlot(IHasInventory inv, int index, int xPosition, int yPosition, Class<?> clz) {
        return new FilterSlot(inv, index, xPosition, yPosition, new ClassFilter(clz));
    }

    public static Slot createChargeSlot(IHasInventory inv, int index, int xPosition, int yPosition) {
        return new FilterSlot(inv, index, xPosition, yPosition, ElectricItemFilter.CHARGE_FILTER).setBackground(new ResourceLocation("ic2", "misc/gui/charge_slot"));
    }

    public static Slot createChargeSlot(IHasInventory inv, int tier, int index, int xPosition, int yPosition) {
        return new FilterSlot(inv, index, xPosition, yPosition, new ElectricItemFilter(true, true, tier)).setBackground(new ResourceLocation("ic2", "misc/gui/charge_slot"));
    }

    public static Slot createDischargeSlot(IHasInventory inv, int tier, int index, int xPosition, int yPosition) {
        return new FilterSlot(inv, index, xPosition, yPosition, new ElectricItemFilter(false, true, tier)).setBackground(new ResourceLocation("ic2", "misc/gui/discharge"));
    }

    public static Slot createDischargeSlot(IHasInventory inv, int index, int xPosition, int yPosition) {
        return new FilterSlot(inv, index, xPosition, yPosition, ElectricItemFilter.DISCHARGE_EXTERNAL_FILTER).setBackground(new ResourceLocation("ic2", "misc/gui/discharge"));
    }

    public static Slot createFluidFillSlot(IHasInventory inv, int index, int xPosition, int yPosition) {
        return new FilterSlot(inv, index, xPosition, yPosition, FluidFilter.EMPTY_CONTAINER).setBackground(new ResourceLocation("ic2", "misc/gui/fluid_drain"));
    }

    public static Slot createFluidFillSlot(IHasInventory inv, int index, int xPosition, int yPosition, Fluid fluid) {
        return new FilterSlot(inv, index, xPosition, yPosition, FluidFilter.createFillFilter(fluid)).setBackground(new ResourceLocation("ic2", "misc/gui/fluid_drain"));
    }

    public static Slot createFluidDrainSlot(IHasInventory inv, int index, int xPosition, int yPosition, Fluid ... fluids) {
        return new FilterSlot(inv, index, xPosition, yPosition, new FluidFilter(fluids)).setBackground(new ResourceLocation("ic2", "misc/gui/fluid_drain"));
    }

    public static Slot createFluidFillSlot(IHasInventory inv, int index, int xPosition, int yPosition, IFluidHandler fluid) {
        return new FilterSlot(inv, index, xPosition, yPosition, new FluidFilter.FluidTankFillFilter(fluid)).setBackground(new ResourceLocation("ic2", "misc/gui/fluid_drain"));
    }

    public static Slot createFluidDrainSlot(IHasInventory inv, int index, int xPosition, int yPosition, IFluidHandler fluid) {
        return new FilterSlot(inv, index, xPosition, yPosition, new FluidFilter.FluidTankFilter(fluid)).setBackground(new ResourceLocation("ic2", "misc/gui/fluid_drain"));
    }

    public static Slot createOutputSlot(IHasInventory inv, int index, int xPosition, int yPosition) {
        return new FilterSlot(inv, index, xPosition, yPosition, SpecialFilters.ALWAYS_FALSE);
    }

    public static Slot createFluidOutputSlot(IHasInventory inv, int index, int xPosition, int yPosition) {
        return new FilterSlot(inv, index, xPosition, yPosition, SpecialFilters.ALWAYS_FALSE).setBackground(new ResourceLocation("ic2", "misc/gui/fluid_drain"));
    }

    public static Slot createFuelSlot(IHasInventory inv, int index, int xPosition, int yPosition, boolean lava) {
        return new FilterSlot(inv, index, xPosition, yPosition, lava ? FuelFilter.WITH_LAVA : FuelFilter.WITHOUT_LAVA).setBackground(new ResourceLocation("ic2", "misc/gui/fuel"));
    }
}

