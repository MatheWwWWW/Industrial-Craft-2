/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class RangedAttackGoal
extends Goal {
    private final Mob f_25757_;
    private final RangedAttackMob f_25758_;
    @Nullable
    private LivingEntity f_25759_;
    private int f_25760_ = -1;
    private final double f_25761_;
    private int f_25762_;
    private final int f_25763_;
    private final int f_25764_;
    private final float f_25765_;
    private final float f_25766_;

    public RangedAttackGoal(RangedAttackMob p_25768_, double p_25769_, int p_25770_, float p_25771_) {
        this(p_25768_, p_25769_, p_25770_, p_25770_, p_25771_);
    }

    public RangedAttackGoal(RangedAttackMob p_25773_, double p_25774_, int p_25775_, int p_25776_, float p_25777_) {
        if (!(p_25773_ instanceof LivingEntity)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        this.f_25758_ = p_25773_;
        this.f_25757_ = (Mob)((Object)p_25773_);
        this.f_25761_ = p_25774_;
        this.f_25763_ = p_25775_;
        this.f_25764_ = p_25776_;
        this.f_25765_ = p_25777_;
        this.f_25766_ = p_25777_ * p_25777_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean m_8036_() {
        LivingEntity $$0 = this.f_25757_.m_5448_();
        if ($$0 == null || !$$0.m_6084_()) {
            return false;
        }
        this.f_25759_ = $$0;
        return true;
    }

    @Override
    public boolean m_8045_() {
        return this.m_8036_() || this.f_25759_.m_6084_() && !this.f_25757_.m_21573_().m_26571_();
    }

    @Override
    public void m_8041_() {
        this.f_25759_ = null;
        this.f_25762_ = 0;
        this.f_25760_ = -1;
    }

    @Override
    public boolean m_183429_() {
        return true;
    }

    @Override
    public void m_8037_() {
        double $$0 = this.f_25757_.m_20275_(this.f_25759_.m_20185_(), this.f_25759_.m_20186_(), this.f_25759_.m_20189_());
        boolean $$1 = this.f_25757_.m_21574_().m_148306_(this.f_25759_);
        this.f_25762_ = $$1 ? ++this.f_25762_ : 0;
        if ($$0 > (double)this.f_25766_ || this.f_25762_ < 5) {
            this.f_25757_.m_21573_().m_5624_(this.f_25759_, this.f_25761_);
        } else {
            this.f_25757_.m_21573_().m_26573_();
        }
        this.f_25757_.m_21563_().m_24960_(this.f_25759_, 30.0f, 30.0f);
        if (--this.f_25760_ == 0) {
            if (!$$1) {
                return;
            }
            float $$2 = (float)Math.sqrt($$0) / this.f_25765_;
            float $$3 = Mth.m_14036_($$2, 0.1f, 1.0f);
            this.f_25758_.m_6504_(this.f_25759_, $$3);
            this.f_25760_ = Mth.m_14143_($$2 * (float)(this.f_25764_ - this.f_25763_) + (float)this.f_25763_);
        } else if (this.f_25760_ < 0) {
            this.f_25760_ = Mth.m_14107_(Mth.m_14139_(Math.sqrt($$0) / (double)this.f_25765_, this.f_25763_, this.f_25764_));
        }
    }
}

