/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.DyeItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Blocks
 */
package ic2.core.block.crops.crops;

import ic2.api.crops.ICropTile;
import ic2.core.block.crops.crops.BaseCrop;
import ic2.core.block.crops.crops.DyeFlowerCrop;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

public class RainbowFlowerCrop
extends DyeFlowerCrop {
    public RainbowFlowerCrop() {
        super("rainbow_flower", null, BaseCrop.DAENARA, "Rainbow", "Tulip");
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)Blocks.f_50256_);
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        return new ItemStack[]{new ItemStack((ItemLike)DyeItem.m_41082_((DyeColor)DyeColor.m_41053_((int)BaseCrop.getRandom(cropTile).m_188503_(16))))};
    }
}

