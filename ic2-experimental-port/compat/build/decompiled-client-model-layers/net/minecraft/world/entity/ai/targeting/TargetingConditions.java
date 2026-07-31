/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.targeting;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class TargetingConditions {
    public static final TargetingConditions f_26872_ = TargetingConditions.m_148352_();
    private static final double f_148347_ = 2.0;
    private final boolean f_148348_;
    private double f_26873_ = -1.0;
    private boolean f_148349_ = true;
    private boolean f_26878_ = true;
    @Nullable
    private Predicate<LivingEntity> f_26879_;

    private TargetingConditions(boolean p_148351_) {
        this.f_148348_ = p_148351_;
    }

    public static TargetingConditions m_148352_() {
        return new TargetingConditions(true);
    }

    public static TargetingConditions m_148353_() {
        return new TargetingConditions(false);
    }

    public TargetingConditions m_148354_() {
        TargetingConditions $$0 = this.f_148348_ ? TargetingConditions.m_148352_() : TargetingConditions.m_148353_();
        $$0.f_26873_ = this.f_26873_;
        $$0.f_148349_ = this.f_148349_;
        $$0.f_26878_ = this.f_26878_;
        $$0.f_26879_ = this.f_26879_;
        return $$0;
    }

    public TargetingConditions m_26883_(double p_26884_) {
        this.f_26873_ = p_26884_;
        return this;
    }

    public TargetingConditions m_148355_() {
        this.f_148349_ = false;
        return this;
    }

    public TargetingConditions m_26893_() {
        this.f_26878_ = false;
        return this;
    }

    public TargetingConditions m_26888_(@Nullable Predicate<LivingEntity> p_26889_) {
        this.f_26879_ = p_26889_;
        return this;
    }

    public boolean m_26885_(@Nullable LivingEntity p_26886_, LivingEntity p_26887_) {
        if (p_26886_ == p_26887_) {
            return false;
        }
        if (!p_26887_.m_142065_()) {
            return false;
        }
        if (this.f_26879_ != null && !this.f_26879_.test(p_26887_)) {
            return false;
        }
        if (p_26886_ == null) {
            if (this.f_148348_ && (!p_26887_.m_142066_() || p_26887_.f_19853_.m_46791_() == Difficulty.PEACEFUL)) {
                return false;
            }
        } else {
            Mob $$5;
            if (this.f_148348_ && (!p_26886_.m_6779_(p_26887_) || !p_26886_.m_6549_(p_26887_.m_6095_()) || p_26886_.m_7307_(p_26887_))) {
                return false;
            }
            if (this.f_26873_ > 0.0) {
                double $$2 = this.f_26878_ ? p_26887_.m_20968_(p_26886_) : 1.0;
                double $$3 = Math.max(this.f_26873_ * $$2, 2.0);
                double $$4 = p_26886_.m_20275_(p_26887_.m_20185_(), p_26887_.m_20186_(), p_26887_.m_20189_());
                if ($$4 > $$3 * $$3) {
                    return false;
                }
            }
            if (this.f_148349_ && p_26886_ instanceof Mob && !($$5 = (Mob)p_26886_).m_21574_().m_148306_(p_26887_)) {
                return false;
            }
        }
        return true;
    }
}

