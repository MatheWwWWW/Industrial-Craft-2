/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.crops.crops;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.core.block.crops.crops.BaseCrop;
import ic2.core.platform.rendering.IC2Textures;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Set;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class OreCrop
extends BaseCrop {
    private String name;
    private Component discovered;
    private Set<Block> validBlocks;
    private Item drop;

    public OreCrop(String name, CropProperties props, Block[] blocks, Item drop, Component discovered, String attribute) {
        super(name, props, attribute, "Leaves", "Ores");
        this.name = name;
        this.validBlocks = new ObjectOpenHashSet((Object[])blocks);
        this.drop = drop;
        this.discovered = discovered;
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
        return IC2Textures.getMappedEntriesBlockIC2("crops/ores").get(stageTexture);
    }

    @Override
    public int getGrowthSteps() {
        return 4;
    }

    @Override
    public int getAfterHarvestStage(ICropTile cropTile) {
        return 2;
    }

    @Override
    public boolean canGrow(ICropTile cropTile) {
        if (cropTile.getGrowthStage() < 3) {
            return true;
        }
        if (cropTile.getGrowthStage() == 3) {
            for (Block block : cropTile.getBlocksBelow()) {
                if (!this.validBlocks.contains(block)) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public int getGrowthDuration(ICropTile cropTile) {
        if (cropTile.getGrowthStage() == 3) {
            if (this.name.equals("argentum") || this.name.equals("aurelia") || this.name.equals("adamantum") || this.name.equals("alumen")) {
                return 2200;
            }
            return 2000;
        }
        if (this.name.equals("argentum") || this.name.equals("aurelia") || this.name.equals("adamantum") || this.name.equals("alumen")) {
            return 1000;
        }
        return 800;
    }

    @Override
    public Component discoveredBy() {
        return this.discovered;
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)this.drop);
    }

    @Override
    public double getDropChance(ICropTile cropTile) {
        if (this.name.equals("adamantum")) {
            return super.getDropChance(cropTile) / 4.0;
        }
        if (this.name.equals("alumen")) {
            return super.getDropChance(cropTile) / 3.0;
        }
        return super.getDropChance(cropTile) / 2.0;
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        return new ItemStack[]{new ItemStack((ItemLike)this.drop)};
    }
}

