/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.biome;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public abstract class Biomes {
    public static final ResourceKey<Biome> f_48173_ = Biomes.m_48228_("the_void");
    public static final ResourceKey<Biome> f_48202_ = Biomes.m_48228_("plains");
    public static final ResourceKey<Biome> f_48176_ = Biomes.m_48228_("sunflower_plains");
    public static final ResourceKey<Biome> f_186761_ = Biomes.m_48228_("snowy_plains");
    public static final ResourceKey<Biome> f_48182_ = Biomes.m_48228_("ice_spikes");
    public static final ResourceKey<Biome> f_48203_ = Biomes.m_48228_("desert");
    public static final ResourceKey<Biome> f_48207_ = Biomes.m_48228_("swamp");
    public static final ResourceKey<Biome> f_220595_ = Biomes.m_48228_("mangrove_swamp");
    public static final ResourceKey<Biome> f_48205_ = Biomes.m_48228_("forest");
    public static final ResourceKey<Biome> f_48179_ = Biomes.m_48228_("flower_forest");
    public static final ResourceKey<Biome> f_48149_ = Biomes.m_48228_("birch_forest");
    public static final ResourceKey<Biome> f_48151_ = Biomes.m_48228_("dark_forest");
    public static final ResourceKey<Biome> f_186762_ = Biomes.m_48228_("old_growth_birch_forest");
    public static final ResourceKey<Biome> f_186763_ = Biomes.m_48228_("old_growth_pine_taiga");
    public static final ResourceKey<Biome> f_186764_ = Biomes.m_48228_("old_growth_spruce_taiga");
    public static final ResourceKey<Biome> f_48206_ = Biomes.m_48228_("taiga");
    public static final ResourceKey<Biome> f_48152_ = Biomes.m_48228_("snowy_taiga");
    public static final ResourceKey<Biome> f_48157_ = Biomes.m_48228_("savanna");
    public static final ResourceKey<Biome> f_48158_ = Biomes.m_48228_("savanna_plateau");
    public static final ResourceKey<Biome> f_186765_ = Biomes.m_48228_("windswept_hills");
    public static final ResourceKey<Biome> f_186766_ = Biomes.m_48228_("windswept_gravelly_hills");
    public static final ResourceKey<Biome> f_186767_ = Biomes.m_48228_("windswept_forest");
    public static final ResourceKey<Biome> f_186768_ = Biomes.m_48228_("windswept_savanna");
    public static final ResourceKey<Biome> f_48222_ = Biomes.m_48228_("jungle");
    public static final ResourceKey<Biome> f_186769_ = Biomes.m_48228_("sparse_jungle");
    public static final ResourceKey<Biome> f_48197_ = Biomes.m_48228_("bamboo_jungle");
    public static final ResourceKey<Biome> f_48159_ = Biomes.m_48228_("badlands");
    public static final ResourceKey<Biome> f_48194_ = Biomes.m_48228_("eroded_badlands");
    public static final ResourceKey<Biome> f_186753_ = Biomes.m_48228_("wooded_badlands");
    public static final ResourceKey<Biome> f_186754_ = Biomes.m_48228_("meadow");
    public static final ResourceKey<Biome> f_186755_ = Biomes.m_48228_("grove");
    public static final ResourceKey<Biome> f_186756_ = Biomes.m_48228_("snowy_slopes");
    public static final ResourceKey<Biome> f_186757_ = Biomes.m_48228_("frozen_peaks");
    public static final ResourceKey<Biome> f_186758_ = Biomes.m_48228_("jagged_peaks");
    public static final ResourceKey<Biome> f_186759_ = Biomes.m_48228_("stony_peaks");
    public static final ResourceKey<Biome> f_48208_ = Biomes.m_48228_("river");
    public static final ResourceKey<Biome> f_48212_ = Biomes.m_48228_("frozen_river");
    public static final ResourceKey<Biome> f_48217_ = Biomes.m_48228_("beach");
    public static final ResourceKey<Biome> f_48148_ = Biomes.m_48228_("snowy_beach");
    public static final ResourceKey<Biome> f_186760_ = Biomes.m_48228_("stony_shore");
    public static final ResourceKey<Biome> f_48166_ = Biomes.m_48228_("warm_ocean");
    public static final ResourceKey<Biome> f_48167_ = Biomes.m_48228_("lukewarm_ocean");
    public static final ResourceKey<Biome> f_48170_ = Biomes.m_48228_("deep_lukewarm_ocean");
    public static final ResourceKey<Biome> f_48174_ = Biomes.m_48228_("ocean");
    public static final ResourceKey<Biome> f_48225_ = Biomes.m_48228_("deep_ocean");
    public static final ResourceKey<Biome> f_48168_ = Biomes.m_48228_("cold_ocean");
    public static final ResourceKey<Biome> f_48171_ = Biomes.m_48228_("deep_cold_ocean");
    public static final ResourceKey<Biome> f_48211_ = Biomes.m_48228_("frozen_ocean");
    public static final ResourceKey<Biome> f_48172_ = Biomes.m_48228_("deep_frozen_ocean");
    public static final ResourceKey<Biome> f_48215_ = Biomes.m_48228_("mushroom_fields");
    public static final ResourceKey<Biome> f_151784_ = Biomes.m_48228_("dripstone_caves");
    public static final ResourceKey<Biome> f_151785_ = Biomes.m_48228_("lush_caves");
    public static final ResourceKey<Biome> f_220594_ = Biomes.m_48228_("deep_dark");
    public static final ResourceKey<Biome> f_48209_ = Biomes.m_48228_("nether_wastes");
    public static final ResourceKey<Biome> f_48201_ = Biomes.m_48228_("warped_forest");
    public static final ResourceKey<Biome> f_48200_ = Biomes.m_48228_("crimson_forest");
    public static final ResourceKey<Biome> f_48199_ = Biomes.m_48228_("soul_sand_valley");
    public static final ResourceKey<Biome> f_48175_ = Biomes.m_48228_("basalt_deltas");
    public static final ResourceKey<Biome> f_48210_ = Biomes.m_48228_("the_end");
    public static final ResourceKey<Biome> f_48164_ = Biomes.m_48228_("end_highlands");
    public static final ResourceKey<Biome> f_48163_ = Biomes.m_48228_("end_midlands");
    public static final ResourceKey<Biome> f_48162_ = Biomes.m_48228_("small_end_islands");
    public static final ResourceKey<Biome> f_48165_ = Biomes.m_48228_("end_barrens");

    private static ResourceKey<Biome> m_48228_(String p_48229_) {
        return ResourceKey.m_135785_(Registry.f_122885_, new ResourceLocation(p_48229_));
    }
}

