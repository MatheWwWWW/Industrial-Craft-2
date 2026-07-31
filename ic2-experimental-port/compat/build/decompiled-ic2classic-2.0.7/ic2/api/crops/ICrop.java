/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.api.crops;

import ic2.api.crops.BaseSeed;
import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropRegistry;
import ic2.api.crops.ICropTile;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class ICrop {
    public static final Component UNKNOWN = Component.m_237115_((String)"crop.ic2.unknown").m_130940_(ChatFormatting.RED);
    public static final ItemStack[] EMPTY_DROPS = new ItemStack[0];

    public abstract ResourceLocation id();

    public Component discoveredBy() {
        return UNKNOWN;
    }

    public abstract Component getName();

    public ItemStack getDisplayItem() {
        return ItemStack.f_41583_;
    }

    public abstract CropProperties getProperties();

    public CropType getCropType() {
        return CropType.AIR;
    }

    public boolean isWaterCleaningCrop(ICropTile tile) {
        return false;
    }

    public abstract String[] getAttributes();

    @OnlyIn(value=Dist.CLIENT)
    public abstract List<ResourceLocation> getTextures();

    public abstract int getGrowthSteps();

    public int getOptimalHarvestStep(ICropTile cropTile) {
        return this.getGrowthSteps();
    }

    public int getGrowthDuration(ICropTile cropTile) {
        return this.getProperties().getTier() * 200;
    }

    public int getStatInfluence(ICropTile cropTile, int humidity, int nutrients, int air) {
        return humidity + nutrients + air;
    }

    public boolean canGrow(ICropTile cropTile) {
        return cropTile.getGrowthStage() < this.getGrowthSteps();
    }

    public boolean canBeHarvested(ICropTile cropTile) {
        return cropTile.getGrowthStage() == this.getGrowthSteps();
    }

    public int getAfterHarvestStage(ICropTile cropTile) {
        return 1;
    }

    public double getDropChance(ICropTile cropTile) {
        return Math.pow(0.95, this.getProperties().getTier());
    }

    public abstract ItemStack[] getDrops(ICropTile var1);

    public float getSeedDropChance(ICropTile cropTile) {
        if (cropTile.getGrowthStage() == 1) {
            return 0.0f;
        }
        float base = 0.5f;
        if (cropTile.getGrowthStage() == 2) {
            base /= 2.0f;
        }
        for (int i = 0; i < this.getProperties().getTier(); ++i) {
            base = (float)((double)base * 0.8);
        }
        return base;
    }

    public ItemStack getSeeds(ICropTile cropTile) {
        return cropTile.createSeeds(cropTile.getCrop(), cropTile.getGrowthStat(), cropTile.getGainStat(), cropTile.getResistanceStat(), cropTile.getScanLevel());
    }

    public boolean canBreed(ICropTile cropTile) {
        return cropTile.getGrowthStage() >= 3;
    }

    public boolean isWeed(ICropTile cropTile) {
        return cropTile.getGrowthStage() >= 2 && (cropTile.getCrop() == ICropRegistry.WEED || cropTile.getGrowthStat() >= 24);
    }

    public boolean onRightClick(ICropTile cropTile, Player player, InteractionHand hand) {
        return cropTile.performManualHarvest();
    }

    public boolean onLeftClick(ICropTile cropTile, Player player) {
        return cropTile.pickCrop();
    }

    public void onTick(ICropTile cropTile) {
    }

    public void onBlockUpdate(ICropTile cropTile) {
    }

    public void onEntityCollision(ICropTile cropTile, Entity entity) {
        if (entity instanceof LivingEntity && entity.m_20142_()) {
            cropTile.pickCrop();
        }
    }

    public void onCropPlaceFailed(BaseSeed seed, ICropTile cropTile, Player player) {
        if (seed.crop.canGrow(cropTile)) {
            if (seed.crop.getCropType().isCompatible(cropTile.isWaterLogged())) {
                player.m_5661_((Component)Component.m_237115_((String)"info.crop.ic2.plant.placement_error.not_compatible.water"), false);
            } else {
                player.m_5661_((Component)Component.m_237115_((String)"info.crop.ic2.plant.placement_error.not_compatible.land"), false);
            }
        } else if (this.hasCustomCropPlaceFailedMessage(cropTile)) {
            player.m_5661_((Component)Component.m_237110_((String)"info.crop.ic2.plant.placement_error", (Object[])new Object[]{this.getCustomCropPlaceFailedMessage(cropTile)}), false);
        } else {
            player.m_5661_((Component)Component.m_237115_((String)"info.crop.ic2.plant.placement_error.not_compatible"), false);
        }
    }

    public boolean hasCustomCropPlaceFailedMessage(ICropTile cropTile) {
        return false;
    }

    public abstract Component getCustomCropPlaceFailedMessage(ICropTile var1);

    public boolean canEmitRedstone(ICropTile cropTile) {
        return false;
    }

    public int getRedstoneLevel(ICropTile cropTile) {
        return 0;
    }

    public int getEmittedLight(ICropTile cropTile) {
        return 0;
    }

    public String desc(int i) {
        String[] att = this.getAttributes();
        if (att == null || att.length == 0) {
            return "";
        }
        if (i == 0) {
            Object s = att[0];
            if (att.length >= 2) {
                s = (String)s + ", " + att[1];
                if (att.length >= 3) {
                    s = (String)s + ",";
                }
            }
            return s;
        }
        if (att.length < 3) {
            return "";
        }
        Object s = att[2];
        if (att.length >= 4) {
            s = (String)s + ", " + att[3];
        }
        return s;
    }

    public static int getGrowth(int combo) {
        return combo >> 10 & 0x1F;
    }

    public static int getGain(int combo) {
        return combo >> 5 & 0x1F;
    }

    public static int getResistance(int combo) {
        return combo & 0x1F;
    }

    public static short combineStats(int growth, int gain, int resistance) {
        return (short)((growth & 0x1F) << 10 | (gain & 0x1F) << 5 | resistance & 0x1F);
    }

    public static enum CropType {
        WATER,
        BOTH,
        AIR;


        public boolean isCompatible(boolean waterlogged) {
            return this == BOTH || (waterlogged ? this == WATER : this == AIR);
        }
    }
}

