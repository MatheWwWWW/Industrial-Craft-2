/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior.warden;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class ForceUnmount
extends Behavior<LivingEntity> {
    public ForceUnmount() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of());
    }

    @Override
    protected boolean m_6114_(ServerLevel p_238424_, LivingEntity p_238425_) {
        return p_238425_.m_20159_();
    }

    @Override
    protected void m_6735_(ServerLevel p_238410_, LivingEntity p_238411_, long p_238412_) {
        p_238411_.m_19877_();
    }
}

