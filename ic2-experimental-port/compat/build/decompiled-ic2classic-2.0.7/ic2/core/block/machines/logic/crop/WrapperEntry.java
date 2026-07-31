/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.LongTag
 */
package ic2.core.block.machines.logic.crop;

import ic2.api.crops.ICrop;
import ic2.core.block.machines.logic.crop.ISeedEntry;
import java.util.Objects;
import net.minecraft.nbt.LongTag;

public class WrapperEntry
implements ISeedEntry {
    ISeedEntry wrapped;
    ICrop crop;

    public WrapperEntry(ICrop crop, ISeedEntry wrapped) {
        this.crop = crop;
        this.wrapped = wrapped;
    }

    public int hashCode() {
        return Objects.hash(this.wrapped, this.crop);
    }

    public boolean equals(Object obj) {
        if (obj instanceof WrapperEntry) {
            WrapperEntry other = (WrapperEntry)obj;
            return other.crop == this.crop && other.wrapped.equals(this.wrapped);
        }
        return false;
    }

    @Override
    public int getTier() {
        return this.wrapped.getTier();
    }

    @Override
    public int getGain() {
        return this.wrapped.getGain();
    }

    @Override
    public int getGrowth() {
        return this.wrapped.getGrowth();
    }

    @Override
    public int getResistance() {
        return this.wrapped.getResistance();
    }

    @Override
    public ICrop getCrop(ICrop defaultValue) {
        return this.crop;
    }

    @Override
    public int getCount() {
        return this.wrapped.getCount();
    }

    @Override
    public int grow(int count) {
        return this.wrapped.grow(count);
    }

    @Override
    public int drain(int count, boolean doDrain) {
        return this.wrapped.drain(count, doDrain);
    }

    @Override
    public LongTag write() {
        return this.wrapped.write();
    }

    @Override
    public ISeedEntry getWrapped() {
        return this.wrapped;
    }
}

