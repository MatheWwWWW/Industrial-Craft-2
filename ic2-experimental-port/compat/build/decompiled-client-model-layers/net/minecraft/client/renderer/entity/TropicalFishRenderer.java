/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.ColorableHierarchicalModel;
import net.minecraft.client.model.TropicalFishModelA;
import net.minecraft.client.model.TropicalFishModelB;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.TropicalFishPatternLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.TropicalFish;

public class TropicalFishRenderer
extends MobRenderer<TropicalFish, ColorableHierarchicalModel<TropicalFish>> {
    private final ColorableHierarchicalModel<TropicalFish> f_116183_ = (ColorableHierarchicalModel)this.m_7200_();
    private final ColorableHierarchicalModel<TropicalFish> f_116184_;

    public TropicalFishRenderer(EntityRendererProvider.Context p_174428_) {
        super(p_174428_, new TropicalFishModelA(p_174428_.m_174023_(ModelLayers.f_171258_)), 0.15f);
        this.f_116184_ = new TropicalFishModelB<TropicalFish>(p_174428_.m_174023_(ModelLayers.f_171256_));
        this.m_115326_(new TropicalFishPatternLayer(this, p_174428_.m_174027_()));
    }

    @Override
    public ResourceLocation m_5478_(TropicalFish p_116217_) {
        return p_116217_.m_30047_();
    }

    @Override
    public void m_7392_(TropicalFish p_116219_, float p_116220_, float p_116221_, PoseStack p_116222_, MultiBufferSource p_116223_, int p_116224_) {
        ColorableHierarchicalModel<TropicalFish> $$6;
        this.f_115290_ = $$6 = p_116219_.m_30045_() == 0 ? this.f_116183_ : this.f_116184_;
        float[] $$7 = p_116219_.m_30043_();
        $$6.m_170501_($$7[0], $$7[1], $$7[2]);
        super.m_7392_(p_116219_, p_116220_, p_116221_, p_116222_, p_116223_, p_116224_);
        $$6.m_170501_(1.0f, 1.0f, 1.0f);
    }

    @Override
    protected void m_7523_(TropicalFish p_116226_, PoseStack p_116227_, float p_116228_, float p_116229_, float p_116230_) {
        super.m_7523_(p_116226_, p_116227_, p_116228_, p_116229_, p_116230_);
        float $$5 = 4.3f * Mth.m_14031_(0.6f * p_116228_);
        p_116227_.m_85845_(Vector3f.f_122225_.m_122240_($$5));
        if (!p_116226_.m_20069_()) {
            p_116227_.m_85837_(0.2f, 0.1f, 0.0);
            p_116227_.m_85845_(Vector3f.f_122227_.m_122240_(90.0f));
        }
    }
}

