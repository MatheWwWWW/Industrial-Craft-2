/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class CropReed
extends Ic2CropCard {
    public CropReed(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public Block getCropBlock() {
        return Ic2Blocks.REED_CROP;
    }

    @Override
    public String getDiscoveredBy() {
        return "Notch";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(2, 0, 0, 1, 0, 2);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Reed"};
    }

    @Override
    public int getWeightInfluences(ICropTile iCropTile, int n, int n2, int n3) {
        return (int)((double)n * 1.2 + (double)n2 + (double)n3 * 0.8);
    }

    @Override
    public boolean canBeHarvested(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() > 0;
    }

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        return new ItemStack((ItemLike)Items.f_41909_, iCropTile.getCurrentAge());
    }

    @Override
    public boolean onEntityCollision(ICropTile iCropTile, Entity entity) {
        return false;
    }

    @Override
    public int getGrowthDuration(ICropTile iCropTile) {
        return 200;
    }
}

