/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import java.io.IOException;
import java.util.List;
import java.util.function.IntSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.server.packs.resources.ResourceManager;

public class PostPass
implements AutoCloseable {
    private final EffectInstance f_110054_;
    public final RenderTarget f_110052_;
    public final RenderTarget f_110053_;
    private final List<IntSupplier> f_110055_ = Lists.newArrayList();
    private final List<String> f_110056_ = Lists.newArrayList();
    private final List<Integer> f_110057_ = Lists.newArrayList();
    private final List<Integer> f_110058_ = Lists.newArrayList();
    private Matrix4f f_110059_;

    public PostPass(ResourceManager p_110061_, String p_110062_, RenderTarget p_110063_, RenderTarget p_110064_) throws IOException {
        this.f_110054_ = new EffectInstance(p_110061_, p_110062_);
        this.f_110052_ = p_110063_;
        this.f_110053_ = p_110064_;
    }

    @Override
    public void close() {
        this.f_110054_.close();
    }

    public final String m_173046_() {
        return this.f_110054_.m_172571_();
    }

    public void m_110069_(String p_110070_, IntSupplier p_110071_, int p_110072_, int p_110073_) {
        this.f_110056_.add(this.f_110056_.size(), p_110070_);
        this.f_110055_.add(this.f_110055_.size(), p_110071_);
        this.f_110057_.add(this.f_110057_.size(), p_110072_);
        this.f_110058_.add(this.f_110058_.size(), p_110073_);
    }

    public void m_110067_(Matrix4f p_110068_) {
        this.f_110059_ = p_110068_;
    }

    public void m_110065_(float p_110066_) {
        this.f_110052_.m_83970_();
        float $$1 = this.f_110053_.f_83915_;
        float $$2 = this.f_110053_.f_83916_;
        RenderSystem.m_69949_(0, 0, (int)$$1, (int)$$2);
        this.f_110054_.m_108954_("DiffuseSampler", this.f_110052_::m_83975_);
        for (int $$3 = 0; $$3 < this.f_110055_.size(); ++$$3) {
            this.f_110054_.m_108954_(this.f_110056_.get($$3), this.f_110055_.get($$3));
            this.f_110054_.m_108960_("AuxSize" + $$3).m_7971_(this.f_110057_.get($$3).intValue(), this.f_110058_.get($$3).intValue());
        }
        this.f_110054_.m_108960_("ProjMat").m_5679_(this.f_110059_);
        this.f_110054_.m_108960_("InSize").m_7971_(this.f_110052_.f_83915_, this.f_110052_.f_83916_);
        this.f_110054_.m_108960_("OutSize").m_7971_($$1, $$2);
        this.f_110054_.m_108960_("Time").m_5985_(p_110066_);
        Minecraft $$4 = Minecraft.m_91087_();
        this.f_110054_.m_108960_("ScreenSize").m_7971_($$4.m_91268_().m_85441_(), $$4.m_91268_().m_85442_());
        this.f_110054_.m_108966_();
        this.f_110053_.m_83954_(Minecraft.f_91002_);
        this.f_110053_.m_83947_(false);
        RenderSystem.m_69456_(519);
        BufferBuilder $$5 = Tesselator.m_85913_().m_85915_();
        $$5.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85814_);
        $$5.m_5483_(0.0, 0.0, 500.0).m_5752_();
        $$5.m_5483_($$1, 0.0, 500.0).m_5752_();
        $$5.m_5483_($$1, $$2, 500.0).m_5752_();
        $$5.m_5483_(0.0, $$2, 500.0).m_5752_();
        BufferUploader.m_231209_($$5.m_231175_());
        RenderSystem.m_69456_(515);
        this.f_110054_.m_108965_();
        this.f_110053_.m_83970_();
        this.f_110052_.m_83963_();
        for (IntSupplier $$6 : this.f_110055_) {
            if (!($$6 instanceof RenderTarget)) continue;
            ((RenderTarget)((Object)$$6)).m_83963_();
        }
    }

    public EffectInstance m_110074_() {
        return this.f_110054_;
    }
}

