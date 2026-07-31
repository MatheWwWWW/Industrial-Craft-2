/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.item.crafting.CraftingRecipe
 *  net.minecraftforge.common.crafting.IShapedRecipe
 */
package ic2.compat;

import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraftforge.common.crafting.IShapedRecipe;

public interface Ic2CraftingRecipe
extends CraftingRecipe,
IShapedRecipe<CraftingContainer> {
    public int getIc2RecipeWidth();

    public int getIc2RecipeHeight();

    default public int getRecipeHeight() {
        return this.getIc2RecipeHeight();
    }

    default public int getRecipeWidth() {
        return this.getIc2RecipeWidth();
    }
}

