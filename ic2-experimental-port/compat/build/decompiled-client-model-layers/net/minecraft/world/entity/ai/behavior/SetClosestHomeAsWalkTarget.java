/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.longs.Long2LongMap
 *  it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AcquirePoi;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.pathfinder.Path;

public class SetClosestHomeAsWalkTarget
extends Behavior<LivingEntity> {
    private static final int f_147880_ = 40;
    private static final int f_147881_ = 5;
    private static final int f_147882_ = 20;
    private static final int f_147883_ = 4;
    private final float f_23872_;
    private final Long2LongMap f_23873_ = new Long2LongOpenHashMap();
    private int f_23874_;
    private long f_23875_;

    public SetClosestHomeAsWalkTarget(float p_23877_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT), MemoryModuleType.f_26359_, (Object)((Object)MemoryStatus.VALUE_ABSENT)));
        this.f_23872_ = p_23877_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_23879_, LivingEntity p_23880_) {
        if (p_23879_.m_46467_() - this.f_23875_ < 20L) {
            return false;
        }
        PathfinderMob $$2 = (PathfinderMob)p_23880_;
        PoiManager $$3 = p_23879_.m_8904_();
        Optional<BlockPos> $$4 = $$3.m_27192_(p_217376_ -> p_217376_.m_203565_(PoiTypes.f_218060_), p_23880_.m_20183_(), 48, PoiManager.Occupancy.ANY);
        return $$4.isPresent() && !($$4.get().m_123331_($$2.m_20183_()) <= 4.0);
    }

    @Override
    protected void m_6735_(ServerLevel p_23882_, LivingEntity p_23883_, long p_23884_) {
        Predicate<BlockPos> $$5;
        this.f_23874_ = 0;
        this.f_23875_ = p_23882_.m_46467_() + (long)p_23882_.m_213780_().m_188503_(20);
        PathfinderMob $$3 = (PathfinderMob)p_23883_;
        PoiManager $$4 = p_23882_.m_8904_();
        Set<Pair<Holder<PoiType>, BlockPos>> $$6 = $$4.m_217983_(p_217372_ -> p_217372_.m_203565_(PoiTypes.f_218060_), $$5 = p_217370_ -> {
            long $$1 = p_217370_.m_121878_();
            if (this.f_23873_.containsKey($$1)) {
                return false;
            }
            if (++this.f_23874_ >= 5) {
                return false;
            }
            this.f_23873_.put($$1, this.f_23875_ + 40L);
            return true;
        }, p_23883_.m_20183_(), 48, PoiManager.Occupancy.ANY).collect(Collectors.toSet());
        Path $$7 = AcquirePoi.m_217097_($$3, $$6);
        if ($$7 != null && $$7.m_77403_()) {
            BlockPos $$8 = $$7.m_77406_();
            Optional<Holder<PoiType>> $$9 = $$4.m_27177_($$8);
            if ($$9.isPresent()) {
                p_23883_.m_6274_().m_21879_(MemoryModuleType.f_26370_, new WalkTarget($$8, this.f_23872_, 1));
                DebugPackets.m_133719_(p_23882_, $$8);
            }
        } else if (this.f_23874_ < 5) {
            this.f_23873_.long2LongEntrySet().removeIf(p_217374_ -> p_217374_.getLongValue() < this.f_23875_);
        }
    }
}

