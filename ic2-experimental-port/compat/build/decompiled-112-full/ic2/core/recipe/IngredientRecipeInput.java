/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntComparators
 *  it.unimi.dsi.fastutil.ints.IntList
 *  javax.annotation.Nullable
 *  net.minecraft.client.util.RecipeItemHelper
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.Ingredient
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.recipe;

import ic2.api.recipe.IRecipeInput;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Comparator;
import javax.annotation.Nullable;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class IngredientRecipeInput
extends Ingredient {
    private IRecipeInput part;
    private ItemStack[] items;
    private IntList list;

    IngredientRecipeInput(IRecipeInput part) {
        super(0);
        this.part = part;
    }

    public ItemStack[] func_193365_a() {
        if (this.items == null) {
            this.items = this.part.getInputs().toArray(new ItemStack[0]);
        }
        return this.items;
    }

    public boolean apply(@Nullable ItemStack item) {
        return this.part.matches(item);
    }

    @SideOnly(value=Side.CLIENT)
    public IntList func_194139_b() {
        if (this.list == null) {
            ItemStack[] items = this.func_193365_a();
            this.list = new IntArrayList(items.length);
            for (ItemStack itemstack : items) {
                this.list.add(RecipeItemHelper.func_194113_b((ItemStack)itemstack));
            }
            this.list.sort((Comparator)IntComparators.NATURAL_COMPARATOR);
        }
        return this.list;
    }
}

