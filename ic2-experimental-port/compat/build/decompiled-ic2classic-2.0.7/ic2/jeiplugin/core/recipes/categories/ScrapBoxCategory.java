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
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.jeiplugin.core.recipes.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.recipes.registries.IScrapBoxRegistry;
import ic2.core.platform.registries.IC2Items;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ScrapBoxCategory
implements IRecipeCategory<IScrapBoxRegistry.IDrop> {
    RecipeType<IScrapBoxRegistry.IDrop> id;
    ItemStack stack;
    IDrawable background;
    IDrawable icon;

    public ScrapBoxCategory(IGuiHelper helper, RecipeType<IScrapBoxRegistry.IDrop> location, ResourceLocation texture, ItemLike provider) {
        this.id = location;
        this.stack = new ItemStack(provider);
        this.icon = helper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)this.stack);
        this.background = helper.createDrawable(texture, 50, 25, 90, 45);
    }

    public RecipeType<IScrapBoxRegistry.IDrop> getRecipeType() {
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
    public void draw(IScrapBoxRegistry.IDrop recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        String s = ItemStack.f_41584_.format(recipe.getChance()) + "%";
        Font font = Minecraft.m_91087_().f_91062_;
        font.m_92883_(stack, s, 75.0f - (float)font.m_92895_(s) / 2.0f, 35.0f, 0x404040);
    }

    public void setRecipe(IRecipeLayoutBuilder layout, IScrapBoxRegistry.IDrop recipe, IFocusGroup focus) {
        layout.addSlot(RecipeIngredientRole.INPUT, 6, 10).addItemStack(new ItemStack((ItemLike)IC2Items.SCRAPBOX));
        layout.addSlot(RecipeIngredientRole.OUTPUT, 66, 10).addItemStack(recipe.getDrop());
    }
}

