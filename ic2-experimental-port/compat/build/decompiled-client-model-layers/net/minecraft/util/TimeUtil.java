/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import java.util.concurrent.TimeUnit;
import net.minecraft.util.valueproviders.UniformInt;

public class TimeUtil {
    public static final long f_145016_ = TimeUnit.SECONDS.toNanos(1L);
    public static final long f_145017_ = TimeUnit.MILLISECONDS.toNanos(1L);

    public static UniformInt m_145020_(int p_145021_, int p_145022_) {
        return UniformInt.m_146622_(p_145021_ * 20, p_145022_ * 20);
    }
}

