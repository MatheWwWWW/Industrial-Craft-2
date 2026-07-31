/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.world.level.Level
 */
package ic2.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;

public class ParticleUtil {
    public static void showFurnaceFlames(Level level, BlockPos blockPos, Direction direction) {
        if (level.f_46441_.m_188503_(8) != 0) {
            return;
        }
        double d = (double)blockPos.m_123341_() + ((double)direction.m_122429_() * 1.04 + 1.0) / 2.0;
        double d2 = (double)blockPos.m_123342_() + (double)level.f_46441_.m_188501_() * 0.375;
        double d3 = (double)blockPos.m_123343_() + ((double)direction.m_122431_() * 1.04 + 1.0) / 2.0;
        if (direction.m_122434_() == Direction.Axis.X) {
            d3 += (double)level.f_46441_.m_188501_() * 0.625 - 0.3125;
        } else {
            d += (double)level.f_46441_.m_188501_() * 0.625 - 0.3125;
        }
        level.m_7106_((ParticleOptions)ParticleTypes.f_123762_, d, d2, d3, 0.0, 0.0, 0.0);
        level.m_7106_((ParticleOptions)ParticleTypes.f_123744_, d, d2, d3, 0.0, 0.0, 0.0);
    }
}

