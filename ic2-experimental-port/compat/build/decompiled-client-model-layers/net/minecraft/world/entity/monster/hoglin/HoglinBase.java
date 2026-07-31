/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.monster.hoglin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

public interface HoglinBase {
    public static final int f_149916_ = 10;

    public int m_7575_();

    public static boolean m_34642_(LivingEntity p_34643_, LivingEntity p_34644_) {
        float $$4;
        float $$2 = (float)p_34643_.m_21133_(Attributes.f_22281_);
        if (!p_34643_.m_6162_() && (int)$$2 > 0) {
            float $$3 = $$2 / 2.0f + (float)p_34643_.f_19853_.f_46441_.m_188503_((int)$$2);
        } else {
            $$4 = $$2;
        }
        boolean $$5 = p_34644_.m_6469_(DamageSource.m_19370_(p_34643_), $$4);
        if ($$5) {
            p_34643_.m_19970_(p_34643_, p_34644_);
            if (!p_34643_.m_6162_()) {
                HoglinBase.m_34645_(p_34643_, p_34644_);
            }
        }
        return $$5;
    }

    public static void m_34645_(LivingEntity p_34646_, LivingEntity p_34647_) {
        double $$3;
        double $$2 = p_34646_.m_21133_(Attributes.f_22282_);
        double $$4 = $$2 - ($$3 = p_34647_.m_21133_(Attributes.f_22278_));
        if ($$4 <= 0.0) {
            return;
        }
        double $$5 = p_34647_.m_20185_() - p_34646_.m_20185_();
        double $$6 = p_34647_.m_20189_() - p_34646_.m_20189_();
        float $$7 = p_34646_.f_19853_.f_46441_.m_188503_(21) - 10;
        double $$8 = $$4 * (double)(p_34646_.f_19853_.f_46441_.m_188501_() * 0.5f + 0.2f);
        Vec3 $$9 = new Vec3($$5, 0.0, $$6).m_82541_().m_82490_($$8).m_82524_($$7);
        double $$10 = $$4 * (double)p_34646_.f_19853_.f_46441_.m_188501_() * 0.5;
        p_34647_.m_5997_($$9.f_82479_, $$10, $$9.f_82481_);
        p_34647_.f_19864_ = true;
    }
}

