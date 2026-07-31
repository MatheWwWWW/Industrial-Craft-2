/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class GoalUtils {
    public static boolean m_26894_(Mob p_26895_) {
        return p_26895_.m_21573_() instanceof GroundPathNavigation;
    }

    public static boolean m_148442_(PathfinderMob p_148443_, int p_148444_) {
        return p_148443_.m_21536_() && p_148443_.m_21534_().m_203195_(p_148443_.m_20182_(), (double)(p_148443_.m_21535_() + (float)p_148444_) + 1.0);
    }

    public static boolean m_148451_(BlockPos p_148452_, PathfinderMob p_148453_) {
        return p_148452_.m_123342_() < p_148453_.f_19853_.m_141937_() || p_148452_.m_123342_() > p_148453_.f_19853_.m_151558_();
    }

    public static boolean m_148454_(boolean p_148455_, PathfinderMob p_148456_, BlockPos p_148457_) {
        return p_148455_ && !p_148456_.m_21444_(p_148457_);
    }

    public static boolean m_148448_(PathNavigation p_148449_, BlockPos p_148450_) {
        return !p_148449_.m_6342_(p_148450_);
    }

    public static boolean m_148445_(PathfinderMob p_148446_, BlockPos p_148447_) {
        return p_148446_.f_19853_.m_6425_(p_148447_).m_205070_(FluidTags.f_13131_);
    }

    public static boolean m_148458_(PathfinderMob p_148459_, BlockPos p_148460_) {
        return p_148459_.m_21439_(WalkNodeEvaluator.m_77604_(p_148459_.f_19853_, p_148460_.m_122032_())) != 0.0f;
    }

    public static boolean m_148461_(PathfinderMob p_148462_, BlockPos p_148463_) {
        return p_148462_.f_19853_.m_8055_(p_148463_).m_60767_().m_76333_();
    }
}

