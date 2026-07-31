/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.Item
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FungusCrop
extends BaseCrop {
    boolean isCrimson;
    Item drop;

    public FungusCrop(boolean isCrimson) {
        super((isCrimson ? "crimson" : "warped") + "_fungus", new CropProperties(4, 2, 4, 2, 2, 4), isCrimson ? "Red" : "Cyan", "Food", "Mushroom");
        this.isCrimson = isCrimson;
        this.drop = isCrimson ? Items.f_41954_ : Items.f_41955_;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(int stage) {
        String color = this.isCrimson ? "crimson" : "warped";
        return IC2Textures.getMappedEntriesBlockIC2("crops/shroom/" + color + "_fungus").get("growing_" + stage);
    }

    @Override
    public Component discoveredBy() {
        return BaseCrop.MOJANG;
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)this.drop);
    }

    @Override
    public int getGrowthSteps() {
        return 3;
    }

    @Override
    public boolean canGrow(ICropTile cropTile) {
        return super.canGrow(cropTile) && this.secondaryGrowthCondition(cropTile);
    }

    private boolean secondaryGrowthCondition(ICropTile cropTile) {
        return cropTile.getHumidity() <= 0;
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        return new ItemStack[]{new ItemStack((ItemLike)this.drop)};
    }

    @Override
    public boolean hasCustomCropPlaceFailedMessage(ICropTile cropTile) {
        return true;
    }

    @Override
    public Component getCustomCropPlaceFailedMessage(ICropTile cropTile) {
        if (!this.secondaryGrowthCondition(cropTile)) {
            return this.translate("info.crop.ic2.plant.placement_error.humidity", this.translate("info.crop.ic2.plant.placement_error.less"));
        }
        return super.getCustomCropPlaceFailedMessage(cropTile);
    }
}

