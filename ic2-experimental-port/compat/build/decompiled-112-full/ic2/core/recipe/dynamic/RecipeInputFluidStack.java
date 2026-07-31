/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.core.recipe.dynamic;

import ic2.core.recipe.dynamic.RecipeInputIngredient;
import ic2.core.util.LiquidUtil;
import net.minecraftforge.fluids.FluidStack;

public class RecipeInputFluidStack
extends RecipeInputIngredient<FluidStack> {
    public static RecipeInputFluidStack of(FluidStack ingredient) {
        return new RecipeInputFluidStack(ingredient);
    }

    protected RecipeInputFluidStack(FluidStack ingredient) {
        super(ingredient);
    }

    @Override
    public Object getUnspecific() {
        return ((FluidStack)this.ingredient).getFluid();
    }

    @Override
    public RecipeInputIngredient<FluidStack> copy() {
        return RecipeInputFluidStack.of(((FluidStack)this.ingredient).copy());
    }

    @Override
    public boolean isEmpty() {
        return ((FluidStack)this.ingredient).amount <= 0;
    }

    @Override
    public int getCount() {
        return ((FluidStack)this.ingredient).amount;
    }

    @Override
    public void shrink(int amount) {
        ((FluidStack)this.ingredient).amount -= amount;
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

