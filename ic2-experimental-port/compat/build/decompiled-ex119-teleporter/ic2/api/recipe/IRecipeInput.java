/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 */
package ic2.api.recipe;

import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface IRecipeInput {
    public boolean matches(ItemStack var1);

    public int getAmount();

    public List<ItemStack> getInputs();

    default public Ingredient getIngredient() {
        return Ingredient.m_43921_(this.getInputs().stream());
    }
}

