/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class SmoothSwimmingMoveControl
extends MoveControl {
    private final int f_148064_;
    private final int f_148065_;
    private final float f_148066_;
    private final float f_148067_;
    private final boolean f_148068_;

    public SmoothSwimmingMoveControl(Mob p_148070_, int p_148071_, int p_148072_, float p_148073_, float p_148074_, boolean p_148075_) {
        super(p_148070_);
        this.f_148064_ = p_148071_;
        this.f_148065_ = p_148072_;
        this.f_148066_ = p_148073_;
        this.f_148067_ = p_148074_;
        this.f_148068_ = p_148075_;
    }

    @Override
    public void m_8126_() {
        double $$2;
        double $$1;
        if (this.f_148068_ && this.f_24974_.m_20069_()) {
            this.f_24974_.m_20256_(this.f_24974_.m_20184_().m_82520_(0.0, 0.005, 0.0));
        }
        if (this.f_24981_ != MoveControl.Operation.MOVE_TO || this.f_24974_.m_21573_().m_26571_()) {
            this.f_24974_.m_7910_(0.0f);
            this.f_24974_.m_21570_(0.0f);
            this.f_24974_.m_21567_(0.0f);
            this.f_24974_.m_21564_(0.0f);
            return;
        }
        double $$0 = this.f_24975_ - this.f_24974_.m_20185_();
        double $$3 = $$0 * $$0 + ($$1 = this.f_24976_ - this.f_24974_.m_20186_()) * $$1 + ($$2 = this.f_24977_ - this.f_24974_.m_20189_()) * $$2;
        if ($$3 < 2.500000277905201E-7) {
            this.f_24974_.m_21564_(0.0f);
            return;
        }
        float $$4 = (float)(Mth.m_14136_($$2, $$0) * 57.2957763671875) - 90.0f;
        this.f_24974_.m_146922_(this.m_24991_(this.f_24974_.m_146908_(), $$4, this.f_148065_));
        this.f_24974_.f_20883_ = this.f_24974_.m_146908_();
        this.f_24974_.f_20885_ = this.f_24974_.m_146908_();
        float $$5 = (float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22279_));
        if (this.f_24974_.m_20069_()) {
            this.f_24974_.m_7910_($$5 * this.f_148066_);
            double $$6 = Math.sqrt($$0 * $$0 + $$2 * $$2);
            if (Math.abs($$1) > (double)1.0E-5f || Math.abs($$6) > (double)1.0E-5f) {
                float $$7 = -((float)(Mth.m_14136_($$1, $$6) * 57.2957763671875));
                $$7 = Mth.m_14036_(Mth.m_14177_($$7), -this.f_148064_, this.f_148064_);
                this.f_24974_.m_146926_(this.m_24991_(this.f_24974_.m_146909_(), $$7, 5.0f));
            }
            float $$8 = Mth.m_14089_(this.f_24974_.m_146909_() * ((float)Math.PI / 180));
            float $$9 = Mth.m_14031_(this.f_24974_.m_146909_() * ((float)Math.PI / 180));
            this.f_24974_.f_20902_ = $$8 * $$5;
            this.f_24974_.f_20901_ = -$$9 * $$5;
        } else {
            this.f_24974_.m_7910_($$5 * this.f_148067_);
        }
    }
}

