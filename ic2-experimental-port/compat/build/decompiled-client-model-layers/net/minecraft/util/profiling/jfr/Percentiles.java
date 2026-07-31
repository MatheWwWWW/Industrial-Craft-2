/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.math.Quantiles
 *  com.google.common.math.Quantiles$ScaleAndIndexes
 *  it.unimi.dsi.fastutil.ints.Int2DoubleRBTreeMap
 *  it.unimi.dsi.fastutil.ints.Int2DoubleSortedMap
 *  it.unimi.dsi.fastutil.ints.Int2DoubleSortedMaps
 */
package net.minecraft.util.profiling.jfr;

import com.google.common.math.Quantiles;
import it.unimi.dsi.fastutil.ints.Int2DoubleRBTreeMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleSortedMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleSortedMaps;
import java.util.Comparator;
import java.util.Map;
import net.minecraft.Util;

public class Percentiles {
    public static final Quantiles.ScaleAndIndexes f_185382_ = Quantiles.scale((int)100).indexes(new int[]{50, 75, 90, 99});

    private Percentiles() {
    }

    public static Map<Integer, Double> m_185392_(long[] p_185393_) {
        return p_185393_.length == 0 ? Map.of() : Percentiles.m_185385_(f_185382_.compute(p_185393_));
    }

    public static Map<Integer, Double> m_185390_(double[] p_185391_) {
        return p_185391_.length == 0 ? Map.of() : Percentiles.m_185385_(f_185382_.compute(p_185391_));
    }

    private static Map<Integer, Double> m_185385_(Map<Integer, Double> p_185386_) {
        Int2DoubleSortedMap $$1 = (Int2DoubleSortedMap)Util.m_137469_(new Int2DoubleRBTreeMap(Comparator.reverseOrder()), p_185389_ -> p_185389_.putAll(p_185386_));
        return Int2DoubleSortedMaps.unmodifiable((Int2DoubleSortedMap)$$1);
    }
}

