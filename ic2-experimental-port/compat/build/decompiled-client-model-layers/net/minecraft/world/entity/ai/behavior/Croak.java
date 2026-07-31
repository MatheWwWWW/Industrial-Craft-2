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
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.frog.Frog;

public class Croak
extends Behavior<Frog> {
    private static final int f_217139_ = 60;
    private static final int f_217140_ = 100;
    private int f_217141_;

    public Croak() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT)), 100);
    }

    @Override
    protected boolean m_6114_(ServerLevel p_217151_, Frog p_217152_) {
        return p_217152_.m_20089_() == Pose.STANDING;
    }

    @Override
    protected boolean m_6737_(ServerLevel p_217154_, Frog p_217155_, long p_217156_) {
        return this.f_217141_ < 60;
    }

    @Override
    protected void m_6735_(ServerLevel p_217162_, Frog p_217163_, long p_217164_) {
        if (p_217163_.m_20072_() || p_217163_.m_20077_()) {
            return;
        }
        p_217163_.m_20124_(Pose.CROAKING);
        this.f_217141_ = 0;
    }

    @Override
    protected void m_6732_(ServerLevel p_217170_, Frog p_217171_, long p_217172_) {
        p_217171_.m_20124_(Pose.STANDING);
    }

    @Override
    protected void m_6725_(ServerLevel p_217178_, Frog p_217179_, long p_217180_) {
        ++this.f_217141_;
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Frog)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (Frog)livingEntity, l);
    }
}

