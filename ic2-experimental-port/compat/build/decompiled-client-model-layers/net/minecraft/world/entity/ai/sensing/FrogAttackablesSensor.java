/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.sensing;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.animal.frog.Frog;

public class FrogAttackablesSensor
extends NearestVisibleLivingEntitySensor {
    public static final float f_217807_ = 10.0f;

    @Override
    protected boolean m_142628_(LivingEntity p_217810_, LivingEntity p_217811_) {
        if (!p_217810_.m_6274_().m_21874_(MemoryModuleType.f_148201_) && Sensor.m_148312_(p_217810_, p_217811_) && Frog.m_218532_(p_217811_) && !this.m_238335_(p_217810_, p_217811_)) {
            return p_217811_.m_19950_(p_217810_, 10.0);
        }
        return false;
    }

    private boolean m_238335_(LivingEntity p_238336_, LivingEntity p_238337_) {
        List $$2 = p_238336_.m_6274_().m_21952_(MemoryModuleType.f_238182_).orElseGet(ArrayList::new);
        return $$2.contains(p_238337_.m_20148_());
    }

    @Override
    protected MemoryModuleType<LivingEntity> m_142149_() {
        return MemoryModuleType.f_148194_;
    }
}

