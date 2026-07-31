/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen.biome;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class NetherBiomes {
    public static Biome m_194831_() {
        MobSpawnSettings $$0 = new MobSpawnSettings.Builder().m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20453_, 50, 4, 4)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20531_, 100, 4, 4)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20468_, 2, 4, 4)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20566_, 1, 4, 4)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20511_, 15, 4, 4)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20482_, 60, 1, 2)).m_48381_();
        BiomeGenerationSettings.Builder $$1 = new BiomeGenerationSettings.Builder().m_204198_(GenerationStep.Carving.AIR, Carvers.f_126853_).m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.f_195274_);
        BiomeDefaultFeatures.m_126730_($$1);
        $$1.m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195296_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195299_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195298_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195284_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195285_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.f_195404_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.f_195405_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.f_195315_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195294_);
        BiomeDefaultFeatures.m_126773_($$1);
        return new Biome.BiomeBuilder().m_47597_(Biome.Precipitation.NONE).m_47609_(2.0f).m_47611_(0.0f).m_47603_(new BiomeSpecialEffects.Builder().m_48034_(4159204).m_48037_(329011).m_48019_(0x330808).m_48040_(OverworldBiomes.m_194843_(2.0f)).m_48023_(SoundEvents.f_12113_).m_48027_(new AmbientMoodSettings(SoundEvents.f_12166_, 6000, 8, 2.0)).m_48025_(new AmbientAdditionsSettings(SoundEvents.f_12060_, 0.0111)).m_48021_(Musics.m_11653_(SoundEvents.f_12155_)).m_48018_()).m_47605_($$0).m_47601_($$1.m_47831_()).m_47592_();
    }

    public static Biome m_194832_() {
        double $$0 = 0.7;
        double $$1 = 0.15;
        MobSpawnSettings $$2 = new MobSpawnSettings.Builder().m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20524_, 20, 5, 5)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20453_, 50, 4, 4)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20566_, 1, 4, 4)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20482_, 60, 1, 2)).m_48370_(EntityType.f_20524_, 0.7, 0.15).m_48370_(EntityType.f_20453_, 0.7, 0.15).m_48370_(EntityType.f_20566_, 0.7, 0.15).m_48370_(EntityType.f_20482_, 0.7, 0.15).m_48381_();
        BiomeGenerationSettings.Builder $$3 = new BiomeGenerationSettings.Builder().m_204198_(GenerationStep.Carving.AIR, Carvers.f_126853_).m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.f_195274_).m_204201_(GenerationStep.Decoration.LOCAL_MODIFICATIONS, NetherPlacements.f_195292_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195296_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195299_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195298_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195284_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195285_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195291_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.f_195315_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195294_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.f_195316_);
        BiomeDefaultFeatures.m_126773_($$3);
        return new Biome.BiomeBuilder().m_47597_(Biome.Precipitation.NONE).m_47609_(2.0f).m_47611_(0.0f).m_47603_(new BiomeSpecialEffects.Builder().m_48034_(4159204).m_48037_(329011).m_48019_(1787717).m_48040_(OverworldBiomes.m_194843_(2.0f)).m_48029_(new AmbientParticleSettings(ParticleTypes.f_123783_, 0.00625f)).m_48023_(SoundEvents.f_12272_).m_48027_(new AmbientMoodSettings(SoundEvents.f_12325_, 6000, 8, 2.0)).m_48025_(new AmbientAdditionsSettings(SoundEvents.f_12219_, 0.0111)).m_48021_(Musics.m_11653_(SoundEvents.f_12156_)).m_48018_()).m_47605_($$2).m_47601_($$3.m_47831_()).m_47592_();
    }

    public static Biome m_194833_() {
        MobSpawnSettings $$0 = new MobSpawnSettings.Builder().m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20453_, 40, 1, 1)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20468_, 100, 2, 5)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20482_, 60, 1, 2)).m_48381_();
        BiomeGenerationSettings.Builder $$1 = new BiomeGenerationSettings.Builder().m_204198_(GenerationStep.Carving.AIR, Carvers.f_126853_).m_204201_(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.f_195279_).m_204201_(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.f_195280_).m_204201_(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.f_195281_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195282_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195283_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195293_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195299_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195298_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195284_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195285_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.f_195404_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.f_195405_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.f_195315_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195295_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.f_195317_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.f_195318_);
        BiomeDefaultFeatures.m_126775_($$1);
        return new Biome.BiomeBuilder().m_47597_(Biome.Precipitation.NONE).m_47609_(2.0f).m_47611_(0.0f).m_47603_(new BiomeSpecialEffects.Builder().m_48034_(4159204).m_48037_(329011).m_48019_(6840176).m_48040_(OverworldBiomes.m_194843_(2.0f)).m_48029_(new AmbientParticleSettings(ParticleTypes.f_123790_, 0.118093334f)).m_48023_(SoundEvents.f_11795_).m_48027_(new AmbientMoodSettings(SoundEvents.f_11848_, 6000, 8, 2.0)).m_48025_(new AmbientAdditionsSettings(SoundEvents.f_11742_, 0.0111)).m_48021_(Musics.m_11653_(SoundEvents.f_12154_)).m_48018_()).m_47605_($$0).m_47601_($$1.m_47831_()).m_47592_();
    }

    public static Biome m_194834_() {
        MobSpawnSettings $$0 = new MobSpawnSettings.Builder().m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20531_, 1, 2, 4)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20456_, 9, 3, 4)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20511_, 5, 3, 4)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20482_, 60, 1, 2)).m_48381_();
        BiomeGenerationSettings.Builder $$1 = new BiomeGenerationSettings.Builder().m_204198_(GenerationStep.Carving.AIR, Carvers.f_126853_).m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.f_195274_);
        BiomeDefaultFeatures.m_126730_($$1);
        $$1.m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195296_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195299_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195284_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195285_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.f_195315_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195294_).m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.f_195290_).m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, TreePlacements.f_195372_).m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.f_195286_);
        BiomeDefaultFeatures.m_126773_($$1);
        return new Biome.BiomeBuilder().m_47597_(Biome.Precipitation.NONE).m_47609_(2.0f).m_47611_(0.0f).m_47603_(new BiomeSpecialEffects.Builder().m_48034_(4159204).m_48037_(329011).m_48019_(0x330303).m_48040_(OverworldBiomes.m_194843_(2.0f)).m_48029_(new AmbientParticleSettings(ParticleTypes.f_123784_, 0.025f)).m_48023_(SoundEvents.f_11954_).m_48027_(new AmbientMoodSettings(SoundEvents.f_12007_, 6000, 8, 2.0)).m_48025_(new AmbientAdditionsSettings(SoundEvents.f_11901_, 0.0111)).m_48021_(Musics.m_11653_(SoundEvents.f_12157_)).m_48018_()).m_47605_($$0).m_47601_($$1.m_47831_()).m_47592_();
    }

    public static Biome m_194835_() {
        MobSpawnSettings $$0 = new MobSpawnSettings.Builder().m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20566_, 1, 4, 4)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20482_, 60, 1, 2)).m_48370_(EntityType.f_20566_, 1.0, 0.12).m_48381_();
        BiomeGenerationSettings.Builder $$1 = new BiomeGenerationSettings.Builder().m_204198_(GenerationStep.Carving.AIR, Carvers.f_126853_).m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.f_195274_);
        BiomeDefaultFeatures.m_126730_($$1);
        $$1.m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195296_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195299_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195298_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195284_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195285_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.f_195315_).m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.f_195294_).m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, TreePlacements.f_195373_).m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.f_195287_).m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.f_195288_).m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.f_195289_);
        BiomeDefaultFeatures.m_126773_($$1);
        return new Biome.BiomeBuilder().m_47597_(Biome.Precipitation.NONE).m_47609_(2.0f).m_47611_(0.0f).m_47603_(new BiomeSpecialEffects.Builder().m_48034_(4159204).m_48037_(329011).m_48019_(1705242).m_48040_(OverworldBiomes.m_194843_(2.0f)).m_48029_(new AmbientParticleSettings(ParticleTypes.f_123785_, 0.01428f)).m_48023_(SoundEvents.f_12431_).m_48027_(new AmbientMoodSettings(SoundEvents.f_12484_, 6000, 8, 2.0)).m_48025_(new AmbientAdditionsSettings(SoundEvents.f_12378_, 0.0111)).m_48021_(Musics.m_11653_(SoundEvents.f_12158_)).m_48018_()).m_47605_($$0).m_47601_($$1.m_47831_()).m_47592_();
    }
}

