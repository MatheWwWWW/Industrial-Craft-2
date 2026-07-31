/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class BreedGoal
extends Goal {
    private static final TargetingConditions f_25116_ = TargetingConditions.m_148353_().m_26883_(8.0).m_148355_();
    protected final Animal f_25113_;
    private final Class<? extends Animal> f_25117_;
    protected final Level f_25114_;
    @Nullable
    protected Animal f_25115_;
    private int f_25118_;
    private final double f_25119_;

    public BreedGoal(Animal p_25122_, double p_25123_) {
        this(p_25122_, p_25123_, p_25122_.getClass());
    }

    public BreedGoal(Animal p_25125_, double p_25126_, Class<? extends Animal> p_25127_) {
        this.f_25113_ = p_25125_;
        this.f_25114_ = p_25125_.f_19853_;
        this.f_25117_ = p_25127_;
        this.f_25119_ = p_25126_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean m_8036_() {
        if (!this.f_25113_.m_27593_()) {
            return false;
        }
        this.f_25115_ = this.m_25132_();
        return this.f_25115_ != null;
    }

    @Override
    public boolean m_8045_() {
        return this.f_25115_.m_6084_() && this.f_25115_.m_27593_() && this.f_25118_ < 60;
    }

    @Override
    public void m_8041_() {
        this.f_25115_ = null;
        this.f_25118_ = 0;
    }

    @Override
    public void m_8037_() {
        this.f_25113_.m_21563_().m_24960_(this.f_25115_, 10.0f, this.f_25113_.m_8132_());
        this.f_25113_.m_21573_().m_5624_(this.f_25115_, this.f_25119_);
        ++this.f_25118_;
        if (this.f_25118_ >= this.m_183277_(60) && this.f_25113_.m_20280_(this.f_25115_) < 9.0) {
            this.m_8026_();
        }
    }

    @Nullable
    private Animal m_25132_() {
        List<? extends Animal> $$0 = this.f_25114_.m_45971_(this.f_25117_, f_25116_, this.f_25113_, this.f_25113_.m_20191_().m_82400_(8.0));
        double $$1 = Double.MAX_VALUE;
        Animal $$2 = null;
        for (Animal animal : $$0) {
            if (!this.f_25113_.m_7848_(animal) || !(this.f_25113_.m_20280_(animal) < $$1)) continue;
            $$2 = animal;
            $$1 = this.f_25113_.m_20280_(animal);
        }
        return $$2;
    }

    protected void m_8026_() {
        this.f_25113_.m_27563_((ServerLevel)this.f_25114_, this.f_25115_);
    }
}

