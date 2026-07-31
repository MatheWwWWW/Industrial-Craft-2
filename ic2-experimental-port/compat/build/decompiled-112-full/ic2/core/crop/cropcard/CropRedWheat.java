/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.BlockPos
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.core.crop.IC2CropCard;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class CropRedWheat
extends IC2CropCard {
    @Override
    public String getId() {
        return "redwheat";
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
    public int getMaxSize() {
        return 7;
    }

    @Override
    public boolean canGrow(ICropTile crop) {
        return crop.getCurrentSize() < 7 && crop.getLightLevel() <= 10 && crop.getLightLevel() >= 5;
    }

    @Override
    public boolean canBeHarvested(ICropTile crop) {
        return crop.getCurrentSize() == 7;
    }

    @Override
    public double dropGainChance() {
        return 0.5;
    }

    @Override
    public int getOptimalHarvestSize(ICropTile crop) {
        return 7;
    }

    @Override
    public ItemStack getGain(ICropTile crop) {
        BlockPos coords = crop.getPosition();
        if (crop.getWorldObj().func_175687_A(coords) > 0 || crop.getWorldObj().field_73012_v.nextBoolean()) {
            return new ItemStack(Items.field_151137_ax, 1);
        }
        return new ItemStack(Items.field_151015_O, 1);
    }

    @Override
    public boolean isRedstoneSignalEmitter(ICropTile crop) {
        return true;
    }

    @Override
    public int getEmittedRedstoneSignal(ICropTile crop) {
        return crop.getCurrentSize() == 7 ? 15 : 0;
    }

    @Override
    public int getEmittedLight(ICropTile crop) {
        return crop.getCurrentSize() == 7 ? 7 : 0;
    }

    @Override
    public int getGrowthDuration(ICropTile crop) {
        return 600;
    }

    @Override
    public int getSizeAfterHarvest(ICropTile crop) {
        return 2;
    }
}

