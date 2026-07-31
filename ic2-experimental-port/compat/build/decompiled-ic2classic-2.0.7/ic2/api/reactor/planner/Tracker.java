/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package ic2.api.reactor.planner;

import net.minecraft.nbt.CompoundTag;

public class Tracker {
    private int total = 0;
    private int count = 0;
    private int change = 0;

    public CompoundTag save(CompoundTag tag) {
        tag.m_128405_("total", this.total);
        tag.m_128405_("count", this.count);
        tag.m_128405_("change", this.change);
        return tag;
    }

    public void load(CompoundTag tag) {
        this.total = tag.m_128451_("total");
        this.count = tag.m_128451_("count");
        this.change = tag.m_128451_("change");
    }

    public void addChange(int value) {
        this.change += value;
    }

    public void commit() {
        this.total += this.change;
        this.change = 0;
        ++this.count;
    }

    public void reset() {
        this.total = 0;
        this.count = 0;
        this.change = 0;
    }

    public float getAverage() {
        return this.count == 0 ? 0.0f : (float)this.total / (float)this.count;
    }

    public int getCount() {
        return this.count;
    }

    public int getTotal() {
        return this.total;
    }
}

