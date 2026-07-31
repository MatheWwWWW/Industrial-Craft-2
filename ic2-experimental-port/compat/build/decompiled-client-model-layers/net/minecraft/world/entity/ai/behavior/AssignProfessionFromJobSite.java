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
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;

public class AssignProfessionFromJobSite
extends Behavior<Villager> {
    public AssignProfessionFromJobSite() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26361_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
    }

    @Override
    protected boolean m_6114_(ServerLevel p_22450_, Villager p_22451_) {
        BlockPos $$2 = p_22451_.m_6274_().m_21952_(MemoryModuleType.f_26361_).get().m_122646_();
        return $$2.m_203195_(p_22451_.m_20182_(), 2.0) || p_22451_.m_35504_();
    }

    @Override
    protected void m_6735_(ServerLevel p_22453_, Villager p_22454_, long p_22455_) {
        GlobalPos $$3 = p_22454_.m_6274_().m_21952_(MemoryModuleType.f_26361_).get();
        p_22454_.m_6274_().m_21936_(MemoryModuleType.f_26361_);
        p_22454_.m_6274_().m_21879_(MemoryModuleType.f_26360_, $$3);
        p_22453_.m_7605_(p_22454_, (byte)14);
        if (p_22454_.m_7141_().m_35571_() != VillagerProfession.f_35585_) {
            return;
        }
        MinecraftServer $$4 = p_22453_.m_7654_();
        Optional.ofNullable($$4.m_129880_($$3.m_122640_())).flatMap(p_22467_ -> p_22467_.m_8904_().m_27177_($$3.m_122646_())).flatMap(p_217122_ -> Registry.f_122869_.m_123024_().filter(p_217125_ -> p_217125_.f_219628_().test((Holder<PoiType>)p_217122_)).findFirst()).ifPresent(p_22464_ -> {
            p_22454_.m_34375_(p_22454_.m_7141_().m_35565_((VillagerProfession)p_22464_));
            p_22454_.m_35483_(p_22453_);
        });
    }
}

