/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import ic2.core.crop.Ic2CropCard;
import ic2.core.ref.Ic2Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class CropFlax
extends Ic2CropCard {
    public CropFlax(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public Block getCropBlock() {
        return Ic2Blocks.FLAX_CROP;
    }

    @Override
    public String getDiscoveredBy() {
        return "Eloraam";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(2, 1, 1, 2, 0, 1);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Silk", "Vine", "Addictive"};
    }

    @Override
    public boolean canGrow(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() < this.getMaxAge() && iCropTile.getLightLevel() >= 9;
    }

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        return new ItemStack((ItemLike)Items.f_42401_);
    }

    @Override
    public int getAgeAfterHarvest(ICropTile iCropTile) {
        return 0;
    }
}

