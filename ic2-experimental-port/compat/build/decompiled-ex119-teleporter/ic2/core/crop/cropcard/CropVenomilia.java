/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import ic2.core.IC2;
import ic2.core.crop.Ic2CropCard;
import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2Items;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class CropVenomilia
extends Ic2CropCard {
    public CropVenomilia(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public Block getCropBlock() {
        return Ic2Blocks.VENOMILIA_CROP;
    }

    @Override
    public String getDiscoveredBy() {
        return "raGan";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(3, 3, 1, 3, 3, 3);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Purple", "Flower", "Tulip", "Poison"};
    }

    @Override
    public boolean canGrow(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() <= 3 && iCropTile.getLightLevel() >= 12 || iCropTile.getCurrentAge() == 4;
    }

    @Override
    public boolean canBeHarvested(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() >= 3;
    }

    @Override
    public int getOptimalHarvestAge(ICropTile iCropTile) {
        return 3;
    }

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        if (iCropTile.getCurrentAge() == 4) {
            return new ItemStack((ItemLike)Ic2Items.GRIN_POWDER);
        }
        if (iCropTile.getCurrentAge() >= 3) {
            return new ItemStack((ItemLike)Items.f_42493_);
        }
        return null;
    }

    @Override
    public int getAgeAfterHarvest(ICropTile iCropTile) {
        return 2;
    }

    @Override
    public int getGrowthDuration(ICropTile iCropTile) {
        if (iCropTile.getCurrentAge() >= 2) {
            return 600;
        }
        return 400;
    }

    @Override
    public boolean onRightClick(ICropTile iCropTile, Player player) {
        if (!player.m_6144_()) {
            this.onEntityCollision(iCropTile, (Entity)player);
        }
        return iCropTile.performManualHarvest();
    }

    @Override
    public boolean onLeftClick(ICropTile iCropTile, Player player) {
        if (!player.m_6144_()) {
            this.onEntityCollision(iCropTile, (Entity)player);
        }
        return iCropTile.pick();
    }

    @Override
    public boolean onEntityCollision(ICropTile iCropTile, Entity entity) {
        if (iCropTile.getCurrentAge() == 4 && entity instanceof LivingEntity) {
            if (entity instanceof Player && entity.m_6144_() && IC2.random.m_188503_(50) != 0) {
                return super.onEntityCollision(iCropTile, entity);
            }
            ((LivingEntity)entity).m_7292_(new MobEffectInstance(MobEffects.f_19614_, (IC2.random.m_188503_(10) + 5) * 20, 0));
            iCropTile.setCurrentAge(3);
            iCropTile.updateState();
        }
        return super.onEntityCollision(iCropTile, entity);
    }

    @Override
    public boolean isWeed(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() == 4 && iCropTile.getStatGrowth() >= 8;
    }
}

