/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  mezz.jei.api.constants.VanillaTypes
 *  mezz.jei.api.gui.builder.IRecipeLayoutBuilder
 *  mezz.jei.api.gui.drawable.IDrawable
 *  mezz.jei.api.gui.drawable.IDrawableAnimated$StartDirection
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
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
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

public class PlasmafierCategory
implements IRecipeCategory<PlasmafierRecipe> {
    public static final RecipeType<PlasmafierRecipe> TYPE = new RecipeType(new ResourceLocation("ic2cuumatter", "plasmafier"), PlasmafierRecipe.class);
    public static final ResourceLocation plasmafierTexture = new ResourceLocation("ic2cuumatter", "textures/gui/plasmafier.png");
    public static final ResourceLocation slotVanilla = new ResourceLocation("jei", "textures/gui/slot.png");
    IDrawable background;
    IDrawable icon;
    IDrawable press1;
    IDrawable press2;
    IDrawable press3;
    IDrawable tank1;
    IDrawable tank2;
    IDrawable tank3;
    IDrawable plasma;
    IDrawable glass;
    IDrawable slot;
    IDrawable frame;
    IDrawable frameBottom;
    IDrawable frameTop;
    int input1X = 31;
    int input1Y = 21;
    int input2X = 103;
    int input2Y = 9;
    int outputX = 103;
    int outputY = 31;

    public PlasmafierCategory(IGuiHelper helper, ResourceLocation texture, ItemLike itemStack) {
        this.background = helper.createBlankDrawable(132, 64);
        this.icon = helper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)new ItemStack(itemStack));
        this.slot = helper.drawableBuilder(slotVanilla, 0, 0, 18, 18).setTextureSize(18, 18).build();
        this.frame = helper.drawableBuilder(texture, 78, 15, 20, 52).build();
        this.frameBottom = helper.drawableBuilder(texture, 79, 67, 18, 1).build();
        this.frameTop = helper.drawableBuilder(texture, 79, 14, 18, 1).build();
        this.press1 = helper.createDrawable(texture, 176, 41, 12, 1);
        this.press2 = helper.createDrawable(texture, 176, 42, 12, 1);
        this.press3 = helper.createDrawable(texture, 176, 45, 12, 1);
        this.tank1 = helper.drawableBuilder(plasmafierTexture, 201, 0, 12, 41).buildAnimated(250, IDrawableAnimated.StartDirection.TOP, true);
        this.tank2 = helper.drawableBuilder(plasmafierTexture, 213, 0, 12, 41).buildAnimated(250, IDrawableAnimated.StartDirection.TOP, true);
        this.tank3 = helper.drawableBuilder(plasmafierTexture, 225, 0, 12, 41).buildAnimated(250, IDrawableAnimated.StartDirection.TOP, true);
        this.plasma = helper.drawableBuilder(texture, 176, 0, 12, 41).buildAnimated(250, IDrawableAnimated.StartDirection.TOP, true);
        this.glass = helper.createDrawable(texture, 189, 0, 12, 46);
    }

    @NotNull
    public RecipeType<PlasmafierRecipe> getRecipeType() {
        return TYPE;
    }

    @NotNull
    public Component getTitle() {
        return Component.m_237115_((String)"block.ic2.plasmafier");
    }

    @NotNull
    public IDrawable getBackground() {
        return this.background;
    }

    @NotNull
    public IDrawable getIcon() {
        return this.icon;
    }

    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, PlasmafierRecipe recipe, @NotNull IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, this.input1X, this.input1Y).addIngredients(recipe.tool());
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, this.input2X, this.input2Y).addIngredients(recipe.block());
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, this.outputX, this.outputY).addItemStack(recipe.output());
    }

    public void draw(@NotNull PlasmafierRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull PoseStack stack, double mouseX, double mouseY) {
        this.slot.draw(stack, this.input1X - 1, this.input1Y - 1);
        this.slot.draw(stack, this.input2X - 1, this.input2Y - 1);
        this.slot.draw(stack, this.outputX - 1, this.outputY - 1);
        this.frame.draw(stack, 65, 1);
        this.frameBottom.draw(stack, 66, 53);
        this.frameTop.draw(stack, 66, 0);
        this.press1.draw(stack, 69, 45);
        this.press2.draw(stack, 69, 46);
        this.press2.draw(stack, 69, 47);
        this.press2.draw(stack, 69, 48);
        this.press3.draw(stack, 69, 49);
        this.tank1.draw(stack, 69, 4);
        this.tank2.draw(stack, 69, 5);
        this.tank3.draw(stack, 69, 8);
        this.plasma.draw(stack, 69, 9);
        this.glass.draw(stack, 68, 4);
        Font font = Minecraft.m_91087_().f_91062_;
        MutableComponent tier = Component.m_237115_((String)"translation.ic2cuumatter.tier");
        MutableComponent EV = Component.m_237115_((String)"translation.ic2cuumatter.tier.ev");
        MutableComponent tierEV = Component.m_237110_((String)"translation.ic2cuumatter.format2", (Object[])new Object[]{tier, EV});
        font.m_92889_(stack, (Component)tierEV, 0.0f, 0.0f, 0x404040);
        MutableComponent ticks = Component.m_237115_((String)"translation.ic2cuumatter.ticks");
        MutableComponent ticksFormat = Component.m_237110_((String)"translation.ic2cuumatter.format2", (Object[])new Object[]{ticks, "2,500"});
        font.m_92889_(stack, (Component)ticksFormat, 0.0f, 57.0f, 0x404040);
        font.m_92889_(stack, Component.m_130674_((String)"2,048 EU/p"), 79.0f, 57.0f, 0x404040);
    }

    record PlasmafierRecipe(Ingredient tool, Ingredient block, ItemStack output) {
    }
}

