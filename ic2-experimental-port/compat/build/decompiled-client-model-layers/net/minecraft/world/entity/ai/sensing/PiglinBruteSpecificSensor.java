/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;

public class PiglinBruteSpecificSensor
extends Sensor<LivingEntity> {
    @Override
    public Set<MemoryModuleType<?>> m_7163_() {
        return ImmutableSet.of(MemoryModuleType.f_148205_, MemoryModuleType.f_26333_, MemoryModuleType.f_26346_);
    }

    @Override
    protected void m_5578_(ServerLevel p_26721_, LivingEntity p_26722_) {
        Brain<?> $$2 = p_26722_.m_6274_();
        ArrayList $$3 = Lists.newArrayList();
        NearestVisibleLivingEntities $$4 = $$2.m_21952_(MemoryModuleType.f_148205_).orElse(NearestVisibleLivingEntities.m_186106_());
        Optional<Mob> $$5 = $$4.m_186116_(p_186155_ -> p_186155_ instanceof WitherSkeleton || p_186155_ instanceof WitherBoss).map(Mob.class::cast);
        List<LivingEntity> $$6 = $$2.m_21952_(MemoryModuleType.f_148204_).orElse((List<LivingEntity>)ImmutableList.of());
        for (LivingEntity $$7 : $$6) {
            if (!($$7 instanceof AbstractPiglin) || !((AbstractPiglin)$$7).m_34667_()) continue;
            $$3.add((AbstractPiglin)$$7);
        }
        $$2.m_21886_(MemoryModuleType.f_26333_, $$5);
        $$2.m_21879_(MemoryModuleType.f_26346_, $$3);
    }
}

