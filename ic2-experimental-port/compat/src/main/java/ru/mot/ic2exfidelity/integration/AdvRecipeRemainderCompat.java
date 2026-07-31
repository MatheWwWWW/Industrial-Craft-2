package ru.mot.ic2exfidelity.integration;

import ic2.api.recipe.IRecipeInput;
import ic2.core.IC2;
import ic2.core.recipe.AdvRecipe;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;

/** Makes IC2 recipe-input stack counts consume their exact declared amount. */
public final class AdvRecipeRemainderCompat {
    private AdvRecipeRemainderCompat() {
    }

    public static NonNullList<ItemStack> getRemainder(
            AdvRecipe recipe, CraftingContainer crafting) {
        NonNullList<ItemStack> remainder = NonNullList.m_122780_(
                crafting.m_6643_(), ItemStack.f_41583_);
        if (!recipe.consuming) {
            for (int slot = 0; slot < crafting.m_6643_(); slot++) {
                ItemStack stack = crafting.m_8020_(slot);
                if (!stack.m_41619_() && IC2.envProxy.hasRecipeRemainder(stack)) {
                    remainder.set(slot, IC2.envProxy.getRecipeRemainder(stack));
                }
            }
        }

        List<Integer> occupied = new ArrayList<>();
        for (int slot = 0; slot < crafting.m_6643_(); slot++) {
            if (!crafting.m_8020_(slot).m_41619_()) {
                occupied.add(slot);
            }
        }
        IRecipeInput[] matched = matches(recipe.input, crafting, occupied)
                ? recipe.input
                : recipe.inputMirrored != null
                        && matches(recipe.inputMirrored, crafting, occupied)
                        ? recipe.inputMirrored : null;
        if (matched == null) {
            return remainder;
        }

        // ResultSlot performs the normal one-item shrink after this callback.
        // Remove only the extra amount here so a declared N consumes exactly N.
        for (int index = 0; index < matched.length; index++) {
            int extra = matched[index].getAmount() - 1;
            if (extra > 0) {
                crafting.m_8020_(occupied.get(index)).m_41774_(extra);
            }
        }
        return remainder;
    }

    private static boolean matches(
            IRecipeInput[] inputs, CraftingContainer crafting, List<Integer> occupied) {
        if (inputs.length != occupied.size()) {
            return false;
        }
        for (int index = 0; index < inputs.length; index++) {
            if (!inputs[index].matches(crafting.m_8020_(occupied.get(index)))) {
                return false;
            }
        }
        return true;
    }
}
