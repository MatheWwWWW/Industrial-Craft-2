/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.behavior;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class StayCloseToTarget<E extends LivingEntity>
extends Behavior<E> {
    private final Function<LivingEntity, Optional<PositionTracker>> f_217381_;
    private final int f_217382_;
    private final int f_217383_;
    private final float f_217384_;

    public StayCloseToTarget(Function<LivingEntity, Optional<PositionTracker>> p_217386_, int p_217387_, int p_217388_, float p_217389_) {
        super(Map.of(MemoryModuleType.f_26370_, MemoryStatus.VALUE_ABSENT));
        this.f_217381_ = p_217386_;
        this.f_217382_ = p_217387_;
        this.f_217383_ = p_217388_;
        this.f_217384_ = p_217389_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_217391_, E p_217392_) {
        Optional<PositionTracker> $$2 = this.f_217381_.apply((LivingEntity)p_217392_);
        if ($$2.isEmpty()) {
            return false;
        }
        PositionTracker $$3 = $$2.get();
        return !((Entity)p_217392_).m_20182_().m_82509_($$3.m_7024_(), this.f_217383_);
    }

    @Override
    protected void m_6735_(ServerLevel p_217394_, E p_217395_, long p_217396_) {
        BehaviorUtils.m_217128_(p_217395_, this.f_217381_.apply((LivingEntity)p_217395_).get(), this.f_217384_, this.f_217382_);
    }
}

