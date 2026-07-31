package ru.mot.ic2exfidelity.ic2cuumatter;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

/** The original animated Plasmafier information page. */
public final class LegacyPlasmafierCategory
        implements IRecipeCategory<LegacyPlasmafierCategory.Recipe> {
    public static final RecipeType<Recipe> TYPE = new RecipeType<>(
            new ResourceLocation("ic2cuumatter", "plasmafier"), Recipe.class);
    private static final ResourceLocation ADDON_TEXTURE = new ResourceLocation(
            "ic2cuumatter", "textures/gui/plasmafier.png");
    private static final ResourceLocation SLOT = new ResourceLocation(
            "jei", "textures/gui/slot.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable press1;
    private final IDrawable press2;
    private final IDrawable press3;
    private final IDrawable tank1;
    private final IDrawable tank2;
    private final IDrawable tank3;
    private final IDrawable plasma;
    private final IDrawable glass;
    private final IDrawable slot;
    private final IDrawable frame;
    private final IDrawable frameBottom;
    private final IDrawable frameTop;

    public LegacyPlasmafierCategory(
            IGuiHelper helper, ResourceLocation machineTexture, ItemLike machine) {
        background = helper.createBlankDrawable(132, 64);
        icon = helper.createDrawableIngredient(
                VanillaTypes.ITEM_STACK, new ItemStack(machine));
        slot = helper.drawableBuilder(SLOT, 0, 0, 18, 18)
                .setTextureSize(18, 18).build();
        frame = helper.drawableBuilder(machineTexture, 78, 15, 20, 52).build();
        frameBottom = helper.drawableBuilder(machineTexture, 79, 67, 18, 1).build();
        frameTop = helper.drawableBuilder(machineTexture, 79, 14, 18, 1).build();
        press1 = helper.createDrawable(machineTexture, 176, 41, 12, 1);
        press2 = helper.createDrawable(machineTexture, 176, 42, 12, 1);
        press3 = helper.createDrawable(machineTexture, 176, 45, 12, 1);
        tank1 = helper.drawableBuilder(ADDON_TEXTURE, 201, 0, 12, 41)
                .buildAnimated(250, IDrawableAnimated.StartDirection.TOP, true);
        tank2 = helper.drawableBuilder(ADDON_TEXTURE, 213, 0, 12, 41)
                .buildAnimated(250, IDrawableAnimated.StartDirection.TOP, true);
        tank3 = helper.drawableBuilder(ADDON_TEXTURE, 225, 0, 12, 41)
                .buildAnimated(250, IDrawableAnimated.StartDirection.TOP, true);
        plasma = helper.drawableBuilder(machineTexture, 176, 0, 12, 41)
                .buildAnimated(250, IDrawableAnimated.StartDirection.TOP, true);
        glass = helper.createDrawable(machineTexture, 189, 0, 12, 46);
    }

    @Override
    public RecipeType<Recipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.m_237115_("block.ic2.plasmafier");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(
            IRecipeLayoutBuilder layout, Recipe recipe, IFocusGroup focuses) {
        layout.addSlot(RecipeIngredientRole.INPUT, 31, 21)
                .addIngredients(recipe.matter());
        layout.addSlot(RecipeIngredientRole.INPUT, 103, 9)
                .addIngredients(recipe.cell());
        layout.addSlot(RecipeIngredientRole.OUTPUT, 103, 31)
                .addItemStack(recipe.output());
    }

    @Override
    public void draw(
            Recipe recipe, IRecipeSlotsView slots, PoseStack pose,
            double mouseX, double mouseY) {
        slot.draw(pose, 30, 20);
        slot.draw(pose, 102, 8);
        slot.draw(pose, 102, 30);
        frame.draw(pose, 65, 1);
        frameBottom.draw(pose, 66, 53);
        frameTop.draw(pose, 66, 0);
        press1.draw(pose, 69, 45);
        press2.draw(pose, 69, 46);
        press2.draw(pose, 69, 47);
        press2.draw(pose, 69, 48);
        press3.draw(pose, 69, 49);
        tank1.draw(pose, 69, 4);
        tank2.draw(pose, 69, 5);
        tank3.draw(pose, 69, 8);
        plasma.draw(pose, 69, 9);
        glass.draw(pose, 68, 4);
        Font font = Minecraft.m_91087_().f_91062_;
        font.m_92889_(pose, Component.m_237110_(
                "translation.ic2cuumatter.format2",
                Component.m_237115_("translation.ic2cuumatter.tier"),
                Component.m_237115_("translation.ic2cuumatter.tier.ev")),
                0.0F, 0.0F, 0x404040);
        font.m_92889_(pose, Component.m_237110_(
                "translation.ic2cuumatter.format2",
                Component.m_237115_("translation.ic2cuumatter.ticks"),
                "2,500"), 0.0F, 57.0F, 0x404040);
        font.m_92889_(pose, Component.m_237113_("2,048 EU/p"),
                79.0F, 57.0F, 0x404040);
    }

    public record Recipe(
            Ingredient matter, Ingredient cell, ItemStack output) {
    }
}
