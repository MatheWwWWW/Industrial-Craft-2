/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.api.recipe;

import ic2.api.recipe.IBasicMachineRecipeManager;
import java.util.Map;
import net.minecraft.item.ItemStack;

public interface IScrapboxManager
extends IBasicMachineRecipeManager {
    public void addDrop(ItemStack var1, float var2);

    public ItemStack getDrop(ItemStack var1, boolean var2);

    public Map<ItemStack, Float> getDrops();
}

