/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.levelgen.flat;

import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.LayerConfiguration;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.slf4j.Logger;

public class FlatLevelGeneratorSettings {
    private static final Logger f_70348_ = LogUtils.getLogger();
    public static final Codec<FlatLevelGeneratorSettings> f_70347_ = RecordCodecBuilder.create(p_209800_ -> p_209800_.group((App)RegistryOps.m_206832_(Registry.f_122885_).forGetter(p_161916_ -> p_161916_.f_70350_), (App)RegistryCodecs.m_206277_(Registry.f_211073_).optionalFieldOf("structure_overrides").forGetter(p_209812_ -> p_209812_.f_209788_), (App)FlatLayerInfo.f_70329_.listOf().fieldOf("layers").forGetter(FlatLevelGeneratorSettings::m_70401_), (App)Codec.BOOL.fieldOf("lakes").orElse((Object)false).forGetter(p_161912_ -> p_161912_.f_70357_), (App)Codec.BOOL.fieldOf("features").orElse((Object)false).forGetter(p_209809_ -> p_209809_.f_70356_), (App)Biome.f_47431_.optionalFieldOf("biome").orElseGet(Optional::empty).forGetter(p_209807_ -> Optional.of(p_209807_.f_70353_))).apply((Applicative)p_209800_, FlatLevelGeneratorSettings::new)).comapFlatMap(FlatLevelGeneratorSettings::m_161905_, Function.identity()).stable();
    private final Registry<Biome> f_70350_;
    private final Optional<HolderSet<StructureSet>> f_209788_;
    private final List<FlatLayerInfo> f_70352_ = Lists.newArrayList();
    private Holder<Biome> f_70353_;
    private final List<BlockState> f_70354_;
    private boolean f_70355_;
    private boolean f_70356_;
    private boolean f_70357_;

    private static DataResult<FlatLevelGeneratorSettings> m_161905_(FlatLevelGeneratorSettings p_161906_) {
        int $$1 = p_161906_.f_70352_.stream().mapToInt(FlatLayerInfo::m_70337_).sum();
        if ($$1 > DimensionType.f_156651_) {
            return DataResult.error((String)("Sum of layer heights is > " + DimensionType.f_156651_), (Object)p_161906_);
        }
        return DataResult.success((Object)p_161906_);
    }

    private FlatLevelGeneratorSettings(Registry<Biome> p_209790_, Optional<HolderSet<StructureSet>> p_209791_, List<FlatLayerInfo> p_209792_, boolean p_209793_, boolean p_209794_, Optional<Holder<Biome>> p_209795_) {
        this(p_209791_, p_209790_);
        if (p_209793_) {
            this.m_70385_();
        }
        if (p_209794_) {
            this.m_70369_();
        }
        this.f_70352_.addAll(p_209792_);
        this.m_70403_();
        if (p_209795_.isEmpty()) {
            f_70348_.error("Unknown biome, defaulting to plains");
            this.f_70353_ = p_209790_.m_214121_(Biomes.f_48202_);
        } else {
            this.f_70353_ = p_209795_.get();
        }
    }

    public FlatLevelGeneratorSettings(Optional<HolderSet<StructureSet>> p_209797_, Registry<Biome> p_209798_) {
        this.f_70350_ = p_209798_;
        this.f_209788_ = p_209797_;
        this.f_70353_ = p_209798_.m_214121_(Biomes.f_48202_);
        this.f_70354_ = Lists.newArrayList();
    }

    public FlatLevelGeneratorSettings m_209803_(List<FlatLayerInfo> p_209804_, Optional<HolderSet<StructureSet>> p_209805_) {
        FlatLevelGeneratorSettings $$2 = new FlatLevelGeneratorSettings(p_209805_, this.f_70350_);
        for (FlatLayerInfo $$3 : p_209804_) {
            $$2.f_70352_.add(new FlatLayerInfo($$3.m_70337_(), $$3.m_70344_().m_60734_()));
            $$2.m_70403_();
        }
        $$2.m_204918_(this.f_70353_);
        if (this.f_70356_) {
            $$2.m_70369_();
        }
        if (this.f_70357_) {
            $$2.m_70385_();
        }
        return $$2;
    }

    public void m_70369_() {
        this.f_70356_ = true;
    }

    public void m_70385_() {
        this.f_70357_ = true;
    }

    public BiomeGenerationSettings m_226294_(Holder<Biome> p_226295_) {
        boolean $$3;
        if (!p_226295_.equals(this.f_70353_)) {
            return p_226295_.m_203334_().m_47536_();
        }
        BiomeGenerationSettings $$1 = this.m_204921_().m_203334_().m_47536_();
        BiomeGenerationSettings.Builder $$2 = new BiomeGenerationSettings.Builder();
        if (this.f_70357_) {
            $$2.m_204201_(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.f_195266_);
            $$2.m_204201_(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.f_195267_);
        }
        boolean bl = $$3 = (!this.f_70355_ || p_226295_.m_203565_(Biomes.f_48173_)) && this.f_70356_;
        if ($$3) {
            List<HolderSet<PlacedFeature>> $$4 = $$1.m_47818_();
            for (int $$5 = 0; $$5 < $$4.size(); ++$$5) {
                if ($$5 == GenerationStep.Decoration.UNDERGROUND_STRUCTURES.ordinal() || $$5 == GenerationStep.Decoration.SURFACE_STRUCTURES.ordinal()) continue;
                HolderSet<PlacedFeature> $$6 = $$4.get($$5);
                for (Holder holder : $$6) {
                    $$2.m_204193_($$5, holder);
                }
            }
        }
        List<BlockState> $$8 = this.m_161917_();
        for (int $$9 = 0; $$9 < $$8.size(); ++$$9) {
            BlockState $$10 = $$8.get($$9);
            if (Heightmap.Types.MOTION_BLOCKING.m_64299_().test($$10)) continue;
            $$8.set($$9, null);
            $$2.m_204201_(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, PlacementUtils.m_206502_(Feature.f_65750_, new LayerConfiguration($$9, $$10), new PlacementModifier[0]));
        }
        return $$2.m_47831_();
    }

    public Optional<HolderSet<StructureSet>> m_209810_() {
        return this.f_209788_;
    }

    public Holder<Biome> m_204921_() {
        return this.f_70353_;
    }

    public void m_204918_(Holder<Biome> p_204919_) {
        this.f_70353_ = p_204919_;
    }

    public List<FlatLayerInfo> m_70401_() {
        return this.f_70352_;
    }

    public List<BlockState> m_161917_() {
        return this.f_70354_;
    }

    public void m_70403_() {
        this.f_70354_.clear();
        for (FlatLayerInfo $$0 : this.f_70352_) {
            for (int $$1 = 0; $$1 < $$0.m_70337_(); ++$$1) {
                this.f_70354_.add($$0.m_70344_());
            }
        }
        this.f_70355_ = this.f_70354_.stream().allMatch(p_209802_ -> p_209802_.m_60713_(Blocks.f_50016_));
    }

    public static FlatLevelGeneratorSettings m_211734_(Registry<Biome> p_211735_, Registry<StructureSet> p_211736_) {
        HolderSet.Direct $$2 = HolderSet.m_205809_(p_211736_.m_206081_(BuiltinStructureSets.f_209836_), p_211736_.m_206081_(BuiltinStructureSets.f_209820_));
        FlatLevelGeneratorSettings $$3 = new FlatLevelGeneratorSettings(Optional.of($$2), p_211735_);
        $$3.f_70353_ = p_211735_.m_214121_(Biomes.f_48202_);
        $$3.m_70401_().add(new FlatLayerInfo(1, Blocks.f_50752_));
        $$3.m_70401_().add(new FlatLayerInfo(2, Blocks.f_50493_));
        $$3.m_70401_().add(new FlatLayerInfo(1, Blocks.f_50440_));
        $$3.m_70403_();
        return $$3;
    }
}

