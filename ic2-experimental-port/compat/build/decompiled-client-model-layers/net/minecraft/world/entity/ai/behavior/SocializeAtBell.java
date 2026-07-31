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
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class SocializeAtBell
extends Behavior<LivingEntity> {
    private static final float f_147969_ = 0.3f;

    public SocializeAtBell() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26362_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_148205_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26374_, (Object)((Object)MemoryStatus.VALUE_ABSENT)));
    }

    @Override
    protected boolean m_6114_(ServerLevel p_24170_, LivingEntity p_24171_) {
        Brain<?> $$2 = p_24171_.m_6274_();
        Optional<GlobalPos> $$3 = $$2.m_21952_(MemoryModuleType.f_26362_);
        return p_24170_.m_213780_().m_188503_(100) == 0 && $$3.isPresent() && p_24170_.m_46472_() == $$3.get().m_122640_() && $$3.get().m_122646_().m_203195_(p_24171_.m_20182_(), 4.0) && $$2.m_21952_(MemoryModuleType.f_148205_).get().m_186130_(p_24189_ -> EntityType.f_20492_.equals(p_24189_.m_6095_()));
    }

    @Override
    protected void m_6735_(ServerLevel p_24173_, LivingEntity p_24174_, long p_24175_) {
        Brain<?> $$3 = p_24174_.m_6274_();
        $$3.m_21952_(MemoryModuleType.f_148205_).flatMap(p_186067_ -> p_186067_.m_186116_(p_186064_ -> EntityType.f_20492_.equals(p_186064_.m_6095_()) && p_186064_.m_20280_(p_24174_) <= 32.0)).ifPresent(p_147977_ -> {
            $$3.m_21879_(MemoryModuleType.f_26374_, p_147977_);
            $$3.m_21879_(MemoryModuleType.f_26371_, new EntityTracker((Entity)p_147977_, true));
            $$3.m_21879_(MemoryModuleType.f_26370_, new WalkTarget(new EntityTracker((Entity)p_147977_, false), 0.3f, 1));
        });
    }
}

