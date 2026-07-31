/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.api.recipe;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.util.FluidContainerOutputMode;
import ic2.core.fluid.Ic2FluidStack;
import java.util.Collection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public interface IEmptyFluidContainerRecipeManager
extends IMachineRecipeManager<Void, Output, ItemStack> {
    public MachineRecipeResult<Void, Output, ItemStack> apply(ItemStack var1, Fluid var2, FluidContainerOutputMode var3, boolean var4);

    public static class Output {
        public final Collection<ItemStack> container;
        public final Ic2FluidStack fluid;

        public Output(Collection<ItemStack> collection, Ic2FluidStack ic2FluidStack) {
            this.container = collection;
            this.fluid = ic2FluidStack;
        }
    }
}

