/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectList
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
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.jeiplugin.core.recipes.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class BasicMachineCategory
implements IRecipeCategory<IMachineRecipeList.RecipeEntry> {
    RecipeType<IMachineRecipeList.RecipeEntry> id;
    ItemStack stack;
    IDrawable background;
    IDrawable progress;
    IDrawable charge;
    IDrawable icon;

    public BasicMachineCategory(IGuiHelper helper, RecipeType<IMachineRecipeList.RecipeEntry> location, ResourceLocation texture, ItemLike provider) {
        this.id = location;
        this.stack = new ItemStack(provider);
        this.icon = helper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)this.stack);
        this.background = helper.createDrawable(texture, 50, 15, 90, 55);
        this.progress = helper.drawableBuilder(texture, 176, 14, 23, 16).buildAnimated(150, IDrawableAnimated.StartDirection.LEFT, false);
        this.charge = helper.drawableBuilder(texture, 176, 0, 13, 14).buildAnimated(500, IDrawableAnimated.StartDirection.TOP, true);
    }

    public BasicMachineCategory(IGuiHelper helper, RecipeType<IMachineRecipeList.RecipeEntry> id, ItemStack stack, IDrawable background, IDrawable progress, IDrawable charge) {
        this.id = id;
        this.stack = stack;
        this.background = background;
        this.progress = progress;
        this.charge = charge;
        this.icon = helper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)stack);
    }

    public RecipeType<IMachineRecipeList.RecipeEntry> getRecipeType() {
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

    public void draw(IMachineRecipeList.RecipeEntry recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        this.progress.draw(stack, 29, 19);
        this.charge.draw(stack, 6, 21);
    }

    public void setRecipe(IRecipeLayoutBuilder layout, IMachineRecipeList.RecipeEntry recipe, IFocusGroup focus) {
        layout.addSlot(RecipeIngredientRole.INPUT, 6, 2).addItemStacks(recipe.getInputs()[0].getComponents());
        layout.addSlot(RecipeIngredientRole.OUTPUT, 66, 20).addItemStacks(recipe.getOutput().getAllOutputs());
    }

    public static class AlloySmelterCategory
    extends BasicMachineCategory {
        public AlloySmelterCategory(IGuiHelper helper, RecipeType<IMachineRecipeList.RecipeEntry> location, ResourceLocation texture, ItemLike provider) {
            super(helper, location, new ItemStack(provider), (IDrawable)helper.createDrawable(texture, 40, 15, 100, 60), (IDrawable)helper.drawableBuilder(texture, 176, 14, 23, 16).buildAnimated(150, IDrawableAnimated.StartDirection.LEFT, false), (IDrawable)helper.drawableBuilder(texture, 176, 0, 13, 14).buildAnimated(500, IDrawableAnimated.StartDirection.TOP, true));
        }

        @Override
        public void draw(IMachineRecipeList.RecipeEntry recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
            this.progress.draw(stack, 39, 19);
            this.charge.draw(stack, 16, 21);
        }

        @Override
        public void setRecipe(IRecipeLayoutBuilder layout, IMachineRecipeList.RecipeEntry recipe, IFocusGroup focus) {
            ObjectList lists = CollectionUtils.createList();
            for (IInput inputs : recipe.getInputs()) {
                lists.addAll(inputs.getComponents());
            }
            layout.addSlot(RecipeIngredientRole.INPUT, 6, 2).addItemStacks(recipe.getInputs()[0].getComponents());
            layout.addSlot(RecipeIngredientRole.INPUT, 26, 2).addItemStacks(recipe.getInputs()[1].getComponents());
            layout.addSlot(RecipeIngredientRole.OUTPUT, 76, 20).addItemStacks(recipe.getOutput().getAllOutputs());
        }
    }
}

