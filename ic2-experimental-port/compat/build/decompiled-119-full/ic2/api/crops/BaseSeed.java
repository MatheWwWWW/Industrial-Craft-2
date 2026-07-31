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

    public BaseSeed(CropCard cropCard, int n, int n2, int n3, int n4) {
        this.crop = cropCard;
        this.size = n;
        this.statGrowth = n2;
        this.statGain = n3;
        this.statResistance = n4;
    }

    @Deprecated
    public BaseSeed(CropCard cropCard, int n, int n2, int n3, int n4, int n5) {
        this(cropCard, n, n2, n3, n4);
    }
}

