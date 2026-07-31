/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

@FunctionalInterface
public interface TimeSource {
    public long m_239336_(TimeUnit var1);

    public static interface NanoTimeSource
    extends TimeSource,
    LongSupplier {
        @Override
        default public long m_239336_(TimeUnit p_239379_) {
            return p_239379_.convert(this.getAsLong(), TimeUnit.NANOSECONDS);
        }
    }
}

