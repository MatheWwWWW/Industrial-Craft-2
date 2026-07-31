/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.core.crop.IC2CropCard;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CropColorFlower
extends IC2CropCard {
    public String name;
    public String[] attributes;
    public int color;

    public CropColorFlower(String n, String[] a, int c) {
        this.name = n;
        this.attributes = a;
        this.color = c;
    }

    @Override
    public String getDiscoveredBy() {
        if (this.name.equals("dandelion") || this.name.equals("rose")) {
            return "Notch";
        }
        return "Alblaka";
    }

    @Override
    public String getId() {
        return this.name;
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
    public int getMaxSize() {
        return 4;
    }

    @Override
    public boolean canGrow(ICropTile crop) {
        return crop.getCurrentSize() <= 3 && crop.getLightLevel() >= 12;
    }

    @Override
    public boolean canBeHarvested(ICropTile crop) {
        return crop.getCurrentSize() == 4;
    }

    @Override
    public int getOptimalHarvestSize(ICropTile crop) {
        return 4;
    }

    @Override
    public ItemStack getGain(ICropTile crop) {
        return new ItemStack(Items.field_151100_aR, 1, this.color);
    }

    @Override
    public int getSizeAfterHarvest(ICropTile crop) {
        return 3;
    }

    @Override
    public int getGrowthDuration(ICropTile crop) {
        if (crop.getCurrentSize() == 3) {
            return 600;
        }
        return 400;
    }
}

