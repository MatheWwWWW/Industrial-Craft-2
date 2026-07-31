/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class StringUtil {
    private static final Pattern f_14402_ = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
    private static final Pattern f_144995_ = Pattern.compile("\\r\\n|\\v");
    private static final Pattern f_144996_ = Pattern.compile("(?:\\r\\n|\\v)$");

    public static String m_14404_(int p_14405_) {
        int $$1 = p_14405_ / 20;
        int $$2 = $$1 / 60;
        if (($$1 %= 60) < 10) {
            return $$2 + ":0" + $$1;
        }
        return $$2 + ":" + $$1;
    }

    public static String m_14406_(String p_14407_) {
        return f_14402_.matcher(p_14407_).replaceAll("");
    }

    public static boolean m_14408_(@Nullable String p_14409_) {
        return StringUtils.isEmpty((CharSequence)p_14409_);
    }

    public static String m_144998_(String p_144999_, int p_145000_, boolean p_145001_) {
        if (p_144999_.length() <= p_145000_) {
            return p_144999_;
        }
        if (p_145001_ && p_145000_ > 3) {
            return p_144999_.substring(0, p_145000_ - 3) + "...";
        }
        return p_144999_.substring(0, p_145000_);
    }

    public static int m_145002_(String p_145003_) {
        if (p_145003_.isEmpty()) {
            return 0;
        }
        Matcher $$1 = f_144995_.matcher(p_145003_);
        int $$2 = 1;
        while ($$1.find()) {
            ++$$2;
        }
        return $$2;
    }

    public static boolean m_145004_(String p_145005_) {
        return f_144996_.matcher(p_145005_).find();
    }

    public static String m_216469_(String p_216470_) {
        return StringUtil.m_144998_(p_216470_, 256, false);
    }
}

