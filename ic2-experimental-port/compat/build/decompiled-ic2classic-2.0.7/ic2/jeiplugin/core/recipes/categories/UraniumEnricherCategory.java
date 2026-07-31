/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  mezz.jei.api.constants.VanillaTypes
 *  mezz.jei.api.gui.builder.IRecipeLayoutBuilder
 *  mezz.jei.api.gui.builder.IRecipeSlotBuilder
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
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.jeiplugin.core.recipes.categories;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.recipes.misc.EnrichRecipe;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.ColorUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class UraniumEnricherCategory
implements IRecipeCategory<EnrichRecipe> {
    RecipeType<EnrichRecipe> id;
    ItemStack stack;
    IDrawable background;
    IDrawable progress;
    IDrawable subProgress;
    IDrawable fuel;
    IDrawable icon;

    public UraniumEnricherCategory(IGuiHelper helper, RecipeType<EnrichRecipe> location, ResourceLocation texture, ItemLike provider) {
        this.id = location;
        this.stack = new ItemStack(provider);
        this.icon = helper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)this.stack);
        this.background = helper.createDrawable(texture, 42, 13, 129, 64);
        this.progress = helper.drawableBuilder(texture, 176, 14, 23, 16).buildAnimated(150, IDrawableAnimated.StartDirection.LEFT, false);
        this.subProgress = helper.drawableBuilder(texture, 176, 36, 52, 34).buildAnimated(250, IDrawableAnimated.StartDirection.LEFT, false);
        this.fuel = helper.drawableBuilder(texture, 176, 75, 4, 33).build();
    }

    public RecipeType<EnrichRecipe> getRecipeType() {
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

    public void draw(EnrichRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        this.progress.draw(stack, 20, 13);
        this.subProgress.draw(stack, 55, 21);
        RenderSystem.m_157429_((float)ColorUtils.getFloatR(recipe.getColor()), (float)ColorUtils.getFloatG(recipe.getColor()), (float)ColorUtils.getFloatB(recipe.getColor()), (float)ColorUtils.getFloatA(recipe.getColor()));
        this.fuel.draw(stack, 49, 3);
        RenderSystem.m_157429_((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Font font = Minecraft.m_91087_().f_91062_;
        font.m_92889_(stack, (Component)Component.m_237115_((String)"gui.ic2.uranium_enricher.per_ingot"), 63.0f, 0.0f, 0x404040);
        font.m_92889_(stack, (Component)Component.m_237113_((String)("" + recipe.getRequiredPoints())), 63.0f, 10.0f, 0x404040);
        ItemStack fuel = recipeSlotsView.findSlotByName("fuel").flatMap(slot1 -> slot1.getDisplayedIngredient((IIngredientType)VanillaTypes.ITEM_STACK)).orElse(ItemStack.f_41583_);
        if (fuel.m_41619_()) {
            return;
        }
        font.m_92889_(stack, (Component)Component.m_237115_((String)"gui.ic2.uranium_enricher.points"), 0.0f, 35.0f, 0x404040);
        font.m_92889_(stack, (Component)Component.m_237113_((String)("" + recipe.getFuelValue(fuel))), 0.0f, 45.0f, 0x404040);
    }

    public void setRecipe(IRecipeLayoutBuilder layout, EnrichRecipe recipe, IFocusGroup focus) {
        ((IRecipeSlotBuilder)layout.addSlot(RecipeIngredientRole.INPUT, 3, 14).addItemStacks(recipe.getFuels())).setSlotName("fuel");
        ObjectList inputs = CollectionUtils.createList();
        for (Item item : recipe.getInputs()) {
            inputs.add((ItemStack)new ItemStack((ItemLike)item));
        }
        layout.addSlot(RecipeIngredientRole.INPUT, 39, 45).addItemStacks(inputs);
        layout.addSlot(RecipeIngredientRole.OUTPUT, 110, 30).addItemStack(recipe.getOutput());
    }
}

