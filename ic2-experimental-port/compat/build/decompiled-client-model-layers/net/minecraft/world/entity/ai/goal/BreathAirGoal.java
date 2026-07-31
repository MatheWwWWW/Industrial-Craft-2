/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

public class BreathAirGoal
extends Goal {
    private final PathfinderMob f_25101_;

    public BreathAirGoal(PathfinderMob p_25103_) {
        this.f_25101_ = p_25103_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean m_8036_() {
        return this.f_25101_.m_20146_() < 140;
    }

    @Override
    public boolean m_8045_() {
        return this.m_8036_();
    }

    @Override
    public boolean m_6767_() {
        return false;
    }

    @Override
    public void m_8056_() {
        this.m_25112_();
    }

    private void m_25112_() {
        Iterable<BlockPos> $$0 = BlockPos.m_121976_(Mth.m_14107_(this.f_25101_.m_20185_() - 1.0), this.f_25101_.m_146904_(), Mth.m_14107_(this.f_25101_.m_20189_() - 1.0), Mth.m_14107_(this.f_25101_.m_20185_() + 1.0), Mth.m_14107_(this.f_25101_.m_20186_() + 8.0), Mth.m_14107_(this.f_25101_.m_20189_() + 1.0));
        Vec3i $$1 = null;
        for (BlockPos $$2 : $$0) {
            if (!this.m_25106_(this.f_25101_.f_19853_, $$2)) continue;
            $$1 = $$2;
            break;
        }
        if ($$1 == null) {
            $$1 = new BlockPos(this.f_25101_.m_20185_(), this.f_25101_.m_20186_() + 8.0, this.f_25101_.m_20189_());
        }
        this.f_25101_.m_21573_().m_26519_($$1.m_123341_(), $$1.m_123342_() + 1, $$1.m_123343_(), 1.0);
    }

    @Override
    public void m_8037_() {
        this.m_25112_();
        this.f_25101_.m_19920_(0.02f, new Vec3(this.f_25101_.f_20900_, this.f_25101_.f_20901_, this.f_25101_.f_20902_));
        this.f_25101_.m_6478_(MoverType.SELF, this.f_25101_.m_20184_());
    }

    private boolean m_25106_(LevelReader p_25107_, BlockPos p_25108_) {
        BlockState $$2 = p_25107_.m_8055_(p_25108_);
        return (p_25107_.m_6425_(p_25108_).m_76178_() || $$2.m_60713_(Blocks.f_50628_)) && $$2.m_60647_(p_25107_, p_25108_, PathComputationType.LAND);
    }
}

