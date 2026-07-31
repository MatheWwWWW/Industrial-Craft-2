/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;

public class Tesselator {
    private static final int f_166857_ = 0x800000;
    private static final int f_166858_ = 0x200000;
    private final BufferBuilder f_85907_;
    private static final Tesselator f_85908_ = new Tesselator();

    public static Tesselator m_85913_() {
        RenderSystem.m_187553_();
        return f_85908_;
    }

    public Tesselator(int p_85912_) {
        this.f_85907_ = new BufferBuilder(p_85912_);
    }

    public Tesselator() {
        this(0x200000);
    }

    public void m_85914_() {
        BufferUploader.m_231202_(this.f_85907_.m_231175_());
    }

    public BufferBuilder m_85915_() {
        return this.f_85907_;
    }
}

