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

public class CampfireCookingRecipe
extends AbstractCookingRecipe {
    public CampfireCookingRecipe(ResourceLocation p_43822_, String p_43823_, Ingredient p_43824_, ItemStack p_43825_, float p_43826_, int p_43827_) {
        super(RecipeType.f_44111_, p_43822_, p_43823_, p_43824_, p_43825_, p_43826_, p_43827_);
    }

    @Override
    public ItemStack m_8042_() {
        return new ItemStack(Blocks.f_50683_);
    }

    @Override
    public RecipeSerializer<?> m_7707_() {
        return RecipeSerializer.f_44094_;
    }
}

