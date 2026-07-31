/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.forge;

import ic2.core.fluid.Ic2FluidStack;
import java.util.Objects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

final class Ic2FluidStackImpl
implements Ic2FluidStack {
    final FluidStack parent;

    public Ic2FluidStackImpl(FluidStack fluidStack) {
        if (fluidStack == null) {
            throw new NullPointerException();
        }
        this.parent = fluidStack;
    }

    @Override
    public Ic2FluidStack copy() {
        if (this.parent.getTag() == null) {
            return Ic2FluidStack.create(this.parent.getFluid(), this.parent.getAmount());
        }
        return new Ic2FluidStackImpl(this.parent.copy());
    }

    @Override
    public Fluid getFluid() {
        return this.parent.getFluid();
    }

    @Override
    public boolean hasExactFluid(Fluid fluid) {
        return this.parent.getTag() == null && fluid == this.parent.getFluid();
    }

    @Override
    public boolean hasExactFluid(Ic2FluidStack ic2FluidStack) {
        if (ic2FluidStack instanceof Ic2FluidStackImpl) {
            return this.parent.isFluidEqual(((Ic2FluidStackImpl)ic2FluidStack).parent);
        }
        return this.hasExactFluid(ic2FluidStack.getFluid());
    }

    @Override
    public int getAmountMb() {
        return this.parent.getAmount();
    }

    @Override
    public void setAmountMb(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        this.parent.setAmount(n);
    }

    @Override
    public void toNbt(CompoundTag compoundTag) {
        this.parent.writeToNBT(compoundTag);
    }

    public boolean equals(Object object) {
        if (object instanceof Ic2FluidStack) {
            return false;
        }
        Ic2FluidStack ic2FluidStack = (Ic2FluidStack)object;
        if (this.parent.getAmount() != ic2FluidStack.getAmountMb() || this.parent.getFluid() != ic2FluidStack.getFluid()) {
            return false;
        }
        if (ic2FluidStack instanceof Ic2FluidStackImpl) {
            return Objects.equals(this.parent.getTag(), ((Ic2FluidStackImpl)ic2FluidStack).parent.getTag());
        }
        return this.parent.getTag() == null;
    }

    public int hashCode() {
        return this.parent.getFluid().hashCode() ^ this.parent.getAmount();
    }

    public String toString() {
        Fluid fluid = this.parent.getFluid();
        return String.format("%dx%s@%s", this.parent.getAmount(), fluid != null ? ForgeRegistries.FLUIDS.getKey((Object)fluid) : "(null)", this.parent.getTag() != null ? this.parent.getTag().toString() : "(-)");
    }
}

