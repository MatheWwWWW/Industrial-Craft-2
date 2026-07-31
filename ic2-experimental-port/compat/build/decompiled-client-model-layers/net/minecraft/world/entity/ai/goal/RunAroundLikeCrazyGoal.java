/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class RunAroundLikeCrazyGoal
extends Goal {
    private final AbstractHorse f_25884_;
    private final double f_25885_;
    private double f_25886_;
    private double f_25887_;
    private double f_25888_;

    public RunAroundLikeCrazyGoal(AbstractHorse p_25890_, double p_25891_) {
        this.f_25884_ = p_25890_;
        this.f_25885_ = p_25891_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean m_8036_() {
        if (this.f_25884_.m_30614_() || !this.f_25884_.m_20160_()) {
            return false;
        }
        Vec3 $$0 = DefaultRandomPos.m_148403_(this.f_25884_, 5, 4);
        if ($$0 == null) {
            return false;
        }
        this.f_25886_ = $$0.f_82479_;
        this.f_25887_ = $$0.f_82480_;
        this.f_25888_ = $$0.f_82481_;
        return true;
    }

    @Override
    public void m_8056_() {
        this.f_25884_.m_21573_().m_26519_(this.f_25886_, this.f_25887_, this.f_25888_, this.f_25885_);
    }

    @Override
    public boolean m_8045_() {
        return !this.f_25884_.m_30614_() && !this.f_25884_.m_21573_().m_26571_() && this.f_25884_.m_20160_();
    }

    @Override
    public void m_8037_() {
        if (!this.f_25884_.m_30614_() && this.f_25884_.m_217043_().m_188503_(this.m_183277_(50)) == 0) {
            Entity $$0 = this.f_25884_.m_20197_().get(0);
            if ($$0 == null) {
                return;
            }
            if ($$0 instanceof Player) {
                int $$1 = this.f_25884_.m_30624_();
                int $$2 = this.f_25884_.m_7555_();
                if ($$2 > 0 && this.f_25884_.m_217043_().m_188503_($$2) < $$1) {
                    this.f_25884_.m_30637_((Player)$$0);
                    return;
                }
                this.f_25884_.m_30653_(5);
            }
            this.f_25884_.m_20153_();
            this.f_25884_.m_7564_();
            this.f_25884_.f_19853_.m_7605_(this.f_25884_, (byte)6);
        }
    }
}

