/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableBoolean
 */
package net.minecraft.world.level.levelgen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.Set;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CanyonWorldCarver;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.NetherWorldCarver;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.apache.commons.lang3.mutable.MutableBoolean;

public abstract class WorldCarver<C extends CarverConfiguration> {
    public static final WorldCarver<CaveCarverConfiguration> f_64974_ = WorldCarver.m_65065_("cave", new CaveWorldCarver(CaveCarverConfiguration.f_159154_));
    public static final WorldCarver<CaveCarverConfiguration> f_64975_ = WorldCarver.m_65065_("nether_cave", new NetherWorldCarver(CaveCarverConfiguration.f_159154_));
    public static final WorldCarver<CanyonCarverConfiguration> f_64976_ = WorldCarver.m_65065_("canyon", new CanyonWorldCarver(CanyonCarverConfiguration.f_158966_));
    protected static final BlockState f_64979_ = Blocks.f_50016_.m_49966_();
    protected static final BlockState f_64980_ = Blocks.f_50627_.m_49966_();
    protected static final FluidState f_64981_ = Fluids.f_76193_.m_76145_();
    protected static final FluidState f_64982_ = Fluids.f_76195_.m_76145_();
    protected Set<Fluid> f_64984_ = ImmutableSet.of((Object)Fluids.f_76193_);
    private final Codec<ConfiguredWorldCarver<C>> f_64986_;

    private static <C extends CarverConfiguration, F extends WorldCarver<C>> F m_65065_(String p_65066_, F p_65067_) {
        return (F)Registry.m_122961_(Registry.f_122837_, p_65066_, p_65067_);
    }

    public WorldCarver(Codec<C> p_159366_) {
        this.f_64986_ = p_159366_.fieldOf("config").xmap(this::m_65063_, ConfiguredWorldCarver::f_64850_).codec();
    }

    public ConfiguredWorldCarver<C> m_65063_(C p_65064_) {
        return new ConfiguredWorldCarver<C>(this, p_65064_);
    }

    public Codec<ConfiguredWorldCarver<C>> m_65072_() {
        return this.f_64986_;
    }

    public int m_65073_() {
        return 4;
    }

    protected boolean m_190753_(CarvingContext p_190754_, C p_190755_, ChunkAccess p_190756_, Function<BlockPos, Holder<Biome>> p_190757_, Aquifer p_190758_, double p_190759_, double p_190760_, double p_190761_, double p_190762_, double p_190763_, CarvingMask p_190764_, CarveSkipChecker p_190765_) {
        ChunkPos $$12 = p_190756_.m_7697_();
        double $$13 = $$12.m_151390_();
        double $$14 = $$12.m_151393_();
        double $$15 = 16.0 + p_190762_ * 2.0;
        if (Math.abs(p_190759_ - $$13) > $$15 || Math.abs(p_190761_ - $$14) > $$15) {
            return false;
        }
        int $$16 = $$12.m_45604_();
        int $$17 = $$12.m_45605_();
        int $$18 = Math.max(Mth.m_14107_(p_190759_ - p_190762_) - $$16 - 1, 0);
        int $$19 = Math.min(Mth.m_14107_(p_190759_ + p_190762_) - $$16, 15);
        int $$20 = Math.max(Mth.m_14107_(p_190760_ - p_190763_) - 1, p_190754_.m_142201_() + 1);
        int $$21 = p_190756_.m_187679_() ? 0 : 7;
        int $$22 = Math.min(Mth.m_14107_(p_190760_ + p_190763_) + 1, p_190754_.m_142201_() + p_190754_.m_142208_() - 1 - $$21);
        int $$23 = Math.max(Mth.m_14107_(p_190761_ - p_190762_) - $$17 - 1, 0);
        int $$24 = Math.min(Mth.m_14107_(p_190761_ + p_190762_) - $$17, 15);
        boolean $$25 = false;
        BlockPos.MutableBlockPos $$26 = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos $$27 = new BlockPos.MutableBlockPos();
        for (int $$28 = $$18; $$28 <= $$19; ++$$28) {
            int $$29 = $$12.m_151382_($$28);
            double $$30 = ((double)$$29 + 0.5 - p_190759_) / p_190762_;
            for (int $$31 = $$23; $$31 <= $$24; ++$$31) {
                int $$32 = $$12.m_151391_($$31);
                double $$33 = ((double)$$32 + 0.5 - p_190761_) / p_190762_;
                if ($$30 * $$30 + $$33 * $$33 >= 1.0) continue;
                MutableBoolean $$34 = new MutableBoolean(false);
                for (int $$35 = $$22; $$35 > $$20; --$$35) {
                    double $$36 = ((double)$$35 - 0.5 - p_190760_) / p_190763_;
                    if (p_190765_.m_159425_(p_190754_, $$30, $$36, $$33, $$35) || p_190764_.m_187594_($$28, $$35, $$31) && !WorldCarver.m_159423_(p_190755_)) continue;
                    p_190764_.m_187585_($$28, $$35, $$31);
                    $$26.m_122178_($$29, $$35, $$32);
                    $$25 |= this.m_183633_(p_190754_, p_190755_, p_190756_, p_190757_, p_190764_, $$26, $$27, p_190758_, $$34);
                }
            }
        }
        return $$25;
    }

    protected boolean m_183633_(CarvingContext p_190744_, C p_190745_, ChunkAccess p_190746_, Function<BlockPos, Holder<Biome>> p_190747_, CarvingMask p_190748_, BlockPos.MutableBlockPos p_190749_, BlockPos.MutableBlockPos p_190750_, Aquifer p_190751_, MutableBoolean p_190752_) {
        BlockState $$9 = p_190746_.m_8055_(p_190749_);
        if ($$9.m_60713_(Blocks.f_50440_) || $$9.m_60713_(Blocks.f_50195_)) {
            p_190752_.setTrue();
        }
        if (!this.m_224910_(p_190745_, $$9) && !WorldCarver.m_159423_(p_190745_)) {
            return false;
        }
        BlockState $$10 = this.m_159418_(p_190744_, p_190745_, p_190749_, p_190751_);
        if ($$10 == null) {
            return false;
        }
        p_190746_.m_6978_(p_190749_, $$10, false);
        if (p_190751_.m_142203_() && !$$10.m_60819_().m_76178_()) {
            p_190746_.m_8113_(p_190749_);
        }
        if (p_190752_.isTrue()) {
            p_190750_.m_122159_(p_190749_, Direction.DOWN);
            if (p_190746_.m_8055_(p_190750_).m_60713_(Blocks.f_50493_)) {
                p_190744_.m_190646_(p_190747_, p_190746_, p_190750_, !$$10.m_60819_().m_76178_()).ifPresent(p_190743_ -> {
                    p_190746_.m_6978_(p_190750_, (BlockState)p_190743_, false);
                    if (!p_190743_.m_60819_().m_76178_()) {
                        p_190746_.m_8113_(p_190750_);
                    }
                });
            }
        }
        return true;
    }

    @Nullable
    private BlockState m_159418_(CarvingContext p_159419_, C p_159420_, BlockPos p_159421_, Aquifer p_159422_) {
        if (p_159421_.m_123342_() <= ((CarverConfiguration)p_159420_).f_159090_.m_142322_(p_159419_)) {
            return f_64982_.m_76188_();
        }
        BlockState $$4 = p_159422_.m_207104_(new DensityFunction.SinglePointContext(p_159421_.m_123341_(), p_159421_.m_123342_(), p_159421_.m_123343_()), 0.0);
        if ($$4 == null) {
            return WorldCarver.m_159423_(p_159420_) ? ((CarverConfiguration)p_159420_).f_159092_.m_159148_() : null;
        }
        return WorldCarver.m_159423_(p_159420_) ? WorldCarver.m_159381_(p_159420_, $$4) : $$4;
    }

    private static BlockState m_159381_(CarverConfiguration p_159382_, BlockState p_159383_) {
        if (p_159383_.m_60713_(Blocks.f_50016_)) {
            return p_159382_.f_159092_.m_159145_();
        }
        if (p_159383_.m_60713_(Blocks.f_49990_)) {
            BlockState $$2 = p_159382_.f_159092_.m_159146_();
            if ($$2.m_61138_(BlockStateProperties.f_61362_)) {
                return (BlockState)$$2.m_61124_(BlockStateProperties.f_61362_, true);
            }
            return $$2;
        }
        if (p_159383_.m_60713_(Blocks.f_49991_)) {
            return p_159382_.f_159092_.m_159147_();
        }
        return p_159383_;
    }

    public abstract boolean m_213788_(CarvingContext var1, C var2, ChunkAccess var3, Function<BlockPos, Holder<Biome>> var4, RandomSource var5, Aquifer var6, ChunkPos var7, CarvingMask var8);

    public abstract boolean m_214133_(C var1, RandomSource var2);

    protected boolean m_224910_(C p_224911_, BlockState p_224912_) {
        return p_224912_.m_204341_(((CarverConfiguration)p_224911_).f_224830_);
    }

    protected static boolean m_159367_(ChunkPos p_159368_, double p_159369_, double p_159370_, int p_159371_, int p_159372_, float p_159373_) {
        double $$11;
        double $$10;
        double $$7;
        double $$9;
        double $$6 = p_159368_.m_151390_();
        double $$8 = p_159369_ - $$6;
        return $$8 * $$8 + ($$9 = p_159370_ - ($$7 = (double)p_159368_.m_151393_())) * $$9 - ($$10 = (double)(p_159372_ - p_159371_)) * $$10 <= ($$11 = (double)(p_159373_ + 2.0f + 16.0f)) * $$11;
    }

    private static boolean m_159423_(CarverConfiguration p_159424_) {
        return p_159424_.f_159092_.m_159128_();
    }

    public static interface CarveSkipChecker {
        public boolean m_159425_(CarvingContext var1, double var2, double var4, double var6, int var8);
    }
}

