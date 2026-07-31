/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal.target;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.raid.Raider;

public class NearestHealableRaiderTargetGoal<T extends LivingEntity>
extends NearestAttackableTargetGoal<T> {
    private static final int f_148153_ = 200;
    private int f_26085_ = 0;

    public NearestHealableRaiderTargetGoal(Raider p_26087_, Class<T> p_26088_, boolean p_26089_, @Nullable Predicate<LivingEntity> p_26090_) {
        super(p_26087_, p_26088_, 500, p_26089_, false, p_26090_);
    }

    public int m_26093_() {
        return this.f_26085_;
    }

    public void m_26094_() {
        --this.f_26085_;
    }

    @Override
    public boolean m_8036_() {
        if (this.f_26085_ > 0 || !this.f_26135_.m_217043_().m_188499_()) {
            return false;
        }
        if (!((Raider)this.f_26135_).m_37886_()) {
            return false;
        }
        this.m_26073_();
        return this.f_26050_ != null;
    }

    @Override
    public void m_8056_() {
        this.f_26085_ = NearestHealableRaiderTargetGoal.m_186073_(200);
        super.m_8056_();
    }
}

