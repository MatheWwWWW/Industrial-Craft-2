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

public class CropCoffee
extends Ic2CropCard {
    public CropCoffee(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public Block getCropBlock() {
        return Ic2Blocks.COFFEE_CROP;
    }

    @Override
    public String getDiscoveredBy() {
        return "Snoochy";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(7, 1, 4, 1, 2, 0);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Leaves", "Ingredient", "Beans"};
    }

    @Override
    public boolean canGrow(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() < this.getMaxAge() && iCropTile.getLightLevel() >= 9;
    }

    @Override
    public int getWeightInfluences(ICropTile iCropTile, int n, int n2, int n3) {
        return (int)(0.4 * (double)n + 1.4 * (double)n2 + 1.2 * (double)n3);
    }

    @Override
    public int getGrowthDuration(ICropTile iCropTile) {
        if (iCropTile.getCurrentAge() == this.getMaxAge() - 2) {
            return (int)((double)super.getGrowthDuration(iCropTile) * 0.5);
        }
        if (iCropTile.getCurrentAge() == this.getMaxAge() - 3) {
            return (int)((double)super.getGrowthDuration(iCropTile) * 1.5);
        }
        return super.getGrowthDuration(iCropTile);
    }

    @Override
    public boolean canBeHarvested(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() >= this.getMaxAge() - 1;
    }

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        if (iCropTile.getCurrentAge() == this.getMaxAge() - 1) {
            return null;
        }
        return new ItemStack((ItemLike)Ic2Items.COFFEE_BEANS);
    }

    @Override
    public int getAgeAfterHarvest(ICropTile iCropTile) {
        return this.getMaxAge() - 2;
    }
}

