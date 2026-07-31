/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
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
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.IC2Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VenomiliaCrop
extends BaseCrop {
    public VenomiliaCrop() {
        super("venomilia", new CropProperties(3, 3, 1, 3, 3, 3), "Purple", "Flower", "Tulip", "Poison");
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
            case 3: 
            case 4: 
            case 5: {
                stageTexture = "growing_venomilia_3";
            }
        }
        return IC2Textures.getMappedEntriesBlockIC2("crops/flowers").get(stageTexture);
    }

    @Override
    public int getGrowthSteps() {
        return 6;
    }

    @Override
    public int getOptimalHarvestStep(ICropTile cropTile) {
        return 5;
    }

    @Override
    public int getGrowthDuration(ICropTile cropTile) {
        if (cropTile.getGrowthStage() >= 3) {
            return 600;
        }
        return 400;
    }

    @Override
    public boolean canGrow(ICropTile cropTile) {
        return cropTile.getGrowthStage() < 5 && this.secondaryGrowthCondition(cropTile) || cropTile.getGrowthStage() == 5;
    }

    private boolean secondaryGrowthCondition(ICropTile cropTile) {
        return cropTile.getLightLevel() >= 12;
    }

    @Override
    public boolean canBeHarvested(ICropTile cropTile) {
        return cropTile.getGrowthStage() >= 4;
    }

    @Override
    public int getAfterHarvestStage(ICropTile cropTile) {
        return 3;
    }

    @Override
    public boolean isWeed(ICropTile cropTile) {
        return cropTile.getGrowthStage() == 5 && cropTile.getGrowthStat() >= 8;
    }

    @Override
    public boolean onRightClick(ICropTile cropTile, Player player, InteractionHand hand) {
        if (!player.m_6144_()) {
            this.onEntityCollision(cropTile, (Entity)player);
        }
        return cropTile.performManualHarvest();
    }

    @Override
    public boolean onLeftClick(ICropTile cropTile, Player player) {
        if (!player.m_6144_()) {
            this.onEntityCollision(cropTile, (Entity)player);
        }
        return cropTile.pickCrop();
    }

    @Override
    public void onEntityCollision(ICropTile cropTile, Entity entity) {
        if (cropTile.getGrowthStage() == 5 && entity instanceof LivingEntity) {
            RandomSource random = BaseCrop.getRandom(cropTile);
            if (entity instanceof Player && entity.m_6144_() && random.m_188503_(50) != 0) {
                super.onEntityCollision(cropTile, entity);
                return;
            }
            ((LivingEntity)entity).m_7292_(new MobEffectInstance(MobEffects.f_19614_, (random.m_188503_(10) + 5) * 20));
            cropTile.setGrowthStage(4);
            cropTile.requestStateUpdate();
        }
        super.onEntityCollision(cropTile, entity);
    }

    @Override
    public ItemStack getDisplayItem() {
        return new ItemStack((ItemLike)IC2Items.GRIN_POWDER);
    }

    @Override
    public Component discoveredBy() {
        return Component.m_237113_((String)"raGan");
    }

    @Override
    public ItemStack[] getDrops(ICropTile cropTile) {
        if (cropTile.getGrowthStage() == 5) {
            return new ItemStack[]{new ItemStack((ItemLike)IC2Items.GRIN_POWDER)};
        }
        if (cropTile.getGrowthStage() >= 4) {
            return new ItemStack[]{new ItemStack((ItemLike)Items.f_42493_)};
        }
        return new ItemStack[0];
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

