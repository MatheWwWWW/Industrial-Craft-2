/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.fluid;

import ic2.core.fluid.Ic2FluidStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;

public class Ic2FluidTank {
    private int capacity;
    private Ic2FluidStack fluidStack;

    public Ic2FluidTank(int n) {
        this.capacity = n;
    }

    public final int getCapacity() {
        return this.capacity;
    }

    public final void setCapacity(int n) {
        this.capacity = n;
    }

    public final Ic2FluidStack getFluidStack() {
        return this.fluidStack;
    }

    public final void setFluidStack(Ic2FluidStack ic2FluidStack) {
        this.fluidStack = ic2FluidStack;
    }

    public final boolean hasExactFluid(Fluid fluid) {
        return this.fluidStack != null && this.fluidStack.hasExactFluid(fluid);
    }

    public final boolean isEmpty() {
        return this.fluidStack == null || this.fluidStack.isEmpty();
    }

    public final int getFluidAmount() {
        return this.fluidStack != null ? this.fluidStack.getAmountMb() : 0;
    }

    public final void fromNbt(CompoundTag compoundTag) {
        this.fluidStack = compoundTag.m_128441_("Empty") ? null : Ic2FluidStack.read(compoundTag);
    }

    public final CompoundTag toNbt(CompoundTag compoundTag) {
        if (this.fluidStack == null || this.fluidStack.isEmpty()) {
            compoundTag.m_128359_("Empty", "");
        } else {
            this.fluidStack.toNbt(compoundTag);
        }
        return compoundTag;
    }

    public final Ic2FluidStack drainMb(int n, boolean bl) {
        return this.drainMb(n, bl, true);
    }

    public final Ic2FluidStack drainMbUnchecked(int n, boolean bl) {
        return this.drainMb(n, bl, false);
    }

    private Ic2FluidStack drainMb(int n, boolean bl, boolean bl2) {
        Ic2FluidStack ic2FluidStack;
        if (n <= 0 || this.fluidStack == null || this.fluidStack.isEmpty() || bl2 && !this.canDrain()) {
            return Ic2FluidStack.EMPTY;
        }
        if (n >= this.fluidStack.getAmountMb()) {
            if (bl) {
                ic2FluidStack = this.fluidStack.copy();
            } else {
                ic2FluidStack = this.fluidStack;
                this.fluidStack = null;
            }
        } else {
            ic2FluidStack = this.fluidStack.copyWithAmountMb(n);
            if (!bl) {
                this.fluidStack.decreaseMb(n);
            }
        }
        return ic2FluidStack;
    }

    public final int drainMb(Ic2FluidStack ic2FluidStack, boolean bl) {
        return this.drainMb(ic2FluidStack, bl, true);
    }

    public final int drainMbUnchecked(Ic2FluidStack ic2FluidStack, boolean bl) {
        return this.drainMb(ic2FluidStack, bl, false);
    }

    private int drainMb(Ic2FluidStack ic2FluidStack, boolean bl, boolean bl2) {
        int n;
        if (ic2FluidStack.isEmpty() || this.fluidStack == null || this.fluidStack.isEmpty() || !this.fluidStack.hasExactFluid(ic2FluidStack) || bl2 && !this.canDrain()) {
            return 0;
        }
        int n2 = ic2FluidStack.getAmountMb();
        if (n2 <= this.fluidStack.getAmountMb()) {
            n = this.fluidStack.getAmountMb();
            if (!bl) {
                this.fluidStack = null;
            }
        } else {
            n = n2;
            if (!bl) {
                this.fluidStack.decreaseMb(n2);
            }
        }
        return n;
    }

    public final int fillMb(Ic2FluidStack ic2FluidStack, boolean bl) {
        return this.fillMb(ic2FluidStack, bl, true);
    }

    public final int fillMbUnchecked(Ic2FluidStack ic2FluidStack, boolean bl) {
        return this.fillMb(ic2FluidStack, bl, false);
    }

    private int fillMb(Ic2FluidStack ic2FluidStack, boolean bl, boolean bl2) {
        if (ic2FluidStack.isEmpty() || this.capacity <= 0 || this.fluidStack != null && (this.fluidStack.getAmountMb() >= this.capacity || !this.fluidStack.isEmpty() && !this.fluidStack.hasExactFluid(ic2FluidStack)) || bl2 && !this.canFill(ic2FluidStack.getFluid())) {
            return 0;
        }
        int n = Math.min(ic2FluidStack.getAmountMb(), this.capacity - this.getFluidAmount());
        if (!bl) {
            if (this.fluidStack == null || this.fluidStack.isEmpty()) {
                this.fluidStack = ic2FluidStack.copyWithAmountMb(n);
            } else {
                this.fluidStack.increaseMb(n);
            }
        }
        return n;
    }

    protected boolean canDrain() {
        return true;
    }

    protected boolean canFill(Fluid fluid) {
        return true;
    }
}

