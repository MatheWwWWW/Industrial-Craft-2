/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class SetWalkTargetFromAttackTargetIfTargetOutOfReach
extends Behavior<Mob> {
    private static final int f_147903_ = 1;
    private final Function<LivingEntity, Float> f_24024_;

    public SetWalkTargetFromAttackTargetIfTargetOutOfReach(float p_24026_) {
        this((LivingEntity p_147908_) -> Float.valueOf(p_24026_));
    }

    public SetWalkTargetFromAttackTargetIfTargetOutOfReach(Function<LivingEntity, Float> p_147905_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_148205_, (Object)((Object)MemoryStatus.REGISTERED)));
        this.f_24024_ = p_147905_;
    }

    @Override
    protected void m_6735_(ServerLevel p_24032_, Mob p_24033_, long p_24034_) {
        LivingEntity $$3 = p_24033_.m_6274_().m_21952_(MemoryModuleType.f_26372_).get();
        if (BehaviorUtils.m_22667_(p_24033_, $$3) && BehaviorUtils.m_22632_(p_24033_, $$3, 1)) {
            this.m_24035_(p_24033_);
        } else {
            this.m_24037_(p_24033_, $$3);
        }
    }

    private void m_24037_(LivingEntity p_24038_, LivingEntity p_24039_) {
        Brain<?> $$2 = p_24038_.m_6274_();
        $$2.m_21879_(MemoryModuleType.f_26371_, new EntityTracker(p_24039_, true));
        WalkTarget $$3 = new WalkTarget(new EntityTracker(p_24039_, false), this.f_24024_.apply(p_24038_).floatValue(), 0);
        $$2.m_21879_(MemoryModuleType.f_26370_, $$3);
    }

    private void m_24035_(LivingEntity p_24036_) {
        p_24036_.m_6274_().m_21936_(MemoryModuleType.f_26370_);
    }
}

