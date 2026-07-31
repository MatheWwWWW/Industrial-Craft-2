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
import ic2.core.IC2;
import ic2.core.crop.Ic2CropCard;
import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2Items;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class CropStickyReed
extends Ic2CropCard {
    public CropStickyReed(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public Block getCropBlock() {
        return Ic2Blocks.STICKY_REED_CROP;
    }

    @Override
    public String getDiscoveredBy() {
        return "raa1337";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(4, 2, 0, 1, 0, 1);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Reed", "Resin"};
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
        if (iCropTile.getCurrentAge() <= this.getMaxAge() - 1) {
            return new ItemStack((ItemLike)Items.f_41909_, iCropTile.getCurrentAge());
        }
        return new ItemStack((ItemLike)Ic2Items.RESIN);
    }

    @Override
    public int getAgeAfterHarvest(ICropTile iCropTile) {
        if (iCropTile.getCurrentAge() == this.getMaxAge()) {
            return (byte)(2 - IC2.random.m_188503_(2));
        }
        return 0;
    }

    @Override
    public boolean onEntityCollision(ICropTile iCropTile, Entity entity) {
        return false;
    }

    @Override
    public int getGrowthDuration(ICropTile iCropTile) {
        if (iCropTile.getCurrentAge() == this.getMaxAge()) {
            return 400;
        }
        return 100;
    }
}

