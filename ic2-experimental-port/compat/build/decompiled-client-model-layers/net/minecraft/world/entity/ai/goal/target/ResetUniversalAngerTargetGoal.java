/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal.target;

import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AABB;

public class ResetUniversalAngerTargetGoal<T extends Mob>
extends Goal {
    private static final int f_148154_ = 10;
    private final T f_26117_;
    private final boolean f_26118_;
    private int f_26119_;

    public ResetUniversalAngerTargetGoal(T p_26121_, boolean p_26122_) {
        this.f_26117_ = p_26121_;
        this.f_26118_ = p_26122_;
    }

    @Override
    public boolean m_8036_() {
        return ((Mob)this.f_26117_).f_19853_.m_46469_().m_46207_(GameRules.f_46127_) && this.m_26129_();
    }

    private boolean m_26129_() {
        return ((LivingEntity)this.f_26117_).m_21188_() != null && ((LivingEntity)this.f_26117_).m_21188_().m_6095_() == EntityType.f_20532_ && ((LivingEntity)this.f_26117_).m_21213_() > this.f_26119_;
    }

    @Override
    public void m_8056_() {
        this.f_26119_ = ((LivingEntity)this.f_26117_).m_21213_();
        ((NeutralMob)this.f_26117_).m_21661_();
        if (this.f_26118_) {
            this.m_26130_().stream().filter(p_26127_ -> p_26127_ != this.f_26117_).map(p_26125_ -> (NeutralMob)((Object)p_26125_)).forEach(NeutralMob::m_21661_);
        }
        super.m_8056_();
    }

    private List<? extends Mob> m_26130_() {
        double $$0 = ((LivingEntity)this.f_26117_).m_21133_(Attributes.f_22277_);
        AABB $$1 = AABB.m_82333_(((Entity)this.f_26117_).m_20182_()).m_82377_($$0, 10.0, $$0);
        return ((Mob)this.f_26117_).f_19853_.m_6443_(this.f_26117_.getClass(), $$1, EntitySelector.f_20408_);
    }
}

