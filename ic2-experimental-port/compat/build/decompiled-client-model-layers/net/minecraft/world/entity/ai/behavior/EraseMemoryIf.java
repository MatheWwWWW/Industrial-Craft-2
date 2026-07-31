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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class EraseMemoryIf<E extends LivingEntity>
extends Behavior<E> {
    private final Predicate<E> f_22856_;
    private final MemoryModuleType<?> f_22857_;

    public EraseMemoryIf(Predicate<E> p_22859_, MemoryModuleType<?> p_22860_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(p_22860_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        this.f_22856_ = p_22859_;
        this.f_22857_ = p_22860_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_22862_, E p_22863_) {
        return this.f_22856_.test(p_22863_);
    }

    @Override
    protected void m_6735_(ServerLevel p_22865_, E p_22866_, long p_22867_) {
        ((LivingEntity)p_22866_).m_6274_().m_21936_(this.f_22857_);
    }
}

