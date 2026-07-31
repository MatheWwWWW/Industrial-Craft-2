/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.core.fluid;

import ic2.core.inventory.base.INBTSavable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.fluids.FluidStack;

public class WrappedFluid
implements INBTSavable {
    FluidStack fluid;

    public WrappedFluid(FluidStack fluid) {
        this.fluid = fluid;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.m_128365_("fluid", (Tag)this.fluid.writeToNBT(new CompoundTag()));
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        this.fluid = FluidStack.loadFluidStackFromNBT((CompoundTag)nbt.m_128469_("fluid"));
    }

    public FluidStack getFluid() {
        return this.fluid;
    }
}

