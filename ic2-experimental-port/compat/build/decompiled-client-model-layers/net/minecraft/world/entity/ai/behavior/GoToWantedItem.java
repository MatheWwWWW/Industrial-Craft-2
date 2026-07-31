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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.item.ItemEntity;

public class GoToWantedItem<E extends LivingEntity>
extends Behavior<E> {
    private final Predicate<E> f_23136_;
    private final int f_23137_;
    private final float f_23138_;

    public GoToWantedItem(float p_23140_, boolean p_23141_, int p_23142_) {
        this(p_23158_ -> true, p_23140_, p_23141_, p_23142_);
    }

    public GoToWantedItem(Predicate<E> p_23144_, float p_23145_, boolean p_23146_, int p_23147_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26370_, (Object)((Object)(p_23146_ ? MemoryStatus.REGISTERED : MemoryStatus.VALUE_ABSENT)), MemoryModuleType.f_26332_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        this.f_23136_ = p_23144_;
        this.f_23137_ = p_23147_;
        this.f_23138_ = p_23145_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23149_, E p_23150_) {
        return !this.m_217253_(p_23150_) && this.f_23136_.test(p_23150_) && this.m_23155_(p_23150_).m_19950_((Entity)p_23150_, this.f_23137_);
    }

    @Override
    protected void m_6735_(ServerLevel p_23152_, E p_23153_, long p_23154_) {
        BehaviorUtils.m_22590_(p_23153_, this.m_23155_(p_23153_), this.f_23138_, 0);
    }

    private boolean m_217253_(E p_217254_) {
        return ((LivingEntity)p_217254_).m_6274_().m_21876_(MemoryModuleType.f_217781_, MemoryStatus.VALUE_PRESENT);
    }

    private ItemEntity m_23155_(E p_23156_) {
        return ((LivingEntity)p_23156_).m_6274_().m_21952_(MemoryModuleType.f_26332_).get();
    }
}

