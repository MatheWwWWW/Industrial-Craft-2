/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.item.ItemStack
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.core.crop.cropcard.CropBaseMetalCommon;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class CropBaseMetalUncommon
extends CropBaseMetalCommon {
    public CropBaseMetalUncommon(String cropName, String[] cropAttributes, Block[] cropRootsRequirement, ItemStack cropDrop) {
        super(cropName, cropAttributes, cropRootsRequirement, cropDrop);
    }

    public CropBaseMetalUncommon(String cropName, String[] cropAttributes, String[] cropRootsRequirement, ItemStack cropDrop) {
        super(cropName, cropAttributes, cropRootsRequirement, cropDrop);
    }

    @Override
    public int getMaxSize() {
        return 5;
    }

    @Override
    public boolean canGrow(ICropTile crop) {
        if (crop.getCurrentSize() < 4) {
            return true;
        }
        if (crop.getCurrentSize() == 4) {
            if (this.cropRootsRequirement == null || this.cropRootsRequirement.length == 0) {
                return true;
            }
            for (Object aux : this.cropRootsRequirement) {
                if (aux instanceof String && crop.isBlockBelow((String)aux)) {
                    return true;
                }
                if (!(aux instanceof Block) || !crop.isBlockBelow((Block)aux)) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(6, 2, 0, 0, 2, 0);
    }

    @Override
    public boolean canBeHarvested(ICropTile crop) {
        return crop.getCurrentSize() == 5;
    }

    @Override
    public int getOptimalHarvestSize(ICropTile crop) {
        return 5;
    }

    @Override
    public double dropGainChance() {
        return Math.pow(0.95, this.getProperties().getTier());
    }

    @Override
    public int getGrowthDuration(ICropTile crop) {
        if (crop.getCurrentSize() == 4) {
            return 2200;
        }
        return 750;
    }
}

