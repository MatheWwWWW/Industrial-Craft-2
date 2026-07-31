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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class HurtBySensor
extends Sensor<LivingEntity> {
    @Override
    public Set<MemoryModuleType<?>> m_7163_() {
        return ImmutableSet.of(MemoryModuleType.f_26381_, MemoryModuleType.f_26382_);
    }

    @Override
    protected void m_5578_(ServerLevel p_26670_, LivingEntity p_26671_) {
        Brain<?> $$2 = p_26671_.m_6274_();
        DamageSource $$3 = p_26671_.m_21225_();
        if ($$3 != null) {
            $$2.m_21879_(MemoryModuleType.f_26381_, p_26671_.m_21225_());
            Entity $$4 = $$3.m_7639_();
            if ($$4 instanceof LivingEntity) {
                $$2.m_21879_(MemoryModuleType.f_26382_, (LivingEntity)$$4);
            }
        } else {
            $$2.m_21936_(MemoryModuleType.f_26381_);
        }
        $$2.m_21952_(MemoryModuleType.f_26382_).ifPresent(p_26675_ -> {
            if (!p_26675_.m_6084_() || p_26675_.f_19853_ != p_26670_) {
                $$2.m_21936_(MemoryModuleType.f_26382_);
            }
        });
    }
}

