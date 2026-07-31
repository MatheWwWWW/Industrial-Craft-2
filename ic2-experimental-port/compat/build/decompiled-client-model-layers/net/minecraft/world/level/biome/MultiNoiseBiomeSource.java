/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.levelgen.NoiseRouterData;

public class MultiNoiseBiomeSource
extends BiomeSource {
    public static final MapCodec<MultiNoiseBiomeSource> f_48424_ = RecordCodecBuilder.mapCodec(p_187070_ -> p_187070_.group((App)ExtraCodecs.m_144637_(RecordCodecBuilder.create(p_187078_ -> p_187078_.group((App)Climate.ParameterPoint.f_186862_.fieldOf("parameters").forGetter(Pair::getFirst), (App)Biome.f_47431_.fieldOf("biome").forGetter(Pair::getSecond)).apply((Applicative)p_187078_, Pair::of)).listOf()).xmap(Climate.ParameterList::new, Climate.ParameterList::m_186850_).fieldOf("biomes").forGetter(p_187080_ -> p_187080_.f_48435_)).apply((Applicative)p_187070_, MultiNoiseBiomeSource::new));
    public static final Codec<MultiNoiseBiomeSource> f_48425_ = Codec.mapEither(PresetInstance.f_48540_, f_48424_).xmap(p_187068_ -> (MultiNoiseBiomeSource)p_187068_.map(PresetInstance::m_48565_, Function.identity()), p_187066_ -> p_187066_.m_48490_().map(Either::left).orElseGet(() -> Either.right((Object)p_187066_))).codec();
    private final Climate.ParameterList<Holder<Biome>> f_48435_;
    private final Optional<PresetInstance> f_48438_;

    private MultiNoiseBiomeSource(Climate.ParameterList<Holder<Biome>> p_187057_) {
        this(p_187057_, Optional.empty());
    }

    MultiNoiseBiomeSource(Climate.ParameterList<Holder<Biome>> p_187059_, Optional<PresetInstance> p_187060_) {
        super(p_187059_.m_186850_().stream().map(Pair::getSecond));
        this.f_48438_ = p_187060_;
        this.f_48435_ = p_187059_;
    }

    @Override
    protected Codec<? extends BiomeSource> m_5820_() {
        return f_48425_;
    }

    private Optional<PresetInstance> m_48490_() {
        return this.f_48438_;
    }

    public boolean m_187063_(Preset p_187064_) {
        return this.f_48438_.isPresent() && Objects.equals(this.f_48438_.get().f_48541_(), p_187064_);
    }

    @Override
    public Holder<Biome> m_203407_(int p_204272_, int p_204273_, int p_204274_, Climate.Sampler p_204275_) {
        return this.m_204269_(p_204275_.m_183445_(p_204272_, p_204273_, p_204274_));
    }

    @VisibleForDebug
    public Holder<Biome> m_204269_(Climate.TargetPoint p_204270_) {
        return this.f_48435_.m_204252_(p_204270_);
    }

    @Override
    public void m_207301_(List<String> p_207895_, BlockPos p_207896_, Climate.Sampler p_207897_) {
        int $$3 = QuartPos.m_175400_(p_207896_.m_123341_());
        int $$4 = QuartPos.m_175400_(p_207896_.m_123342_());
        int $$5 = QuartPos.m_175400_(p_207896_.m_123343_());
        Climate.TargetPoint $$6 = p_207897_.m_183445_($$3, $$4, $$5);
        float $$7 = Climate.m_186796_($$6.f_187005_());
        float $$8 = Climate.m_186796_($$6.f_187006_());
        float $$9 = Climate.m_186796_($$6.f_187003_());
        float $$10 = Climate.m_186796_($$6.f_187004_());
        float $$11 = Climate.m_186796_($$6.f_187008_());
        double $$12 = NoiseRouterData.m_224435_($$11);
        OverworldBiomeBuilder $$13 = new OverworldBiomeBuilder();
        p_207895_.add("Biome builder PV: " + OverworldBiomeBuilder.m_187155_($$12) + " C: " + $$13.m_187189_($$7) + " E: " + $$13.m_187209_($$8) + " T: " + $$13.m_187220_($$9) + " H: " + $$13.m_187231_($$10));
    }

    record PresetInstance(Preset f_48541_, Registry<Biome> f_48542_) {
        public static final MapCodec<PresetInstance> f_48540_ = RecordCodecBuilder.mapCodec(p_48558_ -> p_48558_.group((App)ResourceLocation.f_135803_.flatXmap(p_151869_ -> Optional.ofNullable(Preset.f_48513_.get(p_151869_)).map(DataResult::success).orElseGet(() -> DataResult.error((String)("Unknown preset: " + p_151869_))), p_151867_ -> DataResult.success((Object)p_151867_.f_48514_)).fieldOf("preset").stable().forGetter(PresetInstance::f_48541_), (App)RegistryOps.m_206832_(Registry.f_122885_).forGetter(PresetInstance::f_48542_)).apply((Applicative)p_48558_, p_48558_.stable(PresetInstance::new)));

        public MultiNoiseBiomeSource m_48565_() {
            return this.f_48541_.m_187092_(this, true);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{PresetInstance.class, "preset;biomes", "f_48541_", "f_48542_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{PresetInstance.class, "preset;biomes", "f_48541_", "f_48542_"}, this);
        }

        @Override
        public final boolean equals(Object p_187118_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{PresetInstance.class, "preset;biomes", "f_48541_", "f_48542_"}, this, p_187118_);
        }
    }

    public static class Preset {
        static final Map<ResourceLocation, Preset> f_48513_ = Maps.newHashMap();
        public static final Preset f_48512_ = new Preset(new ResourceLocation("nether"), p_204283_ -> new Climate.ParameterList(ImmutableList.of((Object)Pair.of((Object)Climate.m_186788_(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f), p_204283_.m_214121_(Biomes.f_48209_)), (Object)Pair.of((Object)Climate.m_186788_(0.0f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f), p_204283_.m_214121_(Biomes.f_48199_)), (Object)Pair.of((Object)Climate.m_186788_(0.4f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f), p_204283_.m_214121_(Biomes.f_48200_)), (Object)Pair.of((Object)Climate.m_186788_(0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.375f), p_204283_.m_214121_(Biomes.f_48201_)), (Object)Pair.of((Object)Climate.m_186788_(-0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.175f), p_204283_.m_214121_(Biomes.f_48175_)))));
        public static final Preset f_187087_ = new Preset(new ResourceLocation("overworld"), p_204281_ -> {
            ImmutableList.Builder $$1 = ImmutableList.builder();
            new OverworldBiomeBuilder().m_187175_(p_204279_ -> $$1.add((Object)p_204279_.mapSecond(p_204281_::m_214121_)));
            return new Climate.ParameterList($$1.build());
        });
        final ResourceLocation f_48514_;
        private final Function<Registry<Biome>, Climate.ParameterList<Holder<Biome>>> f_187088_;

        public Preset(ResourceLocation p_187090_, Function<Registry<Biome>, Climate.ParameterList<Holder<Biome>>> p_187091_) {
            this.f_48514_ = p_187090_;
            this.f_187088_ = p_187091_;
            f_48513_.put(p_187090_, this);
        }

        @VisibleForDebug
        public static Stream<Pair<ResourceLocation, Preset>> m_220657_() {
            return f_48513_.entrySet().stream().map(p_220661_ -> Pair.of((Object)((ResourceLocation)p_220661_.getKey()), (Object)((Preset)p_220661_.getValue())));
        }

        MultiNoiseBiomeSource m_187092_(PresetInstance p_187093_, boolean p_187094_) {
            Climate.ParameterList<Holder<Biome>> $$2 = this.f_187088_.apply(p_187093_.f_48542_());
            return new MultiNoiseBiomeSource($$2, p_187094_ ? Optional.of(p_187093_) : Optional.empty());
        }

        public MultiNoiseBiomeSource m_187104_(Registry<Biome> p_187105_, boolean p_187106_) {
            return this.m_187092_(new PresetInstance(this, p_187105_), p_187106_);
        }

        public MultiNoiseBiomeSource m_187099_(Registry<Biome> p_187100_) {
            return this.m_187104_(p_187100_, true);
        }

        public Stream<ResourceKey<Biome>> m_220662_() {
            return this.m_187099_(BuiltinRegistries.f_123865_).m_207840_().stream().flatMap(p_220659_ -> p_220659_.m_203543_().stream());
        }
    }
}

