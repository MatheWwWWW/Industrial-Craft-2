/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;

public class EnchantTableRenderer
implements BlockEntityRenderer<EnchantmentTableBlockEntity> {
    public static final Material f_112405_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/enchanting_table_book"));
    private final BookModel f_112406_;

    public EnchantTableRenderer(BlockEntityRendererProvider.Context p_173619_) {
        this.f_112406_ = new BookModel(p_173619_.m_173582_(ModelLayers.f_171271_));
    }

    @Override
    public void m_6922_(EnchantmentTableBlockEntity p_112418_, float p_112419_, PoseStack p_112420_, MultiBufferSource p_112421_, int p_112422_, int p_112423_) {
        float $$7;
        p_112420_.m_85836_();
        p_112420_.m_85837_(0.5, 0.75, 0.5);
        float $$6 = (float)p_112418_.f_59251_ + p_112419_;
        p_112420_.m_85837_(0.0, 0.1f + Mth.m_14031_($$6 * 0.1f) * 0.01f, 0.0);
        for ($$7 = p_112418_.f_59258_ - p_112418_.f_59259_; $$7 >= (float)Math.PI; $$7 -= (float)Math.PI * 2) {
        }
        while ($$7 < (float)(-Math.PI)) {
            $$7 += (float)Math.PI * 2;
        }
        float $$8 = p_112418_.f_59259_ + $$7 * p_112419_;
        p_112420_.m_85845_(Vector3f.f_122225_.m_122270_(-$$8));
        p_112420_.m_85845_(Vector3f.f_122227_.m_122240_(80.0f));
        float $$9 = Mth.m_14179_(p_112419_, p_112418_.f_59253_, p_112418_.f_59252_);
        float $$10 = Mth.m_14187_($$9 + 0.25f) * 1.6f - 0.3f;
        float $$11 = Mth.m_14187_($$9 + 0.75f) * 1.6f - 0.3f;
        float $$12 = Mth.m_14179_(p_112419_, p_112418_.f_59257_, p_112418_.f_59256_);
        this.f_112406_.m_102292_($$6, Mth.m_14036_($$10, 0.0f, 1.0f), Mth.m_14036_($$11, 0.0f, 1.0f), $$12);
        VertexConsumer $$13 = f_112405_.m_119194_(p_112421_, RenderType::m_110446_);
        this.f_112406_.m_102316_(p_112420_, $$13, p_112422_, p_112423_, 1.0f, 1.0f, 1.0f, 1.0f);
        p_112420_.m_85849_();
    }
}

