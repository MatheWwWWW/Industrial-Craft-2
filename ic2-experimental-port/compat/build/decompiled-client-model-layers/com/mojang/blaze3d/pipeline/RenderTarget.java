/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.blaze3d.pipeline;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;

public abstract class RenderTarget {
    private static final int f_166194_ = 0;
    private static final int f_166195_ = 1;
    private static final int f_166196_ = 2;
    private static final int f_166197_ = 3;
    public int f_83915_;
    public int f_83916_;
    public int f_83917_;
    public int f_83918_;
    public final boolean f_83919_;
    public int f_83920_;
    protected int f_83923_;
    protected int f_83924_;
    private final float[] f_83921_ = Util.m_137537_(() -> {
        float[] $$0 = new float[]{1.0f, 1.0f, 1.0f, 0.0f};
        return $$0;
    });
    public int f_83922_;

    public RenderTarget(boolean p_166199_) {
        this.f_83919_ = p_166199_;
        this.f_83920_ = -1;
        this.f_83923_ = -1;
        this.f_83924_ = -1;
    }

    public void m_83941_(int p_83942_, int p_83943_, boolean p_83944_) {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> this.m_83964_(p_83942_, p_83943_, p_83944_));
        } else {
            this.m_83964_(p_83942_, p_83943_, p_83944_);
        }
    }

    private void m_83964_(int p_83965_, int p_83966_, boolean p_83967_) {
        RenderSystem.m_187555_();
        GlStateManager.m_84513_();
        if (this.f_83920_ >= 0) {
            this.m_83930_();
        }
        this.m_83950_(p_83965_, p_83966_, p_83967_);
        GlStateManager.m_84486_(36160, 0);
    }

    public void m_83930_() {
        RenderSystem.m_187555_();
        this.m_83963_();
        this.m_83970_();
        if (this.f_83924_ > -1) {
            TextureUtil.m_85281_(this.f_83924_);
            this.f_83924_ = -1;
        }
        if (this.f_83923_ > -1) {
            TextureUtil.m_85281_(this.f_83923_);
            this.f_83923_ = -1;
        }
        if (this.f_83920_ > -1) {
            GlStateManager.m_84486_(36160, 0);
            GlStateManager.m_84502_(this.f_83920_);
            this.f_83920_ = -1;
        }
    }

    public void m_83945_(RenderTarget p_83946_) {
        RenderSystem.m_187555_();
        GlStateManager.m_84486_(36008, p_83946_.f_83920_);
        GlStateManager.m_84486_(36009, this.f_83920_);
        GlStateManager.m_84188_(0, 0, p_83946_.f_83915_, p_83946_.f_83916_, 0, 0, this.f_83915_, this.f_83916_, 256, 9728);
        GlStateManager.m_84486_(36160, 0);
    }

    public void m_83950_(int p_83951_, int p_83952_, boolean p_83953_) {
        RenderSystem.m_187555_();
        int $$3 = RenderSystem.m_69839_();
        if (p_83951_ <= 0 || p_83951_ > $$3 || p_83952_ <= 0 || p_83952_ > $$3) {
            throw new IllegalArgumentException("Window " + p_83951_ + "x" + p_83952_ + " size out of bounds (max. size: " + $$3 + ")");
        }
        this.f_83917_ = p_83951_;
        this.f_83918_ = p_83952_;
        this.f_83915_ = p_83951_;
        this.f_83916_ = p_83952_;
        this.f_83920_ = GlStateManager.m_84543_();
        this.f_83923_ = TextureUtil.m_85280_();
        if (this.f_83919_) {
            this.f_83924_ = TextureUtil.m_85280_();
            GlStateManager.m_84544_(this.f_83924_);
            GlStateManager.m_84331_(3553, 10241, 9728);
            GlStateManager.m_84331_(3553, 10240, 9728);
            GlStateManager.m_84331_(3553, 34892, 0);
            GlStateManager.m_84331_(3553, 10242, 33071);
            GlStateManager.m_84331_(3553, 10243, 33071);
            GlStateManager.m_84209_(3553, 0, 6402, this.f_83915_, this.f_83916_, 0, 6402, 5126, null);
        }
        this.m_83936_(9728);
        GlStateManager.m_84544_(this.f_83923_);
        GlStateManager.m_84331_(3553, 10242, 33071);
        GlStateManager.m_84331_(3553, 10243, 33071);
        GlStateManager.m_84209_(3553, 0, 32856, this.f_83915_, this.f_83916_, 0, 6408, 5121, null);
        GlStateManager.m_84486_(36160, this.f_83920_);
        GlStateManager.m_84173_(36160, 36064, 3553, this.f_83923_, 0);
        if (this.f_83919_) {
            GlStateManager.m_84173_(36160, 36096, 3553, this.f_83924_, 0);
        }
        this.m_83949_();
        this.m_83954_(p_83953_);
        this.m_83963_();
    }

    public void m_83936_(int p_83937_) {
        RenderSystem.m_187555_();
        this.f_83922_ = p_83937_;
        GlStateManager.m_84544_(this.f_83923_);
        GlStateManager.m_84331_(3553, 10241, p_83937_);
        GlStateManager.m_84331_(3553, 10240, p_83937_);
        GlStateManager.m_84544_(0);
    }

    public void m_83949_() {
        RenderSystem.m_187555_();
        int $$0 = GlStateManager.m_84508_(36160);
        if ($$0 == 36053) {
            return;
        }
        if ($$0 == 36054) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
        }
        if ($$0 == 36055) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
        }
        if ($$0 == 36059) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
        }
        if ($$0 == 36060) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
        }
        if ($$0 == 36061) {
            throw new RuntimeException("GL_FRAMEBUFFER_UNSUPPORTED");
        }
        if ($$0 == 1285) {
            throw new RuntimeException("GL_OUT_OF_MEMORY");
        }
        throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + $$0);
    }

    public void m_83956_() {
        RenderSystem.m_187554_();
        GlStateManager.m_84544_(this.f_83923_);
    }

    public void m_83963_() {
        RenderSystem.m_187555_();
        GlStateManager.m_84544_(0);
    }

    public void m_83947_(boolean p_83948_) {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> this.m_83961_(p_83948_));
        } else {
            this.m_83961_(p_83948_);
        }
    }

    private void m_83961_(boolean p_83962_) {
        RenderSystem.m_187555_();
        GlStateManager.m_84486_(36160, this.f_83920_);
        if (p_83962_) {
            GlStateManager.m_84430_(0, 0, this.f_83917_, this.f_83918_);
        }
    }

    public void m_83970_() {
        if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> GlStateManager.m_84486_(36160, 0));
        } else {
            GlStateManager.m_84486_(36160, 0);
        }
    }

    public void m_83931_(float p_83932_, float p_83933_, float p_83934_, float p_83935_) {
        this.f_83921_[0] = p_83932_;
        this.f_83921_[1] = p_83933_;
        this.f_83921_[2] = p_83934_;
        this.f_83921_[3] = p_83935_;
    }

    public void m_83938_(int p_83939_, int p_83940_) {
        this.m_83957_(p_83939_, p_83940_, true);
    }

    public void m_83957_(int p_83958_, int p_83959_, boolean p_83960_) {
        RenderSystem.m_187553_();
        if (!RenderSystem.m_69583_()) {
            RenderSystem.m_69879_(() -> this.m_83971_(p_83958_, p_83959_, p_83960_));
        } else {
            this.m_83971_(p_83958_, p_83959_, p_83960_);
        }
    }

    private void m_83971_(int p_83972_, int p_83973_, boolean p_83974_) {
        RenderSystem.m_187554_();
        GlStateManager.m_84300_(true, true, true, false);
        GlStateManager.m_84507_();
        GlStateManager.m_84298_(false);
        GlStateManager.m_84430_(0, 0, p_83972_, p_83973_);
        if (p_83974_) {
            GlStateManager.m_84519_();
        }
        Minecraft $$3 = Minecraft.m_91087_();
        ShaderInstance $$4 = $$3.f_91063_.f_172635_;
        $$4.m_173350_("DiffuseSampler", this.f_83923_);
        Matrix4f $$5 = Matrix4f.m_27636_(p_83972_, -p_83973_, 1000.0f, 3000.0f);
        RenderSystem.m_157425_($$5);
        if ($$4.f_173308_ != null) {
            $$4.f_173308_.m_5679_(Matrix4f.m_27653_(0.0f, 0.0f, -2000.0f));
        }
        if ($$4.f_173309_ != null) {
            $$4.f_173309_.m_5679_($$5);
        }
        $$4.m_173363_();
        float $$6 = p_83972_;
        float $$7 = p_83973_;
        float $$8 = (float)this.f_83917_ / (float)this.f_83915_;
        float $$9 = (float)this.f_83918_ / (float)this.f_83916_;
        Tesselator $$10 = RenderSystem.m_69883_();
        BufferBuilder $$11 = $$10.m_85915_();
        $$11.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
        $$11.m_5483_(0.0, $$7, 0.0).m_7421_(0.0f, 0.0f).m_6122_(255, 255, 255, 255).m_5752_();
        $$11.m_5483_($$6, $$7, 0.0).m_7421_($$8, 0.0f).m_6122_(255, 255, 255, 255).m_5752_();
        $$11.m_5483_($$6, 0.0, 0.0).m_7421_($$8, $$9).m_6122_(255, 255, 255, 255).m_5752_();
        $$11.m_5483_(0.0, 0.0, 0.0).m_7421_(0.0f, $$9).m_6122_(255, 255, 255, 255).m_5752_();
        BufferUploader.m_231209_($$11.m_231175_());
        $$4.m_173362_();
        GlStateManager.m_84298_(true);
        GlStateManager.m_84300_(true, true, true, true);
    }

    public void m_83954_(boolean p_83955_) {
        RenderSystem.m_187555_();
        this.m_83947_(true);
        GlStateManager.m_84318_(this.f_83921_[0], this.f_83921_[1], this.f_83921_[2], this.f_83921_[3]);
        int $$1 = 16384;
        if (this.f_83919_) {
            GlStateManager.m_84121_(1.0);
            $$1 |= 0x100;
        }
        GlStateManager.m_84266_($$1, p_83955_);
        this.m_83970_();
    }

    public int m_83975_() {
        return this.f_83923_;
    }

    public int m_83980_() {
        return this.f_83924_;
    }
}

