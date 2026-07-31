/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.data.worldgen.biome;

import javax.annotation.Nullable;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class OverworldBiomes {
    protected static final int f_194836_ = 4159204;
    protected static final int f_194837_ = 329011;
    private static final int f_194838_ = 12638463;
    @Nullable
    private static final Music f_194839_ = null;

    protected static int m_194843_(float p_194844_) {
        float $$1 = p_194844_;
        $$1 /= 3.0f;
        $$1 = Mth.m_14036_($$1, -1.0f, 1.0f);
        return Mth.m_14169_(0.62222224f - $$1 * 0.05f, 0.5f + $$1 * 0.1f, 1.0f);
    }

    private static Biome m_236663_(Biome.Precipitation p_236664_, float p_236665_, float p_236666_, MobSpawnSettings.Builder p_236667_, BiomeGenerationSettings.Builder p_236668_, @Nullable Music p_236669_) {
        return OverworldBiomes.m_236654_(p_236664_, p_236665_, p_236666_, 4159204, 329011, p_236667_, p_236668_, p_236669_);
    }

    private static Biome m_236654_(Biome.Precipitation p_236655_, float p_236656_, float p_236657_, int p_236658_, int p_236659_, MobSpawnSettings.Builder p_236660_, BiomeGenerationSettings.Builder p_236661_, @Nullable Music p_236662_) {
        return new Biome.BiomeBuilder().m_47597_(p_236655_).m_47609_(p_236656_).m_47611_(p_236657_).m_47603_(new BiomeSpecialEffects.Builder().m_48034_(p_236658_).m_48037_(p_236659_).m_48019_(12638463).m_48040_(OverworldBiomes.m_194843_(p_236656_)).m_48027_(AmbientMoodSettings.f_47387_).m_48021_(p_236662_).m_48018_()).m_47605_(p_236660_.m_48381_()).m_47601_(p_236661_.m_47831_()).m_47592_();
    }

    private static void m_194869_(BiomeGenerationSettings.Builder p_194870_) {
        BiomeDefaultFeatures.m_194720_(p_194870_);
        BiomeDefaultFeatures.m_176857_(p_194870_);
        BiomeDefaultFeatures.m_126806_(p_194870_);
        BiomeDefaultFeatures.m_126810_(p_194870_);
        BiomeDefaultFeatures.m_126765_(p_194870_);
        BiomeDefaultFeatures.m_126771_(p_194870_);
    }

    public static Biome m_194876_(boolean p_194877_) {
        MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126734_($$1);
        $$1.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20499_, 8, 4, 4));
        $$1.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20517_, 4, 2, 3));
        $$1.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20452_, 8, 2, 4));
        if (p_194877_) {
            BiomeDefaultFeatures.m_126788_($$1);
        } else {
            BiomeDefaultFeatures.m_176859_($$1);
            BiomeDefaultFeatures.m_194725_($$1, 100, 25, 100, false);
        }
        BiomeGenerationSettings.Builder $$2 = new BiomeGenerationSettings.Builder();
        OverworldBiomes.m_194869_($$2);
        BiomeDefaultFeatures.m_126826_($$2);
        BiomeDefaultFeatures.m_126828_($$2);
        BiomeDefaultFeatures.m_126814_($$2);
        BiomeDefaultFeatures.m_126822_($$2);
        $$2.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, p_194877_ ? VegetationPlacements.f_195442_ : VegetationPlacements.f_195443_);
        BiomeDefaultFeatures.m_126720_($$2);
        BiomeDefaultFeatures.m_126718_($$2);
        BiomeDefaultFeatures.m_126730_($$2);
        BiomeDefaultFeatures.m_126745_($$2);
        BiomeDefaultFeatures.m_194737_($$2);
        Music $$3 = Musics.m_11653_(SoundEvents.f_215732_);
        return OverworldBiomes.m_236663_(Biome.Precipitation.RAIN, p_194877_ ? 0.25f : 0.3f, 0.8f, $$1, $$2, $$3);
    }

    public static Biome m_194842_() {
        MobSpawnSettings.Builder $$0 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126808_($$0);
        return OverworldBiomes.m_194845_(0.8f, false, true, false, $$0);
    }

    public static Biome m_194885_() {
        MobSpawnSettings.Builder $$0 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126808_($$0);
        $$0.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20508_, 40, 1, 2)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20505_, 2, 1, 3)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20507_, 1, 1, 2));
        return OverworldBiomes.m_194845_(0.9f, false, false, true, $$0);
    }

    public static Biome m_194895_() {
        MobSpawnSettings.Builder $$0 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126808_($$0);
        $$0.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20508_, 40, 1, 2)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20507_, 80, 1, 2)).m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20505_, 2, 1, 1));
        return OverworldBiomes.m_194845_(0.9f, true, false, true, $$0);
    }

    private static Biome m_194845_(float p_194846_, boolean p_194847_, boolean p_194848_, boolean p_194849_, MobSpawnSettings.Builder p_194850_) {
        BiomeGenerationSettings.Builder $$5 = new BiomeGenerationSettings.Builder();
        OverworldBiomes.m_194869_($$5);
        BiomeDefaultFeatures.m_126814_($$5);
        BiomeDefaultFeatures.m_126822_($$5);
        if (p_194847_) {
            BiomeDefaultFeatures.m_126836_($$5);
        } else {
            if (p_194849_) {
                BiomeDefaultFeatures.m_126834_($$5);
            }
            if (p_194848_) {
                BiomeDefaultFeatures.m_198927_($$5);
            } else {
                BiomeDefaultFeatures.m_126688_($$5);
            }
        }
        BiomeDefaultFeatures.m_126722_($$5);
        BiomeDefaultFeatures.m_126696_($$5);
        BiomeDefaultFeatures.m_126730_($$5);
        BiomeDefaultFeatures.m_126745_($$5);
        BiomeDefaultFeatures.m_198933_($$5);
        if (p_194848_) {
            BiomeDefaultFeatures.m_198931_($$5);
        } else {
            BiomeDefaultFeatures.m_198929_($$5);
        }
        Music $$6 = Musics.m_11653_(SoundEvents.f_215731_);
        return OverworldBiomes.m_236663_(Biome.Precipitation.RAIN, 0.95f, p_194846_, p_194850_, $$5, $$6);
    }

    public static Biome m_194886_(boolean p_194887_) {
        MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126734_($$1);
        $$1.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20466_, 5, 4, 6));
        BiomeDefaultFeatures.m_126788_($$1);
        BiomeGenerationSettings.Builder $$2 = new BiomeGenerationSettings.Builder();
        OverworldBiomes.m_194869_($$2);
        BiomeDefaultFeatures.m_126814_($$2);
        BiomeDefaultFeatures.m_126822_($$2);
        if (p_194887_) {
            BiomeDefaultFeatures.m_194716_($$2);
        } else {
            BiomeDefaultFeatures.m_126684_($$2);
        }
        BiomeDefaultFeatures.m_126720_($$2);
        BiomeDefaultFeatures.m_126724_($$2);
        BiomeDefaultFeatures.m_126730_($$2);
        BiomeDefaultFeatures.m_126745_($$2);
        BiomeDefaultFeatures.m_126818_($$2);
        BiomeDefaultFeatures.m_126820_($$2);
        return OverworldBiomes.m_236663_(Biome.Precipitation.RAIN, 0.2f, 0.3f, $$1, $$2, f_194839_);
    }

    public static Biome m_194898_() {
        MobSpawnSettings.Builder $$0 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126800_($$0);
        BiomeGenerationSettings.Builder $$1 = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.m_126757_($$1);
        OverworldBiomes.m_194869_($$1);
        BiomeDefaultFeatures.m_126814_($$1);
        BiomeDefaultFeatures.m_126822_($$1);
        BiomeDefaultFeatures.m_126720_($$1);
        BiomeDefaultFeatures.m_126724_($$1);
        BiomeDefaultFeatures.m_126716_($$1);
        BiomeDefaultFeatures.m_126730_($$1);
        BiomeDefaultFeatures.m_126751_($$1);
        BiomeDefaultFeatures.m_126755_($$1);
        return OverworldBiomes.m_236663_(Biome.Precipitation.NONE, 2.0f, 0.0f, $$0, $$1, f_194839_);
    }

    public static Biome m_194881_(boolean p_194882_, boolean p_194883_, boolean p_194884_) {
        MobSpawnSettings.Builder $$3 = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder $$4 = new BiomeGenerationSettings.Builder();
        OverworldBiomes.m_194869_($$4);
        if (p_194883_) {
            $$3.m_48368_(0.07f);
            BiomeDefaultFeatures.m_126796_($$3);
            if (p_194884_) {
                $$4.m_204201_(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.f_195260_);
                $$4.m_204201_(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.f_195261_);
            }
        } else {
            BiomeDefaultFeatures.m_126792_($$3);
            BiomeDefaultFeatures.m_126728_($$4);
            if (p_194882_) {
                $$4.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195449_);
            }
        }
        BiomeDefaultFeatures.m_126814_($$4);
        BiomeDefaultFeatures.m_126822_($$4);
        if (p_194883_) {
            BiomeDefaultFeatures.m_126694_($$4);
            BiomeDefaultFeatures.m_126720_($$4);
            BiomeDefaultFeatures.m_126724_($$4);
        } else {
            BiomeDefaultFeatures.m_126714_($$4);
        }
        BiomeDefaultFeatures.m_126730_($$4);
        if (p_194882_) {
            $$4.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195403_);
            $$4.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195450_);
        } else {
            BiomeDefaultFeatures.m_126745_($$4);
        }
        float $$5 = p_194883_ ? 0.0f : 0.8f;
        return OverworldBiomes.m_236663_(p_194883_ ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN, $$5, p_194883_ ? 0.5f : 0.4f, $$3, $$4, f_194839_);
    }

    public static Biome m_194901_() {
        MobSpawnSettings.Builder $$0 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126804_($$0);
        BiomeGenerationSettings.Builder $$1 = new BiomeGenerationSettings.Builder();
        OverworldBiomes.m_194869_($$1);
        BiomeDefaultFeatures.m_126814_($$1);
        BiomeDefaultFeatures.m_126822_($$1);
        BiomeDefaultFeatures.m_126712_($$1);
        BiomeDefaultFeatures.m_126745_($$1);
        return OverworldBiomes.m_236663_(Biome.Precipitation.RAIN, 0.9f, 1.0f, $$0, $$1, f_194839_);
    }

    public static Biome m_194878_(boolean p_194879_, boolean p_194880_) {
        BiomeGenerationSettings.Builder $$2 = new BiomeGenerationSettings.Builder();
        OverworldBiomes.m_194869_($$2);
        if (!p_194879_) {
            BiomeDefaultFeatures.m_126698_($$2);
        }
        BiomeDefaultFeatures.m_126814_($$2);
        BiomeDefaultFeatures.m_126822_($$2);
        if (p_194879_) {
            BiomeDefaultFeatures.m_126682_($$2);
            BiomeDefaultFeatures.m_126720_($$2);
            BiomeDefaultFeatures.m_126700_($$2);
        } else {
            BiomeDefaultFeatures.m_126680_($$2);
            BiomeDefaultFeatures.m_126722_($$2);
            BiomeDefaultFeatures.m_126702_($$2);
        }
        BiomeDefaultFeatures.m_126730_($$2);
        BiomeDefaultFeatures.m_126745_($$2);
        MobSpawnSettings.Builder $$3 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126734_($$3);
        $$3.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20457_, 1, 2, 6)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20560_, 1, 1, 1));
        BiomeDefaultFeatures.m_126788_($$3);
        if (p_194880_) {
            $$3.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20466_, 8, 4, 4));
        }
        return OverworldBiomes.m_236663_(Biome.Precipitation.NONE, 2.0f, 0.0f, $$3, $$2, f_194839_);
    }

    public static Biome m_194896_(boolean p_194897_) {
        MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126788_($$1);
        BiomeGenerationSettings.Builder $$2 = new BiomeGenerationSettings.Builder();
        OverworldBiomes.m_194869_($$2);
        BiomeDefaultFeatures.m_126814_($$2);
        BiomeDefaultFeatures.m_126816_($$2);
        BiomeDefaultFeatures.m_126822_($$2);
        if (p_194897_) {
            BiomeDefaultFeatures.m_126692_($$2);
        }
        BiomeDefaultFeatures.m_126704_($$2);
        BiomeDefaultFeatures.m_126730_($$2);
        BiomeDefaultFeatures.m_126747_($$2);
        return new Biome.BiomeBuilder().m_47597_(Biome.Precipitation.NONE).m_47609_(2.0f).m_47611_(0.0f).m_47603_(new BiomeSpecialEffects.Builder().m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(OverworldBiomes.m_194843_(2.0f)).m_48043_(10387789).m_48045_(9470285).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_($$1.m_48381_()).m_47601_($$2.m_47831_()).m_47592_();
    }

    private static Biome m_194871_(MobSpawnSettings.Builder p_194872_, int p_194873_, int p_194874_, BiomeGenerationSettings.Builder p_194875_) {
        return OverworldBiomes.m_236654_(Biome.Precipitation.RAIN, 0.5f, 0.5f, p_194873_, p_194874_, p_194872_, p_194875_, f_194839_);
    }

    private static BiomeGenerationSettings.Builder m_194924_() {
        BiomeGenerationSettings.Builder $$0 = new BiomeGenerationSettings.Builder();
        OverworldBiomes.m_194869_($$0);
        BiomeDefaultFeatures.m_126814_($$0);
        BiomeDefaultFeatures.m_126822_($$0);
        BiomeDefaultFeatures.m_126840_($$0);
        BiomeDefaultFeatures.m_126720_($$0);
        BiomeDefaultFeatures.m_126724_($$0);
        BiomeDefaultFeatures.m_126730_($$0);
        BiomeDefaultFeatures.m_126745_($$0);
        return $$0;
    }

    public static Biome m_194899_(boolean p_194900_) {
        MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126740_($$1, 3, 4, 15);
        $$1.m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20519_, 15, 1, 5));
        BiomeGenerationSettings.Builder $$2 = OverworldBiomes.m_194924_();
        $$2.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, p_194900_ ? AquaticPlacements.f_195225_ : AquaticPlacements.f_195220_);
        BiomeDefaultFeatures.m_126761_($$2);
        BiomeDefaultFeatures.m_126759_($$2);
        return OverworldBiomes.m_194871_($$1, 4020182, 329011, $$2);
    }

    public static Biome m_194902_(boolean p_194903_) {
        MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126740_($$1, 1, 4, 10);
        $$1.m_48376_(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20559_, 1, 1, 2));
        BiomeGenerationSettings.Builder $$2 = OverworldBiomes.m_194924_();
        $$2.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, p_194903_ ? AquaticPlacements.f_195224_ : AquaticPlacements.f_195219_);
        BiomeDefaultFeatures.m_126761_($$2);
        BiomeDefaultFeatures.m_126759_($$2);
        return OverworldBiomes.m_194871_($$1, 4159204, 329011, $$2);
    }

    public static Biome m_194905_(boolean p_194906_) {
        MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder();
        if (p_194906_) {
            BiomeDefaultFeatures.m_126740_($$1, 8, 4, 8);
        } else {
            BiomeDefaultFeatures.m_126740_($$1, 10, 2, 15);
        }
        $$1.m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20516_, 5, 1, 3)).m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20489_, 25, 8, 8)).m_48376_(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20559_, 2, 1, 2));
        BiomeGenerationSettings.Builder $$2 = OverworldBiomes.m_194924_();
        $$2.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, p_194906_ ? AquaticPlacements.f_195223_ : AquaticPlacements.f_195218_);
        if (p_194906_) {
            BiomeDefaultFeatures.m_126761_($$2);
        }
        BiomeDefaultFeatures.m_126763_($$2);
        return OverworldBiomes.m_194871_($$1, 4566514, 267827, $$2);
    }

    public static Biome m_194904_() {
        MobSpawnSettings.Builder $$0 = new MobSpawnSettings.Builder().m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20516_, 15, 1, 3));
        BiomeDefaultFeatures.m_126736_($$0, 10, 4);
        BiomeGenerationSettings.Builder $$1 = OverworldBiomes.m_194924_().m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.f_195230_).m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.f_195218_).m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.f_195227_);
        return OverworldBiomes.m_194871_($$0, 4445678, 270131, $$1);
    }

    public static Biome m_194908_(boolean p_194909_) {
        MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder().m_48376_(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20480_, 1, 1, 4)).m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20519_, 15, 1, 5)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20514_, 1, 1, 2));
        BiomeDefaultFeatures.m_126788_($$1);
        $$1.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20562_, 5, 1, 1));
        float $$2 = p_194909_ ? 0.5f : 0.0f;
        BiomeGenerationSettings.Builder $$3 = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.m_126767_($$3);
        OverworldBiomes.m_194869_($$3);
        BiomeDefaultFeatures.m_126769_($$3);
        BiomeDefaultFeatures.m_126814_($$3);
        BiomeDefaultFeatures.m_126822_($$3);
        BiomeDefaultFeatures.m_126840_($$3);
        BiomeDefaultFeatures.m_126720_($$3);
        BiomeDefaultFeatures.m_126724_($$3);
        BiomeDefaultFeatures.m_126730_($$3);
        BiomeDefaultFeatures.m_126745_($$3);
        return new Biome.BiomeBuilder().m_47597_(p_194909_ ? Biome.Precipitation.RAIN : Biome.Precipitation.SNOW).m_47609_($$2).m_47599_(Biome.TemperatureModifier.FROZEN).m_47611_(0.5f).m_47603_(new BiomeSpecialEffects.Builder().m_48034_(3750089).m_48037_(329011).m_48019_(12638463).m_48040_(OverworldBiomes.m_194843_($$2)).m_48027_(AmbientMoodSettings.f_47387_).m_48018_()).m_47605_($$1.m_48381_()).m_47601_($$3.m_47831_()).m_47592_();
    }

    public static Biome m_194891_(boolean p_194892_, boolean p_194893_, boolean p_194894_) {
        BiomeGenerationSettings.Builder $$3 = new BiomeGenerationSettings.Builder();
        OverworldBiomes.m_194869_($$3);
        if (p_194894_) {
            $$3.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195423_);
        } else {
            BiomeDefaultFeatures.m_126706_($$3);
        }
        BiomeDefaultFeatures.m_126814_($$3);
        BiomeDefaultFeatures.m_126822_($$3);
        if (p_194894_) {
            $$3.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195425_);
            $$3.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195416_);
            BiomeDefaultFeatures.m_126724_($$3);
        } else {
            if (p_194892_) {
                if (p_194893_) {
                    BiomeDefaultFeatures.m_126846_($$3);
                } else {
                    BiomeDefaultFeatures.m_126842_($$3);
                }
            } else {
                BiomeDefaultFeatures.m_126844_($$3);
            }
            BiomeDefaultFeatures.m_126720_($$3);
            BiomeDefaultFeatures.m_126708_($$3);
        }
        BiomeDefaultFeatures.m_126730_($$3);
        BiomeDefaultFeatures.m_126745_($$3);
        MobSpawnSettings.Builder $$4 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126734_($$4);
        BiomeDefaultFeatures.m_126788_($$4);
        if (p_194894_) {
            $$4.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20517_, 4, 2, 3));
        } else if (!p_194892_) {
            $$4.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20499_, 5, 4, 4));
        }
        float $$5 = p_194892_ ? 0.6f : 0.7f;
        Music $$6 = Musics.m_11653_(SoundEvents.f_215731_);
        return OverworldBiomes.m_236663_(Biome.Precipitation.RAIN, $$5, p_194892_ ? 0.6f : 0.8f, $$4, $$3, $$6);
    }

    public static Biome m_194911_(boolean p_194912_) {
        MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126734_($$1);
        $$1.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20499_, 8, 4, 4)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20517_, 4, 2, 3)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20452_, 8, 2, 4));
        BiomeDefaultFeatures.m_126788_($$1);
        float $$2 = p_194912_ ? -0.5f : 0.25f;
        BiomeGenerationSettings.Builder $$3 = new BiomeGenerationSettings.Builder();
        OverworldBiomes.m_194869_($$3);
        BiomeDefaultFeatures.m_126828_($$3);
        BiomeDefaultFeatures.m_126814_($$3);
        BiomeDefaultFeatures.m_126822_($$3);
        BiomeDefaultFeatures.m_126838_($$3);
        BiomeDefaultFeatures.m_126720_($$3);
        BiomeDefaultFeatures.m_126726_($$3);
        BiomeDefaultFeatures.m_126745_($$3);
        if (p_194912_) {
            BiomeDefaultFeatures.m_194735_($$3);
        } else {
            BiomeDefaultFeatures.m_194737_($$3);
        }
        return OverworldBiomes.m_236654_(p_194912_ ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN, $$2, p_194912_ ? 0.4f : 0.8f, p_194912_ ? 4020182 : 4159204, 329011, $$1, $$3, f_194839_);
    }

    public static Biome m_194907_() {
        MobSpawnSettings.Builder $$0 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126734_($$0);
        BiomeDefaultFeatures.m_126788_($$0);
        BiomeGenerationSettings.Builder $$1 = new BiomeGenerationSettings.Builder();
        OverworldBiomes.m_194869_($$1);
        $$1.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195422_);
        BiomeDefaultFeatures.m_126706_($$1);
        BiomeDefaultFeatures.m_126814_($$1);
        BiomeDefaultFeatures.m_126822_($$1);
        BiomeDefaultFeatures.m_126720_($$1);
        BiomeDefaultFeatures.m_126708_($$1);
        BiomeDefaultFeatures.m_126730_($$1);
        BiomeDefaultFeatures.m_126745_($$1);
        Music $$2 = Musics.m_11653_(SoundEvents.f_215731_);
        return new Biome.BiomeBuilder().m_47597_(Biome.Precipitation.RAIN).m_47609_(0.7f).m_47611_(0.8f).m_47603_(new BiomeSpecialEffects.Builder().m_48034_(4159204).m_48037_(329011).m_48019_(12638463).m_48040_(OverworldBiomes.m_194843_(0.7f)).m_48031_(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST).m_48027_(AmbientMoodSettings.f_47387_).m_48021_($$2).m_48018_()).m_47605_($$0.m_48381_()).m_47601_($$1.m_47831_()).m_47592_();
    }

    public static Biome m_194910_() {
        MobSpawnSettings.Builder $$0 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126734_($$0);
        BiomeDefaultFeatures.m_126788_($$0);
        $$0.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20526_, 1, 1, 1));
        $$0.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_217012_, 10, 2, 5));
        BiomeGenerationSettings.Builder $$1 = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.m_126757_($$1);
        OverworldBiomes.m_194869_($$1);
        BiomeDefaultFeatures.m_126814_($$1);
        BiomeDefaultFeatures.m_126824_($$1);
        BiomeDefaultFeatures.m_126710_($$1);
        BiomeDefaultFeatures.m_126730_($$1);
        BiomeDefaultFeatures.m_126753_($$1);
        $$1.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.f_195222_);
        Music $$2 = Musics.m_11653_(SoundEvents.f_215730_);
        return new Biome.BiomeBuilder().m_47597_(Biome.Precipitation.RAIN).m_47609_(0.8f).m_47611_(0.9f).m_47603_(new BiomeSpecialEffects.Builder().m_48034_(6388580).m_48037_(2302743).m_48019_(12638463).m_48040_(OverworldBiomes.m_194843_(0.8f)).m_48043_(6975545).m_48031_(BiomeSpecialEffects.GrassColorModifier.SWAMP).m_48027_(AmbientMoodSettings.f_47387_).m_48021_($$2).m_48018_()).m_47605_($$0.m_48381_()).m_47601_($$1.m_47831_()).m_47592_();
    }

    public static Biome m_236670_() {
        MobSpawnSettings.Builder $$0 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126788_($$0);
        $$0.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20526_, 1, 1, 1));
        $$0.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_217012_, 10, 2, 5));
        $$0.m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20489_, 25, 8, 8));
        BiomeGenerationSettings.Builder $$1 = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.m_126757_($$1);
        OverworldBiomes.m_194869_($$1);
        BiomeDefaultFeatures.m_126814_($$1);
        BiomeDefaultFeatures.m_236470_($$1);
        BiomeDefaultFeatures.m_236466_($$1);
        $$1.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.f_195222_);
        Music $$2 = Musics.m_11653_(SoundEvents.f_215730_);
        return new Biome.BiomeBuilder().m_47597_(Biome.Precipitation.RAIN).m_47609_(0.8f).m_47611_(0.9f).m_47603_(new BiomeSpecialEffects.Builder().m_48034_(3832426).m_48037_(5077600).m_48019_(12638463).m_48040_(OverworldBiomes.m_194843_(0.8f)).m_48043_(9285927).m_48031_(BiomeSpecialEffects.GrassColorModifier.SWAMP).m_48027_(AmbientMoodSettings.f_47387_).m_48021_($$2).m_48018_()).m_47605_($$0.m_48381_()).m_47601_($$1.m_47831_()).m_47592_();
    }

    public static Biome m_194914_(boolean p_194915_) {
        MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder().m_48376_(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20480_, 2, 1, 4)).m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20519_, 5, 1, 5));
        BiomeDefaultFeatures.m_126788_($$1);
        $$1.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20562_, p_194915_ ? 1 : 100, 1, 1));
        BiomeGenerationSettings.Builder $$2 = new BiomeGenerationSettings.Builder();
        OverworldBiomes.m_194869_($$2);
        BiomeDefaultFeatures.m_126814_($$2);
        BiomeDefaultFeatures.m_126822_($$2);
        BiomeDefaultFeatures.m_126840_($$2);
        BiomeDefaultFeatures.m_126720_($$2);
        BiomeDefaultFeatures.m_126724_($$2);
        BiomeDefaultFeatures.m_126730_($$2);
        BiomeDefaultFeatures.m_126745_($$2);
        if (!p_194915_) {
            $$2.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.f_195221_);
        }
        float $$3 = p_194915_ ? 0.0f : 0.5f;
        return OverworldBiomes.m_236654_(p_194915_ ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN, $$3, 0.5f, p_194915_ ? 3750089 : 4159204, 329011, $$1, $$2, f_194839_);
    }

    public static Biome m_194888_(boolean p_194889_, boolean p_194890_) {
        float $$7;
        boolean $$3;
        MobSpawnSettings.Builder $$2 = new MobSpawnSettings.Builder();
        boolean bl = $$3 = !p_194890_ && !p_194889_;
        if ($$3) {
            $$2.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20490_, 5, 2, 5));
        }
        BiomeDefaultFeatures.m_126788_($$2);
        BiomeGenerationSettings.Builder $$4 = new BiomeGenerationSettings.Builder();
        OverworldBiomes.m_194869_($$4);
        BiomeDefaultFeatures.m_126814_($$4);
        BiomeDefaultFeatures.m_126822_($$4);
        BiomeDefaultFeatures.m_126720_($$4);
        BiomeDefaultFeatures.m_126724_($$4);
        BiomeDefaultFeatures.m_126730_($$4);
        BiomeDefaultFeatures.m_126745_($$4);
        if (p_194889_) {
            float $$5 = 0.05f;
        } else if (p_194890_) {
            float $$6 = 0.2f;
        } else {
            $$7 = 0.8f;
        }
        return OverworldBiomes.m_236654_(p_194889_ ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN, $$7, $$3 ? 0.4f : 0.3f, p_194889_ ? 4020182 : 4159204, 329011, $$2, $$4, f_194839_);
    }

    public static Biome m_194913_() {
        BiomeGenerationSettings.Builder $$0 = new BiomeGenerationSettings.Builder();
        $$0.m_204201_(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.f_195272_);
        return OverworldBiomes.m_236663_(Biome.Precipitation.NONE, 0.5f, 0.5f, new MobSpawnSettings.Builder(), $$0, f_194839_);
    }

    public static Biome m_194916_() {
        BiomeGenerationSettings.Builder $$0 = new BiomeGenerationSettings.Builder();
        MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder();
        $$1.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20560_, 1, 1, 2)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20517_, 2, 2, 6)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20520_, 2, 2, 4));
        BiomeDefaultFeatures.m_126788_($$1);
        OverworldBiomes.m_194869_($$0);
        BiomeDefaultFeatures.m_126728_($$0);
        BiomeDefaultFeatures.m_126814_($$0);
        BiomeDefaultFeatures.m_126822_($$0);
        BiomeDefaultFeatures.m_194718_($$0);
        BiomeDefaultFeatures.m_126818_($$0);
        BiomeDefaultFeatures.m_126820_($$0);
        Music $$2 = Musics.m_11653_(SoundEvents.f_184223_);
        return OverworldBiomes.m_236654_(Biome.Precipitation.RAIN, 0.5f, 0.8f, 937679, 329011, $$1, $$0, $$2);
    }

    public static Biome m_194917_() {
        BiomeGenerationSettings.Builder $$0 = new BiomeGenerationSettings.Builder();
        MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder();
        $$1.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_147035_, 5, 1, 3));
        BiomeDefaultFeatures.m_126788_($$1);
        OverworldBiomes.m_194869_($$0);
        BiomeDefaultFeatures.m_194731_($$0);
        BiomeDefaultFeatures.m_126814_($$0);
        BiomeDefaultFeatures.m_126822_($$0);
        BiomeDefaultFeatures.m_126818_($$0);
        BiomeDefaultFeatures.m_126820_($$0);
        Music $$2 = Musics.m_11653_(SoundEvents.f_184224_);
        return OverworldBiomes.m_236663_(Biome.Precipitation.SNOW, -0.7f, 0.9f, $$1, $$0, $$2);
    }

    public static Biome m_194918_() {
        BiomeGenerationSettings.Builder $$0 = new BiomeGenerationSettings.Builder();
        MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder();
        $$1.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_147035_, 5, 1, 3));
        BiomeDefaultFeatures.m_126788_($$1);
        OverworldBiomes.m_194869_($$0);
        BiomeDefaultFeatures.m_194731_($$0);
        BiomeDefaultFeatures.m_126814_($$0);
        BiomeDefaultFeatures.m_126822_($$0);
        BiomeDefaultFeatures.m_126818_($$0);
        BiomeDefaultFeatures.m_126820_($$0);
        Music $$2 = Musics.m_11653_(SoundEvents.f_184221_);
        return OverworldBiomes.m_236663_(Biome.Precipitation.SNOW, -0.7f, 0.9f, $$1, $$0, $$2);
    }

    public static Biome m_194919_() {
        BiomeGenerationSettings.Builder $$0 = new BiomeGenerationSettings.Builder();
        MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126788_($$1);
        OverworldBiomes.m_194869_($$0);
        BiomeDefaultFeatures.m_126814_($$0);
        BiomeDefaultFeatures.m_126822_($$0);
        BiomeDefaultFeatures.m_126818_($$0);
        BiomeDefaultFeatures.m_126820_($$0);
        Music $$2 = Musics.m_11653_(SoundEvents.f_184226_);
        return OverworldBiomes.m_236663_(Biome.Precipitation.RAIN, 1.0f, 0.3f, $$1, $$0, $$2);
    }

    public static Biome m_194920_() {
        BiomeGenerationSettings.Builder $$0 = new BiomeGenerationSettings.Builder();
        MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder();
        $$1.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20517_, 4, 2, 3)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_147035_, 5, 1, 3));
        BiomeDefaultFeatures.m_126788_($$1);
        OverworldBiomes.m_194869_($$0);
        BiomeDefaultFeatures.m_194731_($$0);
        BiomeDefaultFeatures.m_126814_($$0);
        BiomeDefaultFeatures.m_126822_($$0);
        BiomeDefaultFeatures.m_126745_($$0);
        BiomeDefaultFeatures.m_126818_($$0);
        BiomeDefaultFeatures.m_126820_($$0);
        Music $$2 = Musics.m_11653_(SoundEvents.f_184225_);
        return OverworldBiomes.m_236663_(Biome.Precipitation.SNOW, -0.3f, 0.9f, $$1, $$0, $$2);
    }

    public static Biome m_194921_() {
        BiomeGenerationSettings.Builder $$0 = new BiomeGenerationSettings.Builder();
        MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_126734_($$1);
        $$1.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20499_, 8, 4, 4)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20517_, 4, 2, 3)).m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20452_, 8, 2, 4));
        BiomeDefaultFeatures.m_126788_($$1);
        OverworldBiomes.m_194869_($$0);
        BiomeDefaultFeatures.m_194731_($$0);
        BiomeDefaultFeatures.m_126814_($$0);
        BiomeDefaultFeatures.m_126822_($$0);
        BiomeDefaultFeatures.m_194739_($$0);
        BiomeDefaultFeatures.m_126745_($$0);
        BiomeDefaultFeatures.m_126818_($$0);
        BiomeDefaultFeatures.m_126820_($$0);
        Music $$2 = Musics.m_11653_(SoundEvents.f_184220_);
        return OverworldBiomes.m_236663_(Biome.Precipitation.SNOW, -0.2f, 0.8f, $$1, $$0, $$2);
    }

    public static Biome m_194922_() {
        MobSpawnSettings.Builder $$0 = new MobSpawnSettings.Builder();
        $$0.m_48376_(MobCategory.AXOLOTLS, new MobSpawnSettings.SpawnerData(EntityType.f_147039_, 10, 4, 6));
        $$0.m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20489_, 25, 8, 8));
        BiomeDefaultFeatures.m_126788_($$0);
        BiomeGenerationSettings.Builder $$1 = new BiomeGenerationSettings.Builder();
        OverworldBiomes.m_194869_($$1);
        BiomeDefaultFeatures.m_126728_($$1);
        BiomeDefaultFeatures.m_126814_($$1);
        BiomeDefaultFeatures.m_176852_($$1);
        BiomeDefaultFeatures.m_126822_($$1);
        BiomeDefaultFeatures.m_176850_($$1);
        Music $$2 = Musics.m_11653_(SoundEvents.f_184222_);
        return OverworldBiomes.m_236663_(Biome.Precipitation.RAIN, 0.5f, 0.5f, $$0, $$1, $$2);
    }

    public static Biome m_194923_() {
        MobSpawnSettings.Builder $$0 = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.m_194733_($$0);
        BiomeGenerationSettings.Builder $$1 = new BiomeGenerationSettings.Builder();
        OverworldBiomes.m_194869_($$1);
        BiomeDefaultFeatures.m_126728_($$1);
        BiomeDefaultFeatures.m_194722_($$1, true);
        BiomeDefaultFeatures.m_126822_($$1);
        BiomeDefaultFeatures.m_126714_($$1);
        BiomeDefaultFeatures.m_126730_($$1);
        BiomeDefaultFeatures.m_126745_($$1);
        BiomeDefaultFeatures.m_176863_($$1);
        Music $$2 = Musics.m_11653_(SoundEvents.f_184219_);
        return OverworldBiomes.m_236663_(Biome.Precipitation.RAIN, 0.8f, 0.4f, $$0, $$1, $$2);
    }

    public static Biome m_236671_() {
        MobSpawnSettings.Builder $$0 = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder $$1 = new BiomeGenerationSettings.Builder();
        $$1.m_204198_(GenerationStep.Carving.AIR, Carvers.f_126848_);
        $$1.m_204198_(GenerationStep.Carving.AIR, Carvers.f_194741_);
        $$1.m_204198_(GenerationStep.Carving.AIR, Carvers.f_126849_);
        BiomeDefaultFeatures.m_176857_($$1);
        BiomeDefaultFeatures.m_126806_($$1);
        BiomeDefaultFeatures.m_126810_($$1);
        BiomeDefaultFeatures.m_126771_($$1);
        BiomeDefaultFeatures.m_126728_($$1);
        BiomeDefaultFeatures.m_194722_($$1, true);
        BiomeDefaultFeatures.m_126822_($$1);
        BiomeDefaultFeatures.m_126714_($$1);
        BiomeDefaultFeatures.m_126730_($$1);
        BiomeDefaultFeatures.m_126745_($$1);
        BiomeDefaultFeatures.m_236468_($$1);
        Music $$2 = Musics.m_11653_(SoundEvents.f_215729_);
        return OverworldBiomes.m_236663_(Biome.Precipitation.RAIN, 0.8f, 0.4f, $$0, $$1, $$2);
    }
}

