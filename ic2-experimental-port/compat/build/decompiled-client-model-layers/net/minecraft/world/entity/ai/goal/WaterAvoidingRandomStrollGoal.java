/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class WaterAvoidingRandomStrollGoal
extends RandomStrollGoal {
    public static final float f_148149_ = 0.001f;
    protected final float f_25985_;

    public WaterAvoidingRandomStrollGoal(PathfinderMob p_25987_, double p_25988_) {
        this(p_25987_, p_25988_, 0.001f);
    }

    public WaterAvoidingRandomStrollGoal(PathfinderMob p_25990_, double p_25991_, float p_25992_) {
        super(p_25990_, p_25991_);
        this.f_25985_ = p_25992_;
    }

    @Override
    @Nullable
    protected Vec3 m_7037_() {
        if (this.f_25725_.m_20072_()) {
            Vec3 $$0 = LandRandomPos.m_148488_(this.f_25725_, 15, 7);
            return $$0 == null ? super.m_7037_() : $$0;
        }
        if (this.f_25725_.m_217043_().m_188501_() >= this.f_25985_) {
            return LandRandomPos.m_148488_(this.f_25725_, 10, 7);
        }
        return super.m_7037_();
    }
}

