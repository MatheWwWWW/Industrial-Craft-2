/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 */
package ic2.core.crop;

import ic2.api.crops.CropCard;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.ResourceLocation;

public abstract class IC2CropCard
extends CropCard {
    @Override
    public String getOwner() {
        return "ic2";
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
        ArrayList<ResourceLocation> ret = new ArrayList<ResourceLocation>(this.getMaxSize());
        for (int size = 1; size <= this.getMaxSize(); ++size) {
            ret.add(new ResourceLocation("ic2", "blocks/crop/" + this.getId() + "_" + size));
        }
        return ret;
    }
}

