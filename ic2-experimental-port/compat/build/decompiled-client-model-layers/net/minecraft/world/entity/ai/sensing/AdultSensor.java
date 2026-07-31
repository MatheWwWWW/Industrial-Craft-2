/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 */
package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class AdultSensor
extends Sensor<AgeableMob> {
    @Override
    public Set<MemoryModuleType<?>> m_7163_() {
        return ImmutableSet.of(MemoryModuleType.f_26331_, MemoryModuleType.f_148205_);
    }

    @Override
    protected void m_5578_(ServerLevel p_148248_, AgeableMob p_148249_) {
        p_148249_.m_6274_().m_21952_(MemoryModuleType.f_148205_).ifPresent(p_186145_ -> this.m_186140_(p_148249_, (NearestVisibleLivingEntities)p_186145_));
    }

    private void m_186140_(AgeableMob p_186141_, NearestVisibleLivingEntities p_186142_) {
        Optional<AgeableMob> $$2 = p_186142_.m_186116_(p_148254_ -> p_148254_.m_6095_() == p_186141_.m_6095_() && !p_148254_.m_6162_()).map(AgeableMob.class::cast);
        p_186141_.m_6274_().m_21886_(MemoryModuleType.f_26331_, $$2);
    }
}

