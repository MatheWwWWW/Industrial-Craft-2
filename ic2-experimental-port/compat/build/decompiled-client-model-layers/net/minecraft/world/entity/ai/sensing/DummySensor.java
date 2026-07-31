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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class DummySensor
extends Sensor<LivingEntity> {
    @Override
    protected void m_5578_(ServerLevel p_26638_, LivingEntity p_26639_) {
    }

    @Override
    public Set<MemoryModuleType<?>> m_7163_() {
        return ImmutableSet.of();
    }
}

