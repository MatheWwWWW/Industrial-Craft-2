/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class ScreenEffectRenderer {
    private static final ResourceLocation f_110714_ = new ResourceLocation("textures/misc/underwater.png");

    public static void m_110718_(Minecraft p_110719_, PoseStack p_110720_) {
        BlockState $$3;
        LocalPlayer $$2 = p_110719_.f_91074_;
        if (!$$2.f_19794_ && ($$3 = ScreenEffectRenderer.m_110716_($$2)) != null) {
            ScreenEffectRenderer.m_173296_(p_110719_.m_91289_().m_110907_().m_110882_($$3), p_110720_);
        }
        if (!p_110719_.f_91074_.m_5833_()) {
            if (p_110719_.f_91074_.m_204029_(FluidTags.f_13131_)) {
                ScreenEffectRenderer.m_110725_(p_110719_, p_110720_);
            }
            if (p_110719_.f_91074_.m_6060_()) {
                ScreenEffectRenderer.m_110728_(p_110719_, p_110720_);
            }
        }
    }

    @Nullable
    private static BlockState m_110716_(Player p_110717_) {
        BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
        for (int $$2 = 0; $$2 < 8; ++$$2) {
            double $$3 = p_110717_.m_20185_() + (double)(((float)(($$2 >> 0) % 2) - 0.5f) * p_110717_.m_20205_() * 0.8f);
            double $$4 = p_110717_.m_20188_() + (double)(((float)(($$2 >> 1) % 2) - 0.5f) * 0.1f);
            double $$5 = p_110717_.m_20189_() + (double)(((float)(($$2 >> 2) % 2) - 0.5f) * p_110717_.m_20205_() * 0.8f);
            $$1.m_122169_($$3, $$4, $$5);
            BlockState $$6 = p_110717_.f_19853_.m_8055_($$1);
            if ($$6.m_60799_() == RenderShape.INVISIBLE || !$$6.m_60831_(p_110717_.f_19853_, $$1)) continue;
            return $$6;
        }
        return null;
    }

    private static void m_173296_(TextureAtlasSprite p_173297_, PoseStack p_173298_) {
        RenderSystem.m_157456_(0, p_173297_.m_118414_().m_118330_());
        RenderSystem.m_157427_(GameRenderer::m_172814_);
        BufferBuilder $$2 = Tesselator.m_85913_().m_85915_();
        float $$3 = 0.1f;
        float $$4 = -1.0f;
        float $$5 = 1.0f;
        float $$6 = -1.0f;
        float $$7 = 1.0f;
        float $$8 = -0.5f;
        float $$9 = p_173297_.m_118409_();
        float $$10 = p_173297_.m_118410_();
        float $$11 = p_173297_.m_118411_();
        float $$12 = p_173297_.m_118412_();
        Matrix4f $$13 = p_173298_.m_85850_().m_85861_();
        $$2.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85818_);
        $$2.m_85982_($$13, -1.0f, -1.0f, -0.5f).m_85950_(0.1f, 0.1f, 0.1f, 1.0f).m_7421_($$10, $$12).m_5752_();
        $$2.m_85982_($$13, 1.0f, -1.0f, -0.5f).m_85950_(0.1f, 0.1f, 0.1f, 1.0f).m_7421_($$9, $$12).m_5752_();
        $$2.m_85982_($$13, 1.0f, 1.0f, -0.5f).m_85950_(0.1f, 0.1f, 0.1f, 1.0f).m_7421_($$9, $$11).m_5752_();
        $$2.m_85982_($$13, -1.0f, 1.0f, -0.5f).m_85950_(0.1f, 0.1f, 0.1f, 1.0f).m_7421_($$10, $$11).m_5752_();
        BufferUploader.m_231202_($$2.m_231175_());
    }

    private static void m_110725_(Minecraft p_110726_, PoseStack p_110727_) {
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_69493_();
        RenderSystem.m_157456_(0, f_110714_);
        BufferBuilder $$2 = Tesselator.m_85913_().m_85915_();
        BlockPos $$3 = new BlockPos(p_110726_.f_91074_.m_20185_(), p_110726_.f_91074_.m_20188_(), p_110726_.f_91074_.m_20189_());
        float $$4 = LightTexture.m_234316_(p_110726_.f_91074_.f_19853_.m_6042_(), p_110726_.f_91074_.f_19853_.m_46803_($$3));
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_157429_($$4, $$4, $$4, 0.1f);
        float $$5 = 4.0f;
        float $$6 = -1.0f;
        float $$7 = 1.0f;
        float $$8 = -1.0f;
        float $$9 = 1.0f;
        float $$10 = -0.5f;
        float $$11 = -p_110726_.f_91074_.m_146908_() / 64.0f;
        float $$12 = p_110726_.f_91074_.m_146909_() / 64.0f;
        Matrix4f $$13 = p_110727_.m_85850_().m_85861_();
        $$2.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        $$2.m_85982_($$13, -1.0f, -1.0f, -0.5f).m_7421_(4.0f + $$11, 4.0f + $$12).m_5752_();
        $$2.m_85982_($$13, 1.0f, -1.0f, -0.5f).m_7421_(0.0f + $$11, 4.0f + $$12).m_5752_();
        $$2.m_85982_($$13, 1.0f, 1.0f, -0.5f).m_7421_(0.0f + $$11, 0.0f + $$12).m_5752_();
        $$2.m_85982_($$13, -1.0f, 1.0f, -0.5f).m_7421_(4.0f + $$11, 0.0f + $$12).m_5752_();
        BufferUploader.m_231202_($$2.m_231175_());
        RenderSystem.m_69461_();
    }

    private static void m_110728_(Minecraft p_110729_, PoseStack p_110730_) {
        BufferBuilder $$2 = Tesselator.m_85913_().m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172814_);
        RenderSystem.m_69456_(519);
        RenderSystem.m_69458_(false);
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69493_();
        TextureAtlasSprite $$3 = ModelBakery.f_119220_.m_119204_();
        RenderSystem.m_157456_(0, $$3.m_118414_().m_118330_());
        float $$4 = $$3.m_118409_();
        float $$5 = $$3.m_118410_();
        float $$6 = ($$4 + $$5) / 2.0f;
        float $$7 = $$3.m_118411_();
        float $$8 = $$3.m_118412_();
        float $$9 = ($$7 + $$8) / 2.0f;
        float $$10 = $$3.m_118417_();
        float $$11 = Mth.m_14179_($$10, $$4, $$6);
        float $$12 = Mth.m_14179_($$10, $$5, $$6);
        float $$13 = Mth.m_14179_($$10, $$7, $$9);
        float $$14 = Mth.m_14179_($$10, $$8, $$9);
        float $$15 = 1.0f;
        for (int $$16 = 0; $$16 < 2; ++$$16) {
            p_110730_.m_85836_();
            float $$17 = -0.5f;
            float $$18 = 0.5f;
            float $$19 = -0.5f;
            float $$20 = 0.5f;
            float $$21 = -0.5f;
            p_110730_.m_85837_((float)(-($$16 * 2 - 1)) * 0.24f, -0.3f, 0.0);
            p_110730_.m_85845_(Vector3f.f_122225_.m_122240_((float)($$16 * 2 - 1) * 10.0f));
            Matrix4f $$22 = p_110730_.m_85850_().m_85861_();
            $$2.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85818_);
            $$2.m_85982_($$22, -0.5f, -0.5f, -0.5f).m_85950_(1.0f, 1.0f, 1.0f, 0.9f).m_7421_($$12, $$14).m_5752_();
            $$2.m_85982_($$22, 0.5f, -0.5f, -0.5f).m_85950_(1.0f, 1.0f, 1.0f, 0.9f).m_7421_($$11, $$14).m_5752_();
            $$2.m_85982_($$22, 0.5f, 0.5f, -0.5f).m_85950_(1.0f, 1.0f, 1.0f, 0.9f).m_7421_($$11, $$13).m_5752_();
            $$2.m_85982_($$22, -0.5f, 0.5f, -0.5f).m_85950_(1.0f, 1.0f, 1.0f, 0.9f).m_7421_($$12, $$13).m_5752_();
            BufferUploader.m_231202_($$2.m_231175_());
            p_110730_.m_85849_();
        }
        RenderSystem.m_69461_();
        RenderSystem.m_69458_(true);
        RenderSystem.m_69456_(515);
    }
}

