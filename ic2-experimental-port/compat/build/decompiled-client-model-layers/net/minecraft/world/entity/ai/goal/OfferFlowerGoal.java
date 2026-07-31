/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;

public class OfferFlowerGoal
extends Goal {
    private static final TargetingConditions f_25663_ = TargetingConditions.m_148353_().m_26883_(6.0);
    public static final int f_148131_ = 400;
    private final IronGolem f_25664_;
    private Villager f_25665_;
    private int f_25666_;

    public OfferFlowerGoal(IronGolem p_25669_) {
        this.f_25664_ = p_25669_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean m_8036_() {
        if (!this.f_25664_.f_19853_.m_46461_()) {
            return false;
        }
        if (this.f_25664_.m_217043_().m_188503_(8000) != 0) {
            return false;
        }
        this.f_25665_ = this.f_25664_.f_19853_.m_45963_(Villager.class, f_25663_, this.f_25664_, this.f_25664_.m_20185_(), this.f_25664_.m_20186_(), this.f_25664_.m_20189_(), this.f_25664_.m_20191_().m_82377_(6.0, 2.0, 6.0));
        return this.f_25665_ != null;
    }

    @Override
    public boolean m_8045_() {
        return this.f_25666_ > 0;
    }

    @Override
    public void m_8056_() {
        this.f_25666_ = this.m_183277_(400);
        this.f_25664_.m_28885_(true);
    }

    @Override
    public void m_8041_() {
        this.f_25664_.m_28885_(false);
        this.f_25665_ = null;
    }

    @Override
    public void m_8037_() {
        this.f_25664_.m_21563_().m_24960_(this.f_25665_, 30.0f, 30.0f);
        --this.f_25666_;
    }
}

