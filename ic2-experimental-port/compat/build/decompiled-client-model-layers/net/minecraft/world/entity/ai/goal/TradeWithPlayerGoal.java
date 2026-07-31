/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;

public class TradeWithPlayerGoal
extends Goal {
    private final AbstractVillager f_25956_;

    public TradeWithPlayerGoal(AbstractVillager p_25958_) {
        this.f_25956_ = p_25958_;
        this.m_7021_(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean m_8036_() {
        if (!this.f_25956_.m_6084_()) {
            return false;
        }
        if (this.f_25956_.m_20069_()) {
            return false;
        }
        if (!this.f_25956_.m_20096_()) {
            return false;
        }
        if (this.f_25956_.f_19864_) {
            return false;
        }
        Player $$0 = this.f_25956_.m_7962_();
        if ($$0 == null) {
            return false;
        }
        if (this.f_25956_.m_20280_($$0) > 16.0) {
            return false;
        }
        return $$0.f_36096_ != null;
    }

    @Override
    public void m_8056_() {
        this.f_25956_.m_21573_().m_26573_();
    }

    @Override
    public void m_8041_() {
        this.f_25956_.m_7189_(null);
    }
}

