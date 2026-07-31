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
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenAi;

public class Roar
extends Behavior<Warden> {
    private static final int f_217572_ = 25;
    private static final int f_217573_ = 20;

    public Roar() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_217782_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_217771_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_217769_, (Object)((Object)MemoryStatus.REGISTERED)), WardenAi.f_219491_);
    }

    @Override
    protected void m_6735_(ServerLevel p_217580_, Warden p_217581_, long p_217582_) {
        Brain<Warden> $$3 = p_217581_.m_6274_();
        $$3.m_21882_(MemoryModuleType.f_217769_, Unit.INSTANCE, 25L);
        $$3.m_21936_(MemoryModuleType.f_26370_);
        LivingEntity $$4 = p_217581_.m_6274_().m_21952_(MemoryModuleType.f_217782_).get();
        BehaviorUtils.m_22595_(p_217581_, $$4);
        p_217581_.m_20124_(Pose.ROARING);
        p_217581_.m_219387_($$4, 20, false);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_217588_, Warden p_217589_, long p_217590_) {
        return true;
    }

    @Override
    protected void m_6725_(ServerLevel p_217596_, Warden p_217597_, long p_217598_) {
        if (p_217597_.m_6274_().m_21874_(MemoryModuleType.f_217769_) || p_217597_.m_6274_().m_21874_(MemoryModuleType.f_217771_)) {
            return;
        }
        p_217597_.m_6274_().m_21882_(MemoryModuleType.f_217771_, Unit.INSTANCE, WardenAi.f_219491_ - 25);
        p_217597_.m_5496_(SoundEvents.f_215769_, 3.0f, 1.0f);
    }

    @Override
    protected void m_6732_(ServerLevel p_217604_, Warden p_217605_, long p_217606_) {
        if (p_217605_.m_217003_(Pose.ROARING)) {
            p_217605_.m_20124_(Pose.STANDING);
        }
        p_217605_.m_6274_().m_21952_(MemoryModuleType.f_217782_).ifPresent(p_217605_::m_219459_);
        p_217605_.m_6274_().m_21936_(MemoryModuleType.f_217782_);
    }

    @Override
    protected /* synthetic */ void m_6732_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6732_(serverLevel, (Warden)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6725_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6725_(serverLevel, (Warden)livingEntity, l);
    }
}

