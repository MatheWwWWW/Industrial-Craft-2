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
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.jeiplugin.core.recipes.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.recipes.registries.IRareEarthRegistry;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RareEarthCategory
implements IRecipeCategory<IRareEarthRegistry.RareEntry> {
    RecipeType<IRareEarthRegistry.RareEntry> id;
    ItemStack stack;
    IDrawable background;
    IDrawable progress;
    IDrawable charge;
    IDrawable icon;

    public RareEarthCategory(IGuiHelper helper, RecipeType<IRareEarthRegistry.RareEntry> location, ResourceLocation texture, ItemLike provider) {
        this.id = location;
        this.stack = new ItemStack(provider);
        this.icon = helper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)this.stack);
        this.background = helper.createDrawable(texture, 50, 15, 90, 60);
        this.progress = helper.drawableBuilder(texture, 176, 14, 23, 16).buildAnimated(150, IDrawableAnimated.StartDirection.LEFT, false);
        this.charge = helper.drawableBuilder(texture, 176, 0, 13, 14).buildAnimated(500, IDrawableAnimated.StartDirection.TOP, true);
    }

    public RecipeType<IRareEarthRegistry.RareEntry> getRecipeType() {
        return this.id;
    }

    public Component getTitle() {
        return this.stack.m_41786_();
    }

    public IDrawable getBackground() {
        return this.background;
    }

    public IDrawable getIcon() {
        return this.icon;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void draw(IRareEarthRegistry.RareEntry recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        this.progress.draw(stack, 29, 19);
        this.charge.draw(stack, 6, 21);
        float itemCount = 1000.0f / recipe.getValue();
        Font font = Minecraft.m_91087_().f_91062_;
        font.m_92883_(stack, ItemStack.f_41584_.format(recipe.getValue()) + " Points", 25.0f, 43.0f, 0x404040);
        font.m_92883_(stack, ItemStack.f_41584_.format(itemCount) + " Items", 25.0f, 53.0f, 0x404040);
    }

    public void setRecipe(IRecipeLayoutBuilder layout, IRareEarthRegistry.RareEntry recipe, IFocusGroup focus) {
        layout.addSlot(RecipeIngredientRole.INPUT, 6, 2).addItemStack(recipe.getInput());
        layout.addSlot(RecipeIngredientRole.OUTPUT, 66, 20).addItemStack(recipe.getOutput());
    }
}

