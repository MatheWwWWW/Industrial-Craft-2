/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BlockColumn;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.Material;

public class SurfaceSystem {
    private static final BlockState f_189894_ = Blocks.f_50287_.m_49966_();
    private static final BlockState f_189895_ = Blocks.f_50288_.m_49966_();
    private static final BlockState f_189896_ = Blocks.f_50352_.m_49966_();
    private static final BlockState f_189897_ = Blocks.f_50291_.m_49966_();
    private static final BlockState f_189898_ = Blocks.f_50299_.m_49966_();
    private static final BlockState f_189899_ = Blocks.f_50301_.m_49966_();
    private static final BlockState f_189900_ = Blocks.f_50295_.m_49966_();
    private static final BlockState f_189901_ = Blocks.f_50354_.m_49966_();
    private static final BlockState f_189902_ = Blocks.f_50127_.m_49966_();
    private final BlockState f_189904_;
    private final int f_189905_;
    private final BlockState[] f_189906_;
    private final NormalNoise f_189907_;
    private final NormalNoise f_189908_;
    private final NormalNoise f_189909_;
    private final NormalNoise f_189910_;
    private final NormalNoise f_189911_;
    private final NormalNoise f_189912_;
    private final NormalNoise f_189913_;
    private final PositionalRandomFactory f_224635_;
    private final NormalNoise f_189918_;
    private final NormalNoise f_189892_;

    public SurfaceSystem(RandomState p_224637_, BlockState p_224638_, int p_224639_, PositionalRandomFactory p_224640_) {
        this.f_189904_ = p_224638_;
        this.f_189905_ = p_224639_;
        this.f_224635_ = p_224640_;
        this.f_189907_ = p_224637_.m_224560_(Noises.f_189258_);
        this.f_189906_ = SurfaceSystem.m_224641_(p_224640_.m_224540_(new ResourceLocation("clay_bands")));
        this.f_189918_ = p_224637_.m_224560_(Noises.f_189256_);
        this.f_189892_ = p_224637_.m_224560_(Noises.f_189257_);
        this.f_189908_ = p_224637_.m_224560_(Noises.f_189259_);
        this.f_189909_ = p_224637_.m_224560_(Noises.f_189260_);
        this.f_189910_ = p_224637_.m_224560_(Noises.f_189261_);
        this.f_189911_ = p_224637_.m_224560_(Noises.f_189262_);
        this.f_189912_ = p_224637_.m_224560_(Noises.f_189263_);
        this.f_189913_ = p_224637_.m_224560_(Noises.f_189264_);
    }

    public void m_224648_(RandomState p_224649_, BiomeManager p_224650_, Registry<Biome> p_224651_, boolean p_224652_, WorldGenerationContext p_224653_, final ChunkAccess p_224654_, NoiseChunk p_224655_, SurfaceRules.RuleSource p_224656_) {
        final BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos();
        final ChunkPos $$9 = p_224654_.m_7697_();
        int $$10 = $$9.m_45604_();
        int $$11 = $$9.m_45605_();
        BlockColumn $$12 = new BlockColumn(){

            @Override
            public BlockState m_183556_(int p_190006_) {
                return p_224654_.m_8055_($$8.m_142448_(p_190006_));
            }

            @Override
            public void m_183639_(int p_190008_, BlockState p_190009_) {
                LevelHeightAccessor $$2 = p_224654_.m_183618_();
                if (p_190008_ >= $$2.m_141937_() && p_190008_ < $$2.m_151558_()) {
                    p_224654_.m_6978_($$8.m_142448_(p_190008_), p_190009_, false);
                    if (!p_190009_.m_60819_().m_76178_()) {
                        p_224654_.m_8113_($$8);
                    }
                }
            }

            public String toString() {
                return "ChunkBlockColumn " + $$9;
            }
        };
        SurfaceRules.Context $$13 = new SurfaceRules.Context(this, p_224649_, p_224654_, p_224655_, p_224650_::m_204214_, p_224651_, p_224653_);
        SurfaceRules.SurfaceRule $$14 = (SurfaceRules.SurfaceRule)p_224656_.apply($$13);
        BlockPos.MutableBlockPos $$15 = new BlockPos.MutableBlockPos();
        for (int $$16 = 0; $$16 < 16; ++$$16) {
            for (int $$17 = 0; $$17 < 16; ++$$17) {
                int $$18 = $$10 + $$16;
                int $$19 = $$11 + $$17;
                int $$20 = p_224654_.m_5885_(Heightmap.Types.WORLD_SURFACE_WG, $$16, $$17) + 1;
                $$8.m_142451_($$18).m_142443_($$19);
                Holder<Biome> $$21 = p_224650_.m_204214_($$15.m_122178_($$18, p_224652_ ? 0 : $$20, $$19));
                if ($$21.m_203565_(Biomes.f_48194_)) {
                    this.m_189954_($$12, $$18, $$19, $$20, p_224654_);
                }
                int $$22 = p_224654_.m_5885_(Heightmap.Types.WORLD_SURFACE_WG, $$16, $$17) + 1;
                $$13.m_189569_($$18, $$19);
                int $$23 = 0;
                int $$24 = Integer.MIN_VALUE;
                int $$25 = Integer.MAX_VALUE;
                int $$26 = p_224654_.m_141937_();
                for (int $$27 = $$22; $$27 >= $$26; --$$27) {
                    BlockState $$32;
                    BlockState $$28 = $$12.m_183556_($$27);
                    if ($$28.m_60795_()) {
                        $$23 = 0;
                        $$24 = Integer.MIN_VALUE;
                        continue;
                    }
                    if (!$$28.m_60819_().m_76178_()) {
                        if ($$24 != Integer.MIN_VALUE) continue;
                        $$24 = $$27 + 1;
                        continue;
                    }
                    if ($$25 >= $$27) {
                        $$25 = DimensionType.f_188294_;
                        for (int $$29 = $$27 - 1; $$29 >= $$26 - 1; --$$29) {
                            BlockState $$30 = $$12.m_183556_($$29);
                            if (this.m_189952_($$30)) continue;
                            $$25 = $$29 + 1;
                            break;
                        }
                    }
                    int $$31 = $$27 - $$25 + 1;
                    $$13.m_189576_(++$$23, $$31, $$24, $$18, $$27, $$19);
                    if ($$28 != this.f_189904_ || ($$32 = $$14.m_183550_($$18, $$27, $$19)) == null) continue;
                    $$12.m_183639_($$27, $$32);
                }
                if (!$$21.m_203565_(Biomes.f_48211_) && !$$21.m_203565_(Biomes.f_48172_)) continue;
                this.m_189934_($$13.m_189583_(), $$21.m_203334_(), $$12, $$15, $$18, $$19, $$20);
            }
        }
    }

    protected int m_189927_(int p_189928_, int p_189929_) {
        double $$2 = this.f_189918_.m_75380_(p_189928_, 0.0, p_189929_);
        return (int)($$2 * 2.75 + 3.0 + this.f_224635_.m_213715_(p_189928_, 0, p_189929_).m_188500_() * 0.25);
    }

    protected double m_202189_(int p_202190_, int p_202191_) {
        return this.f_189892_.m_75380_(p_202190_, 0.0, p_202191_);
    }

    private boolean m_189952_(BlockState p_189953_) {
        return !p_189953_.m_60795_() && p_189953_.m_60819_().m_76178_();
    }

    @Deprecated
    public Optional<BlockState> m_189971_(SurfaceRules.RuleSource p_189972_, CarvingContext p_189973_, Function<BlockPos, Holder<Biome>> p_189974_, ChunkAccess p_189975_, NoiseChunk p_189976_, BlockPos p_189977_, boolean p_189978_) {
        SurfaceRules.Context $$7 = new SurfaceRules.Context(this, p_189973_.m_224851_(), p_189975_, p_189976_, p_189974_, p_189973_.m_190651_().m_175515_(Registry.f_122885_), p_189973_);
        SurfaceRules.SurfaceRule $$8 = (SurfaceRules.SurfaceRule)p_189972_.apply($$7);
        int $$9 = p_189977_.m_123341_();
        int $$10 = p_189977_.m_123342_();
        int $$11 = p_189977_.m_123343_();
        $$7.m_189569_($$9, $$11);
        $$7.m_189576_(1, 1, p_189978_ ? $$10 + 1 : Integer.MIN_VALUE, $$9, $$10, $$11);
        BlockState $$12 = $$8.m_183550_($$9, $$10, $$11);
        return Optional.ofNullable($$12);
    }

    private void m_189954_(BlockColumn p_189955_, int p_189956_, int p_189957_, int p_189958_, LevelHeightAccessor p_189959_) {
        BlockState $$13;
        double $$5 = 0.2;
        double $$6 = Math.min(Math.abs(this.f_189910_.m_75380_(p_189956_, 0.0, p_189957_) * 8.25), this.f_189908_.m_75380_((double)p_189956_ * 0.2, 0.0, (double)p_189957_ * 0.2) * 15.0);
        if ($$6 <= 0.0) {
            return;
        }
        double $$7 = 0.75;
        double $$8 = 1.5;
        double $$9 = Math.abs(this.f_189909_.m_75380_((double)p_189956_ * 0.75, 0.0, (double)p_189957_ * 0.75) * 1.5);
        double $$10 = 64.0 + Math.min($$6 * $$6 * 2.5, Math.ceil($$9 * 50.0) + 24.0);
        int $$11 = Mth.m_14107_($$10);
        if (p_189958_ > $$11) {
            return;
        }
        for (int $$12 = $$11; $$12 >= p_189959_.m_141937_() && !($$13 = p_189955_.m_183556_($$12)).m_60713_(this.f_189904_.m_60734_()); --$$12) {
            if (!$$13.m_60713_(Blocks.f_49990_)) continue;
            return;
        }
        for (int $$14 = $$11; $$14 >= p_189959_.m_141937_() && p_189955_.m_183556_($$14).m_60795_(); --$$14) {
            p_189955_.m_183639_($$14, this.f_189904_);
        }
    }

    private void m_189934_(int p_189935_, Biome p_189936_, BlockColumn p_189937_, BlockPos.MutableBlockPos p_189938_, int p_189939_, int p_189940_, int p_189941_) {
        double $$14;
        double $$7 = 1.28;
        double $$8 = Math.min(Math.abs(this.f_189913_.m_75380_(p_189939_, 0.0, p_189940_) * 8.25), this.f_189911_.m_75380_((double)p_189939_ * 1.28, 0.0, (double)p_189940_ * 1.28) * 15.0);
        if ($$8 <= 1.8) {
            return;
        }
        double $$9 = 1.17;
        double $$10 = 1.5;
        double $$11 = Math.abs(this.f_189912_.m_75380_((double)p_189939_ * 1.17, 0.0, (double)p_189940_ * 1.17) * 1.5);
        double $$12 = Math.min($$8 * $$8 * 1.2, Math.ceil($$11 * 40.0) + 14.0);
        if (p_189936_.m_198908_(p_189938_.m_122178_(p_189939_, 63, p_189940_))) {
            $$12 -= 2.0;
        }
        if ($$12 > 2.0) {
            double $$13 = (double)this.f_189905_ - $$12 - 7.0;
            $$12 += (double)this.f_189905_;
        } else {
            $$12 = 0.0;
            $$14 = 0.0;
        }
        double $$15 = $$12;
        RandomSource $$16 = this.f_224635_.m_213715_(p_189939_, 0, p_189940_);
        int $$17 = 2 + $$16.m_188503_(4);
        int $$18 = this.f_189905_ + 18 + $$16.m_188503_(10);
        int $$19 = 0;
        for (int $$20 = Math.max(p_189941_, (int)$$15 + 1); $$20 >= p_189935_; --$$20) {
            if (!(p_189937_.m_183556_($$20).m_60795_() && $$20 < (int)$$15 && $$16.m_188500_() > 0.01) && (p_189937_.m_183556_($$20).m_60767_() != Material.f_76305_ || $$20 <= (int)$$14 || $$20 >= this.f_189905_ || $$14 == 0.0 || !($$16.m_188500_() > 0.15))) continue;
            if ($$19 <= $$17 && $$20 > $$18) {
                p_189937_.m_183639_($$20, f_189902_);
                ++$$19;
                continue;
            }
            p_189937_.m_183639_($$20, f_189901_);
        }
    }

    private static BlockState[] m_224641_(RandomSource p_224642_) {
        Object[] $$1 = new BlockState[192];
        Arrays.fill($$1, f_189896_);
        for (int $$2 = 0; $$2 < $$1.length; ++$$2) {
            if (($$2 += p_224642_.m_188503_(5) + 1) >= $$1.length) continue;
            $$1[$$2] = f_189895_;
        }
        SurfaceSystem.m_224643_(p_224642_, (BlockState[])$$1, 1, f_189897_);
        SurfaceSystem.m_224643_(p_224642_, (BlockState[])$$1, 2, f_189898_);
        SurfaceSystem.m_224643_(p_224642_, (BlockState[])$$1, 1, f_189899_);
        int $$3 = p_224642_.m_216332_(9, 15);
        int $$4 = 0;
        for (int $$5 = 0; $$4 < $$3 && $$5 < $$1.length; ++$$4, $$5 += p_224642_.m_188503_(16) + 4) {
            $$1[$$5] = f_189894_;
            if ($$5 - 1 > 0 && p_224642_.m_188499_()) {
                $$1[$$5 - 1] = f_189900_;
            }
            if ($$5 + 1 >= $$1.length || !p_224642_.m_188499_()) continue;
            $$1[$$5 + 1] = f_189900_;
        }
        return $$1;
    }

    private static void m_224643_(RandomSource p_224644_, BlockState[] p_224645_, int p_224646_, BlockState p_224647_) {
        int $$4 = p_224644_.m_216332_(6, 15);
        for (int $$5 = 0; $$5 < $$4; ++$$5) {
            int $$6 = p_224646_ + p_224644_.m_188503_(3);
            int $$7 = p_224644_.m_188503_(p_224645_.length);
            for (int $$8 = 0; $$7 + $$8 < p_224645_.length && $$8 < $$6; ++$$8) {
                p_224645_[$$7 + $$8] = p_224647_;
            }
        }
    }

    protected BlockState m_189930_(int p_189931_, int p_189932_, int p_189933_) {
        int $$3 = (int)Math.round(this.f_189907_.m_75380_(p_189931_, 0.0, p_189933_) * 4.0);
        return this.f_189906_[(p_189932_ + $$3 + this.f_189906_.length) % this.f_189906_.length];
    }
}

