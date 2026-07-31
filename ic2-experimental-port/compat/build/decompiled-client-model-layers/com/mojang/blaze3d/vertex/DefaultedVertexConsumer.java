/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.vertex.VertexConsumer;

public abstract class DefaultedVertexConsumer
implements VertexConsumer {
    protected boolean f_85824_;
    protected int f_85825_ = 255;
    protected int f_85826_ = 255;
    protected int f_85827_ = 255;
    protected int f_85828_ = 255;

    @Override
    public void m_7404_(int p_85830_, int p_85831_, int p_85832_, int p_85833_) {
        this.f_85825_ = p_85830_;
        this.f_85826_ = p_85831_;
        this.f_85827_ = p_85832_;
        this.f_85828_ = p_85833_;
        this.f_85824_ = true;
    }

    @Override
    public void m_141991_() {
        this.f_85824_ = false;
    }
}

