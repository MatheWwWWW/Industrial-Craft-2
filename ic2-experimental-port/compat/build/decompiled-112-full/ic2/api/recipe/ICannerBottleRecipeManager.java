/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.api.recipe;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import net.minecraft.item.ItemStack;

public interface ICannerBottleRecipeManager
extends IMachineRecipeManager<Input, ItemStack, RawInput> {
    public boolean addRecipe(IRecipeInput var1, IRecipeInput var2, ItemStack var3, boolean var4);

    @Deprecated
    public void addRecipe(IRecipeInput var1, IRecipeInput var2, ItemStack var3);

    @Deprecated
    public RecipeOutput getOutputFor(ItemStack var1, ItemStack var2, boolean var3, boolean var4);

    public static class RawInput {
        public final ItemStack container;
        public final ItemStack fill;

        public RawInput(ItemStack container, ItemStack fill) {
            this.container = container;
            this.fill = fill;
        }
    }

    public static class Input {
        public final IRecipeInput container;
        public final IRecipeInput fill;

        public Input(IRecipeInput container, IRecipeInput fill) {
            this.container = container;
            this.fill = fill;
        }

        public boolean matches(ItemStack container, ItemStack fill) {
            return this.container.matches(container) && this.fill.matches(fill);
        }
    }
}

