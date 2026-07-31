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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class BackUpIfTooClose<E extends Mob>
extends Behavior<E> {
    private final int f_22489_;
    private final float f_22490_;

    public BackUpIfTooClose(int p_22492_, float p_22493_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_148205_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        this.f_22489_ = p_22492_;
        this.f_22490_ = p_22493_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_22502_, E p_22503_) {
        return this.m_22508_(p_22503_) && this.m_22510_(p_22503_);
    }

    @Override
    protected void m_6735_(ServerLevel p_22505_, E p_22506_, long p_22507_) {
        ((LivingEntity)p_22506_).m_6274_().m_21879_(MemoryModuleType.f_26371_, new EntityTracker(this.m_22512_(p_22506_), true));
        ((Mob)p_22506_).m_21566_().m_24988_(-this.f_22490_, 0.0f);
        ((Entity)p_22506_).m_146922_(Mth.m_14094_(((Entity)p_22506_).m_146908_(), ((Mob)p_22506_).f_20885_, 0.0f));
    }

    private boolean m_22508_(E p_22509_) {
        return ((LivingEntity)p_22509_).m_6274_().m_21952_(MemoryModuleType.f_148205_).get().m_186107_(this.m_22512_(p_22509_));
    }

    private boolean m_22510_(E p_22511_) {
        return this.m_22512_(p_22511_).m_19950_((Entity)p_22511_, this.f_22489_);
    }

    private LivingEntity m_22512_(E p_22513_) {
        return ((LivingEntity)p_22513_).m_6274_().m_21952_(MemoryModuleType.f_26372_).get();
    }
}

