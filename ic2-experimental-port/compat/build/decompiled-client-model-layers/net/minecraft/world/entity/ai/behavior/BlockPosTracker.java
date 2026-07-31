/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.phys.Vec3;

public class BlockPosTracker
implements PositionTracker {
    private final BlockPos f_22673_;
    private final Vec3 f_22674_;

    public BlockPosTracker(BlockPos p_22676_) {
        this.f_22673_ = p_22676_.m_7949_();
        this.f_22674_ = Vec3.m_82512_(p_22676_);
    }

    @Override
    public Vec3 m_7024_() {
        return this.f_22674_;
    }

    @Override
    public BlockPos m_6675_() {
        return this.f_22673_;
    }

    @Override
    public boolean m_6826_(LivingEntity p_22679_) {
        return true;
    }

    public String toString() {
        return "BlockPosTracker{blockPos=" + this.f_22673_ + ", centerPosition=" + this.f_22674_ + "}";
    }
}

