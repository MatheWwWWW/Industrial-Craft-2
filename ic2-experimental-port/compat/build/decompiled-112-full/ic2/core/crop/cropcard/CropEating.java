/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.init.MobEffects
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.biome.Biome
 *  net.minecraftforge.common.BiomeDictionary
 *  net.minecraftforge.common.BiomeDictionary$Type
 */
package ic2.core.crop.cropcard;

import ic2.api.crops.CropProperties;
import ic2.api.crops.ICropTile;
import ic2.api.item.ItemWrapper;
import ic2.core.IC2;
import ic2.core.IC2DamageSource;
import ic2.core.crop.IC2CropCard;
import ic2.core.util.BiomeUtil;
import ic2.core.util.StackUtil;
import java.util.Collections;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class CropEating
extends IC2CropCard {
    private final double movementMultiplier = 0.5;
    private final double length = 1.0;
    private static final IC2DamageSource damage = new IC2DamageSource("cropEating");

    @Override
    public String getDiscoveredBy() {
        return "Hasudako";
    }

    @Override
    public String getId() {
        return "eatingplant";
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
    public int getMaxSize() {
        return 6;
    }

    @Override
    public boolean canGrow(ICropTile crop) {
        if (crop.getCurrentSize() < 3) {
            return crop.getLightLevel() > 10;
        }
        return crop.isBlockBelow((Block)Blocks.field_150353_l) && crop.getCurrentSize() < this.getMaxSize() && crop.getLightLevel() > 10;
    }

    @Override
    public int getOptimalHarvestSize(ICropTile crop) {
        return 4;
    }

    @Override
    public boolean canBeHarvested(ICropTile crop) {
        return crop.getCurrentSize() >= 4 && crop.getCurrentSize() < 6;
    }

    @Override
    public ItemStack getGain(ICropTile crop) {
        if (crop.getCurrentSize() >= 4 && crop.getCurrentSize() < 6) {
            return new ItemStack((Block)Blocks.field_150434_aF);
        }
        return null;
    }

    @Override
    public void tick(ICropTile crop) {
        List list;
        if (crop.getCurrentSize() == 1) {
            return;
        }
        BlockPos coords = crop.getPosition();
        double xcentered = (double)coords.func_177958_n() + 0.5;
        double ycentered = (double)coords.func_177956_o() + 0.5;
        double zcentered = (double)coords.func_177952_p() + 0.5;
        if (crop.getCustomData().func_74767_n("eaten")) {
            StackUtil.dropAsEntity(crop.getWorldObj(), coords, new ItemStack(Items.field_151078_bh));
            crop.getCustomData().func_74757_a("eaten", false);
        }
        if ((list = crop.getWorldObj().func_72872_a(EntityLivingBase.class, new AxisAlignedBB(xcentered - 1.0, (double)coords.func_177956_o(), zcentered - 1.0, xcentered + 1.0, (double)coords.func_177956_o() + 1.0 + 1.0, zcentered + 1.0))).isEmpty()) {
            return;
        }
        Collections.shuffle(list);
        for (EntityLivingBase entity : list) {
            if (entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d) continue;
            entity.field_70159_w = (xcentered - entity.field_70165_t) * 0.5;
            entity.field_70179_y = (zcentered - entity.field_70161_v) * 0.5;
            if (entity.field_70181_x > -0.05) {
                entity.field_70181_x = -0.05;
            }
            entity.func_70097_a((DamageSource)damage, (float)crop.getCurrentSize() * 2.0f);
            if (!CropEating.hasMetalAromor(entity)) {
                entity.func_70690_d(new PotionEffect(MobEffects.field_76421_d, 64, 50));
                entity.func_70690_d(new PotionEffect(MobEffects.field_76441_p, 64, 0));
                entity.func_70690_d(new PotionEffect(MobEffects.field_76440_q, 64, 0));
            }
            if (this.canGrow(crop)) {
                crop.setGrowthPoints(crop.getGrowthPoints() + 100);
            }
            crop.getWorldObj().func_184148_a(null, xcentered, ycentered, zcentered, SoundEvents.field_187537_bA, SoundCategory.BLOCKS, 1.0f, IC2.random.nextFloat() * 0.1f + 0.9f);
            crop.getCustomData().func_74757_a("eaten", true);
            break;
        }
    }

    @Override
    public int getRootsLength(ICropTile crop) {
        return 5;
    }

    @Override
    public int getGrowthDuration(ICropTile crop) {
        float multiplier = 1.0f;
        BlockPos coords = crop.getPosition();
        Biome biome = BiomeUtil.getBiome(crop.getWorldObj(), coords);
        if (BiomeDictionary.hasType((Biome)biome, (BiomeDictionary.Type)BiomeDictionary.Type.SWAMP) || BiomeDictionary.hasType((Biome)biome, (BiomeDictionary.Type)BiomeDictionary.Type.MOUNTAIN)) {
            multiplier /= 1.5f;
        }
        return (int)((float)super.getGrowthDuration(crop) * (multiplier /= 1.0f + (float)crop.getTerrainAirQuality() / 10.0f));
    }

    private static boolean hasMetalAromor(EntityLivingBase entity) {
        if (!(entity instanceof EntityPlayer)) {
            return false;
        }
        EntityPlayer player = (EntityPlayer)entity;
        for (ItemStack stack : player.field_71071_by.field_70460_b) {
            if (stack == null || !ItemWrapper.isMetalArmor(stack, player)) continue;
            return true;
        }
        return false;
    }
}

