/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.crops.crops;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.core.block.crops.CropRegistry;
import ic2.core.block.crops.crops.BaseCrop;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TerraWartCrop
extends BaseCrop {
    public TerraWartCrop() {
        super("terrawart", new CropProperties(5, 2, 4, 0, 3, 0), "Blue", "Aether", "Food", "Snow", "Wart");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(int stage) {
        return IC2Textures.getMappedEntriesBlockIC2("crops/shroom/terrawart").get("growing_" + stage);
    }

    @Override
    public int getGrowthSteps() {
        return 3;
    }

    @Override
    public double getDropChance(ICropTile cropTile) {
        return 0.8;
    }

    @Override
    public void onTick(ICropTile cropTile) {
        if (cropTile.isBlockBelow(Blocks.f_50127_)) {
            if (this.canGrow(cropTile)) {
                cropTile.setGrowthPoints(cropTile.getGrowthPoints() + cropTile.calculateGrowthSpeed() / 2);
            }
        } else if (cropTile.isBlockBelow(Blocks.f_50135_) && BaseCrop.getRandom(cropTile).m_188503_(300) == 0) {
            cropTile.setCrop(CropRegistry.NETHERWART);
        }
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)IC2Items.TERRA_WART);
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        return new ItemStack[]{new ItemStack((ItemLike)IC2Items.TERRA_WART)};
    }
}

