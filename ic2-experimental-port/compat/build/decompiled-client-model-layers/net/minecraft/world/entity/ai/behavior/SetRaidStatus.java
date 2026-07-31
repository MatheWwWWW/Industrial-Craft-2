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

public class SetRaidStatus
extends Behavior<LivingEntity> {
    public SetRaidStatus() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of());
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23976_, LivingEntity p_23977_) {
        return p_23976_.f_46441_.m_188503_(20) == 0;
    }

    @Override
    protected void m_6735_(ServerLevel p_23979_, LivingEntity p_23980_, long p_23981_) {
        Brain<?> $$3 = p_23980_.m_6274_();
        Raid $$4 = p_23979_.m_8832_(p_23980_.m_20183_());
        if ($$4 != null) {
            if (!$$4.m_37757_() || $$4.m_37749_()) {
                $$3.m_21944_(Activity.f_37986_);
                $$3.m_21889_(Activity.f_37986_);
            } else {
                $$3.m_21944_(Activity.f_37985_);
                $$3.m_21889_(Activity.f_37985_);
            }
        }
    }
}

