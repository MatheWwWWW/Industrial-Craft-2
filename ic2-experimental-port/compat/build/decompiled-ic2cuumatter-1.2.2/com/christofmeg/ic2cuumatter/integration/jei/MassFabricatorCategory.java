/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  mezz.jei.api.constants.VanillaTypes
 *  mezz.jei.api.gui.builder.IRecipeLayoutBuilder
 *  mezz.jei.api.gui.drawable.IDrawable
 *  mezz.jei.api.gui.ingredient.IRecipeSlotsView
 *  mezz.jei.api.helpers.IGuiHelper
 *  mezz.jei.api.ingredients.IIngredientType
 *  mezz.jei.api.recipe.IFocusGroup
 *  mezz.jei.api.recipe.RecipeIngredientRole
 *  mezz.jei.api.recipe.RecipeType
 *  mezz.jei.api.recipe.category.IRecipeCategory
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  org.jetbrains.annotations.NotNull
 */
package com.christofmeg.ic2cuumatter.integration.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Objects;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class MassFabricatorCategory
implements IRecipeCategory<MassFabricatorRecipe> {
    public static final RecipeType<MassFabricatorRecipe> TYPE = new RecipeType(new ResourceLocation("ic2cuumatter", "mass_fabricator"), MassFabricatorRecipe.class);
    public static final ResourceLocation slotVanilla = new ResourceLocation("jei", "textures/gui/slot.png");
    public static final ResourceLocation guiVanilla = new ResourceLocation("jei", "textures/gui/gui_vanilla.png");
    IDrawable background;
    IDrawable icon;
    IDrawable slot;
    IDrawable bigSlot;

    public MassFabricatorCategory(IGuiHelper helper, ItemLike itemStack) {
        this.background = helper.createBlankDrawable(133, 69);
        this.icon = helper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)new ItemStack(itemStack));
        this.slot = helper.drawableBuilder(slotVanilla, 0, 0, 18, 18).setTextureSize(18, 18).build();
        this.bigSlot = helper.drawableBuilder(guiVanilla, 90, 74, 26, 26).build();
    }

    @NotNull
    public RecipeType<MassFabricatorRecipe> getRecipeType() {
        return TYPE;
    }

    @NotNull
    public Component getTitle() {
        return Component.m_237115_((String)"block.ic2.mass_fabricator");
    }

    @NotNull
    public IDrawable getBackground() {
        return this.background;
    }

    @NotNull
    public IDrawable getIcon() {
        return this.icon;
    }

    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, MassFabricatorRecipe recipe, @NotNull IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 67, 42).addIngredients(recipe.item());
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 67, 5).addItemStack(recipe.output());
    }

    public void draw(MassFabricatorRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull PoseStack stack, double mouseX, double mouseY) {
        this.slot.draw(stack, 66, 41);
        this.bigSlot.draw(stack, 62, 0);
        Font font = Minecraft.m_91087_().f_91062_;
        MutableComponent tier = Component.m_237115_((String)"translation.ic2cuumatter.tier");
        MutableComponent HV = Component.m_237115_((String)"translation.ic2cuumatter.tier.hv");
        MutableComponent tierHV = Component.m_237110_((String)"translation.ic2cuumatter.format2", (Object[])new Object[]{tier, HV});
        font.m_92889_(stack, (Component)tierHV, 0.0f, 0.0f, 0x404040);
        MutableComponent energy = Component.m_237115_((String)"translation.ic2cuumatter.energy");
        MutableComponent energyFormat = Component.m_237110_((String)"translation.ic2cuumatter.format1", (Object[])new Object[]{energy});
        font.m_92889_(stack, (Component)energyFormat, 0.0f, 52.0f, 0x404040);
        font.m_92889_(stack, Component.m_130674_((String)"7,000,000 EU"), 0.0f, 62.0f, 0x404040);
        font.m_92889_(stack, Component.m_130674_((String)"512 EU/p"), 88.0f, 62.0f, 0x404040);
        if (!Objects.equals(recipe.getEnergy(), "0")) {
            MutableComponent amplifier = Component.m_237115_((String)"translation.ic2cuumatter.amplifier");
            MutableComponent amplifierFormat = Component.m_237110_((String)"translation.ic2cuumatter.format1", (Object[])new Object[]{amplifier});
            font.m_92889_(stack, (Component)amplifierFormat, 90.0f, 20.0f, 0x404040);
            if (Objects.equals(recipe.getEnergy(), "100,000")) {
                font.m_92889_(stack, Component.m_130674_((String)("+ " + recipe.getEnergy())), 86.0f, 30.0f, 0x404040);
            } else if (Objects.equals(recipe.getEnergy(), "45,000")) {
                font.m_92889_(stack, Component.m_130674_((String)("+ " + recipe.getEnergy())), 92.0f, 30.0f, 0x404040);
            } else {
                font.m_92889_(stack, Component.m_130674_((String)("+ " + recipe.getEnergy())), 98.0f, 30.0f, 0x404040);
            }
        }
    }

    record MassFabricatorRecipe(Ingredient item, ItemStack output, String energy) {
        public String getEnergy() {
            return this.energy;
        }
    }
}

