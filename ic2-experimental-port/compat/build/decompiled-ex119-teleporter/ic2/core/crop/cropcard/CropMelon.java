/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import ic2.core.IC2;
import ic2.core.crop.CropVanillaStem;
import ic2.core.ref.Ic2Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class CropMelon
extends CropVanillaStem {
    public CropMelon(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public Block getCropBlock() {
        return Ic2Blocks.MELON_CROP;
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
        if (IC2.random.m_188503_(3) == 0) {
            return new ItemStack((ItemLike)Blocks.f_50186_);
        }
        return new ItemStack((ItemLike)Items.f_42028_, IC2.random.m_188503_(4) + 2);
    }

    @Override
    protected ItemStack getSeeds() {
        return new ItemStack((ItemLike)Items.f_42578_, IC2.random.m_188503_(2) + 1);
    }

    @Override
    public int getGrowthDuration(ICropTile iCropTile) {
        if (iCropTile.getCurrentAge() == this.getMaxAge() - 1) {
            return 700;
        }
        return 250;
    }
}

