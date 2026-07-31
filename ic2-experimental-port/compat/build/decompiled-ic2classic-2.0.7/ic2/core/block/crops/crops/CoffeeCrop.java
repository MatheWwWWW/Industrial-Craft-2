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
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CoffeeCrop
extends BaseCrop {
    public CoffeeCrop() {
        super("coffee", new CropProperties(7, 1, 4, 1, 2, 0), "Leaves", "Ingredient", "Beans", "Brown");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(int stage) {
        return IC2Textures.getMappedEntriesBlockIC2("crops/coffee").get("growing_" + stage);
    }

    @Override
    public int getGrowthSteps() {
        return 5;
    }

    @Override
    public int getAfterHarvestStage(ICropTile cropTile) {
        return 3;
    }

    @Override
    public boolean canGrow(ICropTile cropTile) {
        return super.canGrow(cropTile) && this.secondaryGrowthCondition(cropTile);
    }

    private boolean secondaryGrowthCondition(ICropTile cropTile) {
        return cropTile.getLightLevel() >= 9;
    }

    @Override
    public int getStatInfluence(ICropTile cropTile, int humidity, int nutrients, int air) {
        return (int)(0.4f * (float)humidity + 1.4f * (float)nutrients + 1.2f * (float)air);
    }

    @Override
    public int getGrowthDuration(ICropTile cropTile) {
        if (cropTile.getGrowthStage() == 3) {
            return (int)((float)super.getGrowthDuration(cropTile) * 0.5f);
        }
        if (cropTile.getGrowthStage() == 4) {
            return (int)((float)super.getGrowthDuration(cropTile) * 1.5f);
        }
        return super.getGrowthDuration(cropTile);
    }

    @Override
    public Component discoveredBy() {
        return this.string("Snoochy");
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)IC2Items.COFFEE_BEANS);
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        return new ItemStack[]{new ItemStack((ItemLike)IC2Items.COFFEE_BEANS)};
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

