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

public class Digging<E extends Warden>
extends Behavior<E> {
    public Digging(int p_217515_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), p_217515_);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_217527_, E p_217528_, long p_217529_) {
        return ((Entity)p_217528_).m_146911_() == null;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_217524_, E p_217525_) {
        return ((Entity)p_217525_).m_20096_() || ((Entity)p_217525_).m_20069_() || ((Entity)p_217525_).m_20077_();
    }

    @Override
    protected void m_6735_(ServerLevel p_217535_, E p_217536_, long p_217537_) {
        if (((Entity)p_217536_).m_20096_()) {
            ((Entity)p_217536_).m_20124_(Pose.DIGGING);
            ((Entity)p_217536_).m_5496_(SoundEvents.f_215780_, 5.0f, 1.0f);
        } else {
            ((Entity)p_217536_).m_5496_(SoundEvents.f_215775_, 5.0f, 1.0f);
            this.m_6732_(p_217535_, p_217536_, p_217537_);
        }
    }

    @Override
    protected void m_6732_(ServerLevel p_217543_, E p_217544_, long p_217545_) {
        if (((Entity)p_217544_).m_146911_() == null) {
            ((Entity)p_217544_).m_142687_(Entity.RemovalReason.DISCARDED);
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

