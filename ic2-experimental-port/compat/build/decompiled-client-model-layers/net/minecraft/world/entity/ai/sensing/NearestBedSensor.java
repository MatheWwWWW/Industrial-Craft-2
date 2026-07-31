/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.longs.Long2LongMap
 *  it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap
 */
package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.AcquirePoi;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.pathfinder.Path;

public class NearestBedSensor
extends Sensor<Mob> {
    private static final int f_148279_ = 40;
    private static final int f_148280_ = 5;
    private static final int f_148281_ = 20;
    private final Long2LongMap f_26676_ = new Long2LongOpenHashMap();
    private int f_26677_;
    private long f_26678_;

    public NearestBedSensor() {
        super(20);
    }

    @Override
    public Set<MemoryModuleType<?>> m_7163_() {
        return ImmutableSet.of(MemoryModuleType.f_26380_);
    }

    @Override
    protected void m_5578_(ServerLevel p_26685_, Mob p_26686_) {
        Predicate<BlockPos> $$3;
        if (!p_26686_.m_6162_()) {
            return;
        }
        this.f_26677_ = 0;
        this.f_26678_ = p_26685_.m_46467_() + (long)p_26685_.m_213780_().m_188503_(20);
        PoiManager $$2 = p_26685_.m_8904_();
        Set<Pair<Holder<PoiType>, BlockPos>> $$4 = $$2.m_217983_(p_217819_ -> p_217819_.m_203565_(PoiTypes.f_218060_), $$3 = p_26688_ -> {
            long $$1 = p_26688_.m_121878_();
            if (this.f_26676_.containsKey($$1)) {
                return false;
            }
            if (++this.f_26677_ >= 5) {
                return false;
            }
            this.f_26676_.put($$1, this.f_26678_ + 40L);
            return true;
        }, p_26686_.m_20183_(), 48, PoiManager.Occupancy.ANY).collect(Collectors.toSet());
        Path $$5 = AcquirePoi.m_217097_(p_26686_, $$4);
        if ($$5 != null && $$5.m_77403_()) {
            BlockPos $$6 = $$5.m_77406_();
            Optional<Holder<PoiType>> $$7 = $$2.m_27177_($$6);
            if ($$7.isPresent()) {
                p_26686_.m_6274_().m_21879_(MemoryModuleType.f_26380_, $$6);
            }
        } else if (this.f_26677_ < 5) {
            this.f_26676_.long2LongEntrySet().removeIf(p_217821_ -> p_217821_.getLongValue() < this.f_26678_);
        }
    }
}

