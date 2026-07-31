/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package ic2.core.inventory.base;

import net.minecraft.nbt.CompoundTag;

public interface INBTSavable {
    public CompoundTag save(CompoundTag var1);

    public void load(CompoundTag var1);
}

