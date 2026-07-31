/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class FollowOwnerGoal
extends Goal {
    public static final int f_148087_ = 12;
    private static final int f_148088_ = 2;
    private static final int f_148089_ = 3;
    private static final int f_148090_ = 1;
    private final TamableAnimal f_25283_;
    private LivingEntity f_25284_;
    private final LevelReader f_25285_;
    private final double f_25286_;
    private final PathNavigation f_25287_;
    private int f_25288_;
    private final float f_25289_;
    private final float f_25290_;
    private float f_25291_;
    private final boolean f_25292_;

    public FollowOwnerGoal(TamableAnimal p_25294_, double p_25295_, float p_25296_, float p_25297_, boolean p_25298_) {
        this.f_25283_ = p_25294_;
        this.f_25285_ = p_25294_.f_19853_;
        this.f_25286_ = p_25295_;
        this.f_25287_ = p_25294_.m_21573_();
        this.f_25290_ = p_25296_;
        this.f_25289_ = p_25297_;
        this.f_25292_ = p_25298_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (!(p_25294_.m_21573_() instanceof GroundPathNavigation) && !(p_25294_.m_21573_() instanceof FlyingPathNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    @Override
    public boolean m_8036_() {
        LivingEntity $$0 = this.f_25283_.m_21826_();
        if ($$0 == null) {
            return false;
        }
        if ($$0.m_5833_()) {
            return false;
        }
        if (this.f_25283_.m_21827_()) {
            return false;
        }
        if (this.f_25283_.m_20280_($$0) < (double)(this.f_25290_ * this.f_25290_)) {
            return false;
        }
        this.f_25284_ = $$0;
        return true;
    }

    @Override
    public boolean m_8045_() {
        if (this.f_25287_.m_26571_()) {
            return false;
        }
        if (this.f_25283_.m_21827_()) {
            return false;
        }
        return !(this.f_25283_.m_20280_(this.f_25284_) <= (double)(this.f_25289_ * this.f_25289_));
    }

    @Override
    public void m_8056_() {
        this.f_25288_ = 0;
        this.f_25291_ = this.f_25283_.m_21439_(BlockPathTypes.WATER);
        this.f_25283_.m_21441_(BlockPathTypes.WATER, 0.0f);
    }

    @Override
    public void m_8041_() {
        this.f_25284_ = null;
        this.f_25287_.m_26573_();
        this.f_25283_.m_21441_(BlockPathTypes.WATER, this.f_25291_);
    }

    @Override
    public void m_8037_() {
        this.f_25283_.m_21563_().m_24960_(this.f_25284_, 10.0f, this.f_25283_.m_8132_());
        if (--this.f_25288_ > 0) {
            return;
        }
        this.f_25288_ = this.m_183277_(10);
        if (this.f_25283_.m_21523_() || this.f_25283_.m_20159_()) {
            return;
        }
        if (this.f_25283_.m_20280_(this.f_25284_) >= 144.0) {
            this.m_25313_();
        } else {
            this.f_25287_.m_5624_(this.f_25284_, this.f_25286_);
        }
    }

    private void m_25313_() {
        BlockPos $$0 = this.f_25284_.m_20183_();
        for (int $$1 = 0; $$1 < 10; ++$$1) {
            int $$2 = this.m_25300_(-3, 3);
            int $$3 = this.m_25300_(-1, 1);
            int $$4 = this.m_25300_(-3, 3);
            boolean $$5 = this.m_25303_($$0.m_123341_() + $$2, $$0.m_123342_() + $$3, $$0.m_123343_() + $$4);
            if (!$$5) continue;
            return;
        }
    }

    private boolean m_25303_(int p_25304_, int p_25305_, int p_25306_) {
        if (Math.abs((double)p_25304_ - this.f_25284_.m_20185_()) < 2.0 && Math.abs((double)p_25306_ - this.f_25284_.m_20189_()) < 2.0) {
            return false;
        }
        if (!this.m_25307_(new BlockPos(p_25304_, p_25305_, p_25306_))) {
            return false;
        }
        this.f_25283_.m_7678_((double)p_25304_ + 0.5, p_25305_, (double)p_25306_ + 0.5, this.f_25283_.m_146908_(), this.f_25283_.m_146909_());
        this.f_25287_.m_26573_();
        return true;
    }

    private boolean m_25307_(BlockPos p_25308_) {
        BlockPathTypes $$1 = WalkNodeEvaluator.m_77604_(this.f_25285_, p_25308_.m_122032_());
        if ($$1 != BlockPathTypes.WALKABLE) {
            return false;
        }
        BlockState $$2 = this.f_25285_.m_8055_(p_25308_.m_7495_());
        if (!this.f_25292_ && $$2.m_60734_() instanceof LeavesBlock) {
            return false;
        }
        BlockPos $$3 = p_25308_.m_121996_(this.f_25283_.m_20183_());
        return this.f_25285_.m_45756_(this.f_25283_, this.f_25283_.m_20191_().m_82338_($$3));
    }

    private int m_25300_(int p_25301_, int p_25302_) {
        return this.f_25283_.m_217043_().m_188503_(p_25302_ - p_25301_ + 1) + p_25301_;
    }
}

