/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownEgg
extends ThrowableItemProjectile {
    public ThrownEgg(EntityType<? extends ThrownEgg> p_37473_, Level p_37474_) {
        super((EntityType<? extends ThrowableItemProjectile>)p_37473_, p_37474_);
    }

    public ThrownEgg(Level p_37481_, LivingEntity p_37482_) {
        super((EntityType<? extends ThrowableItemProjectile>)EntityType.f_20483_, p_37482_, p_37481_);
    }

    public ThrownEgg(Level p_37476_, double p_37477_, double p_37478_, double p_37479_) {
        super((EntityType<? extends ThrowableItemProjectile>)EntityType.f_20483_, p_37477_, p_37478_, p_37479_, p_37476_);
    }

    @Override
    public void m_7822_(byte p_37484_) {
        if (p_37484_ == 3) {
            double $$1 = 0.08;
            for (int $$2 = 0; $$2 < 8; ++$$2) {
                this.f_19853_.m_7106_(new ItemParticleOption(ParticleTypes.f_123752_, this.m_7846_()), this.m_20185_(), this.m_20186_(), this.m_20189_(), ((double)this.f_19796_.m_188501_() - 0.5) * 0.08, ((double)this.f_19796_.m_188501_() - 0.5) * 0.08, ((double)this.f_19796_.m_188501_() - 0.5) * 0.08);
            }
        }
    }

    @Override
    protected void m_5790_(EntityHitResult p_37486_) {
        super.m_5790_(p_37486_);
        p_37486_.m_82443_().m_6469_(DamageSource.m_19361_(this, this.m_37282_()), 0.0f);
    }

    @Override
    protected void m_6532_(HitResult p_37488_) {
        super.m_6532_(p_37488_);
        if (!this.f_19853_.f_46443_) {
            if (this.f_19796_.m_188503_(8) == 0) {
                int $$1 = 1;
                if (this.f_19796_.m_188503_(32) == 0) {
                    $$1 = 4;
                }
                for (int $$2 = 0; $$2 < $$1; ++$$2) {
                    Chicken $$3 = EntityType.f_20555_.m_20615_(this.f_19853_);
                    $$3.m_146762_(-24000);
                    $$3.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), 0.0f);
                    this.f_19853_.m_7967_($$3);
                }
            }
            this.f_19853_.m_7605_(this, (byte)3);
            this.m_146870_();
        }
    }

    @Override
    protected Item m_7881_() {
        return Items.f_42521_;
    }
}

