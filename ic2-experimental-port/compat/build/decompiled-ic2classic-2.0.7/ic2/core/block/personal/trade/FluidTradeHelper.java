/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.personal.trade;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.block.personal.tile.FluidOMatTileEntity;
import ic2.core.inventory.base.IFluidInventory;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.item.misc.FluidDisplay;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidTradeHelper
implements IHasInventory,
IFluidInventory,
INetworkDataBuffer {
    FluidOMatTileEntity tile;
    Fluid fluid = Fluids.f_76191_;
    int amount;

    public FluidTradeHelper(FluidOMatTileEntity tile) {
        this.tile = tile;
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeForgeEntry(this.fluid, ForgeRegistries.FLUIDS);
        buffer.writeVarInt(this.amount);
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.fluid = (Fluid)buffer.readForgeRegistryEntry(ForgeRegistries.FLUIDS);
        this.amount = buffer.readVarInt();
    }

    public FluidStack getResult() {
        return new FluidStack(this.fluid, this.fluid == Fluids.f_76191_ ? 0 : this.amount);
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = Mth.m_14045_((int)amount, (int)0, (int)128000);
        this.tile.onHelperUpdated();
    }

    @Override
    public int getFluidSlots() {
        return 1;
    }

    @Override
    public void setFluidInSlot(int slot, Fluid fluid) {
        this.fluid = fluid;
        this.tile.onHelperUpdated();
    }

    @Override
    public boolean canInsert(int slot, Fluid fluid) {
        return fluid != Fluids.f_76191_;
    }

    @Override
    public Fluid getFluidInSlot(int slot) {
        return this.fluid;
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.fluid == Fluids.f_76191_ ? ItemStack.f_41583_ : FluidDisplay.createDisplay(this.fluid);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
    }

    @Override
    public int getMaxStackSize(int slot) {
        return 0;
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

