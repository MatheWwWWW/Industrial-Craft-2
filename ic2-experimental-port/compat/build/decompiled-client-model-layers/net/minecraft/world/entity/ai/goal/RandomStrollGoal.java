/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class RandomStrollGoal
extends Goal {
    public static final int f_148133_ = 120;
    protected final PathfinderMob f_25725_;
    protected double f_25726_;
    protected double f_25727_;
    protected double f_25728_;
    protected final double f_25729_;
    protected int f_25730_;
    protected boolean f_25731_;
    private final boolean f_25732_;

    public RandomStrollGoal(PathfinderMob p_25734_, double p_25735_) {
        this(p_25734_, p_25735_, 120);
    }

    public RandomStrollGoal(PathfinderMob p_25737_, double p_25738_, int p_25739_) {
        this(p_25737_, p_25738_, p_25739_, true);
    }

    public RandomStrollGoal(PathfinderMob p_25741_, double p_25742_, int p_25743_, boolean p_25744_) {
        this.f_25725_ = p_25741_;
        this.f_25729_ = p_25742_;
        this.f_25730_ = p_25743_;
        this.f_25732_ = p_25744_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean m_8036_() {
        Vec3 $$0;
        if (this.f_25725_.m_20160_()) {
            return false;
        }
        if (!this.f_25731_) {
            if (this.f_25732_ && this.f_25725_.m_21216_() >= 100) {
                return false;
            }
            if (this.f_25725_.m_217043_().m_188503_(RandomStrollGoal.m_186073_(this.f_25730_)) != 0) {
                return false;
            }
        }
        if (($$0 = this.m_7037_()) == null) {
            return false;
        }
        this.f_25726_ = $$0.f_82479_;
        this.f_25727_ = $$0.f_82480_;
        this.f_25728_ = $$0.f_82481_;
        this.f_25731_ = false;
        return true;
    }

    @Nullable
    protected Vec3 m_7037_() {
        return DefaultRandomPos.m_148403_(this.f_25725_, 10, 7);
    }

    @Override
    public boolean m_8045_() {
        return !this.f_25725_.m_21573_().m_26571_() && !this.f_25725_.m_20160_();
    }

    @Override
    public void m_8056_() {
        this.f_25725_.m_21573_().m_26519_(this.f_25726_, this.f_25727_, this.f_25728_, this.f_25729_);
    }

    @Override
    public void m_8041_() {
        this.f_25725_.m_21573_().m_26573_();
        super.m_8041_();
    }

    public void m_25751_() {
        this.f_25731_ = true;
    }

    public void m_25746_(int p_25747_) {
        this.f_25730_ = p_25747_;
    }
}

