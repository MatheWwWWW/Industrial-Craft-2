/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;

public class RememberIfHoglinWasKilled<E extends Piglin>
extends Behavior<E> {
    public RememberIfHoglinWasKilled() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26340_, (Object)((Object)MemoryStatus.REGISTERED)));
    }

    @Override
    protected void m_6735_(ServerLevel p_35133_, E p_35134_, long p_35135_) {
        if (this.m_35136_(p_35134_)) {
            PiglinAi.m_34922_(p_35134_);
        }
    }

    private boolean m_35136_(E p_35137_) {
        LivingEntity $$1 = ((Piglin)p_35137_).m_6274_().m_21952_(MemoryModuleType.f_26372_).get();
        return $$1.m_6095_() == EntityType.f_20456_ && $$1.m_21224_();
    }
}

