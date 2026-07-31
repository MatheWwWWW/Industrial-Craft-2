/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
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
import ic2.core.platform.corehacks.mixins.server.BiomeMixin;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CactusCrop
extends BaseCrop {
    public CactusCrop() {
        super("cactus", new CropProperties(2, 0, 0, 4, 4, 0), "Green", "Cactus", "Thorns");
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(int stage) {
        return IC2Textures.getMappedEntriesBlockIC2("crops/cactus").get("growing_" + stage);
    }

    @Override
    public Component discoveredBy() {
        return this.string("SpwnX");
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)Items.f_41982_);
    }

    @Override
    public int getGrowthSteps() {
        return 4;
    }

    @Override
    public int getGrowthDuration(ICropTile cropTile) {
        return 300;
    }

    @Override
    public int getStatInfluence(ICropTile cropTile, int humidity, int nutrients, int air) {
        int count = super.getStatInfluence(cropTile, humidity, nutrients, air);
        count = (int)((float)count + 10.0f * this.getBiomeTemperature(cropTile));
        return count;
    }

    private float getBiomeTemperature(ICropTile cropTile) {
        return ((BiomeMixin)cropTile.getWorldObj().m_204166_(cropTile.getPosition()).m_203334_()).getTemp(cropTile.getPosition()) - 0.5f;
    }

    @Override
    public boolean canGrow(ICropTile cropTile) {
        return super.canGrow(cropTile) && this.secondaryGrowthCondition(cropTile);
    }

    private boolean secondaryGrowthCondition(ICropTile cropTile) {
        return cropTile.getEnvironmentQuality() >= 2;
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        int count = 1;
        if (BaseCrop.getRandom(cropTile).m_188503_(3) == 0) {
            count = 2;
        }
        return new ItemStack[]{new ItemStack((ItemLike)Items.f_41982_, count)};
    }

    @Override
    public void onEntityCollision(ICropTile cropTile, Entity entity) {
        if (!(entity instanceof ItemEntity) && !cropTile.getWorldObj().f_46443_) {
            entity.m_6469_(DamageSource.f_19314_, 1.0f);
        }
        super.onEntityCollision(cropTile, entity);
    }

    @Override
    public boolean hasCustomCropPlaceFailedMessage(ICropTile cropTile) {
        return true;
    }

    @Override
    public Component getCustomCropPlaceFailedMessage(ICropTile cropTile) {
        if (!this.secondaryGrowthCondition(cropTile)) {
            return this.translate("info.crop.ic2.plant.placement_error.quality", this.translate("info.crop.ic2.plant.placement_error.better"), this.translate("info.crop.ic2.plant.placement_error.air"));
        }
        return super.getCustomCropPlaceFailedMessage(cropTile);
    }
}

