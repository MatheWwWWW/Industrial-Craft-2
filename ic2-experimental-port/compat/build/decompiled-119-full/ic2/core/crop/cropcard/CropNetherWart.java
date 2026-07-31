/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class CropNetherWart
extends Ic2CropCard {
    public CropNetherWart(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public Block getCropBlock() {
        return Ic2Blocks.NETHER_WART_CROP;
    }

    @Override
    public String getDiscoveredBy() {
        return "Notch";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(5, 4, 2, 0, 2, 1);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Red", "Nether", "Ingredient", "Soulsand"};
    }

    @Override
    public double dropGainChance() {
        return 2.0;
    }

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        return new ItemStack((ItemLike)Items.f_42588_, 1);
    }

    @Override
    public void tick(ICropTile iCropTile) {
        if (iCropTile.isBlockBelow(Blocks.f_50135_)) {
            if (this.canGrow(iCropTile)) {
                iCropTile.setGrowthPoints(iCropTile.getGrowthPoints() + 100);
            }
        } else if (iCropTile.isBlockBelow(Blocks.f_50125_) && iCropTile.getWorldObj().f_46441_.m_188503_(300) == 0) {
            iCropTile.setCrop(Ic2Crops.cropTerraWart);
        }
    }

    @Override
    public int getRootsLength(ICropTile iCropTile) {
        return 5;
    }
}

