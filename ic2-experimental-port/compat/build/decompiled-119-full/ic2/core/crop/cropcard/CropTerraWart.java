/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import ic2.core.crop.Ic2CropCard;
import ic2.core.crop.Ic2Crops;
import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class CropTerraWart
extends Ic2CropCard {
    public CropTerraWart(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public Block getCropBlock() {
        return Ic2Blocks.TERRA_WART_CROP;
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(5, 2, 4, 0, 3, 0);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Blue", "Aether", "Consumable", "Snow"};
    }

    @Override
    public double dropGainChance() {
        return 0.8;
    }

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        return new ItemStack((ItemLike)Ic2Items.TERRA_WART);
    }

    @Override
    public void tick(ICropTile iCropTile) {
        if (iCropTile.isBlockBelow(Blocks.f_50125_)) {
            if (this.canGrow(iCropTile)) {
                iCropTile.setGrowthPoints(iCropTile.getGrowthPoints() + 100);
            }
        } else if (iCropTile.isBlockBelow(Blocks.f_50135_) && iCropTile.getWorldObj().f_46441_.m_188503_(300) == 0) {
            iCropTile.setCrop(Ic2Crops.cropNetherWart);
        }
    }

    @Override
    public int getRootsLength(ICropTile iCropTile) {
        return 5;
    }
}

