/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.language;

import java.util.IllegalFormatException;
import net.minecraft.locale.Language;

public class I18n {
    private static volatile Language f_118934_ = Language.m_128107_();

    private I18n() {
    }

    static void m_118941_(Language p_118942_) {
        f_118934_ = p_118942_;
    }

    public static String m_118938_(String p_118939_, Object ... p_118940_) {
        String $$2 = f_118934_.m_6834_(p_118939_);
        try {
            return String.format($$2, p_118940_);
        }
        catch (IllegalFormatException $$3) {
            return "Format error: " + $$2;
        }
    }

    public static boolean m_118936_(String p_118937_) {
        return f_118934_.m_6722_(p_118937_);
    }
}

