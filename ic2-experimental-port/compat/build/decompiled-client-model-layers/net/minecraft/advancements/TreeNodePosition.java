/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.advancements;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;

public class TreeNodePosition {
    private final Advancement f_16554_;
    @Nullable
    private final TreeNodePosition f_16555_;
    @Nullable
    private final TreeNodePosition f_16556_;
    private final int f_16557_;
    private final List<TreeNodePosition> f_16558_ = Lists.newArrayList();
    private TreeNodePosition f_16559_;
    @Nullable
    private TreeNodePosition f_16560_;
    private int f_16561_;
    private float f_16562_;
    private float f_16563_;
    private float f_16564_;
    private float f_16565_;

    public TreeNodePosition(Advancement p_16567_, @Nullable TreeNodePosition p_16568_, @Nullable TreeNodePosition p_16569_, int p_16570_, int p_16571_) {
        if (p_16567_.m_138320_() == null) {
            throw new IllegalArgumentException("Can't position an invisible advancement!");
        }
        this.f_16554_ = p_16567_;
        this.f_16555_ = p_16568_;
        this.f_16556_ = p_16569_;
        this.f_16557_ = p_16570_;
        this.f_16559_ = this;
        this.f_16561_ = p_16571_;
        this.f_16562_ = -1.0f;
        TreeNodePosition $$5 = null;
        for (Advancement $$6 : p_16567_.m_138322_()) {
            $$5 = this.m_16589_($$6, $$5);
        }
    }

    @Nullable
    private TreeNodePosition m_16589_(Advancement p_16590_, @Nullable TreeNodePosition p_16591_) {
        if (p_16590_.m_138320_() != null) {
            p_16591_ = new TreeNodePosition(p_16590_, this, p_16591_, this.f_16558_.size() + 1, this.f_16561_ + 1);
            this.f_16558_.add(p_16591_);
        } else {
            for (Advancement $$2 : p_16590_.m_138322_()) {
                p_16591_ = this.m_16589_($$2, p_16591_);
            }
        }
        return p_16591_;
    }

    private void m_16572_() {
        if (this.f_16558_.isEmpty()) {
            this.f_16562_ = this.f_16556_ != null ? this.f_16556_.f_16562_ + 1.0f : 0.0f;
            return;
        }
        TreeNodePosition $$0 = null;
        for (TreeNodePosition $$1 : this.f_16558_) {
            $$1.m_16572_();
            $$0 = $$1.m_16579_($$0 == null ? $$1 : $$0);
        }
        this.m_16592_();
        float $$2 = (this.f_16558_.get((int)0).f_16562_ + this.f_16558_.get((int)(this.f_16558_.size() - 1)).f_16562_) / 2.0f;
        if (this.f_16556_ != null) {
            this.f_16562_ = this.f_16556_.f_16562_ + 1.0f;
            this.f_16563_ = this.f_16562_ - $$2;
        } else {
            this.f_16562_ = $$2;
        }
    }

    private float m_16575_(float p_16576_, int p_16577_, float p_16578_) {
        this.f_16562_ += p_16576_;
        this.f_16561_ = p_16577_;
        if (this.f_16562_ < p_16578_) {
            p_16578_ = this.f_16562_;
        }
        for (TreeNodePosition $$3 : this.f_16558_) {
            p_16578_ = $$3.m_16575_(p_16576_ + this.f_16563_, p_16577_ + 1, p_16578_);
        }
        return p_16578_;
    }

    private void m_16573_(float p_16574_) {
        this.f_16562_ += p_16574_;
        for (TreeNodePosition $$1 : this.f_16558_) {
            $$1.m_16573_(p_16574_);
        }
    }

    private void m_16592_() {
        float $$0 = 0.0f;
        float $$1 = 0.0f;
        for (int $$2 = this.f_16558_.size() - 1; $$2 >= 0; --$$2) {
            TreeNodePosition $$3 = this.f_16558_.get($$2);
            $$3.f_16562_ += $$0;
            $$3.f_16563_ += $$0;
            $$0 += $$3.f_16565_ + ($$1 += $$3.f_16564_);
        }
    }

    @Nullable
    private TreeNodePosition m_16593_() {
        if (this.f_16560_ != null) {
            return this.f_16560_;
        }
        if (!this.f_16558_.isEmpty()) {
            return this.f_16558_.get(0);
        }
        return null;
    }

    @Nullable
    private TreeNodePosition m_16594_() {
        if (this.f_16560_ != null) {
            return this.f_16560_;
        }
        if (!this.f_16558_.isEmpty()) {
            return this.f_16558_.get(this.f_16558_.size() - 1);
        }
        return null;
    }

    private TreeNodePosition m_16579_(TreeNodePosition p_16580_) {
        if (this.f_16556_ == null) {
            return p_16580_;
        }
        TreeNodePosition $$1 = this;
        TreeNodePosition $$2 = this;
        TreeNodePosition $$3 = this.f_16556_;
        TreeNodePosition $$4 = this.f_16555_.f_16558_.get(0);
        float $$5 = this.f_16563_;
        float $$6 = this.f_16563_;
        float $$7 = $$3.f_16563_;
        float $$8 = $$4.f_16563_;
        while ($$3.m_16594_() != null && $$1.m_16593_() != null) {
            $$3 = $$3.m_16594_();
            $$1 = $$1.m_16593_();
            $$4 = $$4.m_16593_();
            $$2 = $$2.m_16594_();
            $$2.f_16559_ = this;
            float $$9 = $$3.f_16562_ + $$7 - ($$1.f_16562_ + $$5) + 1.0f;
            if ($$9 > 0.0f) {
                $$3.m_16584_(this, p_16580_).m_16581_(this, $$9);
                $$5 += $$9;
                $$6 += $$9;
            }
            $$7 += $$3.f_16563_;
            $$5 += $$1.f_16563_;
            $$8 += $$4.f_16563_;
            $$6 += $$2.f_16563_;
        }
        if ($$3.m_16594_() != null && $$2.m_16594_() == null) {
            $$2.f_16560_ = $$3.m_16594_();
            $$2.f_16563_ += $$7 - $$6;
        } else {
            if ($$1.m_16593_() != null && $$4.m_16593_() == null) {
                $$4.f_16560_ = $$1.m_16593_();
                $$4.f_16563_ += $$5 - $$8;
            }
            p_16580_ = this;
        }
        return p_16580_;
    }

    private void m_16581_(TreeNodePosition p_16582_, float p_16583_) {
        float $$2 = p_16582_.f_16557_ - this.f_16557_;
        if ($$2 != 0.0f) {
            p_16582_.f_16564_ -= p_16583_ / $$2;
            this.f_16564_ += p_16583_ / $$2;
        }
        p_16582_.f_16565_ += p_16583_;
        p_16582_.f_16562_ += p_16583_;
        p_16582_.f_16563_ += p_16583_;
    }

    private TreeNodePosition m_16584_(TreeNodePosition p_16585_, TreeNodePosition p_16586_) {
        if (this.f_16559_ != null && p_16585_.f_16555_.f_16558_.contains(this.f_16559_)) {
            return this.f_16559_;
        }
        return p_16586_;
    }

    private void m_16595_() {
        if (this.f_16554_.m_138320_() != null) {
            this.f_16554_.m_138320_().m_14978_(this.f_16561_, this.f_16562_);
        }
        if (!this.f_16558_.isEmpty()) {
            for (TreeNodePosition $$0 : this.f_16558_) {
                $$0.m_16595_();
            }
        }
    }

    public static void m_16587_(Advancement p_16588_) {
        if (p_16588_.m_138320_() == null) {
            throw new IllegalArgumentException("Can't position children of an invisible root!");
        }
        TreeNodePosition $$1 = new TreeNodePosition(p_16588_, null, null, 1, 0);
        $$1.m_16572_();
        float $$2 = $$1.m_16575_(0.0f, 0, $$1.f_16562_);
        if ($$2 < 0.0f) {
            $$1.m_16573_(-$$2);
        }
        $$1.m_16595_();
    }
}

