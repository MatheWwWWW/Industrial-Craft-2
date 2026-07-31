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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.Items;

public class StopHoldingItemIfNoLongerAdmiring<E extends Piglin>
extends Behavior<E> {
    public StopHoldingItemIfNoLongerAdmiring() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26336_, (Object)((Object)MemoryStatus.VALUE_ABSENT)));
    }

    @Override
    protected boolean m_6114_(ServerLevel p_35255_, E p_35256_) {
        return !((LivingEntity)p_35256_).m_21206_().m_41619_() && !((LivingEntity)p_35256_).m_21206_().m_150930_(Items.f_42740_);
    }

    @Override
    protected void m_6735_(ServerLevel p_35258_, E p_35259_, long p_35260_) {
        PiglinAi.m_34867_(p_35259_, true);
    }
}

