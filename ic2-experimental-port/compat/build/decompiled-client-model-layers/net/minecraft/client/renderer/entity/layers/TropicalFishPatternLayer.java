/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ColorableHierarchicalModel;
import net.minecraft.client.model.TropicalFishModelA;
import net.minecraft.client.model.TropicalFishModelB;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.animal.TropicalFish;

public class TropicalFishPatternLayer
extends RenderLayer<TropicalFish, ColorableHierarchicalModel<TropicalFish>> {
    private final TropicalFishModelA<TropicalFish> f_117596_;
    private final TropicalFishModelB<TropicalFish> f_117597_;

    public TropicalFishPatternLayer(RenderLayerParent<TropicalFish, ColorableHierarchicalModel<TropicalFish>> p_174547_, EntityModelSet p_174548_) {
        super(p_174547_);
        this.f_117596_ = new TropicalFishModelA(p_174548_.m_171103_(ModelLayers.f_171259_));
        this.f_117597_ = new TropicalFishModelB(p_174548_.m_171103_(ModelLayers.f_171257_));
    }

    @Override
    public void m_6494_(PoseStack p_117612_, MultiBufferSource p_117613_, int p_117614_, TropicalFish p_117615_, float p_117616_, float p_117617_, float p_117618_, float p_117619_, float p_117620_, float p_117621_) {
        ColorableHierarchicalModel $$10 = p_117615_.m_30045_() == 0 ? this.f_117596_ : this.f_117597_;
        float[] $$11 = p_117615_.m_30044_();
        TropicalFishPatternLayer.m_117359_(this.m_117386_(), $$10, p_117615_.m_30046_(), p_117612_, p_117613_, p_117614_, p_117615_, p_117616_, p_117617_, p_117619_, p_117620_, p_117621_, p_117618_, $$11[0], $$11[1], $$11[2]);
    }
}

