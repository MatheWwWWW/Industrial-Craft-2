/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.core.crop.IC2CropCard;
import ic2.core.crop.IC2Crops;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CropNetherWart
extends IC2CropCard {
    @Override
    public String getId() {
        return "nether_wart";
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
    public int getMaxSize() {
        return 3;
    }

    @Override
    public double dropGainChance() {
        return 2.0;
    }

    @Override
    public ItemStack getGain(ICropTile crop) {
        return new ItemStack(Items.field_151075_bm, 1);
    }

    @Override
    public void tick(ICropTile crop) {
        if (crop.isBlockBelow(Blocks.field_150425_aM)) {
            if (this.canGrow(crop)) {
                crop.setGrowthPoints(crop.getGrowthPoints() + 100);
            }
        } else if (crop.isBlockBelow(Blocks.field_150433_aE) && crop.getWorldObj().field_73012_v.nextInt(300) == 0) {
            crop.setCrop(IC2Crops.cropTerraWart);
        }
    }

    @Override
    public int getRootsLength(ICropTile crop) {
        return 5;
    }
}

