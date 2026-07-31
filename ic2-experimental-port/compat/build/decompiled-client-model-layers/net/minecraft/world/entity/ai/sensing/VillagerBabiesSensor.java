/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 */
package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class VillagerBabiesSensor
extends Sensor<LivingEntity> {
    @Override
    public Set<MemoryModuleType<?>> m_7163_() {
        return ImmutableSet.of(MemoryModuleType.f_26366_);
    }

    @Override
    protected void m_5578_(ServerLevel p_26834_, LivingEntity p_26835_) {
        p_26835_.m_6274_().m_21879_(MemoryModuleType.f_26366_, this.m_26836_(p_26835_));
    }

    private List<LivingEntity> m_26836_(LivingEntity p_26837_) {
        return ImmutableList.copyOf(this.m_186203_(p_26837_).m_186123_(this::m_26838_));
    }

    private boolean m_26838_(LivingEntity p_26839_) {
        return p_26839_.m_6095_() == EntityType.f_20492_ && p_26839_.m_6162_();
    }

    private NearestVisibleLivingEntities m_186203_(LivingEntity p_186204_) {
        return p_186204_.m_6274_().m_21952_(MemoryModuleType.f_148205_).orElse(NearestVisibleLivingEntities.m_186106_());
    }
}

