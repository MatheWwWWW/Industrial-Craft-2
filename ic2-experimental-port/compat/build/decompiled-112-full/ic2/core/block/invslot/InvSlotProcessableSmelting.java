/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.api.recipe.Recipes;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlotProcessable;
import net.minecraft.item.ItemStack;

public class InvSlotProcessableSmelting
extends InvSlotProcessable<ItemStack, ItemStack, ItemStack> {
    public InvSlotProcessableSmelting(IInventorySlotHolder<?> base, String name, int count) {
        super(base, name, count, Recipes.furnace);
    }

    @Override
    protected ItemStack getInput(ItemStack stack) {
        return stack;
    }

    @Override
    protected void setInput(ItemStack input) {
        this.put(input);
    }
}

