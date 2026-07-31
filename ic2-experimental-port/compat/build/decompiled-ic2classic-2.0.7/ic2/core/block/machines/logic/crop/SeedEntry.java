/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.LongTag
 */
package ic2.core.block.machines.logic.crop;

import ic2.api.crops.ICrop;
import ic2.core.block.machines.logic.crop.ISeedEntry;
import ic2.core.utils.math.MathUtils;
import net.minecraft.nbt.LongTag;

public class SeedEntry
implements ISeedEntry {
    int stat;
    int amount;

    public SeedEntry(long data) {
        this.stat = MathUtils.getIntValue(data);
        this.amount = MathUtils.getIntKey(data);
    }

    public SeedEntry(int growth, int gain, int resistance, int tier, int amount) {
        this.stat = (tier & 0x7F) << 15 | ICrop.combineStats(growth, gain, resistance);
        this.amount = amount;
    }

    public int hashCode() {
        return this.stat;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ISeedEntry) {
            return obj.hashCode() == this.hashCode();
        }
        return false;
    }

    @Override
    public int getTier() {
        return this.stat >> 15 & 0x7F;
    }

    @Override
    public int getGain() {
        return ICrop.getGain(this.stat);
    }

    @Override
    public int getGrowth() {
        return ICrop.getGrowth(this.stat);
    }

    @Override
    public int getResistance() {
        return ICrop.getResistance(this.stat);
    }

    @Override
    public ICrop getCrop(ICrop defaultValue) {
        return defaultValue;
    }

    @Override
    public int getCount() {
        return this.amount;
    }

    @Override
    public int grow(int count) {
        this.amount += count;
        return count;
    }

    @Override
    public int drain(int count, boolean doDrain) {
        int result = Math.min(count, this.amount);
        if (doDrain) {
            this.amount -= result;
        }
        return result;
    }

    @Override
    public LongTag write() {
        return LongTag.m_128882_((long)MathUtils.putIntoLong(this.amount, this.stat));
    }
}

