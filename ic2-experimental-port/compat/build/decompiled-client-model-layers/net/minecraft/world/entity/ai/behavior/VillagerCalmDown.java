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
import net.minecraft.world.entity.ai.behavior.VillagerPanicTrigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;

public class VillagerCalmDown
extends Behavior<Villager> {
    private static final int f_148039_ = 36;

    public VillagerCalmDown() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of());
    }

    @Override
    protected void m_6735_(ServerLevel p_24574_, Villager p_24575_, long p_24576_) {
        boolean $$3;
        boolean bl = $$3 = VillagerPanicTrigger.m_24697_(p_24575_) || VillagerPanicTrigger.m_24687_(p_24575_) || VillagerCalmDown.m_24577_(p_24575_);
        if (!$$3) {
            p_24575_.m_6274_().m_21936_(MemoryModuleType.f_26381_);
            p_24575_.m_6274_().m_21936_(MemoryModuleType.f_26382_);
            p_24575_.m_6274_().m_21862_(p_24574_.m_46468_(), p_24574_.m_46467_());
        }
    }

    private static boolean m_24577_(Villager p_24578_) {
        return p_24578_.m_6274_().m_21952_(MemoryModuleType.f_26382_).filter(p_24581_ -> p_24581_.m_20280_(p_24578_) <= 36.0).isPresent();
    }
}

