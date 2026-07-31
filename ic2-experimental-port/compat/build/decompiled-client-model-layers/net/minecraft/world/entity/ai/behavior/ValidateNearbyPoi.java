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
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ValidateNearbyPoi
extends Behavior<LivingEntity> {
    private static final int f_148036_ = 16;
    private final MemoryModuleType<GlobalPos> f_24515_;
    private final Predicate<Holder<PoiType>> f_24516_;

    public ValidateNearbyPoi(Predicate<Holder<PoiType>> p_217490_, MemoryModuleType<GlobalPos> p_217491_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(p_217491_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        this.f_24516_ = p_217490_;
        this.f_24515_ = p_217491_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_24521_, LivingEntity p_24522_) {
        GlobalPos $$2 = p_24522_.m_6274_().m_21952_(this.f_24515_).get();
        return p_24521_.m_46472_() == $$2.m_122640_() && $$2.m_122646_().m_203195_(p_24522_.m_20182_(), 16.0);
    }

    @Override
    protected void m_6735_(ServerLevel p_24524_, LivingEntity p_24525_, long p_24526_) {
        Brain<?> $$3 = p_24525_.m_6274_();
        GlobalPos $$4 = $$3.m_21952_(this.f_24515_).get();
        BlockPos $$5 = $$4.m_122646_();
        ServerLevel $$6 = p_24524_.m_7654_().m_129880_($$4.m_122640_());
        if ($$6 == null || this.m_24527_($$6, $$5)) {
            $$3.m_21936_(this.f_24515_);
        } else if (this.m_24530_($$6, $$5, p_24525_)) {
            $$3.m_21936_(this.f_24515_);
            p_24524_.m_8904_().m_27154_($$5);
            DebugPackets.m_133719_(p_24524_, $$5);
        }
    }

    private boolean m_24530_(ServerLevel p_24531_, BlockPos p_24532_, LivingEntity p_24533_) {
        BlockState $$3 = p_24531_.m_8055_(p_24532_);
        return $$3.m_204336_(BlockTags.f_13038_) && $$3.m_61143_(BedBlock.f_49441_) != false && !p_24533_.m_5803_();
    }

    private boolean m_24527_(ServerLevel p_24528_, BlockPos p_24529_) {
        return !p_24528_.m_8904_().m_27091_(p_24529_, this.f_24516_);
    }
}

