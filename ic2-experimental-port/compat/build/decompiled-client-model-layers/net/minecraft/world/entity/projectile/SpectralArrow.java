/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SpectralArrow
extends AbstractArrow {
    private int f_37409_ = 200;

    public SpectralArrow(EntityType<? extends SpectralArrow> p_37411_, Level p_37412_) {
        super((EntityType<? extends AbstractArrow>)p_37411_, p_37412_);
    }

    public SpectralArrow(Level p_37419_, LivingEntity p_37420_) {
        super(EntityType.f_20478_, p_37420_, p_37419_);
    }

    public SpectralArrow(Level p_37414_, double p_37415_, double p_37416_, double p_37417_) {
        super(EntityType.f_20478_, p_37415_, p_37416_, p_37417_, p_37414_);
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (this.f_19853_.f_46443_ && !this.f_36703_) {
            this.f_19853_.m_7106_(ParticleTypes.f_123751_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected ItemStack m_7941_() {
        return new ItemStack(Items.f_42737_);
    }

    @Override
    protected void m_7761_(LivingEntity p_37422_) {
        super.m_7761_(p_37422_);
        MobEffectInstance $$1 = new MobEffectInstance(MobEffects.f_19619_, this.f_37409_, 0);
        p_37422_.m_147207_($$1, this.m_150173_());
    }

    @Override
    public void m_7378_(CompoundTag p_37424_) {
        super.m_7378_(p_37424_);
        if (p_37424_.m_128441_("Duration")) {
            this.f_37409_ = p_37424_.m_128451_("Duration");
        }
    }

    @Override
    public void m_7380_(CompoundTag p_37426_) {
        super.m_7380_(p_37426_);
        p_37426_.m_128405_("Duration", this.f_37409_);
    }
}

