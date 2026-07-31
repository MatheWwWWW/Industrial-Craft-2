/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.biome.Biome;

public class BiomeColors {
    public static final ColorResolver f_108789_ = Biome::m_47464_;
    public static final ColorResolver f_108790_ = (p_108808_, p_108809_, p_108810_) -> p_108808_.m_47542_();
    public static final ColorResolver f_108791_ = (p_108801_, p_108802_, p_108803_) -> p_108801_.m_47560_();

    private static int m_108796_(BlockAndTintGetter p_108797_, BlockPos p_108798_, ColorResolver p_108799_) {
        return p_108797_.m_6171_(p_108798_, p_108799_);
    }

    public static int m_108793_(BlockAndTintGetter p_108794_, BlockPos p_108795_) {
        return BiomeColors.m_108796_(p_108794_, p_108795_, f_108789_);
    }

    public static int m_108804_(BlockAndTintGetter p_108805_, BlockPos p_108806_) {
        return BiomeColors.m_108796_(p_108805_, p_108806_, f_108790_);
    }

    public static int m_108811_(BlockAndTintGetter p_108812_, BlockPos p_108813_) {
        return BiomeColors.m_108796_(p_108812_, p_108813_, f_108791_);
    }
}

