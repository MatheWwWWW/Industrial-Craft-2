/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

public class CubeMap {
    private static final int f_172561_ = 6;
    private final ResourceLocation[] f_108846_ = new ResourceLocation[6];

    public CubeMap(ResourceLocation p_108848_) {
        for (int $$1 = 0; $$1 < 6; ++$$1) {
            this.f_108846_[$$1] = new ResourceLocation(p_108848_.m_135827_(), p_108848_.m_135815_() + "_" + $$1 + ".png");
        }
    }

    public void m_108849_(Minecraft p_108850_, float p_108851_, float p_108852_, float p_108853_) {
        Tesselator $$4 = Tesselator.m_85913_();
        BufferBuilder $$5 = $$4.m_85915_();
        Matrix4f $$6 = Matrix4f.m_27625_(85.0, (float)p_108850_.m_91268_().m_85441_() / (float)p_108850_.m_91268_().m_85442_(), 0.05f, 10.0f);
        RenderSystem.m_157183_();
        RenderSystem.m_157425_($$6);
        PoseStack $$7 = RenderSystem.m_157191_();
        $$7.m_85836_();
        $$7.m_166856_();
        $$7.m_85845_(Vector3f.f_122223_.m_122240_(180.0f));
        RenderSystem.m_157182_();
        RenderSystem.m_157427_(GameRenderer::m_172820_);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.m_69478_();
        RenderSystem.m_69464_();
        RenderSystem.m_69458_(false);
        RenderSystem.m_69453_();
        int $$8 = 2;
        for (int $$9 = 0; $$9 < 4; ++$$9) {
            $$7.m_85836_();
            float $$10 = ((float)($$9 % 2) / 2.0f - 0.5f) / 256.0f;
            float $$11 = ((float)($$9 / 2) / 2.0f - 0.5f) / 256.0f;
            float $$12 = 0.0f;
            $$7.m_85837_($$10, $$11, 0.0);
            $$7.m_85845_(Vector3f.f_122223_.m_122240_(p_108851_));
            $$7.m_85845_(Vector3f.f_122225_.m_122240_(p_108852_));
            RenderSystem.m_157182_();
            for (int $$13 = 0; $$13 < 6; ++$$13) {
                RenderSystem.m_157456_(0, this.f_108846_[$$13]);
                $$5.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
                int $$14 = Math.round(255.0f * p_108853_) / ($$9 + 1);
                if ($$13 == 0) {
                    $$5.m_5483_(-1.0, -1.0, 1.0).m_7421_(0.0f, 0.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(-1.0, 1.0, 1.0).m_7421_(0.0f, 1.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(1.0, 1.0, 1.0).m_7421_(1.0f, 1.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(1.0, -1.0, 1.0).m_7421_(1.0f, 0.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                }
                if ($$13 == 1) {
                    $$5.m_5483_(1.0, -1.0, 1.0).m_7421_(0.0f, 0.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(1.0, 1.0, 1.0).m_7421_(0.0f, 1.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(1.0, 1.0, -1.0).m_7421_(1.0f, 1.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(1.0, -1.0, -1.0).m_7421_(1.0f, 0.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                }
                if ($$13 == 2) {
                    $$5.m_5483_(1.0, -1.0, -1.0).m_7421_(0.0f, 0.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(1.0, 1.0, -1.0).m_7421_(0.0f, 1.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(-1.0, 1.0, -1.0).m_7421_(1.0f, 1.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(-1.0, -1.0, -1.0).m_7421_(1.0f, 0.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                }
                if ($$13 == 3) {
                    $$5.m_5483_(-1.0, -1.0, -1.0).m_7421_(0.0f, 0.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(-1.0, 1.0, -1.0).m_7421_(0.0f, 1.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(-1.0, 1.0, 1.0).m_7421_(1.0f, 1.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(-1.0, -1.0, 1.0).m_7421_(1.0f, 0.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                }
                if ($$13 == 4) {
                    $$5.m_5483_(-1.0, -1.0, -1.0).m_7421_(0.0f, 0.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(-1.0, -1.0, 1.0).m_7421_(0.0f, 1.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(1.0, -1.0, 1.0).m_7421_(1.0f, 1.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(1.0, -1.0, -1.0).m_7421_(1.0f, 0.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                }
                if ($$13 == 5) {
                    $$5.m_5483_(-1.0, 1.0, 1.0).m_7421_(0.0f, 0.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(-1.0, 1.0, -1.0).m_7421_(0.0f, 1.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(1.0, 1.0, -1.0).m_7421_(1.0f, 1.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                    $$5.m_5483_(1.0, 1.0, 1.0).m_7421_(1.0f, 0.0f).m_6122_(255, 255, 255, $$14).m_5752_();
                }
                $$4.m_85914_();
            }
            $$7.m_85849_();
            RenderSystem.m_157182_();
            RenderSystem.m_69444_(true, true, true, false);
        }
        RenderSystem.m_69444_(true, true, true, true);
        RenderSystem.m_157424_();
        $$7.m_85849_();
        RenderSystem.m_157182_();
        RenderSystem.m_69458_(true);
        RenderSystem.m_69481_();
        RenderSystem.m_69482_();
    }

    public CompletableFuture<Void> m_108854_(TextureManager p_108855_, Executor p_108856_) {
        CompletableFuture[] $$2 = new CompletableFuture[6];
        for (int $$3 = 0; $$3 < $$2.length; ++$$3) {
            $$2[$$3] = p_108855_.m_118501_(this.f_108846_[$$3], p_108856_);
        }
        return CompletableFuture.allOf($$2);
    }
}

