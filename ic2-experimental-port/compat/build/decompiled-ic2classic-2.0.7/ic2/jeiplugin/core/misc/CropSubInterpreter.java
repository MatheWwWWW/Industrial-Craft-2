/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter
 *  mezz.jei.api.ingredients.subtypes.UidContext
 *  net.minecraft.world.item.ItemStack
 */
package ic2.jeiplugin.core.misc;

import ic2.api.crops.ICrop;
import ic2.core.block.crops.CropRegistry;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;

public class CropSubInterpreter
implements IIngredientSubtypeInterpreter<ItemStack> {
    public static final CropSubInterpreter INSTANCE = new CropSubInterpreter();

    public String apply(ItemStack itemStack, UidContext context) {
        ICrop crop = CropRegistry.INSTANCE.getCrop(itemStack);
        return crop == null ? "" : crop.id().toString();
    }
}

