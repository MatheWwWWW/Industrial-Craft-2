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
import ic2.core.block.crops.crops.BaseSeedCrop;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MelonCrop
extends BaseSeedCrop {
    public MelonCrop() {
        super("melon", new CropProperties(2, 0, 4, 0, 2, 0), "Green", "Food", "Stem", "Red");
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
                stageTexture = "growing_melon_" + stage;
            }
        }
        return IC2Textures.getMappedEntriesBlockIC2("crops/stem").get(stageTexture);
    }

    @Override
    public Component discoveredBy() {
        return this.string("Chao");
    }

    @Override
    public int getGrowthSteps() {
        return 4;
    }

    @Override
    public int getGrowthDuration(ICropTile cropTile) {
        if (cropTile.getGrowthStage() == 3) {
            return 700;
        }
        return 250;
    }

    @Override
    public int getStatInfluence(ICropTile cropTile, int humidity, int nutrients, int air) {
        return (int)((float)humidity * 1.1f + (float)nutrients * 0.9f + (float)air);
    }

    @Override
    public int getAfterHarvestStage(ICropTile cropTile) {
        return 3;
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        if (BaseCrop.getRandom(cropTile).m_188503_(3) == 0) {
            return new ItemStack[]{new ItemStack((ItemLike)Items.f_42028_)};
        }
        return new ItemStack[]{new ItemStack((ItemLike)Items.f_42575_, BaseCrop.getRandom(cropTile).m_188503_(4) + 2)};
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)Items.f_42575_);
    }

    @Override
    public ItemStack[] getSeedDrops(ICropTile tile) {
        return new ItemStack[]{new ItemStack((ItemLike)Items.f_42578_)};
    }
}

