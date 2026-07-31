/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.ingredients.IIngredients
 *  mezz.jei.api.recipe.BlankRecipeWrapper
 *  net.minecraft.client.Minecraft
 *  net.minecraft.item.ItemStack
 */
package ic2.jeiIntegration.recipe.misc;

import ic2.api.recipe.Recipes;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.ItemName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ScrapboxRecipeWrapper
extends BlankRecipeWrapper {
    private final Map.Entry<ItemStack, Float> entry;

    public ScrapboxRecipeWrapper(Map.Entry<ItemStack, Float> entry) {
        this.entry = entry;
    }

    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        float value = this.entry.getValue().floatValue();
        String text = (double)value < 0.001 ? "< 0.01" : "  " + String.format("%.2f", Float.valueOf(value * 100.0f));
        minecraft.field_71466_p.func_78276_b(text + "%", 86, 9, 0x404040);
    }

    public static List<ScrapboxRecipeWrapper> createRecipes() {
        ArrayList<ScrapboxRecipeWrapper> recipes = new ArrayList<ScrapboxRecipeWrapper>();
        for (Map.Entry<ItemStack, Float> e : Recipes.scrapboxDrops.getDrops().entrySet()) {
            recipes.add(new ScrapboxRecipeWrapper(e));
        }
        return recipes;
    }

    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, (Object)ItemName.crafting.getItemStack(CraftingItemType.scrap_box));
        ingredients.setOutput(ItemStack.class, (Object)this.entry.getKey());
    }
}

