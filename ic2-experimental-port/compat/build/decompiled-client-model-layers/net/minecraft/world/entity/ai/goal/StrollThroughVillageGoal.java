/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class StrollThroughVillageGoal
extends Goal {
    private static final int f_148136_ = 10;
    private final PathfinderMob f_25903_;
    private final int f_25904_;
    @Nullable
    private BlockPos f_25905_;

    public StrollThroughVillageGoal(PathfinderMob p_25907_, int p_25908_) {
        this.f_25903_ = p_25907_;
        this.f_25904_ = StrollThroughVillageGoal.m_186073_(p_25908_);
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean m_8036_() {
        if (this.f_25903_.m_20160_()) {
            return false;
        }
        if (this.f_25903_.f_19853_.m_46461_()) {
            return false;
        }
        if (this.f_25903_.m_217043_().m_188503_(this.f_25904_) != 0) {
            return false;
        }
        ServerLevel $$0 = (ServerLevel)this.f_25903_.f_19853_;
        BlockPos $$1 = this.f_25903_.m_20183_();
        if (!$$0.m_8736_($$1, 6)) {
            return false;
        }
        Vec3 $$2 = LandRandomPos.m_148503_(this.f_25903_, 15, 7, p_25912_ -> -$$0.m_8828_(SectionPos.m_123199_(p_25912_)));
        this.f_25905_ = $$2 == null ? null : new BlockPos($$2);
        return this.f_25905_ != null;
    }

    @Override
    public boolean m_8045_() {
        return this.f_25905_ != null && !this.f_25903_.m_21573_().m_26571_() && this.f_25903_.m_21573_().m_26567_().equals(this.f_25905_);
    }

    @Override
    public void m_8037_() {
        if (this.f_25905_ == null) {
            return;
        }
        PathNavigation $$0 = this.f_25903_.m_21573_();
        if ($$0.m_26571_() && !this.f_25905_.m_203195_(this.f_25903_.m_20182_(), 10.0)) {
            Vec3 $$1 = Vec3.m_82539_(this.f_25905_);
            Vec3 $$2 = this.f_25903_.m_20182_();
            Vec3 $$3 = $$2.m_82546_($$1);
            $$1 = $$3.m_82490_(0.4).m_82549_($$1);
            Vec3 $$4 = $$1.m_82546_($$2).m_82541_().m_82490_(10.0).m_82549_($$2);
            BlockPos $$5 = new BlockPos($$4);
            if (!$$0.m_26519_(($$5 = this.f_25903_.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, $$5)).m_123341_(), $$5.m_123342_(), $$5.m_123343_(), 1.0)) {
                this.m_25915_();
            }
        }
    }

    private void m_25915_() {
        RandomSource $$0 = this.f_25903_.m_217043_();
        BlockPos $$1 = this.f_25903_.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, this.f_25903_.m_20183_().m_7918_(-8 + $$0.m_188503_(16), 0, -8 + $$0.m_188503_(16)));
        this.f_25903_.m_21573_().m_26519_($$1.m_123341_(), $$1.m_123342_(), $$1.m_123343_(), 1.0);
    }
}

