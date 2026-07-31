/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.recipe;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

@Deprecated
public final class RecipeOutput {
    public final List<ItemStack> items;
    public final CompoundTag metadata;

    public RecipeOutput(CompoundTag compoundTag, List<ItemStack> list) {
        assert (!list.contains(null));
        this.metadata = compoundTag;
        this.items = list;
    }

    public RecipeOutput(CompoundTag compoundTag, ItemStack ... itemStackArray) {
        this(compoundTag, Arrays.asList(itemStackArray));
    }

    public boolean equals(Object object) {
        if (object instanceof RecipeOutput) {
            RecipeOutput recipeOutput = (RecipeOutput)object;
            if (this.items.size() == recipeOutput.items.size() && (this.metadata == null && recipeOutput.metadata == null || this.metadata != null && recipeOutput.metadata != null && this.metadata.equals((Object)recipeOutput.metadata))) {
                Iterator<ItemStack> iterator = this.items.iterator();
                Iterator<ItemStack> iterator2 = recipeOutput.items.iterator();
                while (iterator.hasNext() && iterator2.hasNext()) {
                    ItemStack itemStack;
                    ItemStack itemStack2 = iterator.next();
                    if (!ItemStack.m_41728_((ItemStack)itemStack2, (ItemStack)(itemStack = iterator2.next()))) continue;
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "ROutput<" + this.items + "," + this.metadata + ">";
    }
}

