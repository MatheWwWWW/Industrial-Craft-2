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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RingBell
extends Behavior<LivingEntity> {
    private static final float f_147863_ = 0.95f;
    public static final int f_147862_ = 3;

    public RingBell() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26362_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23789_, LivingEntity p_23790_) {
        return p_23789_.f_46441_.m_188501_() > 0.95f;
    }

    @Override
    protected void m_6735_(ServerLevel p_23792_, LivingEntity p_23793_, long p_23794_) {
        BlockState $$5;
        Brain<?> $$3 = p_23793_.m_6274_();
        BlockPos $$4 = $$3.m_21952_(MemoryModuleType.f_26362_).get().m_122646_();
        if ($$4.m_123314_(p_23793_.m_20183_(), 3.0) && ($$5 = p_23792_.m_8055_($$4)).m_60713_(Blocks.f_50680_)) {
            BellBlock $$6 = (BellBlock)$$5.m_60734_();
            $$6.m_152188_(p_23793_, p_23792_, $$4, null);
        }
    }
}

