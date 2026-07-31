/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownEnderpearl
extends ThrowableItemProjectile {
    public ThrownEnderpearl(EntityType<? extends ThrownEnderpearl> p_37491_, Level p_37492_) {
        super((EntityType<? extends ThrowableItemProjectile>)p_37491_, p_37492_);
    }

    public ThrownEnderpearl(Level p_37499_, LivingEntity p_37500_) {
        super((EntityType<? extends ThrowableItemProjectile>)EntityType.f_20484_, p_37500_, p_37499_);
    }

    @Override
    protected Item m_7881_() {
        return Items.f_42584_;
    }

    @Override
    protected void m_5790_(EntityHitResult p_37502_) {
        super.m_5790_(p_37502_);
        p_37502_.m_82443_().m_6469_(DamageSource.m_19361_(this, this.m_37282_()), 0.0f);
    }

    @Override
    protected void m_6532_(HitResult p_37504_) {
        super.m_6532_(p_37504_);
        for (int $$1 = 0; $$1 < 32; ++$$1) {
            this.f_19853_.m_7106_(ParticleTypes.f_123760_, this.m_20185_(), this.m_20186_() + this.f_19796_.m_188500_() * 2.0, this.m_20189_(), this.f_19796_.m_188583_(), 0.0, this.f_19796_.m_188583_());
        }
        if (!this.f_19853_.f_46443_ && !this.m_213877_()) {
            Entity $$2 = this.m_37282_();
            if ($$2 instanceof ServerPlayer) {
                ServerPlayer $$3 = (ServerPlayer)$$2;
                if ($$3.f_8906_.m_6198_().m_129536_() && $$3.f_19853_ == this.f_19853_ && !$$3.m_5803_()) {
                    if (this.f_19796_.m_188501_() < 0.05f && this.f_19853_.m_46469_().m_46207_(GameRules.f_46134_)) {
                        Endermite $$4 = EntityType.f_20567_.m_20615_(this.f_19853_);
                        $$4.m_7678_($$2.m_20185_(), $$2.m_20186_(), $$2.m_20189_(), $$2.m_146908_(), $$2.m_146909_());
                        this.f_19853_.m_7967_($$4);
                    }
                    if ($$2.m_20159_()) {
                        $$3.m_142098_(this.m_20185_(), this.m_20186_(), this.m_20189_());
                    } else {
                        $$2.m_6021_(this.m_20185_(), this.m_20186_(), this.m_20189_());
                    }
                    $$2.m_183634_();
                    $$2.m_6469_(DamageSource.f_19315_, 5.0f);
                }
            } else if ($$2 != null) {
                $$2.m_6021_(this.m_20185_(), this.m_20186_(), this.m_20189_());
                $$2.m_183634_();
            }
            this.m_146870_();
        }
    }

    @Override
    public void m_8119_() {
        Entity $$0 = this.m_37282_();
        if ($$0 instanceof Player && !$$0.m_6084_()) {
            this.m_146870_();
        } else {
            super.m_8119_();
        }
    }

    @Override
    @Nullable
    public Entity m_5489_(ServerLevel p_37506_) {
        Entity $$1 = this.m_37282_();
        if ($$1 != null && $$1.f_19853_.m_46472_() != p_37506_.m_46472_()) {
            this.m_5602_(null);
        }
        return super.m_5489_(p_37506_);
    }
}

