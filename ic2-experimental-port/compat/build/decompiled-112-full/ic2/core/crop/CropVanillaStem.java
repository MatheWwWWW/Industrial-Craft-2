/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.crop;

import ic2.api.crops.ICropTile;
import ic2.core.crop.CropVanilla;

public abstract class CropVanillaStem
extends CropVanilla {
    protected CropVanillaStem(int maxAge) {
        super(maxAge);
    }

    @Override
    public int getWeightInfluences(ICropTile crop, int humidity, int nutrients, int air) {
        return (int)((double)humidity * 1.1 + (double)nutrients * 0.9 + (double)air);
    }

    @Override
    public int getSizeAfterHarvest(ICropTile crop) {
        return this.maxAge - 1;
    }
}

