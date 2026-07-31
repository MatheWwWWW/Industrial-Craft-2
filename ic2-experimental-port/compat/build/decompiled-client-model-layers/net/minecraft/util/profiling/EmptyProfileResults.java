/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.ResultField;

public class EmptyProfileResults
implements ProfileResults {
    public static final EmptyProfileResults f_18441_ = new EmptyProfileResults();

    private EmptyProfileResults() {
    }

    @Override
    public List<ResultField> m_6412_(String p_18448_) {
        return Collections.emptyList();
    }

    @Override
    public boolean m_142444_(Path p_145937_) {
        return false;
    }

    @Override
    public long m_7229_() {
        return 0L;
    }

    @Override
    public int m_7230_() {
        return 0;
    }

    @Override
    public long m_7236_() {
        return 0L;
    }

    @Override
    public int m_7317_() {
        return 0;
    }

    @Override
    public String m_142368_() {
        return "";
    }
}

