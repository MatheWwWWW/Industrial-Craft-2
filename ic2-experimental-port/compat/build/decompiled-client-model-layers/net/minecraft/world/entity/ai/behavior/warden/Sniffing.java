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
import net.minecraft.world.entity.monster.warden.WardenAi;

public class Sniffing<E extends Warden>
extends Behavior<E> {
    private static final double f_217644_ = 6.0;
    private static final double f_217645_ = 20.0;

    public Sniffing(int p_217647_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_217785_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_148194_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_217783_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_217772_, (Object)((Object)MemoryStatus.REGISTERED)), p_217647_);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_217653_, E p_217654_, long p_217655_) {
        return true;
    }

    @Override
    protected void m_6735_(ServerLevel p_217664_, E p_217665_, long p_217666_) {
        ((Entity)p_217665_).m_5496_(SoundEvents.f_215770_, 5.0f, 1.0f);
    }

    @Override
    protected void m_6732_(ServerLevel p_217672_, E p_217673_, long p_217674_) {
        if (((Entity)p_217673_).m_217003_(Pose.SNIFFING)) {
            ((Entity)p_217673_).m_20124_(Pose.STANDING);
        }
        ((Warden)p_217673_).m_6274_().m_21936_(MemoryModuleType.f_217785_);
        ((Warden)p_217673_).m_6274_().m_21952_(MemoryModuleType.f_148194_).filter(arg_0 -> p_217673_.m_219385_(arg_0)).ifPresent(p_217658_ -> {
            if (p_217673_.m_216992_((Entity)p_217658_, 6.0, 20.0)) {
                p_217673_.m_219441_((Entity)p_217658_);
            }
            if (!p_217673_.m_6274_().m_21874_(MemoryModuleType.f_217783_)) {
                WardenAi.m_219523_(p_217673_, p_217658_.m_20183_());
            }
        });
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

