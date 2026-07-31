/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item.crafting;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.item.crafting.UpgradeRecipe;

public interface RecipeType<T extends Recipe<?>> {
    public static final RecipeType<CraftingRecipe> f_44107_ = RecipeType.m_44119_("crafting");
    public static final RecipeType<SmeltingRecipe> f_44108_ = RecipeType.m_44119_("smelting");
    public static final RecipeType<BlastingRecipe> f_44109_ = RecipeType.m_44119_("blasting");
    public static final RecipeType<SmokingRecipe> f_44110_ = RecipeType.m_44119_("smoking");
    public static final RecipeType<CampfireCookingRecipe> f_44111_ = RecipeType.m_44119_("campfire_cooking");
    public static final RecipeType<StonecutterRecipe> f_44112_ = RecipeType.m_44119_("stonecutting");
    public static final RecipeType<UpgradeRecipe> f_44113_ = RecipeType.m_44119_("smithing");

    public static <T extends Recipe<?>> RecipeType<T> m_44119_(final String p_44120_) {
        return Registry.m_122965_(Registry.f_122864_, new ResourceLocation(p_44120_), new RecipeType<T>(){

            public String toString() {
                return p_44120_;
            }
        });
    }
}

