/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.crops.crops;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.util.DirectionList;
import ic2.core.block.crops.crops.BaseCrop;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GlowshroomCrop
extends BaseCrop {
    public GlowshroomCrop() {
        super("glowshroom", new CropProperties(5, 2, 4, 0, 3, 0), "Yellow", "Nether", "Light", "Soulsand");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(int stage) {
        return IC2Textures.getMappedEntriesBlockIC2("crops/shroom/glowshroom").get("growing_" + stage);
    }

    @Override
    public boolean canGrow(ICropTile cropTile) {
        return cropTile.getGrowthStage() < 1 || cropTile.getGrowthStage() < 3 && this.hasGlowStone(cropTile);
    }

    @Override
    public Component discoveredBy() {
        return SPEIGER;
    }

    @Override
    public int getGrowthSteps() {
        return 3;
    }

    @Override
    public int getAfterHarvestStage(ICropTile cropTile) {
        return 2;
    }

    @Override
    public double getDropChance(ICropTile cropTile) {
        return 0.8;
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)Items.f_42525_);
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        return new ItemStack[]{new ItemStack((ItemLike)Items.f_42525_)};
    }

    private boolean hasGlowStone(ICropTile crop) {
        Level world = crop.getWorldObj();
        for (Direction facing : DirectionList.DOWN.invert()) {
            BlockPos pos = crop.getPosition().m_121945_(facing);
            if (!world.m_46749_(pos) || world.m_8055_(pos).m_60734_() != Blocks.f_50141_) continue;
            return true;
        }
        return false;
    }
}

