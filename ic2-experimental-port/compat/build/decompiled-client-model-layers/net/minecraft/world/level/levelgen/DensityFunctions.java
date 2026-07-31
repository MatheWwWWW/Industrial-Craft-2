/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Either
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.doubles.Double2DoubleFunction
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.CubicSpline;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.ToFloatFunction;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
import org.slf4j.Logger;

public final class DensityFunctions {
    private static final Codec<DensityFunction> f_208259_ = Registry.f_211076_.m_194605_().dispatch(p_224053_ -> p_224053_.m_214023_().f_216232_(), Function.identity());
    protected static final double f_208257_ = 1000000.0;
    static final Codec<Double> f_208260_ = Codec.doubleRange((double)-1000000.0, (double)1000000.0);
    public static final Codec<DensityFunction> f_208258_ = Codec.either(f_208260_, f_208259_).xmap(p_224023_ -> (DensityFunction)p_224023_.map(DensityFunctions::m_208264_, Function.identity()), p_224051_ -> {
        if (p_224051_ instanceof Constant) {
            Constant $$1 = (Constant)p_224051_;
            return Either.left((Object)$$1.f_208607_());
        }
        return Either.right((Object)p_224051_);
    });

    public static Codec<? extends DensityFunction> m_208342_(Registry<Codec<? extends DensityFunction>> p_208343_) {
        DensityFunctions.m_224034_(p_208343_, "blend_alpha", BlendAlpha.f_208528_);
        DensityFunctions.m_224034_(p_208343_, "blend_offset", BlendOffset.f_208565_);
        DensityFunctions.m_224034_(p_208343_, "beardifier", BeardifierMarker.f_208524_);
        DensityFunctions.m_224034_(p_208343_, "old_blended_noise", BlendedNoise.f_210616_);
        for (Marker.Type type : Marker.Type.values()) {
            DensityFunctions.m_224034_(p_208343_, type.m_7912_(), type.f_208731_);
        }
        DensityFunctions.m_224034_(p_208343_, "noise", Noise.f_208785_);
        DensityFunctions.m_224034_(p_208343_, "end_islands", EndIslandDensityFunction.f_208626_);
        DensityFunctions.m_224034_(p_208343_, "weird_scaled_sampler", WeirdScaledSampler.f_208424_);
        DensityFunctions.m_224034_(p_208343_, "shifted_noise", ShiftedNoise.f_208923_);
        DensityFunctions.m_224034_(p_208343_, "range_choice", RangeChoice.f_208822_);
        DensityFunctions.m_224034_(p_208343_, "shift_a", ShiftA.f_208878_);
        DensityFunctions.m_224034_(p_208343_, "shift_b", ShiftB.f_208898_);
        DensityFunctions.m_224034_(p_208343_, "shift", Shift.f_208858_);
        DensityFunctions.m_224034_(p_208343_, "blend_density", BlendDensity.f_208547_);
        DensityFunctions.m_224034_(p_208343_, "clamp", Clamp.f_208583_);
        for (Enum enum_ : Mapped.Type.values()) {
            DensityFunctions.m_224034_(p_208343_, ((Mapped.Type)enum_).m_7912_(), ((Mapped.Type)enum_).f_208691_);
        }
        for (Enum enum_ : TwoArgumentSimpleFunction.Type.values()) {
            DensityFunctions.m_224034_(p_208343_, ((TwoArgumentSimpleFunction.Type)enum_).m_7912_(), ((TwoArgumentSimpleFunction.Type)enum_).f_209082_);
        }
        DensityFunctions.m_224034_(p_208343_, "spline", Spline.f_211701_);
        DensityFunctions.m_224034_(p_208343_, "constant", Constant.f_208608_);
        return DensityFunctions.m_224034_(p_208343_, "y_clamped_gradient", YClampedGradient.f_208480_);
    }

    private static Codec<? extends DensityFunction> m_224034_(Registry<Codec<? extends DensityFunction>> p_224035_, String p_224036_, KeyDispatchDataCodec<? extends DensityFunction> p_224037_) {
        return Registry.m_122961_(p_224035_, p_224036_, p_224037_.f_216232_());
    }

    static <A, O> KeyDispatchDataCodec<O> m_224024_(Codec<A> p_224025_, Function<A, O> p_224026_, Function<O, A> p_224027_) {
        return KeyDispatchDataCodec.m_216238_(p_224025_.fieldOf("argument").xmap(p_224026_, p_224027_));
    }

    static <O> KeyDispatchDataCodec<O> m_224042_(Function<DensityFunction, O> p_224043_, Function<O, DensityFunction> p_224044_) {
        return DensityFunctions.m_224024_(DensityFunction.f_208218_, p_224043_, p_224044_);
    }

    static <O> KeyDispatchDataCodec<O> m_224038_(BiFunction<DensityFunction, DensityFunction, O> p_224039_, Function<O, DensityFunction> p_224040_, Function<O, DensityFunction> p_224041_) {
        return KeyDispatchDataCodec.m_216238_(RecordCodecBuilder.mapCodec(p_224049_ -> p_224049_.group((App)DensityFunction.f_208218_.fieldOf("argument1").forGetter(p_224040_), (App)DensityFunction.f_208218_.fieldOf("argument2").forGetter(p_224041_)).apply((Applicative)p_224049_, p_224039_)));
    }

    static <O> KeyDispatchDataCodec<O> m_224028_(MapCodec<O> p_224029_) {
        return KeyDispatchDataCodec.m_216238_(p_224029_);
    }

    private DensityFunctions() {
    }

    public static DensityFunction m_208281_(DensityFunction p_208282_) {
        return new Marker(Marker.Type.Interpolated, p_208282_);
    }

    public static DensityFunction m_208361_(DensityFunction p_208362_) {
        return new Marker(Marker.Type.FlatCache, p_208362_);
    }

    public static DensityFunction m_208373_(DensityFunction p_208374_) {
        return new Marker(Marker.Type.Cache2D, p_208374_);
    }

    public static DensityFunction m_208380_(DensityFunction p_208381_) {
        return new Marker(Marker.Type.CacheOnce, p_208381_);
    }

    public static DensityFunction m_208387_(DensityFunction p_208388_) {
        return new Marker(Marker.Type.CacheAllInCell, p_208388_);
    }

    public static DensityFunction m_208336_(Holder<NormalNoise.NoiseParameters> p_208337_, @Deprecated double p_208338_, double p_208339_, double p_208340_, double p_208341_) {
        return DensityFunctions.m_208283_(new Noise(new DensityFunction.NoiseHolder(p_208337_), p_208338_, p_208339_), p_208340_, p_208341_);
    }

    public static DensityFunction m_208331_(Holder<NormalNoise.NoiseParameters> p_208332_, double p_208333_, double p_208334_, double p_208335_) {
        return DensityFunctions.m_208336_(p_208332_, 1.0, p_208333_, p_208334_, p_208335_);
    }

    public static DensityFunction m_208327_(Holder<NormalNoise.NoiseParameters> p_208328_, double p_208329_, double p_208330_) {
        return DensityFunctions.m_208336_(p_208328_, 1.0, 1.0, p_208329_, p_208330_);
    }

    public static DensityFunction m_208296_(DensityFunction p_208297_, DensityFunction p_208298_, double p_208299_, Holder<NormalNoise.NoiseParameters> p_208300_) {
        return new ShiftedNoise(p_208297_, DensityFunctions.m_208263_(), p_208298_, p_208299_, 0.0, new DensityFunction.NoiseHolder(p_208300_));
    }

    public static DensityFunction m_208322_(Holder<NormalNoise.NoiseParameters> p_208323_) {
        return DensityFunctions.m_208368_(p_208323_, 1.0, 1.0);
    }

    public static DensityFunction m_208368_(Holder<NormalNoise.NoiseParameters> p_208369_, double p_208370_, double p_208371_) {
        return new Noise(new DensityFunction.NoiseHolder(p_208369_), p_208370_, p_208371_);
    }

    public static DensityFunction m_208324_(Holder<NormalNoise.NoiseParameters> p_208325_, double p_208326_) {
        return DensityFunctions.m_208368_(p_208325_, 1.0, p_208326_);
    }

    public static DensityFunction m_208287_(DensityFunction p_208288_, double p_208289_, double p_208290_, DensityFunction p_208291_, DensityFunction p_208292_) {
        return new RangeChoice(p_208288_, p_208289_, p_208290_, p_208291_, p_208292_);
    }

    public static DensityFunction m_208366_(Holder<NormalNoise.NoiseParameters> p_208367_) {
        return new ShiftA(new DensityFunction.NoiseHolder(p_208367_));
    }

    public static DensityFunction m_208378_(Holder<NormalNoise.NoiseParameters> p_208379_) {
        return new ShiftB(new DensityFunction.NoiseHolder(p_208379_));
    }

    public static DensityFunction m_208385_(Holder<NormalNoise.NoiseParameters> p_208386_) {
        return new Shift(new DensityFunction.NoiseHolder(p_208386_));
    }

    public static DensityFunction m_208389_(DensityFunction p_208390_) {
        return new BlendDensity(p_208390_);
    }

    public static DensityFunction m_208271_(long p_208272_) {
        return new EndIslandDensityFunction(p_208272_);
    }

    public static DensityFunction m_208315_(DensityFunction p_208316_, Holder<NormalNoise.NoiseParameters> p_208317_, WeirdScaledSampler.RarityValueMapper p_208318_) {
        return new WeirdScaledSampler(p_208316_, new DensityFunction.NoiseHolder(p_208317_), p_208318_);
    }

    public static DensityFunction m_208293_(DensityFunction p_208294_, DensityFunction p_208295_) {
        return TwoArgumentSimpleFunction.m_209073_(TwoArgumentSimpleFunction.Type.ADD, p_208294_, p_208295_);
    }

    public static DensityFunction m_208363_(DensityFunction p_208364_, DensityFunction p_208365_) {
        return TwoArgumentSimpleFunction.m_209073_(TwoArgumentSimpleFunction.Type.MUL, p_208364_, p_208365_);
    }

    public static DensityFunction m_208375_(DensityFunction p_208376_, DensityFunction p_208377_) {
        return TwoArgumentSimpleFunction.m_209073_(TwoArgumentSimpleFunction.Type.MIN, p_208376_, p_208377_);
    }

    public static DensityFunction m_208382_(DensityFunction p_208383_, DensityFunction p_208384_) {
        return TwoArgumentSimpleFunction.m_209073_(TwoArgumentSimpleFunction.Type.MAX, p_208383_, p_208384_);
    }

    public static DensityFunction m_224020_(CubicSpline<Spline.Point, Spline.Coordinate> p_224021_) {
        return new Spline(p_224021_);
    }

    public static DensityFunction m_208263_() {
        return Constant.f_208609_;
    }

    public static DensityFunction m_208264_(double p_208265_) {
        return new Constant(p_208265_);
    }

    public static DensityFunction m_208266_(int p_208267_, int p_208268_, double p_208269_, double p_208270_) {
        return new YClampedGradient(p_208267_, p_208268_, p_208269_, p_208270_);
    }

    public static DensityFunction m_208312_(DensityFunction p_208313_, Mapped.Type p_208314_) {
        return Mapped.m_208671_(p_208314_, p_208313_);
    }

    private static DensityFunction m_208283_(DensityFunction p_208284_, double p_208285_, double p_208286_) {
        double $$3 = (p_208285_ + p_208286_) * 0.5;
        double $$4 = (p_208286_ - p_208285_) * 0.5;
        return DensityFunctions.m_208293_(DensityFunctions.m_208264_($$3), DensityFunctions.m_208363_(DensityFunctions.m_208264_($$4), p_208284_));
    }

    public static DensityFunction m_208360_() {
        return BlendAlpha.INSTANCE;
    }

    public static DensityFunction m_208372_() {
        return BlendOffset.INSTANCE;
    }

    public static DensityFunction m_208301_(DensityFunction p_208302_, DensityFunction p_208303_, DensityFunction p_208304_) {
        if (p_208303_ instanceof Constant) {
            Constant $$3 = (Constant)p_208303_;
            return DensityFunctions.m_224030_(p_208302_, $$3.f_208607_, p_208304_);
        }
        DensityFunction $$4 = DensityFunctions.m_208380_(p_208302_);
        DensityFunction $$5 = DensityFunctions.m_208293_(DensityFunctions.m_208363_($$4, DensityFunctions.m_208264_(-1.0)), DensityFunctions.m_208264_(1.0));
        return DensityFunctions.m_208293_(DensityFunctions.m_208363_(p_208303_, $$5), DensityFunctions.m_208363_(p_208304_, $$4));
    }

    public static DensityFunction m_224030_(DensityFunction p_224031_, double p_224032_, DensityFunction p_224033_) {
        return DensityFunctions.m_208293_(DensityFunctions.m_208363_(p_224031_, DensityFunctions.m_208293_(p_224033_, DensityFunctions.m_208264_(-p_224032_))), DensityFunctions.m_208264_(p_224032_));
    }

    protected static final class BlendAlpha
    extends Enum<BlendAlpha>
    implements DensityFunction.SimpleFunction {
        public static final /* enum */ BlendAlpha INSTANCE = new BlendAlpha();
        public static final KeyDispatchDataCodec<DensityFunction> f_208528_;
        private static final /* synthetic */ BlendAlpha[] $VALUES;

        public static BlendAlpha[] values() {
            return (BlendAlpha[])$VALUES.clone();
        }

        public static BlendAlpha valueOf(String p_208544_) {
            return Enum.valueOf(BlendAlpha.class, p_208544_);
        }

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_208536_) {
            return 1.0;
        }

        @Override
        public void m_207362_(double[] p_208538_, DensityFunction.ContextProvider p_208539_) {
            Arrays.fill(p_208538_, 1.0);
        }

        @Override
        public double m_207402_() {
            return 1.0;
        }

        @Override
        public double m_207401_() {
            return 1.0;
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return f_208528_;
        }

        private static /* synthetic */ BlendAlpha[] m_208542_() {
            return new BlendAlpha[]{INSTANCE};
        }

        static {
            $VALUES = BlendAlpha.m_208542_();
            f_208528_ = KeyDispatchDataCodec.m_216238_(MapCodec.unit((Object)INSTANCE));
        }
    }

    protected static final class BlendOffset
    extends Enum<BlendOffset>
    implements DensityFunction.SimpleFunction {
        public static final /* enum */ BlendOffset INSTANCE = new BlendOffset();
        public static final KeyDispatchDataCodec<DensityFunction> f_208565_;
        private static final /* synthetic */ BlendOffset[] $VALUES;

        public static BlendOffset[] values() {
            return (BlendOffset[])$VALUES.clone();
        }

        public static BlendOffset valueOf(String p_208581_) {
            return Enum.valueOf(BlendOffset.class, p_208581_);
        }

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_208573_) {
            return 0.0;
        }

        @Override
        public void m_207362_(double[] p_208575_, DensityFunction.ContextProvider p_208576_) {
            Arrays.fill(p_208575_, 0.0);
        }

        @Override
        public double m_207402_() {
            return 0.0;
        }

        @Override
        public double m_207401_() {
            return 0.0;
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return f_208565_;
        }

        private static /* synthetic */ BlendOffset[] m_208579_() {
            return new BlendOffset[]{INSTANCE};
        }

        static {
            $VALUES = BlendOffset.m_208579_();
            f_208565_ = KeyDispatchDataCodec.m_216238_(MapCodec.unit((Object)INSTANCE));
        }
    }

    protected static final class BeardifierMarker
    extends Enum<BeardifierMarker>
    implements BeardifierOrMarker {
        public static final /* enum */ BeardifierMarker INSTANCE = new BeardifierMarker();
        private static final /* synthetic */ BeardifierMarker[] $VALUES;

        public static BeardifierMarker[] values() {
            return (BeardifierMarker[])$VALUES.clone();
        }

        public static BeardifierMarker valueOf(String p_208522_) {
            return Enum.valueOf(BeardifierMarker.class, p_208522_);
        }

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_208515_) {
            return 0.0;
        }

        @Override
        public void m_207362_(double[] p_208517_, DensityFunction.ContextProvider p_208518_) {
            Arrays.fill(p_208517_, 0.0);
        }

        @Override
        public double m_207402_() {
            return 0.0;
        }

        @Override
        public double m_207401_() {
            return 0.0;
        }

        private static /* synthetic */ BeardifierMarker[] m_208520_() {
            return new BeardifierMarker[]{INSTANCE};
        }

        static {
            $VALUES = BeardifierMarker.m_208520_();
        }
    }

    protected static final class Marker
    extends Record
    implements MarkerOrMarked {
        private final Type f_208705_;
        private final DensityFunction f_208706_;

        protected Marker(Type f_208705_, DensityFunction f_208706_) {
            this.f_208705_ = f_208705_;
            this.f_208706_ = f_208706_;
        }

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_208712_) {
            return this.f_208706_.m_207386_(p_208712_);
        }

        @Override
        public void m_207362_(double[] p_208716_, DensityFunction.ContextProvider p_208717_) {
            this.f_208706_.m_207362_(p_208716_, p_208717_);
        }

        @Override
        public double m_207402_() {
            return this.f_208706_.m_207402_();
        }

        @Override
        public double m_207401_() {
            return this.f_208706_.m_207401_();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Marker.class, "type;wrapped", "f_208705_", "f_208706_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Marker.class, "type;wrapped", "f_208705_", "f_208706_"}, this);
        }

        @Override
        public final boolean equals(Object p_208720_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Marker.class, "type;wrapped", "f_208705_", "f_208706_"}, this, p_208720_);
        }

        @Override
        public Type m_207136_() {
            return this.f_208705_;
        }

        @Override
        public DensityFunction m_207056_() {
            return this.f_208706_;
        }

        static final class Type
        extends Enum<Type>
        implements StringRepresentable {
            public static final /* enum */ Type Interpolated = new Type("interpolated");
            public static final /* enum */ Type FlatCache = new Type("flat_cache");
            public static final /* enum */ Type Cache2D = new Type("cache_2d");
            public static final /* enum */ Type CacheOnce = new Type("cache_once");
            public static final /* enum */ Type CacheAllInCell = new Type("cache_all_in_cell");
            private final String f_208730_;
            final KeyDispatchDataCodec<MarkerOrMarked> f_208731_ = DensityFunctions.m_224042_(p_208740_ -> new Marker(this, (DensityFunction)p_208740_), MarkerOrMarked::m_207056_);
            private static final /* synthetic */ Type[] $VALUES;

            public static Type[] values() {
                return (Type[])$VALUES.clone();
            }

            public static Type valueOf(String p_208743_) {
                return Enum.valueOf(Type.class, p_208743_);
            }

            private Type(String p_208737_) {
                this.f_208730_ = p_208737_;
            }

            @Override
            public String m_7912_() {
                return this.f_208730_;
            }

            private static /* synthetic */ Type[] m_208738_() {
                return new Type[]{Interpolated, FlatCache, Cache2D, CacheOnce, CacheAllInCell};
            }

            static {
                $VALUES = Type.m_208738_();
            }
        }
    }

    protected record Noise(DensityFunction.NoiseHolder f_208787_, @Deprecated double f_208788_, double f_208789_) implements DensityFunction
    {
        public static final MapCodec<Noise> f_208784_ = RecordCodecBuilder.mapCodec(p_208798_ -> p_208798_.group((App)DensityFunction.NoiseHolder.f_223996_.fieldOf("noise").forGetter(Noise::f_208787_), (App)Codec.DOUBLE.fieldOf("xz_scale").forGetter(Noise::f_208788_), (App)Codec.DOUBLE.fieldOf("y_scale").forGetter(Noise::f_208789_)).apply((Applicative)p_208798_, Noise::new));
        public static final KeyDispatchDataCodec<Noise> f_208785_ = DensityFunctions.m_224028_(f_208784_);

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_208800_) {
            return this.f_208787_.m_224006_((double)p_208800_.m_207115_() * this.f_208788_, (double)p_208800_.m_207114_() * this.f_208789_, (double)p_208800_.m_207113_() * this.f_208788_);
        }

        @Override
        public void m_207362_(double[] p_224079_, DensityFunction.ContextProvider p_224080_) {
            p_224080_.m_207207_(p_224079_, this);
        }

        @Override
        public DensityFunction m_207456_(DensityFunction.Visitor p_224077_) {
            return p_224077_.m_214017_(new Noise(p_224077_.m_213918_(this.f_208787_), this.f_208788_, this.f_208789_));
        }

        @Override
        public double m_207402_() {
            return -this.m_207401_();
        }

        @Override
        public double m_207401_() {
            return this.f_208787_.m_224005_();
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return f_208785_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Noise.class, "noise;xzScale;yScale", "f_208787_", "f_208788_", "f_208789_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Noise.class, "noise;xzScale;yScale", "f_208787_", "f_208788_", "f_208789_"}, this);
        }

        @Override
        public final boolean equals(Object p_208808_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Noise.class, "noise;xzScale;yScale", "f_208787_", "f_208788_", "f_208789_"}, this, p_208808_);
        }
    }

    protected static final class EndIslandDensityFunction
    implements DensityFunction.SimpleFunction {
        public static final KeyDispatchDataCodec<EndIslandDensityFunction> f_208626_ = KeyDispatchDataCodec.m_216238_(MapCodec.unit((Object)new EndIslandDensityFunction(0L)));
        private static final float f_224061_ = -0.9f;
        private final SimplexNoise f_208627_;

        public EndIslandDensityFunction(long p_208630_) {
            LegacyRandomSource $$1 = new LegacyRandomSource(p_208630_);
            $$1.m_190110_(17292);
            this.f_208627_ = new SimplexNoise($$1);
        }

        private static float m_224062_(SimplexNoise p_224063_, int p_224064_, int p_224065_) {
            int $$3 = p_224064_ / 2;
            int $$4 = p_224065_ / 2;
            int $$5 = p_224064_ % 2;
            int $$6 = p_224065_ % 2;
            float $$7 = 100.0f - Mth.m_14116_(p_224064_ * p_224064_ + p_224065_ * p_224065_) * 8.0f;
            $$7 = Mth.m_14036_($$7, -100.0f, 80.0f);
            for (int $$8 = -12; $$8 <= 12; ++$$8) {
                for (int $$9 = -12; $$9 <= 12; ++$$9) {
                    long $$10 = $$3 + $$8;
                    long $$11 = $$4 + $$9;
                    if ($$10 * $$10 + $$11 * $$11 <= 4096L || !(p_224063_.m_75464_($$10, $$11) < (double)-0.9f)) continue;
                    float $$12 = (Mth.m_14154_($$10) * 3439.0f + Mth.m_14154_($$11) * 147.0f) % 13.0f + 9.0f;
                    float $$13 = $$5 - $$8 * 2;
                    float $$14 = $$6 - $$9 * 2;
                    float $$15 = 100.0f - Mth.m_14116_($$13 * $$13 + $$14 * $$14) * $$12;
                    $$15 = Mth.m_14036_($$15, -100.0f, 80.0f);
                    $$7 = Math.max($$7, $$15);
                }
            }
            return $$7;
        }

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_208633_) {
            return ((double)EndIslandDensityFunction.m_224062_(this.f_208627_, p_208633_.m_207115_() / 8, p_208633_.m_207113_() / 8) - 8.0) / 128.0;
        }

        @Override
        public double m_207402_() {
            return -0.84375;
        }

        @Override
        public double m_207401_() {
            return 0.5625;
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return f_208626_;
        }
    }

    protected static final class WeirdScaledSampler
    extends Record
    implements TransformerWithContext {
        private final DensityFunction f_208425_;
        private final DensityFunction.NoiseHolder f_208427_;
        private final RarityValueMapper f_208428_;
        private static final MapCodec<WeirdScaledSampler> f_208429_ = RecordCodecBuilder.mapCodec(p_208438_ -> p_208438_.group((App)DensityFunction.f_208218_.fieldOf("input").forGetter(WeirdScaledSampler::m_207189_), (App)DensityFunction.NoiseHolder.f_223996_.fieldOf("noise").forGetter(WeirdScaledSampler::f_208427_), (App)RarityValueMapper.f_208460_.fieldOf("rarity_value_mapper").forGetter(WeirdScaledSampler::f_208428_)).apply((Applicative)p_208438_, WeirdScaledSampler::new));
        public static final KeyDispatchDataCodec<WeirdScaledSampler> f_208424_ = DensityFunctions.m_224028_(f_208429_);

        protected WeirdScaledSampler(DensityFunction f_208425_, DensityFunction.NoiseHolder f_208427_, RarityValueMapper f_208428_) {
            this.f_208425_ = f_208425_;
            this.f_208427_ = f_208427_;
            this.f_208428_ = f_208428_;
        }

        @Override
        public double m_207219_(DensityFunction.FunctionContext p_208440_, double p_208441_) {
            double $$2 = this.f_208428_.f_208463_.get(p_208441_);
            return $$2 * Math.abs(this.f_208427_.m_224006_((double)p_208440_.m_207115_() / $$2, (double)p_208440_.m_207114_() / $$2, (double)p_208440_.m_207113_() / $$2));
        }

        @Override
        public DensityFunction m_207456_(DensityFunction.Visitor p_208443_) {
            return p_208443_.m_214017_(new WeirdScaledSampler(this.f_208425_.m_207456_(p_208443_), p_208443_.m_213918_(this.f_208427_), this.f_208428_));
        }

        @Override
        public double m_207402_() {
            return 0.0;
        }

        @Override
        public double m_207401_() {
            return this.f_208428_.f_208464_ * this.f_208427_.m_224005_();
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return f_208424_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{WeirdScaledSampler.class, "input;noise;rarityValueMapper", "f_208425_", "f_208427_", "f_208428_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{WeirdScaledSampler.class, "input;noise;rarityValueMapper", "f_208425_", "f_208427_", "f_208428_"}, this);
        }

        @Override
        public final boolean equals(Object p_208451_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{WeirdScaledSampler.class, "input;noise;rarityValueMapper", "f_208425_", "f_208427_", "f_208428_"}, this, p_208451_);
        }

        @Override
        public DensityFunction m_207189_() {
            return this.f_208425_;
        }

        public DensityFunction.NoiseHolder f_208427_() {
            return this.f_208427_;
        }

        public RarityValueMapper f_208428_() {
            return this.f_208428_;
        }

        public static final class RarityValueMapper
        extends Enum<RarityValueMapper>
        implements StringRepresentable {
            public static final /* enum */ RarityValueMapper TYPE1 = new RarityValueMapper("type_1", NoiseRouterData.QuantizedSpaghettiRarity::m_209565_, 2.0);
            public static final /* enum */ RarityValueMapper TYPE2 = new RarityValueMapper("type_2", NoiseRouterData.QuantizedSpaghettiRarity::m_209563_, 3.0);
            public static final Codec<RarityValueMapper> f_208460_;
            private final String f_208462_;
            final Double2DoubleFunction f_208463_;
            final double f_208464_;
            private static final /* synthetic */ RarityValueMapper[] $VALUES;

            public static RarityValueMapper[] values() {
                return (RarityValueMapper[])$VALUES.clone();
            }

            public static RarityValueMapper valueOf(String p_208478_) {
                return Enum.valueOf(RarityValueMapper.class, p_208478_);
            }

            private RarityValueMapper(String p_208470_, Double2DoubleFunction p_208471_, double p_208472_) {
                this.f_208462_ = p_208470_;
                this.f_208463_ = p_208471_;
                this.f_208464_ = p_208472_;
            }

            @Override
            public String m_7912_() {
                return this.f_208462_;
            }

            private static /* synthetic */ RarityValueMapper[] m_208473_() {
                return new RarityValueMapper[]{TYPE1, TYPE2};
            }

            static {
                $VALUES = RarityValueMapper.m_208473_();
                f_208460_ = StringRepresentable.m_216439_(RarityValueMapper::values);
            }
        }
    }

    protected record ShiftedNoise(DensityFunction f_208924_, DensityFunction f_208925_, DensityFunction f_208926_, double f_208927_, double f_208928_, DensityFunction.NoiseHolder f_208930_) implements DensityFunction
    {
        private static final MapCodec<ShiftedNoise> f_208931_ = RecordCodecBuilder.mapCodec(p_208943_ -> p_208943_.group((App)DensityFunction.f_208218_.fieldOf("shift_x").forGetter(ShiftedNoise::f_208924_), (App)DensityFunction.f_208218_.fieldOf("shift_y").forGetter(ShiftedNoise::f_208925_), (App)DensityFunction.f_208218_.fieldOf("shift_z").forGetter(ShiftedNoise::f_208926_), (App)Codec.DOUBLE.fieldOf("xz_scale").forGetter(ShiftedNoise::f_208927_), (App)Codec.DOUBLE.fieldOf("y_scale").forGetter(ShiftedNoise::f_208928_), (App)DensityFunction.NoiseHolder.f_223996_.fieldOf("noise").forGetter(ShiftedNoise::f_208930_)).apply((Applicative)p_208943_, ShiftedNoise::new));
        public static final KeyDispatchDataCodec<ShiftedNoise> f_208923_ = DensityFunctions.m_224028_(f_208931_);

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_208945_) {
            double $$1 = (double)p_208945_.m_207115_() * this.f_208927_ + this.f_208924_.m_207386_(p_208945_);
            double $$2 = (double)p_208945_.m_207114_() * this.f_208928_ + this.f_208925_.m_207386_(p_208945_);
            double $$3 = (double)p_208945_.m_207113_() * this.f_208927_ + this.f_208926_.m_207386_(p_208945_);
            return this.f_208930_.m_224006_($$1, $$2, $$3);
        }

        @Override
        public void m_207362_(double[] p_208956_, DensityFunction.ContextProvider p_208957_) {
            p_208957_.m_207207_(p_208956_, this);
        }

        @Override
        public DensityFunction m_207456_(DensityFunction.Visitor p_208947_) {
            return p_208947_.m_214017_(new ShiftedNoise(this.f_208924_.m_207456_(p_208947_), this.f_208925_.m_207456_(p_208947_), this.f_208926_.m_207456_(p_208947_), this.f_208927_, this.f_208928_, p_208947_.m_213918_(this.f_208930_)));
        }

        @Override
        public double m_207402_() {
            return -this.m_207401_();
        }

        @Override
        public double m_207401_() {
            return this.f_208930_.m_224005_();
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return f_208923_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ShiftedNoise.class, "shiftX;shiftY;shiftZ;xzScale;yScale;noise", "f_208924_", "f_208925_", "f_208926_", "f_208927_", "f_208928_", "f_208930_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ShiftedNoise.class, "shiftX;shiftY;shiftZ;xzScale;yScale;noise", "f_208924_", "f_208925_", "f_208926_", "f_208927_", "f_208928_", "f_208930_"}, this);
        }

        @Override
        public final boolean equals(Object p_208961_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ShiftedNoise.class, "shiftX;shiftY;shiftZ;xzScale;yScale;noise", "f_208924_", "f_208925_", "f_208926_", "f_208927_", "f_208928_", "f_208930_"}, this, p_208961_);
        }
    }

    record RangeChoice(DensityFunction f_208823_, double f_208824_, double f_208825_, DensityFunction f_208826_, DensityFunction f_208827_) implements DensityFunction
    {
        public static final MapCodec<RangeChoice> f_208821_ = RecordCodecBuilder.mapCodec(p_208837_ -> p_208837_.group((App)DensityFunction.f_208218_.fieldOf("input").forGetter(RangeChoice::f_208823_), (App)f_208260_.fieldOf("min_inclusive").forGetter(RangeChoice::f_208824_), (App)f_208260_.fieldOf("max_exclusive").forGetter(RangeChoice::f_208825_), (App)DensityFunction.f_208218_.fieldOf("when_in_range").forGetter(RangeChoice::f_208826_), (App)DensityFunction.f_208218_.fieldOf("when_out_of_range").forGetter(RangeChoice::f_208827_)).apply((Applicative)p_208837_, RangeChoice::new));
        public static final KeyDispatchDataCodec<RangeChoice> f_208822_ = DensityFunctions.m_224028_(f_208821_);

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_208839_) {
            double $$1 = this.f_208823_.m_207386_(p_208839_);
            if ($$1 >= this.f_208824_ && $$1 < this.f_208825_) {
                return this.f_208826_.m_207386_(p_208839_);
            }
            return this.f_208827_.m_207386_(p_208839_);
        }

        @Override
        public void m_207362_(double[] p_208843_, DensityFunction.ContextProvider p_208844_) {
            this.f_208823_.m_207362_(p_208843_, p_208844_);
            for (int $$2 = 0; $$2 < p_208843_.length; ++$$2) {
                double $$3 = p_208843_[$$2];
                p_208843_[$$2] = $$3 >= this.f_208824_ && $$3 < this.f_208825_ ? this.f_208826_.m_207386_(p_208844_.m_207263_($$2)) : this.f_208827_.m_207386_(p_208844_.m_207263_($$2));
            }
        }

        @Override
        public DensityFunction m_207456_(DensityFunction.Visitor p_208841_) {
            return p_208841_.m_214017_(new RangeChoice(this.f_208823_.m_207456_(p_208841_), this.f_208824_, this.f_208825_, this.f_208826_.m_207456_(p_208841_), this.f_208827_.m_207456_(p_208841_)));
        }

        @Override
        public double m_207402_() {
            return Math.min(this.f_208826_.m_207402_(), this.f_208827_.m_207402_());
        }

        @Override
        public double m_207401_() {
            return Math.max(this.f_208826_.m_207401_(), this.f_208827_.m_207401_());
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return f_208822_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{RangeChoice.class, "input;minInclusive;maxExclusive;whenInRange;whenOutOfRange", "f_208823_", "f_208824_", "f_208825_", "f_208826_", "f_208827_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{RangeChoice.class, "input;minInclusive;maxExclusive;whenInRange;whenOutOfRange", "f_208823_", "f_208824_", "f_208825_", "f_208826_", "f_208827_"}, this);
        }

        @Override
        public final boolean equals(Object p_208848_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{RangeChoice.class, "input;minInclusive;maxExclusive;whenInRange;whenOutOfRange", "f_208823_", "f_208824_", "f_208825_", "f_208826_", "f_208827_"}, this, p_208848_);
        }
    }

    protected static final class ShiftA
    extends Record
    implements ShiftNoise {
        private final DensityFunction.NoiseHolder f_208877_;
        static final KeyDispatchDataCodec<ShiftA> f_208878_ = DensityFunctions.m_224024_(DensityFunction.NoiseHolder.f_223996_, ShiftA::new, ShiftA::m_214040_);

        protected ShiftA(DensityFunction.NoiseHolder f_208877_) {
            this.f_208877_ = f_208877_;
        }

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_208884_) {
            return this.m_208917_(p_208884_.m_207115_(), 0.0, p_208884_.m_207113_());
        }

        @Override
        public DensityFunction m_207456_(DensityFunction.Visitor p_224093_) {
            return p_224093_.m_214017_(new ShiftA(p_224093_.m_213918_(this.f_208877_)));
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return f_208878_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ShiftA.class, "offsetNoise", "f_208877_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ShiftA.class, "offsetNoise", "f_208877_"}, this);
        }

        @Override
        public final boolean equals(Object p_208891_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ShiftA.class, "offsetNoise", "f_208877_"}, this, p_208891_);
        }

        @Override
        public DensityFunction.NoiseHolder m_214040_() {
            return this.f_208877_;
        }
    }

    protected static final class ShiftB
    extends Record
    implements ShiftNoise {
        private final DensityFunction.NoiseHolder f_208897_;
        static final KeyDispatchDataCodec<ShiftB> f_208898_ = DensityFunctions.m_224024_(DensityFunction.NoiseHolder.f_223996_, ShiftB::new, ShiftB::m_214040_);

        protected ShiftB(DensityFunction.NoiseHolder f_208897_) {
            this.f_208897_ = f_208897_;
        }

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_208904_) {
            return this.m_208917_(p_208904_.m_207113_(), p_208904_.m_207115_(), 0.0);
        }

        @Override
        public DensityFunction m_207456_(DensityFunction.Visitor p_224099_) {
            return p_224099_.m_214017_(new ShiftB(p_224099_.m_213918_(this.f_208897_)));
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return f_208898_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ShiftB.class, "offsetNoise", "f_208897_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ShiftB.class, "offsetNoise", "f_208897_"}, this);
        }

        @Override
        public final boolean equals(Object p_208911_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ShiftB.class, "offsetNoise", "f_208897_"}, this, p_208911_);
        }

        @Override
        public DensityFunction.NoiseHolder m_214040_() {
            return this.f_208897_;
        }
    }

    protected static final class Shift
    extends Record
    implements ShiftNoise {
        private final DensityFunction.NoiseHolder f_208857_;
        static final KeyDispatchDataCodec<Shift> f_208858_ = DensityFunctions.m_224024_(DensityFunction.NoiseHolder.f_223996_, Shift::new, Shift::m_214040_);

        protected Shift(DensityFunction.NoiseHolder f_208857_) {
            this.f_208857_ = f_208857_;
        }

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_208864_) {
            return this.m_208917_(p_208864_.m_207115_(), p_208864_.m_207114_(), p_208864_.m_207113_());
        }

        @Override
        public DensityFunction m_207456_(DensityFunction.Visitor p_224087_) {
            return p_224087_.m_214017_(new Shift(p_224087_.m_213918_(this.f_208857_)));
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return f_208858_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Shift.class, "offsetNoise", "f_208857_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Shift.class, "offsetNoise", "f_208857_"}, this);
        }

        @Override
        public final boolean equals(Object p_208871_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Shift.class, "offsetNoise", "f_208857_"}, this, p_208871_);
        }

        @Override
        public DensityFunction.NoiseHolder m_214040_() {
            return this.f_208857_;
        }
    }

    static final class BlendDensity
    extends Record
    implements TransformerWithContext {
        private final DensityFunction f_208546_;
        static final KeyDispatchDataCodec<BlendDensity> f_208547_ = DensityFunctions.m_224042_(BlendDensity::new, BlendDensity::m_207189_);

        BlendDensity(DensityFunction f_208546_) {
            this.f_208546_ = f_208546_;
        }

        @Override
        public double m_207219_(DensityFunction.FunctionContext p_208553_, double p_208554_) {
            return p_208553_.m_188743_().m_207103_(p_208553_, p_208554_);
        }

        @Override
        public DensityFunction m_207456_(DensityFunction.Visitor p_208556_) {
            return p_208556_.m_214017_(new BlendDensity(this.f_208546_.m_207456_(p_208556_)));
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
            return f_208547_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{BlendDensity.class, "input", "f_208546_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{BlendDensity.class, "input", "f_208546_"}, this);
        }

        @Override
        public final boolean equals(Object p_208560_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{BlendDensity.class, "input", "f_208546_"}, this, p_208560_);
        }

        @Override
        public DensityFunction m_207189_() {
            return this.f_208546_;
        }
    }

    protected static final class Clamp
    extends Record
    implements PureTransformer {
        private final DensityFunction f_208584_;
        private final double f_208585_;
        private final double f_208586_;
        private static final MapCodec<Clamp> f_208587_ = RecordCodecBuilder.mapCodec(p_208597_ -> p_208597_.group((App)DensityFunction.f_208216_.fieldOf("input").forGetter(Clamp::m_207305_), (App)f_208260_.fieldOf("min").forGetter(Clamp::m_207402_), (App)f_208260_.fieldOf("max").forGetter(Clamp::m_207401_)).apply((Applicative)p_208597_, Clamp::new));
        public static final KeyDispatchDataCodec<Clamp> f_208583_ = DensityFunctions.m_224028_(f_208587_);

        protected Clamp(DensityFunction f_208584_, double f_208585_, double f_208586_) {
            this.f_208584_ = f_208584_;
            this.f_208585_ = f_208585_;
            this.f_208586_ = f_208586_;
        }

        @Override
        public double m_207382_(double p_208595_) {
            return Mth.m_14008_(p_208595_, this.f_208585_, this.f_208586_);
        }

        @Override
        public DensityFunction m_207456_(DensityFunction.Visitor p_208599_) {
            return new Clamp(this.f_208584_.m_207456_(p_208599_), this.f_208585_, this.f_208586_);
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return f_208583_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Clamp.class, "input;minValue;maxValue", "f_208584_", "f_208585_", "f_208586_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Clamp.class, "input;minValue;maxValue", "f_208584_", "f_208585_", "f_208586_"}, this);
        }

        @Override
        public final boolean equals(Object p_208604_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Clamp.class, "input;minValue;maxValue", "f_208584_", "f_208585_", "f_208586_"}, this, p_208604_);
        }

        @Override
        public DensityFunction m_207305_() {
            return this.f_208584_;
        }

        @Override
        public double m_207402_() {
            return this.f_208585_;
        }

        @Override
        public double m_207401_() {
            return this.f_208586_;
        }
    }

    protected static final class Mapped
    extends Record
    implements PureTransformer {
        private final Type f_208654_;
        private final DensityFunction f_208655_;
        private final double f_208656_;
        private final double f_208657_;

        protected Mapped(Type f_208654_, DensityFunction f_208655_, double f_208656_, double f_208657_) {
            this.f_208654_ = f_208654_;
            this.f_208655_ = f_208655_;
            this.f_208656_ = f_208656_;
            this.f_208657_ = f_208657_;
        }

        public static Mapped m_208671_(Type p_208672_, DensityFunction p_208673_) {
            double $$2 = p_208673_.m_207402_();
            double $$3 = Mapped.m_208668_(p_208672_, $$2);
            double $$4 = Mapped.m_208668_(p_208672_, p_208673_.m_207401_());
            if (p_208672_ == Type.ABS || p_208672_ == Type.SQUARE) {
                return new Mapped(p_208672_, p_208673_, Math.max(0.0, $$2), Math.max($$3, $$4));
            }
            return new Mapped(p_208672_, p_208673_, $$3, $$4);
        }

        private static double m_208668_(Type p_208669_, double p_208670_) {
            return switch (p_208669_) {
                default -> throw new IncompatibleClassChangeError();
                case Type.ABS -> Math.abs(p_208670_);
                case Type.SQUARE -> p_208670_ * p_208670_;
                case Type.CUBE -> p_208670_ * p_208670_ * p_208670_;
                case Type.HALF_NEGATIVE -> {
                    if (p_208670_ > 0.0) {
                        yield p_208670_;
                    }
                    yield p_208670_ * 0.5;
                }
                case Type.QUARTER_NEGATIVE -> {
                    if (p_208670_ > 0.0) {
                        yield p_208670_;
                    }
                    yield p_208670_ * 0.25;
                }
                case Type.SQUEEZE -> {
                    double $$2 = Mth.m_14008_(p_208670_, -1.0, 1.0);
                    yield $$2 / 2.0 - $$2 * $$2 * $$2 / 24.0;
                }
            };
        }

        @Override
        public double m_207382_(double p_208665_) {
            return Mapped.m_208668_(this.f_208654_, p_208665_);
        }

        @Override
        public Mapped m_207456_(DensityFunction.Visitor p_208677_) {
            return Mapped.m_208671_(this.f_208654_, this.f_208655_.m_207456_(p_208677_));
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return this.f_208654_.f_208691_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Mapped.class, "type;input;minValue;maxValue", "f_208654_", "f_208655_", "f_208656_", "f_208657_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Mapped.class, "type;input;minValue;maxValue", "f_208654_", "f_208655_", "f_208656_", "f_208657_"}, this);
        }

        @Override
        public final boolean equals(Object p_208680_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Mapped.class, "type;input;minValue;maxValue", "f_208654_", "f_208655_", "f_208656_", "f_208657_"}, this, p_208680_);
        }

        public Type f_208654_() {
            return this.f_208654_;
        }

        @Override
        public DensityFunction m_207305_() {
            return this.f_208655_;
        }

        @Override
        public double m_207402_() {
            return this.f_208656_;
        }

        @Override
        public double m_207401_() {
            return this.f_208657_;
        }

        @Override
        public /* synthetic */ DensityFunction m_207456_(DensityFunction.Visitor visitor) {
            return this.m_207456_(visitor);
        }

        static final class Type
        extends Enum<Type>
        implements StringRepresentable {
            public static final /* enum */ Type ABS = new Type("abs");
            public static final /* enum */ Type SQUARE = new Type("square");
            public static final /* enum */ Type CUBE = new Type("cube");
            public static final /* enum */ Type HALF_NEGATIVE = new Type("half_negative");
            public static final /* enum */ Type QUARTER_NEGATIVE = new Type("quarter_negative");
            public static final /* enum */ Type SQUEEZE = new Type("squeeze");
            private final String f_208690_;
            final KeyDispatchDataCodec<Mapped> f_208691_ = DensityFunctions.m_224042_(p_208700_ -> Mapped.m_208671_(this, p_208700_), Mapped::m_207305_);
            private static final /* synthetic */ Type[] $VALUES;

            public static Type[] values() {
                return (Type[])$VALUES.clone();
            }

            public static Type valueOf(String p_208703_) {
                return Enum.valueOf(Type.class, p_208703_);
            }

            private Type(String p_208697_) {
                this.f_208690_ = p_208697_;
            }

            @Override
            public String m_7912_() {
                return this.f_208690_;
            }

            private static /* synthetic */ Type[] m_208698_() {
                return new Type[]{ABS, SQUARE, CUBE, HALF_NEGATIVE, QUARTER_NEGATIVE, SQUEEZE};
            }

            static {
                $VALUES = Type.m_208698_();
            }
        }
    }

    static interface TwoArgumentSimpleFunction
    extends DensityFunction {
        public static final Logger f_209071_ = LogUtils.getLogger();

        public static TwoArgumentSimpleFunction m_209073_(Type p_209074_, DensityFunction p_209075_, DensityFunction p_209076_) {
            double $$10;
            double $$3 = p_209075_.m_207402_();
            double $$4 = p_209076_.m_207402_();
            double $$5 = p_209075_.m_207401_();
            double $$6 = p_209076_.m_207401_();
            if (p_209074_ == Type.MIN || p_209074_ == Type.MAX) {
                boolean $$8;
                boolean $$7 = $$3 >= $$6;
                boolean bl = $$8 = $$4 >= $$5;
                if ($$7 || $$8) {
                    f_209071_.warn("Creating a " + p_209074_ + " function between two non-overlapping inputs: " + p_209075_ + " and " + p_209076_);
                }
            }
            double $$9 = switch (p_209074_) {
                default -> throw new IncompatibleClassChangeError();
                case Type.ADD -> $$3 + $$4;
                case Type.MAX -> Math.max($$3, $$4);
                case Type.MIN -> Math.min($$3, $$4);
                case Type.MUL -> $$3 > 0.0 && $$4 > 0.0 ? $$3 * $$4 : ($$5 < 0.0 && $$6 < 0.0 ? $$5 * $$6 : Math.min($$3 * $$6, $$5 * $$4));
            };
            switch (p_209074_) {
                default: {
                    throw new IncompatibleClassChangeError();
                }
                case ADD: {
                    double d = $$5 + $$6;
                    break;
                }
                case MAX: {
                    double d = Math.max($$5, $$6);
                    break;
                }
                case MIN: {
                    double d = Math.min($$5, $$6);
                    break;
                }
                case MUL: {
                    double d = $$3 > 0.0 && $$4 > 0.0 ? $$5 * $$6 : ($$10 = $$5 < 0.0 && $$6 < 0.0 ? $$3 * $$4 : Math.max($$3 * $$4, $$5 * $$6));
                }
            }
            if (p_209074_ == Type.MUL || p_209074_ == Type.ADD) {
                if (p_209075_ instanceof Constant) {
                    Constant $$11 = (Constant)p_209075_;
                    return new MulOrAdd(p_209074_ == Type.ADD ? MulOrAdd.Type.ADD : MulOrAdd.Type.MUL, p_209076_, $$9, $$10, $$11.f_208607_);
                }
                if (p_209076_ instanceof Constant) {
                    Constant $$12 = (Constant)p_209076_;
                    return new MulOrAdd(p_209074_ == Type.ADD ? MulOrAdd.Type.ADD : MulOrAdd.Type.MUL, p_209075_, $$9, $$10, $$12.f_208607_);
                }
            }
            return new Ap2(p_209074_, p_209075_, p_209076_, $$9, $$10);
        }

        public Type m_207119_();

        public DensityFunction m_207185_();

        public DensityFunction m_207190_();

        @Override
        default public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return this.m_207119_().f_209082_;
        }

        public static final class Type
        extends Enum<Type>
        implements StringRepresentable {
            public static final /* enum */ Type ADD = new Type("add");
            public static final /* enum */ Type MUL = new Type("mul");
            public static final /* enum */ Type MIN = new Type("min");
            public static final /* enum */ Type MAX = new Type("max");
            final KeyDispatchDataCodec<TwoArgumentSimpleFunction> f_209082_ = DensityFunctions.m_224038_((p_209092_, p_209093_) -> TwoArgumentSimpleFunction.m_209073_(this, p_209092_, p_209093_), TwoArgumentSimpleFunction::m_207185_, TwoArgumentSimpleFunction::m_207190_);
            private final String f_209083_;
            private static final /* synthetic */ Type[] $VALUES;

            public static Type[] values() {
                return (Type[])$VALUES.clone();
            }

            public static Type valueOf(String p_209096_) {
                return Enum.valueOf(Type.class, p_209096_);
            }

            private Type(String p_209089_) {
                this.f_209083_ = p_209089_;
            }

            @Override
            public String m_7912_() {
                return this.f_209083_;
            }

            private static /* synthetic */ Type[] m_209090_() {
                return new Type[]{ADD, MUL, MIN, MAX};
            }

            static {
                $VALUES = Type.m_209090_();
            }
        }
    }

    public record Spline(CubicSpline<Point, Coordinate> f_211702_) implements DensityFunction
    {
        private static final Codec<CubicSpline<Point, Coordinate>> f_224114_ = CubicSpline.m_184262_(Coordinate.f_224121_);
        private static final MapCodec<Spline> f_211705_ = f_224114_.fieldOf("spline").xmap(Spline::new, Spline::f_211702_);
        public static final KeyDispatchDataCodec<Spline> f_211701_ = DensityFunctions.m_224028_(f_211705_);

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_211715_) {
            return this.f_211702_.m_183321_(new Point(p_211715_));
        }

        @Override
        public double m_207402_() {
            return this.f_211702_.m_213850_();
        }

        @Override
        public double m_207401_() {
            return this.f_211702_.m_213849_();
        }

        @Override
        public void m_207362_(double[] p_211722_, DensityFunction.ContextProvider p_211723_) {
            p_211723_.m_207207_(p_211722_, this);
        }

        @Override
        public DensityFunction m_207456_(DensityFunction.Visitor p_211717_) {
            return p_211717_.m_214017_(new Spline(this.f_211702_.m_211396_(p_224119_ -> p_224119_.m_224127_(p_211717_))));
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return f_211701_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Spline.class, "spline", "f_211702_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Spline.class, "spline", "f_211702_"}, this);
        }

        @Override
        public final boolean equals(Object p_211727_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Spline.class, "spline", "f_211702_"}, this, p_211727_);
        }

        public record Point(DensityFunction.FunctionContext f_224139_) {
            @Override
            public final String toString() {
                return ObjectMethods.bootstrap("toString", new MethodHandle[]{Point.class, "context", "f_224139_"}, this);
            }

            @Override
            public final int hashCode() {
                return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Point.class, "context", "f_224139_"}, this);
            }

            @Override
            public final boolean equals(Object p_224144_) {
                return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Point.class, "context", "f_224139_"}, this, p_224144_);
            }
        }

        public record Coordinate(Holder<DensityFunction> f_224122_) implements ToFloatFunction<Point>
        {
            public static final Codec<Coordinate> f_224121_ = DensityFunction.f_208217_.xmap(Coordinate::new, Coordinate::f_224122_);

            @Override
            public String toString() {
                Optional<ResourceKey<DensityFunction>> $$0 = this.f_224122_.m_203543_();
                if ($$0.isPresent()) {
                    ResourceKey<DensityFunction> $$1 = $$0.get();
                    if ($$1 == NoiseRouterData.f_209451_) {
                        return "continents";
                    }
                    if ($$1 == NoiseRouterData.f_209452_) {
                        return "erosion";
                    }
                    if ($$1 == NoiseRouterData.f_209453_) {
                        return "weirdness";
                    }
                    if ($$1 == NoiseRouterData.f_224429_) {
                        return "ridges";
                    }
                }
                return "Coordinate[" + this.f_224122_ + "]";
            }

            @Override
            public float m_183321_(Point p_224130_) {
                return (float)this.f_224122_.m_203334_().m_207386_(p_224130_.f_224139_());
            }

            @Override
            public float m_213850_() {
                return (float)this.f_224122_.m_203334_().m_207402_();
            }

            @Override
            public float m_213849_() {
                return (float)this.f_224122_.m_203334_().m_207401_();
            }

            public Coordinate m_224127_(DensityFunction.Visitor p_224128_) {
                return new Coordinate(new Holder.Direct<DensityFunction>(this.f_224122_.m_203334_().m_207456_(p_224128_)));
            }

            @Override
            public final int hashCode() {
                return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Coordinate.class, "function", "f_224122_"}, this);
            }

            @Override
            public final boolean equals(Object p_224136_) {
                return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Coordinate.class, "function", "f_224122_"}, this, p_224136_);
            }
        }
    }

    record Constant(double f_208607_) implements DensityFunction.SimpleFunction
    {
        static final KeyDispatchDataCodec<Constant> f_208608_ = DensityFunctions.m_224024_(f_208260_, Constant::new, Constant::f_208607_);
        static final Constant f_208609_ = new Constant(0.0);

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_208615_) {
            return this.f_208607_;
        }

        @Override
        public void m_207362_(double[] p_208617_, DensityFunction.ContextProvider p_208618_) {
            Arrays.fill(p_208617_, this.f_208607_);
        }

        @Override
        public double m_207402_() {
            return this.f_208607_;
        }

        @Override
        public double m_207401_() {
            return this.f_208607_;
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return f_208608_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Constant.class, "value", "f_208607_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Constant.class, "value", "f_208607_"}, this);
        }

        @Override
        public final boolean equals(Object p_208622_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Constant.class, "value", "f_208607_"}, this, p_208622_);
        }
    }

    record YClampedGradient(int f_208481_, int f_208482_, double f_208483_, double f_208484_) implements DensityFunction.SimpleFunction
    {
        private static final MapCodec<YClampedGradient> f_208485_ = RecordCodecBuilder.mapCodec(p_208494_ -> p_208494_.group((App)Codec.intRange((int)(DimensionType.f_156653_ * 2), (int)(DimensionType.f_156652_ * 2)).fieldOf("from_y").forGetter(YClampedGradient::f_208481_), (App)Codec.intRange((int)(DimensionType.f_156653_ * 2), (int)(DimensionType.f_156652_ * 2)).fieldOf("to_y").forGetter(YClampedGradient::f_208482_), (App)f_208260_.fieldOf("from_value").forGetter(YClampedGradient::f_208483_), (App)f_208260_.fieldOf("to_value").forGetter(YClampedGradient::f_208484_)).apply((Applicative)p_208494_, YClampedGradient::new));
        public static final KeyDispatchDataCodec<YClampedGradient> f_208480_ = DensityFunctions.m_224028_(f_208485_);

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_208496_) {
            return Mth.m_144851_(p_208496_.m_207114_(), this.f_208481_, this.f_208482_, this.f_208483_, this.f_208484_);
        }

        @Override
        public double m_207402_() {
            return Math.min(this.f_208483_, this.f_208484_);
        }

        @Override
        public double m_207401_() {
            return Math.max(this.f_208483_, this.f_208484_);
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return f_208480_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{YClampedGradient.class, "fromY;toY;fromValue;toValue", "f_208481_", "f_208482_", "f_208483_", "f_208484_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{YClampedGradient.class, "fromY;toY;fromValue;toValue", "f_208481_", "f_208482_", "f_208483_", "f_208484_"}, this);
        }

        @Override
        public final boolean equals(Object p_208500_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{YClampedGradient.class, "fromY;toY;fromValue;toValue", "f_208481_", "f_208482_", "f_208483_", "f_208484_"}, this, p_208500_);
        }
    }

    static final class Ap2
    extends Record
    implements TwoArgumentSimpleFunction {
        private final TwoArgumentSimpleFunction.Type f_208397_;
        private final DensityFunction f_208398_;
        private final DensityFunction f_208399_;
        private final double f_208400_;
        private final double f_208401_;

        Ap2(TwoArgumentSimpleFunction.Type f_208397_, DensityFunction f_208398_, DensityFunction f_208399_, double f_208400_, double f_208401_) {
            this.f_208397_ = f_208397_;
            this.f_208398_ = f_208398_;
            this.f_208399_ = f_208399_;
            this.f_208400_ = f_208400_;
            this.f_208401_ = f_208401_;
        }

        @Override
        public double m_207386_(DensityFunction.FunctionContext p_208410_) {
            double $$1 = this.f_208398_.m_207386_(p_208410_);
            return switch (this.f_208397_) {
                default -> throw new IncompatibleClassChangeError();
                case TwoArgumentSimpleFunction.Type.ADD -> $$1 + this.f_208399_.m_207386_(p_208410_);
                case TwoArgumentSimpleFunction.Type.MUL -> {
                    if ($$1 == 0.0) {
                        yield 0.0;
                    }
                    yield $$1 * this.f_208399_.m_207386_(p_208410_);
                }
                case TwoArgumentSimpleFunction.Type.MIN -> {
                    if ($$1 < this.f_208399_.m_207402_()) {
                        yield $$1;
                    }
                    yield Math.min($$1, this.f_208399_.m_207386_(p_208410_));
                }
                case TwoArgumentSimpleFunction.Type.MAX -> $$1 > this.f_208399_.m_207401_() ? $$1 : Math.max($$1, this.f_208399_.m_207386_(p_208410_));
            };
        }

        @Override
        public void m_207362_(double[] p_208414_, DensityFunction.ContextProvider p_208415_) {
            this.f_208398_.m_207362_(p_208414_, p_208415_);
            switch (this.f_208397_) {
                case ADD: {
                    double[] $$2 = new double[p_208414_.length];
                    this.f_208399_.m_207362_($$2, p_208415_);
                    for (int $$3 = 0; $$3 < p_208414_.length; ++$$3) {
                        p_208414_[$$3] = p_208414_[$$3] + $$2[$$3];
                    }
                    break;
                }
                case MUL: {
                    for (int $$4 = 0; $$4 < p_208414_.length; ++$$4) {
                        double $$5 = p_208414_[$$4];
                        p_208414_[$$4] = $$5 == 0.0 ? 0.0 : $$5 * this.f_208399_.m_207386_(p_208415_.m_207263_($$4));
                    }
                    break;
                }
                case MIN: {
                    double $$6 = this.f_208399_.m_207402_();
                    for (int $$7 = 0; $$7 < p_208414_.length; ++$$7) {
                        double $$8 = p_208414_[$$7];
                        p_208414_[$$7] = $$8 < $$6 ? $$8 : Math.min($$8, this.f_208399_.m_207386_(p_208415_.m_207263_($$7)));
                    }
                    break;
                }
                case MAX: {
                    double $$9 = this.f_208399_.m_207401_();
                    for (int $$10 = 0; $$10 < p_208414_.length; ++$$10) {
                        double $$11 = p_208414_[$$10];
                        p_208414_[$$10] = $$11 > $$9 ? $$11 : Math.max($$11, this.f_208399_.m_207386_(p_208415_.m_207263_($$10)));
                    }
                    break;
                }
            }
        }

        @Override
        public DensityFunction m_207456_(DensityFunction.Visitor p_208412_) {
            return p_208412_.m_214017_(TwoArgumentSimpleFunction.m_209073_(this.f_208397_, this.f_208398_.m_207456_(p_208412_), this.f_208399_.m_207456_(p_208412_)));
        }

        @Override
        public double m_207402_() {
            return this.f_208400_;
        }

        @Override
        public double m_207401_() {
            return this.f_208401_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Ap2.class, "type;argument1;argument2;minValue;maxValue", "f_208397_", "f_208398_", "f_208399_", "f_208400_", "f_208401_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Ap2.class, "type;argument1;argument2;minValue;maxValue", "f_208397_", "f_208398_", "f_208399_", "f_208400_", "f_208401_"}, this);
        }

        @Override
        public final boolean equals(Object p_208418_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Ap2.class, "type;argument1;argument2;minValue;maxValue", "f_208397_", "f_208398_", "f_208399_", "f_208400_", "f_208401_"}, this, p_208418_);
        }

        @Override
        public TwoArgumentSimpleFunction.Type m_207119_() {
            return this.f_208397_;
        }

        @Override
        public DensityFunction m_207185_() {
            return this.f_208398_;
        }

        @Override
        public DensityFunction m_207190_() {
            return this.f_208399_;
        }
    }

    static final class MulOrAdd
    extends Record
    implements PureTransformer,
    TwoArgumentSimpleFunction {
        private final Type f_208746_;
        private final DensityFunction f_208747_;
        private final double f_208748_;
        private final double f_208749_;
        private final double f_208750_;

        MulOrAdd(Type f_208746_, DensityFunction f_208747_, double f_208748_, double f_208749_, double f_208750_) {
            this.f_208746_ = f_208746_;
            this.f_208747_ = f_208747_;
            this.f_208748_ = f_208748_;
            this.f_208749_ = f_208749_;
            this.f_208750_ = f_208750_;
        }

        @Override
        public TwoArgumentSimpleFunction.Type m_207119_() {
            return this.f_208746_ == Type.MUL ? TwoArgumentSimpleFunction.Type.MUL : TwoArgumentSimpleFunction.Type.ADD;
        }

        @Override
        public DensityFunction m_207185_() {
            return DensityFunctions.m_208264_(this.f_208750_);
        }

        @Override
        public DensityFunction m_207190_() {
            return this.f_208747_;
        }

        @Override
        public double m_207382_(double p_208759_) {
            return switch (this.f_208746_) {
                default -> throw new IncompatibleClassChangeError();
                case Type.MUL -> p_208759_ * this.f_208750_;
                case Type.ADD -> p_208759_ + this.f_208750_;
            };
        }

        @Override
        public DensityFunction m_207456_(DensityFunction.Visitor p_208761_) {
            double $$9;
            double $$8;
            DensityFunction $$1 = this.f_208747_.m_207456_(p_208761_);
            double $$2 = $$1.m_207402_();
            double $$3 = $$1.m_207401_();
            if (this.f_208746_ == Type.ADD) {
                double $$4 = $$2 + this.f_208750_;
                double $$5 = $$3 + this.f_208750_;
            } else if (this.f_208750_ >= 0.0) {
                double $$6 = $$2 * this.f_208750_;
                double $$7 = $$3 * this.f_208750_;
            } else {
                $$8 = $$3 * this.f_208750_;
                $$9 = $$2 * this.f_208750_;
            }
            return new MulOrAdd(this.f_208746_, $$1, $$8, $$9, this.f_208750_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{MulOrAdd.class, "specificType;input;minValue;maxValue;argument", "f_208746_", "f_208747_", "f_208748_", "f_208749_", "f_208750_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{MulOrAdd.class, "specificType;input;minValue;maxValue;argument", "f_208746_", "f_208747_", "f_208748_", "f_208749_", "f_208750_"}, this);
        }

        @Override
        public final boolean equals(Object p_208765_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{MulOrAdd.class, "specificType;input;minValue;maxValue;argument", "f_208746_", "f_208747_", "f_208748_", "f_208749_", "f_208750_"}, this, p_208765_);
        }

        public Type f_208746_() {
            return this.f_208746_;
        }

        @Override
        public DensityFunction m_207305_() {
            return this.f_208747_;
        }

        @Override
        public double m_207402_() {
            return this.f_208748_;
        }

        @Override
        public double m_207401_() {
            return this.f_208749_;
        }

        public double f_208750_() {
            return this.f_208750_;
        }

        static final class Type
        extends Enum<Type> {
            public static final /* enum */ Type MUL = new Type();
            public static final /* enum */ Type ADD = new Type();
            private static final /* synthetic */ Type[] $VALUES;

            public static Type[] values() {
                return (Type[])$VALUES.clone();
            }

            public static Type valueOf(String p_208782_) {
                return Enum.valueOf(Type.class, p_208782_);
            }

            private static /* synthetic */ Type[] m_208780_() {
                return new Type[]{MUL, ADD};
            }

            static {
                $VALUES = Type.m_208780_();
            }
        }
    }

    static interface ShiftNoise
    extends DensityFunction {
        public DensityFunction.NoiseHolder m_214040_();

        @Override
        default public double m_207402_() {
            return -this.m_207401_();
        }

        @Override
        default public double m_207401_() {
            return this.m_214040_().m_224005_() * 4.0;
        }

        default public double m_208917_(double p_208918_, double p_208919_, double p_208920_) {
            return this.m_214040_().m_224006_(p_208918_ * 0.25, p_208919_ * 0.25, p_208920_ * 0.25) * 4.0;
        }

        @Override
        default public void m_207362_(double[] p_224103_, DensityFunction.ContextProvider p_224104_) {
            p_224104_.m_207207_(p_224103_, this);
        }
    }

    public static interface MarkerOrMarked
    extends DensityFunction {
        public Marker.Type m_207136_();

        public DensityFunction m_207056_();

        @Override
        default public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return this.m_207136_().f_208731_;
        }

        @Override
        default public DensityFunction m_207456_(DensityFunction.Visitor p_224070_) {
            return p_224070_.m_214017_(new Marker(this.m_207136_(), this.m_207056_().m_207456_(p_224070_)));
        }
    }

    @VisibleForDebug
    public record HolderHolder(Holder<DensityFunction> f_208636_) implements DensityFunction
    {
        @Override
        public double m_207386_(DensityFunction.FunctionContext p_208641_) {
            return this.f_208636_.m_203334_().m_207386_(p_208641_);
        }

        @Override
        public void m_207362_(double[] p_208645_, DensityFunction.ContextProvider p_208646_) {
            this.f_208636_.m_203334_().m_207362_(p_208645_, p_208646_);
        }

        @Override
        public DensityFunction m_207456_(DensityFunction.Visitor p_208643_) {
            return p_208643_.m_214017_(new HolderHolder(new Holder.Direct<DensityFunction>(this.f_208636_.m_203334_().m_207456_(p_208643_))));
        }

        @Override
        public double m_207402_() {
            return this.f_208636_.m_203334_().m_207402_();
        }

        @Override
        public double m_207401_() {
            return this.f_208636_.m_203334_().m_207401_();
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            throw new UnsupportedOperationException("Calling .codec() on HolderHolder");
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{HolderHolder.class, "function", "f_208636_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{HolderHolder.class, "function", "f_208636_"}, this);
        }

        @Override
        public final boolean equals(Object p_208650_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{HolderHolder.class, "function", "f_208636_"}, this, p_208650_);
        }
    }

    public static interface BeardifierOrMarker
    extends DensityFunction.SimpleFunction {
        public static final KeyDispatchDataCodec<DensityFunction> f_208524_ = KeyDispatchDataCodec.m_216238_(MapCodec.unit((Object)BeardifierMarker.INSTANCE));

        @Override
        default public KeyDispatchDataCodec<? extends DensityFunction> m_214023_() {
            return f_208524_;
        }
    }

    static interface PureTransformer
    extends DensityFunction {
        public DensityFunction m_207305_();

        @Override
        default public double m_207386_(DensityFunction.FunctionContext p_208817_) {
            return this.m_207382_(this.m_207305_().m_207386_(p_208817_));
        }

        @Override
        default public void m_207362_(double[] p_208819_, DensityFunction.ContextProvider p_208820_) {
            this.m_207305_().m_207362_(p_208819_, p_208820_);
            for (int $$2 = 0; $$2 < p_208819_.length; ++$$2) {
                p_208819_[$$2] = this.m_207382_(p_208819_[$$2]);
            }
        }

        public double m_207382_(double var1);
    }

    static interface TransformerWithContext
    extends DensityFunction {
        public DensityFunction m_207189_();

        @Override
        default public double m_207386_(DensityFunction.FunctionContext p_209065_) {
            return this.m_207219_(p_209065_, this.m_207189_().m_207386_(p_209065_));
        }

        @Override
        default public void m_207362_(double[] p_209069_, DensityFunction.ContextProvider p_209070_) {
            this.m_207189_().m_207362_(p_209069_, p_209070_);
            for (int $$2 = 0; $$2 < p_209069_.length; ++$$2) {
                p_209069_[$$2] = this.m_207219_(p_209070_.m_207263_($$2), p_209069_[$$2]);
            }
        }

        public double m_207219_(DensityFunction.FunctionContext var1, double var2);
    }
}

