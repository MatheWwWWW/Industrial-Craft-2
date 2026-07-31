/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Lifecycle
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.dimension;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public final class LevelStem {
    public static final Codec<LevelStem> f_63970_ = RecordCodecBuilder.create(p_63986_ -> p_63986_.group((App)DimensionType.f_63853_.fieldOf("type").forGetter(LevelStem::m_204521_), (App)ChunkGenerator.f_62136_.fieldOf("generator").forGetter(LevelStem::m_63990_)).apply((Applicative)p_63986_, p_63986_.stable(LevelStem::new)));
    public static final ResourceKey<LevelStem> f_63971_ = ResourceKey.m_135785_(Registry.f_122820_, new ResourceLocation("overworld"));
    public static final ResourceKey<LevelStem> f_63972_ = ResourceKey.m_135785_(Registry.f_122820_, new ResourceLocation("the_nether"));
    public static final ResourceKey<LevelStem> f_63973_ = ResourceKey.m_135785_(Registry.f_122820_, new ResourceLocation("the_end"));
    private static final Set<ResourceKey<LevelStem>> f_63974_ = ImmutableSet.of(f_63971_, f_63972_, f_63973_);
    private final Holder<DimensionType> f_63975_;
    private final ChunkGenerator f_63976_;

    public LevelStem(Holder<DimensionType> p_204519_, ChunkGenerator p_204520_) {
        this.f_63975_ = p_204519_;
        this.f_63976_ = p_204520_;
    }

    public Holder<DimensionType> m_204521_() {
        return this.f_63975_;
    }

    public ChunkGenerator m_63990_() {
        return this.f_63976_;
    }

    public static Stream<ResourceKey<LevelStem>> m_223605_(Stream<ResourceKey<LevelStem>> p_223606_) {
        return Stream.concat(f_63974_.stream(), p_223606_.filter(p_223600_ -> !f_63974_.contains(p_223600_)));
    }

    public static Registry<LevelStem> m_204525_(Registry<LevelStem> p_204526_) {
        MappedRegistry<LevelStem> $$1 = new MappedRegistry<LevelStem>(Registry.f_122820_, Lifecycle.experimental(), null);
        LevelStem.m_223605_(p_204526_.m_214010_().stream()).forEach(p_223604_ -> {
            LevelStem $$3 = (LevelStem)p_204526_.m_6246_((ResourceKey<LevelStem>)p_223604_);
            if ($$3 != null) {
                $$1.m_203505_((ResourceKey<LevelStem>)p_223604_, $$3, p_204526_.m_6228_($$3));
            }
        });
        return $$1;
    }

    public static boolean m_223607_(Registry<LevelStem> p_223608_) {
        if (p_223608_.m_13562_() != f_63974_.size()) {
            return false;
        }
        Optional<LevelStem> $$1 = p_223608_.m_123009_(f_63971_);
        Optional<LevelStem> $$2 = p_223608_.m_123009_(f_63972_);
        Optional<LevelStem> $$3 = p_223608_.m_123009_(f_63973_);
        if ($$1.isEmpty() || $$2.isEmpty() || $$3.isEmpty()) {
            return false;
        }
        if (!$$1.get().m_204521_().m_203565_(BuiltinDimensionTypes.f_223538_) && !$$1.get().m_204521_().m_203565_(BuiltinDimensionTypes.f_223541_)) {
            return false;
        }
        if (!$$2.get().m_204521_().m_203565_(BuiltinDimensionTypes.f_223539_)) {
            return false;
        }
        if (!$$3.get().m_204521_().m_203565_(BuiltinDimensionTypes.f_223540_)) {
            return false;
        }
        if (!($$2.get().m_63990_() instanceof NoiseBasedChunkGenerator) || !($$3.get().m_63990_() instanceof NoiseBasedChunkGenerator)) {
            return false;
        }
        NoiseBasedChunkGenerator $$4 = (NoiseBasedChunkGenerator)$$2.get().m_63990_();
        NoiseBasedChunkGenerator $$5 = (NoiseBasedChunkGenerator)$$3.get().m_63990_();
        if (!$$4.m_224221_(NoiseGeneratorSettings.f_64434_)) {
            return false;
        }
        if (!$$5.m_224221_(NoiseGeneratorSettings.f_64435_)) {
            return false;
        }
        if (!($$4.m_62218_() instanceof MultiNoiseBiomeSource)) {
            return false;
        }
        MultiNoiseBiomeSource $$6 = (MultiNoiseBiomeSource)$$4.m_62218_();
        if (!$$6.m_187063_(MultiNoiseBiomeSource.Preset.f_48512_)) {
            return false;
        }
        BiomeSource $$7 = $$1.get().m_63990_().m_62218_();
        if ($$7 instanceof MultiNoiseBiomeSource && !((MultiNoiseBiomeSource)$$7).m_187063_(MultiNoiseBiomeSource.Preset.f_187087_)) {
            return false;
        }
        return $$5.m_62218_() instanceof TheEndBiomeSource;
    }
}

