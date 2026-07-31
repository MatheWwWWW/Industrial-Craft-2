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
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.pathfinder.Path;

public class YieldJobSite
extends Behavior<Villager> {
    private final float f_24833_;

    public YieldJobSite(float p_24835_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26361_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_26360_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_148204_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        this.f_24833_ = p_24835_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_24844_, Villager p_24845_) {
        if (p_24845_.m_6162_()) {
            return false;
        }
        return p_24845_.m_7141_().m_35571_() == VillagerProfession.f_35585_;
    }

    @Override
    protected void m_6735_(ServerLevel p_24847_, Villager p_24848_, long p_24849_) {
        BlockPos $$3 = p_24848_.m_6274_().m_21952_(MemoryModuleType.f_26361_).get().m_122646_();
        Optional<Holder<PoiType>> $$4 = p_24847_.m_8904_().m_27177_($$3);
        if (!$$4.isPresent()) {
            return;
        }
        BehaviorUtils.m_22650_(p_24848_, p_24874_ -> this.m_217510_((Holder)$$4.get(), (Villager)p_24874_, $$3)).findFirst().ifPresent(p_24860_ -> this.m_24850_(p_24847_, p_24848_, (Villager)p_24860_, $$3, p_24860_.m_6274_().m_21952_(MemoryModuleType.f_26360_).isPresent()));
    }

    private boolean m_217510_(Holder<PoiType> p_217511_, Villager p_217512_, BlockPos p_217513_) {
        boolean $$3 = p_217512_.m_6274_().m_21952_(MemoryModuleType.f_26361_).isPresent();
        if ($$3) {
            return false;
        }
        Optional<GlobalPos> $$4 = p_217512_.m_6274_().m_21952_(MemoryModuleType.f_26360_);
        VillagerProfession $$5 = p_217512_.m_7141_().m_35571_();
        if ($$5.f_219628_().test(p_217511_)) {
            if (!$$4.isPresent()) {
                return this.m_24867_(p_217512_, p_217513_, p_217511_.m_203334_());
            }
            return $$4.get().m_122646_().equals(p_217513_);
        }
        return false;
    }

    private void m_24850_(ServerLevel p_24851_, Villager p_24852_, Villager p_24853_, BlockPos p_24854_, boolean p_24855_) {
        this.m_24865_(p_24852_);
        if (!p_24855_) {
            BehaviorUtils.m_22617_(p_24853_, p_24854_, this.f_24833_, 1);
            p_24853_.m_6274_().m_21879_(MemoryModuleType.f_26361_, GlobalPos.m_122643_(p_24851_.m_46472_(), p_24854_));
            DebugPackets.m_133719_(p_24851_, p_24854_);
        }
    }

    private boolean m_24867_(Villager p_24868_, BlockPos p_24869_, PoiType p_24870_) {
        Path $$3 = p_24868_.m_21573_().m_7864_(p_24869_, p_24870_.f_27328_());
        return $$3 != null && $$3.m_77403_();
    }

    private void m_24865_(Villager p_24866_) {
        p_24866_.m_6274_().m_21936_(MemoryModuleType.f_26370_);
        p_24866_.m_6274_().m_21936_(MemoryModuleType.f_26371_);
        p_24866_.m_6274_().m_21936_(MemoryModuleType.f_26361_);
    }
}

