/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;

public interface ParticleRenderType {
    public static final ParticleRenderType f_107429_ = new ParticleRenderType(){

        @Override
        public void m_6505_(BufferBuilder p_107441_, TextureManager p_107442_) {
            RenderSystem.m_69478_();
            RenderSystem.m_69453_();
            RenderSystem.m_69458_(true);
            RenderSystem.m_157456_(0, TextureAtlas.f_118259_);
            p_107441_.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85813_);
        }

        @Override
        public void m_6294_(Tesselator p_107444_) {
            p_107444_.m_85914_();
        }

        public String toString() {
            return "TERRAIN_SHEET";
        }
    };
    public static final ParticleRenderType f_107430_ = new ParticleRenderType(){

        @Override
        public void m_6505_(BufferBuilder p_107448_, TextureManager p_107449_) {
            RenderSystem.m_69461_();
            RenderSystem.m_69458_(true);
            RenderSystem.m_157427_(GameRenderer::m_172829_);
            RenderSystem.m_157456_(0, TextureAtlas.f_118260_);
            p_107448_.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85813_);
        }

        @Override
        public void m_6294_(Tesselator p_107451_) {
            p_107451_.m_85914_();
        }

        public String toString() {
            return "PARTICLE_SHEET_OPAQUE";
        }
    };
    public static final ParticleRenderType f_107431_ = new ParticleRenderType(){

        @Override
        public void m_6505_(BufferBuilder p_107455_, TextureManager p_107456_) {
            RenderSystem.m_69458_(true);
            RenderSystem.m_157456_(0, TextureAtlas.f_118260_);
            RenderSystem.m_69478_();
            RenderSystem.m_69408_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            p_107455_.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85813_);
        }

        @Override
        public void m_6294_(Tesselator p_107458_) {
            p_107458_.m_85914_();
        }

        public String toString() {
            return "PARTICLE_SHEET_TRANSLUCENT";
        }
    };
    public static final ParticleRenderType f_107432_ = new ParticleRenderType(){

        @Override
        public void m_6505_(BufferBuilder p_107462_, TextureManager p_107463_) {
            RenderSystem.m_69461_();
            RenderSystem.m_69458_(true);
            RenderSystem.m_157456_(0, TextureAtlas.f_118260_);
            p_107462_.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85813_);
        }

        @Override
        public void m_6294_(Tesselator p_107465_) {
            p_107465_.m_85914_();
        }

        public String toString() {
            return "PARTICLE_SHEET_LIT";
        }
    };
    public static final ParticleRenderType f_107433_ = new ParticleRenderType(){

        @Override
        public void m_6505_(BufferBuilder p_107469_, TextureManager p_107470_) {
            RenderSystem.m_69458_(true);
            RenderSystem.m_69461_();
        }

        @Override
        public void m_6294_(Tesselator p_107472_) {
        }

        public String toString() {
            return "CUSTOM";
        }
    };
    public static final ParticleRenderType f_107434_ = new ParticleRenderType(){

        @Override
        public void m_6505_(BufferBuilder p_107476_, TextureManager p_107477_) {
        }

        @Override
        public void m_6294_(Tesselator p_107479_) {
        }

        public String toString() {
            return "NO_RENDER";
        }
    };

    public void m_6505_(BufferBuilder var1, TextureManager var2);

    public void m_6294_(Tesselator var1);
}

