/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.data.worldgen;

import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class BiomeDefaultFeatures {
    public static void m_194720_(BiomeGenerationSettings.Builder p_194721_) {
        p_194721_.m_204198_(GenerationStep.Carving.AIR, Carvers.f_126848_);
        p_194721_.m_204198_(GenerationStep.Carving.AIR, Carvers.f_194741_);
        p_194721_.m_204198_(GenerationStep.Carving.AIR, Carvers.f_126849_);
        p_194721_.m_204201_(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.f_195266_);
        p_194721_.m_204201_(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.f_195267_);
    }

    public static void m_126806_(BiomeGenerationSettings.Builder p_126807_) {
        p_126807_.m_204201_(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.f_195235_);
        p_126807_.m_204201_(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.f_195236_);
    }

    public static void m_126810_(BiomeGenerationSettings.Builder p_126811_) {
        p_126811_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195323_);
        p_126811_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195324_);
        p_126811_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195325_);
        p_126811_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195326_);
        p_126811_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195327_);
        p_126811_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195328_);
        p_126811_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195329_);
        p_126811_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195330_);
        p_126811_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195331_);
        p_126811_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.f_195243_);
    }

    public static void m_176863_(BiomeGenerationSettings.Builder p_176864_) {
        p_176864_.m_204201_(GenerationStep.Decoration.LOCAL_MODIFICATIONS, CavePlacements.f_195240_);
        p_176864_.m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.f_195239_);
        p_176864_.m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.f_195241_);
    }

    public static void m_236468_(BiomeGenerationSettings.Builder p_236469_) {
        p_236469_.m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.f_236767_);
        p_236469_.m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.f_236765_);
    }

    public static void m_126814_(BiomeGenerationSettings.Builder p_126815_) {
        BiomeDefaultFeatures.m_194722_(p_126815_, false);
    }

    public static void m_194722_(BiomeGenerationSettings.Builder p_194723_, boolean p_194724_) {
        p_194723_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195332_);
        p_194723_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195333_);
        p_194723_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195334_);
        p_194723_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195335_);
        p_194723_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195336_);
        p_194723_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195338_);
        p_194723_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195339_);
        p_194723_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195340_);
        p_194723_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195302_);
        p_194723_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195303_);
        p_194723_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195304_);
        p_194723_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195305_);
        p_194723_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195306_);
        p_194723_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195307_);
        p_194723_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, p_194724_ ? OrePlacements.f_195313_ : OrePlacements.f_195312_);
        p_194723_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, CavePlacements.f_195242_);
    }

    public static void m_126816_(BiomeGenerationSettings.Builder p_126817_) {
        p_126817_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195337_);
    }

    public static void m_126818_(BiomeGenerationSettings.Builder p_126819_) {
        p_126819_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195309_);
    }

    public static void m_126820_(BiomeGenerationSettings.Builder p_126821_) {
        p_126821_.m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.f_195308_);
    }

    public static void m_126822_(BiomeGenerationSettings.Builder p_126823_) {
        p_126823_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.f_195270_);
        p_126823_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.f_195268_);
        p_126823_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.f_195269_);
    }

    public static void m_126824_(BiomeGenerationSettings.Builder p_126825_) {
        p_126825_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.f_195268_);
    }

    public static void m_236470_(BiomeGenerationSettings.Builder p_236471_) {
        p_236471_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.f_236768_);
        p_236471_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.f_195268_);
    }

    public static void m_126826_(BiomeGenerationSettings.Builder p_126827_) {
        p_126827_.m_204201_(GenerationStep.Decoration.LOCAL_MODIFICATIONS, MiscOverworldPlacements.f_195262_);
    }

    public static void m_126828_(BiomeGenerationSettings.Builder p_126829_) {
        p_126829_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195469_);
    }

    public static void m_194735_(BiomeGenerationSettings.Builder p_194736_) {
        p_194736_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195465_);
    }

    public static void m_194737_(BiomeGenerationSettings.Builder p_194738_) {
        p_194738_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195464_);
    }

    public static void m_126834_(BiomeGenerationSettings.Builder p_126835_) {
        p_126835_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195426_);
    }

    public static void m_126836_(BiomeGenerationSettings.Builder p_126837_) {
        p_126837_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195447_);
        p_126837_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195445_);
    }

    public static void m_126838_(BiomeGenerationSettings.Builder p_126839_) {
        p_126839_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195428_);
    }

    public static void m_194739_(BiomeGenerationSettings.Builder p_194740_) {
        p_194740_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195429_);
    }

    public static void m_126840_(BiomeGenerationSettings.Builder p_126841_) {
        p_126841_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195439_);
    }

    public static void m_126842_(BiomeGenerationSettings.Builder p_126843_) {
        p_126843_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195436_);
    }

    public static void m_126844_(BiomeGenerationSettings.Builder p_126845_) {
        p_126845_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195440_);
    }

    public static void m_126846_(BiomeGenerationSettings.Builder p_126847_) {
        p_126847_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195435_);
    }

    public static void m_126680_(BiomeGenerationSettings.Builder p_126681_) {
        p_126681_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195434_);
    }

    public static void m_126682_(BiomeGenerationSettings.Builder p_126683_) {
        p_126683_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195433_);
    }

    public static void m_176850_(BiomeGenerationSettings.Builder p_176851_) {
        p_176851_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.f_195248_);
        p_176851_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.f_195245_);
        p_176851_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.f_195247_);
        p_176851_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.f_195246_);
        p_176851_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.f_195244_);
        p_176851_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.f_195249_);
        p_176851_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.f_195250_);
    }

    public static void m_176852_(BiomeGenerationSettings.Builder p_176853_) {
        p_176853_.m_204201_(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.f_195314_);
    }

    public static void m_126684_(BiomeGenerationSettings.Builder p_126685_) {
        p_126685_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195438_);
    }

    public static void m_194716_(BiomeGenerationSettings.Builder p_194717_) {
        p_194717_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195437_);
    }

    public static void m_126688_(BiomeGenerationSettings.Builder p_126689_) {
        p_126689_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195444_);
    }

    public static void m_198927_(BiomeGenerationSettings.Builder p_198928_) {
        p_198928_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195441_);
    }

    public static void m_126692_(BiomeGenerationSettings.Builder p_126693_) {
        p_126693_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195430_);
    }

    public static void m_126694_(BiomeGenerationSettings.Builder p_126695_) {
        p_126695_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195431_);
    }

    public static void m_126696_(BiomeGenerationSettings.Builder p_126697_) {
        p_126697_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195458_);
    }

    public static void m_126698_(BiomeGenerationSettings.Builder p_126699_) {
        p_126699_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195468_);
    }

    public static void m_126700_(BiomeGenerationSettings.Builder p_126701_) {
        p_126701_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195455_);
    }

    public static void m_126702_(BiomeGenerationSettings.Builder p_126703_) {
        p_126703_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195454_);
    }

    public static void m_126704_(BiomeGenerationSettings.Builder p_126705_) {
        p_126705_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195453_);
        p_126705_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195462_);
    }

    public static void m_126706_(BiomeGenerationSettings.Builder p_126707_) {
        p_126707_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195424_);
    }

    public static void m_126708_(BiomeGenerationSettings.Builder p_126709_) {
        p_126709_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195452_);
    }

    public static void m_126710_(BiomeGenerationSettings.Builder p_126711_) {
        p_126711_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195432_);
        p_126711_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195417_);
        p_126711_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195455_);
        p_126711_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195461_);
        p_126711_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195466_);
        p_126711_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195412_);
        p_126711_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195413_);
    }

    public static void m_236466_(BiomeGenerationSettings.Builder p_236467_) {
        p_236467_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_236773_);
        p_236467_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195455_);
        p_236467_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195461_);
        p_236467_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195466_);
    }

    public static void m_126712_(BiomeGenerationSettings.Builder p_126713_) {
        p_126713_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195446_);
        p_126713_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195408_);
        p_126713_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195409_);
    }

    public static void m_126714_(BiomeGenerationSettings.Builder p_126715_) {
        p_126715_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195421_);
        p_126715_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_197412_);
        p_126715_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195451_);
    }

    public static void m_126716_(BiomeGenerationSettings.Builder p_126717_) {
        p_126717_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195460_);
    }

    public static void m_126718_(BiomeGenerationSettings.Builder p_126719_) {
        p_126719_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195457_);
        p_126719_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195461_);
        p_126719_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195410_);
        p_126719_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195411_);
    }

    public static void m_126720_(BiomeGenerationSettings.Builder p_126721_) {
        p_126721_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195415_);
    }

    public static void m_194718_(BiomeGenerationSettings.Builder p_194719_) {
        p_194719_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195451_);
        p_194719_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195419_);
        p_194719_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195427_);
    }

    public static void m_126722_(BiomeGenerationSettings.Builder p_126723_) {
        p_126723_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195414_);
    }

    public static void m_126724_(BiomeGenerationSettings.Builder p_126725_) {
        p_126725_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195453_);
    }

    public static void m_126726_(BiomeGenerationSettings.Builder p_126727_) {
        p_126727_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195456_);
        p_126727_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195408_);
        p_126727_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195409_);
    }

    public static void m_126728_(BiomeGenerationSettings.Builder p_126729_) {
        p_126729_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195467_);
    }

    public static void m_126730_(BiomeGenerationSettings.Builder p_126731_) {
        p_126731_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195406_);
        p_126731_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195407_);
    }

    public static void m_126745_(BiomeGenerationSettings.Builder p_126746_) {
        p_126746_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195403_);
        p_126746_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195450_);
    }

    public static void m_126747_(BiomeGenerationSettings.Builder p_126748_) {
        p_126748_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195402_);
        p_126748_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195450_);
        p_126748_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195471_);
    }

    public static void m_198929_(BiomeGenerationSettings.Builder p_198930_) {
        p_198930_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195463_);
    }

    public static void m_198931_(BiomeGenerationSettings.Builder p_198932_) {
        p_198932_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_198935_);
    }

    public static void m_198933_(BiomeGenerationSettings.Builder p_198934_) {
        p_198934_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195448_);
    }

    public static void m_126751_(BiomeGenerationSettings.Builder p_126752_) {
        p_126752_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195401_);
        p_126752_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195450_);
        p_126752_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195470_);
    }

    public static void m_126753_(BiomeGenerationSettings.Builder p_126754_) {
        p_126754_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195400_);
        p_126754_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.f_195450_);
    }

    public static void m_126755_(BiomeGenerationSettings.Builder p_126756_) {
        p_126756_.m_204201_(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.f_195273_);
    }

    public static void m_126757_(BiomeGenerationSettings.Builder p_126758_) {
        p_126758_.m_204201_(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.f_195237_);
        p_126758_.m_204201_(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.f_195238_);
    }

    public static void m_126759_(BiomeGenerationSettings.Builder p_126760_) {
        p_126760_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.f_195228_);
    }

    public static void m_126761_(BiomeGenerationSettings.Builder p_126762_) {
        p_126762_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.f_195226_);
    }

    public static void m_126763_(BiomeGenerationSettings.Builder p_126764_) {
        p_126764_.m_204201_(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.f_195229_);
    }

    public static void m_126765_(BiomeGenerationSettings.Builder p_126766_) {
        p_126766_.m_204201_(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.f_195276_);
        p_126766_.m_204201_(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.f_195274_);
    }

    public static void m_194731_(BiomeGenerationSettings.Builder p_194732_) {
        p_194732_.m_204201_(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.f_195275_);
    }

    public static void m_126767_(BiomeGenerationSettings.Builder p_126768_) {
        p_126768_.m_204201_(GenerationStep.Decoration.LOCAL_MODIFICATIONS, MiscOverworldPlacements.f_195263_);
        p_126768_.m_204201_(GenerationStep.Decoration.LOCAL_MODIFICATIONS, MiscOverworldPlacements.f_195264_);
    }

    public static void m_126769_(BiomeGenerationSettings.Builder p_126770_) {
        p_126770_.m_204201_(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.f_195265_);
    }

    public static void m_126771_(BiomeGenerationSettings.Builder p_126772_) {
        p_126772_.m_204201_(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.f_195271_);
    }

    public static void m_126773_(BiomeGenerationSettings.Builder p_126774_) {
        p_126774_.m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.f_195321_);
        p_126774_.m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.f_195322_);
        p_126774_.m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.f_195319_);
        p_126774_.m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.f_195320_);
        BiomeDefaultFeatures.m_126775_(p_126774_);
    }

    public static void m_126775_(BiomeGenerationSettings.Builder p_126776_) {
        p_126776_.m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.f_195310_);
        p_126776_.m_204201_(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.f_195311_);
    }

    public static void m_176857_(BiomeGenerationSettings.Builder p_176858_) {
        p_176858_.m_204201_(GenerationStep.Decoration.LOCAL_MODIFICATIONS, CavePlacements.f_195251_);
    }

    public static void m_126734_(MobSpawnSettings.Builder p_126735_) {
        p_126735_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20520_, 12, 4, 4));
        p_126735_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20510_, 10, 4, 4));
        p_126735_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20555_, 10, 4, 4));
        p_126735_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20557_, 8, 4, 4));
    }

    public static void m_176859_(MobSpawnSettings.Builder p_176860_) {
        p_176860_.m_48376_(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20549_, 10, 8, 8));
        p_176860_.m_48376_(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_147034_, 10, 4, 6));
    }

    public static void m_126788_(MobSpawnSettings.Builder p_126789_) {
        BiomeDefaultFeatures.m_176859_(p_126789_);
        BiomeDefaultFeatures.m_194725_(p_126789_, 95, 5, 100, false);
    }

    public static void m_126740_(MobSpawnSettings.Builder p_126741_, int p_126742_, int p_126743_, int p_126744_) {
        p_126741_.m_48376_(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20480_, p_126742_, 1, p_126743_));
        p_126741_.m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20556_, p_126744_, 3, 6));
        BiomeDefaultFeatures.m_126788_(p_126741_);
        p_126741_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20562_, 5, 1, 1));
    }

    public static void m_126736_(MobSpawnSettings.Builder p_126737_, int p_126738_, int p_126739_) {
        p_126737_.m_48376_(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20480_, p_126738_, p_126739_, 4));
        p_126737_.m_48376_(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.f_20489_, 25, 8, 8));
        p_126737_.m_48376_(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20559_, 2, 1, 2));
        p_126737_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20562_, 5, 1, 1));
        BiomeDefaultFeatures.m_126788_(p_126737_);
    }

    public static void m_126792_(MobSpawnSettings.Builder p_126793_) {
        BiomeDefaultFeatures.m_126734_(p_126793_);
        p_126793_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20457_, 5, 2, 6));
        p_126793_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20560_, 1, 1, 3));
        BiomeDefaultFeatures.m_126788_(p_126793_);
    }

    public static void m_126796_(MobSpawnSettings.Builder p_126797_) {
        p_126797_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20517_, 10, 2, 3));
        p_126797_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20514_, 1, 1, 2));
        BiomeDefaultFeatures.m_176859_(p_126797_);
        BiomeDefaultFeatures.m_194725_(p_126797_, 95, 5, 20, false);
        p_126797_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20481_, 80, 4, 4));
    }

    public static void m_126800_(MobSpawnSettings.Builder p_126801_) {
        p_126801_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20517_, 4, 2, 3));
        BiomeDefaultFeatures.m_176859_(p_126801_);
        BiomeDefaultFeatures.m_194725_(p_126801_, 19, 1, 100, false);
        p_126801_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20458_, 80, 4, 4));
    }

    public static void m_194733_(MobSpawnSettings.Builder p_194734_) {
        BiomeDefaultFeatures.m_176859_(p_194734_);
        int $$1 = 95;
        BiomeDefaultFeatures.m_194725_(p_194734_, 95, 5, 100, false);
        p_194734_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20562_, 95, 4, 4));
    }

    public static void m_194725_(MobSpawnSettings.Builder p_194726_, int p_194727_, int p_194728_, int p_194729_, boolean p_194730_) {
        p_194726_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20479_, 100, 4, 4));
        p_194726_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(p_194730_ ? EntityType.f_20562_ : EntityType.f_20501_, p_194727_, 4, 4));
        p_194726_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20530_, p_194728_, 1, 1));
        p_194726_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20524_, p_194729_, 4, 4));
        p_194726_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20558_, 100, 4, 4));
        p_194726_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20526_, 100, 4, 4));
        p_194726_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20566_, 10, 1, 4));
        p_194726_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20495_, 5, 1, 1));
    }

    public static void m_126804_(MobSpawnSettings.Builder p_126805_) {
        p_126805_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20504_, 8, 4, 8));
        BiomeDefaultFeatures.m_176859_(p_126805_);
    }

    public static void m_126808_(MobSpawnSettings.Builder p_126809_) {
        BiomeDefaultFeatures.m_126734_(p_126809_);
        p_126809_.m_48376_(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.f_20555_, 10, 4, 4));
        BiomeDefaultFeatures.m_126788_(p_126809_);
    }

    public static void m_126812_(MobSpawnSettings.Builder p_126813_) {
        p_126813_.m_48376_(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.f_20566_, 10, 4, 4));
    }
}

