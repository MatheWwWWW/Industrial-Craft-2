/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  it.unimi.dsi.fastutil.ints.Int2IntFunction
 */
package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.List;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.util.StringDecomposer;

@FunctionalInterface
public interface FormattedCharSequence {
    public static final FormattedCharSequence f_13691_ = p_13704_ -> true;

    public boolean m_13731_(FormattedCharSink var1);

    public static FormattedCharSequence m_13693_(int p_13694_, Style p_13695_) {
        return p_13730_ -> p_13730_.m_6411_(0, p_13695_, p_13694_);
    }

    public static FormattedCharSequence m_13714_(String p_13715_, Style p_13716_) {
        if (p_13715_.isEmpty()) {
            return f_13691_;
        }
        return p_13739_ -> StringDecomposer.m_14317_(p_13715_, p_13716_, p_13739_);
    }

    public static FormattedCharSequence m_144717_(String p_144718_, Style p_144719_, Int2IntFunction p_144720_) {
        if (p_144718_.isEmpty()) {
            return f_13691_;
        }
        return p_144730_ -> StringDecomposer.m_14317_(p_144718_, p_144719_, FormattedCharSequence.m_13705_(p_144730_, p_144720_));
    }

    public static FormattedCharSequence m_144723_(String p_144724_, Style p_144725_) {
        if (p_144724_.isEmpty()) {
            return f_13691_;
        }
        return p_144716_ -> StringDecomposer.m_14337_(p_144724_, p_144725_, p_144716_);
    }

    public static FormattedCharSequence m_13740_(String p_13741_, Style p_13742_, Int2IntFunction p_13743_) {
        if (p_13741_.isEmpty()) {
            return f_13691_;
        }
        return p_13721_ -> StringDecomposer.m_14337_(p_13741_, p_13742_, FormattedCharSequence.m_13705_(p_13721_, p_13743_));
    }

    public static FormattedCharSink m_13705_(FormattedCharSink p_13706_, Int2IntFunction p_13707_) {
        return (p_13711_, p_13712_, p_13713_) -> p_13706_.m_6411_(p_13711_, p_13712_, (Integer)p_13707_.apply((Object)p_13713_));
    }

    public static FormattedCharSequence m_144710_() {
        return f_13691_;
    }

    public static FormattedCharSequence m_144711_(FormattedCharSequence p_144712_) {
        return p_144712_;
    }

    public static FormattedCharSequence m_13696_(FormattedCharSequence p_13697_, FormattedCharSequence p_13698_) {
        return FormattedCharSequence.m_13733_(p_13697_, p_13698_);
    }

    public static FormattedCharSequence m_144721_(FormattedCharSequence ... p_144722_) {
        return FormattedCharSequence.m_13744_((List<FormattedCharSequence>)ImmutableList.copyOf((Object[])p_144722_));
    }

    public static FormattedCharSequence m_13722_(List<FormattedCharSequence> p_13723_) {
        int $$1 = p_13723_.size();
        switch ($$1) {
            case 0: {
                return f_13691_;
            }
            case 1: {
                return p_13723_.get(0);
            }
            case 2: {
                return FormattedCharSequence.m_13733_(p_13723_.get(0), p_13723_.get(1));
            }
        }
        return FormattedCharSequence.m_13744_((List<FormattedCharSequence>)ImmutableList.copyOf(p_13723_));
    }

    public static FormattedCharSequence m_13733_(FormattedCharSequence p_13734_, FormattedCharSequence p_13735_) {
        return p_13702_ -> p_13734_.m_13731_(p_13702_) && p_13735_.m_13731_(p_13702_);
    }

    public static FormattedCharSequence m_13744_(List<FormattedCharSequence> p_13745_) {
        return p_13726_ -> {
            for (FormattedCharSequence $$2 : p_13745_) {
                if ($$2.m_13731_(p_13726_)) continue;
                return false;
            }
            return true;
        };
    }
}

