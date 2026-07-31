/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlotProcessable;
import java.util.Collection;
import net.minecraft.world.item.ItemStack;

public class InvSlotProcessableGeneric
extends InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack> {
    public InvSlotProcessableGeneric(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n, Recipes.IGetter<? extends IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>> iGetter) {
        super(iInventorySlotHolder, string, n, iGetter);
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

