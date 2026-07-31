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

public class ResetRaidStatus
extends Behavior<LivingEntity> {
    public ResetRaidStatus() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of());
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23781_, LivingEntity p_23782_) {
        return p_23781_.f_46441_.m_188503_(20) == 0;
    }

    @Override
    protected void m_6735_(ServerLevel p_23784_, LivingEntity p_23785_, long p_23786_) {
        Brain<?> $$3 = p_23785_.m_6274_();
        Raid $$4 = p_23784_.m_8832_(p_23785_.m_20183_());
        if ($$4 == null || $$4.m_37762_() || $$4.m_37768_()) {
            $$3.m_21944_(Activity.f_37979_);
            $$3.m_21862_(p_23784_.m_46468_(), p_23784_.m_46467_());
        }
    }
}

