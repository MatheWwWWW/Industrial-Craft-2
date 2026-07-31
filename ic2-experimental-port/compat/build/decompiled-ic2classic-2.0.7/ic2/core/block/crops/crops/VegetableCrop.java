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
import ic2.core.block.crops.crops.BaseCrop;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.utils.helpers.SanityHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VegetableCrop
extends BaseCrop {
    private String name;
    private ItemLike drop;

    public VegetableCrop(String name, String color, ItemLike drop) {
        super(name, new CropProperties(2, 0, 4, 0, 0, 2), "Food", SanityHelper.firstLetterUppercase(name), color);
        this.name = name;
        this.drop = drop;
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
        return IC2Textures.getMappedEntriesBlockIC2("crops/vegetable").get(stageTexture);
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack(this.drop);
    }

    @Override
    public Component discoveredBy() {
        return BaseCrop.NOTCH;
    }

    @Override
    public int getGrowthSteps() {
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
    public ItemStack[] getDrops(ICropTile cropTile) {
        if (this.name.equals("potato") && BaseCrop.getRandom(cropTile).m_188503_(50) == 0) {
            return new ItemStack[]{new ItemStack((ItemLike)Items.f_42675_)};
        }
        return new ItemStack[]{new ItemStack(this.drop)};
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

