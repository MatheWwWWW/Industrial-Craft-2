/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.longs.Long2IntMap
 *  it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ColumnPos;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.OreVeinifier;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.material.MaterialRuleList;

public class NoiseChunk
implements DensityFunction.ContextProvider,
DensityFunction.FunctionContext {
    private final NoiseSettings f_188717_;
    final int f_188718_;
    final int f_188719_;
    final int f_188720_;
    private final int f_188721_;
    private final int f_188722_;
    final int f_188723_;
    final int f_188724_;
    final List<NoiseInterpolator> f_188725_;
    final List<CacheAllInCell> f_209160_;
    private final Map<DensityFunction, DensityFunction> f_209161_ = new HashMap<DensityFunction, DensityFunction>();
    private final Long2IntMap f_198238_ = new Long2IntOpenHashMap();
    private final Aquifer f_188728_;
    private final DensityFunction f_209162_;
    private final BlockStateFiller f_209163_;
    private final Blender f_188731_;
    private final FlatCache f_209164_;
    private final FlatCache f_209165_;
    private final DensityFunctions.BeardifierOrMarker f_209166_;
    private long f_209167_ = ChunkPos.f_45577_;
    private Blender.BlendingOutput f_209168_ = new Blender.BlendingOutput(1.0, 0.0);
    final int f_209169_;
    final int f_209170_;
    final int f_209171_;
    boolean f_209172_;
    boolean f_209173_;
    private int f_209150_;
    int f_209151_;
    private int f_209152_;
    int f_209153_;
    int f_209154_;
    int f_209155_;
    long f_209156_;
    long f_209157_;
    int f_209158_;
    private final DensityFunction.ContextProvider f_209159_ = new DensityFunction.ContextProvider(){

        @Override
        public DensityFunction.FunctionContext m_207263_(int p_209253_) {
            NoiseChunk.this.f_209151_ = (p_209253_ + NoiseChunk.this.f_188720_) * NoiseChunk.this.f_209171_;
            ++NoiseChunk.this.f_209156_;
            NoiseChunk.this.f_209154_ = 0;
            NoiseChunk.this.f_209158_ = p_209253_;
            return NoiseChunk.this;
        }

        @Override
        public void m_207207_(double[] p_209255_, DensityFunction p_209256_) {
            for (int $$2 = 0; $$2 < NoiseChunk.this.f_188719_ + 1; ++$$2) {
                NoiseChunk.this.f_209151_ = ($$2 + NoiseChunk.this.f_188720_) * NoiseChunk.this.f_209171_;
                ++NoiseChunk.this.f_209156_;
                NoiseChunk.this.f_209154_ = 0;
                NoiseChunk.this.f_209158_ = $$2;
                p_209255_[$$2] = p_209256_.m_207386_(NoiseChunk.this);
            }
        }
    };

    public static NoiseChunk m_224352_(ChunkAccess p_224353_, RandomState p_224354_, DensityFunctions.BeardifierOrMarker p_224355_, NoiseGeneratorSettings p_224356_, Aquifer.FluidPicker p_224357_, Blender p_224358_) {
        NoiseSettings $$6 = p_224356_.f_64439_().m_224530_(p_224353_);
        ChunkPos $$7 = p_224353_.m_7697_();
        int $$8 = 16 / $$6.m_189213_();
        return new NoiseChunk($$8, p_224354_, $$7.m_45604_(), $$7.m_45605_(), $$6, p_224355_, p_224356_, p_224357_, p_224358_);
    }

    public NoiseChunk(int p_224343_, RandomState p_224344_, int p_224345_, int p_224346_, NoiseSettings p_224347_, DensityFunctions.BeardifierOrMarker p_224348_, NoiseGeneratorSettings p_224349_, Aquifer.FluidPicker p_224350_, Blender p_224351_) {
        this.f_188717_ = p_224347_;
        this.f_209170_ = p_224347_.m_189213_();
        this.f_209171_ = p_224347_.m_189212_();
        this.f_188718_ = p_224343_;
        this.f_188719_ = Mth.m_14042_(p_224347_.f_64508_(), this.f_209171_);
        this.f_188720_ = Mth.m_14042_(p_224347_.f_158688_(), this.f_209171_);
        this.f_188721_ = Math.floorDiv(p_224345_, this.f_209170_);
        this.f_188722_ = Math.floorDiv(p_224346_, this.f_209170_);
        this.f_188725_ = Lists.newArrayList();
        this.f_209160_ = Lists.newArrayList();
        this.f_188723_ = QuartPos.m_175400_(p_224345_);
        this.f_188724_ = QuartPos.m_175400_(p_224346_);
        this.f_209169_ = QuartPos.m_175400_(p_224343_ * this.f_209170_);
        this.f_188731_ = p_224351_;
        this.f_209166_ = p_224348_;
        this.f_209164_ = new FlatCache(new BlendAlpha(), false);
        this.f_209165_ = new FlatCache(new BlendOffset(), false);
        for (int $$9 = 0; $$9 <= this.f_209169_; ++$$9) {
            int $$10 = this.f_188723_ + $$9;
            int $$11 = QuartPos.m_175402_($$10);
            for (int $$12 = 0; $$12 <= this.f_209169_; ++$$12) {
                int $$13 = this.f_188724_ + $$12;
                int $$14 = QuartPos.m_175402_($$13);
                Blender.BlendingOutput $$15 = p_224351_.m_207242_($$11, $$14);
                this.f_209164_.f_209327_[$$9][$$12] = $$15.f_209729_();
                this.f_209165_.f_209327_[$$9][$$12] = $$15.f_209730_();
            }
        }
        NoiseRouter $$16 = p_224344_.m_224578_();
        NoiseRouter $$17 = $$16.m_224412_(this::m_209213_);
        if (!p_224349_.m_158567_()) {
            this.f_188728_ = Aquifer.m_188374_(p_224350_);
        } else {
            int $$18 = SectionPos.m_123171_(p_224345_);
            int $$19 = SectionPos.m_123171_(p_224346_);
            this.f_188728_ = Aquifer.m_223880_(this, new ChunkPos($$18, $$19), $$17, p_224344_.m_224581_(), p_224347_.f_158688_(), p_224347_.f_64508_(), p_224350_);
        }
        ImmutableList.Builder $$20 = ImmutableList.builder();
        DensityFunction $$21 = DensityFunctions.m_208387_(DensityFunctions.m_208293_($$17.f_209391_(), DensityFunctions.BeardifierMarker.INSTANCE)).m_207456_(this::m_209213_);
        $$20.add(p_209217_ -> this.f_188728_.m_207104_(p_209217_, $$21.m_207386_(p_209217_)));
        if (p_224349_.m_209369_()) {
            $$20.add((Object)OreVeinifier.m_209667_($$17.f_209392_(), $$17.f_209393_(), $$17.f_209394_(), p_224344_.m_224582_()));
        }
        this.f_209163_ = new MaterialRuleList((List<BlockStateFiller>)$$20.build());
        this.f_209162_ = $$17.f_209390_();
    }

    protected Climate.Sampler m_224359_(NoiseRouter p_224360_, List<Climate.ParameterPoint> p_224361_) {
        return new Climate.Sampler(p_224360_.f_209384_().m_207456_(this::m_209213_), p_224360_.f_224392_().m_207456_(this::m_209213_), p_224360_.f_209386_().m_207456_(this::m_209213_), p_224360_.f_209387_().m_207456_(this::m_209213_), p_224360_.f_209388_().m_207456_(this::m_209213_), p_224360_.f_209389_().m_207456_(this::m_209213_), p_224361_);
    }

    @Nullable
    protected BlockState m_209247_() {
        return this.f_209163_.m_207387_(this);
    }

    @Override
    public int m_207115_() {
        return this.f_209150_ + this.f_209153_;
    }

    @Override
    public int m_207114_() {
        return this.f_209151_ + this.f_209154_;
    }

    @Override
    public int m_207113_() {
        return this.f_209152_ + this.f_209155_;
    }

    public int m_198256_(int p_198257_, int p_198258_) {
        int $$2 = QuartPos.m_175402_(QuartPos.m_175400_(p_198257_));
        int $$3 = QuartPos.m_175402_(QuartPos.m_175400_(p_198258_));
        return this.f_198238_.computeIfAbsent(ColumnPos.m_143197_($$2, $$3), this::m_198249_);
    }

    private int m_198249_(long p_198250_) {
        int $$1 = ColumnPos.m_214969_(p_198250_);
        int $$2 = ColumnPos.m_214971_(p_198250_);
        int $$3 = this.f_188717_.f_158688_();
        for (int $$4 = $$3 + this.f_188717_.f_64508_(); $$4 >= $$3; $$4 -= this.f_209171_) {
            DensityFunction.SinglePointContext singlePointContext = new DensityFunction.SinglePointContext($$1, $$4, $$2);
            if (!(this.f_209162_.m_207386_(singlePointContext) > 0.390625)) continue;
            return $$4;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public Blender m_188743_() {
        return this.f_188731_;
    }

    private void m_209220_(boolean p_209221_, int p_209222_) {
        this.f_209150_ = p_209222_ * this.f_209170_;
        this.f_209153_ = 0;
        for (int $$2 = 0; $$2 < this.f_188718_ + 1; ++$$2) {
            int $$3 = this.f_188722_ + $$2;
            this.f_209152_ = $$3 * this.f_209170_;
            this.f_209155_ = 0;
            ++this.f_209157_;
            for (NoiseInterpolator $$4 : this.f_188725_) {
                double[] $$5 = (p_209221_ ? $$4.f_188828_ : $$4.f_188829_)[$$2];
                $$4.m_207362_($$5, this.f_209159_);
            }
        }
        ++this.f_209157_;
    }

    public void m_188791_() {
        if (this.f_209172_) {
            throw new IllegalStateException("Staring interpolation twice");
        }
        this.f_209172_ = true;
        this.f_209156_ = 0L;
        this.m_209220_(true, this.f_188721_);
    }

    public void m_188749_(int p_188750_) {
        this.m_209220_(false, this.f_188721_ + p_188750_ + 1);
        this.f_209150_ = (this.f_188721_ + p_188750_) * this.f_209170_;
    }

    @Override
    public NoiseChunk m_207263_(int p_209240_) {
        int $$1 = Math.floorMod(p_209240_, this.f_209170_);
        int $$2 = Math.floorDiv(p_209240_, this.f_209170_);
        int $$3 = Math.floorMod($$2, this.f_209170_);
        int $$4 = this.f_209171_ - 1 - Math.floorDiv($$2, this.f_209170_);
        this.f_209153_ = $$3;
        this.f_209154_ = $$4;
        this.f_209155_ = $$1;
        this.f_209158_ = p_209240_;
        return this;
    }

    @Override
    public void m_207207_(double[] p_209224_, DensityFunction p_209225_) {
        this.f_209158_ = 0;
        for (int $$2 = this.f_209171_ - 1; $$2 >= 0; --$$2) {
            this.f_209154_ = $$2;
            for (int $$3 = 0; $$3 < this.f_209170_; ++$$3) {
                this.f_209153_ = $$3;
                int $$4 = 0;
                while ($$4 < this.f_209170_) {
                    this.f_209155_ = $$4++;
                    p_209224_[this.f_209158_++] = p_209225_.m_207386_(this);
                }
            }
        }
    }

    public void m_188810_(int p_188811_, int p_188812_) {
        this.f_188725_.forEach(p_209205_ -> p_209205_.m_188863_(p_188811_, p_188812_));
        this.f_209173_ = true;
        this.f_209151_ = (p_188811_ + this.f_188720_) * this.f_209171_;
        this.f_209152_ = (this.f_188722_ + p_188812_) * this.f_209170_;
        ++this.f_209157_;
        for (CacheAllInCell $$2 : this.f_209160_) {
            $$2.f_209297_.m_207362_($$2.f_209298_, this);
        }
        ++this.f_209157_;
        this.f_209173_ = false;
    }

    public void m_209191_(int p_209192_, double p_209193_) {
        this.f_209154_ = p_209192_ - this.f_209151_;
        this.f_188725_.forEach(p_209238_ -> p_209238_.m_188850_(p_209193_));
    }

    public void m_209230_(int p_209231_, double p_209232_) {
        this.f_209153_ = p_209231_ - this.f_209150_;
        this.f_188725_.forEach(p_209229_ -> p_209229_.m_188861_(p_209232_));
    }

    public void m_209241_(int p_209242_, double p_209243_) {
        this.f_209155_ = p_209242_ - this.f_209152_;
        ++this.f_209156_;
        this.f_188725_.forEach(p_209188_ -> p_209188_.m_188866_(p_209243_));
    }

    public void m_209248_() {
        if (!this.f_209172_) {
            throw new IllegalStateException("Staring interpolation twice");
        }
        this.f_209172_ = false;
    }

    public void m_188804_() {
        this.f_188725_.forEach(NoiseInterpolator::m_188860_);
    }

    public Aquifer m_188817_() {
        return this.f_188728_;
    }

    protected int m_224362_() {
        return this.f_209170_;
    }

    protected int m_224363_() {
        return this.f_209171_;
    }

    Blender.BlendingOutput m_209244_(int p_209245_, int p_209246_) {
        Blender.BlendingOutput $$3;
        long $$2 = ChunkPos.m_45589_(p_209245_, p_209246_);
        if (this.f_209167_ == $$2) {
            return this.f_209168_;
        }
        this.f_209167_ = $$2;
        this.f_209168_ = $$3 = this.f_188731_.m_207242_(p_209245_, p_209246_);
        return $$3;
    }

    protected DensityFunction m_209213_(DensityFunction p_209214_) {
        return this.f_209161_.computeIfAbsent(p_209214_, this::m_209233_);
    }

    private DensityFunction m_209233_(DensityFunction p_209234_) {
        if (p_209234_ instanceof DensityFunctions.Marker) {
            DensityFunctions.Marker $$1 = (DensityFunctions.Marker)p_209234_;
            return switch ($$1.m_207136_()) {
                default -> throw new IncompatibleClassChangeError();
                case DensityFunctions.Marker.Type.Interpolated -> new NoiseInterpolator($$1.m_207056_());
                case DensityFunctions.Marker.Type.FlatCache -> new FlatCache($$1.m_207056_(), true);
                case DensityFunctions.Marker.Type.Cache2D -> new Cache2D($$1.m_207056_());
                case DensityFunctions.Marker.Type.CacheOnce -> new CacheOnce($$1.m_207056_());
                case DensityFunctions.Marker.Type.CacheAllInCell -> new CacheAllInCell($$1.m_207056_());
            };
        }
        if (this.f_188731_ != Blender.m_190153_()) {
            if (p_209234_ == DensityFunctions.BlendAlpha.INSTANCE) {
                return this.f_209164_;
            }
            if (p_209234_ == DensityFunctions.BlendOffset.INSTANCE) {
                return this.f_209165_;
            }
        }
        if (p_209234_ == DensityFunctions.BeardifierMarker.INSTANCE) {
            return this.f_209166_;
        }
        if (p_209234_ instanceof DensityFunctions.HolderHolder) {
            DensityFunctions.HolderHolder $$2 = (DensityFunctions.HolderHolder)p_209234_;
            return $$2.f_208636_().m_203334_();
        }
        return p_209234_;
    }

    @Override
    public /* synthetic */ DensityFunction.FunctionContext m_207263_(int n) {
        return this.m_207263_(n);
    }

    class FlatCache
    implements DensityFunctions.MarkerOrMarked,
    NoiseChunkDensityFunction {
        private final DensityFunction f_209326_;
        final double[][] f_209327_;

        FlatCache(DensityFunction p_209330_, boolean p_209331_) {
            this.f_209326_ = p_209330_;
            this.f_209327_ = new double[NoiseChunk.this.f_209169_ + 1][NoiseChunk.this.f_209169_ + 1];
            if (p_209331_) {
                for (int $$2 = 0; $$2 <= NoiseChunk.this.f_209169_; ++$$2) {
                    int $$3 = NoiseChunk.this.f_188723_ + $$2;
                    int $$4 = QuartPos.m_175402_($$3);
                    for (int $$5 = 0; $$5 <= NoiseChunk.this.f_209169_; ++$$5) {
                        int $$6 = NoiseChunk.this.f_188724_ + $$5;
                        int $$7 = QuartPos.m_175402_($$6);
                        this.f_209327_[$$2][$$5] = p_209330_.m_207386_(new DensityFunction.SinglePointContext($$4, 0, $$7));
                    }
                }
            }
        }

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_209333_) {
            int $$1 = QuartPos.m_175400_(p_209333_.m_207115_());
            int $$2 = QuartPos.m_175400_(p_209333_.m_207113_());
            int $$3 = $$1 - NoiseChunk.this.f_188723_;
            int $$4 = $$2 - NoiseChunk.this.f_188724_;
            int $$5 = this.f_209327_.length;
            if ($$3 >= 0 && $$4 >= 0 && $$3 < $$5 && $$4 < $$5) {
                return this.f_209327_[$$3][$$4];
            }
            return this.f_209326_.m_207386_(p_209333_);
        }

        @Override
        public void m_207362_(double[] p_209335_, DensityFunction.ContextProvider p_209336_) {
            p_209336_.m_207207_(p_209335_, this);
        }

        @Override
        public DensityFunction m_207056_() {
            return this.f_209326_;
        }

        @Override
        public DensityFunctions.Marker.Type m_207136_() {
            return DensityFunctions.Marker.Type.FlatCache;
        }
    }

    class BlendAlpha
    implements NoiseChunkDensityFunction {
        BlendAlpha() {
        }

        @Override
        public DensityFunction m_207056_() {
            return DensityFunctions.BlendAlpha.INSTANCE;
        }

        @Override
        public DensityFunction m_207456_(DensityFunction.Visitor p_224365_) {
            return this.m_207056_().m_207456_(p_224365_);
        }

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_209264_) {
            return NoiseChunk.this.m_209244_(p_209264_.m_207115_(), p_209264_.m_207113_()).f_209729_();
        }

        @Override
        public void m_207362_(double[] p_209266_, DensityFunction.ContextProvider p_209267_) {
            p_209267_.m_207207_(p_209266_, this);
        }

        @Override
        public double m_207402_() {
            return 0.0;
        }

        @Override
        public double m_207401_() {
            return 1.0;
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return DensityFunctions.BlendAlpha.f_208528_;
        }
    }

    class BlendOffset
    implements NoiseChunkDensityFunction {
        BlendOffset() {
        }

        @Override
        public DensityFunction m_207056_() {
            return DensityFunctions.BlendOffset.INSTANCE;
        }

        @Override
        public DensityFunction m_207456_(DensityFunction.Visitor p_224368_) {
            return this.m_207056_().m_207456_(p_224368_);
        }

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_209276_) {
            return NoiseChunk.this.m_209244_(p_209276_.m_207115_(), p_209276_.m_207113_()).f_209730_();
        }

        @Override
        public void m_207362_(double[] p_209278_, DensityFunction.ContextProvider p_209279_) {
            p_209279_.m_207207_(p_209278_, this);
        }

        @Override
        public double m_207402_() {
            return Double.NEGATIVE_INFINITY;
        }

        @Override
        public double m_207401_() {
            return Double.POSITIVE_INFINITY;
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return DensityFunctions.BlendOffset.f_208565_;
        }
    }

    @FunctionalInterface
    public static interface BlockStateFiller {
        @Nullable
        public BlockState m_207387_(DensityFunction.FunctionContext var1);
    }

    public class NoiseInterpolator
    implements DensityFunctions.MarkerOrMarked,
    NoiseChunkDensityFunction {
        double[][] f_188828_;
        double[][] f_188829_;
        private final DensityFunction f_188830_;
        private double f_188831_;
        private double f_188832_;
        private double f_188833_;
        private double f_188834_;
        private double f_188835_;
        private double f_188836_;
        private double f_188837_;
        private double f_188838_;
        private double f_188839_;
        private double f_188840_;
        private double f_188841_;
        private double f_188842_;
        private double f_188843_;
        private double f_188844_;
        private double f_188845_;

        NoiseInterpolator(DensityFunction p_209345_) {
            this.f_188830_ = p_209345_;
            this.f_188828_ = this.m_188854_(NoiseChunk.this.f_188719_, NoiseChunk.this.f_188718_);
            this.f_188829_ = this.m_188854_(NoiseChunk.this.f_188719_, NoiseChunk.this.f_188718_);
            NoiseChunk.this.f_188725_.add(this);
        }

        private double[][] m_188854_(int p_188855_, int p_188856_) {
            int $$2 = p_188856_ + 1;
            int $$3 = p_188855_ + 1;
            double[][] $$4 = new double[$$2][$$3];
            for (int $$5 = 0; $$5 < $$2; ++$$5) {
                $$4[$$5] = new double[$$3];
            }
            return $$4;
        }

        void m_188863_(int p_188864_, int p_188865_) {
            this.f_188831_ = this.f_188828_[p_188865_][p_188864_];
            this.f_188832_ = this.f_188828_[p_188865_ + 1][p_188864_];
            this.f_188833_ = this.f_188829_[p_188865_][p_188864_];
            this.f_188834_ = this.f_188829_[p_188865_ + 1][p_188864_];
            this.f_188835_ = this.f_188828_[p_188865_][p_188864_ + 1];
            this.f_188836_ = this.f_188828_[p_188865_ + 1][p_188864_ + 1];
            this.f_188837_ = this.f_188829_[p_188865_][p_188864_ + 1];
            this.f_188838_ = this.f_188829_[p_188865_ + 1][p_188864_ + 1];
        }

        void m_188850_(double p_188851_) {
            this.f_188839_ = Mth.m_14139_(p_188851_, this.f_188831_, this.f_188835_);
            this.f_188840_ = Mth.m_14139_(p_188851_, this.f_188833_, this.f_188837_);
            this.f_188841_ = Mth.m_14139_(p_188851_, this.f_188832_, this.f_188836_);
            this.f_188842_ = Mth.m_14139_(p_188851_, this.f_188834_, this.f_188838_);
        }

        void m_188861_(double p_188862_) {
            this.f_188843_ = Mth.m_14139_(p_188862_, this.f_188839_, this.f_188840_);
            this.f_188844_ = Mth.m_14139_(p_188862_, this.f_188841_, this.f_188842_);
        }

        void m_188866_(double p_188867_) {
            this.f_188845_ = Mth.m_14139_(p_188867_, this.f_188843_, this.f_188844_);
        }

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_209347_) {
            if (p_209347_ != NoiseChunk.this) {
                return this.f_188830_.m_207386_(p_209347_);
            }
            if (!NoiseChunk.this.f_209172_) {
                throw new IllegalStateException("Trying to sample interpolator outside the interpolation loop");
            }
            if (NoiseChunk.this.f_209173_) {
                return Mth.m_14019_((double)NoiseChunk.this.f_209153_ / (double)NoiseChunk.this.f_209170_, (double)NoiseChunk.this.f_209154_ / (double)NoiseChunk.this.f_209171_, (double)NoiseChunk.this.f_209155_ / (double)NoiseChunk.this.f_209170_, this.f_188831_, this.f_188833_, this.f_188835_, this.f_188837_, this.f_188832_, this.f_188834_, this.f_188836_, this.f_188838_);
            }
            return this.f_188845_;
        }

        @Override
        public void m_207362_(double[] p_209349_, DensityFunction.ContextProvider p_209350_) {
            if (NoiseChunk.this.f_209173_) {
                p_209350_.m_207207_(p_209349_, this);
                return;
            }
            this.m_207056_().m_207362_(p_209349_, p_209350_);
        }

        @Override
        public DensityFunction m_207056_() {
            return this.f_188830_;
        }

        private void m_188860_() {
            double[][] $$0 = this.f_188828_;
            this.f_188828_ = this.f_188829_;
            this.f_188829_ = $$0;
        }

        @Override
        public DensityFunctions.Marker.Type m_207136_() {
            return DensityFunctions.Marker.Type.Interpolated;
        }
    }

    class CacheAllInCell
    implements DensityFunctions.MarkerOrMarked,
    NoiseChunkDensityFunction {
        final DensityFunction f_209297_;
        final double[] f_209298_;

        CacheAllInCell(DensityFunction p_209301_) {
            this.f_209297_ = p_209301_;
            this.f_209298_ = new double[NoiseChunk.this.f_209170_ * NoiseChunk.this.f_209170_ * NoiseChunk.this.f_209171_];
            NoiseChunk.this.f_209160_.add(this);
        }

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_209303_) {
            if (p_209303_ != NoiseChunk.this) {
                return this.f_209297_.m_207386_(p_209303_);
            }
            if (!NoiseChunk.this.f_209172_) {
                throw new IllegalStateException("Trying to sample interpolator outside the interpolation loop");
            }
            int $$1 = NoiseChunk.this.f_209153_;
            int $$2 = NoiseChunk.this.f_209154_;
            int $$3 = NoiseChunk.this.f_209155_;
            if ($$1 >= 0 && $$2 >= 0 && $$3 >= 0 && $$1 < NoiseChunk.this.f_209170_ && $$2 < NoiseChunk.this.f_209171_ && $$3 < NoiseChunk.this.f_209170_) {
                return this.f_209298_[((NoiseChunk.this.f_209171_ - 1 - $$2) * NoiseChunk.this.f_209170_ + $$1) * NoiseChunk.this.f_209170_ + $$3];
            }
            return this.f_209297_.m_207386_(p_209303_);
        }

        @Override
        public void m_207362_(double[] p_209305_, DensityFunction.ContextProvider p_209306_) {
            p_209306_.m_207207_(p_209305_, this);
        }

        @Override
        public DensityFunction m_207056_() {
            return this.f_209297_;
        }

        @Override
        public DensityFunctions.Marker.Type m_207136_() {
            return DensityFunctions.Marker.Type.CacheAllInCell;
        }
    }

    static class Cache2D
    implements DensityFunctions.MarkerOrMarked,
    NoiseChunkDensityFunction {
        private final DensityFunction f_209284_;
        private long f_209285_ = ChunkPos.f_45577_;
        private double f_209286_;

        Cache2D(DensityFunction p_209288_) {
            this.f_209284_ = p_209288_;
        }

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_209290_) {
            double $$4;
            int $$2;
            int $$1 = p_209290_.m_207115_();
            long $$3 = ChunkPos.m_45589_($$1, $$2 = p_209290_.m_207113_());
            if (this.f_209285_ == $$3) {
                return this.f_209286_;
            }
            this.f_209285_ = $$3;
            this.f_209286_ = $$4 = this.f_209284_.m_207386_(p_209290_);
            return $$4;
        }

        @Override
        public void m_207362_(double[] p_209292_, DensityFunction.ContextProvider p_209293_) {
            this.f_209284_.m_207362_(p_209292_, p_209293_);
        }

        @Override
        public DensityFunction m_207056_() {
            return this.f_209284_;
        }

        @Override
        public DensityFunctions.Marker.Type m_207136_() {
            return DensityFunctions.Marker.Type.Cache2D;
        }
    }

    class CacheOnce
    implements DensityFunctions.MarkerOrMarked,
    NoiseChunkDensityFunction {
        private final DensityFunction f_209310_;
        private long f_209311_;
        private long f_209312_;
        private double f_209313_;
        @Nullable
        private double[] f_209314_;

        CacheOnce(DensityFunction p_209317_) {
            this.f_209310_ = p_209317_;
        }

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_209319_) {
            double $$1;
            if (p_209319_ != NoiseChunk.this) {
                return this.f_209310_.m_207386_(p_209319_);
            }
            if (this.f_209314_ != null && this.f_209312_ == NoiseChunk.this.f_209157_) {
                return this.f_209314_[NoiseChunk.this.f_209158_];
            }
            if (this.f_209311_ == NoiseChunk.this.f_209156_) {
                return this.f_209313_;
            }
            this.f_209311_ = NoiseChunk.this.f_209156_;
            this.f_209313_ = $$1 = this.f_209310_.m_207386_(p_209319_);
            return $$1;
        }

        @Override
        public void m_207362_(double[] p_209321_, DensityFunction.ContextProvider p_209322_) {
            if (this.f_209314_ != null && this.f_209312_ == NoiseChunk.this.f_209157_) {
                System.arraycopy(this.f_209314_, 0, p_209321_, 0, p_209321_.length);
                return;
            }
            this.m_207056_().m_207362_(p_209321_, p_209322_);
            if (this.f_209314_ != null && this.f_209314_.length == p_209321_.length) {
                System.arraycopy(p_209321_, 0, this.f_209314_, 0, p_209321_.length);
            } else {
                this.f_209314_ = (double[])p_209321_.clone();
            }
            this.f_209312_ = NoiseChunk.this.f_209157_;
        }

        @Override
        public DensityFunction m_207056_() {
            return this.f_209310_;
        }

        @Override
        public DensityFunctions.Marker.Type m_207136_() {
            return DensityFunctions.Marker.Type.CacheOnce;
        }
    }

    static interface NoiseChunkDensityFunction
    extends DensityFunction {
        public DensityFunction m_207056_();

        @Override
        default public double m_207402_() {
            return this.m_207056_().m_207402_();
        }

        @Override
        default public double m_207401_() {
            return this.m_207056_().m_207401_();
        }
    }
}

