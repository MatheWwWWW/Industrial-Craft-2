/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;

public class VillagerHostilesSensor
extends NearestVisibleLivingEntitySensor {
    private static final ImmutableMap<EntityType<?>, Float> f_26842_ = ImmutableMap.builder().put(EntityType.f_20562_, (Object)Float.valueOf(8.0f)).put(EntityType.f_20568_, (Object)Float.valueOf(12.0f)).put(EntityType.f_20458_, (Object)Float.valueOf(8.0f)).put(EntityType.f_20459_, (Object)Float.valueOf(12.0f)).put(EntityType.f_20513_, (Object)Float.valueOf(15.0f)).put(EntityType.f_20518_, (Object)Float.valueOf(12.0f)).put(EntityType.f_20491_, (Object)Float.valueOf(8.0f)).put(EntityType.f_20493_, (Object)Float.valueOf(10.0f)).put(EntityType.f_20500_, (Object)Float.valueOf(10.0f)).put(EntityType.f_20501_, (Object)Float.valueOf(8.0f)).put(EntityType.f_20530_, (Object)Float.valueOf(8.0f)).build();

    @Override
    protected boolean m_142628_(LivingEntity p_148344_, LivingEntity p_148345_) {
        return this.m_26867_(p_148345_) && this.m_26860_(p_148344_, p_148345_);
    }

    private boolean m_26860_(LivingEntity p_26861_, LivingEntity p_26862_) {
        float $$2 = ((Float)f_26842_.get(p_26862_.m_6095_())).floatValue();
        return p_26862_.m_20280_(p_26861_) <= (double)($$2 * $$2);
    }

    @Override
    protected MemoryModuleType<LivingEntity> m_142149_() {
        return MemoryModuleType.f_26323_;
    }

    private boolean m_26867_(LivingEntity p_26868_) {
        return f_26842_.containsKey(p_26868_.m_6095_());
    }
}

