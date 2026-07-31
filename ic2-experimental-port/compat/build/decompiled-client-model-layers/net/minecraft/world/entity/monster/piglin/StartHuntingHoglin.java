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
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;

public class StartHuntingHoglin<E extends Piglin>
extends Behavior<E> {
    public StartHuntingHoglin() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26343_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26334_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26340_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26347_, (Object)((Object)MemoryStatus.REGISTERED)));
    }

    @Override
    protected boolean m_6114_(ServerLevel p_35164_, Piglin p_35165_) {
        return !p_35165_.m_6162_() && !PiglinAi.m_34965_(p_35165_);
    }

    @Override
    protected void m_6735_(ServerLevel p_35167_, E p_35168_, long p_35169_) {
        Hoglin $$3 = ((Piglin)p_35168_).m_6274_().m_21952_(MemoryModuleType.f_26343_).get();
        PiglinAi.m_34924_(p_35168_, $$3);
        PiglinAi.m_34922_(p_35168_);
        PiglinAi.m_34895_(p_35168_, $$3);
        PiglinAi.m_34977_(p_35168_);
    }
}

