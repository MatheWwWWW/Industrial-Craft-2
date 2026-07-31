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
import ic2.core.crop.CropVanilla;
import net.minecraft.block.BlockCrops;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CropBeetroot
extends CropVanilla {
    public CropBeetroot() {
        super((BlockCrops)Blocks.field_185773_cZ);
    }

    @Override
    public String getId() {
        return "beetroots";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(1, 0, 4, 0, 1, 2);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Red", "Food", "Beetroot"};
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(Items.field_185164_cV, 1);
    }

    @Override
    public ItemStack getSeeds() {
        return new ItemStack(Items.field_185163_cU);
    }
}

