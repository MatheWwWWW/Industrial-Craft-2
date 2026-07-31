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
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;

public class ResetProfession
extends Behavior<Villager> {
    public ResetProfession() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26360_, (Object)((Object)MemoryStatus.VALUE_ABSENT)));
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23773_, Villager p_23774_) {
        VillagerData $$2 = p_23774_.m_7141_();
        return $$2.m_35571_() != VillagerProfession.f_35585_ && $$2.m_35571_() != VillagerProfession.f_35596_ && p_23774_.m_7809_() == 0 && $$2.m_35576_() <= 1;
    }

    @Override
    protected void m_6735_(ServerLevel p_23776_, Villager p_23777_, long p_23778_) {
        p_23777_.m_34375_(p_23777_.m_7141_().m_35565_(VillagerProfession.f_35585_));
        p_23777_.m_35483_(p_23776_);
    }
}

