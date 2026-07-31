/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.utils.plugins;

import net.minecraft.world.item.ItemStack;

public interface IJEIHelper {
    public boolean isValid();

    public void triggerUsage(ItemStack var1);

    public void triggerRecipe(ItemStack var1);

    public static class Dummy
    implements IJEIHelper {
        @Override
        public boolean isValid() {
            return false;
        }

        @Override
        public void triggerUsage(ItemStack stack) {
        }

        @Override
        public void triggerRecipe(ItemStack stack) {
        }
    }
}

