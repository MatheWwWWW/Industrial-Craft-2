/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class Snowball
extends ThrowableItemProjectile {
    public Snowball(EntityType<? extends Snowball> p_37391_, Level p_37392_) {
        super((EntityType<? extends ThrowableItemProjectile>)p_37391_, p_37392_);
    }

    public Snowball(Level p_37399_, LivingEntity p_37400_) {
        super((EntityType<? extends ThrowableItemProjectile>)EntityType.f_20477_, p_37400_, p_37399_);
    }

    public Snowball(Level p_37394_, double p_37395_, double p_37396_, double p_37397_) {
        super((EntityType<? extends ThrowableItemProjectile>)EntityType.f_20477_, p_37395_, p_37396_, p_37397_, p_37394_);
    }

    @Override
    protected Item m_7881_() {
        return Items.f_42452_;
    }

    private ParticleOptions m_37408_() {
        ItemStack $$0 = this.m_37454_();
        return $$0.m_41619_() ? ParticleTypes.f_123754_ : new ItemParticleOption(ParticleTypes.f_123752_, $$0);
    }

    @Override
    public void m_7822_(byte p_37402_) {
        if (p_37402_ == 3) {
            ParticleOptions $$1 = this.m_37408_();
            for (int $$2 = 0; $$2 < 8; ++$$2) {
                this.f_19853_.m_7106_($$1, this.m_20185_(), this.m_20186_(), this.m_20189_(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void m_5790_(EntityHitResult p_37404_) {
        super.m_5790_(p_37404_);
        Entity $$1 = p_37404_.m_82443_();
        int $$2 = $$1 instanceof Blaze ? 3 : 0;
        $$1.m_6469_(DamageSource.m_19361_(this, this.m_37282_()), $$2);
    }

    @Override
    protected void m_6532_(HitResult p_37406_) {
        super.m_6532_(p_37406_);
        if (!this.f_19853_.f_46443_) {
            this.f_19853_.m_7605_(this, (byte)3);
            this.m_146870_();
        }
    }
}

