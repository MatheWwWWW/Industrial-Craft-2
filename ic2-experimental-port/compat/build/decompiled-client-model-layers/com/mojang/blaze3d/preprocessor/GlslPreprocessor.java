/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 */
package com.mojang.blaze3d.preprocessor;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.FileUtil;
import net.minecraft.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

public abstract class GlslPreprocessor {
    private static final String f_166454_ = "/\\*(?:[^*]|\\*+[^*/])*\\*+/";
    private static final String f_166455_ = "//[^\\v]*";
    private static final Pattern f_166456_ = Pattern.compile("(#(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*moj_import(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*(?:\"(.*)\"|<(.*)>))");
    private static final Pattern f_166457_ = Pattern.compile("(#(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*version(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*(\\d+))\\b");
    private static final Pattern f_166458_ = Pattern.compile("(?:^|\\v)(?:\\s|/\\*(?:[^*]|\\*+[^*/])*\\*+/|(//[^\\v]*))*\\z");

    public List<String> m_166461_(String p_166462_) {
        Context $$1 = new Context();
        List<String> $$2 = this.m_166469_(p_166462_, $$1, "");
        $$2.set(0, this.m_166463_($$2.get(0), $$1.f_166482_));
        return $$2;
    }

    private List<String> m_166469_(String p_166470_, Context p_166471_, String p_166472_) {
        int $$3 = p_166471_.f_166483_;
        int $$4 = 0;
        String $$5 = "";
        ArrayList $$6 = Lists.newArrayList();
        Matcher $$7 = f_166456_.matcher(p_166470_);
        while ($$7.find()) {
            boolean $$9;
            if (GlslPreprocessor.m_166476_(p_166470_, $$7, $$4)) continue;
            String $$8 = $$7.group(2);
            boolean bl = $$9 = $$8 != null;
            if (!$$9) {
                $$8 = $$7.group(3);
            }
            if ($$8 == null) continue;
            String $$10 = p_166470_.substring($$4, $$7.start(1));
            String $$11 = p_166472_ + $$8;
            Object $$12 = this.m_142138_($$9, $$11);
            if (!Strings.isNullOrEmpty((String)$$12)) {
                if (!StringUtil.m_145004_((String)$$12)) {
                    $$12 = (String)$$12 + System.lineSeparator();
                }
                int $$13 = ++p_166471_.f_166483_;
                List<String> $$14 = this.m_166469_((String)$$12, p_166471_, $$9 ? FileUtil.m_179922_($$11) : "");
                $$14.set(0, String.format(Locale.ROOT, "#line %d %d\n%s", 0, $$13, this.m_166466_($$14.get(0), p_166471_)));
                if (!StringUtils.isBlank((CharSequence)$$10)) {
                    $$6.add($$10);
                }
                $$6.addAll($$14);
            } else {
                String $$15 = $$9 ? String.format(Locale.ROOT, "/*#moj_import \"%s\"*/", $$8) : String.format(Locale.ROOT, "/*#moj_import <%s>*/", $$8);
                $$6.add($$5 + $$10 + $$15);
            }
            int $$16 = StringUtil.m_145002_(p_166470_.substring(0, $$7.end(1)));
            $$5 = String.format(Locale.ROOT, "#line %d %d", $$16, $$3);
            $$4 = $$7.end(1);
        }
        String $$17 = p_166470_.substring($$4);
        if (!StringUtils.isBlank((CharSequence)$$17)) {
            $$6.add($$5 + $$17);
        }
        return $$6;
    }

    private String m_166466_(String p_166467_, Context p_166468_) {
        Matcher $$2 = f_166457_.matcher(p_166467_);
        if ($$2.find() && GlslPreprocessor.m_166473_(p_166467_, $$2)) {
            p_166468_.f_166482_ = Math.max(p_166468_.f_166482_, Integer.parseInt($$2.group(2)));
            return p_166467_.substring(0, $$2.start(1)) + "/*" + p_166467_.substring($$2.start(1), $$2.end(1)) + "*/" + p_166467_.substring($$2.end(1));
        }
        return p_166467_;
    }

    private String m_166463_(String p_166464_, int p_166465_) {
        Matcher $$2 = f_166457_.matcher(p_166464_);
        if ($$2.find() && GlslPreprocessor.m_166473_(p_166464_, $$2)) {
            return p_166464_.substring(0, $$2.start(2)) + Math.max(p_166465_, Integer.parseInt($$2.group(2))) + p_166464_.substring($$2.end(2));
        }
        return p_166464_;
    }

    private static boolean m_166473_(String p_166474_, Matcher p_166475_) {
        return !GlslPreprocessor.m_166476_(p_166474_, p_166475_, 0);
    }

    private static boolean m_166476_(String p_166477_, Matcher p_166478_, int p_166479_) {
        int $$3 = p_166478_.start() - p_166479_;
        if ($$3 == 0) {
            return false;
        }
        Matcher $$4 = f_166458_.matcher(p_166477_.substring(p_166479_, p_166478_.start()));
        if (!$$4.find()) {
            return true;
        }
        int $$5 = $$4.end(1);
        return $$5 == p_166478_.start();
    }

    @Nullable
    public abstract String m_142138_(boolean var1, String var2);

    static final class Context {
        int f_166482_;
        int f_166483_;

        Context() {
        }
    }
}

