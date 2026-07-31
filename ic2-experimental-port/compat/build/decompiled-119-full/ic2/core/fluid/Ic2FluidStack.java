/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 */
package ic2.core.fluid;

import ic2.core.fluid.FluidHandler;
import ic2.core.fluid.Ic2FluidItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public interface Ic2FluidStack {
    public static final Ic2FluidStack EMPTY = Ic2FluidStack.create(Fluids.f_76191_, 0);
    public static final int BUCKET_MB = 1000;

    public static Ic2FluidStack create(Fluid fluid, int n) {
        if (fluid == null) {
            throw new NullPointerException();
        }
        return FluidHandler.ENV_HANDLER.createFluidStackMb(fluid, n, null);
    }

    public static Ic2FluidStack get(ItemStack itemStack) {
        Item item = itemStack.m_41720_();
        if (item instanceof Ic2FluidItem) {
            return ((Ic2FluidItem)item).getFluidStack(itemStack);
        }
        return FluidHandler.ENV_HANDLER.getFluidStack(itemStack);
    }

    public static Ic2FluidStack[] getAll(ItemStack itemStack) {
        Item item = itemStack.m_41720_();
        if (item instanceof Ic2FluidItem) {
            return new Ic2FluidStack[]{((Ic2FluidItem)item).getFluidStack(itemStack)};
        }
        return FluidHandler.ENV_HANDLER.getFluidStacks(itemStack);
    }

    public static Ic2FluidStack read(CompoundTag compoundTag) {
        return FluidHandler.ENV_HANDLER.readFluidStack(compoundTag);
    }

    public Ic2FluidStack copy();

    default public Ic2FluidStack copyWithAmountMb(int n) {
        Ic2FluidStack ic2FluidStack = this.copy();
        ic2FluidStack.setAmountMb(n);
        return ic2FluidStack;
    }

    public Fluid getFluid();

    public boolean hasExactFluid(Fluid var1);

    public boolean hasExactFluid(Ic2FluidStack var1);

    default public boolean isEmpty() {
        return this.getAmountMb() == 0;
    }

    public int getAmountMb();

    public void setAmountMb(int var1);

    default public void increaseMb(int n) {
        this.setAmountMb(this.getAmountMb() + n);
    }

    default public void decreaseMb(int n) {
        this.setAmountMb(this.getAmountMb() - n);
    }

    public void toNbt(CompoundTag var1);
}

