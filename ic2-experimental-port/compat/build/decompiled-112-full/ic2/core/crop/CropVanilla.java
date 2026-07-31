/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockCrops
 *  net.minecraft.item.ItemStack
 */
package ic2.core.crop;

import ic2.api.crops.ICropTile;
import ic2.core.crop.IC2CropCard;
import net.minecraft.block.BlockCrops;
import net.minecraft.item.ItemStack;

public abstract class CropVanilla
extends IC2CropCard {
    protected final int maxAge;

    protected CropVanilla(BlockCrops block) {
        this(block.func_185526_g());
    }

    protected CropVanilla(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public String getDiscoveredBy() {
        return "Notch";
    }

    @Override
    public int getMaxSize() {
        return this.maxAge;
    }

    @Override
    public boolean canGrow(ICropTile crop) {
        return crop.getCurrentSize() < this.getMaxSize() && crop.getLightLevel() >= 9;
    }

    protected abstract ItemStack getSeeds();

    protected abstract ItemStack getProduct();

    @Override
    public ItemStack getGain(ICropTile crop) {
        return this.getProduct();
    }

    @Override
    public ItemStack getSeeds(ICropTile crop) {
        if (crop.getStatGain() <= 1 && crop.getStatGrowth() <= 1 && crop.getStatResistance() <= 1) {
            return this.getSeeds();
        }
        return super.getSeeds(crop);
    }
}

