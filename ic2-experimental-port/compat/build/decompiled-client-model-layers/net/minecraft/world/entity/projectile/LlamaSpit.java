/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class LlamaSpit
extends Projectile {
    public LlamaSpit(EntityType<? extends LlamaSpit> p_37224_, Level p_37225_) {
        super((EntityType<? extends Projectile>)p_37224_, p_37225_);
    }

    public LlamaSpit(Level p_37235_, Llama p_37236_) {
        this((EntityType<? extends LlamaSpit>)EntityType.f_20467_, p_37235_);
        this.m_5602_(p_37236_);
        this.m_6034_(p_37236_.m_20185_() - (double)(p_37236_.m_20205_() + 1.0f) * 0.5 * (double)Mth.m_14031_(p_37236_.f_20883_ * ((float)Math.PI / 180)), p_37236_.m_20188_() - (double)0.1f, p_37236_.m_20189_() + (double)(p_37236_.m_20205_() + 1.0f) * 0.5 * (double)Mth.m_14089_(p_37236_.f_20883_ * ((float)Math.PI / 180)));
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        Vec3 $$0 = this.m_20184_();
        HitResult $$1 = ProjectileUtil.m_37294_(this, this::m_5603_);
        this.m_6532_($$1);
        double $$2 = this.m_20185_() + $$0.f_82479_;
        double $$3 = this.m_20186_() + $$0.f_82480_;
        double $$4 = this.m_20189_() + $$0.f_82481_;
        this.m_37283_();
        float $$5 = 0.99f;
        float $$6 = 0.06f;
        if (this.f_19853_.m_45556_(this.m_20191_()).noneMatch(BlockBehaviour.BlockStateBase::m_60795_)) {
            this.m_146870_();
            return;
        }
        if (this.m_20072_()) {
            this.m_146870_();
            return;
        }
        this.m_20256_($$0.m_82490_(0.99f));
        if (!this.m_20068_()) {
            this.m_20256_(this.m_20184_().m_82520_(0.0, -0.06f, 0.0));
        }
        this.m_6034_($$2, $$3, $$4);
    }

    @Override
    protected void m_5790_(EntityHitResult p_37241_) {
        super.m_5790_(p_37241_);
        Entity $$1 = this.m_37282_();
        if ($$1 instanceof LivingEntity) {
            p_37241_.m_82443_().m_6469_(DamageSource.m_19340_(this, (LivingEntity)$$1).m_19366_(), 1.0f);
        }
    }

    @Override
    protected void m_8060_(BlockHitResult p_37239_) {
        super.m_8060_(p_37239_);
        if (!this.f_19853_.f_46443_) {
            this.m_146870_();
        }
    }

    @Override
    protected void m_8097_() {
    }

    @Override
    public void m_141965_(ClientboundAddEntityPacket p_150162_) {
        super.m_141965_(p_150162_);
        double $$1 = p_150162_.m_131503_();
        double $$2 = p_150162_.m_131504_();
        double $$3 = p_150162_.m_131505_();
        for (int $$4 = 0; $$4 < 7; ++$$4) {
            double $$5 = 0.4 + 0.1 * (double)$$4;
            this.f_19853_.m_7106_(ParticleTypes.f_123764_, this.m_20185_(), this.m_20186_(), this.m_20189_(), $$1 * $$5, $$2, $$3 * $$5);
        }
        this.m_20334_($$1, $$2, $$3);
    }
}

