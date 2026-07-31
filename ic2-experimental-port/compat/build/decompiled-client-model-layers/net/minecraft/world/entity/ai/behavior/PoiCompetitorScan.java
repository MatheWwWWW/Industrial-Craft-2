/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;

public class PoiCompetitorScan
extends Behavior<Villager> {
    final VillagerProfession f_23708_;

    public PoiCompetitorScan(VillagerProfession p_23710_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26360_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_148204_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        this.f_23708_ = p_23710_;
    }

    @Override
    protected void m_6735_(ServerLevel p_23716_, Villager p_23717_, long p_23718_) {
        GlobalPos $$3 = p_23717_.m_6274_().m_21952_(MemoryModuleType.f_26360_).get();
        p_23716_.m_8904_().m_27177_($$3.m_122646_()).ifPresent(p_217328_ -> BehaviorUtils.m_22650_(p_23717_, p_217339_ -> this.m_217329_($$3, (Holder<PoiType>)p_217328_, (Villager)p_217339_)).reduce(p_23717_, PoiCompetitorScan::m_23724_));
    }

    private static Villager m_23724_(Villager p_23725_, Villager p_23726_) {
        Villager $$5;
        Villager $$4;
        if (p_23725_.m_7809_() > p_23726_.m_7809_()) {
            Villager $$2 = p_23725_;
            Villager $$3 = p_23726_;
        } else {
            $$4 = p_23726_;
            $$5 = p_23725_;
        }
        $$5.m_6274_().m_21936_(MemoryModuleType.f_26360_);
        return $$4;
    }

    private boolean m_217329_(GlobalPos p_217330_, Holder<PoiType> p_217331_, Villager p_217332_) {
        return this.m_23722_(p_217332_) && p_217330_.equals(p_217332_.m_6274_().m_21952_(MemoryModuleType.f_26360_).get()) && this.m_217333_(p_217331_, p_217332_.m_7141_().m_35571_());
    }

    private boolean m_217333_(Holder<PoiType> p_217334_, VillagerProfession p_217335_) {
        return p_217335_.f_219628_().test(p_217334_);
    }

    private boolean m_23722_(Villager p_23723_) {
        return p_23723_.m_6274_().m_21952_(MemoryModuleType.f_26360_).isPresent();
    }
}

