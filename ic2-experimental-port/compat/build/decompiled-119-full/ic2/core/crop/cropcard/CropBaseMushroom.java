/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import ic2.core.crop.CropBase;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class CropBaseMushroom
extends CropBase {
    protected final String[] cropAttributes;
    protected final Block cropBlock;

    public CropBaseMushroom(ICropType iCropType, Block block, String[] stringArray, ItemStack itemStack) {
        super(iCropType, itemStack);
        this.cropAttributes = stringArray;
        this.cropBlock = block;
    }

    @Override
    public Block getCropBlock() {
        return this.cropBlock;
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(2, 0, 4, 0, 0, 4);
    }

    @Override
    public String[] getAttributes() {
        return this.cropAttributes;
    }

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        return this.cropDrop.m_41777_();
    }

    @Override
    public int getGrowthDuration(ICropTile iCropTile) {
        return 200;
    }
}

