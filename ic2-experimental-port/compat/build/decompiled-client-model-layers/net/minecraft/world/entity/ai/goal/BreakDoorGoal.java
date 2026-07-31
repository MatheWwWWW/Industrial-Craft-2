/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.function.Predicate;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.DoorInteractGoal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;

public class BreakDoorGoal
extends DoorInteractGoal {
    private static final int f_148080_ = 240;
    private final Predicate<Difficulty> f_25085_;
    protected int f_25082_;
    protected int f_25083_ = -1;
    protected int f_25084_ = -1;

    public BreakDoorGoal(Mob p_25091_, Predicate<Difficulty> p_25092_) {
        super(p_25091_);
        this.f_25085_ = p_25092_;
    }

    public BreakDoorGoal(Mob p_25087_, int p_25088_, Predicate<Difficulty> p_25089_) {
        this(p_25087_, p_25089_);
        this.f_25084_ = p_25088_;
    }

    protected int m_25100_() {
        return Math.max(240, this.f_25084_);
    }

    @Override
    public boolean m_8036_() {
        if (!super.m_8036_()) {
            return false;
        }
        if (!this.f_25189_.f_19853_.m_46469_().m_46207_(GameRules.f_46132_)) {
            return false;
        }
        return this.m_25094_(this.f_25189_.f_19853_.m_46791_()) && !this.m_25200_();
    }

    @Override
    public void m_8056_() {
        super.m_8056_();
        this.f_25082_ = 0;
    }

    @Override
    public boolean m_8045_() {
        return this.f_25082_ <= this.m_25100_() && !this.m_25200_() && this.f_25190_.m_203195_(this.f_25189_.m_20182_(), 2.0) && this.m_25094_(this.f_25189_.f_19853_.m_46791_());
    }

    @Override
    public void m_8041_() {
        super.m_8041_();
        this.f_25189_.f_19853_.m_6801_(this.f_25189_.m_19879_(), this.f_25190_, -1);
    }

    @Override
    public void m_8037_() {
        super.m_8037_();
        if (this.f_25189_.m_217043_().m_188503_(20) == 0) {
            this.f_25189_.f_19853_.m_46796_(1019, this.f_25190_, 0);
            if (!this.f_25189_.f_20911_) {
                this.f_25189_.m_6674_(this.f_25189_.m_7655_());
            }
        }
        ++this.f_25082_;
        int $$0 = (int)((float)this.f_25082_ / (float)this.m_25100_() * 10.0f);
        if ($$0 != this.f_25083_) {
            this.f_25189_.f_19853_.m_6801_(this.f_25189_.m_19879_(), this.f_25190_, $$0);
            this.f_25083_ = $$0;
        }
        if (this.f_25082_ == this.m_25100_() && this.m_25094_(this.f_25189_.f_19853_.m_46791_())) {
            this.f_25189_.f_19853_.m_7471_(this.f_25190_, false);
            this.f_25189_.f_19853_.m_46796_(1021, this.f_25190_, 0);
            this.f_25189_.f_19853_.m_46796_(2001, this.f_25190_, Block.m_49956_(this.f_25189_.f_19853_.m_8055_(this.f_25190_)));
        }
    }

    private boolean m_25094_(Difficulty p_25095_) {
        return this.f_25085_.test(p_25095_);
    }
}

