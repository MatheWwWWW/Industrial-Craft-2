/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.api.recipe;

import java.util.List;
import net.minecraft.item.ItemStack;

public interface IPatternStorage {
    public boolean addPattern(ItemStack var1);

    public List<ItemStack> getPatterns();
}

