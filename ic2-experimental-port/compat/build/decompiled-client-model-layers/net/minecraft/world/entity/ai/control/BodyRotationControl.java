/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.Control;

public class BodyRotationControl
implements Control {
    private final Mob f_24875_;
    private static final int f_148048_ = 15;
    private static final int f_148049_ = 10;
    private static final int f_148050_ = 10;
    private int f_24876_;
    private float f_24877_;

    public BodyRotationControl(Mob p_24879_) {
        this.f_24875_ = p_24879_;
    }

    public void m_8121_() {
        if (this.m_24884_()) {
            this.f_24875_.f_20883_ = this.f_24875_.m_146908_();
            this.m_24881_();
            this.f_24877_ = this.f_24875_.f_20885_;
            this.f_24876_ = 0;
            return;
        }
        if (this.m_24883_()) {
            if (Math.abs(this.f_24875_.f_20885_ - this.f_24877_) > 15.0f) {
                this.f_24876_ = 0;
                this.f_24877_ = this.f_24875_.f_20885_;
                this.m_24880_();
            } else {
                ++this.f_24876_;
                if (this.f_24876_ > 10) {
                    this.m_24882_();
                }
            }
        }
    }

    private void m_24880_() {
        this.f_24875_.f_20883_ = Mth.m_14094_(this.f_24875_.f_20883_, this.f_24875_.f_20885_, this.f_24875_.m_8085_());
    }

    private void m_24881_() {
        this.f_24875_.f_20885_ = Mth.m_14094_(this.f_24875_.f_20885_, this.f_24875_.f_20883_, this.f_24875_.m_8085_());
    }

    private void m_24882_() {
        int $$0 = this.f_24876_ - 10;
        float $$1 = Mth.m_14036_((float)$$0 / 10.0f, 0.0f, 1.0f);
        float $$2 = (float)this.f_24875_.m_8085_() * (1.0f - $$1);
        this.f_24875_.f_20883_ = Mth.m_14094_(this.f_24875_.f_20883_, this.f_24875_.f_20885_, $$2);
    }

    private boolean m_24883_() {
        return !(this.f_24875_.m_146895_() instanceof Mob);
    }

    private boolean m_24884_() {
        double $$1;
        double $$0 = this.f_24875_.m_20185_() - this.f_24875_.f_19854_;
        return $$0 * $$0 + ($$1 = this.f_24875_.m_20189_() - this.f_24875_.f_19856_) * $$1 > 2.500000277905201E-7;
    }
}

