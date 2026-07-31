/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ContainerData
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.block.transport.fluid.helper;

import ic2.core.block.base.cache.ICache;
import ic2.core.block.transport.fluid.helper.FluidCache;
import ic2.core.inventory.base.IFluidInventory;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.item.misc.FluidDisplay;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class InventoryData
implements IFluidInventory,
IHasInventory,
ContainerData {
    NonNullList<ItemStack> inventory = NonNullList.m_122780_((int)9, (Object)ItemStack.f_41583_);
    int direction;
    int allDirs;
    ICache<IFluidHandler> fluidCache;
    FluidCache<?> cache;

    public InventoryData(ICache<IFluidHandler> fluidCache, FluidCache<?> cache) {
        this.fluidCache = fluidCache;
        this.cache = cache;
        this.allDirs = fluidCache.getPresentSides().getCode();
        this.direction = this.getIndex();
        this.inventory.clear();
        for (int i = 0; i < 9; ++i) {
            Fluid fluid = this.getFluidInSlot(i);
            this.inventory.set(i, (Object)(fluid == Fluids.f_76191_ ? ItemStack.f_41583_ : FluidDisplay.createDisplay(fluid)));
        }
    }

    protected int getIndex() {
        return this.fluidCache.getPresentSides().getRandomFacing(Direction.DOWN).m_122411_();
    }

    public void setFacing(AbstractContainerMenu container, int direction) {
        this.direction = direction;
        container.m_38946_();
        this.inventory.clear();
        for (int i = 0; i < 9; ++i) {
            Fluid fluid = this.getFluidInSlot(i);
            this.inventory.set(i, (Object)(fluid == Fluids.f_76191_ ? ItemStack.f_41583_ : FluidDisplay.createDisplay(fluid)));
        }
    }

    public void setPriority(int value) {
        this.cache.get((int)this.direction).priority += value;
    }

    public void setWhiteList(boolean value) {
        this.cache.get((int)this.direction).whiteList = value;
    }

    public int getDir() {
        return this.direction;
    }

    public int getDirs() {
        return this.allDirs;
    }

    public int getPriority() {
        return this.cache.get((int)this.direction).priority;
    }

    public boolean isWhiteList() {
        return this.cache.get((int)this.direction).whiteList;
    }

    public int m_6413_(int index) {
        switch (index) {
            case 1: {
                return this.cache.get((int)this.direction).priority;
            }
            case 2: {
                return this.cache.get((int)this.direction).whiteList ? 1 : 0;
            }
            case 3: {
                return this.allDirs;
            }
        }
        return this.direction;
    }

    public void m_8050_(int index, int value) {
        switch (index) {
            case 0: {
                this.direction = value;
                break;
            }
            case 1: {
                this.cache.get((int)this.direction).priority = value;
                break;
            }
            case 2: {
                this.cache.get((int)this.direction).whiteList = value == 1;
                break;
            }
            case 3: {
                this.allDirs = value;
            }
        }
    }

    public int m_6499_() {
        return 4;
    }

    @Override
    public int getFluidSlots() {
        return 9;
    }

    @Override
    public void setFluidInSlot(int slot, Fluid fluid) {
        this.cache.get(this.direction).setFluid(slot, fluid);
        this.inventory.set(slot, (Object)(fluid == Fluids.f_76191_ ? ItemStack.f_41583_ : FluidDisplay.createDisplay(fluid)));
    }

    @Override
    public boolean canInsert(int slot, Fluid fluid) {
        return fluid != Fluids.f_76191_;
    }

    @Override
    public Fluid getFluidInSlot(int slot) {
        return this.cache.get(this.direction).getFluid(slot);
    }

    @Override
    public int getSlotCount() {
        return 9;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return (ItemStack)this.inventory.get(slot);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.inventory.set(slot, (Object)stack);
    }

    @Override
    public int getMaxStackSize(int slot) {
        return 64;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack) {
        return false;
    }
}

