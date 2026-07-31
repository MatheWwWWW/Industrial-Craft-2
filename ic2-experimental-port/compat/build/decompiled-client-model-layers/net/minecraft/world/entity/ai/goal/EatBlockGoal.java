/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;

public class EatBlockGoal
extends Goal {
    private static final int f_148085_ = 40;
    private static final Predicate<BlockState> f_25201_ = BlockStatePredicate.m_61287_(Blocks.f_50034_);
    private final Mob f_25202_;
    private final Level f_25203_;
    private int f_25204_;

    public EatBlockGoal(Mob p_25207_) {
        this.f_25202_ = p_25207_;
        this.f_25203_ = p_25207_.f_19853_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean m_8036_() {
        if (this.f_25202_.m_217043_().m_188503_(this.f_25202_.m_6162_() ? 50 : 1000) != 0) {
            return false;
        }
        BlockPos $$0 = this.f_25202_.m_20183_();
        if (f_25201_.test(this.f_25203_.m_8055_($$0))) {
            return true;
        }
        return this.f_25203_.m_8055_($$0.m_7495_()).m_60713_(Blocks.f_50440_);
    }

    @Override
    public void m_8056_() {
        this.f_25204_ = this.m_183277_(40);
        this.f_25203_.m_7605_(this.f_25202_, (byte)10);
        this.f_25202_.m_21573_().m_26573_();
    }

    @Override
    public void m_8041_() {
        this.f_25204_ = 0;
    }

    @Override
    public boolean m_8045_() {
        return this.f_25204_ > 0;
    }

    public int m_25213_() {
        return this.f_25204_;
    }

    @Override
    public void m_8037_() {
        this.f_25204_ = Math.max(0, this.f_25204_ - 1);
        if (this.f_25204_ != this.m_183277_(4)) {
            return;
        }
        BlockPos $$0 = this.f_25202_.m_20183_();
        if (f_25201_.test(this.f_25203_.m_8055_($$0))) {
            if (this.f_25203_.m_46469_().m_46207_(GameRules.f_46132_)) {
                this.f_25203_.m_46961_($$0, false);
            }
            this.f_25202_.m_8035_();
        } else {
            BlockPos $$1 = $$0.m_7495_();
            if (this.f_25203_.m_8055_($$1).m_60713_(Blocks.f_50440_)) {
                if (this.f_25203_.m_46469_().m_46207_(GameRules.f_46132_)) {
                    this.f_25203_.m_46796_(2001, $$1, Block.m_49956_(Blocks.f_50440_.m_49966_()));
                    this.f_25203_.m_7731_($$1, Blocks.f_50493_.m_49966_(), 2);
                }
                this.f_25202_.m_8035_();
            }
        }
    }
}

