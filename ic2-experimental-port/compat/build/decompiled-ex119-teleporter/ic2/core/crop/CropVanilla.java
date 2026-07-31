/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.crop;

import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import ic2.core.crop.Ic2CropCard;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public abstract class CropVanilla
extends Ic2CropCard {
    public CropVanilla(ICropType iCropType) {
        super(iCropType);
    }

    protected List<ResourceLocation> getDefaultTexturesLocation() {
        return super.getTexturesLocation();
    }

    @Override
    public List<ResourceLocation> getTexturesLocation() {
        ArrayList<ResourceLocation> arrayList = new ArrayList<ResourceLocation>(this.getMaxAge());
        for (int i = 1; i <= this.getMaxAge(); ++i) {
            arrayList.add(new ResourceLocation("blocks/" + this.getId() + "_stage_" + i));
        }
        return arrayList;
    }

    @Override
    public String getDiscoveredBy() {
        return "Notch";
    }

    @Override
    public boolean canGrow(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() < this.getMaxAge() && iCropTile.getLightLevel() >= 9;
    }

    protected abstract ItemStack getSeeds();

    protected abstract ItemStack getProduct();

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        return this.getProduct();
    }

    @Override
    public ItemStack getSeeds(ICropTile iCropTile) {
        if (iCropTile.getStatGain() <= 1 && iCropTile.getStatGrowth() <= 1 && iCropTile.getStatResistance() <= 1) {
            return this.getSeeds();
        }
        return super.getSeeds(iCropTile);
    }
}

