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
import ic2.core.IC2;
import ic2.core.crop.CropVanillaStem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CropMelon
extends CropVanillaStem {
    public CropMelon() {
        super(4);
    }

    @Override
    public String getId() {
        return "melon";
    }

    @Override
    public String getDiscoveredBy() {
        return "Chao";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(2, 0, 4, 0, 2, 0);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Green", "Food", "Stem"};
    }

    @Override
    protected ItemStack getProduct() {
        if (IC2.random.nextInt(3) == 0) {
            return new ItemStack(Blocks.field_150440_ba);
        }
        return new ItemStack(Items.field_151127_ba, IC2.random.nextInt(4) + 2);
    }

    @Override
    protected ItemStack getSeeds() {
        return new ItemStack(Items.field_151081_bc, IC2.random.nextInt(2) + 1);
    }

    @Override
    public int getGrowthDuration(ICropTile crop) {
        if (crop.getCurrentSize() == 3) {
            return 700;
        }
        return 250;
    }
}

