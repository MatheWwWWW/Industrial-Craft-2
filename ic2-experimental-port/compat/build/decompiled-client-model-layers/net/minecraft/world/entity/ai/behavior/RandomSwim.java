/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.behavior;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.phys.Vec3;

public class RandomSwim
extends RandomStroll {
    public static final int[][] f_182355_ = new int[][]{{1, 1}, {3, 3}, {5, 5}, {6, 5}, {7, 7}, {10, 7}};

    public RandomSwim(float p_147853_) {
        super(p_147853_);
    }

    @Override
    protected boolean m_6114_(ServerLevel p_147858_, PathfinderMob p_147859_) {
        return p_147859_.m_20072_();
    }

    @Override
    @Nullable
    protected Vec3 m_142622_(PathfinderMob p_147861_) {
        Vec3 $$1 = null;
        Vec3 $$2 = null;
        for (int[] $$3 : f_182355_) {
            $$2 = $$1 == null ? BehaviorUtils.m_147444_(p_147861_, $$3[0], $$3[1]) : p_147861_.m_20182_().m_82549_(p_147861_.m_20182_().m_82505_($$1).m_82541_().m_82542_($$3[0], $$3[1], $$3[0]));
            if ($$2 == null || p_147861_.f_19853_.m_6425_(new BlockPos($$2)).m_76178_()) {
                return $$1;
            }
            $$1 = $$2;
        }
        return $$2;
    }
}

