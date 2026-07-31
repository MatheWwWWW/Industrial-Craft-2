/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import ic2.core.crop.CropBase;
import java.util.Collection;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class CropBaseMetalCommon
extends CropBase {
    protected final String[] cropAttributes;
    protected final Block cropBlock;
    protected final Collection<TagKey<Block>> cropRootsRequirement;

    public CropBaseMetalCommon(ICropType iCropType, Block block, String[] stringArray, Collection<TagKey<Block>> collection, ItemStack itemStack) {
        super(iCropType, itemStack);
        this.cropBlock = block;
        this.cropAttributes = stringArray;
        this.cropRootsRequirement = collection;
    }

    @Override
    public Block getCropBlock() {
        return this.cropBlock;
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(6, 2, 0, 0, 1, 0);
    }

    @Override
    public String[] getAttributes() {
        return this.cropAttributes;
    }

    @Override
    public boolean canGrow(ICropTile iCropTile) {
        int n = this.getMaxAge() - 1;
        if (iCropTile.getCurrentAge() < n) {
            return true;
        }
        if (iCropTile.getCurrentAge() == n) {
            if (this.cropRootsRequirement == null || this.cropRootsRequirement.isEmpty()) {
                return true;
            }
            for (TagKey<Block> tagKey : this.cropRootsRequirement) {
                if (!iCropTile.isBlockBelow(tagKey)) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public int getRootsLength(ICropTile iCropTile) {
        return 5;
    }

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        return this.cropDrop.m_41777_();
    }

    @Override
    public double dropGainChance() {
        return super.dropGainChance() / 2.0;
    }

    @Override
    public int getGrowthDuration(ICropTile iCropTile) {
        if (iCropTile.getCurrentAge() == this.getMaxAge() - 1) {
            return 2000;
        }
        return 800;
    }

    @Override
    public int getAgeAfterHarvest(ICropTile iCropTile) {
        return 1;
    }
}

