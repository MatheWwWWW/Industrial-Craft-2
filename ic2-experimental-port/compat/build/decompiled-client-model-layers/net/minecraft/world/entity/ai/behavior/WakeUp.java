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
import net.minecraft.world.entity.schedule.Activity;

public class WakeUp
extends Behavior<LivingEntity> {
    public WakeUp() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of());
    }

    @Override
    protected boolean m_6114_(ServerLevel p_24709_, LivingEntity p_24710_) {
        return !p_24710_.m_6274_().m_21954_(Activity.f_37982_) && p_24710_.m_5803_();
    }

    @Override
    protected void m_6735_(ServerLevel p_24712_, LivingEntity p_24713_, long p_24714_) {
        p_24713_.m_5796_();
    }
}

