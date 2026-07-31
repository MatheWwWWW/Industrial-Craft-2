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
 *  net.minecraft.world.level.ItemLike
 */
package ic2.jeiplugin.core.recipes.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.recipes.registries.IElectrolyzerRecipeList;
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
import net.minecraft.world.level.ItemLike;

public class ElectrolyzerCategory
implements IRecipeCategory<IElectrolyzerRecipeList.ElectrolyzerRecipe> {
    RecipeType<IElectrolyzerRecipeList.ElectrolyzerRecipe> id;
    ItemStack stack;
    IDrawable background;
    IDrawable charge;
    IDrawable icon;

    public ElectrolyzerCategory(IGuiHelper helper, RecipeType<IElectrolyzerRecipeList.ElectrolyzerRecipe> location, ResourceLocation texture, ItemLike provider) {
        this.id = location;
        this.stack = new ItemStack(provider);
        this.background = helper.drawableBuilder(texture, 45, 30, 90, 45).addPadding(0, 0, 20, 20).build();
        this.charge = helper.drawableBuilder(texture, 176, 14, 24, 16).buildAnimated(150, IDrawableAnimated.StartDirection.LEFT, false);
        this.icon = helper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)this.stack);
    }

    public RecipeType<IElectrolyzerRecipeList.ElectrolyzerRecipe> getRecipeType() {
        return this.id;
    }

    public IDrawable getBackground() {
        return this.background;
    }

    public IDrawable getIcon() {
        return this.icon;
    }

    public Component getTitle() {
        return this.stack.m_41786_();
    }

    public void draw(IElectrolyzerRecipeList.ElectrolyzerRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
        boolean dual;
        this.charge.draw(matrixStack, 54, 4);
        Font font = Minecraft.m_91087_().f_91062_;
        boolean bl = dual = recipe.isChargeRecipe() && recipe.isDischargeRecipe();
        MutableComponent comp = Component.m_237115_((String)("gui.ic2.electrolyzer." + (dual ? "dual" : (recipe.isChargeRecipe() ? "charging" : "discharging"))));
        font.m_92889_(matrixStack, (Component)comp, (float)(0 - font.m_92724_(comp.m_7532_()) / 2 + 65), 25.0f, 0x404040);
        comp = Component.m_237110_((String)"gui.ic2.electrolyzer.energy", (Object[])new Object[]{recipe.getEnergy()});
        font.m_92889_(matrixStack, (Component)comp, (float)(0 - font.m_92724_(comp.m_7532_()) / 2 + 65), 36.0f, 0x404040);
    }

    public void setRecipe(IRecipeLayoutBuilder layout, IElectrolyzerRecipeList.ElectrolyzerRecipe recipe, IFocusGroup focus) {
        layout.addSlot(RecipeIngredientRole.INPUT, 29, 5).addItemStack(recipe.getInput());
        layout.addSlot(RecipeIngredientRole.OUTPUT, 87, 5).addItemStack(recipe.getOutput());
    }
}

