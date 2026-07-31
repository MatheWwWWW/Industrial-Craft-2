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

public class DefaultRandomPos {
    @Nullable
    public static Vec3 m_148403_(PathfinderMob p_148404_, int p_148405_, int p_148406_) {
        boolean $$3 = GoalUtils.m_148442_(p_148404_, p_148405_);
        return RandomPos.m_148542_(p_148404_, () -> {
            BlockPos $$4 = RandomPos.m_217851_(p_148404_.m_217043_(), p_148405_, p_148406_);
            return DefaultRandomPos.m_148436_(p_148404_, p_148405_, $$3, $$4);
        });
    }

    @Nullable
    public static Vec3 m_148412_(PathfinderMob p_148413_, int p_148414_, int p_148415_, Vec3 p_148416_, double p_148417_) {
        Vec3 $$5 = p_148416_.m_82492_(p_148413_.m_20185_(), p_148413_.m_20186_(), p_148413_.m_20189_());
        boolean $$6 = GoalUtils.m_148442_(p_148413_, p_148414_);
        return RandomPos.m_148542_(p_148413_, () -> {
            BlockPos $$6 = RandomPos.m_217855_(p_148413_.m_217043_(), p_148414_, p_148415_, 0, p_242780_.f_82479_, p_242780_.f_82481_, p_148417_);
            if ($$6 == null) {
                return null;
            }
            return DefaultRandomPos.m_148436_(p_148413_, p_148414_, $$6, $$6);
        });
    }

    @Nullable
    public static Vec3 m_148407_(PathfinderMob p_148408_, int p_148409_, int p_148410_, Vec3 p_148411_) {
        Vec3 $$4 = p_148408_.m_20182_().m_82546_(p_148411_);
        boolean $$5 = GoalUtils.m_148442_(p_148408_, p_148409_);
        return RandomPos.m_148542_(p_148408_, () -> {
            BlockPos $$5 = RandomPos.m_217855_(p_148408_.m_217043_(), p_148409_, p_148410_, 0, p_242786_.f_82479_, p_242786_.f_82481_, 1.5707963705062866);
            if ($$5 == null) {
                return null;
            }
            return DefaultRandomPos.m_148436_(p_148408_, p_148409_, $$5, $$5);
        });
    }

    @Nullable
    private static BlockPos m_148436_(PathfinderMob p_148437_, int p_148438_, boolean p_148439_, BlockPos p_148440_) {
        BlockPos $$4 = RandomPos.m_217863_(p_148437_, p_148438_, p_148437_.m_217043_(), p_148440_);
        if (GoalUtils.m_148451_($$4, p_148437_) || GoalUtils.m_148454_(p_148439_, p_148437_, $$4) || GoalUtils.m_148448_(p_148437_.m_21573_(), $$4) || GoalUtils.m_148458_(p_148437_, $$4)) {
            return null;
        }
        return $$4;
    }
}

