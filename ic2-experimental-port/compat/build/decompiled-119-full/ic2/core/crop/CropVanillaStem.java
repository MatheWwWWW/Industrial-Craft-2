/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.crop;

import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import ic2.core.crop.CropVanilla;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

public abstract class CropVanillaStem
extends CropVanilla {
    public CropVanillaStem(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public List<ResourceLocation> getTexturesLocation() {
        return this.getDefaultTexturesLocation();
    }

    @Override
    public int getWeightInfluences(ICropTile iCropTile, int n, int n2, int n3) {
        return (int)((double)n * 1.1 + (double)n2 * 0.9 + (double)n3);
    }

    @Override
    public int getAgeAfterHarvest(ICropTile iCropTile) {
        return this.getMaxAge() - 1;
    }
}

