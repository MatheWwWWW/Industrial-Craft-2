/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.control;

import java.util.Optional;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.Control;
import net.minecraft.world.phys.Vec3;

public class LookControl
implements Control {
    protected final Mob f_24937_;
    protected float f_24938_;
    protected float f_24939_;
    protected int f_186068_;
    protected double f_24941_;
    protected double f_24942_;
    protected double f_24943_;

    public LookControl(Mob p_24945_) {
        this.f_24937_ = p_24945_;
    }

    public void m_24964_(Vec3 p_24965_) {
        this.m_24946_(p_24965_.f_82479_, p_24965_.f_82480_, p_24965_.f_82481_);
    }

    public void m_148051_(Entity p_148052_) {
        this.m_24946_(p_148052_.m_20185_(), LookControl.m_24966_(p_148052_), p_148052_.m_20189_());
    }

    public void m_24960_(Entity p_24961_, float p_24962_, float p_24963_) {
        this.m_24950_(p_24961_.m_20185_(), LookControl.m_24966_(p_24961_), p_24961_.m_20189_(), p_24962_, p_24963_);
    }

    public void m_24946_(double p_24947_, double p_24948_, double p_24949_) {
        this.m_24950_(p_24947_, p_24948_, p_24949_, this.f_24937_.m_21529_(), this.f_24937_.m_8132_());
    }

    public void m_24950_(double p_24951_, double p_24952_, double p_24953_, float p_24954_, float p_24955_) {
        this.f_24941_ = p_24951_;
        this.f_24942_ = p_24952_;
        this.f_24943_ = p_24953_;
        this.f_24938_ = p_24954_;
        this.f_24939_ = p_24955_;
        this.f_186068_ = 2;
    }

    public void m_8128_() {
        if (this.m_8106_()) {
            this.f_24937_.m_146926_(0.0f);
        }
        if (this.f_186068_ > 0) {
            --this.f_186068_;
            this.m_180896_().ifPresent(p_181130_ -> {
                this.f_24937_.f_20885_ = this.m_24956_(this.f_24937_.f_20885_, p_181130_.floatValue(), this.f_24938_);
            });
            this.m_180897_().ifPresent(p_181128_ -> this.f_24937_.m_146926_(this.m_24956_(this.f_24937_.m_146909_(), p_181128_.floatValue(), this.f_24939_)));
        } else {
            this.f_24937_.f_20885_ = this.m_24956_(this.f_24937_.f_20885_, this.f_24937_.f_20883_, 10.0f);
        }
        this.m_142586_();
    }

    protected void m_142586_() {
        if (!this.f_24937_.m_21573_().m_26571_()) {
            this.f_24937_.f_20885_ = Mth.m_14094_(this.f_24937_.f_20885_, this.f_24937_.f_20883_, this.f_24937_.m_8085_());
        }
    }

    protected boolean m_8106_() {
        return true;
    }

    public boolean m_186069_() {
        return this.f_186068_ > 0;
    }

    public double m_24969_() {
        return this.f_24941_;
    }

    public double m_24970_() {
        return this.f_24942_;
    }

    public double m_24971_() {
        return this.f_24943_;
    }

    protected Optional<Float> m_180897_() {
        double $$0 = this.f_24941_ - this.f_24937_.m_20185_();
        double $$1 = this.f_24942_ - this.f_24937_.m_20188_();
        double $$2 = this.f_24943_ - this.f_24937_.m_20189_();
        double $$3 = Math.sqrt($$0 * $$0 + $$2 * $$2);
        return Math.abs($$1) > (double)1.0E-5f || Math.abs($$3) > (double)1.0E-5f ? Optional.of(Float.valueOf((float)(-(Mth.m_14136_($$1, $$3) * 57.2957763671875)))) : Optional.empty();
    }

    protected Optional<Float> m_180896_() {
        double $$0 = this.f_24941_ - this.f_24937_.m_20185_();
        double $$1 = this.f_24943_ - this.f_24937_.m_20189_();
        return Math.abs($$1) > (double)1.0E-5f || Math.abs($$0) > (double)1.0E-5f ? Optional.of(Float.valueOf((float)(Mth.m_14136_($$1, $$0) * 57.2957763671875) - 90.0f)) : Optional.empty();
    }

    protected float m_24956_(float p_24957_, float p_24958_, float p_24959_) {
        float $$3 = Mth.m_14118_(p_24957_, p_24958_);
        float $$4 = Mth.m_14036_($$3, -p_24959_, p_24959_);
        return p_24957_ + $$4;
    }

    private static double m_24966_(Entity p_24967_) {
        if (p_24967_ instanceof LivingEntity) {
            return p_24967_.m_20188_();
        }
        return (p_24967_.m_20191_().f_82289_ + p_24967_.m_20191_().f_82292_) / 2.0;
    }
}

