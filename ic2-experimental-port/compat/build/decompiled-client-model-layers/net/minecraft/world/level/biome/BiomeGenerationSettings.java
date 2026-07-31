/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Suppliers
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Keyable
 *  com.mojang.serialization.MapCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.biome;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.slf4j.Logger;

public class BiomeGenerationSettings {
    private static final Logger f_47776_ = LogUtils.getLogger();
    public static final BiomeGenerationSettings f_47777_ = new BiomeGenerationSettings((Map<GenerationStep.Carving, HolderSet<ConfiguredWorldCarver<?>>>)ImmutableMap.of(), (List<HolderSet<PlacedFeature>>)ImmutableList.of());
    public static final MapCodec<BiomeGenerationSettings> f_47778_ = RecordCodecBuilder.mapCodec(p_186655_ -> p_186655_.group((App)Codec.simpleMap(GenerationStep.Carving.f_64194_, (Codec)ConfiguredWorldCarver.f_64848_.promotePartial(Util.m_137489_("Carver: ", arg_0 -> ((Logger)f_47776_).error(arg_0))), (Keyable)StringRepresentable.m_14357_(GenerationStep.Carving.values())).fieldOf("carvers").forGetter(p_186661_ -> p_186661_.f_47780_), (App)PlacedFeature.f_204922_.promotePartial(Util.m_137489_("Features: ", arg_0 -> ((Logger)f_47776_).error(arg_0))).fieldOf("features").forGetter(p_186653_ -> p_186653_.f_47781_)).apply((Applicative)p_186655_, BiomeGenerationSettings::new));
    private final Map<GenerationStep.Carving, HolderSet<ConfiguredWorldCarver<?>>> f_47780_;
    private final List<HolderSet<PlacedFeature>> f_47781_;
    private final Supplier<List<ConfiguredFeature<?, ?>>> f_47783_;
    private final Supplier<Set<PlacedFeature>> f_186648_;

    BiomeGenerationSettings(Map<GenerationStep.Carving, HolderSet<ConfiguredWorldCarver<?>>> p_186650_, List<HolderSet<PlacedFeature>> p_186651_) {
        this.f_47780_ = p_186650_;
        this.f_47781_ = p_186651_;
        this.f_47783_ = Suppliers.memoize(() -> (List)p_186651_.stream().flatMap(HolderSet::m_203614_).map(Holder::m_203334_).flatMap(PlacedFeature::m_191781_).filter(p_186657_ -> p_186657_.f_65377_() == Feature.f_65761_).collect(ImmutableList.toImmutableList()));
        this.f_186648_ = Suppliers.memoize(() -> p_186651_.stream().flatMap(HolderSet::m_203614_).map(Holder::m_203334_).collect(Collectors.toSet()));
    }

    public Iterable<Holder<ConfiguredWorldCarver<?>>> m_204187_(GenerationStep.Carving p_204188_) {
        return Objects.requireNonNullElseGet((Iterable)this.f_47780_.get(p_204188_), List::of);
    }

    public List<ConfiguredFeature<?, ?>> m_47815_() {
        return this.f_47783_.get();
    }

    public List<HolderSet<PlacedFeature>> m_47818_() {
        return this.f_47781_;
    }

    public boolean m_186658_(PlacedFeature p_186659_) {
        return this.f_186648_.get().contains(p_186659_);
    }

    public static class Builder {
        private final Map<GenerationStep.Carving, List<Holder<ConfiguredWorldCarver<?>>>> f_47827_ = Maps.newLinkedHashMap();
        private final List<List<Holder<PlacedFeature>>> f_47828_ = Lists.newArrayList();

        public Builder m_204201_(GenerationStep.Decoration p_204202_, Holder<PlacedFeature> p_204203_) {
            return this.m_204193_(p_204202_.ordinal(), p_204203_);
        }

        public Builder m_204193_(int p_204194_, Holder<PlacedFeature> p_204195_) {
            this.m_47832_(p_204194_);
            this.f_47828_.get(p_204194_).add(p_204195_);
            return this;
        }

        public Builder m_204198_(GenerationStep.Carving p_204199_, Holder<? extends ConfiguredWorldCarver<?>> p_204200_) {
            this.f_47827_.computeIfAbsent(p_204199_, p_204197_ -> Lists.newArrayList()).add(Holder.m_205706_(p_204200_));
            return this;
        }

        private void m_47832_(int p_47833_) {
            while (this.f_47828_.size() <= p_47833_) {
                this.f_47828_.add(Lists.newArrayList());
            }
        }

        public BiomeGenerationSettings m_47831_() {
            return new BiomeGenerationSettings((Map)this.f_47827_.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, p_204205_ -> HolderSet.m_205800_((List)p_204205_.getValue()))), (List)this.f_47828_.stream().map(HolderSet::m_205800_).collect(ImmutableList.toImmutableList()));
        }
    }
}

