package ru.mot.ic2exfidelity.integration;

import ic2.api.recipe.IRecipeInput;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.item.ItemStack;

/** Makes IC2 strict-NBT recipe ingredients safe for JEI's ingredient scan. */
public final class RecipeInputCompat {
    private RecipeInputCompat() {
    }

    public static List<ItemStack> normalize(IRecipeInput input, List<ItemStack> stacks) {
        List<ItemStack> mutable = new ArrayList<>(stacks.size());
        for (ItemStack stack : stacks) {
            mutable.add(StackUtil.setImmutableSize(stack, input.getAmount()));
        }
        return Collections.unmodifiableList(mutable);
    }
}
