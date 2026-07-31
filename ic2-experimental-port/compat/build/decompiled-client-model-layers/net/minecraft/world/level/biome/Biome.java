/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public final class Biome {
    public static final Codec<Biome> f_47429_ = RecordCodecBuilder.create(p_220544_ -> p_220544_.group((App)ClimateSettings.f_47679_.forGetter(p_151717_ -> p_151717_.f_47437_), (App)BiomeSpecialEffects.f_47926_.fieldOf("effects").forGetter(p_220550_ -> p_220550_.f_47443_), (App)BiomeGenerationSettings.f_47778_.forGetter(p_220548_ -> p_220548_.f_47438_), (App)MobSpawnSettings.f_48327_.forGetter(p_220546_ -> p_220546_.f_47439_)).apply((Applicative)p_220544_, Biome::new));
    public static final Codec<Biome> f_47430_ = RecordCodecBuilder.create(p_220540_ -> p_220540_.group((App)ClimateSettings.f_47679_.forGetter(p_220542_ -> p_220542_.f_47437_), (App)BiomeSpecialEffects.f_47926_.fieldOf("effects").forGetter(p_220538_ -> p_220538_.f_47443_)).apply((Applicative)p_220540_, (p_220535_, p_220536_) -> new Biome((ClimateSettings)p_220535_, (BiomeSpecialEffects)p_220536_, BiomeGenerationSettings.f_47777_, MobSpawnSettings.f_48326_)));
    public static final Codec<Holder<Biome>> f_47431_ = RegistryFileCodec.m_135589_(Registry.f_122885_, f_47429_);
    public static final Codec<HolderSet<Biome>> f_47432_ = RegistryCodecs.m_206279_(Registry.f_122885_, f_47429_);
    private static final PerlinSimplexNoise f_47435_ = new PerlinSimplexNoise((RandomSource)new WorldgenRandom(new LegacyRandomSource(1234L)), (List<Integer>)ImmutableList.of((Object)0));
    static final PerlinSimplexNoise f_47436_ = new PerlinSimplexNoise((RandomSource)new WorldgenRandom(new LegacyRandomSource(3456L)), (List<Integer>)ImmutableList.of((Object)-2, (Object)-1, (Object)0));
    @Deprecated(forRemoval=true)
    public static final PerlinSimplexNoise f_47433_ = new PerlinSimplexNoise((RandomSource)new WorldgenRandom(new LegacyRandomSource(2345L)), (List<Integer>)ImmutableList.of((Object)0));
    private static final int f_151655_ = 1024;
    private final ClimateSettings f_47437_;
    private final BiomeGenerationSettings f_47438_;
    private final MobSpawnSettings f_47439_;
    private final BiomeSpecialEffects f_47443_;
    private final ThreadLocal<Long2FloatLinkedOpenHashMap> f_47444_ = ThreadLocal.withInitial(() -> Util.m_137537_(() -> {
        Long2FloatLinkedOpenHashMap $$0 = new Long2FloatLinkedOpenHashMap(1024, 0.25f){

            protected void rehash(int p_47580_) {
            }
        };
        $$0.defaultReturnValue(Float.NaN);
        return $$0;
    }));

    Biome(ClimateSettings p_220530_, BiomeSpecialEffects p_220531_, BiomeGenerationSettings p_220532_, MobSpawnSettings p_220533_) {
        this.f_47437_ = p_220530_;
        this.f_47438_ = p_220532_;
        this.f_47439_ = p_220533_;
        this.f_47443_ = p_220531_;
    }

    public int m_47463_() {
        return this.f_47443_.m_47978_();
    }

    public MobSpawnSettings m_47518_() {
        return this.f_47439_;
    }

    public Precipitation m_47530_() {
        return this.f_47437_.f_47680_;
    }

    public boolean m_47533_() {
        return this.m_47548_() > 0.85f;
    }

    private float m_47528_(BlockPos p_47529_) {
        float $$1 = this.f_47437_.f_47682_.m_8117_(p_47529_, this.m_47554_());
        if (p_47529_.m_123342_() > 80) {
            float $$2 = (float)(f_47435_.m_75449_((float)p_47529_.m_123341_() / 8.0f, (float)p_47529_.m_123343_() / 8.0f, false) * 8.0);
            return $$1 - ($$2 + (float)p_47529_.m_123342_() - 80.0f) * 0.05f / 40.0f;
        }
        return $$1;
    }

    @Deprecated
    private float m_47505_(BlockPos p_47506_) {
        long $$1 = p_47506_.m_121878_();
        Long2FloatLinkedOpenHashMap $$2 = this.f_47444_.get();
        float $$3 = $$2.get($$1);
        if (!Float.isNaN($$3)) {
            return $$3;
        }
        float $$4 = this.m_47528_(p_47506_);
        if ($$2.size() == 1024) {
            $$2.removeFirstFloat();
        }
        $$2.put($$1, $$4);
        return $$4;
    }

    public boolean m_47477_(LevelReader p_47478_, BlockPos p_47479_) {
        return this.m_47480_(p_47478_, p_47479_, true);
    }

    public boolean m_47480_(LevelReader p_47481_, BlockPos p_47482_, boolean p_47483_) {
        if (this.m_198906_(p_47482_)) {
            return false;
        }
        if (p_47482_.m_123342_() >= p_47481_.m_141937_() && p_47482_.m_123342_() < p_47481_.m_151558_() && p_47481_.m_45517_(LightLayer.BLOCK, p_47482_) < 10) {
            BlockState $$3 = p_47481_.m_8055_(p_47482_);
            FluidState $$4 = p_47481_.m_6425_(p_47482_);
            if ($$4.m_76152_() == Fluids.f_76193_ && $$3.m_60734_() instanceof LiquidBlock) {
                boolean $$5;
                if (!p_47483_) {
                    return true;
                }
                boolean bl = $$5 = p_47481_.m_46801_(p_47482_.m_122024_()) && p_47481_.m_46801_(p_47482_.m_122029_()) && p_47481_.m_46801_(p_47482_.m_122012_()) && p_47481_.m_46801_(p_47482_.m_122019_());
                if (!$$5) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean m_198904_(BlockPos p_198905_) {
        return !this.m_198906_(p_198905_);
    }

    public boolean m_198906_(BlockPos p_198907_) {
        return this.m_47505_(p_198907_) >= 0.15f;
    }

    public boolean m_198908_(BlockPos p_198909_) {
        return this.m_47505_(p_198909_) > 0.1f;
    }

    public boolean m_198910_(BlockPos p_198911_) {
        return this.m_47505_(p_198911_) > 1.0f;
    }

    public boolean m_47519_(LevelReader p_47520_, BlockPos p_47521_) {
        BlockState $$2;
        if (this.m_198906_(p_47521_)) {
            return false;
        }
        return p_47521_.m_123342_() >= p_47520_.m_141937_() && p_47521_.m_123342_() < p_47520_.m_151558_() && p_47520_.m_45517_(LightLayer.BLOCK, p_47521_) < 10 && ($$2 = p_47520_.m_8055_(p_47521_)).m_60795_() && Blocks.f_50125_.m_49966_().m_60710_(p_47520_, p_47521_);
    }

    public BiomeGenerationSettings m_47536_() {
        return this.f_47438_;
    }

    public int m_47539_() {
        return this.f_47443_.m_47967_();
    }

    public int m_47464_(double p_47465_, double p_47466_) {
        int $$2 = this.f_47443_.m_47984_().orElseGet(this::m_47570_);
        return this.f_47443_.m_47987_().m_6583_(p_47465_, p_47466_, $$2);
    }

    private int m_47570_() {
        double $$0 = Mth.m_14036_(this.f_47437_.f_47681_, 0.0f, 1.0f);
        double $$1 = Mth.m_14036_(this.f_47437_.f_47683_, 0.0f, 1.0f);
        return GrassColor.m_46415_($$0, $$1);
    }

    public int m_47542_() {
        return this.f_47443_.m_47981_().orElseGet(this::m_47571_);
    }

    private int m_47571_() {
        double $$0 = Mth.m_14036_(this.f_47437_.f_47681_, 0.0f, 1.0f);
        double $$1 = Mth.m_14036_(this.f_47437_.f_47683_, 0.0f, 1.0f);
        return FoliageColor.m_46107_($$0, $$1);
    }

    public float m_47548_() {
        return this.f_47437_.f_47683_;
    }

    public float m_47554_() {
        return this.f_47437_.f_47681_;
    }

    public BiomeSpecialEffects m_47557_() {
        return this.f_47443_;
    }

    public int m_47560_() {
        return this.f_47443_.m_47972_();
    }

    public int m_47561_() {
        return this.f_47443_.m_47975_();
    }

    public Optional<AmbientParticleSettings> m_47562_() {
        return this.f_47443_.m_47990_();
    }

    public Optional<SoundEvent> m_47563_() {
        return this.f_47443_.m_47993_();
    }

    public Optional<AmbientMoodSettings> m_47564_() {
        return this.f_47443_.m_47996_();
    }

    public Optional<AmbientAdditionsSettings> m_47565_() {
        return this.f_47443_.m_47999_();
    }

    public Optional<Music> m_47566_() {
        return this.f_47443_.m_48002_();
    }

    record ClimateSettings(Precipitation f_47680_, float f_47681_, TemperatureModifier f_47682_, float f_47683_) {
        public static final MapCodec<ClimateSettings> f_47679_ = RecordCodecBuilder.mapCodec(p_47699_ -> p_47699_.group((App)Precipitation.f_47717_.fieldOf("precipitation").forGetter(p_151739_ -> p_151739_.f_47680_), (App)Codec.FLOAT.fieldOf("temperature").forGetter(p_151737_ -> Float.valueOf(p_151737_.f_47681_)), (App)TemperatureModifier.f_47737_.optionalFieldOf("temperature_modifier", (Object)TemperatureModifier.NONE).forGetter(p_151735_ -> p_151735_.f_47682_), (App)Codec.FLOAT.fieldOf("downfall").forGetter(p_151733_ -> Float.valueOf(p_151733_.f_47683_))).apply((Applicative)p_47699_, ClimateSettings::new));

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ClimateSettings.class, "precipitation;temperature;temperatureModifier;downfall", "f_47680_", "f_47681_", "f_47682_", "f_47683_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ClimateSettings.class, "precipitation;temperature;temperatureModifier;downfall", "f_47680_", "f_47681_", "f_47682_", "f_47683_"}, this);
        }

        @Override
        public final boolean equals(Object p_220558_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ClimateSettings.class, "precipitation;temperature;temperatureModifier;downfall", "f_47680_", "f_47681_", "f_47682_", "f_47683_"}, this, p_220558_);
        }
    }

    public static final class Precipitation
    extends Enum<Precipitation>
    implements StringRepresentable {
        public static final /* enum */ Precipitation NONE = new Precipitation("none");
        public static final /* enum */ Precipitation RAIN = new Precipitation("rain");
        public static final /* enum */ Precipitation SNOW = new Precipitation("snow");
        public static final Codec<Precipitation> f_47717_;
        private final String f_47719_;
        private static final /* synthetic */ Precipitation[] $VALUES;

        public static Precipitation[] values() {
            return (Precipitation[])$VALUES.clone();
        }

        public static Precipitation valueOf(String p_47733_) {
            return Enum.valueOf(Precipitation.class, p_47733_);
        }

        private Precipitation(String p_47725_) {
            this.f_47719_ = p_47725_;
        }

        public String m_47731_() {
            return this.f_47719_;
        }

        @Override
        public String m_7912_() {
            return this.f_47719_;
        }

        private static /* synthetic */ Precipitation[] m_151740_() {
            return new Precipitation[]{NONE, RAIN, SNOW};
        }

        static {
            $VALUES = Precipitation.m_151740_();
            f_47717_ = StringRepresentable.m_216439_(Precipitation::values);
        }
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static abstract class TemperatureModifier
    extends Enum<TemperatureModifier>
    implements StringRepresentable {
        public static final /* enum */ TemperatureModifier NONE = new TemperatureModifier("none"){

            @Override
            public float m_8117_(BlockPos p_47767_, float p_47768_) {
                return p_47768_;
            }
        };
        public static final /* enum */ TemperatureModifier FROZEN = new TemperatureModifier("frozen"){

            @Override
            public float m_8117_(BlockPos p_47774_, float p_47775_) {
                double $$5;
                double $$3;
                double $$2 = f_47436_.m_75449_((double)p_47774_.m_123341_() * 0.05, (double)p_47774_.m_123343_() * 0.05, false) * 7.0;
                double $$4 = $$2 + ($$3 = f_47433_.m_75449_((double)p_47774_.m_123341_() * 0.2, (double)p_47774_.m_123343_() * 0.2, false));
                if ($$4 < 0.3 && ($$5 = f_47433_.m_75449_((double)p_47774_.m_123341_() * 0.09, (double)p_47774_.m_123343_() * 0.09, false)) < 0.8) {
                    return 0.2f;
                }
                return p_47775_;
            }
        };
        private final String f_47738_;
        public static final Codec<TemperatureModifier> f_47737_;
        private static final /* synthetic */ TemperatureModifier[] $VALUES;

        public static TemperatureModifier[] values() {
            return (TemperatureModifier[])$VALUES.clone();
        }

        public static TemperatureModifier valueOf(String p_47760_) {
            return Enum.valueOf(TemperatureModifier.class, p_47760_);
        }

        public abstract float m_8117_(BlockPos var1, float var2);

        TemperatureModifier(String p_47745_) {
            this.f_47738_ = p_47745_;
        }

        public String m_47758_() {
            return this.f_47738_;
        }

        @Override
        public String m_7912_() {
            return this.f_47738_;
        }

        private static /* synthetic */ TemperatureModifier[] m_151741_() {
            return new TemperatureModifier[]{NONE, FROZEN};
        }

        static {
            $VALUES = TemperatureModifier.m_151741_();
            f_47737_ = StringRepresentable.m_216439_(TemperatureModifier::values);
        }
    }

    public static class BiomeBuilder {
        @Nullable
        private Precipitation f_47581_;
        @Nullable
        private Float f_47585_;
        private TemperatureModifier f_47586_ = TemperatureModifier.NONE;
        @Nullable
        private Float f_47587_;
        @Nullable
        private BiomeSpecialEffects f_47588_;
        @Nullable
        private MobSpawnSettings f_47589_;
        @Nullable
        private BiomeGenerationSettings f_47590_;

        public BiomeBuilder m_47597_(Precipitation p_47598_) {
            this.f_47581_ = p_47598_;
            return this;
        }

        public BiomeBuilder m_47609_(float p_47610_) {
            this.f_47585_ = Float.valueOf(p_47610_);
            return this;
        }

        public BiomeBuilder m_47611_(float p_47612_) {
            this.f_47587_ = Float.valueOf(p_47612_);
            return this;
        }

        public BiomeBuilder m_47603_(BiomeSpecialEffects p_47604_) {
            this.f_47588_ = p_47604_;
            return this;
        }

        public BiomeBuilder m_47605_(MobSpawnSettings p_47606_) {
            this.f_47589_ = p_47606_;
            return this;
        }

        public BiomeBuilder m_47601_(BiomeGenerationSettings p_47602_) {
            this.f_47590_ = p_47602_;
            return this;
        }

        public BiomeBuilder m_47599_(TemperatureModifier p_47600_) {
            this.f_47586_ = p_47600_;
            return this;
        }

        public Biome m_47592_() {
            if (this.f_47581_ == null || this.f_47585_ == null || this.f_47587_ == null || this.f_47588_ == null || this.f_47589_ == null || this.f_47590_ == null) {
                throw new IllegalStateException("You are missing parameters to build a proper biome\n" + this);
            }
            return new Biome(new ClimateSettings(this.f_47581_, this.f_47585_.floatValue(), this.f_47586_, this.f_47587_.floatValue()), this.f_47588_, this.f_47590_, this.f_47589_);
        }

        public String toString() {
            return "BiomeBuilder{\nprecipitation=" + this.f_47581_ + ",\ntemperature=" + this.f_47585_ + ",\ntemperatureModifier=" + this.f_47586_ + ",\ndownfall=" + this.f_47587_ + ",\nspecialEffects=" + this.f_47588_ + ",\nmobSpawnSettings=" + this.f_47589_ + ",\ngenerationSettings=" + this.f_47590_ + ",\n}";
        }
    }
}

