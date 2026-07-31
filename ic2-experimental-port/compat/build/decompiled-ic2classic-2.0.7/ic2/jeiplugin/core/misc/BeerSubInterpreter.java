/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter
 *  mezz.jei.api.ingredients.subtypes.UidContext
 *  net.minecraft.world.item.ItemStack
 */
package ic2.jeiplugin.core.misc;

import ic2.core.utils.helpers.StackUtil;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;

public class BeerSubInterpreter
implements IIngredientSubtypeInterpreter<ItemStack> {
    public static final BeerSubInterpreter INSTANCE = new BeerSubInterpreter();

    public String apply(ItemStack stack, UidContext context) {
        return "" + StackUtil.getNbtData(stack).m_128451_("data");
    }
}

