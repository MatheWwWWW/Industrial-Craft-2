/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.mojang.blaze3d.shaders;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Locale;
import javax.annotation.Nullable;

public class BlendMode {
    @Nullable
    private static BlendMode f_85499_;
    private final int f_85500_;
    private final int f_85501_;
    private final int f_85502_;
    private final int f_85503_;
    private final int f_85504_;
    private final boolean f_85505_;
    private final boolean f_85506_;

    private BlendMode(boolean p_85519_, boolean p_85520_, int p_85521_, int p_85522_, int p_85523_, int p_85524_, int p_85525_) {
        this.f_85505_ = p_85519_;
        this.f_85500_ = p_85521_;
        this.f_85502_ = p_85522_;
        this.f_85501_ = p_85523_;
        this.f_85503_ = p_85524_;
        this.f_85506_ = p_85520_;
        this.f_85504_ = p_85525_;
    }

    public BlendMode() {
        this(false, true, 1, 0, 1, 0, 32774);
    }

    public BlendMode(int p_85509_, int p_85510_, int p_85511_) {
        this(false, false, p_85509_, p_85510_, p_85509_, p_85510_, p_85511_);
    }

    public BlendMode(int p_85513_, int p_85514_, int p_85515_, int p_85516_, int p_85517_) {
        this(true, false, p_85513_, p_85514_, p_85515_, p_85516_, p_85517_);
    }

    public void m_85526_() {
        if (this.equals(f_85499_)) {
            return;
        }
        if (f_85499_ == null || this.f_85506_ != f_85499_.m_85529_()) {
            f_85499_ = this;
            if (this.f_85506_) {
                RenderSystem.m_69461_();
                return;
            }
            RenderSystem.m_69478_();
        }
        RenderSystem.m_69403_(this.f_85504_);
        if (this.f_85505_) {
            RenderSystem.m_69411_(this.f_85500_, this.f_85502_, this.f_85501_, this.f_85503_);
        } else {
            RenderSystem.m_69405_(this.f_85500_, this.f_85502_);
        }
    }

    public boolean equals(Object p_85533_) {
        if (this == p_85533_) {
            return true;
        }
        if (!(p_85533_ instanceof BlendMode)) {
            return false;
        }
        BlendMode $$1 = (BlendMode)p_85533_;
        if (this.f_85504_ != $$1.f_85504_) {
            return false;
        }
        if (this.f_85503_ != $$1.f_85503_) {
            return false;
        }
        if (this.f_85502_ != $$1.f_85502_) {
            return false;
        }
        if (this.f_85506_ != $$1.f_85506_) {
            return false;
        }
        if (this.f_85505_ != $$1.f_85505_) {
            return false;
        }
        if (this.f_85501_ != $$1.f_85501_) {
            return false;
        }
        return this.f_85500_ == $$1.f_85500_;
    }

    public int hashCode() {
        int $$0 = this.f_85500_;
        $$0 = 31 * $$0 + this.f_85501_;
        $$0 = 31 * $$0 + this.f_85502_;
        $$0 = 31 * $$0 + this.f_85503_;
        $$0 = 31 * $$0 + this.f_85504_;
        $$0 = 31 * $$0 + (this.f_85505_ ? 1 : 0);
        $$0 = 31 * $$0 + (this.f_85506_ ? 1 : 0);
        return $$0;
    }

    public boolean m_85529_() {
        return this.f_85506_;
    }

    public static int m_85527_(String p_85528_) {
        String $$1 = p_85528_.trim().toLowerCase(Locale.ROOT);
        if ("add".equals($$1)) {
            return 32774;
        }
        if ("subtract".equals($$1)) {
            return 32778;
        }
        if ("reversesubtract".equals($$1)) {
            return 32779;
        }
        if ("reverse_subtract".equals($$1)) {
            return 32779;
        }
        if ("min".equals($$1)) {
            return 32775;
        }
        if ("max".equals($$1)) {
            return 32776;
        }
        return 32774;
    }

    public static int m_85530_(String p_85531_) {
        String $$1 = p_85531_.trim().toLowerCase(Locale.ROOT);
        $$1 = $$1.replaceAll("_", "");
        $$1 = $$1.replaceAll("one", "1");
        $$1 = $$1.replaceAll("zero", "0");
        if ("0".equals($$1 = $$1.replaceAll("minus", "-"))) {
            return 0;
        }
        if ("1".equals($$1)) {
            return 1;
        }
        if ("srccolor".equals($$1)) {
            return 768;
        }
        if ("1-srccolor".equals($$1)) {
            return 769;
        }
        if ("dstcolor".equals($$1)) {
            return 774;
        }
        if ("1-dstcolor".equals($$1)) {
            return 775;
        }
        if ("srcalpha".equals($$1)) {
            return 770;
        }
        if ("1-srcalpha".equals($$1)) {
            return 771;
        }
        if ("dstalpha".equals($$1)) {
            return 772;
        }
        if ("1-dstalpha".equals($$1)) {
            return 773;
        }
        return -1;
    }
}

