/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class MoveBackToVillageGoal
extends RandomStrollGoal {
    private static final int f_148126_ = 10;
    private static final int f_148127_ = 7;

    public MoveBackToVillageGoal(PathfinderMob p_25568_, double p_25569_, boolean p_25570_) {
        super(p_25568_, p_25569_, 10, p_25570_);
    }

    @Override
    public boolean m_8036_() {
        ServerLevel $$0 = (ServerLevel)this.f_25725_.f_19853_;
        BlockPos $$1 = this.f_25725_.m_20183_();
        if ($$0.m_8802_($$1)) {
            return false;
        }
        return super.m_8036_();
    }

    @Override
    @Nullable
    protected Vec3 m_7037_() {
        ServerLevel $$0 = (ServerLevel)this.f_25725_.f_19853_;
        BlockPos $$1 = this.f_25725_.m_20183_();
        SectionPos $$2 = SectionPos.m_123199_($$1);
        SectionPos $$3 = BehaviorUtils.m_22581_($$0, $$2, 2);
        if ($$3 != $$2) {
            return DefaultRandomPos.m_148412_(this.f_25725_, 10, 7, Vec3.m_82539_($$3.m_123250_()), 1.5707963705062866);
        }
        return null;
    }
}

