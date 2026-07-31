/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TryFindWater
extends Behavior<PathfinderMob> {
    private final int f_147998_;
    private final float f_147999_;
    private long f_148000_;

    public TryFindWater(int p_148002_, float p_148003_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED)));
        this.f_147998_ = p_148002_;
        this.f_147999_ = p_148003_;
    }

    @Override
    protected void m_6732_(ServerLevel p_148015_, PathfinderMob p_148016_, long p_148017_) {
        this.f_148000_ = p_148017_ + 20L + 2L;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_148012_, PathfinderMob p_148013_) {
        return !p_148013_.f_19853_.m_6425_(p_148013_.m_20183_()).m_205070_(FluidTags.f_13131_);
    }

    @Override
    protected void m_6735_(ServerLevel p_148019_, PathfinderMob p_148020_, long p_148021_) {
        if (p_148021_ < this.f_148000_) {
            return;
        }
        BlockPos $$3 = null;
        BlockPos $$4 = null;
        BlockPos $$5 = p_148020_.m_20183_();
        Iterable<BlockPos> $$6 = BlockPos.m_121925_($$5, this.f_147998_, this.f_147998_, this.f_147998_);
        for (BlockPos $$7 : $$6) {
            if ($$7.m_123341_() == $$5.m_123341_() && $$7.m_123343_() == $$5.m_123343_()) continue;
            BlockState $$8 = p_148020_.f_19853_.m_8055_($$7.m_7494_());
            BlockState $$9 = p_148020_.f_19853_.m_8055_($$7);
            if (!$$9.m_60713_(Blocks.f_49990_)) continue;
            if ($$8.m_60795_()) {
                $$3 = $$7.m_7949_();
                break;
            }
            if ($$4 != null || $$7.m_203195_(p_148020_.m_20182_(), 1.5)) continue;
            $$4 = $$7.m_7949_();
        }
        if ($$3 == null) {
            $$3 = $$4;
        }
        if ($$3 != null) {
            this.f_148000_ = p_148021_ + 40L;
            BehaviorUtils.m_22617_(p_148020_, $$3, this.f_147999_, 0);
        }
    }

    @Override
    protected /* synthetic */ void m_6732_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6732_(serverLevel, (PathfinderMob)livingEntity, l);
    }

    @Override
    protected /* synthetic */ void m_6735_(ServerLevel serverLevel, LivingEntity livingEntity, long l) {
        this.m_6735_(serverLevel, (PathfinderMob)livingEntity, l);
    }
}

