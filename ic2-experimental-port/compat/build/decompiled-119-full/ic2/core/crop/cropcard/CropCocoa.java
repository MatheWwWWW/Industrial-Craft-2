/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import ic2.core.crop.Ic2CropCard;
import ic2.core.ref.Ic2Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class CropCocoa
extends Ic2CropCard {
    public CropCocoa(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public Block getCropBlock() {
        return Ic2Blocks.COCOA_CROP;
    }

    @Override
    public String getDiscoveredBy() {
        return "Notch";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(3, 1, 3, 0, 4, 0);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Brown", "Food", "Stem"};
    }

    @Override
    public boolean canGrow(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() <= this.getMaxAge() - 1 && iCropTile.getStorageNutrients() >= 3;
    }

    @Override
    public int getWeightInfluences(ICropTile iCropTile, int n, int n2, int n3) {
        return (int)((double)n * 0.8 + (double)n2 * 1.3 + (double)n3 * 0.9);
    }

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        return new ItemStack((ItemLike)Items.f_42533_);
    }

    @Override
    public int getGrowthDuration(ICropTile iCropTile) {
        if (iCropTile.getCurrentAge() == this.getMaxAge() - 1) {
            return 900;
        }
        return 400;
    }

    @Override
    public int getAgeAfterHarvest(ICropTile iCropTile) {
        return this.getMaxAge() - 1;
    }
}

