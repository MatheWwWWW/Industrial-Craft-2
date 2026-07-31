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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.schedule.Activity;

public class ReactToBell
extends Behavior<LivingEntity> {
    public ReactToBell() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26325_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
    }

    @Override
    protected void m_6735_(ServerLevel p_23761_, LivingEntity p_23762_, long p_23763_) {
        Brain<?> $$3 = p_23762_.m_6274_();
        Raid $$4 = p_23761_.m_8832_(p_23762_.m_20183_());
        if ($$4 == null) {
            $$3.m_21889_(Activity.f_37987_);
        }
    }
}

