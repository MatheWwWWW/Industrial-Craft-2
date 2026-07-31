/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.slot;

import net.minecraft.world.item.ItemStack;

public interface IGhostSlot {
    public int getSlotID();

    public int getXPos();

    public int getYPos();

    public boolean isStackValid(ItemStack var1);

    public void setFilter(ItemStack var1);

    default public GhostType getType() {
        return GhostType.NORMAL;
    }

    public static enum GhostType {
        FILTER,
        NORMAL;

    }
}

