/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling;

import java.nio.file.Path;
import java.util.List;
import net.minecraft.util.profiling.ResultField;

public interface ProfileResults {
    public static final char f_145956_ = '\u001e';

    public List<ResultField> m_6412_(String var1);

    public boolean m_142444_(Path var1);

    public long m_7229_();

    public int m_7230_();

    public long m_7236_();

    public int m_7317_();

    default public long m_18577_() {
        return this.m_7236_() - this.m_7229_();
    }

    default public int m_7315_() {
        return this.m_7317_() - this.m_7230_();
    }

    public String m_142368_();

    public static String m_18575_(String p_18576_) {
        return p_18576_.replace('\u001e', '.');
    }
}

