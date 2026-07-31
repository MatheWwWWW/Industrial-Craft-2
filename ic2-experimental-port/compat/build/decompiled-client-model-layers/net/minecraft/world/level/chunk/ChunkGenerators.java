/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.chunk;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.DebugLevelSource;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;

public class ChunkGenerators {
    public static Codec<? extends ChunkGenerator> m_223242_(Registry<Codec<? extends ChunkGenerator>> p_223243_) {
        Registry.m_122961_(p_223243_, "noise", NoiseBasedChunkGenerator.f_64314_);
        Registry.m_122961_(p_223243_, "flat", FlatLevelSource.f_64164_);
        return Registry.m_122961_(p_223243_, "debug", DebugLevelSource.f_64111_);
    }
}

