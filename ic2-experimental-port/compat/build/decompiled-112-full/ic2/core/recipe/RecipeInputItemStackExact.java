/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.recipe;

import ic2.core.recipe.RecipeInputBase;
import ic2.core.util.StackUtil;
import java.util.Collections;
import java.util.List;
import net.minecraft.item.ItemStack;

public class RecipeInputItemStackExact
extends RecipeInputBase {
    public final ItemStack input;
    public final int amount;

    RecipeInputItemStackExact(ItemStack input) {
        this(input, StackUtil.getSize(input));
    }

    RecipeInputItemStackExact(ItemStack input, int amount) {
        if (StackUtil.isEmpty(input) || input.func_77960_j() == Short.MAX_VALUE) {
            throw new IllegalArgumentException("invalid input stack");
        }
        this.input = StackUtil.copy(input);
        this.amount = amount;
    }

    @Override
    public boolean matches(ItemStack subject) {
        return subject.func_77973_b() == this.input.func_77973_b() && subject.func_77960_j() == this.input.func_77960_j() && StackUtil.checkNbtEqualityStrict(subject, this.input);
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public List<ItemStack> getInputs() {
        return Collections.singletonList(StackUtil.setImmutableSize(this.input, this.getAmount()));
    }

    public String toString() {
        return "RInputItemStackExact<" + StackUtil.setImmutableSize(this.input, this.amount) + '>';
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        RecipeInputItemStackExact other = (RecipeInputItemStackExact)obj;
        if (!StackUtil.checkItemEqualityStrict(other.input, this.input)) return false;
        if (other.amount != this.amount) return false;
        return true;
    }
}

