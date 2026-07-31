/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import ic2.core.crop.Ic2CropCard;
import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class CropHops
extends Ic2CropCard {
    public CropHops(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public Block getCropBlock() {
        return Ic2Blocks.HOPS_CROP;
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(5, 2, 2, 0, 1, 1);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Green", "Ingredient", "Wheat"};
    }

    @Override
    public int getGrowthDuration(ICropTile iCropTile) {
        return 600;
    }

    @Override
    public boolean canGrow(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() < this.getMaxAge() && iCropTile.getLightLevel() >= 9;
    }

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        return new ItemStack((ItemLike)Ic2Items.HOPS);
    }

    @Override
    public int getAgeAfterHarvest(ICropTile iCropTile) {
        return 2;
    }
}

