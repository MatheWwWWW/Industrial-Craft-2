/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui.components;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Whence;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;

public class MultilineTextField {
    public static final int f_238667_ = Integer.MAX_VALUE;
    private static final int f_238620_ = 2;
    private final Font f_238538_;
    private final List<StringView> f_238722_ = Lists.newArrayList();
    private String f_238645_ = "";
    private int f_238566_;
    private int f_238550_;
    private boolean f_238557_;
    private int f_238569_ = Integer.MAX_VALUE;
    private final int f_238603_;
    private Consumer<String> f_238527_ = p_239235_ -> {};
    private Runnable f_238625_ = () -> {};

    public MultilineTextField(Font p_239611_, int p_239612_) {
        this.f_238538_ = p_239611_;
        this.f_238603_ = p_239612_;
    }

    public int m_239390_() {
        return this.f_238569_;
    }

    public void m_240162_(int p_240163_) {
        if (p_240163_ < 0) {
            throw new IllegalArgumentException("Character limit cannot be negative");
        }
        this.f_238569_ = p_240163_;
    }

    public boolean m_239629_() {
        return this.f_238569_ != Integer.MAX_VALUE;
    }

    public void m_239919_(Consumer<String> p_239920_) {
        this.f_238527_ = p_239920_;
    }

    public void m_239257_(Runnable p_239258_) {
        this.f_238625_ = p_239258_;
    }

    public void m_239677_(String p_239678_) {
        this.f_238645_ = this.m_239842_(p_239678_);
        this.f_238550_ = this.f_238566_ = this.f_238645_.length();
        this.m_239743_();
    }

    public String m_239618_() {
        return this.f_238645_;
    }

    public void m_240015_(String p_240016_) {
        if (p_240016_.isEmpty() && !this.m_239344_()) {
            return;
        }
        String $$1 = this.m_239417_(SharedConstants.m_239657_(p_240016_, true));
        StringView $$2 = this.m_239982_();
        this.f_238645_ = new StringBuilder(this.f_238645_).replace($$2.f_238590_, $$2.f_238654_, $$1).toString();
        this.f_238550_ = this.f_238566_ = $$2.f_238590_ + $$1.length();
        this.m_239743_();
    }

    public void m_239474_(int p_239475_) {
        if (!this.m_239344_()) {
            this.f_238550_ = Mth.m_14045_(this.f_238566_ + p_239475_, 0, this.f_238645_.length());
        }
        this.m_240015_("");
    }

    public int m_239456_() {
        return this.f_238566_;
    }

    public void m_239950_(boolean p_239951_) {
        this.f_238557_ = p_239951_;
    }

    public StringView m_239982_() {
        return new StringView(Math.min(this.f_238550_, this.f_238566_), Math.max(this.f_238550_, this.f_238566_));
    }

    public int m_239340_() {
        return this.f_238722_.size();
    }

    public int m_239268_() {
        for (int $$0 = 0; $$0 < this.f_238722_.size(); ++$$0) {
            StringView $$1 = this.f_238722_.get($$0);
            if (this.f_238566_ < $$1.f_238590_ || this.f_238566_ > $$1.f_238654_) continue;
            return $$0;
        }
        return -1;
    }

    public StringView m_239144_(int p_239145_) {
        return this.f_238722_.get(Mth.m_14045_(p_239145_, 0, this.f_238722_.size() - 1));
    }

    public void m_239797_(Whence p_239798_, int p_239799_) {
        switch (p_239798_) {
            case ABSOLUTE: {
                this.f_238566_ = p_239799_;
                break;
            }
            case RELATIVE: {
                this.f_238566_ += p_239799_;
                break;
            }
            case END: {
                this.f_238566_ = this.f_238645_.length() + p_239799_;
            }
        }
        this.f_238566_ = Mth.m_14045_(this.f_238566_, 0, this.f_238645_.length());
        this.f_238625_.run();
        if (!this.f_238557_) {
            this.f_238550_ = this.f_238566_;
        }
    }

    public void m_239393_(int p_239394_) {
        if (p_239394_ == 0) {
            return;
        }
        int $$1 = this.f_238538_.m_92895_(this.f_238645_.substring(this.m_240043_().f_238590_, this.f_238566_)) + 2;
        StringView $$2 = this.m_239854_(p_239394_);
        int $$3 = this.f_238538_.m_92834_(this.f_238645_.substring($$2.f_238590_, $$2.f_238654_), $$1).length();
        this.m_239797_(Whence.ABSOLUTE, $$2.f_238590_ + $$3);
    }

    public void m_239578_(double p_239579_, double p_239580_) {
        int $$2 = Mth.m_14107_(p_239579_);
        int $$3 = Mth.m_14107_(p_239580_ / (double)this.f_238538_.f_92710_);
        StringView $$4 = this.f_238722_.get(Mth.m_14045_($$3, 0, this.f_238722_.size() - 1));
        int $$5 = this.f_238538_.m_92834_(this.f_238645_.substring($$4.f_238590_, $$4.f_238654_), $$2).length();
        this.m_239797_(Whence.ABSOLUTE, $$4.f_238590_ + $$5);
    }

    public boolean m_239711_(int p_239712_) {
        this.f_238557_ = Screen.m_96638_();
        if (Screen.m_96634_(p_239712_)) {
            this.f_238566_ = this.f_238645_.length();
            this.f_238550_ = 0;
            return true;
        }
        if (Screen.m_96632_(p_239712_)) {
            Minecraft.m_91087_().f_91068_.m_90911_(this.m_240059_());
            return true;
        }
        if (Screen.m_96630_(p_239712_)) {
            this.m_240015_(Minecraft.m_91087_().f_91068_.m_90876_());
            return true;
        }
        if (Screen.m_96628_(p_239712_)) {
            Minecraft.m_91087_().f_91068_.m_90911_(this.m_240059_());
            this.m_240015_("");
            return true;
        }
        switch (p_239712_) {
            case 263: {
                if (Screen.m_96637_()) {
                    StringView $$1 = this.m_239637_();
                    this.m_239797_(Whence.ABSOLUTE, $$1.f_238590_);
                } else {
                    this.m_239797_(Whence.RELATIVE, -1);
                }
                return true;
            }
            case 262: {
                if (Screen.m_96637_()) {
                    StringView $$2 = this.m_239361_();
                    this.m_239797_(Whence.ABSOLUTE, $$2.f_238590_);
                } else {
                    this.m_239797_(Whence.RELATIVE, 1);
                }
                return true;
            }
            case 265: {
                if (!Screen.m_96637_()) {
                    this.m_239393_(-1);
                }
                return true;
            }
            case 264: {
                if (!Screen.m_96637_()) {
                    this.m_239393_(1);
                }
                return true;
            }
            case 266: {
                this.m_239797_(Whence.ABSOLUTE, 0);
                return true;
            }
            case 267: {
                this.m_239797_(Whence.END, 0);
                return true;
            }
            case 268: {
                if (Screen.m_96637_()) {
                    this.m_239797_(Whence.ABSOLUTE, 0);
                } else {
                    this.m_239797_(Whence.ABSOLUTE, this.m_240043_().f_238590_);
                }
                return true;
            }
            case 269: {
                if (Screen.m_96637_()) {
                    this.m_239797_(Whence.END, 0);
                } else {
                    this.m_239797_(Whence.ABSOLUTE, this.m_240043_().f_238654_);
                }
                return true;
            }
            case 259: {
                if (Screen.m_96637_()) {
                    StringView $$3 = this.m_239637_();
                    this.m_239474_($$3.f_238590_ - this.f_238566_);
                } else {
                    this.m_239474_(-1);
                }
                return true;
            }
            case 261: {
                if (Screen.m_96637_()) {
                    StringView $$4 = this.m_239361_();
                    this.m_239474_($$4.f_238590_ - this.f_238566_);
                } else {
                    this.m_239474_(1);
                }
                return true;
            }
            case 257: 
            case 335: {
                this.m_240015_("\n");
                return true;
            }
        }
        return false;
    }

    public Iterable<StringView> m_239290_() {
        return this.f_238722_;
    }

    public boolean m_239344_() {
        return this.f_238550_ != this.f_238566_;
    }

    @VisibleForTesting
    public String m_240059_() {
        StringView $$0 = this.m_239982_();
        return this.f_238645_.substring($$0.f_238590_, $$0.f_238654_);
    }

    private StringView m_240043_() {
        return this.m_239854_(0);
    }

    private StringView m_239854_(int p_239855_) {
        int $$1 = this.m_239268_();
        if ($$1 < 0) {
            throw new IllegalStateException("Cursor is not within text (cursor = " + this.f_238566_ + ", length = " + this.f_238645_.length() + ")");
        }
        return this.f_238722_.get(Mth.m_14045_($$1 + p_239855_, 0, this.f_238722_.size() - 1));
    }

    @VisibleForTesting
    public StringView m_239637_() {
        int $$0;
        if (this.f_238645_.isEmpty()) {
            return StringView.f_238547_;
        }
        for ($$0 = Mth.m_14045_(this.f_238566_, 0, this.f_238645_.length() - 1); $$0 > 0 && Character.isWhitespace(this.f_238645_.charAt($$0 - 1)); --$$0) {
        }
        while ($$0 > 0 && !Character.isWhitespace(this.f_238645_.charAt($$0 - 1))) {
            --$$0;
        }
        return new StringView($$0, this.m_240092_($$0));
    }

    @VisibleForTesting
    public StringView m_239361_() {
        int $$0;
        if (this.f_238645_.isEmpty()) {
            return StringView.f_238547_;
        }
        for ($$0 = Mth.m_14045_(this.f_238566_, 0, this.f_238645_.length() - 1); $$0 < this.f_238645_.length() && !Character.isWhitespace(this.f_238645_.charAt($$0)); ++$$0) {
        }
        while ($$0 < this.f_238645_.length() && Character.isWhitespace(this.f_238645_.charAt($$0))) {
            ++$$0;
        }
        return new StringView($$0, this.m_240092_($$0));
    }

    private int m_240092_(int p_240093_) {
        int $$1;
        for ($$1 = p_240093_; $$1 < this.f_238645_.length() && !Character.isWhitespace(this.f_238645_.charAt($$1)); ++$$1) {
        }
        return $$1;
    }

    private void m_239743_() {
        this.m_239915_();
        this.f_238527_.accept(this.f_238645_);
        this.f_238625_.run();
    }

    private void m_239915_() {
        this.f_238722_.clear();
        if (this.f_238645_.isEmpty()) {
            this.f_238722_.add(StringView.f_238547_);
            return;
        }
        this.f_238538_.m_92865_().m_92364_(this.f_238645_, this.f_238603_, Style.f_131099_, false, (p_239846_, p_239847_, p_239848_) -> this.f_238722_.add(new StringView(p_239847_, p_239848_)));
        if (this.f_238645_.charAt(this.f_238645_.length() - 1) == '\n') {
            this.f_238722_.add(new StringView(this.f_238645_.length(), this.f_238645_.length()));
        }
    }

    private String m_239842_(String p_239843_) {
        if (this.m_239629_()) {
            return StringUtil.m_144998_(p_239843_, this.f_238569_, false);
        }
        return p_239843_;
    }

    private String m_239417_(String p_239418_) {
        if (this.m_239629_()) {
            int $$1 = this.f_238569_ - this.f_238645_.length();
            return StringUtil.m_144998_(p_239418_, $$1, false);
        }
        return p_239418_;
    }

    protected record StringView(int f_238590_, int f_238654_) {
        static final StringView f_238547_ = new StringView(0, 0);

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{StringView.class, "beginIndex;endIndex", "f_238590_", "f_238654_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{StringView.class, "beginIndex;endIndex", "f_238590_", "f_238654_"}, this);
        }

        @Override
        public final boolean equals(Object p_239694_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{StringView.class, "beginIndex;endIndex", "f_238590_", "f_238654_"}, this, p_239694_);
        }
    }
}

