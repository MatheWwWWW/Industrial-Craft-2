/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.api.recipe;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface ICannerEnrichRecipeManager
extends IMachineRecipeManager<Input, FluidStack, RawInput> {
    @Deprecated
    public void addRecipe(FluidStack var1, IRecipeInput var2, FluidStack var3);

    @Deprecated
    public RecipeOutput getOutputFor(FluidStack var1, ItemStack var2, boolean var3, boolean var4);

    public static class RawInput {
        public final FluidStack fluid;
        public final ItemStack additive;

        public RawInput(FluidStack fluid, ItemStack additive) {
            this.fluid = fluid;
            this.additive = additive;
        }
    }

    public static class Input {
        public final FluidStack fluid;
        public final IRecipeInput additive;

        public Input(FluidStack fluid, IRecipeInput additive) {
            this.fluid = fluid;
            this.additive = additive;
        }

        public boolean matches(FluidStack fluid, ItemStack additive) {
            return this.fluid.isFluidEqual(fluid) && this.additive.matches(additive);
        }
    }
}

