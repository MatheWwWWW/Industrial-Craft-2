/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class TryFindWaterGoal
extends Goal {
    private final PathfinderMob f_25962_;

    public TryFindWaterGoal(PathfinderMob p_25964_) {
        this.f_25962_ = p_25964_;
    }

    @Override
    public boolean m_8036_() {
        return this.f_25962_.m_20096_() && !this.f_25962_.f_19853_.m_6425_(this.f_25962_.m_20183_()).m_205070_(FluidTags.f_13131_);
    }

    @Override
    public void m_8056_() {
        Vec3i $$0 = null;
        Iterable<BlockPos> $$1 = BlockPos.m_121976_(Mth.m_14107_(this.f_25962_.m_20185_() - 2.0), Mth.m_14107_(this.f_25962_.m_20186_() - 2.0), Mth.m_14107_(this.f_25962_.m_20189_() - 2.0), Mth.m_14107_(this.f_25962_.m_20185_() + 2.0), this.f_25962_.m_146904_(), Mth.m_14107_(this.f_25962_.m_20189_() + 2.0));
        for (BlockPos $$2 : $$1) {
            if (!this.f_25962_.f_19853_.m_6425_($$2).m_205070_(FluidTags.f_13131_)) continue;
            $$0 = $$2;
            break;
        }
        if ($$0 != null) {
            this.f_25962_.m_21566_().m_6849_($$0.m_123341_(), $$0.m_123342_(), $$0.m_123343_(), 1.0);
        }
    }
}

