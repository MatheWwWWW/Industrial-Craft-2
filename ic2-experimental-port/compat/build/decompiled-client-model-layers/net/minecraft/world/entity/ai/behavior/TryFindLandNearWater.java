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
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.CollisionContext;

public class TryFindLandNearWater
extends Behavior<PathfinderMob> {
    private final int f_217442_;
    private final float f_217443_;
    private long f_217444_;

    public TryFindLandNearWater(int p_217446_, float p_217447_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED)));
        this.f_217442_ = p_217446_;
        this.f_217443_ = p_217447_;
    }

    @Override
    protected void m_6732_(ServerLevel p_217459_, PathfinderMob p_217460_, long p_217461_) {
        this.f_217444_ = p_217461_ + 40L;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_217456_, PathfinderMob p_217457_) {
        return !p_217457_.f_19853_.m_6425_(p_217457_.m_20183_()).m_205070_(FluidTags.f_13131_);
    }

    @Override
    protected void m_6735_(ServerLevel p_217463_, PathfinderMob p_217464_, long p_217465_) {
        if (p_217465_ < this.f_217444_) {
            return;
        }
        CollisionContext $$3 = CollisionContext.m_82750_(p_217464_);
        BlockPos $$4 = p_217464_.m_20183_();
        BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
        for (BlockPos $$6 : BlockPos.m_121925_($$4, this.f_217442_, this.f_217442_, this.f_217442_)) {
            if ($$6.m_123341_() == $$4.m_123341_() && $$6.m_123343_() == $$4.m_123343_() || !p_217463_.m_8055_($$6).m_60742_(p_217463_, $$6, $$3).m_83281_() || p_217463_.m_8055_($$5.m_122159_($$6, Direction.DOWN)).m_60742_(p_217463_, $$6, $$3).m_83281_()) continue;
            for (Direction $$7 : Direction.Plane.HORIZONTAL) {
                $$5.m_122159_($$6, $$7);
                if (!p_217463_.m_8055_($$5).m_60795_() || !p_217463_.m_8055_($$5.m_122173_(Direction.DOWN)).m_60713_(Blocks.f_49990_)) continue;
                this.f_217444_ = p_217465_ + 40L;
                BehaviorUtils.m_22617_(p_217464_, $$6, this.f_217443_, 0);
                return;
            }
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

