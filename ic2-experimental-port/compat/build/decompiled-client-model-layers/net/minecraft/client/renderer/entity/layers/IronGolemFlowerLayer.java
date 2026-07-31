/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.block.Blocks;

public class IronGolemFlowerLayer
extends RenderLayer<IronGolem, IronGolemModel<IronGolem>> {
    private final BlockRenderDispatcher f_234840_;

    public IronGolemFlowerLayer(RenderLayerParent<IronGolem, IronGolemModel<IronGolem>> p_234842_, BlockRenderDispatcher p_234843_) {
        super(p_234842_);
        this.f_234840_ = p_234843_;
    }

    @Override
    public void m_6494_(PoseStack p_117172_, MultiBufferSource p_117173_, int p_117174_, IronGolem p_117175_, float p_117176_, float p_117177_, float p_117178_, float p_117179_, float p_117180_, float p_117181_) {
        if (p_117175_.m_28875_() == 0) {
            return;
        }
        p_117172_.m_85836_();
        ModelPart $$10 = ((IronGolemModel)this.m_117386_()).m_102968_();
        $$10.m_104299_(p_117172_);
        p_117172_.m_85837_(-1.1875, 1.0625, -0.9375);
        p_117172_.m_85837_(0.5, 0.5, 0.5);
        float $$11 = 0.5f;
        p_117172_.m_85841_(0.5f, 0.5f, 0.5f);
        p_117172_.m_85845_(Vector3f.f_122223_.m_122240_(-90.0f));
        p_117172_.m_85837_(-0.5, -0.5, -0.5);
        this.f_234840_.m_110912_(Blocks.f_50112_.m_49966_(), p_117172_, p_117173_, p_117174_, OverlayTexture.f_118083_);
        p_117172_.m_85849_();
    }
}

