/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Holder
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.crops.ICropType;
import ic2.api.item.ItemWrapper;
import ic2.core.IC2;
import ic2.core.Ic2DamageSource;
import ic2.core.crop.Ic2CropCard;
import ic2.core.proxy.EnvProxy;
import ic2.core.ref.Ic2Blocks;
import ic2.core.util.BiomeUtil;
import ic2.core.util.StackUtil;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

public class CropEating
extends Ic2CropCard {
    private final double movementMultiplier = 0.5;
    private final double length = 1.0;
    private static final Ic2DamageSource damage = new Ic2DamageSource("cropEating");

    public CropEating(ICropType iCropType) {
        super(iCropType);
    }

    @Override
    public Block getCropBlock() {
        return Ic2Blocks.EATING_PLANT_CROP;
    }

    @Override
    public String getDiscoveredBy() {
        return "Hasudako";
    }

    @Override
    public CropProperties getProperties() {
        return new CropProperties(6, 1, 1, 3, 1, 4);
    }

    @Override
    public String[] getAttributes() {
        return new String[]{"Bad", "Food"};
    }

    @Override
    public boolean canGrow(ICropTile iCropTile) {
        if (iCropTile.getCurrentAge() < 2) {
            return iCropTile.getLightLevel() > 10;
        }
        return iCropTile.isBlockBelow(Blocks.f_49991_) && iCropTile.getCurrentAge() < this.getMaxAge() && iCropTile.getLightLevel() > 10;
    }

    @Override
    public int getOptimalHarvestAge(ICropTile iCropTile) {
        return this.getMaxAge() - 2;
    }

    @Override
    public boolean canBeHarvested(ICropTile iCropTile) {
        return iCropTile.getCurrentAge() >= this.getOptimalHarvestAge(iCropTile) && iCropTile.getCurrentAge() < this.getMaxAge();
    }

    @Override
    public ItemStack getGain(ICropTile iCropTile) {
        if (this.canBeHarvested(iCropTile)) {
            return new ItemStack((ItemLike)Blocks.f_50128_);
        }
        return null;
    }

    @Override
    public void tick(ICropTile iCropTile) {
        List list;
        if (iCropTile.getCurrentAge() == 0) {
            return;
        }
        BlockPos blockPos = iCropTile.getPosition();
        double d = (double)blockPos.m_123341_() + 0.5;
        double d2 = (double)blockPos.m_123342_() + 0.5;
        double d3 = (double)blockPos.m_123343_() + 0.5;
        if (iCropTile.getCustomData().m_128471_("eaten")) {
            StackUtil.dropAsEntity(iCropTile.getWorldObj(), blockPos, new ItemStack((ItemLike)Items.f_42583_));
            iCropTile.getCustomData().m_128379_("eaten", false);
        }
        if ((list = iCropTile.getWorldObj().m_6443_(LivingEntity.class, new AABB(d - 1.0, (double)blockPos.m_123342_(), d3 - 1.0, d + 1.0, (double)blockPos.m_123342_() + 1.0 + 1.0, d3 + 1.0), null)).isEmpty()) {
            return;
        }
        Collections.shuffle(list);
        for (LivingEntity livingEntity : list) {
            if (livingEntity instanceof Player && ((Player)livingEntity).m_150110_().f_35937_) continue;
            livingEntity.m_20334_((d - livingEntity.m_20185_()) * 0.5, Math.min(livingEntity.m_20184_().m_7098_(), -0.05), (d3 - livingEntity.m_20189_()) * 0.5);
            livingEntity.m_6469_((DamageSource)damage, (float)(iCropTile.getCurrentAge() + 1) * 2.0f);
            if (!CropEating.hasMetalAromor(livingEntity)) {
                livingEntity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 64, 50));
                livingEntity.m_7292_(new MobEffectInstance(MobEffects.f_19609_, 64, 0));
                livingEntity.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 64, 0));
            }
            if (this.canGrow(iCropTile)) {
                iCropTile.setGrowthPoints(iCropTile.getGrowthPoints() + 100);
            }
            iCropTile.getWorldObj().m_6263_(null, d, d2, d3, SoundEvents.f_11912_, SoundSource.BLOCKS, 1.0f, IC2.random.m_188501_() * 0.1f + 0.9f);
            iCropTile.getCustomData().m_128379_("eaten", true);
            break;
        }
    }

    @Override
    public int getRootsLength(ICropTile iCropTile) {
        return 5;
    }

    @Override
    public int getGrowthDuration(ICropTile iCropTile) {
        float f = 1.0f;
        BlockPos blockPos = iCropTile.getPosition();
        Holder<Biome> holder = BiomeUtil.getBiome((LevelReader)iCropTile.getWorldObj(), blockPos);
        if (IC2.envProxy.biomeHasType(holder, EnvProxy.BiomeType.SWAMP) || IC2.envProxy.biomeHasType(holder, EnvProxy.BiomeType.MOUNTAIN)) {
            f /= 1.5f;
        }
        return (int)((float)super.getGrowthDuration(iCropTile) * (f /= 1.0f + (float)iCropTile.getTerrainAirQuality() / 10.0f));
    }

    private static boolean hasMetalAromor(LivingEntity livingEntity) {
        if (!(livingEntity instanceof Player)) {
            return false;
        }
        Player player = (Player)livingEntity;
        for (ItemStack itemStack : player.m_150109_().f_35975_) {
            if (itemStack == null || !ItemWrapper.isMetalArmor(itemStack, player)) continue;
            return true;
        }
        return false;
    }
}

