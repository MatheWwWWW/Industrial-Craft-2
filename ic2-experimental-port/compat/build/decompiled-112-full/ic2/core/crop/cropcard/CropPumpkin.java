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

public class CropPumpkin
extends CropVanillaStem {
    public CropPumpkin() {
        super(4);
    }

    @Override
    public String getId() {
        return "pumpkin";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(1, 0, 1, 0, 3, 1);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Orange", "Decoration", "Stem"};
    }

    @Override
    protected ItemStack getProduct() {
        return new ItemStack(Blocks.field_150423_aK);
    }

    @Override
    protected ItemStack getSeeds() {
        return new ItemStack(Items.field_151080_bb, IC2.random.nextInt(3) + 1);
    }

    @Override
    public int getGrowthDuration(ICropTile crop) {
        if (crop.getCurrentSize() == 3) {
            return 600;
        }
        return 200;
    }
}

