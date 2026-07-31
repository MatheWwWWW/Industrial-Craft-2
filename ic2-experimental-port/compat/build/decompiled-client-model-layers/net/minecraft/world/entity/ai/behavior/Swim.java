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
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class Swim
extends Behavior<Mob> {
    private final float f_24381_;

    public Swim(float p_24383_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of());
        this.f_24381_ = p_24383_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_24388_, Mob p_24389_) {
        return p_24389_.m_20069_() && p_24389_.m_204036_(FluidTags.f_13131_) > p_24389_.m_20204_() || p_24389_.m_20077_();
    }

    @Override
    protected boolean m_6737_(ServerLevel p_24391_, Mob p_24392_, long p_24393_) {
        return this.m_6114_(p_24391_, p_24392_);
    }

    @Override
    protected void m_6725_(ServerLevel p_24399_, Mob p_24400_, long p_24401_) {
        if (p_24400_.m_217043_().m_188501_() < this.f_24381_) {
            p_24400_.m_21569_().m_24901_();
        }
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Mob)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6725_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6725_(serverLevel, (Mob)livingEntity, l);
    }
}

