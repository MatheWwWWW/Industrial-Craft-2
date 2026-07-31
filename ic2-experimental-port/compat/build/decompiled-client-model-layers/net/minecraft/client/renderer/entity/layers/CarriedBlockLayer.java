/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.block.state.BlockState;

public class CarriedBlockLayer
extends RenderLayer<EnderMan, EndermanModel<EnderMan>> {
    private final BlockRenderDispatcher f_234812_;

    public CarriedBlockLayer(RenderLayerParent<EnderMan, EndermanModel<EnderMan>> p_234814_, BlockRenderDispatcher p_234815_) {
        super(p_234814_);
        this.f_234812_ = p_234815_;
    }

    @Override
    public void m_6494_(PoseStack p_116639_, MultiBufferSource p_116640_, int p_116641_, EnderMan p_116642_, float p_116643_, float p_116644_, float p_116645_, float p_116646_, float p_116647_, float p_116648_) {
        BlockState $$10 = p_116642_.m_32530_();
        if ($$10 == null) {
            return;
        }
        p_116639_.m_85836_();
        p_116639_.m_85837_(0.0, 0.6875, -0.75);
        p_116639_.m_85845_(Vector3f.f_122223_.m_122240_(20.0f));
        p_116639_.m_85845_(Vector3f.f_122225_.m_122240_(45.0f));
        p_116639_.m_85837_(0.25, 0.1875, 0.25);
        float $$11 = 0.5f;
        p_116639_.m_85841_(-0.5f, -0.5f, 0.5f);
        p_116639_.m_85845_(Vector3f.f_122225_.m_122240_(90.0f));
        this.f_234812_.m_110912_($$10, p_116639_, p_116640_, p_116641_, OverlayTexture.f_118083_);
        p_116639_.m_85849_();
    }
}

