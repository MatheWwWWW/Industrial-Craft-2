/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SmallFireball
extends Fireball {
    public SmallFireball(EntityType<? extends SmallFireball> p_37364_, Level p_37365_) {
        super((EntityType<? extends Fireball>)p_37364_, p_37365_);
    }

    public SmallFireball(Level p_37375_, LivingEntity p_37376_, double p_37377_, double p_37378_, double p_37379_) {
        super((EntityType<? extends Fireball>)EntityType.f_20527_, p_37376_, p_37377_, p_37378_, p_37379_, p_37375_);
    }

    public SmallFireball(Level p_37367_, double p_37368_, double p_37369_, double p_37370_, double p_37371_, double p_37372_, double p_37373_) {
        super((EntityType<? extends Fireball>)EntityType.f_20527_, p_37368_, p_37369_, p_37370_, p_37371_, p_37372_, p_37373_, p_37367_);
    }

    @Override
    protected void m_5790_(EntityHitResult p_37386_) {
        super.m_5790_(p_37386_);
        if (this.f_19853_.f_46443_) {
            return;
        }
        Entity $$1 = p_37386_.m_82443_();
        Entity $$2 = this.m_37282_();
        int $$3 = $$1.m_20094_();
        $$1.m_20254_(5);
        if (!$$1.m_6469_(DamageSource.m_19349_(this, $$2), 5.0f)) {
            $$1.m_7311_($$3);
        } else if ($$2 instanceof LivingEntity) {
            this.m_19970_((LivingEntity)$$2, $$1);
        }
    }

    @Override
    protected void m_8060_(BlockHitResult p_37384_) {
        BlockPos $$2;
        super.m_8060_(p_37384_);
        if (this.f_19853_.f_46443_) {
            return;
        }
        Entity $$1 = this.m_37282_();
        if ((!($$1 instanceof Mob) || this.f_19853_.m_46469_().m_46207_(GameRules.f_46132_)) && this.f_19853_.m_46859_($$2 = p_37384_.m_82425_().m_121945_(p_37384_.m_82434_()))) {
            this.f_19853_.m_46597_($$2, BaseFireBlock.m_49245_(this.f_19853_, $$2));
        }
    }

    @Override
    protected void m_6532_(HitResult p_37388_) {
        super.m_6532_(p_37388_);
        if (!this.f_19853_.f_46443_) {
            this.m_146870_();
        }
    }

    @Override
    public boolean m_6087_() {
        return false;
    }

    @Override
    public boolean m_6469_(DamageSource p_37381_, float p_37382_) {
        return false;
    }
}

