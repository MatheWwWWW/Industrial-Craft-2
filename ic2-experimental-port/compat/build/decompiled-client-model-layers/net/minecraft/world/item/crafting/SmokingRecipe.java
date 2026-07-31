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

public class SmokingRecipe
extends AbstractCookingRecipe {
    public SmokingRecipe(ResourceLocation p_44469_, String p_44470_, Ingredient p_44471_, ItemStack p_44472_, float p_44473_, int p_44474_) {
        super(RecipeType.f_44110_, p_44469_, p_44470_, p_44471_, p_44472_, p_44473_, p_44474_);
    }

    @Override
    public ItemStack m_8042_() {
        return new ItemStack(Blocks.f_50619_);
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44093_;
    }
}

