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
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;

public class PanicGoal
extends Goal {
    public static final int f_198171_ = 1;
    protected final PathfinderMob f_25684_;
    protected final double f_25685_;
    protected double f_25686_;
    protected double f_25687_;
    protected double f_25688_;
    protected boolean f_25689_;

    public PanicGoal(PathfinderMob p_25691_, double p_25692_) {
        this.f_25684_ = p_25691_;
        this.f_25685_ = p_25692_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean m_8036_() {
        BlockPos $$0;
        if (!this.m_202729_()) {
            return false;
        }
        if (this.f_25684_.m_6060_() && ($$0 = this.m_198172_(this.f_25684_.f_19853_, this.f_25684_, 5)) != null) {
            this.f_25686_ = $$0.m_123341_();
            this.f_25687_ = $$0.m_123342_();
            this.f_25688_ = $$0.m_123343_();
            return true;
        }
        return this.m_25702_();
    }

    protected boolean m_202729_() {
        return this.f_25684_.m_21188_() != null || this.f_25684_.m_203117_() || this.f_25684_.m_6060_();
    }

    protected boolean m_25702_() {
        Vec3 $$0 = DefaultRandomPos.m_148403_(this.f_25684_, 5, 4);
        if ($$0 == null) {
            return false;
        }
        this.f_25686_ = $$0.f_82479_;
        this.f_25687_ = $$0.f_82480_;
        this.f_25688_ = $$0.f_82481_;
        return true;
    }

    public boolean m_25703_() {
        return this.f_25689_;
    }

    @Override
    public void m_8056_() {
        this.f_25684_.m_21573_().m_26519_(this.f_25686_, this.f_25687_, this.f_25688_, this.f_25685_);
        this.f_25689_ = true;
    }

    @Override
    public void m_8041_() {
        this.f_25689_ = false;
    }

    @Override
    public boolean m_8045_() {
        return !this.f_25684_.m_21573_().m_26571_();
    }

    @Nullable
    protected BlockPos m_198172_(BlockGetter p_198173_, Entity p_198174_, int p_198175_) {
        BlockPos $$3 = p_198174_.m_20183_();
        if (!p_198173_.m_8055_($$3).m_60812_(p_198173_, $$3).m_83281_()) {
            return null;
        }
        return BlockPos.m_121930_(p_198174_.m_20183_(), p_198175_, 1, p_196649_ -> p_198173_.m_6425_((BlockPos)p_196649_).m_205070_(FluidTags.f_13131_)).orElse(null);
    }
}

