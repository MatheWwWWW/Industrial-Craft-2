/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.recipe;

import ic2.api.recipe.IRecipeInput;
import ic2.core.recipe.RecipeInputBase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.item.ItemStack;

public class RecipeInputMultiple
extends RecipeInputBase
implements IRecipeInput {
    private final IRecipeInput[] inputs;

    RecipeInputMultiple(IRecipeInput ... inputs) {
        this.inputs = inputs;
    }

    RecipeInputMultiple(List<IRecipeInput> inputs) {
        this.inputs = inputs.toArray(new IRecipeInput[0]);
    }

    @Override
    public boolean matches(ItemStack subject) {
        for (IRecipeInput input : this.inputs) {
            if (!input.matches(subject)) continue;
            return true;
        }
        return false;
    }

    @Override
    public int getAmount() {
        return 1;
    }

    @Override
    public List<ItemStack> getInputs() {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for (IRecipeInput input : this.inputs) {
            list.addAll(input.getInputs());
        }
        return Collections.unmodifiableList(list);
    }

    public String toString() {
        if (this.inputs.length <= 0) {
            return "RecipeInputMultiple<Nothing>";
        }
        StringBuilder b = new StringBuilder("RecipeInputMultiple<");
        int i = 0;
        int end = this.inputs.length - 1;
        while (true) {
            b.append(this.inputs[i].toString());
            if (i == end) {
                return b.append('>').toString();
            }
            b.append(", ");
            ++i;
        }
    }

    public boolean equals(Object obj) {
        IRecipeInput[] otherInputs;
        if (obj != null && this.getClass() == obj.getClass() && this.inputs.length == (otherInputs = ((RecipeInputMultiple)obj).inputs).length) {
            for (int i = 0; i < this.inputs.length; ++i) {
                if (this.inputs[i].equals(otherInputs[i])) continue;
                return false;
            }
            return true;
        }
        return false;
    }
}

