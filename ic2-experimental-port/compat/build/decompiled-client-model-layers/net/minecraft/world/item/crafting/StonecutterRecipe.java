/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class StonecutterRecipe
extends SingleItemRecipe {
    public StonecutterRecipe(ResourceLocation p_44478_, String p_44479_, Ingredient p_44480_, ItemStack p_44481_) {
        super(RecipeType.f_44112_, RecipeSerializer.f_44095_, p_44478_, p_44479_, p_44480_, p_44481_);
    }

    @Override
    public boolean m_5818_(Container p_44483_, Level p_44484_) {
        return this.f_44409_.test(p_44483_.m_8020_(0));
    }

    @Override
    public ItemStack m_8042_() {
        return new ItemStack(Blocks.f_50679_);
    }
}

