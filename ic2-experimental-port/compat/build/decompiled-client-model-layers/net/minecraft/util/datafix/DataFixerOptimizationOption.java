/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.datafix;

public final class DataFixerOptimizationOption
extends Enum<DataFixerOptimizationOption> {
    public static final /* enum */ DataFixerOptimizationOption UNINITIALIZED_UNOPTIMIZED = new DataFixerOptimizationOption();
    public static final /* enum */ DataFixerOptimizationOption UNINITIALIZED_OPTIMIZED = new DataFixerOptimizationOption();
    public static final /* enum */ DataFixerOptimizationOption INITIALIZED_UNOPTIMIZED = new DataFixerOptimizationOption();
    public static final /* enum */ DataFixerOptimizationOption INITIALIZED_OPTIMIZED = new DataFixerOptimizationOption();
    private static final /* synthetic */ DataFixerOptimizationOption[] $VALUES;

    public static DataFixerOptimizationOption[] values() {
        return (DataFixerOptimizationOption[])$VALUES.clone();
    }

    public static DataFixerOptimizationOption valueOf(String p_216510_) {
        return Enum.valueOf(DataFixerOptimizationOption.class, p_216510_);
    }

    private static /* synthetic */ DataFixerOptimizationOption[] m_216508_() {
        return new DataFixerOptimizationOption[]{UNINITIALIZED_UNOPTIMIZED, UNINITIALIZED_OPTIMIZED, INITIALIZED_UNOPTIMIZED, INITIALIZED_OPTIMIZED};
    }

    static {
        $VALUES = DataFixerOptimizationOption.m_216508_();
    }
}

