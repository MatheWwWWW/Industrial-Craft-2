/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.api.recipe.Recipes;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlotProcessable;
import net.minecraft.world.item.ItemStack;

public class InvSlotProcessableSmelting
extends InvSlotProcessable<ItemStack, ItemStack, ItemStack> {
    public InvSlotProcessableSmelting(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n) {
        super(iInventorySlotHolder, string, n, level -> Recipes.furnace);
    }

    @Override
    protected ItemStack getInput(ItemStack itemStack) {
        return itemStack;
    }

    @Override
    protected void setInput(ItemStack itemStack) {
        this.put(itemStack);
    }
}

