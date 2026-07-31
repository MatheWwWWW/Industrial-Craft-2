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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class CountDownCooldownTicks
extends Behavior<LivingEntity> {
    private final MemoryModuleType<Integer> f_147460_;

    public CountDownCooldownTicks(MemoryModuleType<Integer> p_147462_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(p_147462_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        this.f_147460_ = p_147462_;
    }

    private Optional<Integer> m_147465_(LivingEntity p_147466_) {
        return p_147466_.m_6274_().m_21952_(this.f_147460_);
    }

    @Override
    protected boolean m_7773_(long p_147464_) {
        return false;
    }

    @Override
    protected boolean m_6737_(ServerLevel p_147468_, LivingEntity p_147469_, long p_147470_) {
        Optional<Integer> $$3 = this.m_147465_(p_147469_);
        return $$3.isPresent() && $$3.get() > 0;
    }

    @Override
    protected void m_6725_(ServerLevel p_147476_, LivingEntity p_147477_, long p_147478_) {
        Optional<Integer> $$3 = this.m_147465_(p_147477_);
        p_147477_.m_6274_().m_21879_(this.f_147460_, $$3.get() - 1);
    }

    @Override
    protected void m_6732_(ServerLevel p_147472_, LivingEntity p_147473_, long p_147474_) {
        p_147473_.m_6274_().m_21936_(this.f_147460_);
    }
}

