/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class InsideBrownianWalk
extends Behavior<PathfinderMob> {
    private final float f_23207_;

    public InsideBrownianWalk(float p_23209_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT)));
        this.f_23207_ = p_23209_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23218_, PathfinderMob p_23219_) {
        return !p_23218_.m_45527_(p_23219_.m_20183_());
    }

    @Override
    protected void m_6735_(ServerLevel p_23221_, PathfinderMob p_23222_, long p_23223_) {
        BlockPos $$3 = p_23222_.m_20183_();
        List $$4 = BlockPos.m_121990_($$3.m_7918_(-1, -1, -1), $$3.m_7918_(1, 1, 1)).map(BlockPos::m_7949_).collect(Collectors.toList());
        Collections.shuffle($$4);
        Optional<BlockPos> $$5 = $$4.stream().filter(p_23230_ -> !p_23221_.m_45527_((BlockPos)p_23230_)).filter(p_23237_ -> p_23221_.m_46575_((BlockPos)p_23237_, p_23222_)).filter(p_23227_ -> p_23221_.m_45786_(p_23222_)).findFirst();
        $$5.ifPresent(p_23233_ -> p_23222_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget((BlockPos)p_23233_, this.f_23207_, 0)));
    }
}

