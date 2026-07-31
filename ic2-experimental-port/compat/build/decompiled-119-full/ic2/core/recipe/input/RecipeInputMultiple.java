/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.recipe.input;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import ic2.api.recipe.IRecipeInput;
import ic2.core.recipe.input.RecipeInputBase;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.ItemStack;

public class RecipeInputMultiple
extends RecipeInputBase {
    public final IRecipeInput[] inputs;
    private final int amount;

    public RecipeInputMultiple(int n, IRecipeInput ... iRecipeInputArray) {
        this.inputs = iRecipeInputArray;
        this.amount = n;
    }

    public RecipeInputMultiple(int n, List<IRecipeInput> list) {
        this.inputs = list.toArray(new IRecipeInput[0]);
        this.amount = n;
    }

    @Override
    public boolean matches(ItemStack itemStack) {
        for (IRecipeInput iRecipeInput : this.inputs) {
            if (!iRecipeInput.matches(itemStack)) continue;
            return true;
        }
        return false;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public List<ItemStack> listStacks() {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        for (IRecipeInput iRecipeInput : this.inputs) {
            arrayList.addAll(iRecipeInput.getInputs());
        }
        return arrayList;
    }

    public String toString() {
        if (this.inputs.length <= 0) {
            return "RecipeInputMultiple<Nothing>";
        }
        StringBuilder stringBuilder = new StringBuilder("RecipeInputMultiple<");
        int n = 0;
        int n2 = this.inputs.length - 1;
        while (true) {
            stringBuilder.append(this.inputs[n].toString());
            if (n == n2) {
                return stringBuilder.append('>').toString();
            }
            stringBuilder.append(", ");
            ++n;
        }
    }

    public boolean equals(Object object) {
        IRecipeInput[] iRecipeInputArray;
        if (object != null && this.getClass() == object.getClass() && this.inputs.length == (iRecipeInputArray = ((RecipeInputMultiple)object).inputs).length) {
            for (int i = 0; i < this.inputs.length; ++i) {
                if (this.inputs[i].equals(iRecipeInputArray[i])) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public JsonElement toJson() {
        if (this.inputs.length == 1) {
            return ((RecipeInputBase)this.inputs[0]).toJson();
        }
        JsonArray jsonArray = new JsonArray();
        for (IRecipeInput iRecipeInput : this.inputs) {
            jsonArray.add(((RecipeInputBase)iRecipeInput).toJson());
        }
        return jsonArray;
    }
}

