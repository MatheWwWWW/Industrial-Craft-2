/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.crops.crops;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.core.block.crops.crops.BaseCrop;
import ic2.core.block.crops.crops.BaseSeedCrop;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SaplingCrop
extends BaseSeedCrop {
    private String name;
    private ItemLike drop;
    private ItemLike seed;

    public SaplingCrop(String name, ItemLike drop, ItemLike seed) {
        super("sapling_" + name, new CropProperties(3, 1, 0, 4, 4, 0), "Leaves", "Sapling", "Green");
        this.name = name;
        this.drop = drop;
        this.seed = seed;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(int stage) {
        Object stageTexture = "";
        switch (stage) {
            case 0: 
            case 1: 
            case 2: {
                stageTexture = "growing_" + stage;
                break;
            }
            case 3: 
            case 4: {
                stageTexture = "growing_" + this.name + "_" + stage;
            }
        }
        return IC2Textures.getMappedEntriesBlockIC2("crops/sapling").get(stageTexture);
    }

    @Override
    public int getGrowthSteps() {
        return 5;
    }

    @Override
    public int getAfterHarvestStage(ICropTile cropTile) {
        return 4;
    }

    @Override
    public boolean canGrow(ICropTile cropTile) {
        return super.canGrow(cropTile) && this.secondaryGrowthCondition(cropTile);
    }

    private boolean secondaryGrowthCondition(ICropTile cropTile) {
        return cropTile.getLightLevel() >= 9;
    }

    @Override
    public Component discoveredBy() {
        return BaseCrop.NOTCH;
    }

    @Override
    public int getGrowthDuration(ICropTile cropTile) {
        if (cropTile.getGrowthStage() == 4) {
            return 150;
        }
        return super.getGrowthDuration(cropTile);
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        return new ItemStack[]{new ItemStack(this.drop)};
    }

    @Override
    public ItemStack[] getSeedDrops(ICropTile tile) {
        return new ItemStack[]{new ItemStack(this.seed)};
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack(this.drop);
    }

    @Override
    public boolean hasCustomCropPlaceFailedMessage(ICropTile cropTile) {
        return true;
    }

    @Override
    public Component getCustomCropPlaceFailedMessage(ICropTile cropTile) {
        if (!this.secondaryGrowthCondition(cropTile)) {
            return this.translate("info.crop.ic2.plant.placement_error.light", this.translate("info.crop.ic2.plant.placement_error.more"));
        }
        return super.getCustomCropPlaceFailedMessage(cropTile);
    }
}

