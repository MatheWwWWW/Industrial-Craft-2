/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.LevelReader;

public abstract class MoveToBlockGoal
extends Goal {
    private static final int f_148128_ = 1200;
    private static final int f_148129_ = 1200;
    private static final int f_148130_ = 200;
    protected final PathfinderMob f_25598_;
    public final double f_25599_;
    protected int f_25600_;
    protected int f_25601_;
    private int f_25604_;
    protected BlockPos f_25602_ = BlockPos.f_121853_;
    private boolean f_25605_;
    private final int f_25606_;
    private final int f_25607_;
    protected int f_25603_;

    public MoveToBlockGoal(PathfinderMob p_25609_, double p_25610_, int p_25611_) {
        this(p_25609_, p_25610_, p_25611_, 1);
    }

    public MoveToBlockGoal(PathfinderMob p_25613_, double p_25614_, int p_25615_, int p_25616_) {
        this.f_25598_ = p_25613_;
        this.f_25599_ = p_25614_;
        this.f_25606_ = p_25615_;
        this.f_25603_ = 0;
        this.f_25607_ = p_25616_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    @Override
    public boolean m_8036_() {
        if (this.f_25600_ > 0) {
            --this.f_25600_;
            return false;
        }
        this.f_25600_ = this.m_6099_(this.f_25598_);
        return this.m_25626_();
    }

    protected int m_6099_(PathfinderMob p_25618_) {
        return MoveToBlockGoal.m_186073_(200 + p_25618_.m_217043_().m_188503_(200));
    }

    @Override
    public boolean m_8045_() {
        return this.f_25601_ >= -this.f_25604_ && this.f_25601_ <= 1200 && this.m_6465_(this.f_25598_.f_19853_, this.f_25602_);
    }

    @Override
    public void m_8056_() {
        this.m_25624_();
        this.f_25601_ = 0;
        this.f_25604_ = this.f_25598_.m_217043_().m_188503_(this.f_25598_.m_217043_().m_188503_(1200) + 1200) + 1200;
    }

    protected void m_25624_() {
        this.f_25598_.m_21573_().m_26519_((double)this.f_25602_.m_123341_() + 0.5, this.f_25602_.m_123342_() + 1, (double)this.f_25602_.m_123343_() + 0.5, this.f_25599_);
    }

    public double m_8052_() {
        return 1.0;
    }

    protected BlockPos m_6669_() {
        return this.f_25602_.m_7494_();
    }

    @Override
    public boolean m_183429_() {
        return true;
    }

    @Override
    public void m_8037_() {
        BlockPos $$0 = this.m_6669_();
        if (!$$0.m_203195_(this.f_25598_.m_20182_(), this.m_8052_())) {
            this.f_25605_ = false;
            ++this.f_25601_;
            if (this.m_8064_()) {
                this.f_25598_.m_21573_().m_26519_((double)$$0.m_123341_() + 0.5, $$0.m_123342_(), (double)$$0.m_123343_() + 0.5, this.f_25599_);
            }
        } else {
            this.f_25605_ = true;
            --this.f_25601_;
        }
    }

    public boolean m_8064_() {
        return this.f_25601_ % 40 == 0;
    }

    protected boolean m_25625_() {
        return this.f_25605_;
    }

    protected boolean m_25626_() {
        int $$0 = this.f_25606_;
        int $$1 = this.f_25607_;
        BlockPos $$2 = this.f_25598_.m_20183_();
        BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();
        int $$4 = this.f_25603_;
        while ($$4 <= $$1) {
            for (int $$5 = 0; $$5 < $$0; ++$$5) {
                int $$6 = 0;
                while ($$6 <= $$5) {
                    int $$7;
                    int n = $$7 = $$6 < $$5 && $$6 > -$$5 ? $$5 : 0;
                    while ($$7 <= $$5) {
                        $$3.m_122154_($$2, $$6, $$4 - 1, $$7);
                        if (this.f_25598_.m_21444_($$3) && this.m_6465_(this.f_25598_.f_19853_, $$3)) {
                            this.f_25602_ = $$3;
                            return true;
                        }
                        $$7 = $$7 > 0 ? -$$7 : 1 - $$7;
                    }
                    $$6 = $$6 > 0 ? -$$6 : 1 - $$6;
                }
            }
            $$4 = $$4 > 0 ? -$$4 : 1 - $$4;
        }
        return false;
    }

    protected abstract boolean m_6465_(LevelReader var1, BlockPos var2);
}

