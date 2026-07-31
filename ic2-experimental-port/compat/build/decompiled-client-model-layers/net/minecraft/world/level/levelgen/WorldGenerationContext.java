/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.levelgen;

import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;

public class WorldGenerationContext {
    private final int f_182504_;
    private final int f_182505_;

    public WorldGenerationContext(ChunkGenerator p_182507_, LevelHeightAccessor p_182508_) {
        this.f_182504_ = Math.max(p_182508_.m_141937_(), p_182507_.m_142062_());
        this.f_182505_ = Math.min(p_182508_.m_141928_(), p_182507_.m_6331_());
    }

    public int m_142201_() {
        return this.f_182504_;
    }

    public int m_142208_() {
        return this.f_182505_;
    }
}

