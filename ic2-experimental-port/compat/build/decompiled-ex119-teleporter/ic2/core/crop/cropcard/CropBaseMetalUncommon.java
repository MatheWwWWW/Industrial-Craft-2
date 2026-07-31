/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import ic2.core.crop.cropcard.CropBaseMetalCommon;
import java.util.Collection;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class CropBaseMetalUncommon
extends CropBaseMetalCommon {
    public CropBaseMetalUncommon(ICropType iCropType, Block block, String[] stringArray, Collection<TagKey<Block>> collection, ItemStack itemStack) {
        super(iCropType, block, stringArray, collection, itemStack);
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(6, 2, 0, 0, 2, 0);
    }

    @Override
    public double dropGainChance() {
        return Math.pow(0.95, this.getProperties().getTier());
    }

    @Override
    public int getGrowthDuration(ICropTile iCropTile) {
        if (iCropTile.getCurrentAge() == this.getMaxAge() - 1) {
            return 2200;
        }
        return 750;
    }
}

