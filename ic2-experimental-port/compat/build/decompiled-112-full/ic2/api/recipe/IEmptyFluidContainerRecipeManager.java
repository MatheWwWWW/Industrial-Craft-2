/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.api.recipe;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.util.FluidContainerOutputMode;
import java.util.Collection;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface IEmptyFluidContainerRecipeManager
extends IMachineRecipeManager<Void, Output, ItemStack> {
    public MachineRecipeResult<Void, Output, ItemStack> apply(ItemStack var1, Fluid var2, FluidContainerOutputMode var3, boolean var4);

    public static class Output {
        public final Collection<ItemStack> container;
        public final FluidStack fluid;

        public Output(Collection<ItemStack> container, FluidStack fluid) {
            this.container = container;
            this.fluid = fluid;
        }
    }
}

