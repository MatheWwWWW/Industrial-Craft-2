/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter
 *  mezz.jei.api.ingredients.subtypes.UidContext
 *  net.minecraft.world.item.ItemStack
 */
package ic2.jeiplugin.core.misc;

import ic2.api.items.electric.ElectricItem;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;

public class EUSubTypeInterpreter
implements IIngredientSubtypeInterpreter<ItemStack> {
    public static final EUSubTypeInterpreter INSTANCE = new EUSubTypeInterpreter();

    public String apply(ItemStack itemStack, UidContext context) {
        return context == UidContext.Ingredient ? "charge:" + ElectricItem.MANAGER.getCharge(itemStack) + "/" + ElectricItem.MANAGER.getCapacity(itemStack) : "";
    }
}

