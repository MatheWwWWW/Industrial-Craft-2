/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 */
package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.phys.AABB;

public class NearestLivingEntitySensor<T extends LivingEntity>
extends Sensor<T> {
    @Override
    protected void m_5578_(ServerLevel p_26710_, T p_26711_) {
        AABB $$2 = ((Entity)p_26711_).m_20191_().m_82377_(this.m_214020_(), this.m_214019_(), this.m_214020_());
        List<LivingEntity> $$3 = p_26710_.m_6443_(LivingEntity.class, $$2, p_26717_ -> p_26717_ != p_26711_ && p_26717_.m_6084_());
        $$3.sort(Comparator.comparingDouble(arg_0 -> p_26711_.m_20280_(arg_0)));
        Brain<?> $$4 = ((LivingEntity)p_26711_).m_6274_();
        $$4.m_21879_(MemoryModuleType.f_148204_, $$3);
        $$4.m_21879_(MemoryModuleType.f_148205_, new NearestVisibleLivingEntities((LivingEntity)p_26711_, $$3));
    }

    protected int m_214020_() {
        return 16;
    }

    protected int m_214019_() {
        return 16;
    }

    @Override
    public Set<MemoryModuleType<?>> m_7163_() {
        return ImmutableSet.of(MemoryModuleType.f_148204_, MemoryModuleType.f_148205_);
    }
}

