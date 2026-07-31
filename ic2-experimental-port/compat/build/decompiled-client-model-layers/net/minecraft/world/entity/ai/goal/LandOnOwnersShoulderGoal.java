/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;

public class LandOnOwnersShoulderGoal
extends Goal {
    private final ShoulderRidingEntity f_25479_;
    private ServerPlayer f_25480_;
    private boolean f_25481_;

    public LandOnOwnersShoulderGoal(ShoulderRidingEntity p_25483_) {
        this.f_25479_ = p_25483_;
    }

    @Override
    public boolean m_8036_() {
        ServerPlayer $$0 = (ServerPlayer)this.f_25479_.m_21826_();
        boolean $$1 = $$0 != null && !$$0.m_5833_() && !$$0.m_150110_().f_35935_ && !$$0.m_20069_() && !$$0.f_146808_;
        return !this.f_25479_.m_21827_() && $$1 && this.f_25479_.m_29897_();
    }

    @Override
    public boolean m_6767_() {
        return !this.f_25481_;
    }

    @Override
    public void m_8056_() {
        this.f_25480_ = (ServerPlayer)this.f_25479_.m_21826_();
        this.f_25481_ = false;
    }

    @Override
    public void m_8037_() {
        if (this.f_25481_ || this.f_25479_.m_21825_() || this.f_25479_.m_21523_()) {
            return;
        }
        if (this.f_25479_.m_20191_().m_82381_(this.f_25480_.m_20191_())) {
            this.f_25481_ = this.f_25479_.m_29895_(this.f_25480_);
        }
    }
}

