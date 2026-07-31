/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.api.recipe.ICannerBottleRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.machine.tileentity.TileEntitySolidCanner;
import net.minecraft.world.item.ItemStack;

public class InvSlotProcessableSolidCanner
extends InvSlotProcessable<ICannerBottleRecipeManager.Input, ItemStack, ICannerBottleRecipeManager.RawInput> {
    public InvSlotProcessableSolidCanner(TileEntitySolidCanner tileEntitySolidCanner, String string, int n) {
        super(tileEntitySolidCanner, string, n, Recipes.cannerBottle);
    }

    @Override
    protected ICannerBottleRecipeManager.RawInput getInput(ItemStack itemStack) {
        return new ICannerBottleRecipeManager.RawInput(((TileEntitySolidCanner)this.base).canInputSlot.get(), itemStack);
    }

    @Override
    protected void setInput(ICannerBottleRecipeManager.RawInput rawInput) {
        ((TileEntitySolidCanner)this.base).canInputSlot.put(rawInput.container);
        this.put(rawInput.fill);
    }
}

