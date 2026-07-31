/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.BiPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class DismountOrSkipMounting<E extends LivingEntity, T extends Entity>
extends Behavior<E> {
    private final int f_22824_;
    private final BiPredicate<E, Entity> f_22825_;

    public DismountOrSkipMounting(int p_22827_, BiPredicate<E, Entity> p_22828_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26376_, (Object)((Object)MemoryStatus.REGISTERED)));
        this.f_22824_ = p_22827_;
        this.f_22825_ = p_22828_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_22830_, E p_22831_) {
        Entity $$2 = ((Entity)p_22831_).m_20202_();
        Entity $$3 = ((LivingEntity)p_22831_).m_6274_().m_21952_(MemoryModuleType.f_26376_).orElse(null);
        if ($$2 == null && $$3 == null) {
            return false;
        }
        Entity $$4 = $$2 == null ? $$3 : $$2;
        return !this.m_22836_(p_22831_, $$4) || this.f_22825_.test(p_22831_, $$4);
    }

    private boolean m_22836_(E p_22837_, Entity p_22838_) {
        return p_22838_.m_6084_() && p_22838_.m_19950_((Entity)p_22837_, this.f_22824_) && p_22838_.f_19853_ == ((LivingEntity)p_22837_).f_19853_;
    }

    @Override
    protected void m_6735_(ServerLevel p_22833_, E p_22834_, long p_22835_) {
        ((LivingEntity)p_22834_).m_8127_();
        ((LivingEntity)p_22834_).m_6274_().m_21936_(MemoryModuleType.f_26376_);
    }
}

