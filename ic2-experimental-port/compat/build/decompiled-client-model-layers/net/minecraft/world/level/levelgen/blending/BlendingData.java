/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.primitives.Doubles
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen.blending;

import com.google.common.primitives.Doubles;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction8;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;

public class BlendingData {
    private static final double f_190255_ = 0.1;
    protected static final int f_198290_ = 4;
    protected static final int f_190252_ = 8;
    protected static final int f_198291_ = 2;
    private static final double f_224736_ = 1.0;
    private static final double f_224737_ = -1.0;
    private static final int f_190257_ = 2;
    private static final int f_190258_ = QuartPos.m_175400_(16);
    private static final int f_190259_ = f_190258_ - 1;
    private static final int f_190260_ = f_190258_;
    private static final int f_190261_ = 2 * f_190259_ + 1;
    private static final int f_190262_ = 2 * f_190260_ + 1;
    private static final int f_190263_ = f_190261_ + f_190262_;
    private final LevelHeightAccessor f_224738_;
    private static final List<Block> f_190265_ = List.of(Blocks.f_50599_, Blocks.f_49994_, Blocks.f_50440_, Blocks.f_50069_, Blocks.f_50546_, Blocks.f_49992_, Blocks.f_49993_, Blocks.f_50195_, Blocks.f_50127_, Blocks.f_50352_, Blocks.f_50493_);
    protected static final double f_190253_ = Double.MAX_VALUE;
    private boolean f_190267_;
    private final double[] f_190269_;
    private final List<List<Holder<Biome>>> f_202276_;
    private final transient double[][] f_190270_;
    private static final Codec<double[]> f_190272_ = Codec.DOUBLE.listOf().xmap(Doubles::toArray, Doubles::asList);
    public static final Codec<BlendingData> f_190254_ = RecordCodecBuilder.create(p_224754_ -> p_224754_.group((App)Codec.INT.fieldOf("min_section").forGetter(p_224767_ -> p_224767_.f_224738_.m_151560_()), (App)Codec.INT.fieldOf("max_section").forGetter(p_224765_ -> p_224765_.f_224738_.m_151561_()), (App)f_190272_.optionalFieldOf("heights").forGetter(p_224762_ -> DoubleStream.of(p_224762_.f_190269_).anyMatch(p_224745_ -> p_224745_ != Double.MAX_VALUE) ? Optional.of(p_224762_.f_190269_) : Optional.empty())).apply((Applicative)p_224754_, BlendingData::new)).comapFlatMap(BlendingData::m_190320_, Function.identity());

    private static DataResult<BlendingData> m_190320_(BlendingData p_190321_) {
        if (p_190321_.f_190269_.length != f_190263_) {
            return DataResult.error((String)("heights has to be of length " + f_190263_));
        }
        return DataResult.success((Object)p_190321_);
    }

    private BlendingData(int p_224740_, int p_224741_, Optional<double[]> p_224742_) {
        this.f_190269_ = p_224742_.orElse(Util.m_137469_(new double[f_190263_], p_224756_ -> Arrays.fill(p_224756_, Double.MAX_VALUE)));
        this.f_190270_ = new double[f_190263_][];
        ObjectArrayList $$3 = new ObjectArrayList(f_190263_);
        $$3.size(f_190263_);
        this.f_202276_ = $$3;
        int $$4 = SectionPos.m_123223_(p_224740_);
        int $$5 = SectionPos.m_123223_(p_224741_) - $$4;
        this.f_224738_ = LevelHeightAccessor.m_186487_($$4, $$5);
    }

    @Nullable
    public static BlendingData m_190304_(WorldGenRegion p_190305_, int p_190306_, int p_190307_) {
        ChunkAccess $$3 = p_190305_.m_6325_(p_190306_, p_190307_);
        BlendingData $$4 = $$3.m_183407_();
        if ($$4 == null) {
            return null;
        }
        $$4.m_190317_($$3, BlendingData.m_197065_(p_190305_, p_190306_, p_190307_, false));
        return $$4;
    }

    public static Set<Direction8> m_197065_(WorldGenLevel p_197066_, int p_197067_, int p_197068_, boolean p_197069_) {
        EnumSet<Direction8> $$4 = EnumSet.noneOf(Direction8.class);
        for (Direction8 $$5 : Direction8.values()) {
            int $$7;
            int $$6 = p_197067_ + $$5.m_235697_();
            if (p_197066_.m_6325_($$6, $$7 = p_197068_ + $$5.m_235698_()).m_187675_() != p_197069_) continue;
            $$4.add($$5);
        }
        return $$4;
    }

    private void m_190317_(ChunkAccess p_190318_, Set<Direction8> p_190319_) {
        if (this.f_190267_) {
            return;
        }
        if (p_190319_.contains((Object)Direction8.NORTH) || p_190319_.contains((Object)Direction8.WEST) || p_190319_.contains((Object)Direction8.NORTH_WEST)) {
            this.m_190299_(BlendingData.m_190330_(0, 0), p_190318_, 0, 0);
        }
        if (p_190319_.contains((Object)Direction8.NORTH)) {
            for (int $$2 = 1; $$2 < f_190258_; ++$$2) {
                this.m_190299_(BlendingData.m_190330_($$2, 0), p_190318_, 4 * $$2, 0);
            }
        }
        if (p_190319_.contains((Object)Direction8.WEST)) {
            for (int $$3 = 1; $$3 < f_190258_; ++$$3) {
                this.m_190299_(BlendingData.m_190330_(0, $$3), p_190318_, 0, 4 * $$3);
            }
        }
        if (p_190319_.contains((Object)Direction8.EAST)) {
            for (int $$4 = 1; $$4 < f_190258_; ++$$4) {
                this.m_190299_(BlendingData.m_190350_(f_190260_, $$4), p_190318_, 15, 4 * $$4);
            }
        }
        if (p_190319_.contains((Object)Direction8.SOUTH)) {
            for (int $$5 = 0; $$5 < f_190258_; ++$$5) {
                this.m_190299_(BlendingData.m_190350_($$5, f_190260_), p_190318_, 4 * $$5, 15);
            }
        }
        if (p_190319_.contains((Object)Direction8.EAST) && p_190319_.contains((Object)Direction8.NORTH_EAST)) {
            this.m_190299_(BlendingData.m_190350_(f_190260_, 0), p_190318_, 15, 0);
        }
        if (p_190319_.contains((Object)Direction8.EAST) && p_190319_.contains((Object)Direction8.SOUTH) && p_190319_.contains((Object)Direction8.SOUTH_EAST)) {
            this.m_190299_(BlendingData.m_190350_(f_190260_, f_190260_), p_190318_, 15, 15);
        }
        this.f_190267_ = true;
    }

    private void m_190299_(int p_190300_, ChunkAccess p_190301_, int p_190302_, int p_190303_) {
        if (this.f_190269_[p_190300_] == Double.MAX_VALUE) {
            this.f_190269_[p_190300_] = this.m_190310_(p_190301_, p_190302_, p_190303_);
        }
        this.f_190270_[p_190300_] = this.m_198292_(p_190301_, p_190302_, p_190303_, Mth.m_14107_(this.f_190269_[p_190300_]));
        this.f_202276_.set(p_190300_, this.m_224757_(p_190301_, p_190302_, p_190303_));
    }

    private int m_190310_(ChunkAccess p_190311_, int p_190312_, int p_190313_) {
        int $$4;
        if (p_190311_.m_187658_(Heightmap.Types.WORLD_SURFACE_WG)) {
            int $$3 = Math.min(p_190311_.m_5885_(Heightmap.Types.WORLD_SURFACE_WG, p_190312_, p_190313_) + 1, this.f_224738_.m_151558_());
        } else {
            $$4 = this.f_224738_.m_151558_();
        }
        int $$5 = this.f_224738_.m_141937_();
        BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos(p_190312_, $$4, p_190313_);
        while ($$6.m_123342_() > $$5) {
            $$6.m_122173_(Direction.DOWN);
            if (!f_190265_.contains(p_190311_.m_8055_($$6).m_60734_())) continue;
            return $$6.m_123342_();
        }
        return $$5;
    }

    private static double m_198297_(ChunkAccess p_198298_, BlockPos.MutableBlockPos p_198299_) {
        return BlendingData.m_190314_(p_198298_, p_198299_.m_122173_(Direction.DOWN)) ? 1.0 : -1.0;
    }

    private static double m_198300_(ChunkAccess p_198301_, BlockPos.MutableBlockPos p_198302_) {
        double $$2 = 0.0;
        for (int $$3 = 0; $$3 < 7; ++$$3) {
            $$2 += BlendingData.m_198297_(p_198301_, p_198302_);
        }
        return $$2;
    }

    private double[] m_198292_(ChunkAccess p_198293_, int p_198294_, int p_198295_, int p_198296_) {
        double[] $$4 = new double[this.m_190327_()];
        Arrays.fill($$4, -1.0);
        BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos(p_198294_, this.f_224738_.m_151558_(), p_198295_);
        double $$6 = BlendingData.m_198300_(p_198293_, $$5);
        for (int $$7 = $$4.length - 2; $$7 >= 0; --$$7) {
            double $$8 = BlendingData.m_198297_(p_198293_, $$5);
            double $$9 = BlendingData.m_198300_(p_198293_, $$5);
            $$4[$$7] = ($$6 + $$8 + $$9) / 15.0;
            $$6 = $$9;
        }
        int $$10 = this.m_224746_(Mth.m_14042_(p_198296_, 8));
        if ($$10 >= 0 && $$10 < $$4.length - 1) {
            double $$11 = ((double)p_198296_ + 0.5) % 8.0 / 8.0;
            double $$12 = (1.0 - $$11) / $$11;
            double $$13 = Math.max($$12, 1.0) * 0.25;
            $$4[$$10 + 1] = -$$12 / $$13;
            $$4[$$10] = 1.0 / $$13;
        }
        return $$4;
    }

    private List<Holder<Biome>> m_224757_(ChunkAccess p_224758_, int p_224759_, int p_224760_) {
        ObjectArrayList $$3 = new ObjectArrayList(this.m_224763_());
        $$3.size(this.m_224763_());
        for (int $$4 = 0; $$4 < $$3.size(); ++$$4) {
            int $$5 = $$4 + QuartPos.m_175400_(this.f_224738_.m_141937_());
            $$3.set($$4, p_224758_.m_203495_(QuartPos.m_175400_(p_224759_), $$5, QuartPos.m_175400_(p_224760_)));
        }
        return $$3;
    }

    private static boolean m_190314_(ChunkAccess p_190315_, BlockPos p_190316_) {
        BlockState $$2 = p_190315_.m_8055_(p_190316_);
        if ($$2.m_60795_()) {
            return false;
        }
        if ($$2.m_204336_(BlockTags.f_13035_)) {
            return false;
        }
        if ($$2.m_204336_(BlockTags.f_13106_)) {
            return false;
        }
        if ($$2.m_60713_(Blocks.f_50180_) || $$2.m_60713_(Blocks.f_50181_)) {
            return false;
        }
        return !$$2.m_60812_(p_190315_, p_190316_).m_83281_();
    }

    protected double m_190285_(int p_190286_, int p_190287_, int p_190288_) {
        if (p_190286_ == f_190260_ || p_190288_ == f_190260_) {
            return this.f_190269_[BlendingData.m_190350_(p_190286_, p_190288_)];
        }
        if (p_190286_ == 0 || p_190288_ == 0) {
            return this.f_190269_[BlendingData.m_190330_(p_190286_, p_190288_)];
        }
        return Double.MAX_VALUE;
    }

    private double m_190324_(@Nullable double[] p_190325_, int p_190326_) {
        if (p_190325_ == null) {
            return Double.MAX_VALUE;
        }
        int $$2 = this.m_224746_(p_190326_);
        if ($$2 < 0 || $$2 >= p_190325_.length) {
            return Double.MAX_VALUE;
        }
        return p_190325_[$$2] * 0.1;
    }

    protected double m_190333_(int p_190334_, int p_190335_, int p_190336_) {
        if (p_190335_ == this.m_190353_()) {
            return 0.1;
        }
        if (p_190334_ == f_190260_ || p_190336_ == f_190260_) {
            return this.m_190324_(this.f_190270_[BlendingData.m_190350_(p_190334_, p_190336_)], p_190335_);
        }
        if (p_190334_ == 0 || p_190336_ == 0) {
            return this.m_190324_(this.f_190270_[BlendingData.m_190330_(p_190334_, p_190336_)], p_190335_);
        }
        return Double.MAX_VALUE;
    }

    protected void m_224748_(int p_224749_, int p_224750_, int p_224751_, BiomeConsumer p_224752_) {
        if (p_224750_ < QuartPos.m_175400_(this.f_224738_.m_141937_()) || p_224750_ >= QuartPos.m_175400_(this.f_224738_.m_151558_())) {
            return;
        }
        int $$4 = p_224750_ - QuartPos.m_175400_(this.f_224738_.m_141937_());
        for (int $$5 = 0; $$5 < this.f_202276_.size(); ++$$5) {
            Holder<Biome> $$6;
            if (this.f_202276_.get($$5) == null || ($$6 = this.f_202276_.get($$5).get($$4)) == null) continue;
            p_224752_.m_204673_(p_224749_ + BlendingData.m_190348_($$5), p_224751_ + BlendingData.m_190354_($$5), $$6);
        }
    }

    protected void m_190295_(int p_190296_, int p_190297_, HeightConsumer p_190298_) {
        for (int $$3 = 0; $$3 < this.f_190269_.length; ++$$3) {
            double $$4 = this.f_190269_[$$3];
            if ($$4 == Double.MAX_VALUE) continue;
            p_190298_.m_190366_(p_190296_ + BlendingData.m_190348_($$3), p_190297_ + BlendingData.m_190354_($$3), $$4);
        }
    }

    protected void m_190289_(int p_190290_, int p_190291_, int p_190292_, int p_190293_, DensityConsumer p_190294_) {
        int $$5 = this.m_190347_();
        int $$6 = Math.max(0, p_190292_ - $$5);
        int $$7 = Math.min(this.m_190327_(), p_190293_ - $$5);
        for (int $$8 = 0; $$8 < this.f_190270_.length; ++$$8) {
            double[] $$9 = this.f_190270_[$$8];
            if ($$9 == null) continue;
            int $$10 = p_190290_ + BlendingData.m_190348_($$8);
            int $$11 = p_190291_ + BlendingData.m_190354_($$8);
            for (int $$12 = $$6; $$12 < $$7; ++$$12) {
                p_190294_.m_190361_($$10, $$12 + $$5, $$11, $$9[$$12] * 0.1);
            }
        }
    }

    private int m_190327_() {
        return this.f_224738_.m_151559_() * 2;
    }

    private int m_224763_() {
        return QuartPos.m_175404_(this.f_224738_.m_151559_());
    }

    private int m_190347_() {
        return this.m_190353_() + 1;
    }

    private int m_190353_() {
        return this.f_224738_.m_151560_() * 2;
    }

    private int m_224746_(int p_224747_) {
        return p_224747_ - this.m_190347_();
    }

    private static int m_190330_(int p_190331_, int p_190332_) {
        return f_190259_ - p_190331_ + p_190332_;
    }

    private static int m_190350_(int p_190351_, int p_190352_) {
        return f_190261_ + p_190351_ + f_190260_ - p_190352_;
    }

    private static int m_190348_(int p_190349_) {
        if (p_190349_ < f_190261_) {
            return BlendingData.m_190356_(f_190259_ - p_190349_);
        }
        int $$1 = p_190349_ - f_190261_;
        return f_190260_ - BlendingData.m_190356_(f_190260_ - $$1);
    }

    private static int m_190354_(int p_190355_) {
        if (p_190355_ < f_190261_) {
            return BlendingData.m_190356_(p_190355_ - f_190259_);
        }
        int $$1 = p_190355_ - f_190261_;
        return f_190260_ - BlendingData.m_190356_($$1 - f_190260_);
    }

    private static int m_190356_(int p_190357_) {
        return p_190357_ & ~(p_190357_ >> 31);
    }

    public LevelHeightAccessor m_224743_() {
        return this.f_224738_;
    }

    protected static interface BiomeConsumer {
        public void m_204673_(int var1, int var2, Holder<Biome> var3);
    }

    protected static interface HeightConsumer {
        public void m_190366_(int var1, int var2, double var3);
    }

    protected static interface DensityConsumer {
        public void m_190361_(int var1, int var2, int var3, double var4);
    }
}

