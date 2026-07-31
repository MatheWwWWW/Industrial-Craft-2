/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package ic2.api.tiles;

import net.minecraft.nbt.CompoundTag;

public interface ICopyableSettings {
    public void saveSettings(CompoundTag var1);

    public void loadSettings(CompoundTag var1);
}

