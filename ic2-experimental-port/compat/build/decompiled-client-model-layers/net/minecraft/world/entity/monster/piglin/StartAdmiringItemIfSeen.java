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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;

public class StartAdmiringItemIfSeen<E extends Piglin>
extends Behavior<E> {
    private final int f_35138_;

    public StartAdmiringItemIfSeen(int p_35140_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26332_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26336_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26339_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26338_, (Object)((Object)MemoryStatus.VALUE_ABSENT)));
        this.f_35138_ = p_35140_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_35149_, E p_35150_) {
        ItemEntity $$2 = ((Piglin)p_35150_).m_6274_().m_21952_(MemoryModuleType.f_26332_).get();
        return PiglinAi.m_149965_($$2.m_32055_());
    }

    @Override
    protected void m_6735_(ServerLevel p_35152_, E p_35153_, long p_35154_) {
        ((Piglin)p_35153_).m_6274_().m_21882_(MemoryModuleType.f_26336_, true, this.f_35138_);
    }
}

