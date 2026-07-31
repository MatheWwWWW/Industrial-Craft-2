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
import net.minecraft.world.item.ItemStack;

public interface ICannerBottleRecipeManager
extends IMachineRecipeManager<Input, ItemStack, RawInput> {
    @Deprecated
    public void addRecipe(IRecipeInput var1, IRecipeInput var2, ItemStack var3);

    @Deprecated
    public RecipeOutput getOutputFor(ItemStack var1, ItemStack var2, boolean var3, boolean var4);

    public static class RawInput {
        public final ItemStack container;
        public final ItemStack fill;

        public RawInput(ItemStack itemStack, ItemStack itemStack2) {
            this.container = itemStack;
            this.fill = itemStack2;
        }
    }

    public static class Input {
        public final IRecipeInput container;
        public final IRecipeInput fill;

        public Input(IRecipeInput iRecipeInput, IRecipeInput iRecipeInput2) {
            this.container = iRecipeInput;
            this.fill = iRecipeInput2;
        }

        public boolean matches(ItemStack itemStack, ItemStack itemStack2) {
            return this.container.matches(itemStack) && this.fill.matches(itemStack2);
        }
    }
}

