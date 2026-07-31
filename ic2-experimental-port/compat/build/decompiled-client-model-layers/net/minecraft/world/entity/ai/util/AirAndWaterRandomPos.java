/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.util;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.phys.Vec3;

public class AirAndWaterRandomPos {
    @Nullable
    public static Vec3 m_148357_(PathfinderMob p_148358_, int p_148359_, int p_148360_, int p_148361_, double p_148362_, double p_148363_, double p_148364_) {
        boolean $$7 = GoalUtils.m_148442_(p_148358_, p_148359_);
        return RandomPos.m_148542_(p_148358_, () -> AirAndWaterRandomPos.m_148365_(p_148358_, p_148359_, p_148360_, p_148361_, p_148362_, p_148363_, p_148364_, $$7));
    }

    @Nullable
    public static BlockPos m_148365_(PathfinderMob p_148366_, int p_148367_, int p_148368_, int p_148369_, double p_148370_, double p_148371_, double p_148372_, boolean p_148373_) {
        BlockPos $$8 = RandomPos.m_217855_(p_148366_.m_217043_(), p_148367_, p_148368_, p_148369_, p_148370_, p_148371_, p_148372_);
        if ($$8 == null) {
            return null;
        }
        BlockPos $$9 = RandomPos.m_217863_(p_148366_, p_148367_, p_148366_.m_217043_(), $$8);
        if (GoalUtils.m_148451_($$9, p_148366_) || GoalUtils.m_148454_(p_148373_, p_148366_, $$9)) {
            return null;
        }
        if (GoalUtils.m_148458_(p_148366_, $$9 = RandomPos.m_148545_($$9, p_148366_.f_19853_.m_151558_(), p_148376_ -> GoalUtils.m_148461_(p_148366_, p_148376_)))) {
            return null;
        }
        return $$9;
    }
}

