/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ic2.core.block.machines.containers.ev.PlasmafierContainer
 *  ic2.core.block.machines.containers.hv.MassFabricatorContainer
 *  ic2.core.inventory.gui.IC2Screen
 *  ic2.core.platform.registries.IC2Blocks
 *  ic2.core.platform.registries.IC2Items
 *  mezz.jei.api.IModPlugin
 *  mezz.jei.api.JeiPlugin
 *  mezz.jei.api.gui.handlers.IGuiContainerHandler
 *  mezz.jei.api.recipe.RecipeType
 *  mezz.jei.api.recipe.category.IRecipeCategory
 *  mezz.jei.api.registration.IGuiHandlerRegistration
 *  mezz.jei.api.registration.IRecipeCatalystRegistration
 *  mezz.jei.api.registration.IRecipeCategoryRegistration
 *  mezz.jei.api.registration.IRecipeRegistration
 *  mezz.jei.api.registration.IRecipeTransferRegistration
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  org.jetbrains.annotations.NotNull
 */
package com.christofmeg.ic2cuumatter.integration.jei;

import com.christofmeg.ic2cuumatter.integration.jei.GuiClickableArea;
import com.christofmeg.ic2cuumatter.integration.jei.MassFabricatorCategory;
import com.christofmeg.ic2cuumatter.integration.jei.PlasmafierCategory;
import ic2.core.block.machines.containers.ev.PlasmafierContainer;
import ic2.core.block.machines.containers.hv.MassFabricatorContainer;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import java.util.Arrays;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JEIPlugin
implements IModPlugin {
    @NotNull
    public ResourceLocation getPluginUid() {
        return new ResourceLocation("ic2cuumatter", "jei_plugin");
    }

    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new IRecipeCategory[]{new MassFabricatorCategory(registration.getJeiHelpers().getGuiHelper(), (ItemLike)IC2Blocks.MASS_FABRICATOR), new PlasmafierCategory(registration.getJeiHelpers().getGuiHelper(), PlasmafierContainer.TEXTURE, (ItemLike)IC2Blocks.PLASMAFIER)});
    }

    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(MassFabricatorCategory.TYPE, Arrays.asList(new MassFabricatorCategory.MassFabricatorRecipe(Ingredient.m_43927_((ItemStack[])new ItemStack[]{new ItemStack((ItemLike)Items.f_41852_)}), new ItemStack((ItemLike)IC2Items.UUMATTER), "0"), new MassFabricatorCategory.MassFabricatorRecipe(Ingredient.m_43927_((ItemStack[])new ItemStack[]{new ItemStack((ItemLike)IC2Items.SCRAP)}), new ItemStack((ItemLike)IC2Items.UUMATTER), "1,000"), new MassFabricatorCategory.MassFabricatorRecipe(Ingredient.m_43927_((ItemStack[])new ItemStack[]{new ItemStack((ItemLike)IC2Items.SCRAPBOX)}), new ItemStack((ItemLike)IC2Items.UUMATTER), "45,000"), new MassFabricatorCategory.MassFabricatorRecipe(Ingredient.m_43927_((ItemStack[])new ItemStack[]{new ItemStack((ItemLike)IC2Items.SCRAP_METAL)}), new ItemStack((ItemLike)IC2Items.UUMATTER), "100,000")));
        registration.addRecipes(PlasmafierCategory.TYPE, List.of(new PlasmafierCategory.PlasmafierRecipe(Ingredient.m_43927_((ItemStack[])new ItemStack[]{new ItemStack((ItemLike)IC2Items.UUMATTER, 10)}), Ingredient.m_43929_((ItemLike[])new ItemLike[]{IC2Items.CELL_EMPTY}), new ItemStack((ItemLike)IC2Items.CELL_PLASMA))));
    }

    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.MASS_FABRICATOR), new RecipeType[]{MassFabricatorCategory.TYPE});
        registration.addRecipeCatalyst(new ItemStack((ItemLike)IC2Blocks.PLASMAFIER), new RecipeType[]{PlasmafierCategory.TYPE});
    }

    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGuiContainerHandler(IC2Screen.class, (IGuiContainerHandler)new GuiClickableArea());
    }

    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        int mrecipeSlotStart = 0;
        int mrecipeSlotCount = 1;
        int minventorySlotStart = 2;
        int minventorySlotCount = 41;
        registration.addRecipeTransferHandler(MassFabricatorContainer.class, null, MassFabricatorCategory.TYPE, mrecipeSlotStart, mrecipeSlotCount, minventorySlotStart, minventorySlotCount);
        int recipeSlotStart = 0;
        int recipeSlotCount = 2;
        int inventorySlotStart = 2;
        int inventorySlotCount = 42;
        registration.addRecipeTransferHandler(PlasmafierContainer.class, null, PlasmafierCategory.TYPE, recipeSlotStart, recipeSlotCount, inventorySlotStart, inventorySlotCount);
    }
}

