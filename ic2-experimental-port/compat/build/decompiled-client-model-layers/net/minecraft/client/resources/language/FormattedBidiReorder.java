/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.ibm.icu.lang.UCharacter
 *  com.ibm.icu.text.ArabicShaping
 *  com.ibm.icu.text.Bidi
 *  com.ibm.icu.text.BidiRun
 */
package net.minecraft.client.resources.language;

import com.google.common.collect.Lists;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.Bidi;
import com.ibm.icu.text.BidiRun;
import java.util.ArrayList;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.SubStringSource;
import net.minecraft.util.FormattedCharSequence;

public class FormattedBidiReorder {
    public static FormattedCharSequence m_118931_(FormattedText p_118932_, boolean p_118933_) {
        SubStringSource $$2 = SubStringSource.m_131251_(p_118932_, UCharacter::getMirror, FormattedBidiReorder::m_118929_);
        Bidi $$3 = new Bidi($$2.m_131235_(), p_118933_ ? 127 : 126);
        $$3.setReorderingMode(0);
        ArrayList $$4 = Lists.newArrayList();
        int $$5 = $$3.countRuns();
        for (int $$6 = 0; $$6 < $$5; ++$$6) {
            BidiRun $$7 = $$3.getVisualRun($$6);
            $$4.addAll($$2.m_131236_($$7.getStart(), $$7.getLength(), $$7.isOddRun()));
        }
        return FormattedCharSequence.m_13722_($$4);
    }

    private static String m_118929_(String p_118930_) {
        try {
            return new ArabicShaping(8).shape(p_118930_);
        }
        catch (Exception $$1) {
            return p_118930_;
        }
    }
}

