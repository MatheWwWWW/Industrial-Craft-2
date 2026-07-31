/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.core.recipe.dynamic;

import ic2.core.recipe.dynamic.RecipeOutputIngredient;
import ic2.core.util.LiquidUtil;
import net.minecraftforge.fluids.FluidStack;

public class RecipeOutputFluidStack
extends RecipeOutputIngredient<FluidStack> {
    public static RecipeOutputFluidStack of(FluidStack ingredient) {
        return new RecipeOutputFluidStack(ingredient);
    }

    protected RecipeOutputFluidStack(FluidStack ingredient) {
        super(ingredient);
    }

    @Override
    public RecipeOutputIngredient<FluidStack> copy() {
        return RecipeOutputFluidStack.of(((FluidStack)this.ingredient).copy());
    }

    @Override
    public boolean isEmpty() {
        return ((FluidStack)this.ingredient).amount <= 0;
    }

    @Override
    public boolean matches(Object other) {
        if (!(other instanceof FluidStack)) {
            return false;
        }
        return ((FluidStack)this.ingredient).isFluidStackIdentical((FluidStack)other);
    }

    @Override
    public boolean matchesStrict(Object other) {
        return this.matches(other);
    }

    @Override
    public String toStringSafe() {
        return LiquidUtil.toStringSafe((FluidStack)this.ingredient);
    }
}

