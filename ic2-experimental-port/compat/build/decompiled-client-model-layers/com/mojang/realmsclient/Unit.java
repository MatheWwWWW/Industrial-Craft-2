/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.realmsclient;

import java.util.Locale;

public final class Unit
extends Enum<Unit> {
    public static final /* enum */ Unit B = new Unit();
    public static final /* enum */ Unit KB = new Unit();
    public static final /* enum */ Unit MB = new Unit();
    public static final /* enum */ Unit GB = new Unit();
    private static final int f_167231_ = 1024;
    private static final /* synthetic */ Unit[] $VALUES;

    public static Unit[] values() {
        return (Unit[])$VALUES.clone();
    }

    public static Unit valueOf(String p_86951_) {
        return Enum.valueOf(Unit.class, p_86951_);
    }

    public static Unit m_86940_(long p_86941_) {
        if (p_86941_ < 1024L) {
            return B;
        }
        try {
            int $$1 = (int)(Math.log(p_86941_) / Math.log(1024.0));
            String $$2 = String.valueOf("KMGTPE".charAt($$1 - 1));
            return Unit.valueOf($$2 + "B");
        }
        catch (Exception $$3) {
            return GB;
        }
    }

    public static double m_86942_(long p_86943_, Unit p_86944_) {
        if (p_86944_ == B) {
            return p_86943_;
        }
        return (double)p_86943_ / Math.pow(1024.0, p_86944_.ordinal());
    }

    public static String m_86945_(long p_86946_) {
        int $$1 = 1024;
        if (p_86946_ < 1024L) {
            return p_86946_ + " B";
        }
        int $$2 = (int)(Math.log(p_86946_) / Math.log(1024.0));
        String $$3 = "" + "KMGTPE".charAt($$2 - 1);
        return String.format(Locale.ROOT, "%.1f %sB", (double)p_86946_ / Math.pow(1024.0, $$2), $$3);
    }

    public static String m_86947_(long p_86948_, Unit p_86949_) {
        return String.format(Locale.ROOT, "%." + (p_86949_ == GB ? "1" : "0") + "f %s", Unit.m_86942_(p_86948_, p_86949_), p_86949_.name());
    }

    private static /* synthetic */ Unit[] m_167232_() {
        return new Unit[]{B, KB, MB, GB};
    }

    static {
        $VALUES = Unit.m_167232_();
    }
}

