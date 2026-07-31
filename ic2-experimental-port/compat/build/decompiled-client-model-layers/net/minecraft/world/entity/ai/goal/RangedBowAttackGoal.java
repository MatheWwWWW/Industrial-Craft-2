/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Items;

public class RangedBowAttackGoal<T extends Monster>
extends Goal {
    private final T f_25782_;
    private final double f_25783_;
    private int f_25784_;
    private final float f_25785_;
    private int f_25786_ = -1;
    private int f_25787_;
    private boolean f_25788_;
    private boolean f_25789_;
    private int f_25790_ = -1;

    public RangedBowAttackGoal(T p_25792_, double p_25793_, int p_25794_, float p_25795_) {
        this.f_25782_ = p_25792_;
        this.f_25783_ = p_25793_;
        this.f_25784_ = p_25794_;
        this.f_25785_ = p_25795_ * p_25795_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public void m_25797_(int p_25798_) {
        this.f_25784_ = p_25798_;
    }

    @Override
    public boolean m_8036_() {
        if (((Mob)this.f_25782_).m_5448_() == null) {
            return false;
        }
        return this.m_25803_();
    }

    protected boolean m_25803_() {
        return ((LivingEntity)this.f_25782_).m_21055_(Items.f_42411_);
    }

    @Override
    public boolean m_8045_() {
        return (this.m_8036_() || !((Mob)this.f_25782_).m_21573_().m_26571_()) && this.m_25803_();
    }

    @Override
    public void m_8056_() {
        super.m_8056_();
        ((Mob)this.f_25782_).m_21561_(true);
    }

    @Override
    public void m_8041_() {
        super.m_8041_();
        ((Mob)this.f_25782_).m_21561_(false);
        this.f_25787_ = 0;
        this.f_25786_ = -1;
        ((LivingEntity)this.f_25782_).m_5810_();
    }

    @Override
    public boolean m_183429_() {
        return true;
    }

    @Override
    public void m_8037_() {
        boolean $$3;
        LivingEntity $$0 = ((Mob)this.f_25782_).m_5448_();
        if ($$0 == null) {
            return;
        }
        double $$1 = ((Entity)this.f_25782_).m_20275_($$0.m_20185_(), $$0.m_20186_(), $$0.m_20189_());
        boolean $$2 = ((Mob)this.f_25782_).m_21574_().m_148306_($$0);
        boolean bl = $$3 = this.f_25787_ > 0;
        if ($$2 != $$3) {
            this.f_25787_ = 0;
        }
        this.f_25787_ = $$2 ? ++this.f_25787_ : --this.f_25787_;
        if ($$1 > (double)this.f_25785_ || this.f_25787_ < 20) {
            ((Mob)this.f_25782_).m_21573_().m_5624_($$0, this.f_25783_);
            this.f_25790_ = -1;
        } else {
            ((Mob)this.f_25782_).m_21573_().m_26573_();
            ++this.f_25790_;
        }
        if (this.f_25790_ >= 20) {
            if ((double)((LivingEntity)this.f_25782_).m_217043_().m_188501_() < 0.3) {
                boolean bl2 = this.f_25788_ = !this.f_25788_;
            }
            if ((double)((LivingEntity)this.f_25782_).m_217043_().m_188501_() < 0.3) {
                this.f_25789_ = !this.f_25789_;
            }
            this.f_25790_ = 0;
        }
        if (this.f_25790_ > -1) {
            if ($$1 > (double)(this.f_25785_ * 0.75f)) {
                this.f_25789_ = false;
            } else if ($$1 < (double)(this.f_25785_ * 0.25f)) {
                this.f_25789_ = true;
            }
            ((Mob)this.f_25782_).m_21566_().m_24988_(this.f_25789_ ? -0.5f : 0.5f, this.f_25788_ ? 0.5f : -0.5f);
            ((Mob)this.f_25782_).m_21391_($$0, 30.0f, 30.0f);
        } else {
            ((Mob)this.f_25782_).m_21563_().m_24960_($$0, 30.0f, 30.0f);
        }
        if (((LivingEntity)this.f_25782_).m_6117_()) {
            int $$4;
            if (!$$2 && this.f_25787_ < -60) {
                ((LivingEntity)this.f_25782_).m_5810_();
            } else if ($$2 && ($$4 = ((LivingEntity)this.f_25782_).m_21252_()) >= 20) {
                ((LivingEntity)this.f_25782_).m_5810_();
                ((RangedAttackMob)this.f_25782_).m_6504_($$0, BowItem.m_40661_($$4));
                this.f_25786_ = this.f_25784_;
            }
        } else if (--this.f_25786_ <= 0 && this.f_25787_ >= -60) {
            ((LivingEntity)this.f_25782_).m_6672_(ProjectileUtil.m_37297_(this.f_25782_, Items.f_42411_));
        }
    }
}

