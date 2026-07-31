/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior.warden;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.warden.Warden;

public class SetWardenLookTarget
extends Behavior<Warden> {
    public SetWardenLookTarget() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_217783_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_217782_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_ABSENT)));
    }

    @Override
    protected boolean m_6114_(ServerLevel p_217636_, Warden p_217637_) {
        return p_217637_.m_6274_().m_21874_(MemoryModuleType.f_217783_) || p_217637_.m_6274_().m_21874_(MemoryModuleType.f_217782_);
    }

    @Override
    protected void m_6735_(ServerLevel p_217639_, Warden p_217640_, long p_217641_) {
        BlockPos $$3 = p_217640_.m_6274_().m_21952_(MemoryModuleType.f_217782_).map(Entity::m_20183_).or(() -> p_217640_.m_6274_().m_21952_(MemoryModuleType.f_217783_)).get();
        p_217640_.m_6274_().m_21879_(MemoryModuleType.f_26371_, new BlockPosTracker($$3));
    }
}

