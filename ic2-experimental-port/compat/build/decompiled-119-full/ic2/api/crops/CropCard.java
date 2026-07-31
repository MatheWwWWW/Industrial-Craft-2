/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 */
package ic2.api.crops;

import ic2.api.crops.CropProperties;
import ic2.api.crops.Crops;
import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public abstract class CropCard {
    protected final ICropType cropType;

    public CropCard(ICropType iCropType) {
        this.cropType = iCropType;
    }

    public String getId() {
        return this.cropType.getName();
    }

    public String getOwner() {
        return this.cropType.getOwner();
    }

    public abstract Block getCropBlock();

    public String getUnlocalizedName() {
        return this.getOwner() + ".crop." + this.getId();
    }

    public String getDiscoveredBy() {
        return "unknown";
    }

    public String desc(int n) {
        String[] stringArray = this.getAttributes();
        if (stringArray == null || stringArray.length == 0) {
            return "";
        }
        if (n == 0) {
            Object object = stringArray[0];
            if (stringArray.length >= 2) {
                object = (String)object + ", " + stringArray[1];
                if (stringArray.length >= 3) {
                    object = (String)object + ",";
                }
            }
            return object;
        }
        if (stringArray.length < 3) {
            return "";
        }
        Object object = stringArray[2];
        if (stringArray.length >= 4) {
            object = (String)object + ", " + stringArray[3];
        }
        return object;
    }

    public int getRootsLength(ICropTile iCropTile) {
        return 1;
    }

    public abstract CropProperties getProperties();

    public String[] getAttributes() {
        return new String[0];
    }

    public String getSeedType() {
        return "ic2.crop.seeds";
    }

    public int getMaxAge() {
        return this.cropType.getMaxAge();
    }

    public int getGrowthDuration(ICropTile iCropTile) {
        return this.getProperties().getTier() * 200;
    }

    public boolean canGrow(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() < this.getMaxAge();
    }

    public int getWeightInfluences(ICropTile iCropTile, int n, int n2, int n3) {
        return n + n2 + n3;
    }

    public boolean canCross(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() >= 2;
    }

    public boolean onRightClick(ICropTile iCropTile, Player player) {
        return iCropTile.performManualHarvest();
    }

    public int getOptimalHarvestAge(ICropTile iCropTile) {
        return this.getMaxAge();
    }

    public boolean canBeHarvested(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() == this.getMaxAge();
    }

    public double dropGainChance() {
        return Math.pow(0.95, this.getProperties().getTier());
    }

    @Deprecated
    public ItemStack getGain(ICropTile iCropTile) {
        return ItemStack.f_41583_;
    }

    public ItemStack[] getGains(ICropTile iCropTile) {
        return new ItemStack[]{this.getGain(iCropTile)};
    }

    public int getAgeAfterHarvest(ICropTile iCropTile) {
        return 0;
    }

    public boolean onLeftClick(ICropTile iCropTile, Player player) {
        return iCropTile.pick();
    }

    public float dropSeedChance(ICropTile iCropTile) {
        if (iCropTile.getCurrentAge() == 0) {
            return 0.0f;
        }
        float f = 0.5f;
        if (iCropTile.getCurrentAge() == 1) {
            f /= 2.0f;
        }
        for (int i = 0; i < this.getProperties().getTier(); ++i) {
            f = (float)((double)f * 0.8);
        }
        return f;
    }

    public ItemStack getSeeds(ICropTile iCropTile) {
        return iCropTile.generateSeeds(iCropTile.getCrop(), iCropTile.getStatGrowth(), iCropTile.getStatGain(), iCropTile.getStatResistance(), iCropTile.getScanLevel());
    }

    public void onNeighbourChange(ICropTile iCropTile) {
    }

    public boolean isRedstoneSignalEmitter(ICropTile iCropTile) {
        return false;
    }

    public int getEmittedRedstoneSignal(ICropTile iCropTile) {
        return 0;
    }

    public void onBlockDestroyed(ICropTile iCropTile) {
    }

    public int getEmittedLight(ICropTile iCropTile) {
        return 0;
    }

    public boolean onEntityCollision(ICropTile iCropTile, Entity entity) {
        return entity instanceof LivingEntity && entity.m_20142_();
    }

    public void tick(ICropTile iCropTile) {
    }

    public boolean isWeed(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() >= 1 && (iCropTile.getCrop() == Crops.weed || iCropTile.getStatGrowth() >= 24);
    }

    public Level getWorld(ICropTile iCropTile) {
        return iCropTile.getWorldObj();
    }

    @Environment(value=EnvType.CLIENT)
    public abstract List<ResourceLocation> getTexturesLocation();
}

