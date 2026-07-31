/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class Mount<E extends LivingEntity>
extends Behavior<E> {
    private static final int f_147698_ = 1;
    private final float f_23534_;

    public Mount(float p_23536_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26376_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        this.f_23534_ = p_23536_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23538_, E p_23539_) {
        return !((Entity)p_23539_).m_20159_();
    }

    @Override
    protected void m_6735_(ServerLevel p_23541_, E p_23542_, long p_23543_) {
        if (this.m_23544_(p_23542_)) {
            ((Entity)p_23542_).m_20329_(this.m_23546_(p_23542_));
        } else {
            BehaviorUtils.m_22590_(p_23542_, this.m_23546_(p_23542_), this.f_23534_, 1);
        }
    }

    private boolean m_23544_(E p_23545_) {
        return this.m_23546_(p_23545_).m_19950_((Entity)p_23545_, 1.0);
    }

    private Entity m_23546_(E p_23547_) {
        return ((LivingEntity)p_23547_).m_6274_().m_21952_(MemoryModuleType.f_26376_).get();
    }
}

