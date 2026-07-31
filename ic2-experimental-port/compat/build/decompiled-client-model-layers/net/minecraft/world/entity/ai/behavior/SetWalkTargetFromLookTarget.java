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
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class SetWalkTargetFromLookTarget
extends Behavior<LivingEntity> {
    private final Function<LivingEntity, Float> f_24081_;
    private final int f_24082_;
    private final Predicate<LivingEntity> f_182357_;

    public SetWalkTargetFromLookTarget(float p_24084_, int p_24085_) {
        this((LivingEntity p_182369_) -> true, p_182364_ -> Float.valueOf(p_24084_), p_24085_);
    }

    public SetWalkTargetFromLookTarget(Predicate<LivingEntity> p_182359_, Function<LivingEntity, Float> p_182360_, int p_182361_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        this.f_24081_ = p_182360_;
        this.f_24082_ = p_182361_;
        this.f_182357_ = p_182359_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_182366_, LivingEntity p_182367_) {
        return this.f_182357_.test(p_182367_);
    }

    @Override
    protected void m_6735_(ServerLevel p_24087_, LivingEntity p_24088_, long p_24089_) {
        Brain<?> $$3 = p_24088_.m_6274_();
        PositionTracker $$4 = $$3.m_21952_(MemoryModuleType.f_26371_).get();
        $$3.m_21879_(MemoryModuleType.f_26370_, new WalkTarget($$4, this.f_24081_.apply(p_24088_).floatValue(), this.f_24082_));
    }
}

