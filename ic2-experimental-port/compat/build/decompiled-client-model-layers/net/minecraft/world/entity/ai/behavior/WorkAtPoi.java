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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;

public class WorkAtPoi
extends Behavior<Villager> {
    private static final int f_148046_ = 300;
    private static final double f_148047_ = 1.73;
    private long f_24804_;

    public WorkAtPoi() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26360_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED)));
    }

    @Override
    protected boolean m_6114_(ServerLevel p_24827_, Villager p_24828_) {
        if (p_24827_.m_46467_() - this.f_24804_ < 300L) {
            return false;
        }
        if (p_24827_.f_46441_.m_188503_(2) != 0) {
            return false;
        }
        this.f_24804_ = p_24827_.m_46467_();
        GlobalPos $$2 = p_24828_.m_6274_().m_21952_(MemoryModuleType.f_26360_).get();
        return $$2.m_122640_() == p_24827_.m_46472_() && $$2.m_122646_().m_203195_(p_24828_.m_20182_(), 1.73);
    }

    @Override
    protected void m_6735_(ServerLevel p_24816_, Villager p_24817_, long p_24818_) {
        Brain<Villager> $$3 = p_24817_.m_6274_();
        $$3.m_21879_(MemoryModuleType.f_26330_, p_24818_);
        $$3.m_21952_(MemoryModuleType.f_26360_).ifPresent(p_24821_ -> $$3.m_21879_(MemoryModuleType.f_26371_, new BlockPosTracker(p_24821_.m_122646_())));
        p_24817_.m_35512_();
        this.m_5628_(p_24816_, p_24817_);
        if (p_24817_.m_35511_()) {
            p_24817_.m_35510_();
        }
    }

    protected void m_5628_(ServerLevel p_24813_, Villager p_24814_) {
    }

    @Override
    protected boolean m_6737_(ServerLevel p_24830_, Villager p_24831_, long p_24832_) {
        Optional<GlobalPos> $$3 = p_24831_.m_6274_().m_21952_(MemoryModuleType.f_26360_);
        if (!$$3.isPresent()) {
            return false;
        }
        GlobalPos $$4 = $$3.get();
        return $$4.m_122640_() == p_24830_.m_46472_() && $$4.m_122646_().m_203195_(p_24831_.m_20182_(), 1.73);
    }

    @Override
    protected /* synthetic */ boolean m_6114_(ServerLevel serverLevel, LivingEntity livingEntity) {
        return this.m_6114_(serverLevel, (Villager)livingEntity);
    }
}

