/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.DyeItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import ic2.core.crop.Ic2CropCard;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class CropColorFlower
extends Ic2CropCard {
    public String name;
    public Block cropBlock;
    public String[] attributes;
    public DyeColor color;

    public CropColorFlower(ICropType iCropType, Block block, String[] stringArray, DyeColor dyeColor) {
        super(iCropType);
        this.name = iCropType.getName();
        this.cropBlock = block;
        this.attributes = stringArray;
        this.color = dyeColor;
    }

    @Override
    public Block getCropBlock() {
        return this.cropBlock;
    }

    @Override
    public String getDiscoveredBy() {
        if (this.name.equals("dandelion") || this.name.equals("rose")) {
            return "Notch";
        }
        return "Alblaka";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(2, 1, 1, 0, 5, 1);
    }

    @Override
    public String[] getAttributes() {
        return this.attributes;
    }

    @Override
    public boolean canGrow(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() <= this.getMaxAge() - 1 && iCropTile.getLightLevel() >= 12;
    }

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        return new ItemStack((ItemLike)DyeItem.m_41082_((DyeColor)this.color));
    }

    @Override
    public int getAgeAfterHarvest(ICropTile iCropTile) {
        return this.getMaxAge() - 1;
    }

    @Override
    public int getGrowthDuration(ICropTile iCropTile) {
        if (iCropTile.getCurrentAge() == this.getMaxAge() - 1) {
            return 600;
        }
        return 400;
    }
}

