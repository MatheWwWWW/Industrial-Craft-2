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
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.schedule.Activity;

public class VillagerPanicTrigger
extends Behavior<Villager> {
    public VillagerPanicTrigger() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of());
    }

    @Override
    protected boolean m_6737_(ServerLevel p_24684_, Villager p_24685_, long p_24686_) {
        return VillagerPanicTrigger.m_24697_(p_24685_) || VillagerPanicTrigger.m_24687_(p_24685_);
    }

    @Override
    protected void m_6735_(ServerLevel p_24694_, Villager p_24695_, long p_24696_) {
        if (VillagerPanicTrigger.m_24697_(p_24695_) || VillagerPanicTrigger.m_24687_(p_24695_)) {
            Brain<Villager> $$3 = p_24695_.m_6274_();
            if (!$$3.m_21954_(Activity.f_37984_)) {
                $$3.m_21936_(MemoryModuleType.f_26377_);
                $$3.m_21936_(MemoryModuleType.f_26370_);
                $$3.m_21936_(MemoryModuleType.f_26371_);
                $$3.m_21936_(MemoryModuleType.f_26375_);
                $$3.m_21936_(MemoryModuleType.f_26374_);
            }
            $$3.m_21889_(Activity.f_37984_);
        }
    }

    @Override
    protected void m_6725_(ServerLevel p_24700_, Villager p_24701_, long p_24702_) {
        if (p_24702_ % 100L == 0L) {
            p_24701_.m_35397_(p_24700_, p_24702_, 3);
        }
    }

    public static boolean m_24687_(LivingEntity p_24688_) {
        return p_24688_.m_6274_().m_21874_(MemoryModuleType.f_26323_);
    }

    public static boolean m_24697_(LivingEntity p_24698_) {
        return p_24698_.m_6274_().m_21874_(MemoryModuleType.f_26381_);
    }

    @Override
    protected /* synthetic */ boolean m_6737_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        return this.m_6737_(serverLevel, (Villager)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6725_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6725_(serverLevel, (Villager)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (Villager)livingEntity, l);
    }
}

