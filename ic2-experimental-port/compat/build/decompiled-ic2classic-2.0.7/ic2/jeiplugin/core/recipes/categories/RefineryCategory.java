/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  mezz.jei.api.constants.VanillaTypes
 *  mezz.jei.api.forge.ForgeTypes
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
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package ic2.jeiplugin.core.recipes.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.recipes.registries.IRefiningRecipeList;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.mutable.MutableInt;

public class RefineryCategory
implements IRecipeCategory<IRefiningRecipeList.FluidRecipe> {
    RecipeType<IRefiningRecipeList.FluidRecipe> id;
    ItemStack stack;
    IDrawable background;
    IDrawable progress;
    IDrawable charge;
    IDrawable icon;

    public RefineryCategory(IGuiHelper helper, RecipeType<IRefiningRecipeList.FluidRecipe> location, ResourceLocation texture, ItemLike provider) {
        this.id = location;
        this.stack = new ItemStack(provider);
        this.icon = helper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)this.stack);
        this.background = helper.createDrawable(texture, 13, 15, 137, 64);
        this.progress = helper.drawableBuilder(texture, 176, 14, 23, 16).buildAnimated(150, IDrawableAnimated.StartDirection.LEFT, false);
        this.charge = helper.drawableBuilder(texture, 176, 0, 13, 14).buildAnimated(500, IDrawableAnimated.StartDirection.TOP, true);
    }

    public RecipeType<IRefiningRecipeList.FluidRecipe> getRecipeType() {
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

    public void draw(IRefiningRecipeList.FluidRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        this.progress.draw(stack, 66, 23);
        this.charge.draw(stack, 44, 25);
    }

    private List<List<ItemStack>> getOutputs(IRefiningRecipeList.FluidRecipe recipe) {
        ObjectList lists = CollectionUtils.createList();
        for (int i = 0; i < 3; ++i) {
            lists.add(CollectionUtils.createList());
        }
        MutableInt index = new MutableInt();
        Object2IntOpenHashMap itemMap = new Object2IntOpenHashMap();
        for (ItemStack stack : recipe.getOutput().getAllOutputs()) {
            ((List)lists.get(itemMap.computeIfAbsent((Object)stack.m_41720_(), T -> index.getAndIncrement() % 3))).add(stack);
        }
        return lists;
    }

    public void setRecipe(IRecipeLayoutBuilder layout, IRefiningRecipeList.FluidRecipe recipe, IFocusGroup focus) {
        List<List<ItemStack>> outputs = this.getOutputs(recipe);
        layout.addSlot(RecipeIngredientRole.INPUT, 44, 6).addItemStacks(recipe.getItemInput().getComponents());
        layout.addSlot(RecipeIngredientRole.OUTPUT, 97, 6).addItemStacks(outputs.get(0));
        layout.addSlot(RecipeIngredientRole.OUTPUT, 97, 24).addItemStacks(outputs.get(1));
        layout.addSlot(RecipeIngredientRole.OUTPUT, 97, 42).addItemStacks(outputs.get(2));
        if (!recipe.getFirstTank().isEmpty()) {
            ((IRecipeSlotBuilder)layout.addSlot(RecipeIngredientRole.INPUT, 3, 3).addIngredient((IIngredientType)ForgeTypes.FLUID_STACK, (Object)recipe.getFirstTank())).setFluidRenderer(1L, false, 16, 58);
        }
        if (!recipe.getSecondTank().isEmpty()) {
            ((IRecipeSlotBuilder)layout.addSlot(RecipeIngredientRole.INPUT, 23, 3).addIngredient((IIngredientType)ForgeTypes.FLUID_STACK, (Object)recipe.getSecondTank())).setFluidRenderer(1L, false, 16, 58);
        }
        ((IRecipeSlotBuilder)layout.addSlot(RecipeIngredientRole.OUTPUT, 118, 3).addIngredients((IIngredientType)ForgeTypes.FLUID_STACK, recipe.getOutput().getAllFluidOutputs())).setFluidRenderer(1L, false, 16, 58);
    }
}

