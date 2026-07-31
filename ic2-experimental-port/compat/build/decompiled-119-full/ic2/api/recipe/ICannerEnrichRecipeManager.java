/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.recipe;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.core.fluid.Ic2FluidStack;
import net.minecraft.world.item.ItemStack;

public interface ICannerEnrichRecipeManager
extends IMachineRecipeManager<Input, Ic2FluidStack, RawInput> {
    @Deprecated
    public RecipeOutput getOutputFor(Ic2FluidStack var1, ItemStack var2, boolean var3, boolean var4);

    public static class RawInput {
        public final Ic2FluidStack fluid;
        public final ItemStack additive;

        public RawInput(Ic2FluidStack ic2FluidStack, ItemStack itemStack) {
            this.fluid = ic2FluidStack;
            this.additive = itemStack;
        }
    }

    public static class Input {
        public final Ic2FluidStack fluid;
        public final IRecipeInput additive;

        public Input(Ic2FluidStack ic2FluidStack, IRecipeInput iRecipeInput) {
            this.fluid = ic2FluidStack;
            this.additive = iRecipeInput;
        }

        public boolean matches(Ic2FluidStack ic2FluidStack, ItemStack itemStack) {
            return this.fluid.hasExactFluid(ic2FluidStack) && this.additive.matches(itemStack);
        }
    }
}

