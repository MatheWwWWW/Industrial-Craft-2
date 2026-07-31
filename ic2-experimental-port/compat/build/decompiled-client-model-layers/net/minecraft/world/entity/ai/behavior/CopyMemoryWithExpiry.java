/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class CopyMemoryWithExpiry<E extends Mob, T>
extends Behavior<E> {
    private final Predicate<E> f_22719_;
    private final MemoryModuleType<? extends T> f_22720_;
    private final MemoryModuleType<T> f_22721_;
    private final UniformInt f_22722_;

    public CopyMemoryWithExpiry(Predicate<E> p_147456_, MemoryModuleType<? extends T> p_147457_, MemoryModuleType<T> p_147458_, UniformInt p_147459_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(p_147457_, (Object)((Object)MemoryStatus.VALUE_PRESENT), p_147458_, (Object)((Object)MemoryStatus.VALUE_ABSENT)));
        this.f_22719_ = p_147456_;
        this.f_22720_ = p_147457_;
        this.f_22721_ = p_147458_;
        this.f_22722_ = p_147459_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_22736_, E p_22737_) {
        return this.f_22719_.test(p_22737_);
    }

    @Override
    protected void m_6735_(ServerLevel p_22739_, E p_22740_, long p_22741_) {
        Brain<?> $$3 = ((LivingEntity)p_22740_).m_6274_();
        $$3.m_21882_(this.f_22721_, $$3.m_21952_(this.f_22720_).get(), this.f_22722_.m_214085_(p_22739_.f_46441_));
    }
}

