/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.biome;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.EndPlacements;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class EndBiomes {
    private static Biome m_194824_(BiomeGenerationSettings.Builder p_194825_) {
        MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126812_($$1);
        return new Biome.BiomeBuilder().m_47597_(Biome.Precipitation.NONE).m_47609_(0.5f).m_47611_(0.5f).m_47603_(new BiomeSpecialEffects.Builder().m_48034_(4159204).m_48037_(329011).m_48019_(0xA080A0).m_48040_(0).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_($$1.m_48381_()).m_47601_(p_194825_.m_47831_()).m_47592_();
    }

    public static Biome m_194823_() {
        BiomeGenerationSettings.Builder $$0 = new BiomeGenerationSettings.Builder();
        return EndBiomes.m_194824_($$0);
    }

    public static Biome m_194826_() {
        BiomeGenerationSettings.Builder $$0 = new BiomeGenerationSettings.Builder().m_204201_(GenerationStep.Decoration.SURFACE_STRUCTURES, EndPlacements.f_195254_);
        return EndBiomes.m_194824_($$0);
    }

    public static Biome m_194827_() {
        BiomeGenerationSettings.Builder $$0 = new BiomeGenerationSettings.Builder();
        return EndBiomes.m_194824_($$0);
    }

    public static Biome m_194828_() {
        BiomeGenerationSettings.Builder $$0 = new BiomeGenerationSettings.Builder().m_204201_(GenerationStep.Decoration.SURFACE_STRUCTURES, EndPlacements.f_195255_).m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, EndPlacements.f_195256_);
        return EndBiomes.m_194824_($$0);
    }

    public static Biome m_194829_() {
        BiomeGenerationSettings.Builder $$0 = new BiomeGenerationSettings.Builder().m_204201_(GenerationStep.Decoration.RAW_GENERATION, EndPlacements.f_195257_);
        return EndBiomes.m_194824_($$0);
    }
}

