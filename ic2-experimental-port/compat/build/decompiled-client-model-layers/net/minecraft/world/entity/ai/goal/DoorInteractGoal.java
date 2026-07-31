/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

public abstract class DoorInteractGoal
extends Goal {
    protected Mob f_25189_;
    protected BlockPos f_25190_ = BlockPos.f_121853_;
    protected boolean f_25191_;
    private boolean f_25186_;
    private float f_25187_;
    private float f_25188_;

    public DoorInteractGoal(Mob p_25193_) {
        this.f_25189_ = p_25193_;
        if (!GoalUtils.m_26894_(p_25193_)) {
            throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
        }
    }

    protected boolean m_25200_() {
        if (!this.f_25191_) {
            return false;
        }
        BlockState $$0 = this.f_25189_.f_19853_.m_8055_(this.f_25190_);
        if (!($$0.m_60734_() instanceof DoorBlock)) {
            this.f_25191_ = false;
            return false;
        }
        return $$0.m_61143_(DoorBlock.f_52727_);
    }

    protected void m_25195_(boolean p_25196_) {
        BlockState $$1;
        if (this.f_25191_ && ($$1 = this.f_25189_.f_19853_.m_8055_(this.f_25190_)).m_60734_() instanceof DoorBlock) {
            ((DoorBlock)$$1.m_60734_()).m_153165_(this.f_25189_, this.f_25189_.f_19853_, $$1, this.f_25190_, p_25196_);
        }
    }

    @Override
    public boolean m_8036_() {
        if (!GoalUtils.m_26894_(this.f_25189_)) {
            return false;
        }
        if (!this.f_25189_.f_19862_) {
            return false;
        }
        GroundPathNavigation $$0 = (GroundPathNavigation)this.f_25189_.m_21573_();
        Path $$1 = $$0.m_26570_();
        if ($$1 == null || $$1.m_77392_() || !$$0.m_26492_()) {
            return false;
        }
        for (int $$2 = 0; $$2 < Math.min($$1.m_77399_() + 2, $$1.m_77398_()); ++$$2) {
            Node $$3 = $$1.m_77375_($$2);
            this.f_25190_ = new BlockPos($$3.f_77271_, $$3.f_77272_ + 1, $$3.f_77273_);
            if (this.f_25189_.m_20275_(this.f_25190_.m_123341_(), this.f_25189_.m_20186_(), this.f_25190_.m_123343_()) > 2.25) continue;
            this.f_25191_ = DoorBlock.m_52745_(this.f_25189_.f_19853_, this.f_25190_);
            if (!this.f_25191_) continue;
            return true;
        }
        this.f_25190_ = this.f_25189_.m_20183_().m_7494_();
        this.f_25191_ = DoorBlock.m_52745_(this.f_25189_.f_19853_, this.f_25190_);
        return this.f_25191_;
    }

    @Override
    public boolean m_8045_() {
        return !this.f_25186_;
    }

    @Override
    public void m_8056_() {
        this.f_25186_ = false;
        this.f_25187_ = (float)((double)this.f_25190_.m_123341_() + 0.5 - this.f_25189_.m_20185_());
        this.f_25188_ = (float)((double)this.f_25190_.m_123343_() + 0.5 - this.f_25189_.m_20189_());
    }

    @Override
    public boolean m_183429_() {
        return true;
    }

    @Override
    public void m_8037_() {
        float $$1;
        float $$0 = (float)((double)this.f_25190_.m_123341_() + 0.5 - this.f_25189_.m_20185_());
        float $$2 = this.f_25187_ * $$0 + this.f_25188_ * ($$1 = (float)((double)this.f_25190_.m_123343_() + 0.5 - this.f_25189_.m_20189_()));
        if ($$2 < 0.0f) {
            this.f_25186_ = true;
        }
    }
}

