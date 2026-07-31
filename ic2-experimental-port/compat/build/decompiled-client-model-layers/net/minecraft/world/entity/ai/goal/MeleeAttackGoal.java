/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

public class MeleeAttackGoal
extends Goal {
    protected final PathfinderMob f_25540_;
    private final double f_25541_;
    private final boolean f_25542_;
    private Path f_25543_;
    private double f_25544_;
    private double f_25545_;
    private double f_25546_;
    private int f_25547_;
    private int f_25548_;
    private final int f_25549_ = 20;
    private long f_25550_;
    private static final long f_148125_ = 20L;

    public MeleeAttackGoal(PathfinderMob p_25552_, double p_25553_, boolean p_25554_) {
        this.f_25540_ = p_25552_;
        this.f_25541_ = p_25553_;
        this.f_25542_ = p_25554_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean m_8036_() {
        long $$0 = this.f_25540_.f_19853_.m_46467_();
        if ($$0 - this.f_25550_ < 20L) {
            return false;
        }
        this.f_25550_ = $$0;
        LivingEntity $$1 = this.f_25540_.m_5448_();
        if ($$1 == null) {
            return false;
        }
        if (!$$1.m_6084_()) {
            return false;
        }
        this.f_25543_ = this.f_25540_.m_21573_().m_6570_($$1, 0);
        if (this.f_25543_ != null) {
            return true;
        }
        return this.m_6639_($$1) >= this.f_25540_.m_20275_($$1.m_20185_(), $$1.m_20186_(), $$1.m_20189_());
    }

    @Override
    public boolean m_8045_() {
        LivingEntity $$0 = this.f_25540_.m_5448_();
        if ($$0 == null) {
            return false;
        }
        if (!$$0.m_6084_()) {
            return false;
        }
        if (!this.f_25542_) {
            return !this.f_25540_.m_21573_().m_26571_();
        }
        if (!this.f_25540_.m_21444_($$0.m_20183_())) {
            return false;
        }
        return !($$0 instanceof Player) || !$$0.m_5833_() && !((Player)$$0).m_7500_();
    }

    @Override
    public void m_8056_() {
        this.f_25540_.m_21573_().m_26536_(this.f_25543_, this.f_25541_);
        this.f_25540_.m_21561_(true);
        this.f_25547_ = 0;
        this.f_25548_ = 0;
    }

    @Override
    public void m_8041_() {
        LivingEntity $$0 = this.f_25540_.m_5448_();
        if (!EntitySelector.f_20406_.test($$0)) {
            this.f_25540_.m_6710_(null);
        }
        this.f_25540_.m_21561_(false);
        this.f_25540_.m_21573_().m_26573_();
    }

    @Override
    public boolean m_183429_() {
        return true;
    }

    @Override
    public void m_8037_() {
        LivingEntity $$0 = this.f_25540_.m_5448_();
        if ($$0 == null) {
            return;
        }
        this.f_25540_.m_21563_().m_24960_($$0, 30.0f, 30.0f);
        double $$1 = this.f_25540_.m_20275_($$0.m_20185_(), $$0.m_20186_(), $$0.m_20189_());
        this.f_25547_ = Math.max(this.f_25547_ - 1, 0);
        if ((this.f_25542_ || this.f_25540_.m_21574_().m_148306_($$0)) && this.f_25547_ <= 0 && (this.f_25544_ == 0.0 && this.f_25545_ == 0.0 && this.f_25546_ == 0.0 || $$0.m_20275_(this.f_25544_, this.f_25545_, this.f_25546_) >= 1.0 || this.f_25540_.m_217043_().m_188501_() < 0.05f)) {
            this.f_25544_ = $$0.m_20185_();
            this.f_25545_ = $$0.m_20186_();
            this.f_25546_ = $$0.m_20189_();
            this.f_25547_ = 4 + this.f_25540_.m_217043_().m_188503_(7);
            if ($$1 > 1024.0) {
                this.f_25547_ += 10;
            } else if ($$1 > 256.0) {
                this.f_25547_ += 5;
            }
            if (!this.f_25540_.m_21573_().m_5624_($$0, this.f_25541_)) {
                this.f_25547_ += 15;
            }
            this.f_25547_ = this.m_183277_(this.f_25547_);
        }
        this.f_25548_ = Math.max(this.f_25548_ - 1, 0);
        this.m_6739_($$0, $$1);
    }

    protected void m_6739_(LivingEntity p_25557_, double p_25558_) {
        double $$2 = this.m_6639_(p_25557_);
        if (p_25558_ <= $$2 && this.f_25548_ <= 0) {
            this.m_25563_();
            this.f_25540_.m_6674_(InteractionHand.MAIN_HAND);
            this.f_25540_.m_7327_(p_25557_);
        }
    }

    protected void m_25563_() {
        this.f_25548_ = this.m_183277_(20);
    }

    protected boolean m_25564_() {
        return this.f_25548_ <= 0;
    }

    protected int m_25565_() {
        return this.f_25548_;
    }

    protected int m_25566_() {
        return this.m_183277_(20);
    }

    protected double m_6639_(LivingEntity p_25556_) {
        return this.f_25540_.m_20205_() * 2.0f * (this.f_25540_.m_20205_() * 2.0f) + p_25556_.m_20205_();
    }
}

