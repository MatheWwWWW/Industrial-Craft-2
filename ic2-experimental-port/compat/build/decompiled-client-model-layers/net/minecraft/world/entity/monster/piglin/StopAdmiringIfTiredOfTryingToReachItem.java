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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.piglin.Piglin;

public class StopAdmiringIfTiredOfTryingToReachItem<E extends Piglin>
extends Behavior<E> {
    private final int f_35227_;
    private final int f_35228_;

    public StopAdmiringIfTiredOfTryingToReachItem(int p_35230_, int p_35231_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26336_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26332_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26337_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26338_, (Object)((Object)MemoryStatus.REGISTERED)));
        this.f_35227_ = p_35230_;
        this.f_35228_ = p_35231_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_35240_, E p_35241_) {
        return ((LivingEntity)p_35241_).m_21206_().m_41619_();
    }

    @Override
    protected void m_6735_(ServerLevel p_35243_, E p_35244_, long p_35245_) {
        Brain<Piglin> $$3 = ((Piglin)p_35244_).m_6274_();
        Optional<Integer> $$4 = $$3.m_21952_(MemoryModuleType.f_26337_);
        if (!$$4.isPresent()) {
            $$3.m_21879_(MemoryModuleType.f_26337_, 0);
        } else {
            int $$5 = $$4.get();
            if ($$5 > this.f_35227_) {
                $$3.m_21936_(MemoryModuleType.f_26336_);
                $$3.m_21936_(MemoryModuleType.f_26337_);
                $$3.m_21882_(MemoryModuleType.f_26338_, true, this.f_35228_);
            } else {
                $$3.m_21879_(MemoryModuleType.f_26337_, $$5 + 1);
            }
        }
    }
}

