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

public class CropPumpkin
extends CropVanillaStem {
    public CropPumpkin(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public Block getCropBlock() {
        return Ic2Blocks.PUMPKIN_CROP;
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
        return new ItemStack((ItemLike)Blocks.f_50133_);
    }

    @Override
    protected ItemStack getSeeds() {
        return new ItemStack((ItemLike)Items.f_42577_, IC2.random.m_188503_(3) + 1);
    }

    @Override
    public int getGrowthDuration(ICropTile iCropTile) {
        if (iCropTile.getCurrentAge() == this.getMaxAge() - 1) {
            return 600;
        }
        return 200;
    }
}

