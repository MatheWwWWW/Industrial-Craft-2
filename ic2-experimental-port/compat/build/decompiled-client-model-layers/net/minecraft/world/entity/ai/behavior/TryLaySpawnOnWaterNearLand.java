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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;

public class TryLaySpawnOnWaterNearLand
extends Behavior<Frog> {
    private final Block f_217470_;
    private final MemoryModuleType<?> f_217471_;

    public TryLaySpawnOnWaterNearLand(Block p_217473_, MemoryModuleType<?> p_217474_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26372_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_PRESENT), MemoryModuleType.f_217767_, (Object)((Object)MemoryStatus.VALUE_PRESENT)));
        this.f_217470_ = p_217473_;
        this.f_217471_ = p_217474_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_217483_, Frog p_217484_) {
        return !p_217484_.m_20069_() && p_217484_.m_20096_();
    }

    @Override
    protected void m_6735_(ServerLevel p_217486_, Frog p_217487_, long p_217488_) {
        BlockPos $$3 = p_217487_.m_20183_().m_7495_();
        for (Direction $$4 : Direction.Plane.HORIZONTAL) {
            BlockPos $$6;
            BlockPos $$5 = $$3.m_121945_($$4);
            if (!p_217486_.m_8055_($$5).m_60812_(p_217486_, $$5).m_83263_(Direction.UP).m_83281_() || !p_217486_.m_6425_($$5).m_192917_(Fluids.f_76193_) || !p_217486_.m_8055_($$6 = $$5.m_7494_()).m_60795_()) continue;
            p_217486_.m_7731_($$6, this.f_217470_.m_49966_(), 3);
            p_217486_.m_6269_(null, p_217487_, SoundEvents.f_215694_, SoundSource.BLOCKS, 1.0f, 1.0f);
            p_217487_.m_6274_().m_21936_(this.f_217471_);
            return;
        }
    }
}

