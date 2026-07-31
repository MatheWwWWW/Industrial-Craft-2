/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.behavior;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.phys.Vec3;

public class FlyingRandomStroll
extends RandomStroll {
    public FlyingRandomStroll(float p_217182_) {
        this(p_217182_, true);
    }

    public FlyingRandomStroll(float p_217184_, boolean p_217185_) {
        super(p_217184_, p_217185_);
    }

    @Override
    protected Vec3 m_142622_(PathfinderMob p_217187_) {
        Vec3 $$1 = p_217187_.m_20252_(0.0f);
        return AirAndWaterRandomPos.m_148357_(p_217187_, this.f_23741_, this.f_23742_, -2, $$1.f_82479_, $$1.f_82481_, 1.5707963705062866);
    }
}

