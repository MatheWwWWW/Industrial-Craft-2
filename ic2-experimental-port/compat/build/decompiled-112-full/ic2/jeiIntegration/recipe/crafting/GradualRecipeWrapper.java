/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.ingredients.IIngredients
 *  mezz.jei.api.recipe.BlankRecipeWrapper
 *  net.minecraft.client.Minecraft
 *  net.minecraft.item.ItemStack
 */
package ic2.jeiIntegration.recipe.crafting;

import ic2.core.init.Localization;
import ic2.core.recipe.GradualRecipe;
import ic2.core.util.Util;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class GradualRecipeWrapper
extends BlankRecipeWrapper {
    private final GradualRecipe recipe;

    public GradualRecipeWrapper(GradualRecipe recipe) {
        this.recipe = recipe;
    }

    public List<ItemStack> getInputs() {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>(2);
        ret.add(this.recipe.chargeMaterial);
        ItemStack repairItem = this.recipe.func_77571_b();
        this.recipe.item.setCustomDamage(repairItem, this.recipe.amount);
        ret.add(repairItem);
        return ret;
    }

    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        assert (this.recipe.item.getMaxCustomDamage(this.recipe.func_77571_b()) > 0);
        String effectiveness = Localization.translate("ic2.jei.condenser", Float.valueOf(Util.limit((float)this.recipe.amount / (float)this.recipe.item.getMaxCustomDamage(this.recipe.func_77571_b()) * 100.0f, 0.0f, 100.0f)));
        int width = minecraft.field_71466_p.func_78256_a(effectiveness);
        if (143 - width < 55) {
            minecraft.field_71466_p.func_78279_b(effectiveness, 55, 88, 90, Color.darkGray.getRGB());
        } else {
            minecraft.field_71466_p.func_78276_b(effectiveness, (55 + (143 - width)) / 2, 42, Color.darkGray.getRGB());
        }
    }

    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, this.getInputs());
        ingredients.setOutput(ItemStack.class, (Object)this.recipe.func_77571_b());
    }
}

