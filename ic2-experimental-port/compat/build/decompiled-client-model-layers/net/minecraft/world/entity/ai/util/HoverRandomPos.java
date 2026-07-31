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
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.phys.Vec3;

public class HoverRandomPos {
    @Nullable
    public static Vec3 m_148465_(PathfinderMob p_148466_, int p_148467_, int p_148468_, double p_148469_, double p_148470_, float p_148471_, int p_148472_, int p_148473_) {
        boolean $$8 = GoalUtils.m_148442_(p_148466_, p_148467_);
        return RandomPos.m_148542_(p_148466_, () -> {
            BlockPos $$9 = RandomPos.m_217855_(p_148466_.m_217043_(), p_148467_, p_148468_, 0, p_148469_, p_148470_, p_148471_);
            if ($$9 == null) {
                return null;
            }
            BlockPos $$10 = LandRandomPos.m_148513_(p_148466_, p_148467_, $$8, $$9);
            if ($$10 == null) {
                return null;
            }
            if (GoalUtils.m_148445_(p_148466_, $$10 = RandomPos.m_26947_($$10, p_148466_.m_217043_().m_188503_(p_148472_ - p_148473_ + 1) + p_148473_, p_148475_.f_19853_.m_151558_(), p_148486_ -> GoalUtils.m_148461_(p_148466_, p_148486_))) || GoalUtils.m_148458_(p_148466_, $$10)) {
                return null;
            }
            return $$10;
        });
    }
}

