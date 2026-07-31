/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.block.machines.logic.crafter;

import ic2.core.block.machines.logic.crafter.CraftingList;

public interface IMemorySlotProvider {
    public int getEnabledSlots();

    public boolean isServerSided();

    public CraftingList getRecipes();
}

