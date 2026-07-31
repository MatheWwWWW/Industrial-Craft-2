/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.pathfinder.Path;

public class AcquirePoi
extends Behavior<PathfinderMob> {
    private static final int f_147364_ = 5;
    private static final int f_147365_ = 20;
    public static final int f_147363_ = 48;
    private final Predicate<Holder<PoiType>> f_22316_;
    private final MemoryModuleType<GlobalPos> f_22317_;
    private final boolean f_22318_;
    private final Optional<Byte> f_22319_;
    private long f_22320_;
    private final Long2ObjectMap<JitteredLinearRetry> f_22321_ = new Long2ObjectOpenHashMap();

    public AcquirePoi(Predicate<Holder<PoiType>> p_217087_, MemoryModuleType<GlobalPos> p_217088_, MemoryModuleType<GlobalPos> p_217089_, boolean p_217090_, Optional<Byte> p_217091_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)AcquirePoi.m_22361_(p_217088_, p_217089_));
        this.f_22316_ = p_217087_;
        this.f_22317_ = p_217089_;
        this.f_22318_ = p_217090_;
        this.f_22319_ = p_217091_;
    }

    public AcquirePoi(Predicate<Holder<PoiType>> p_217093_, MemoryModuleType<GlobalPos> p_217094_, boolean p_217095_, Optional<Byte> p_217096_) {
        this(p_217093_, p_217094_, p_217094_, p_217095_, p_217096_);
    }

    private static ImmutableMap<MemoryModuleType<?>, MemoryStatus> m_22361_(MemoryModuleType<GlobalPos> p_22362_, MemoryModuleType<GlobalPos> p_22363_) {
        ImmutableMap.Builder $$2 = ImmutableMap.builder();
        $$2.put(p_22362_, (Object)MemoryStatus.VALUE_ABSENT);
        if (p_22363_ != p_22362_) {
            $$2.put(p_22363_, (Object)MemoryStatus.VALUE_ABSENT);
        }
        return $$2.build();
    }

    @Override
    protected boolean m_6114_(ServerLevel p_22347_, PathfinderMob p_22348_) {
        if (this.f_22318_ && p_22348_.m_6162_()) {
            return false;
        }
        if (this.f_22320_ == 0L) {
            this.f_22320_ = p_22348_.f_19853_.m_46467_() + (long)p_22347_.f_46441_.m_188503_(20);
            return false;
        }
        return p_22347_.m_46467_() >= this.f_22320_;
    }

    @Override
    protected void m_6735_(ServerLevel p_22350_, PathfinderMob p_22351_, long p_22352_) {
        this.f_22320_ = p_22352_ + 20L + (long)p_22350_.m_213780_().m_188503_(20);
        PoiManager $$3 = p_22350_.m_8904_();
        this.f_22321_.long2ObjectEntrySet().removeIf(p_22338_ -> !((JitteredLinearRetry)p_22338_.getValue()).m_22382_(p_22352_));
        Predicate<BlockPos> $$4 = p_22335_ -> {
            JitteredLinearRetry $$2 = (JitteredLinearRetry)this.f_22321_.get(p_22335_.m_121878_());
            if ($$2 == null) {
                return true;
            }
            if (!$$2.m_22384_(p_22352_)) {
                return false;
            }
            $$2.m_22380_(p_22352_);
            return true;
        };
        Set<Pair<Holder<PoiType>, BlockPos>> $$5 = $$3.m_217994_(this.f_22316_, $$4, p_22351_.m_20183_(), 48, PoiManager.Occupancy.HAS_SPACE).limit(5L).collect(Collectors.toSet());
        Path $$6 = AcquirePoi.m_217097_(p_22351_, $$5);
        if ($$6 != null && $$6.m_77403_()) {
            BlockPos $$7 = $$6.m_77406_();
            $$3.m_27177_($$7).ifPresent(p_217105_ -> {
                $$3.m_217946_(this.f_22316_, (p_217108_, p_217109_) -> p_217109_.equals($$7), $$7, 1);
                p_22351_.m_6274_().m_21879_(this.f_22317_, GlobalPos.m_122643_(p_22350_.m_46472_(), $$7));
                this.f_22319_.ifPresent(p_147369_ -> p_22350_.m_7605_(p_22351_, (byte)p_147369_));
                this.f_22321_.clear();
                DebugPackets.m_133719_(p_22350_, $$7);
            });
        } else {
            for (Pair<Holder<PoiType>, BlockPos> $$8 : $$5) {
                this.f_22321_.computeIfAbsent(((BlockPos)$$8.getSecond()).m_121878_(), p_22360_ -> new JitteredLinearRetry(p_22358_.f_19853_.f_46441_, p_22352_));
            }
        }
    }

    @Nullable
    public static Path m_217097_(Mob p_217098_, Set<Pair<Holder<PoiType>, BlockPos>> p_217099_) {
        if (p_217099_.isEmpty()) {
            return null;
        }
        HashSet<BlockPos> $$2 = new HashSet<BlockPos>();
        int $$3 = 1;
        for (Pair<Holder<PoiType>, BlockPos> $$4 : p_217099_) {
            $$3 = Math.max($$3, ((PoiType)((Holder)$$4.getFirst()).m_203334_()).f_27328_());
            $$2.add((BlockPos)$$4.getSecond());
        }
        return p_217098_.m_21573_().m_26548_($$2, $$3);
    }

    static class JitteredLinearRetry {
        private static final int f_147373_ = 40;
        private static final int f_147374_ = 80;
        private static final int f_147375_ = 400;
        private final RandomSource f_22373_;
        private long f_22374_;
        private long f_22375_;
        private int f_22376_;

        JitteredLinearRetry(RandomSource p_217111_, long p_217112_) {
            this.f_22373_ = p_217111_;
            this.m_22380_(p_217112_);
        }

        public void m_22380_(long p_22381_) {
            this.f_22374_ = p_22381_;
            int $$1 = this.f_22376_ + this.f_22373_.m_188503_(40) + 40;
            this.f_22376_ = Math.min($$1, 400);
            this.f_22375_ = p_22381_ + (long)this.f_22376_;
        }

        public boolean m_22382_(long p_22383_) {
            return p_22383_ - this.f_22374_ < 400L;
        }

        public boolean m_22384_(long p_22385_) {
            return p_22385_ >= this.f_22375_;
        }

        public String toString() {
            return "RetryMarker{, previousAttemptAt=" + this.f_22374_ + ", nextScheduledAttemptAt=" + this.f_22375_ + ", currentDelay=" + this.f_22376_ + "}";
        }
    }
}

