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
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LecternRenderer
implements BlockEntityRenderer<LecternBlockEntity> {
    private final BookModel f_112424_;

    public LecternRenderer(BlockEntityRendererProvider.Context p_173621_) {
        this.f_112424_ = new BookModel(p_173621_.m_173582_(ModelLayers.f_171271_));
    }

    @Override
    public void m_6922_(LecternBlockEntity p_112435_, float p_112436_, PoseStack p_112437_, MultiBufferSource p_112438_, int p_112439_, int p_112440_) {
        BlockState $$6 = p_112435_.m_58900_();
        if (!$$6.m_61143_(LecternBlock.f_54467_).booleanValue()) {
            return;
        }
        p_112437_.m_85836_();
        p_112437_.m_85837_(0.5, 1.0625, 0.5);
        float $$7 = $$6.m_61143_(LecternBlock.f_54465_).m_122427_().m_122435_();
        p_112437_.m_85845_(Vector3f.f_122225_.m_122240_(-$$7));
        p_112437_.m_85845_(Vector3f.f_122227_.m_122240_(67.5f));
        p_112437_.m_85837_(0.0, -0.125, 0.0);
        this.f_112424_.m_102292_(0.0f, 0.1f, 0.9f, 1.2f);
        VertexConsumer $$8 = EnchantTableRenderer.f_112405_.m_119194_(p_112438_, RenderType::m_110446_);
        this.f_112424_.m_102316_(p_112437_, $$8, p_112439_, p_112440_, 1.0f, 1.0f, 1.0f, 1.0f);
        p_112437_.m_85849_();
    }
}

