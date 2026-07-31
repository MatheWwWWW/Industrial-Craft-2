/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ObjectArrays
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.DyeItem
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.crops.crops;

import com.google.common.collect.ObjectArrays;
import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.core.block.crops.crops.BaseCrop;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DyeFlowerCrop
extends BaseCrop {
    private String name;
    private DyeColor color;
    private Component discovered;

    public DyeFlowerCrop(String name, DyeColor color, Component discovered, CropProperties properties, String ... attributes) {
        super(name, properties, (String[])ObjectArrays.concat((Object)"Flower", (Object[])attributes));
        this.name = name;
        this.color = color;
        this.discovered = discovered;
    }

    public DyeFlowerCrop(String name, DyeColor color, Component discovered, String ... attributes) {
        this(name, color, discovered, new CropProperties(2, 1, 1, 0, 5, 1), attributes);
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
            case 3: {
                stageTexture = "growing_" + this.name + "_" + stage;
            }
        }
        return IC2Textures.getMappedEntriesBlockIC2("crops/flowers").get(stageTexture);
    }

    @Override
    public int getGrowthSteps() {
        return 4;
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
        return cropTile.getLightLevel() >= 12;
    }

    @Override
    public Component discoveredBy() {
        return this.discovered;
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)DyeItem.m_41082_((DyeColor)this.color));
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        return new ItemStack[]{new ItemStack((ItemLike)DyeItem.m_41082_((DyeColor)this.color))};
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

