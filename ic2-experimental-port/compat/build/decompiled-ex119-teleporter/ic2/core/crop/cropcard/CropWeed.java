/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import ic2.core.crop.Ic2CropCard;
import ic2.core.ref.Ic2Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class CropWeed
extends Ic2CropCard {
    public CropWeed(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public Block getCropBlock() {
        return Ic2Blocks.WEED_CROP;
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(0, 0, 0, 1, 0, 5);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Weed", "Bad"};
    }

    @Override
    public int getOptimalHarvestAge(ICropTile iCropTile) {
        return 1;
    }

    @Override
    public boolean onLeftClick(ICropTile iCropTile, Player player) {
        return false;
    }

    @Override
    public boolean canBeHarvested(ICropTile iCropTile) {
        return false;
    }

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        return null;
    }

    @Override
    public int getGrowthDuration(ICropTile iCropTile) {
        return 300;
    }

    @Override
    public boolean onEntityCollision(ICropTile iCropTile, Entity entity) {
        return false;
    }
}

