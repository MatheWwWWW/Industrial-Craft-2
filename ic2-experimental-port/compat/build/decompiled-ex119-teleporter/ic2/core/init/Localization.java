/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.language.I18n
 */
package ic2.core.init;

import net.minecraft.client.resources.language.I18n;

public class Localization {
    private static final String defaultLang = "en_us";
    private static final String ic2LangKey = "ic2.";

    public static String translate(String string) {
        return I18n.m_118938_((String)string, (Object[])new Object[0]);
    }

    public static String translate(String string, Object ... objectArray) {
        return I18n.m_118938_((String)string, (Object[])objectArray);
    }
}

