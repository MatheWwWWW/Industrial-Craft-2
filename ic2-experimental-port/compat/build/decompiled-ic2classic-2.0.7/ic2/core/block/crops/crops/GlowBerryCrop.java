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
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GlowBerryCrop
extends BaseCrop {
    public GlowBerryCrop() {
        super("glow_berry", new CropProperties(2, 1, 4, 2, 3, 0), "Yellow", "Green", "Yellow", "Food");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(int stage) {
        return IC2Textures.getMappedEntriesBlockIC2("crops/glow_berry").get("growing_" + stage);
    }

    @Override
    public Component discoveredBy() {
        return BaseCrop.MOJANG;
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)Items.f_151079_);
    }

    @Override
    public int getGrowthSteps() {
        return 5;
    }

    @Override
    public int getGrowthDuration(ICropTile cropTile) {
        if (cropTile.getGrowthStage() >= 2) {
            return 200;
        }
        return super.getGrowthDuration(cropTile);
    }

    @Override
    public int getOptimalHarvestStep(ICropTile cropTile) {
        return this.getGrowthSteps();
    }

    @Override
    public boolean canBeHarvested(ICropTile cropTile) {
        return cropTile.getGrowthStage() >= 4;
    }

    @Override
    public int getAfterHarvestStage(ICropTile cropTile) {
        return 2;
    }

    @Override
    public double getDropChance(ICropTile cropTile) {
        if (cropTile.getGrowthStage() == 4) {
            return super.getDropChance(cropTile) / 2.0;
        }
        return super.getDropChance(cropTile);
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        return new ItemStack[]{new ItemStack((ItemLike)Items.f_151079_)};
    }
}

