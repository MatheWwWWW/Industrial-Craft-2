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
import ic2.core.IC2;
import ic2.core.crop.Ic2CropCard;
import ic2.core.ref.Ic2Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class CropPotato
extends Ic2CropCard {
    public CropPotato(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public Block getCropBlock() {
        return Ic2Blocks.CARROTS_CROP;
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(2, 0, 4, 0, 0, 2);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Yellow", "Food", "Potato"};
    }

    @Override
    public boolean canGrow(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() < 4 && iCropTile.getLightLevel() >= 9;
    }

    @Override
    public int getOptimalHarvestAge(ICropTile iCropTile) {
        return this.getMaxAge() - 1;
    }

    @Override
    public boolean canBeHarvested(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() >= this.getMaxAge() - 1;
    }

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        if (iCropTile.getCurrentAge() >= this.getMaxAge() && IC2.random.m_188503_(20) <= 0) {
            return new ItemStack((ItemLike)Items.f_42675_);
        }
        if (iCropTile.getCurrentAge() >= this.getMaxAge() - 1) {
            return new ItemStack((ItemLike)Items.f_42620_);
        }
        return null;
    }
}

