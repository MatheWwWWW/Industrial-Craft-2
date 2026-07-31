/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.Piglin;

public class StopAdmiringIfItemTooFarAway<E extends Piglin>
extends Behavior<E> {
    private final int f_35210_;

    public StopAdmiringIfItemTooFarAway(int p_35212_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26336_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26332_, (Object)((Object)MemoryStatus.REGISTERED)));
        this.f_35210_ = p_35212_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_35221_, E p_35222_) {
        if (!((LivingEntity)p_35222_).m_21206_().m_41619_()) {
            return false;
        }
        Optional<ItemEntity> $$2 = ((Piglin)p_35222_).m_6274_().m_21952_(MemoryModuleType.f_26332_);
        if (!$$2.isPresent()) {
            return true;
        }
        return !$$2.get().m_19950_((Entity)p_35222_, this.f_35210_);
    }

    @Override
    protected void m_6735_(ServerLevel p_35224_, E p_35225_, long p_35226_) {
        ((Piglin)p_35225_).m_6274_().m_21936_(MemoryModuleType.f_26336_);
    }
}

