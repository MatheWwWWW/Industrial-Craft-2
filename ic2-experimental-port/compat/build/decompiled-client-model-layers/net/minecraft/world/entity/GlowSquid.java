/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class GlowSquid
extends Squid {
    private static final EntityDataAccessor<Integer> f_147108_ = SynchedEntityData.m_135353_(GlowSquid.class, EntityDataSerializers.f_135028_);

    public GlowSquid(EntityType<? extends GlowSquid> p_147111_, Level p_147112_) {
        super((EntityType<? extends Squid>)p_147111_, p_147112_);
    }

    @Override
    protected ParticleOptions m_142033_() {
        return ParticleTypes.f_175826_;
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_147108_, 0);
    }

    @Override
    protected SoundEvent m_142555_() {
        return SoundEvents.f_144162_;
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_144159_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_147124_) {
        return SoundEvents.f_144161_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_144160_;
    }

    @Override
    public void m_7380_(CompoundTag p_147122_) {
        super.m_7380_(p_147122_);
        p_147122_.m_128405_("DarkTicksRemaining", this.m_147128_());
    }

    @Override
    public void m_7378_(CompoundTag p_147117_) {
        super.m_7378_(p_147117_);
        this.m_147119_(p_147117_.m_128451_("DarkTicksRemaining"));
    }

    @Override
    public void m_8107_() {
        super.m_8107_();
        int $$0 = this.m_147128_();
        if ($$0 > 0) {
            this.m_147119_($$0 - 1);
        }
        this.f_19853_.m_7106_(ParticleTypes.f_175827_, this.m_20208_(0.6), this.m_20187_(), this.m_20262_(0.6), 0.0, 0.0, 0.0);
    }

    @Override
    public boolean m_6469_(DamageSource p_147114_, float p_147115_) {
        boolean $$2 = super.m_6469_(p_147114_, p_147115_);
        if ($$2) {
            this.m_147119_(100);
        }
        return $$2;
    }

    private void m_147119_(int p_147120_) {
        this.f_19804_.m_135381_(f_147108_, p_147120_);
    }

    public int m_147128_() {
        return this.f_19804_.m_135370_(f_147108_);
    }

    public static boolean m_217017_(EntityType<? extends LivingEntity> p_217018_, ServerLevelAccessor p_217019_, MobSpawnType p_217020_, BlockPos p_217021_, RandomSource p_217022_) {
        return p_217021_.m_123342_() <= p_217019_.m_5736_() - 33 && p_217019_.m_45524_(p_217021_, 0) == 0 && p_217019_.m_8055_(p_217021_).m_60713_(Blocks.f_49990_);
    }
}

