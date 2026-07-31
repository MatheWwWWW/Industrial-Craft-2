/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.network.chat;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

public class MutableComponent
implements Component {
    private final ComponentContents f_237194_;
    private final List<Component> f_237195_;
    private Style f_237196_;
    private FormattedCharSequence f_237197_ = FormattedCharSequence.f_13691_;
    @Nullable
    private Language f_237198_;

    MutableComponent(ComponentContents p_237200_, List<Component> p_237201_, Style p_237202_) {
        this.f_237194_ = p_237200_;
        this.f_237195_ = p_237201_;
        this.f_237196_ = p_237202_;
    }

    public static MutableComponent m_237204_(ComponentContents p_237205_) {
        return new MutableComponent(p_237205_, Lists.newArrayList(), Style.f_131099_);
    }

    @Override
    public ComponentContents m_214077_() {
        return this.f_237194_;
    }

    @Override
    public List<Component> m_7360_() {
        return this.f_237195_;
    }

    public MutableComponent m_6270_(Style p_130943_) {
        this.f_237196_ = p_130943_;
        return this;
    }

    @Override
    public Style m_7383_() {
        return this.f_237196_;
    }

    public MutableComponent m_130946_(String p_130947_) {
        return this.m_7220_(Component.m_237113_(p_130947_));
    }

    public MutableComponent m_7220_(Component p_130942_) {
        this.f_237195_.add(p_130942_);
        return this;
    }

    public MutableComponent m_130938_(UnaryOperator<Style> p_130939_) {
        this.m_6270_((Style)p_130939_.apply(this.m_7383_()));
        return this;
    }

    public MutableComponent m_130948_(Style p_130949_) {
        this.m_6270_(p_130949_.m_131146_(this.m_7383_()));
        return this;
    }

    public MutableComponent m_130944_(ChatFormatting ... p_130945_) {
        this.m_6270_(this.m_7383_().m_131152_(p_130945_));
        return this;
    }

    public MutableComponent m_130940_(ChatFormatting p_130941_) {
        this.m_6270_(this.m_7383_().m_131157_(p_130941_));
        return this;
    }

    @Override
    public FormattedCharSequence m_7532_() {
        Language $$0 = Language.m_128107_();
        if (this.f_237198_ != $$0) {
            this.f_237197_ = $$0.m_5536_(this);
            this.f_237198_ = $$0;
        }
        return this.f_237197_;
    }

    public boolean equals(Object p_237209_) {
        if (this == p_237209_) {
            return true;
        }
        if (p_237209_ instanceof MutableComponent) {
            MutableComponent $$1 = (MutableComponent)p_237209_;
            return this.f_237194_.equals($$1.f_237194_) && this.f_237196_.equals($$1.f_237196_) && this.f_237195_.equals($$1.f_237195_);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.f_237194_, this.f_237196_, this.f_237195_);
    }

    public String toString() {
        boolean $$2;
        StringBuilder $$0 = new StringBuilder(this.f_237194_.toString());
        boolean $$1 = !this.f_237196_.m_131179_();
        boolean bl = $$2 = !this.f_237195_.isEmpty();
        if ($$1 || $$2) {
            $$0.append('[');
            if ($$1) {
                $$0.append("style=");
                $$0.append(this.f_237196_);
            }
            if ($$1 && $$2) {
                $$0.append(", ");
            }
            if ($$2) {
                $$0.append("siblings=");
                $$0.append(this.f_237195_);
            }
            $$0.append(']');
        }
        return $$0.toString();
    }
}

