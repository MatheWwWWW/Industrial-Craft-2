/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.crops.crops;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.core.block.crops.crops.BaseCrop;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class LumiliaCrop
extends BaseCrop {
    public LumiliaCrop() {
        super("lumilia", new CropProperties(6, 3, 0, 0, 1, 0), "Yellow", "Leaves", "Flower", "Light");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(int stage) {
        return IC2Textures.getMappedEntriesBlockIC2("crops/lumilia").get("growing_" + stage);
    }

    @Override
    public int getEmittedLight(ICropTile cropTile) {
        return cropTile.getGrowthStage() == this.getGrowthSteps() ? 15 : 0;
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
        return cropTile.getGrowthStage() < 3 || cropTile.getGrowthStage() == 3 && cropTile.getWorldObj().m_46940_() >= 0.15f;
    }

    @Override
    public int getStatInfluence(ICropTile cropTile, int humidity, int nutrients, int air) {
        return (int)(0.4f * (float)humidity + 1.4f * (float)nutrients + 1.2f * (float)air);
    }

    @Override
    public int getGrowthDuration(ICropTile cropTile) {
        if (cropTile.getGrowthStage() == 3) {
            return 50;
        }
        return 800;
    }

    @Override
    public Component discoveredBy() {
        return BaseCrop.DAENARA;
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)Blocks.f_50050_);
    }

    @Override
    public double getDropChance(ICropTile cropTile) {
        return super.getDropChance(cropTile) * 2.0;
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        return new ItemStack[]{new ItemStack((ItemLike)ForgeRegistries.ITEMS.tags().getTag(ItemTags.f_13143_).getRandomElement(cropTile.getWorldObj().m_213780_()).orElse(Items.f_41852_))};
    }
}

