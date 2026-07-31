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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class LookAtTargetSink
extends Behavior<Mob> {
    public LookAtTargetSink(int p_23478_, int p_23479_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.VALUE_PRESENT)), p_23478_, p_23479_);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_23481_, Mob p_23482_, long p_23483_) {
        return p_23482_.m_6274_().m_21952_(MemoryModuleType.f_26371_).filter(p_23497_ -> p_23497_.m_6826_(p_23482_)).isPresent();
    }

    @Override
    protected void m_6732_(ServerLevel p_23492_, Mob p_23493_, long p_23494_) {
        p_23493_.m_6274_().m_21936_(MemoryModuleType.f_26371_);
    }

    @Override
    protected void m_6725_(ServerLevel p_23503_, Mob p_23504_, long p_23505_) {
        p_23504_.m_6274_().m_21952_(MemoryModuleType.f_26371_).ifPresent(p_23486_ -> p_23504_.m_21563_().m_24964_(p_23486_.m_7024_()));
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Mob)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6732_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6732_(serverLevel, (Mob)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6725_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6725_(serverLevel, (Mob)livingEntity, l);
    }
}

