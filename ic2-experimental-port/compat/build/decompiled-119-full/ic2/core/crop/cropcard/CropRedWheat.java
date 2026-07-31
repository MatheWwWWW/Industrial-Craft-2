/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
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
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class CropRedWheat
extends Ic2CropCard {
    public CropRedWheat(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public Block getCropBlock() {
        return Ic2Blocks.RED_WHEAT_CROP;
    }

    @Override
    public String getDiscoveredBy() {
        return "raa1337";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(6, 3, 0, 0, 2, 0);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Red", "Redstone", "Wheat"};
    }

    @Override
    public boolean canGrow(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() < this.getMaxAge() && iCropTile.getLightLevel() <= 10 && iCropTile.getLightLevel() >= 5;
    }

    @Override
    public double dropGainChance() {
        return 0.5;
    }

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        BlockPos blockPos = iCropTile.getPosition();
        if (iCropTile.getWorldObj().m_46755_(blockPos) > 0 || iCropTile.getWorldObj().f_46441_.m_188499_()) {
            return new ItemStack((ItemLike)Items.f_42451_, 1);
        }
        return new ItemStack((ItemLike)Items.f_42405_, 1);
    }

    @Override
    public boolean isRedstoneSignalEmitter(ICropTile iCropTile) {
        return true;
    }

    @Override
    public int getEmittedRedstoneSignal(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() == this.getMaxAge() ? 15 : 0;
    }

    @Override
    public int getEmittedLight(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() == this.getMaxAge() ? 7 : 0;
    }

    @Override
    public int getGrowthDuration(ICropTile iCropTile) {
        return 600;
    }

    @Override
    public int getAgeAfterHarvest(ICropTile iCropTile) {
        return 1;
    }
}

