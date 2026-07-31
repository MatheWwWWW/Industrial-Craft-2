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
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class KelpCrop
extends BaseCrop {
    public KelpCrop() {
        super("kelp", new CropProperties(2, 0, 0, 0, 4, 1), "Green", "Food", "Leaves");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(int stage) {
        return IC2Textures.getMappedEntriesBlockIC2("crops/kelp").get("growing_" + stage);
    }

    @Override
    public Component discoveredBy() {
        return BaseCrop.MOJANG;
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)Items.f_41910_);
    }

    @Override
    public int getGrowthSteps() {
        return 4;
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        return new ItemStack[]{new ItemStack((ItemLike)Items.f_41910_)};
    }

    @Override
    public ICrop.CropType getCropType() {
        return ICrop.CropType.WATER;
    }

    @Override
    public boolean isWaterCleaningCrop(ICropTile tile) {
        return true;
    }

    @Override
    public int getGrowthDuration(ICropTile cropTile) {
        return super.getGrowthDuration(cropTile) / 2;
    }
}

