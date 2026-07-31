/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class UpdateActivityFromSchedule
extends Behavior<LivingEntity> {
    public UpdateActivityFromSchedule() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of());
    }

    @Override
    protected void m_6735_(ServerLevel p_24458_, LivingEntity p_24459_, long p_24460_) {
        p_24459_.m_6274_().m_21862_(p_24458_.m_46468_(), p_24458_.m_46467_());
    }
}

