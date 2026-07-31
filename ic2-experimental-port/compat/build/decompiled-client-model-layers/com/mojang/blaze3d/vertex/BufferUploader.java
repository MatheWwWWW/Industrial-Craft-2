/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import javax.annotation.Nullable;

public class BufferUploader {
    @Nullable
    private static VertexBuffer f_231201_;

    public static void m_166835_() {
        if (f_231201_ != null) {
            BufferUploader.m_231208_();
            VertexBuffer.m_85931_();
        }
    }

    public static void m_231208_() {
        f_231201_ = null;
    }

    public static void m_231202_(BufferBuilder.RenderedBuffer p_231203_) {
        if (!RenderSystem.m_69587_()) {
            RenderSystem.m_69879_(() -> BufferUploader.m_231211_(p_231203_));
        } else {
            BufferUploader.m_231211_(p_231203_);
        }
    }

    private static void m_231211_(BufferBuilder.RenderedBuffer p_231212_) {
        VertexBuffer $$1 = BufferUploader.m_231213_(p_231212_);
        if ($$1 != null) {
            $$1.m_166867_(RenderSystem.m_157190_(), RenderSystem.m_157192_(), RenderSystem.m_157196_());
        }
    }

    public static void m_231209_(BufferBuilder.RenderedBuffer p_231210_) {
        VertexBuffer $$1 = BufferUploader.m_231213_(p_231210_);
        if ($$1 != null) {
            $$1.m_166882_();
        }
    }

    @Nullable
    private static VertexBuffer m_231213_(BufferBuilder.RenderedBuffer p_231214_) {
        RenderSystem.m_187554_();
        if (p_231214_.m_231199_()) {
            p_231214_.m_231200_();
            return null;
        }
        VertexBuffer $$1 = BufferUploader.m_231206_(p_231214_.m_231198_().f_85733_());
        $$1.m_231221_(p_231214_);
        return $$1;
    }

    private static VertexBuffer m_231206_(VertexFormat p_231207_) {
        VertexBuffer $$1 = p_231207_.m_231233_();
        BufferUploader.m_231204_($$1);
        return $$1;
    }

    private static void m_231204_(VertexBuffer p_231205_) {
        if (p_231205_ != f_231201_) {
            p_231205_.m_85921_();
            f_231201_ = p_231205_;
        }
    }
}

