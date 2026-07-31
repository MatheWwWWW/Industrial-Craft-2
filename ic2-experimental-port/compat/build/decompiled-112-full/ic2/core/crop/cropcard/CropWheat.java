/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockCrops
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.core.crop.CropVanilla;
import net.minecraft.block.BlockCrops;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CropWheat
extends CropVanilla {
    public CropWheat() {
        super((BlockCrops)Blocks.field_150464_aj);
    }

    @Override
    public String getId() {
        return "wheat";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(1, 0, 4, 0, 0, 2);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Yellow", "Food", "Wheat"};
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(Items.field_151015_O, 1);
    }

    @Override
    public ItemStack getSeeds() {
        return new ItemStack(Items.field_151014_N);
    }

    @Override
    public int getSizeAfterHarvest(ICropTile crop) {
        return 2;
    }
}

