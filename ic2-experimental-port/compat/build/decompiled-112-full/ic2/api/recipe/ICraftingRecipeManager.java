/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.api.recipe;

import net.minecraft.item.ItemStack;

public interface ICraftingRecipeManager {
    public void addRecipe(ItemStack var1, Object ... var2);

    public void addShapelessRecipe(ItemStack var1, Object ... var2);

    public static class AttributeContainer {
        public final boolean hidden;
        public final boolean consuming;
        public final boolean fixedSize;

        public AttributeContainer(boolean hidden, boolean consuming) {
            this(hidden, consuming, false);
        }

        public AttributeContainer(boolean hidden, boolean consuming, boolean fixedSize) {
            this.hidden = hidden;
            this.consuming = consuming;
            this.fixedSize = fixedSize;
        }
    }
}

