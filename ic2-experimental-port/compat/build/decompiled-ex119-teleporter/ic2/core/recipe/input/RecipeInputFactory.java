/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.recipe.input;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.IRecipeInputFactory;
import ic2.core.recipe.input.RecipeInputFluidContainer;
import ic2.core.recipe.input.RecipeInputIngredient;
import ic2.core.recipe.input.RecipeInputItemStack;
import ic2.core.util.StackUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;

public class RecipeInputFactory
implements IRecipeInputFactory {
    @Override
    public IRecipeInput forItem(ItemLike itemLike) {
        return new RecipeInputItemStack(new ItemStack(itemLike));
    }

    @Override
    public IRecipeInput forStack(ItemStack itemStack) {
        return new RecipeInputItemStack(itemStack);
    }

    @Override
    public IRecipeInput forStack(ItemStack itemStack, int n) {
        return new RecipeInputItemStack(StackUtil.copyWithSize(itemStack, n));
    }

    @Override
    public IRecipeInput forTag(String string, int n) {
        return this.forIngredient(Ingredient.m_204132_((TagKey)TagKey.m_203882_((ResourceKey)Registry.f_122904_, (ResourceLocation)new ResourceLocation(string))), n);
    }

    @Override
    public IRecipeInput forFluidContainer(Fluid fluid, int n) {
        return new RecipeInputFluidContainer(fluid, n);
    }

    @Override
    public Ingredient getIngredient(IRecipeInput iRecipeInput) {
        return iRecipeInput.getIngredient();
    }

    @Override
    public IRecipeInput forIngredient(Ingredient ingredient, int n) {
        return new RecipeInputIngredient(ingredient, n);
    }
}

