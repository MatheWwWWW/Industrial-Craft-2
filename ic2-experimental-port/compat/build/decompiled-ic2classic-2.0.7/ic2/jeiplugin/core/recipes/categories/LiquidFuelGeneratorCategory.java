/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
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
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.jeiplugin.core.recipes.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.recipes.registries.IFluidFuelRegistry;
import ic2.core.utils.helpers.Formatters;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fluids.FluidStack;

public class LiquidFuelGeneratorCategory
implements IRecipeCategory<IFluidFuelRegistry.FuelEntry> {
    RecipeType<IFluidFuelRegistry.FuelEntry> id;
    ItemStack stack;
    IDrawable background;
    IDrawable progress;
    IDrawable charge;
    IDrawable icon;

    public LiquidFuelGeneratorCategory(IGuiHelper helper, RecipeType<IFluidFuelRegistry.FuelEntry> location, ResourceLocation texture, ItemLike provider) {
        this.id = location;
        this.stack = new ItemStack(provider);
        this.icon = helper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)this.stack);
        this.background = helper.createDrawable(texture, 45, 12, 77, 64);
        this.progress = helper.drawableBuilder(texture, 176, 14, 24, 16).buildAnimated(150, IDrawableAnimated.StartDirection.LEFT, false);
        this.charge = helper.drawableBuilder(texture, 176, 0, 14, 14).buildAnimated(500, IDrawableAnimated.StartDirection.TOP, true);
    }

    public RecipeType<IFluidFuelRegistry.FuelEntry> getRecipeType() {
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

    public void draw(IFluidFuelRegistry.FuelEntry recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        this.progress.draw(stack, 47, 22);
        this.charge.draw(stack, 25, 24);
        Font font = Minecraft.m_91087_().f_91062_;
        font.m_92889_(stack, (Component)Component.m_237115_((String)"gui.ic2.liquid_gen.burntime"), 25.0f, 2.0f, 0x404040);
        font.m_92889_(stack, (Component)Component.m_237113_((String)(Formatters.EU_FORMAT.format(recipe.getTicksPerBucket()) + "t")), 25.0f, 12.0f, 0x404040);
        font.m_92889_(stack, (Component)Component.m_237115_((String)"gui.ic2.liquid_gen.eu_ticks"), 25.0f, 42.0f, 0x404040);
        font.m_92889_(stack, (Component)Component.m_237113_((String)(Formatters.EU_FORMAT.format(recipe.getEuPerTick()) + "EU")), 25.0f, 52.0f, 0x404040);
    }

    public void setRecipe(IRecipeLayoutBuilder layout, IFluidFuelRegistry.FuelEntry recipe, IFocusGroup focus) {
        ((IRecipeSlotBuilder)layout.addSlot(RecipeIngredientRole.INPUT, 3, 3).addIngredient((IIngredientType)ForgeTypes.FLUID_STACK, (Object)new FluidStack(recipe.getFluid(), 1000))).setFluidRenderer(1L, false, 16, 58);
    }
}

