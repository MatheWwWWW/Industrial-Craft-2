/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;

public class VertexBuffer
implements AutoCloseable {
    private int f_231217_;
    private int f_166860_;
    private int f_166862_;
    @Nullable
    private VertexFormat f_85917_;
    @Nullable
    private RenderSystem.AutoStorageIndexBuffer f_166865_;
    private VertexFormat.IndexType f_166861_;
    private int f_166863_;
    private VertexFormat.Mode f_166864_;

    public VertexBuffer() {
        RenderSystem.m_187554_();
        this.f_231217_ = GlStateManager.m_84537_();
        this.f_166860_ = GlStateManager.m_84537_();
        this.f_166862_ = GlStateManager.m_157089_();
    }

    public void m_231221_(BufferBuilder.RenderedBuffer p_231222_) {
        if (this.m_231230_()) {
            return;
        }
        RenderSystem.m_187554_();
        try {
            BufferBuilder.DrawState $$1 = p_231222_.m_231198_();
            this.f_85917_ = this.m_231218_($$1, p_231222_.m_231196_());
            this.f_166865_ = this.m_231223_($$1, p_231222_.m_231197_());
            this.f_166863_ = $$1.f_166797_();
            this.f_166861_ = $$1.f_166798_();
            this.f_166864_ = $$1.f_85735_();
        }
        finally {
            p_231222_.m_231200_();
        }
    }

    private VertexFormat m_231218_(BufferBuilder.DrawState p_231219_, ByteBuffer p_231220_) {
        boolean $$2 = false;
        if (!p_231219_.f_85733_().equals(this.f_85917_)) {
            if (this.f_85917_ != null) {
                this.f_85917_.m_86024_();
            }
            GlStateManager.m_84480_(34962, this.f_231217_);
            p_231219_.f_85733_().m_166912_();
            $$2 = true;
        }
        if (!p_231219_.f_166799_()) {
            if (!$$2) {
                GlStateManager.m_84480_(34962, this.f_231217_);
            }
            RenderSystem.m_69525_(34962, p_231220_, 35044);
        }
        return p_231219_.f_85733_();
    }

    @Nullable
    private RenderSystem.AutoStorageIndexBuffer m_231223_(BufferBuilder.DrawState p_231224_, ByteBuffer p_231225_) {
        if (p_231224_.f_166800_()) {
            RenderSystem.AutoStorageIndexBuffer $$2 = RenderSystem.m_221941_(p_231224_.f_85735_());
            if ($$2 != this.f_166865_ || !$$2.m_221944_(p_231224_.f_166797_())) {
                $$2.m_221946_(p_231224_.f_166797_());
            }
            return $$2;
        }
        GlStateManager.m_84480_(34963, this.f_166860_);
        RenderSystem.m_69525_(34963, p_231225_, 35044);
        return null;
    }

    public void m_85921_() {
        BufferUploader.m_231208_();
        GlStateManager.m_157068_(this.f_166862_);
    }

    public static void m_85931_() {
        BufferUploader.m_231208_();
        GlStateManager.m_157068_(0);
    }

    public void m_166882_() {
        RenderSystem.m_157186_(this.f_166864_.f_166946_, this.f_166863_, this.m_231231_().f_166923_);
    }

    private VertexFormat.IndexType m_231231_() {
        RenderSystem.AutoStorageIndexBuffer $$0 = this.f_166865_;
        return $$0 != null ? $$0.m_157483_() : this.f_166861_;
    }

    public void m_166867_(Matrix4f p_166868_, Matrix4f p_166869_, ShaderInstance p_166870_) {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> this.m_166876_(p_166868_.m_27658_(), p_166869_.m_27658_(), p_166870_));
        } else {
            this.m_166876_(p_166868_, p_166869_, p_166870_);
        }
    }

    private void m_166876_(Matrix4f p_166877_, Matrix4f p_166878_, ShaderInstance p_166879_) {
        for (int $$3 = 0; $$3 < 12; ++$$3) {
            int $$4 = RenderSystem.m_157203_($$3);
            p_166879_.m_173350_("Sampler" + $$3, $$4);
        }
        if (p_166879_.f_173308_ != null) {
            p_166879_.f_173308_.m_5679_(p_166877_);
        }
        if (p_166879_.f_173309_ != null) {
            p_166879_.f_173309_.m_5679_(p_166878_);
        }
        if (p_166879_.f_200956_ != null) {
            p_166879_.f_200956_.m_200759_(RenderSystem.m_200906_());
        }
        if (p_166879_.f_173312_ != null) {
            p_166879_.f_173312_.m_5941_(RenderSystem.m_157197_());
        }
        if (p_166879_.f_173315_ != null) {
            p_166879_.f_173315_.m_5985_(RenderSystem.m_157200_());
        }
        if (p_166879_.f_173316_ != null) {
            p_166879_.f_173316_.m_5985_(RenderSystem.m_157199_());
        }
        if (p_166879_.f_173317_ != null) {
            p_166879_.f_173317_.m_5941_(RenderSystem.m_157198_());
        }
        if (p_166879_.f_202432_ != null) {
            p_166879_.f_202432_.m_142617_(RenderSystem.m_202041_().m_202324_());
        }
        if (p_166879_.f_173310_ != null) {
            p_166879_.f_173310_.m_5679_(RenderSystem.m_157207_());
        }
        if (p_166879_.f_173319_ != null) {
            p_166879_.f_173319_.m_5985_(RenderSystem.m_157201_());
        }
        if (p_166879_.f_173311_ != null) {
            Window $$5 = Minecraft.m_91087_().m_91268_();
            p_166879_.f_173311_.m_7971_($$5.m_85441_(), $$5.m_85442_());
        }
        if (p_166879_.f_173318_ != null && (this.f_166864_ == VertexFormat.Mode.LINES || this.f_166864_ == VertexFormat.Mode.LINE_STRIP)) {
            p_166879_.f_173318_.m_5985_(RenderSystem.m_157202_());
        }
        RenderSystem.m_157461_(p_166879_);
        p_166879_.m_173363_();
        this.m_166882_();
        p_166879_.m_173362_();
    }

    @Override
    public void close() {
        if (this.f_231217_ >= 0) {
            RenderSystem.m_69529_(this.f_231217_);
            this.f_231217_ = -1;
        }
        if (this.f_166860_ >= 0) {
            RenderSystem.m_69529_(this.f_166860_);
            this.f_166860_ = -1;
        }
        if (this.f_166862_ >= 0) {
            RenderSystem.m_157213_(this.f_166862_);
            this.f_166862_ = -1;
        }
    }

    public VertexFormat m_166892_() {
        return this.f_85917_;
    }

    public boolean m_231230_() {
        return this.f_166862_ == -1;
    }
}

