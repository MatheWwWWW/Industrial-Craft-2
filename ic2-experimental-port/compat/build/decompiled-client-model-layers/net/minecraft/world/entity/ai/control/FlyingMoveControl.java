/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class FlyingMoveControl
extends MoveControl {
    private final int f_24890_;
    private final boolean f_24891_;

    public FlyingMoveControl(Mob p_24893_, int p_24894_, boolean p_24895_) {
        super(p_24893_);
        this.f_24890_ = p_24894_;
        this.f_24891_ = p_24895_;
    }

    @Override
    public void m_8126_() {
        if (this.f_24981_ == MoveControl.Operation.MOVE_TO) {
            float $$6;
            this.f_24981_ = MoveControl.Operation.WAIT;
            this.f_24974_.m_20242_(true);
            double $$0 = this.f_24975_ - this.f_24974_.m_20185_();
            double $$1 = this.f_24976_ - this.f_24974_.m_20186_();
            double $$2 = this.f_24977_ - this.f_24974_.m_20189_();
            double $$3 = $$0 * $$0 + $$1 * $$1 + $$2 * $$2;
            if ($$3 < 2.500000277905201E-7) {
                this.f_24974_.m_21567_(0.0f);
                this.f_24974_.m_21564_(0.0f);
                return;
            }
            float $$4 = (float)(Mth.m_14136_($$2, $$0) * 57.2957763671875) - 90.0f;
            this.f_24974_.m_146922_(this.m_24991_(this.f_24974_.m_146908_(), $$4, 90.0f));
            if (this.f_24974_.m_20096_()) {
                float $$5 = (float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22279_));
            } else {
                $$6 = (float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22280_));
            }
            this.f_24974_.m_7910_($$6);
            double $$7 = Math.sqrt($$0 * $$0 + $$2 * $$2);
            if (Math.abs($$1) > (double)1.0E-5f || Math.abs($$7) > (double)1.0E-5f) {
                float $$8 = (float)(-(Mth.m_14136_($$1, $$7) * 57.2957763671875));
                this.f_24974_.m_146926_(this.m_24991_(this.f_24974_.m_146909_(), $$8, this.f_24890_));
                this.f_24974_.m_21567_($$1 > 0.0 ? $$6 : -$$6);
            }
        } else {
            if (!this.f_24891_) {
                this.f_24974_.m_20242_(false);
            }
            this.f_24974_.m_21567_(0.0f);
            this.f_24974_.m_21564_(0.0f);
        }
    }
}

