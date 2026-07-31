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
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.schedule.Activity;

public class GoToPotentialJobSite
extends Behavior<Villager> {
    private static final int f_147555_ = 1200;
    final float f_23096_;

    public GoToPotentialJobSite(float p_23098_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26361_, (Object)((Object)MemoryStatus.VALUE_PRESENT)), 1200);
        this.f_23096_ = p_23098_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23103_, Villager p_23104_) {
        return p_23104_.m_6274_().m_21968_().map(p_23115_ -> p_23115_ == Activity.f_37979_ || p_23115_ == Activity.f_37980_ || p_23115_ == Activity.f_37981_).orElse(true);
    }

    @Override
    protected boolean m_6737_(ServerLevel p_23106_, Villager p_23107_, long p_23108_) {
        return p_23107_.m_6274_().m_21874_(MemoryModuleType.f_26361_);
    }

    @Override
    protected void m_6725_(ServerLevel p_23121_, Villager p_23122_, long p_23123_) {
        BehaviorUtils.m_22617_(p_23122_, p_23122_.m_6274_().m_21952_(MemoryModuleType.f_26361_).get().m_122646_(), this.f_23096_, 1);
    }

    @Override
    protected void m_6732_(ServerLevel p_23129_, Villager p_23130_, long p_23131_) {
        Optional<GlobalPos> $$3 = p_23130_.m_6274_().m_21952_(MemoryModuleType.f_26361_);
        $$3.ifPresent(p_23111_ -> {
            BlockPos $$2 = p_23111_.m_122646_();
            ServerLevel $$3 = p_23129_.m_7654_().m_129880_(p_23111_.m_122640_());
            if ($$3 == null) {
                return;
            }
            PoiManager $$4 = $$3.m_8904_();
            if ($$4.m_27091_($$2, p_217230_ -> true)) {
                $$4.m_27154_($$2);
            }
            DebugPackets.m_133719_(p_23129_, $$2);
        });
        p_23130_.m_6274_().m_21936_(MemoryModuleType.f_26361_);
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Villager)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6725_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6725_(serverLevel, (Villager)livingEntity, l);
    }
}

