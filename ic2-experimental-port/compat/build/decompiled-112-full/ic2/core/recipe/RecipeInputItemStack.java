/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.recipe;

import ic2.api.recipe.IRecipeInput;
import ic2.core.recipe.RecipeInputBase;
import ic2.core.util.StackUtil;
import java.util.Arrays;
import java.util.List;
import net.minecraft.item.ItemStack;

public class RecipeInputItemStack
extends RecipeInputBase
implements IRecipeInput {
    public final ItemStack input;
    public final int amount;

    RecipeInputItemStack(ItemStack input) {
        this(input, StackUtil.getSize(input));
    }

    RecipeInputItemStack(ItemStack input, int amount) {
        if (StackUtil.isEmpty(input)) {
            throw new IllegalArgumentException("invalid input stack");
        }
        this.input = input.func_77946_l();
        this.amount = amount;
    }

    @Override
    public boolean matches(ItemStack subject) {
        return !(subject.func_77973_b() != this.input.func_77973_b() || subject.func_77960_j() != this.input.func_77960_j() && this.input.func_77960_j() != Short.MAX_VALUE || this.input.func_77960_j() != Short.MAX_VALUE && !StackUtil.matchesNBT(subject.func_77978_p(), this.input.func_77978_p()));
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public List<ItemStack> getInputs() {
        return Arrays.asList(StackUtil.setImmutableSize(this.input, this.getAmount()));
    }

    public String toString() {
        return "RInputItemStack<" + StackUtil.setImmutableSize(this.input, this.amount) + ">";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        RecipeInputItemStack other = (RecipeInputItemStack)obj;
        if (!StackUtil.checkItemEqualityStrict(other.input, this.input)) return false;
        if (other.amount != this.amount) return false;
        return true;
    }
}

