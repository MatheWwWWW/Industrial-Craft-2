/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class CaveSpider
extends Spider {
    public CaveSpider(EntityType<? extends CaveSpider> p_32254_, Level p_32255_) {
        super((EntityType<? extends Spider>)p_32254_, p_32255_);
    }

    public static AttributeSupplier.Builder m_32267_() {
        return Spider.m_33815_().m_22268_(Attributes.f_22276_, 12.0);
    }

    @Override
    public boolean m_7327_(Entity p_32257_) {
        if (super.m_7327_(p_32257_)) {
            if (p_32257_ instanceof LivingEntity) {
                int $$1 = 0;
                if (this.f_19853_.m_46791_() == Difficulty.NORMAL) {
                    $$1 = 7;
                } else if (this.f_19853_.m_46791_() == Difficulty.HARD) {
                    $$1 = 15;
                }
                if ($$1 > 0) {
                    ((LivingEntity)p_32257_).m_147207_(new MobEffectInstance(MobEffects.f_19614_, $$1 * 20, 0), this);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_32259_, DifficultyInstance p_32260_, MobSpawnType p_32261_, @Nullable SpawnGroupData p_32262_, @Nullable CompoundTag p_32263_) {
        return p_32262_;
    }

    @Override
    protected float m_6431_(Pose p_32265_, EntityDimensions p_32266_) {
        return 0.45f;
    }
}

