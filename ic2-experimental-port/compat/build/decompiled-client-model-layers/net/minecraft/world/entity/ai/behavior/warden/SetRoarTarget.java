/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior.warden;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.warden.Warden;

public class SetRoarTarget<E extends Warden>
extends Behavior<E> {
    private final Function<E, Optional<? extends LivingEntity>> f_217607_;

    public SetRoarTarget(Function<E, Optional<? extends LivingEntity>> p_217609_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_217782_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26326_, (Object)((Object)MemoryStatus.REGISTERED)));
        this.f_217607_ = p_217609_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_217618_, E p_217619_) {
        return this.f_217607_.apply(p_217619_).filter(arg_0 -> p_217619_.m_219385_(arg_0)).isPresent();
    }

    @Override
    protected void m_6735_(ServerLevel p_217621_, E p_217622_, long p_217623_) {
        this.f_217607_.apply(p_217622_).ifPresent(p_217626_ -> {
            p_217622_.m_6274_().m_21879_(MemoryModuleType.f_217782_, p_217626_);
            p_217622_.m_6274_().m_21936_(MemoryModuleType.f_26326_);
        });
    }
}

