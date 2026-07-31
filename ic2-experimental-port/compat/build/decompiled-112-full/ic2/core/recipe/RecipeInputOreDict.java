/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.NonNullList
 *  net.minecraftforge.oredict.OreDictionary
 */
package ic2.core.recipe;

import ic2.api.recipe.IRecipeInput;
import ic2.core.recipe.RecipeInputBase;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeInputOreDict
extends RecipeInputBase
implements IRecipeInput {
    public final String input;
    public final int amount;
    public final Integer meta;
    private List<ItemStack> ores;

    RecipeInputOreDict(String input) {
        this(input, 1);
    }

    RecipeInputOreDict(String input, int amount) {
        this(input, amount, null);
    }

    RecipeInputOreDict(String input, int amount, Integer meta) {
        this.input = input;
        this.amount = amount;
        this.meta = meta;
    }

    @Override
    public boolean matches(ItemStack subject) {
        List<ItemStack> inputs = this.getOres();
        boolean useOreStackMeta = this.meta == null;
        Item subjectItem = subject.func_77973_b();
        int subjectMeta = subject.func_77952_i();
        for (ItemStack oreStack : inputs) {
            int metaRequired;
            Item oreItem = oreStack.func_77973_b();
            if (oreItem == null) continue;
            int n = metaRequired = useOreStackMeta ? oreStack.func_77952_i() : this.meta.intValue();
            if (subjectItem != oreItem || subjectMeta != metaRequired && metaRequired != Short.MAX_VALUE) continue;
            return true;
        }
        return false;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public List<ItemStack> getInputs() {
        List<ItemStack> ores = this.getOres();
        boolean hasUnsuitableEntries = false;
        for (ItemStack stack : ores) {
            if (StackUtil.getSize(stack) == this.getAmount()) continue;
            hasUnsuitableEntries = true;
            break;
        }
        if (!hasUnsuitableEntries) {
            return ores;
        }
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>(ores.size());
        for (ItemStack stack : ores) {
            if (stack.func_77973_b() == null) continue;
            if (StackUtil.getSize(stack) != this.getAmount()) {
                stack = StackUtil.copyWithSize(stack, this.getAmount());
            }
            ret.add(stack);
        }
        return Collections.unmodifiableList(ret);
    }

    public String toString() {
        if (this.meta == null) {
            return "RInputOreDict<" + this.amount + "x" + this.input + ">";
        }
        return "RInputOreDict<" + this.amount + "x" + this.input + "@" + this.meta + ">";
    }

    public boolean equals(Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            RecipeInputOreDict other = (RecipeInputOreDict)obj;
            if (this.input.equals(other.input) && other.amount == this.amount) {
                return this.meta == null ? other.meta == null : this.meta == other.meta;
            }
        }
        return false;
    }

    private List<ItemStack> getOres() {
        if (this.ores != null) {
            return this.ores;
        }
        NonNullList ret = OreDictionary.getOres((String)this.input);
        if (ret != OreDictionary.EMPTY_LIST) {
            this.ores = ret;
        }
        return ret;
    }
}

