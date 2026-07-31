/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.serialization.Codec
 *  org.apache.commons.lang3.mutable.MutableBoolean
 */
package net.minecraft.world.level.levelgen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;
import net.minecraft.world.level.material.Fluids;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class NetherWorldCarver
extends CaveWorldCarver {
    public NetherWorldCarver(Codec<CaveCarverConfiguration> p_64873_) {
        super(p_64873_);
        this.f_64984_ = ImmutableSet.of((Object)Fluids.f_76195_, (Object)Fluids.f_76193_);
    }

    @Override
    protected int m_6208_() {
        return 10;
    }

    @Override
    protected float m_213592_(RandomSource p_224907_) {
        return (p_224907_.m_188501_() * 2.0f + p_224907_.m_188501_()) * 2.0f;
    }

    @Override
    protected double m_6203_() {
        return 5.0;
    }

    @Override
    protected boolean m_183633_(CarvingContext p_190731_, CaveCarverConfiguration p_190732_, ChunkAccess p_190733_, Function<BlockPos, Holder<Biome>> p_190734_, CarvingMask p_190735_, BlockPos.MutableBlockPos p_190736_, BlockPos.MutableBlockPos p_190737_, Aquifer p_190738_, MutableBoolean p_190739_) {
        if (this.m_224910_(p_190732_, p_190733_.m_8055_(p_190736_))) {
            BlockState $$10;
            if (p_190736_.m_123342_() <= p_190731_.m_142201_() + 31) {
                BlockState $$9 = f_64982_.m_76188_();
            } else {
                $$10 = f_64980_;
            }
            p_190733_.m_6978_(p_190736_, $$10, false);
            return true;
        }
        return false;
    }
}

