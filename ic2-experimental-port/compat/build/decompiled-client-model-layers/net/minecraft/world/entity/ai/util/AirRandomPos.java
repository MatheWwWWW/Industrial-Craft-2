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
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.phys.Vec3;

public class AirRandomPos {
    @Nullable
    public static Vec3 m_148387_(PathfinderMob p_148388_, int p_148389_, int p_148390_, int p_148391_, Vec3 p_148392_, double p_148393_) {
        Vec3 $$6 = p_148392_.m_82492_(p_148388_.m_20185_(), p_148388_.m_20186_(), p_148388_.m_20189_());
        boolean $$7 = GoalUtils.m_148442_(p_148388_, p_148389_);
        return RandomPos.m_148542_(p_148388_, () -> {
            BlockPos $$7 = AirAndWaterRandomPos.m_148365_(p_148388_, p_148389_, p_148390_, p_148391_, p_148399_.f_82479_, p_148399_.f_82481_, p_148393_, $$7);
            if ($$7 == null || GoalUtils.m_148445_(p_148388_, $$7)) {
                return null;
            }
            return $$7;
        });
    }
}

