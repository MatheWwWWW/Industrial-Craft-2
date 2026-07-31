/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Suppliers
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.levelgen;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.SurfaceSystem;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class SurfaceRules {
    public static final ConditionSource f_189375_ = SurfaceRules.m_202176_(0, false, CaveSurface.FLOOR);
    public static final ConditionSource f_189376_ = SurfaceRules.m_202176_(0, true, CaveSurface.FLOOR);
    public static final ConditionSource f_202169_ = SurfaceRules.m_202171_(0, true, 6, CaveSurface.FLOOR);
    public static final ConditionSource f_202170_ = SurfaceRules.m_202171_(0, true, 30, CaveSurface.FLOOR);
    public static final ConditionSource f_189377_ = SurfaceRules.m_202176_(0, false, CaveSurface.CEILING);
    public static final ConditionSource f_189378_ = SurfaceRules.m_202176_(0, true, CaveSurface.CEILING);

    public static ConditionSource m_202176_(int p_202177_, boolean p_202178_, CaveSurface p_202179_) {
        return new StoneDepthCheck(p_202177_, p_202178_, 0, p_202179_);
    }

    public static ConditionSource m_202171_(int p_202172_, boolean p_202173_, int p_202174_, CaveSurface p_202175_) {
        return new StoneDepthCheck(p_202172_, p_202173_, p_202174_, p_202175_);
    }

    public static ConditionSource m_189392_(ConditionSource p_189393_) {
        return new NotConditionSource(p_189393_);
    }

    public static ConditionSource m_189400_(VerticalAnchor p_189401_, int p_189402_) {
        return new YConditionSource(p_189401_, p_189402_, false);
    }

    public static ConditionSource m_189422_(VerticalAnchor p_189423_, int p_189424_) {
        return new YConditionSource(p_189423_, p_189424_, true);
    }

    public static ConditionSource m_189382_(int p_189383_, int p_189384_) {
        return new WaterConditionSource(p_189383_, p_189384_, false);
    }

    public static ConditionSource m_189419_(int p_189420_, int p_189421_) {
        return new WaterConditionSource(p_189420_, p_189421_, true);
    }

    @SafeVarargs
    public static ConditionSource m_189416_(ResourceKey<Biome> ... p_189417_) {
        return SurfaceRules.m_189407_(List.of(p_189417_));
    }

    private static BiomeConditionSource m_189407_(List<ResourceKey<Biome>> p_189408_) {
        return new BiomeConditionSource(p_189408_);
    }

    public static ConditionSource m_189409_(ResourceKey<NormalNoise.NoiseParameters> p_189410_, double p_189411_) {
        return SurfaceRules.m_189412_(p_189410_, p_189411_, Double.MAX_VALUE);
    }

    public static ConditionSource m_189412_(ResourceKey<NormalNoise.NoiseParameters> p_189413_, double p_189414_, double p_189415_) {
        return new NoiseThresholdConditionSource(p_189413_, p_189414_, p_189415_);
    }

    public static ConditionSource m_189403_(String p_189404_, VerticalAnchor p_189405_, VerticalAnchor p_189406_) {
        return new VerticalGradientConditionSource(new ResourceLocation(p_189404_), p_189405_, p_189406_);
    }

    public static ConditionSource m_189381_() {
        return Steep.INSTANCE;
    }

    public static ConditionSource m_189418_() {
        return Hole.INSTANCE;
    }

    public static ConditionSource m_189425_() {
        return AbovePreliminarySurface.INSTANCE;
    }

    public static ConditionSource m_189426_() {
        return Temperature.INSTANCE;
    }

    public static RuleSource m_189394_(ConditionSource p_189395_, RuleSource p_189396_) {
        return new TestRuleSource(p_189395_, p_189396_);
    }

    public static RuleSource m_198272_(RuleSource ... p_198273_) {
        if (p_198273_.length == 0) {
            throw new IllegalArgumentException("Need at least 1 rule for a sequence");
        }
        return new SequenceRuleSource(Arrays.asList(p_198273_));
    }

    public static RuleSource m_189390_(BlockState p_189391_) {
        return new BlockRuleSource(p_189391_);
    }

    public static RuleSource m_189427_() {
        return Bandlands.INSTANCE;
    }

    static <A> Codec<? extends A> m_224603_(Registry<Codec<? extends A>> p_224604_, String p_224605_, KeyDispatchDataCodec<? extends A> p_224606_) {
        return Registry.m_122961_(p_224604_, p_224605_, p_224606_.f_216232_());
    }

    record StoneDepthCheck(int f_189740_, boolean f_189741_, int f_202182_, CaveSurface f_189743_) implements ConditionSource
    {
        static final KeyDispatchDataCodec<StoneDepthCheck> f_189744_ = KeyDispatchDataCodec.m_216238_(RecordCodecBuilder.mapCodec(p_189753_ -> p_189753_.group((App)Codec.INT.fieldOf("offset").forGetter(StoneDepthCheck::f_189740_), (App)Codec.BOOL.fieldOf("add_surface_depth").forGetter(StoneDepthCheck::f_189741_), (App)Codec.INT.fieldOf("secondary_depth_range").forGetter(StoneDepthCheck::f_202182_), (App)CaveSurface.f_162094_.fieldOf("surface_type").forGetter(StoneDepthCheck::f_189743_)).apply((Applicative)p_189753_, StoneDepthCheck::new)));

        @Override
        public KeyDispatchDataCodec<? extends ConditionSource> m_213794_() {
            return f_189744_;
        }

        @Override
        public Condition apply(final Context p_189755_) {
            final boolean $$1 = this.f_189743_ == CaveSurface.CEILING;
            class StoneDepthCondition
            extends LazyYCondition {
                StoneDepthCondition() {
                    super(context);
                }

                @Override
                protected boolean m_183479_() {
                    int $$0 = $$1 ? this.f_189616_.f_189559_ : this.f_189616_.f_189560_;
                    int $$12 = StoneDepthCheck.this.f_189741_ ? this.f_189616_.f_189548_ : 0;
                    int $$2 = StoneDepthCheck.this.f_202182_ == 0 ? 0 : (int)Mth.m_144914_(this.f_189616_.m_202181_(), -1.0, 1.0, 0.0, StoneDepthCheck.this.f_202182_);
                    return $$0 <= 1 + StoneDepthCheck.this.f_189740_ + $$12 + $$2;
                }
            }
            return new StoneDepthCondition();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{StoneDepthCheck.class, "offset;addSurfaceDepth;secondaryDepthRange;surfaceType", "f_189740_", "f_189741_", "f_202182_", "f_189743_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{StoneDepthCheck.class, "offset;addSurfaceDepth;secondaryDepthRange;surfaceType", "f_189740_", "f_189741_", "f_202182_", "f_189743_"}, this);
        }

        @Override
        public final boolean equals(Object p_189762_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{StoneDepthCheck.class, "offset;addSurfaceDepth;secondaryDepthRange;surfaceType", "f_189740_", "f_189741_", "f_202182_", "f_189743_"}, this, p_189762_);
        }

        @Override
        public /* synthetic */ Object apply(Object object) {
            return this.apply((Context)object);
        }
    }

    record NotConditionSource(ConditionSource f_189667_) implements ConditionSource
    {
        static final KeyDispatchDataCodec<NotConditionSource> f_189668_ = KeyDispatchDataCodec.m_216238_(ConditionSource.f_189532_.xmap(NotConditionSource::new, NotConditionSource::f_189667_).fieldOf("invert"));

        @Override
        public KeyDispatchDataCodec<? extends ConditionSource> m_213794_() {
            return f_189668_;
        }

        @Override
        public Condition apply(Context p_189674_) {
            return new NotCondition((Condition)this.f_189667_.apply(p_189674_));
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{NotConditionSource.class, "target", "f_189667_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{NotConditionSource.class, "target", "f_189667_"}, this);
        }

        @Override
        public final boolean equals(Object p_189679_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{NotConditionSource.class, "target", "f_189667_"}, this, p_189679_);
        }

        @Override
        public /* synthetic */ Object apply(Object object) {
            return this.apply((Context)object);
        }
    }

    public static interface ConditionSource
    extends Function<Context, Condition> {
        public static final Codec<ConditionSource> f_189532_ = Registry.f_194573_.m_194605_().dispatch(p_224613_ -> p_224613_.m_213794_().f_216232_(), Function.identity());

        public static Codec<? extends ConditionSource> m_204624_(Registry<Codec<? extends ConditionSource>> p_204625_) {
            SurfaceRules.m_224603_(p_204625_, "biome", BiomeConditionSource.f_189490_);
            SurfaceRules.m_224603_(p_204625_, "noise_threshold", NoiseThresholdConditionSource.f_189630_);
            SurfaceRules.m_224603_(p_204625_, "vertical_gradient", VerticalGradientConditionSource.f_189831_);
            SurfaceRules.m_224603_(p_204625_, "y_above", YConditionSource.f_189447_);
            SurfaceRules.m_224603_(p_204625_, "water", WaterConditionSource.f_189866_);
            SurfaceRules.m_224603_(p_204625_, "temperature", Temperature.f_189778_);
            SurfaceRules.m_224603_(p_204625_, "steep", Steep.f_189725_);
            SurfaceRules.m_224603_(p_204625_, "not", NotConditionSource.f_189668_);
            SurfaceRules.m_224603_(p_204625_, "hole", Hole.f_189600_);
            SurfaceRules.m_224603_(p_204625_, "above_preliminary_surface", AbovePreliminarySurface.f_189429_);
            return SurfaceRules.m_224603_(p_204625_, "stone_depth", StoneDepthCheck.f_189744_);
        }

        public KeyDispatchDataCodec<? extends ConditionSource> m_213794_();
    }

    record YConditionSource(VerticalAnchor f_189444_, int f_189445_, boolean f_189446_) implements ConditionSource
    {
        static final KeyDispatchDataCodec<YConditionSource> f_189447_ = KeyDispatchDataCodec.m_216238_(RecordCodecBuilder.mapCodec(p_189455_ -> p_189455_.group((App)VerticalAnchor.f_158914_.fieldOf("anchor").forGetter(YConditionSource::f_189444_), (App)Codec.intRange((int)-20, (int)20).fieldOf("surface_depth_multiplier").forGetter(YConditionSource::f_189445_), (App)Codec.BOOL.fieldOf("add_stone_depth").forGetter(YConditionSource::f_189446_)).apply((Applicative)p_189455_, YConditionSource::new)));

        @Override
        public KeyDispatchDataCodec<? extends ConditionSource> m_213794_() {
            return f_189447_;
        }

        @Override
        public Condition apply(final Context p_189457_) {
            class YCondition
            extends LazyYCondition {
                YCondition() {
                    super(context);
                }

                @Override
                protected boolean m_183479_() {
                    return this.f_189616_.f_189557_ + (YConditionSource.this.f_189446_ ? this.f_189616_.f_189560_ : 0) >= YConditionSource.this.f_189444_.m_142322_(this.f_189616_.f_189544_) + this.f_189616_.f_189548_ * YConditionSource.this.f_189445_;
                }
            }
            return new YCondition();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{YConditionSource.class, "anchor;surfaceDepthMultiplier;addStoneDepth", "f_189444_", "f_189445_", "f_189446_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{YConditionSource.class, "anchor;surfaceDepthMultiplier;addStoneDepth", "f_189444_", "f_189445_", "f_189446_"}, this);
        }

        @Override
        public final boolean equals(Object p_189464_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{YConditionSource.class, "anchor;surfaceDepthMultiplier;addStoneDepth", "f_189444_", "f_189445_", "f_189446_"}, this, p_189464_);
        }

        @Override
        public /* synthetic */ Object apply(Object object) {
            return this.apply((Context)object);
        }
    }

    record WaterConditionSource(int f_189863_, int f_189864_, boolean f_189865_) implements ConditionSource
    {
        static final KeyDispatchDataCodec<WaterConditionSource> f_189866_ = KeyDispatchDataCodec.m_216238_(RecordCodecBuilder.mapCodec(p_189874_ -> p_189874_.group((App)Codec.INT.fieldOf("offset").forGetter(WaterConditionSource::f_189863_), (App)Codec.intRange((int)-20, (int)20).fieldOf("surface_depth_multiplier").forGetter(WaterConditionSource::f_189864_), (App)Codec.BOOL.fieldOf("add_stone_depth").forGetter(WaterConditionSource::f_189865_)).apply((Applicative)p_189874_, WaterConditionSource::new)));

        @Override
        public KeyDispatchDataCodec<? extends ConditionSource> m_213794_() {
            return f_189866_;
        }

        @Override
        public Condition apply(final Context p_189876_) {
            class WaterCondition
            extends LazyYCondition {
                WaterCondition() {
                    super(context);
                }

                @Override
                protected boolean m_183479_() {
                    return this.f_189616_.f_189558_ == Integer.MIN_VALUE || this.f_189616_.f_189557_ + (WaterConditionSource.this.f_189865_ ? this.f_189616_.f_189560_ : 0) >= this.f_189616_.f_189558_ + WaterConditionSource.this.f_189863_ + this.f_189616_.f_189548_ * WaterConditionSource.this.f_189864_;
                }
            }
            return new WaterCondition();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{WaterConditionSource.class, "offset;surfaceDepthMultiplier;addStoneDepth", "f_189863_", "f_189864_", "f_189865_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{WaterConditionSource.class, "offset;surfaceDepthMultiplier;addStoneDepth", "f_189863_", "f_189864_", "f_189865_"}, this);
        }

        @Override
        public final boolean equals(Object p_189883_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{WaterConditionSource.class, "offset;surfaceDepthMultiplier;addStoneDepth", "f_189863_", "f_189864_", "f_189865_"}, this, p_189883_);
        }

        @Override
        public /* synthetic */ Object apply(Object object) {
            return this.apply((Context)object);
        }
    }

    static final class BiomeConditionSource
    implements ConditionSource {
        static final KeyDispatchDataCodec<BiomeConditionSource> f_189490_ = KeyDispatchDataCodec.m_216238_(ResourceKey.m_195966_(Registry.f_122885_).listOf().fieldOf("biome_is").xmap(SurfaceRules::m_189407_, p_204620_ -> p_204620_.f_189489_));
        private final List<ResourceKey<Biome>> f_189489_;
        final Predicate<ResourceKey<Biome>> f_204618_;

        BiomeConditionSource(List<ResourceKey<Biome>> p_189493_) {
            this.f_189489_ = p_189493_;
            this.f_204618_ = Set.copyOf(p_189493_)::contains;
        }

        @Override
        public KeyDispatchDataCodec<? extends ConditionSource> m_213794_() {
            return f_189490_;
        }

        @Override
        public Condition apply(final Context p_189496_) {
            class BiomeCondition
            extends LazyYCondition {
                BiomeCondition() {
                    super(context);
                }

                @Override
                protected boolean m_183479_() {
                    return this.f_189616_.f_189555_.get().m_203425_(BiomeConditionSource.this.f_204618_);
                }
            }
            return new BiomeCondition();
        }

        public boolean equals(Object p_209694_) {
            if (this == p_209694_) {
                return true;
            }
            if (p_209694_ instanceof BiomeConditionSource) {
                BiomeConditionSource $$1 = (BiomeConditionSource)p_209694_;
                return this.f_189489_.equals($$1.f_189489_);
            }
            return false;
        }

        public int hashCode() {
            return this.f_189489_.hashCode();
        }

        public String toString() {
            return "BiomeConditionSource[biomes=" + this.f_189489_ + "]";
        }

        @Override
        public /* synthetic */ Object apply(Object object) {
            return this.apply((Context)object);
        }
    }

    record NoiseThresholdConditionSource(ResourceKey<NormalNoise.NoiseParameters> f_189627_, double f_189628_, double f_189629_) implements ConditionSource
    {
        static final KeyDispatchDataCodec<NoiseThresholdConditionSource> f_189630_ = KeyDispatchDataCodec.m_216238_(RecordCodecBuilder.mapCodec(p_189638_ -> p_189638_.group((App)ResourceKey.m_195966_(Registry.f_194568_).fieldOf("noise").forGetter(NoiseThresholdConditionSource::f_189627_), (App)Codec.DOUBLE.fieldOf("min_threshold").forGetter(NoiseThresholdConditionSource::f_189628_), (App)Codec.DOUBLE.fieldOf("max_threshold").forGetter(NoiseThresholdConditionSource::f_189629_)).apply((Applicative)p_189638_, NoiseThresholdConditionSource::new)));

        @Override
        public KeyDispatchDataCodec<? extends ConditionSource> m_213794_() {
            return f_189630_;
        }

        @Override
        public Condition apply(final Context p_189640_) {
            final NormalNoise $$1 = p_189640_.f_224614_.m_224560_(this.f_189627_);
            class NoiseThresholdCondition
            extends LazyXZCondition {
                NoiseThresholdCondition() {
                    super(context);
                }

                @Override
                protected boolean m_183479_() {
                    double $$0 = $$1.m_75380_(this.f_189616_.f_189546_, 0.0, this.f_189616_.f_189547_);
                    return $$0 >= NoiseThresholdConditionSource.this.f_189628_ && $$0 <= NoiseThresholdConditionSource.this.f_189629_;
                }
            }
            return new NoiseThresholdCondition();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{NoiseThresholdConditionSource.class, "noise;minThreshold;maxThreshold", "f_189627_", "f_189628_", "f_189629_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{NoiseThresholdConditionSource.class, "noise;minThreshold;maxThreshold", "f_189627_", "f_189628_", "f_189629_"}, this);
        }

        @Override
        public final boolean equals(Object p_189647_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{NoiseThresholdConditionSource.class, "noise;minThreshold;maxThreshold", "f_189627_", "f_189628_", "f_189629_"}, this, p_189647_);
        }

        @Override
        public /* synthetic */ Object apply(Object object) {
            return this.apply((Context)object);
        }
    }

    record VerticalGradientConditionSource(ResourceLocation f_189828_, VerticalAnchor f_189829_, VerticalAnchor f_189830_) implements ConditionSource
    {
        static final KeyDispatchDataCodec<VerticalGradientConditionSource> f_189831_ = KeyDispatchDataCodec.m_216238_(RecordCodecBuilder.mapCodec(p_189839_ -> p_189839_.group((App)ResourceLocation.f_135803_.fieldOf("random_name").forGetter(VerticalGradientConditionSource::f_189828_), (App)VerticalAnchor.f_158914_.fieldOf("true_at_and_below").forGetter(VerticalGradientConditionSource::f_189829_), (App)VerticalAnchor.f_158914_.fieldOf("false_at_and_above").forGetter(VerticalGradientConditionSource::f_189830_)).apply((Applicative)p_189839_, VerticalGradientConditionSource::new)));

        @Override
        public KeyDispatchDataCodec<? extends ConditionSource> m_213794_() {
            return f_189831_;
        }

        @Override
        public Condition apply(final Context p_189841_) {
            final int $$1 = this.f_189829_().m_142322_(p_189841_.f_189544_);
            final int $$2 = this.f_189830_().m_142322_(p_189841_.f_189544_);
            final PositionalRandomFactory $$3 = p_189841_.f_224614_.m_224565_(this.f_189828_());
            class VerticalGradientCondition
            extends LazyYCondition {
                VerticalGradientCondition() {
                    super(context);
                }

                @Override
                protected boolean m_183479_() {
                    int $$0 = this.f_189616_.f_189557_;
                    if ($$0 <= $$1) {
                        return true;
                    }
                    if ($$0 >= $$2) {
                        return false;
                    }
                    double $$12 = Mth.m_144914_($$0, $$1, $$2, 1.0, 0.0);
                    RandomSource $$22 = $$3.m_213715_(this.f_189616_.f_189546_, $$0, this.f_189616_.f_189547_);
                    return (double)$$22.m_188501_() < $$12;
                }
            }
            return new VerticalGradientCondition();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{VerticalGradientConditionSource.class, "randomName;trueAtAndBelow;falseAtAndAbove", "f_189828_", "f_189829_", "f_189830_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{VerticalGradientConditionSource.class, "randomName;trueAtAndBelow;falseAtAndAbove", "f_189828_", "f_189829_", "f_189830_"}, this);
        }

        @Override
        public final boolean equals(Object p_189848_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{VerticalGradientConditionSource.class, "randomName;trueAtAndBelow;falseAtAndAbove", "f_189828_", "f_189829_", "f_189830_"}, this, p_189848_);
        }

        @Override
        public /* synthetic */ Object apply(Object object) {
            return this.apply((Context)object);
        }
    }

    static final class Steep
    extends Enum<Steep>
    implements ConditionSource {
        public static final /* enum */ Steep INSTANCE = new Steep();
        static final KeyDispatchDataCodec<Steep> f_189725_;
        private static final /* synthetic */ Steep[] $VALUES;

        public static Steep[] values() {
            return (Steep[])$VALUES.clone();
        }

        public static Steep valueOf(String p_189738_) {
            return Enum.valueOf(Steep.class, p_189738_);
        }

        @Override
        public KeyDispatchDataCodec<? extends ConditionSource> m_213794_() {
            return f_189725_;
        }

        @Override
        public Condition apply(Context p_189733_) {
            return p_189733_.f_189537_;
        }

        @Override
        public /* synthetic */ Object apply(Object object) {
            return this.apply((Context)object);
        }

        private static /* synthetic */ Steep[] m_189736_() {
            return new Steep[]{INSTANCE};
        }

        static {
            $VALUES = Steep.m_189736_();
            f_189725_ = KeyDispatchDataCodec.m_216238_(MapCodec.unit((Object)INSTANCE));
        }
    }

    static final class Hole
    extends Enum<Hole>
    implements ConditionSource {
        public static final /* enum */ Hole INSTANCE = new Hole();
        static final KeyDispatchDataCodec<Hole> f_189600_;
        private static final /* synthetic */ Hole[] $VALUES;

        public static Hole[] values() {
            return (Hole[])$VALUES.clone();
        }

        public static Hole valueOf(String p_189613_) {
            return Enum.valueOf(Hole.class, p_189613_);
        }

        @Override
        public KeyDispatchDataCodec<? extends ConditionSource> m_213794_() {
            return f_189600_;
        }

        @Override
        public Condition apply(Context p_189608_) {
            return p_189608_.f_189538_;
        }

        @Override
        public /* synthetic */ Object apply(Object object) {
            return this.apply((Context)object);
        }

        private static /* synthetic */ Hole[] m_189611_() {
            return new Hole[]{INSTANCE};
        }

        static {
            $VALUES = Hole.m_189611_();
            f_189600_ = KeyDispatchDataCodec.m_216238_(MapCodec.unit((Object)INSTANCE));
        }
    }

    static final class AbovePreliminarySurface
    extends Enum<AbovePreliminarySurface>
    implements ConditionSource {
        public static final /* enum */ AbovePreliminarySurface INSTANCE = new AbovePreliminarySurface();
        static final KeyDispatchDataCodec<AbovePreliminarySurface> f_189429_;
        private static final /* synthetic */ AbovePreliminarySurface[] $VALUES;

        public static AbovePreliminarySurface[] values() {
            return (AbovePreliminarySurface[])$VALUES.clone();
        }

        public static AbovePreliminarySurface valueOf(String p_189442_) {
            return Enum.valueOf(AbovePreliminarySurface.class, p_189442_);
        }

        @Override
        public KeyDispatchDataCodec<? extends ConditionSource> m_213794_() {
            return f_189429_;
        }

        @Override
        public Condition apply(Context p_189437_) {
            return p_189437_.f_189539_;
        }

        @Override
        public /* synthetic */ Object apply(Object object) {
            return this.apply((Context)object);
        }

        private static /* synthetic */ AbovePreliminarySurface[] m_189440_() {
            return new AbovePreliminarySurface[]{INSTANCE};
        }

        static {
            $VALUES = AbovePreliminarySurface.m_189440_();
            f_189429_ = KeyDispatchDataCodec.m_216238_(MapCodec.unit((Object)INSTANCE));
        }
    }

    static final class Temperature
    extends Enum<Temperature>
    implements ConditionSource {
        public static final /* enum */ Temperature INSTANCE = new Temperature();
        static final KeyDispatchDataCodec<Temperature> f_189778_;
        private static final /* synthetic */ Temperature[] $VALUES;

        public static Temperature[] values() {
            return (Temperature[])$VALUES.clone();
        }

        public static Temperature valueOf(String p_189791_) {
            return Enum.valueOf(Temperature.class, p_189791_);
        }

        @Override
        public KeyDispatchDataCodec<? extends ConditionSource> m_213794_() {
            return f_189778_;
        }

        @Override
        public Condition apply(Context p_189786_) {
            return p_189786_.f_189536_;
        }

        @Override
        public /* synthetic */ Object apply(Object object) {
            return this.apply((Context)object);
        }

        private static /* synthetic */ Temperature[] m_189789_() {
            return new Temperature[]{INSTANCE};
        }

        static {
            $VALUES = Temperature.m_189789_();
            f_189778_ = KeyDispatchDataCodec.m_216238_(MapCodec.unit((Object)INSTANCE));
        }
    }

    record TestRuleSource(ConditionSource f_189808_, RuleSource f_189809_) implements RuleSource
    {
        static final KeyDispatchDataCodec<TestRuleSource> f_189810_ = KeyDispatchDataCodec.m_216238_(RecordCodecBuilder.mapCodec(p_189817_ -> p_189817_.group((App)ConditionSource.f_189532_.fieldOf("if_true").forGetter(TestRuleSource::f_189808_), (App)RuleSource.f_189682_.fieldOf("then_run").forGetter(TestRuleSource::f_189809_)).apply((Applicative)p_189817_, TestRuleSource::new)));

        @Override
        public KeyDispatchDataCodec<? extends RuleSource> m_213795_() {
            return f_189810_;
        }

        @Override
        public SurfaceRule apply(Context p_189819_) {
            return new TestRule((Condition)this.f_189808_.apply(p_189819_), (SurfaceRule)this.f_189809_.apply(p_189819_));
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{TestRuleSource.class, "ifTrue;thenRun", "f_189808_", "f_189809_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{TestRuleSource.class, "ifTrue;thenRun", "f_189808_", "f_189809_"}, this);
        }

        @Override
        public final boolean equals(Object p_189825_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{TestRuleSource.class, "ifTrue;thenRun", "f_189808_", "f_189809_"}, this, p_189825_);
        }

        @Override
        public /* synthetic */ Object apply(Object object) {
            return this.apply((Context)object);
        }
    }

    public static interface RuleSource
    extends Function<Context, SurfaceRule> {
        public static final Codec<RuleSource> f_189682_ = Registry.f_194574_.m_194605_().dispatch(p_224627_ -> p_224627_.m_213795_().f_216232_(), Function.identity());

        public static Codec<? extends RuleSource> m_204630_(Registry<Codec<? extends RuleSource>> p_204631_) {
            SurfaceRules.m_224603_(p_204631_, "bandlands", Bandlands.f_189474_);
            SurfaceRules.m_224603_(p_204631_, "block", BlockRuleSource.f_189514_);
            SurfaceRules.m_224603_(p_204631_, "sequence", SequenceRuleSource.f_189698_);
            return SurfaceRules.m_224603_(p_204631_, "condition", TestRuleSource.f_189810_);
        }

        public KeyDispatchDataCodec<? extends RuleSource> m_213795_();
    }

    record SequenceRuleSource(List<RuleSource> f_189697_) implements RuleSource
    {
        static final KeyDispatchDataCodec<SequenceRuleSource> f_189698_ = KeyDispatchDataCodec.m_216238_(RuleSource.f_189682_.listOf().xmap(SequenceRuleSource::new, SequenceRuleSource::f_189697_).fieldOf("sequence"));

        @Override
        public KeyDispatchDataCodec<? extends RuleSource> m_213795_() {
            return f_189698_;
        }

        @Override
        public SurfaceRule apply(Context p_189704_) {
            if (this.f_189697_.size() == 1) {
                return (SurfaceRule)this.f_189697_.get(0).apply(p_189704_);
            }
            ImmutableList.Builder $$1 = ImmutableList.builder();
            for (RuleSource $$2 : this.f_189697_) {
                $$1.add((Object)((SurfaceRule)$$2.apply(p_189704_)));
            }
            return new SequenceRule((List<SurfaceRule>)$$1.build());
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{SequenceRuleSource.class, "sequence", "f_189697_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{SequenceRuleSource.class, "sequence", "f_189697_"}, this);
        }

        @Override
        public final boolean equals(Object p_189709_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{SequenceRuleSource.class, "sequence", "f_189697_"}, this, p_189709_);
        }

        @Override
        public /* synthetic */ Object apply(Object object) {
            return this.apply((Context)object);
        }
    }

    record BlockRuleSource(BlockState f_189512_, StateRule f_189513_) implements RuleSource
    {
        static final KeyDispatchDataCodec<BlockRuleSource> f_189514_ = KeyDispatchDataCodec.m_216238_(BlockState.f_61039_.xmap(BlockRuleSource::new, BlockRuleSource::f_189512_).fieldOf("result_state"));

        BlockRuleSource(BlockState p_189517_) {
            this(p_189517_, new StateRule(p_189517_));
        }

        @Override
        public KeyDispatchDataCodec<? extends RuleSource> m_213795_() {
            return f_189514_;
        }

        @Override
        public SurfaceRule apply(Context p_189523_) {
            return this.f_189513_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{BlockRuleSource.class, "resultState;rule", "f_189512_", "f_189513_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{BlockRuleSource.class, "resultState;rule", "f_189512_", "f_189513_"}, this);
        }

        @Override
        public final boolean equals(Object p_189529_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{BlockRuleSource.class, "resultState;rule", "f_189512_", "f_189513_"}, this, p_189529_);
        }

        @Override
        public /* synthetic */ Object apply(Object object) {
            return this.apply((Context)object);
        }
    }

    static final class Bandlands
    extends Enum<Bandlands>
    implements RuleSource {
        public static final /* enum */ Bandlands INSTANCE = new Bandlands();
        static final KeyDispatchDataCodec<Bandlands> f_189474_;
        private static final /* synthetic */ Bandlands[] $VALUES;

        public static Bandlands[] values() {
            return (Bandlands[])$VALUES.clone();
        }

        public static Bandlands valueOf(String p_189487_) {
            return Enum.valueOf(Bandlands.class, p_189487_);
        }

        @Override
        public KeyDispatchDataCodec<? extends RuleSource> m_213795_() {
            return f_189474_;
        }

        @Override
        public SurfaceRule apply(Context p_189482_) {
            return p_189482_.f_189535_::m_189930_;
        }

        @Override
        public /* synthetic */ Object apply(Object object) {
            return this.apply((Context)object);
        }

        private static /* synthetic */ Bandlands[] m_189485_() {
            return new Bandlands[]{INSTANCE};
        }

        static {
            $VALUES = Bandlands.m_189485_();
            f_189474_ = KeyDispatchDataCodec.m_216238_(MapCodec.unit((Object)INSTANCE));
        }
    }

    record SequenceRule(List<SurfaceRule> f_189685_) implements SurfaceRule
    {
        @Override
        @Nullable
        public BlockState m_183550_(int p_189694_, int p_189695_, int p_189696_) {
            for (SurfaceRule $$3 : this.f_189685_) {
                BlockState $$4 = $$3.m_183550_(p_189694_, p_189695_, p_189696_);
                if ($$4 == null) continue;
                return $$4;
            }
            return null;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{SequenceRule.class, "rules", "f_189685_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{SequenceRule.class, "rules", "f_189685_"}, this);
        }

        @Override
        public final boolean equals(Object p_189690_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{SequenceRule.class, "rules", "f_189685_"}, this, p_189690_);
        }
    }

    record TestRule(Condition f_189793_, SurfaceRule f_189794_) implements SurfaceRule
    {
        @Override
        @Nullable
        public BlockState m_183550_(int p_189805_, int p_189806_, int p_189807_) {
            if (!this.f_189793_.m_183475_()) {
                return null;
            }
            return this.f_189794_.m_183550_(p_189805_, p_189806_, p_189807_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{TestRule.class, "condition;followup", "f_189793_", "f_189794_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{TestRule.class, "condition;followup", "f_189793_", "f_189794_"}, this);
        }

        @Override
        public final boolean equals(Object p_189801_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{TestRule.class, "condition;followup", "f_189793_", "f_189794_"}, this, p_189801_);
        }
    }

    record StateRule(BlockState f_189712_) implements SurfaceRule
    {
        @Override
        public BlockState m_183550_(int p_189721_, int p_189722_, int p_189723_) {
            return this.f_189712_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{StateRule.class, "state", "f_189712_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{StateRule.class, "state", "f_189712_"}, this);
        }

        @Override
        public final boolean equals(Object p_189717_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{StateRule.class, "state", "f_189712_"}, this, p_189717_);
        }
    }

    protected static interface SurfaceRule {
        @Nullable
        public BlockState m_183550_(int var1, int var2, int var3);
    }

    record NotCondition(Condition f_189658_) implements Condition
    {
        @Override
        public boolean m_183475_() {
            return !this.f_189658_.m_183475_();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{NotCondition.class, "target", "f_189658_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{NotCondition.class, "target", "f_189658_"}, this);
        }

        @Override
        public final boolean equals(Object p_189664_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{NotCondition.class, "target", "f_189658_"}, this, p_189664_);
        }
    }

    static abstract class LazyYCondition
    extends LazyCondition {
        protected LazyYCondition(Context p_189625_) {
            super(p_189625_);
        }

        @Override
        protected long m_183477_() {
            return this.f_189616_.f_189553_;
        }
    }

    static abstract class LazyXZCondition
    extends LazyCondition {
        protected LazyXZCondition(Context p_189622_) {
            super(p_189622_);
        }

        @Override
        protected long m_183477_() {
            return this.f_189616_.f_189545_;
        }
    }

    static abstract class LazyCondition
    implements Condition {
        protected final Context f_189616_;
        private long f_189615_;
        @Nullable
        Boolean f_189617_;

        protected LazyCondition(Context p_189619_) {
            this.f_189616_ = p_189619_;
            this.f_189615_ = this.m_183477_() - 1L;
        }

        @Override
        public boolean m_183475_() {
            long $$0 = this.m_183477_();
            if ($$0 == this.f_189615_) {
                if (this.f_189617_ == null) {
                    throw new IllegalStateException("Update triggered but the result is null");
                }
                return this.f_189617_;
            }
            this.f_189615_ = $$0;
            this.f_189617_ = this.m_183479_();
            return this.f_189617_;
        }

        protected abstract long m_183477_();

        protected abstract boolean m_183479_();
    }

    static interface Condition {
        public boolean m_183475_();
    }

    protected static final class Context {
        private static final int f_198274_ = 8;
        private static final int f_198275_ = 4;
        private static final int f_198276_ = 16;
        private static final int f_198277_ = 15;
        final SurfaceSystem f_189535_;
        final Condition f_189536_ = new TemperatureHelperCondition(this);
        final Condition f_189537_ = new SteepMaterialCondition(this);
        final Condition f_189538_ = new HoleCondition(this);
        final Condition f_189539_ = new AbovePreliminarySurfaceCondition();
        final RandomState f_224614_;
        final ChunkAccess f_189540_;
        private final NoiseChunk f_189541_;
        private final Function<BlockPos, Holder<Biome>> f_189542_;
        final WorldGenerationContext f_189544_;
        private long f_198278_ = Long.MAX_VALUE;
        private final int[] f_198279_ = new int[4];
        long f_189545_ = -9223372036854775807L;
        int f_189546_;
        int f_189547_;
        int f_189548_;
        private long f_189549_ = this.f_189545_ - 1L;
        private double f_202180_;
        private long f_189551_ = this.f_189545_ - 1L;
        private int f_189552_;
        long f_189553_ = -9223372036854775807L;
        final BlockPos.MutableBlockPos f_189554_ = new BlockPos.MutableBlockPos();
        Supplier<Holder<Biome>> f_189555_;
        int f_189557_;
        int f_189558_;
        int f_189559_;
        int f_189560_;

        protected Context(SurfaceSystem p_224616_, RandomState p_224617_, ChunkAccess p_224618_, NoiseChunk p_224619_, Function<BlockPos, Holder<Biome>> p_224620_, Registry<Biome> p_224621_, WorldGenerationContext p_224622_) {
            this.f_189535_ = p_224616_;
            this.f_224614_ = p_224617_;
            this.f_189540_ = p_224618_;
            this.f_189541_ = p_224619_;
            this.f_189542_ = p_224620_;
            this.f_189544_ = p_224622_;
        }

        protected void m_189569_(int p_189570_, int p_189571_) {
            ++this.f_189545_;
            ++this.f_189553_;
            this.f_189546_ = p_189570_;
            this.f_189547_ = p_189571_;
            this.f_189548_ = this.f_189535_.m_189927_(p_189570_, p_189571_);
        }

        protected void m_189576_(int p_189577_, int p_189578_, int p_189579_, int p_189580_, int p_189581_, int p_189582_) {
            ++this.f_189553_;
            this.f_189555_ = Suppliers.memoize(() -> this.f_189542_.apply(this.f_189554_.m_122178_(p_189580_, p_189581_, p_189582_)));
            this.f_189557_ = p_189581_;
            this.f_189558_ = p_189579_;
            this.f_189559_ = p_189578_;
            this.f_189560_ = p_189577_;
        }

        protected double m_202181_() {
            if (this.f_189549_ != this.f_189545_) {
                this.f_189549_ = this.f_189545_;
                this.f_202180_ = this.f_189535_.m_202189_(this.f_189546_, this.f_189547_);
            }
            return this.f_202180_;
        }

        private static int m_198280_(int p_198281_) {
            return p_198281_ >> 4;
        }

        private static int m_198282_(int p_198283_) {
            return p_198283_ << 4;
        }

        protected int m_189583_() {
            if (this.f_189551_ != this.f_189545_) {
                int $$1;
                this.f_189551_ = this.f_189545_;
                int $$0 = Context.m_198280_(this.f_189546_);
                long $$2 = ChunkPos.m_45589_($$0, $$1 = Context.m_198280_(this.f_189547_));
                if (this.f_198278_ != $$2) {
                    this.f_198278_ = $$2;
                    this.f_198279_[0] = this.f_189541_.m_198256_(Context.m_198282_($$0), Context.m_198282_($$1));
                    this.f_198279_[1] = this.f_189541_.m_198256_(Context.m_198282_($$0 + 1), Context.m_198282_($$1));
                    this.f_198279_[2] = this.f_189541_.m_198256_(Context.m_198282_($$0), Context.m_198282_($$1 + 1));
                    this.f_198279_[3] = this.f_189541_.m_198256_(Context.m_198282_($$0 + 1), Context.m_198282_($$1 + 1));
                }
                int $$3 = Mth.m_14107_(Mth.m_14012_((float)(this.f_189546_ & 0xF) / 16.0f, (float)(this.f_189547_ & 0xF) / 16.0f, this.f_198279_[0], this.f_198279_[1], this.f_198279_[2], this.f_198279_[3]));
                this.f_189552_ = $$3 + this.f_189548_ - 8;
            }
            return this.f_189552_;
        }

        static class TemperatureHelperCondition
        extends LazyYCondition {
            TemperatureHelperCondition(Context p_189597_) {
                super(p_189597_);
            }

            @Override
            protected boolean m_183479_() {
                return this.f_189616_.f_189555_.get().m_203334_().m_198904_(this.f_189616_.f_189554_.m_122178_(this.f_189616_.f_189546_, this.f_189616_.f_189557_, this.f_189616_.f_189547_));
            }
        }

        static class SteepMaterialCondition
        extends LazyXZCondition {
            SteepMaterialCondition(Context p_189594_) {
                super(p_189594_);
            }

            @Override
            protected boolean m_183479_() {
                int $$10;
                int $$0 = this.f_189616_.f_189546_ & 0xF;
                int $$1 = this.f_189616_.f_189547_ & 0xF;
                int $$2 = Math.max($$1 - 1, 0);
                int $$3 = Math.min($$1 + 1, 15);
                ChunkAccess $$4 = this.f_189616_.f_189540_;
                int $$5 = $$4.m_5885_(Heightmap.Types.WORLD_SURFACE_WG, $$0, $$2);
                int $$6 = $$4.m_5885_(Heightmap.Types.WORLD_SURFACE_WG, $$0, $$3);
                if ($$6 >= $$5 + 4) {
                    return true;
                }
                int $$7 = Math.max($$0 - 1, 0);
                int $$8 = Math.min($$0 + 1, 15);
                int $$9 = $$4.m_5885_(Heightmap.Types.WORLD_SURFACE_WG, $$7, $$1);
                return $$9 >= ($$10 = $$4.m_5885_(Heightmap.Types.WORLD_SURFACE_WG, $$8, $$1)) + 4;
            }
        }

        static final class HoleCondition
        extends LazyXZCondition {
            HoleCondition(Context p_189591_) {
                super(p_189591_);
            }

            @Override
            protected boolean m_183479_() {
                return this.f_189616_.f_189548_ <= 0;
            }
        }

        final class AbovePreliminarySurfaceCondition
        implements Condition {
            AbovePreliminarySurfaceCondition() {
            }

            @Override
            public boolean m_183475_() {
                return Context.this.f_189557_ >= Context.this.m_189583_();
            }
        }
    }
}

