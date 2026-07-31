/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.CheckerboardColumnBiomeSource;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.TheEndBiomeSource;

public class BiomeSources {
    public static Codec<? extends BiomeSource> m_220586_(Registry<Codec<? extends BiomeSource>> p_220587_) {
        Registry.m_122961_(p_220587_, "fixed", FixedBiomeSource.f_48251_);
        Registry.m_122961_(p_220587_, "multi_noise", MultiNoiseBiomeSource.f_48425_);
        Registry.m_122961_(p_220587_, "checkerboard", CheckerboardColumnBiomeSource.f_48230_);
        return Registry.m_122961_(p_220587_, "the_end", TheEndBiomeSource.f_48617_);
    }
}

