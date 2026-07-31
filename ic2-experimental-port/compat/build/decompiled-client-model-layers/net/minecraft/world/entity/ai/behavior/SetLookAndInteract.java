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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;

public class SetLookAndInteract
extends Behavior<LivingEntity> {
    private final EntityType<?> f_23937_;
    private final int f_23938_;
    private final Predicate<LivingEntity> f_23939_;
    private final Predicate<LivingEntity> f_23940_;

    public SetLookAndInteract(EntityType<?> p_23945_, int p_23946_, Predicate<LivingEntity> p_23947_, Predicate<LivingEntity> p_23948_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26374_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_148205_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        this.f_23937_ = p_23945_;
        this.f_23938_ = p_23946_ * p_23946_;
        this.f_23939_ = p_23948_;
        this.f_23940_ = p_23947_;
    }

    public SetLookAndInteract(EntityType<?> p_23942_, int p_23943_) {
        this(p_23942_, p_23943_, p_23973_ -> true, p_23971_ -> true);
    }

    @Override
    public boolean m_6114_(ServerLevel p_23950_, LivingEntity p_23951_) {
        return this.f_23940_.test(p_23951_) && this.m_186060_(p_23951_).m_186130_(this::m_23956_);
    }

    @Override
    public void m_6735_(ServerLevel p_23953_, LivingEntity p_23954_, long p_23955_) {
        super.m_6735_(p_23953_, p_23954_, p_23955_);
        Brain<?> $$3 = p_23954_.m_6274_();
        $$3.m_21952_(MemoryModuleType.f_148205_).flatMap(p_186056_ -> p_186056_.m_186116_(p_147899_ -> p_147899_.m_20280_(p_23954_) <= (double)this.f_23938_ && this.m_23956_((LivingEntity)p_147899_))).ifPresent(p_186059_ -> {
            $$3.m_21879_(MemoryModuleType.f_26374_, p_186059_);
            $$3.m_21879_(MemoryModuleType.f_26371_, new EntityTracker((Entity)p_186059_, true));
        });
    }

    private boolean m_23956_(LivingEntity p_23957_) {
        return this.f_23937_.equals(p_23957_.m_6095_()) && this.f_23939_.test(p_23957_);
    }

    private NearestVisibleLivingEntities m_186060_(LivingEntity p_186061_) {
        return p_186061_.m_6274_().m_21952_(MemoryModuleType.f_148205_).get();
    }
}

