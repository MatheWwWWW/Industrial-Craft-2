/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.LongTag
 */
package ic2.core.block.machines.logic.crop;

import ic2.api.crops.ICrop;
import net.minecraft.nbt.LongTag;

public interface ISeedEntry {
    public int getGain();

    public int getGrowth();

    public int getResistance();

    public int getTier();

    public ICrop getCrop(ICrop var1);

    public int getCount();

    default public boolean isEmpty() {
        return this.getCount() <= 0;
    }

    public int grow(int var1);

    public int drain(int var1, boolean var2);

    public LongTag write();

    default public ISeedEntry getWrapped() {
        return this;
    }
}

