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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

public class TryFindLand
extends Behavior<PathfinderMob> {
    private static final int f_217413_ = 60;
    private final int f_217414_;
    private final float f_217415_;
    private long f_217416_;

    public TryFindLand(int p_217418_, float p_217419_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26371_, (Object)((Object)MemoryStatus.REGISTERED)));
        this.f_217414_ = p_217418_;
        this.f_217415_ = p_217419_;
    }

    @Override
    protected void m_6732_(ServerLevel p_217431_, PathfinderMob p_217432_, long p_217433_) {
        this.f_217416_ = p_217433_ + 60L;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_217428_, PathfinderMob p_217429_) {
        return p_217429_.f_19853_.m_6425_(p_217429_.m_20183_()).m_205070_(FluidTags.f_13131_);
    }

    @Override
    protected void m_6735_(ServerLevel p_217435_, PathfinderMob p_217436_, long p_217437_) {
        if (p_217437_ < this.f_217416_) {
            return;
        }
        BlockPos $$3 = p_217436_.m_20183_();
        BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
        CollisionContext $$5 = CollisionContext.m_82750_(p_217436_);
        for (BlockPos $$6 : BlockPos.m_121925_($$3, this.f_217414_, this.f_217414_, this.f_217414_)) {
            if ($$6.m_123341_() == $$3.m_123341_() && $$6.m_123343_() == $$3.m_123343_()) continue;
            BlockState $$7 = p_217435_.m_8055_($$6);
            BlockState $$8 = p_217435_.m_8055_($$4.m_122159_($$6, Direction.DOWN));
            if ($$7.m_60713_(Blocks.f_49990_) || !p_217435_.m_6425_($$6).m_76178_() || !$$7.m_60742_(p_217435_, $$6, $$5).m_83281_() || !$$8.m_60783_(p_217435_, $$4, Direction.UP)) continue;
            this.f_217416_ = p_217437_ + 60L;
            BehaviorUtils.m_22617_(p_217436_, $$6.m_7949_(), this.f_217415_, 1);
            return;
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

