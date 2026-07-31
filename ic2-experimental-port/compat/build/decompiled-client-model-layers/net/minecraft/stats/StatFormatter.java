/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.stats;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import net.minecraft.Util;

public interface StatFormatter {
    public static final DecimalFormat f_12872_ = Util.m_137469_(new DecimalFormat("########0.00"), p_12881_ -> p_12881_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));
    public static final StatFormatter f_12873_ = NumberFormat.getIntegerInstance(Locale.US)::format;
    public static final StatFormatter f_12874_ = p_12885_ -> f_12872_.format((double)p_12885_ * 0.1);
    public static final StatFormatter f_12875_ = p_12883_ -> {
        double $$1 = (double)p_12883_ / 100.0;
        double $$2 = $$1 / 1000.0;
        if ($$2 > 0.5) {
            return f_12872_.format($$2) + " km";
        }
        if ($$1 > 0.5) {
            return f_12872_.format($$1) + " m";
        }
        return p_12883_ + " cm";
    };
    public static final StatFormatter f_12876_ = p_12879_ -> {
        double $$1 = (double)p_12879_ / 20.0;
        double $$2 = $$1 / 60.0;
        double $$3 = $$2 / 60.0;
        double $$4 = $$3 / 24.0;
        double $$5 = $$4 / 365.0;
        if ($$5 > 0.5) {
            return f_12872_.format($$5) + " y";
        }
        if ($$4 > 0.5) {
            return f_12872_.format($$4) + " d";
        }
        if ($$3 > 0.5) {
            return f_12872_.format($$3) + " h";
        }
        if ($$2 > 0.5) {
            return f_12872_.format($$2) + " m";
        }
        return $$1 + " s";
    };

    public String m_12886_(int var1);
}

