/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 */
package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class GolemSensor
extends Sensor<LivingEntity> {
    private static final int f_148277_ = 200;
    private static final int f_148278_ = 600;

    public GolemSensor() {
        this(200);
    }

    public GolemSensor(int p_26642_) {
        super(p_26642_);
    }

    @Override
    protected void m_5578_(ServerLevel p_26645_, LivingEntity p_26646_) {
        GolemSensor.m_26647_(p_26646_);
    }

    @Override
    public Set<MemoryModuleType<?>> m_7163_() {
        return ImmutableSet.of(MemoryModuleType.f_148204_);
    }

    public static void m_26647_(LivingEntity p_26648_) {
        Optional<List<LivingEntity>> $$1 = p_26648_.m_6274_().m_21952_(MemoryModuleType.f_148204_);
        if (!$$1.isPresent()) {
            return;
        }
        boolean $$2 = $$1.get().stream().anyMatch(p_26652_ -> p_26652_.m_6095_().equals(EntityType.f_20460_));
        if ($$2) {
            GolemSensor.m_26649_(p_26648_);
        }
    }

    public static void m_26649_(LivingEntity p_26650_) {
        p_26650_.m_6274_().m_21882_(MemoryModuleType.f_26327_, true, 600L);
    }
}

