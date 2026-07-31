/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.crops;

import ic2.api.crops.CropCard;

public class BaseSeed {
    public final CropCard crop;
    public int size;
    public int statGrowth;
    public int statGain;
    public int statResistance;

    public BaseSeed(CropCard crop, int size, int statGrowth, int statGain, int statResistance) {
        this.crop = crop;
        this.size = size;
        this.statGrowth = statGrowth;
        this.statGain = statGain;
        this.statResistance = statResistance;
    }

    @Deprecated
    public BaseSeed(CropCard crop, int size, int statGrowth, int statGain, int statResistance, int stackSize) {
        this(crop, size, statGrowth, statGain, statResistance);
    }
}

