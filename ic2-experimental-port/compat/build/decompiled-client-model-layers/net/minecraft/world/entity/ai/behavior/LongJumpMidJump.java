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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class LongJumpMidJump
extends Behavior<Mob> {
    public static final int f_147592_ = 100;
    private final UniformInt f_147593_;
    private SoundEvent f_147594_;

    public LongJumpMidJump(UniformInt p_147596_, SoundEvent p_147597_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_148200_, (Object)((Object)MemoryStatus.VALUE_PRESENT)), 100);
        this.f_147593_ = p_147596_;
        this.f_147594_ = p_147597_;
    }

    @Override
    protected boolean m_6737_(ServerLevel p_147603_, Mob p_147604_, long p_147605_) {
        return !p_147604_.m_20096_();
    }

    @Override
    protected void m_6735_(ServerLevel p_147611_, Mob p_147612_, long p_147613_) {
        p_147612_.m_147244_(true);
        p_147612_.m_20124_(Pose.LONG_JUMPING);
    }

    @Override
    protected void m_6732_(ServerLevel p_147619_, Mob p_147620_, long p_147621_) {
        if (p_147620_.m_20096_()) {
            p_147620_.m_20256_(p_147620_.m_20184_().m_82542_(0.1f, 1.0, 0.1f));
            p_147619_.m_6269_(null, p_147620_, this.f_147594_, SoundSource.NEUTRAL, 2.0f, 1.0f);
        }
        p_147620_.m_147244_(false);
        p_147620_.m_20124_(Pose.STANDING);
        p_147620_.m_6274_().m_21936_(MemoryModuleType.f_148200_);
        p_147620_.m_6274_().m_21879_(MemoryModuleType.f_148199_, this.f_147593_.m_214085_(p_147619_.f_46441_));
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Mob)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (Mob)livingEntity, l);
    }
}

