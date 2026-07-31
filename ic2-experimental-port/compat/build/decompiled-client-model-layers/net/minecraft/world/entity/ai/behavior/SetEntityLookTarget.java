/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;

public class SetEntityLookTarget
extends Behavior<LivingEntity> {
    private final Predicate<LivingEntity> f_23889_;
    private final float f_23890_;
    private Optional<LivingEntity> f_186050_ = Optional.empty();

    public SetEntityLookTarget(TagKey<EntityType<?>> p_204047_, float p_204048_) {
        this((LivingEntity p_204051_) -> p_204051_.m_6095_().m_204039_(p_204047_), p_204048_);
    }

    public SetEntityLookTarget(MobCategory p_23897_, float p_23898_) {
        this((LivingEntity p_23923_) -> p_23897_.equals(p_23923_.m_6095_().m_20674_()), p_23898_);
    }

    public SetEntityLookTarget(EntityType<?> p_23894_, float p_23895_) {
        this((LivingEntity p_23911_) -> p_23894_.equals(p_23911_.m_6095_()), p_23895_);
    }

    public SetEntityLookTarget(float p_23892_) {
        this((LivingEntity p_23913_) -> true, p_23892_);
    }

    public SetEntityLookTarget(Predicate<LivingEntity> p_23900_, float p_23901_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_148205_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        this.f_23889_ = p_23900_;
        this.f_23890_ = p_23901_ * p_23901_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23903_, LivingEntity p_23904_) {
        NearestVisibleLivingEntities $$2 = p_23904_.m_6274_().m_21952_(MemoryModuleType.f_148205_).get();
        this.f_186050_ = $$2.m_186116_(this.f_23889_.and(p_186053_ -> p_186053_.m_20280_(p_23904_) <= (double)this.f_23890_));
        return this.f_186050_.isPresent();
    }

    @Override
    protected void m_6735_(ServerLevel p_23906_, LivingEntity p_23907_, long p_23908_) {
        p_23907_.m_6274_().m_21879_(MemoryModuleType.f_26371_, new EntityTracker(this.f_186050_.get(), true));
        this.f_186050_ = Optional.empty();
    }
}

