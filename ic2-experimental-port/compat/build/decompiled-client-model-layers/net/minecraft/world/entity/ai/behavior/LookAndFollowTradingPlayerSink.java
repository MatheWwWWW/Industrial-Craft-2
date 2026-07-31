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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;

public class LookAndFollowTradingPlayerSink
extends Behavior<Villager> {
    private final float f_23432_;

    public LookAndFollowTradingPlayerSink(float p_23434_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.REGISTERED), MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED)), Integer.MAX_VALUE);
        this.f_23432_ = p_23434_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23445_, Villager p_23446_) {
        Player $$2 = p_23446_.m_7962_();
        return p_23446_.m_6084_() && $$2 != null && !p_23446_.m_20069_() && !p_23446_.f_19864_ && p_23446_.m_20280_($$2) <= 16.0 && $$2.f_36096_ != null;
    }

    @Override
    protected boolean m_6737_(ServerLevel p_23448_, Villager p_23449_, long p_23450_) {
        return this.m_6114_(p_23448_, p_23449_);
    }

    @Override
    protected void m_6735_(ServerLevel p_23458_, Villager p_23459_, long p_23460_) {
        this.m_23451_(p_23459_);
    }

    @Override
    protected void m_6732_(ServerLevel p_23466_, Villager p_23467_, long p_23468_) {
        Brain<Villager> $$3 = p_23467_.m_6274_();
        $$3.m_21936_(MemoryModuleType.f_26370_);
        $$3.m_21936_(MemoryModuleType.f_26371_);
    }

    @Override
    protected void m_6725_(ServerLevel p_23474_, Villager p_23475_, long p_23476_) {
        this.m_23451_(p_23475_);
    }

    @Override
    protected boolean m_7773_(long p_23436_) {
        return false;
    }

    private void m_23451_(Villager p_23452_) {
        Brain<Villager> $$1 = p_23452_.m_6274_();
        $$1.m_21879_(MemoryModuleType.f_26370_, new WalkTarget(new EntityTracker(p_23452_.m_7962_(), false), this.f_23432_, 2));
        $$1.m_21879_(MemoryModuleType.f_26371_, new EntityTracker(p_23452_.m_7962_(), true));
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Villager)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (Villager)livingEntity, l);
    }
}

