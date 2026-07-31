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
import ic2.api.crops.ICropTile;
import ic2.core.block.crops.crops.BaseSeedCrop;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WheatCrop
extends BaseSeedCrop {
    public WheatCrop() {
        super("wheat", new CropProperties(1, 0, 4, 0, 0, 2), "Yellow", "Food", "Wheat");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(int stage) {
        return IC2Textures.getMappedEntriesBlockIC2("crops/wheat").get("growing_" + (stage < 4 ? "" : "wheat_") + stage);
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)Items.f_42405_);
    }

    @Override
    public Component discoveredBy() {
        return NOTCH;
    }

    @Override
    public int getGrowthSteps() {
        return 7;
    }

    @Override
    public boolean canGrow(ICropTile cropTile) {
        return super.canGrow(cropTile) && this.secondaryGrowthCondition(cropTile);
    }

    private boolean secondaryGrowthCondition(ICropTile cropTile) {
        return cropTile.getLightLevel() >= 9;
    }

    @Override
    public int getAfterHarvestStage(ICropTile cropTile) {
        return 2;
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        return new ItemStack[]{new ItemStack((ItemLike)Items.f_42405_)};
    }

    @Override
    public ItemStack[] getSeedDrops(ICropTile tile) {
        return new ItemStack[]{new ItemStack((ItemLike)Items.f_42404_)};
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

