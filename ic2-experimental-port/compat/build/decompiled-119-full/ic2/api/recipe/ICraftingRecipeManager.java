/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.recipe;

import net.minecraft.world.item.ItemStack;

public interface ICraftingRecipeManager {
    public void addRecipe(ItemStack var1, Object ... var2);

    public void addShapelessRecipe(ItemStack var1, Object ... var2);

    public static class AttributeContainer {
        public final boolean hidden;
        public final boolean consuming;
        public final boolean fixedSize;

        public AttributeContainer(boolean bl, boolean bl2) {
            this(bl, bl2, false);
        }

        public AttributeContainer(boolean bl, boolean bl2, boolean bl3) {
            this.hidden = bl;
            this.consuming = bl2;
            this.fixedSize = bl3;
        }
    }
}

