/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior.warden;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.warden.Warden;

public class Emerging<E extends Warden>
extends Behavior<E> {
    public Emerging(int p_217547_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_217786_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED)), p_217547_);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_217553_, E p_217554_, long p_217555_) {
        return true;
    }

    @Override
    protected void m_6735_(ServerLevel p_217561_, E p_217562_, long p_217563_) {
        ((Entity)p_217562_).m_20124_(Pose.EMERGING);
        ((Entity)p_217562_).m_5496_(SoundEvents.f_215781_, 5.0f, 1.0f);
    }

    @Override
    protected void m_6732_(ServerLevel p_217569_, E p_217570_, long p_217571_) {
        if (((Entity)p_217570_).m_217003_(Pose.EMERGING)) {
            ((Entity)p_217570_).m_20124_(Pose.STANDING);
        }
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (E)((Warden)livingEntity), l);
    }

    @Override
    protected /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (E)((Warden)livingEntity), l);
    }
}

