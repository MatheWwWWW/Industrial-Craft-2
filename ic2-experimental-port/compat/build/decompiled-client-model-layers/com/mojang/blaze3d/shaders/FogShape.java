/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.blaze3d.shaders;

public final class FogShape
extends Enum<FogShape> {
    public static final /* enum */ FogShape SPHERE = new FogShape(0);
    public static final /* enum */ FogShape CYLINDER = new FogShape(1);
    private final int f_202317_;
    private static final /* synthetic */ FogShape[] $VALUES;

    public static FogShape[] values() {
        return (FogShape[])$VALUES.clone();
    }

    public static FogShape valueOf(String p_202327_) {
        return Enum.valueOf(FogShape.class, p_202327_);
    }

    private FogShape(int p_202323_) {
        this.f_202317_ = p_202323_;
    }

    public int m_202324_() {
        return this.f_202317_;
    }

    private static /* synthetic */ FogShape[] m_202325_() {
        return new FogShape[]{SPHERE, CYLINDER};
    }

    static {
        $VALUES = FogShape.m_202325_();
    }
}

