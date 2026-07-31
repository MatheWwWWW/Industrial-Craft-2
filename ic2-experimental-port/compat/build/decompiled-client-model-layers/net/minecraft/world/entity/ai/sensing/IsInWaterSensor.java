/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 */
package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class IsInWaterSensor
extends Sensor<LivingEntity> {
    @Override
    public Set<MemoryModuleType<?>> m_7163_() {
        return ImmutableSet.of(MemoryModuleType.f_217766_);
    }

    @Override
    protected void m_5578_(ServerLevel p_217816_, LivingEntity p_217817_) {
        if (p_217817_.m_20069_()) {
            p_217817_.m_6274_().m_21879_(MemoryModuleType.f_217766_, Unit.INSTANCE);
        } else {
            p_217817_.m_6274_().m_21936_(MemoryModuleType.f_217766_);
        }
    }
}

