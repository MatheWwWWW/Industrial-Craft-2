/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.worldselection;

import java.util.Map;
import java.util.Optional;
import net.minecraft.client.gui.screens.CreateBuffetWorldScreen;
import net.minecraft.client.gui.screens.CreateFlatWorldScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldCreationContext;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public interface PresetEditor {
    public static final Map<Optional<ResourceKey<WorldPreset>>, PresetEditor> f_232950_ = Map.of(Optional.of(WorldPresets.f_226438_), (p_232974_, p_232975_) -> {
        ChunkGenerator $$2 = p_232975_.f_232987_().m_64666_();
        RegistryAccess.Frozen $$3 = p_232975_.f_232989_();
        Registry<Biome> $$4 = $$3.m_175515_(Registry.f_122885_);
        Registry<StructureSet> $$5 = $$3.m_175515_(Registry.f_211073_);
        return new CreateFlatWorldScreen(p_232974_, p_232960_ -> p_232959_.f_100847_.m_233040_(PresetEditor.m_232952_(p_232960_)), $$2 instanceof FlatLevelSource ? ((FlatLevelSource)$$2).m_64191_() : FlatLevelGeneratorSettings.m_211734_($$4, $$5));
    }, Optional.of(WorldPresets.f_226441_), (p_232962_, p_232963_) -> new CreateBuffetWorldScreen(p_232962_, p_232963_, p_232966_ -> p_232965_.f_100847_.m_233040_(PresetEditor.m_232967_(p_232966_))));

    public Screen m_232976_(CreateWorldScreen var1, WorldCreationContext var2);

    private static WorldCreationContext.Updater m_232952_(FlatLevelGeneratorSettings p_232953_) {
        return (p_232956_, p_232957_) -> {
            Registry<StructureSet> $$3 = p_232956_.m_175515_(Registry.f_211073_);
            FlatLevelSource $$4 = new FlatLevelSource($$3, p_232953_);
            return WorldGenSettings.m_224673_(p_232956_, p_232957_, $$4);
        };
    }

    private static WorldCreationContext.Updater m_232967_(Holder<Biome> p_232968_) {
        return (p_232971_, p_232972_) -> {
            Registry<StructureSet> $$3 = p_232971_.m_175515_(Registry.f_211073_);
            Registry<NoiseGeneratorSettings> $$4 = p_232971_.m_175515_(Registry.f_122878_);
            Registry<NormalNoise.NoiseParameters> $$5 = p_232971_.m_175515_(Registry.f_194568_);
            Holder<NoiseGeneratorSettings> $$6 = $$4.m_214121_(NoiseGeneratorSettings.f_64432_);
            FixedBiomeSource $$7 = new FixedBiomeSource(p_232968_);
            NoiseBasedChunkGenerator $$8 = new NoiseBasedChunkGenerator($$3, $$5, (BiomeSource)$$7, $$6);
            return WorldGenSettings.m_224673_(p_232971_, p_232972_, $$8);
        };
    }
}

