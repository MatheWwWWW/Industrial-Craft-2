/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.phys.Vec3;

public class WaterAvoidingRandomFlyingGoal
extends WaterAvoidingRandomStrollGoal {
    public WaterAvoidingRandomFlyingGoal(PathfinderMob p_25981_, double p_25982_) {
        super(p_25981_, p_25982_);
    }

    @Override
    @Nullable
    protected Vec3 m_7037_() {
        Vec3 $$0 = this.f_25725_.m_20252_(0.0f);
        int $$1 = 8;
        Vec3 $$2 = HoverRandomPos.m_148465_(this.f_25725_, 8, 7, $$0.f_82479_, $$0.f_82481_, 1.5707964f, 3, 1);
        if ($$2 != null) {
            return $$2;
        }
        return AirAndWaterRandomPos.m_148357_(this.f_25725_, 8, 4, -2, $$0.f_82479_, $$0.f_82481_, 1.5707963705062866);
    }
}

