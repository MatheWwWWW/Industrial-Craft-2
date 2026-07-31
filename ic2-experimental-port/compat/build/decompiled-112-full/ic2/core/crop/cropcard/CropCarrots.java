/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.core.crop.CropVanilla;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CropCarrots
extends CropVanilla {
    public CropCarrots() {
        super(3);
    }

    @Override
    public String getId() {
        return "carrots";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(2, 0, 4, 0, 0, 2);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Orange", "Food", "Carrots"};
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(Items.field_151172_bF);
    }

    @Override
    public ItemStack getSeeds() {
        return new ItemStack(Items.field_151172_bF);
    }
}

