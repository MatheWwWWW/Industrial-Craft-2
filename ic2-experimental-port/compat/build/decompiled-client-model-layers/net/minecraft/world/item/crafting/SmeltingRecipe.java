/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;

public class SmeltingRecipe
extends AbstractCookingRecipe {
    public SmeltingRecipe(ResourceLocation p_44460_, String p_44461_, Ingredient p_44462_, ItemStack p_44463_, float p_44464_, int p_44465_) {
        super(RecipeType.f_44108_, p_44460_, p_44461_, p_44462_, p_44463_, p_44464_, p_44465_);
    }

    @Override
    public ItemStack m_8042_() {
        return new ItemStack(Blocks.f_50094_);
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44091_;
    }
}

