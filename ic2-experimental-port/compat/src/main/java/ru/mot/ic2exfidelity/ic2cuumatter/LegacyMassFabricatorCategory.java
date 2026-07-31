package ru.mot.ic2exfidelity.ic2cuumatter;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Objects;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
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

/** The original ic2cuumatter Mass Fabricator information page. */
public final class LegacyMassFabricatorCategory
        implements IRecipeCategory<LegacyMassFabricatorCategory.Recipe> {
    public static final RecipeType<Recipe> TYPE = new RecipeType<>(
            new ResourceLocation("ic2cuumatter", "mass_fabricator"), Recipe.class);
    private static final ResourceLocation SLOT = new ResourceLocation(
            "jei", "textures/gui/slot.png");
    private static final ResourceLocation VANILLA_GUI = new ResourceLocation(
            "jei", "textures/gui/gui_vanilla.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;
    private final IDrawable bigSlot;

    public LegacyMassFabricatorCategory(IGuiHelper helper, ItemLike machine) {
        background = helper.createBlankDrawable(133, 69);
        icon = helper.createDrawableIngredient(
                VanillaTypes.ITEM_STACK, new ItemStack(machine));
        slot = helper.drawableBuilder(SLOT, 0, 0, 18, 18)
                .setTextureSize(18, 18).build();
        bigSlot = helper.drawableBuilder(VANILLA_GUI, 90, 74, 26, 26).build();
    }

    @Override
    public RecipeType<Recipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.m_237115_("block.ic2.mass_fabricator");
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
        layout.addSlot(RecipeIngredientRole.INPUT, 67, 42)
                .addIngredients(recipe.input());
        layout.addSlot(RecipeIngredientRole.OUTPUT, 67, 5)
                .addItemStack(recipe.output());
    }

    @Override
    public void draw(
            Recipe recipe, IRecipeSlotsView slots, PoseStack pose,
            double mouseX, double mouseY) {
        slot.draw(pose, 66, 41);
        bigSlot.draw(pose, 62, 0);
        Font font = Minecraft.m_91087_().f_91062_;
        Component tier = Component.m_237110_(
                "translation.ic2cuumatter.format2",
                Component.m_237115_("translation.ic2cuumatter.tier"),
                Component.m_237115_("translation.ic2cuumatter.tier.hv"));
        font.m_92889_(pose, tier, 0.0F, 0.0F, 0x404040);
        font.m_92889_(pose, Component.m_237110_(
                "translation.ic2cuumatter.format1",
                Component.m_237115_("translation.ic2cuumatter.energy")),
                0.0F, 52.0F, 0x404040);
        font.m_92889_(pose, Component.m_237113_("7,000,000 EU"),
                0.0F, 62.0F, 0x404040);
        font.m_92889_(pose, Component.m_237113_("512 EU/p"),
                88.0F, 62.0F, 0x404040);
        if (!Objects.equals(recipe.amplifier(), "0")) {
            font.m_92889_(pose, Component.m_237110_(
                    "translation.ic2cuumatter.format1",
                    Component.m_237115_("translation.ic2cuumatter.amplifier")),
                    90.0F, 20.0F, 0x404040);
            float x = Objects.equals(recipe.amplifier(), "100,000") ? 86.0F
                    : Objects.equals(recipe.amplifier(), "45,000") ? 92.0F : 98.0F;
            font.m_92889_(pose,
                    Component.m_237113_("+ " + recipe.amplifier()),
                    x, 30.0F, 0x404040);
        }
    }

    public record Recipe(
            Ingredient input, ItemStack output, String amplifier) {
    }
}
