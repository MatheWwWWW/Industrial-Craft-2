/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.entity.animal.Dolphin;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class DolphinJumpGoal
extends JumpGoal {
    private static final int[] f_25162_ = new int[]{0, 1, 4, 5, 6, 7};
    private final Dolphin f_25163_;
    private final int f_25164_;
    private boolean f_25165_;

    public DolphinJumpGoal(Dolphin p_25168_, int p_25169_) {
        this.f_25163_ = p_25168_;
        this.f_25164_ = DolphinJumpGoal.m_186073_(p_25169_);
    }

    @Override
    public boolean m_8036_() {
        if (this.f_25163_.m_217043_().m_188503_(this.f_25164_) != 0) {
            return false;
        }
        Direction $$0 = this.f_25163_.m_6374_();
        int $$1 = $$0.m_122429_();
        int $$2 = $$0.m_122431_();
        BlockPos $$3 = this.f_25163_.m_20183_();
        for (int $$4 : f_25162_) {
            if (this.m_25172_($$3, $$1, $$2, $$4) && this.m_25178_($$3, $$1, $$2, $$4)) continue;
            return false;
        }
        return true;
    }

    private boolean m_25172_(BlockPos p_25173_, int p_25174_, int p_25175_, int p_25176_) {
        BlockPos $$4 = p_25173_.m_7918_(p_25174_ * p_25176_, 0, p_25175_ * p_25176_);
        return this.f_25163_.f_19853_.m_6425_($$4).m_205070_(FluidTags.f_13131_) && !this.f_25163_.f_19853_.m_8055_($$4).m_60767_().m_76334_();
    }

    private boolean m_25178_(BlockPos p_25179_, int p_25180_, int p_25181_, int p_25182_) {
        return this.f_25163_.f_19853_.m_8055_(p_25179_.m_7918_(p_25180_ * p_25182_, 1, p_25181_ * p_25182_)).m_60795_() && this.f_25163_.f_19853_.m_8055_(p_25179_.m_7918_(p_25180_ * p_25182_, 2, p_25181_ * p_25182_)).m_60795_();
    }

    @Override
    public boolean m_8045_() {
        double $$0 = this.f_25163_.m_20184_().f_82480_;
        return !($$0 * $$0 < (double)0.03f && this.f_25163_.m_146909_() != 0.0f && Math.abs(this.f_25163_.m_146909_()) < 10.0f && this.f_25163_.m_20069_() || this.f_25163_.m_20096_());
    }

    @Override
    public boolean m_6767_() {
        return false;
    }

    @Override
    public void m_8056_() {
        Direction $$0 = this.f_25163_.m_6374_();
        this.f_25163_.m_20256_(this.f_25163_.m_20184_().m_82520_((double)$$0.m_122429_() * 0.6, 0.7, (double)$$0.m_122431_() * 0.6));
        this.f_25163_.m_21573_().m_26573_();
    }

    @Override
    public void m_8041_() {
        this.f_25163_.m_146926_(0.0f);
    }

    @Override
    public void m_8037_() {
        boolean $$0 = this.f_25165_;
        if (!$$0) {
            FluidState $$1 = this.f_25163_.f_19853_.m_6425_(this.f_25163_.m_20183_());
            this.f_25165_ = $$1.m_205070_(FluidTags.f_13131_);
        }
        if (this.f_25165_ && !$$0) {
            this.f_25163_.m_5496_(SoundEvents.f_11805_, 1.0f, 1.0f);
        }
        Vec3 $$2 = this.f_25163_.m_20184_();
        if ($$2.f_82480_ * $$2.f_82480_ < (double)0.03f && this.f_25163_.m_146909_() != 0.0f) {
            this.f_25163_.m_146926_(Mth.m_14201_(this.f_25163_.m_146909_(), 0.0f, 0.2f));
        } else if ($$2.m_82553_() > (double)1.0E-5f) {
            double $$3 = $$2.m_165924_();
            double $$4 = Math.atan2(-$$2.f_82480_, $$3) * 57.2957763671875;
            this.f_25163_.m_146926_((float)$$4);
        }
    }
}

