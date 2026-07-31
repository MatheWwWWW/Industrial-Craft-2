/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.crops.crops;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICrop;
import ic2.api.crops.ICropTile;
import ic2.core.block.crops.crops.BaseCrop;
import ic2.core.block.crops.crops.BaseSeedCrop;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlueWheatCrop
extends BaseSeedCrop {
    public BlueWheatCrop() {
        super("blue_wheat", new CropProperties(6, 3, 0, 0, 2, 0), "Blue", "Lapis", "Wheat", "Ores");
    }

    @Override
    public Component discoveredBy() {
        return BaseCrop.DAENARA;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(int stage) {
        Object stageTexture = "";
        switch (stage) {
            case 0: 
            case 1: 
            case 2: 
            case 3: {
                stageTexture = "growing_" + stage;
                break;
            }
            case 4: 
            case 5: {
                stageTexture = "growing_wheat_" + stage;
                break;
            }
            case 6: {
                stageTexture = "growing_bluewheat_" + stage;
            }
        }
        return IC2Textures.getMappedEntriesBlockIC2("crops/wheat").get(stageTexture);
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)Items.f_42534_);
    }

    @Override
    public int getGrowthSteps() {
        return 7;
    }

    @Override
    public int getGrowthDuration(ICropTile cropTile) {
        return 600;
    }

    @Override
    public int getStatInfluence(ICropTile cropTile, int humidity, int nutrients, int air) {
        return (int)((double)humidity * 1.8 + (double)nutrients + (double)air * 0.8);
    }

    @Override
    public boolean canGrow(ICropTile cropTile) {
        return super.canGrow(cropTile) && this.secondaryGrowthCondition(cropTile);
    }

    private boolean secondaryGrowthCondition(ICropTile cropTile) {
        return cropTile.getHumidity() >= 8;
    }

    @Override
    public int getAfterHarvestStage(ICropTile cropTile) {
        return 2;
    }

    @Override
    public double getDropChance(ICropTile cropTile) {
        return 0.5;
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        return new ItemStack[]{new ItemStack((ItemLike)Items.f_42534_)};
    }

    @Override
    public ItemStack[] getSeedDrops(ICropTile tile) {
        return new ItemStack[]{new ItemStack((ItemLike)Items.f_42404_)};
    }

    @Override
    public ICrop.CropType getCropType() {
        return ICrop.CropType.BOTH;
    }

    @Override
    public boolean hasCustomCropPlaceFailedMessage(ICropTile cropTile) {
        return true;
    }

    @Override
    public Component getCustomCropPlaceFailedMessage(ICropTile cropTile) {
        if (!this.secondaryGrowthCondition(cropTile)) {
            return this.translate("info.crop.ic2.plant.placement_error.humidity", this.translate("info.crop.ic2.plant.placement_error.more"));
        }
        return super.getCustomCropPlaceFailedMessage(cropTile);
    }
}

