/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;

public class SecondaryPoiSensor
extends Sensor<Villager> {
    private static final int f_148305_ = 40;

    public SecondaryPoiSensor() {
        super(40);
    }

    @Override
    protected void m_5578_(ServerLevel p_26754_, Villager p_26755_) {
        ResourceKey<Level> $$2 = p_26754_.m_46472_();
        BlockPos $$3 = p_26755_.m_20183_();
        ArrayList $$4 = Lists.newArrayList();
        int $$5 = 4;
        for (int $$6 = -4; $$6 <= 4; ++$$6) {
            for (int $$7 = -2; $$7 <= 2; ++$$7) {
                for (int $$8 = -4; $$8 <= 4; ++$$8) {
                    BlockPos $$9 = $$3.m_7918_($$6, $$7, $$8);
                    if (!p_26755_.m_7141_().m_35571_().f_35603_().contains((Object)p_26754_.m_8055_($$9).m_60734_())) continue;
                    $$4.add(GlobalPos.m_122643_($$2, $$9));
                }
            }
        }
        Brain<Villager> $$10 = p_26755_.m_6274_();
        if (!$$4.isEmpty()) {
            $$10.m_21879_(MemoryModuleType.f_26363_, $$4);
        } else {
            $$10.m_21936_(MemoryModuleType.f_26363_);
        }
    }

    @Override
    public Set<MemoryModuleType<?>> m_7163_() {
        return ImmutableSet.of(MemoryModuleType.f_26363_);
    }
}

