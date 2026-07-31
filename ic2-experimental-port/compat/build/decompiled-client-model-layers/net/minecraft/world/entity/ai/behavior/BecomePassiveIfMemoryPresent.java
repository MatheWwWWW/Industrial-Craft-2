/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class BecomePassiveIfMemoryPresent
extends Behavior<LivingEntity> {
    private final int f_22514_;

    public BecomePassiveIfMemoryPresent(MemoryModuleType<?> p_22516_, int p_22517_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26357_, (Object)((Object)MemoryStatus.VALUE_ABSENT), p_22516_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        this.f_22514_ = p_22517_;
    }

    @Override
    protected void m_6735_(ServerLevel p_22519_, LivingEntity p_22520_, long p_22521_) {
        p_22520_.m_6274_().m_21882_(MemoryModuleType.f_26357_, true, this.f_22514_);
        p_22520_.m_6274_().m_21936_(MemoryModuleType.f_26372_);
    }
}

