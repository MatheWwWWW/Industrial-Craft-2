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
import ic2.core.block.machine.tileentity.TileEntityCanner;
import net.minecraft.world.item.ItemStack;

public class InvSlotConsumableCanner
extends InvSlotConsumableLiquid {
    public InvSlotConsumableCanner(TileEntityCanner tileEntityCanner, String string, int n) {
        super(tileEntityCanner, string, n);
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        switch (((TileEntityCanner)this.base).getMode()) {
            case BottleSolid: {
                return Recipes.cannerBottle.get(this.base.getParent().m_58904_()).apply(new ICannerBottleRecipeManager.RawInput(itemStack, ((TileEntityCanner)this.base).inputSlot.get()), true) != null;
            }
            case BottleLiquid: 
            case EmptyLiquid: 
            case EnrichLiquid: {
                return super.accepts(itemStack);
            }
        }
        assert (false);
        return false;
    }
}

