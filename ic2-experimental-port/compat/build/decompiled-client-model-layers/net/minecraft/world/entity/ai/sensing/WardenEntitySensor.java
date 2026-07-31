/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Iterables
 */
package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestLivingEntitySensor;
import net.minecraft.world.entity.monster.warden.Warden;

public class WardenEntitySensor
extends NearestLivingEntitySensor<Warden> {
    @Override
    public Set<MemoryModuleType<?>> m_7163_() {
        return ImmutableSet.copyOf((Iterable)Iterables.concat(super.m_7163_(), List.of(MemoryModuleType.f_148194_)));
    }

    @Override
    protected void m_5578_(ServerLevel p_217833_, Warden p_217834_) {
        super.m_5578_(p_217833_, p_217834_);
        WardenEntitySensor.m_217842_(p_217834_, p_217847_ -> p_217847_.m_6095_() == EntityType.f_20532_).or(() -> WardenEntitySensor.m_217842_(p_217834_, p_217836_ -> p_217836_.m_6095_() != EntityType.f_20532_)).ifPresentOrElse(p_217841_ -> p_217834_.m_6274_().m_21879_(MemoryModuleType.f_148194_, p_217841_), () -> p_217834_.m_6274_().m_21936_(MemoryModuleType.f_148194_));
    }

    private static Optional<LivingEntity> m_217842_(Warden p_217843_, Predicate<LivingEntity> p_217844_) {
        return p_217843_.m_6274_().m_21952_(MemoryModuleType.f_148204_).stream().flatMap(Collection::stream).filter(p_217843_::m_219385_).filter(p_217844_).findFirst();
    }

    @Override
    protected int m_214020_() {
        return 24;
    }

    @Override
    protected int m_214019_() {
        return 24;
    }
}

