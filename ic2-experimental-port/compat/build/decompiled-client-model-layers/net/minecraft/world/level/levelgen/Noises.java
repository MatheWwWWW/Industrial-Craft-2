/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class Noises {
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189269_ = Noises.m_189309_("temperature");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189278_ = Noises.m_189309_("vegetation");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189279_ = Noises.m_189309_("continentalness");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189280_ = Noises.m_189309_("erosion");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189281_ = Noises.m_189309_("temperature_large");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189282_ = Noises.m_189309_("vegetation_large");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189283_ = Noises.m_189309_("continentalness_large");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189284_ = Noises.m_189309_("erosion_large");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189285_ = Noises.m_189309_("ridge");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189286_ = Noises.m_189309_("offset");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189287_ = Noises.m_189309_("aquifer_barrier");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189288_ = Noises.m_189309_("aquifer_fluid_level_floodedness");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189289_ = Noises.m_189309_("aquifer_lava");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189290_ = Noises.m_189309_("aquifer_fluid_level_spread");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189291_ = Noises.m_189309_("pillar");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189292_ = Noises.m_189309_("pillar_rareness");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189293_ = Noises.m_189309_("pillar_thickness");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189294_ = Noises.m_189309_("spaghetti_2d");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189295_ = Noises.m_189309_("spaghetti_2d_elevation");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189296_ = Noises.m_189309_("spaghetti_2d_modulator");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189297_ = Noises.m_189309_("spaghetti_2d_thickness");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189298_ = Noises.m_189309_("spaghetti_3d_1");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189299_ = Noises.m_189309_("spaghetti_3d_2");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189300_ = Noises.m_189309_("spaghetti_3d_rarity");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189301_ = Noises.m_189309_("spaghetti_3d_thickness");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189302_ = Noises.m_189309_("spaghetti_roughness");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189243_ = Noises.m_189309_("spaghetti_roughness_modulator");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189244_ = Noises.m_189309_("cave_entrance");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189245_ = Noises.m_189309_("cave_layer");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189246_ = Noises.m_189309_("cave_cheese");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189247_ = Noises.m_189309_("ore_veininess");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189248_ = Noises.m_189309_("ore_vein_a");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189249_ = Noises.m_189309_("ore_vein_b");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189250_ = Noises.m_189309_("ore_gap");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189251_ = Noises.m_189309_("noodle");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189252_ = Noises.m_189309_("noodle_thickness");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189253_ = Noises.m_189309_("noodle_ridge_a");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189254_ = Noises.m_189309_("noodle_ridge_b");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189255_ = Noises.m_189309_("jagged");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189256_ = Noises.m_189309_("surface");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189257_ = Noises.m_189309_("surface_secondary");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189258_ = Noises.m_189309_("clay_bands_offset");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189259_ = Noises.m_189309_("badlands_pillar");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189260_ = Noises.m_189309_("badlands_pillar_roof");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189261_ = Noises.m_189309_("badlands_surface");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189262_ = Noises.m_189309_("iceberg_pillar");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189263_ = Noises.m_189309_("iceberg_pillar_roof");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189264_ = Noises.m_189309_("iceberg_surface");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189265_ = Noises.m_189309_("surface_swamp");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189266_ = Noises.m_189309_("calcite");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189267_ = Noises.m_189309_("gravel");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189268_ = Noises.m_189309_("powder_snow");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189270_ = Noises.m_189309_("packed_ice");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189271_ = Noises.m_189309_("ice");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189272_ = Noises.m_189309_("soul_sand_layer");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189273_ = Noises.m_189309_("gravel_layer");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189274_ = Noises.m_189309_("patch");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189275_ = Noises.m_189309_("netherrack");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189276_ = Noises.m_189309_("nether_wart");
    public static final ResourceKey<NormalNoise.NoiseParameters> f_189277_ = Noises.m_189309_("nether_state_selector");

    private static ResourceKey<NormalNoise.NoiseParameters> m_189309_(String p_189310_) {
        return ResourceKey.m_135785_(Registry.f_194568_, new ResourceLocation(p_189310_));
    }

    public static NormalNoise m_189305_(Registry<NormalNoise.NoiseParameters> p_189306_, PositionalRandomFactory p_189307_, ResourceKey<NormalNoise.NoiseParameters> p_189308_) {
        Holder<NormalNoise.NoiseParameters> $$3 = p_189306_.m_206081_(p_189308_);
        return NormalNoise.m_230511_(p_189307_.m_224540_($$3.m_203543_().orElseThrow().m_135782_()), $$3.m_203334_());
    }
}

