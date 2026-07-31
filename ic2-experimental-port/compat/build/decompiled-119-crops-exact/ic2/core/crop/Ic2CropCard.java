/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.ICropType;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

public abstract class Ic2CropCard
extends CropCard {
    public Ic2CropCard(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public String getUnlocalizedName() {
        return "ic2.crop." + this.getId();
    }

    @Override
    public String getDiscoveredBy() {
        return "IC2 Team";
    }

    @Override
    public List<ResourceLocation> getTexturesLocation() {
        ArrayList<ResourceLocation> arrayList = new ArrayList<ResourceLocation>(this.getMaxAge());
        for (int i = 1; i <= this.getMaxAge(); ++i) {
            arrayList.add(new ResourceLocation("ic2", "blocks/crop/" + this.getId() + "_" + i));
        }
        return arrayList;
    }
}
