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

public class BlastingRecipe
extends AbstractCookingRecipe {
    public BlastingRecipe(ResourceLocation p_43793_, String p_43794_, Ingredient p_43795_, ItemStack p_43796_, float p_43797_, int p_43798_) {
        super(RecipeType.f_44109_, p_43793_, p_43794_, p_43795_, p_43796_, p_43797_, p_43798_);
    }

    @Override
    public ItemStack m_8042_() {
        return new ItemStack(Blocks.f_50620_);
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44092_;
    }
}

