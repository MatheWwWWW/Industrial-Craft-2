/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  mezz.jei.api.gui.ingredient.IRecipeSlotsView
 *  mezz.jei.api.helpers.IGuiHelper
 *  mezz.jei.api.recipe.RecipeType
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.integration.jei.recipe.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2Items;
import ic2.integration.jei.recipe.machine.DynamicCategory;
import ic2.integration.jei.recipe.machine.IORecipeWrapper;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class MetalFormerCategory
extends DynamicCategory {
    private final int mode;
    private static final ItemStack[] ICONS = new ItemStack[]{new ItemStack((ItemLike)Ic2Items.COPPER_CABLE), new ItemStack((ItemLike)Ic2Items.FORGE_HAMMER), new ItemStack((ItemLike)Ic2Items.CUTTER)};

    public MetalFormerCategory(RecipeType<IORecipeWrapper> recipeType, int n, IGuiHelper iGuiHelper) {
        super(Ic2Blocks.METAL_FORMER, recipeType, iGuiHelper);
        this.mode = n;
    }

    @Override
    public void draw(IORecipeWrapper iORecipeWrapper, IRecipeSlotsView iRecipeSlotsView, PoseStack poseStack, double d, double d2) {
        super.draw(iORecipeWrapper, iRecipeSlotsView, poseStack, d, d2);
        Minecraft.m_91087_().m_91291_().m_115123_(ICONS[this.mode], 70 + this.xOffset, 35 + this.yOffset);
    }
}

