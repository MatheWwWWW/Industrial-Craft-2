/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.api.recipe.ICannerBottleRecipeManager;
import ic2.api.recipe.Recipes;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.machine.tileentity.TileEntitySolidCanner;
import net.minecraft.world.item.ItemStack;

public class InvSlotConsumableSolidCanner
extends InvSlotConsumableLiquid {
    public InvSlotConsumableSolidCanner(TileEntitySolidCanner tileEntitySolidCanner, String string, int n) {
        super(tileEntitySolidCanner, string, n);
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        return Recipes.cannerBottle.get(this.base.getParent().m_58904_()).apply(new ICannerBottleRecipeManager.RawInput(itemStack, ((TileEntitySolidCanner)this.base).inputSlot.get()), true) != null;
    }
}

