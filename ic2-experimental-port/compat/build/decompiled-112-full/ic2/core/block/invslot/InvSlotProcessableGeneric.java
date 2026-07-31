/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlotProcessable;
import java.util.Collection;
import net.minecraft.item.ItemStack;

public class InvSlotProcessableGeneric
extends InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack> {
    public InvSlotProcessableGeneric(IInventorySlotHolder<?> base, String name, int count, IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack> recipeManager) {
        super(base, name, count, recipeManager);
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

