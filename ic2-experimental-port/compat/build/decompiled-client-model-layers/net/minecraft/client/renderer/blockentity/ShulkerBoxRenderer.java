/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ShulkerBoxRenderer
implements BlockEntityRenderer<ShulkerBoxBlockEntity> {
    private final ShulkerModel<?> f_112466_;

    public ShulkerBoxRenderer(BlockEntityRendererProvider.Context p_173626_) {
        this.f_112466_ = new ShulkerModel(p_173626_.m_173582_(ModelLayers.f_171180_));
    }

    @Override
    public void m_6922_(ShulkerBoxBlockEntity p_112478_, float p_112479_, PoseStack p_112480_, MultiBufferSource p_112481_, int p_112482_, int p_112483_) {
        Material $$10;
        DyeColor $$8;
        BlockState $$7;
        Direction $$6 = Direction.UP;
        if (p_112478_.m_58898_() && ($$7 = p_112478_.m_58904_().m_8055_(p_112478_.m_58899_())).m_60734_() instanceof ShulkerBoxBlock) {
            $$6 = $$7.m_61143_(ShulkerBoxBlock.f_56183_);
        }
        if (($$8 = p_112478_.m_59701_()) == null) {
            Material $$9 = Sheets.f_110741_;
        } else {
            $$10 = Sheets.f_110742_.get($$8.m_41060_());
        }
        p_112480_.m_85836_();
        p_112480_.m_85837_(0.5, 0.5, 0.5);
        float $$11 = 0.9995f;
        p_112480_.m_85841_(0.9995f, 0.9995f, 0.9995f);
        p_112480_.m_85845_($$6.m_122406_());
        p_112480_.m_85841_(1.0f, -1.0f, -1.0f);
        p_112480_.m_85837_(0.0, -1.0, 0.0);
        ModelPart $$12 = this.f_112466_.m_103742_();
        $$12.m_104227_(0.0f, 24.0f - p_112478_.m_59657_(p_112479_) * 0.5f * 16.0f, 0.0f);
        $$12.f_104204_ = 270.0f * p_112478_.m_59657_(p_112479_) * ((float)Math.PI / 180);
        VertexConsumer $$13 = $$10.m_119194_(p_112481_, RenderType::m_110458_);
        this.f_112466_.m_7695_(p_112480_, $$13, p_112482_, p_112483_, 1.0f, 1.0f, 1.0f, 1.0f);
        p_112480_.m_85849_();
    }
}

