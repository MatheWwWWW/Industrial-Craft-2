/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Slime;

public class SlimeRenderer
extends MobRenderer<Slime, SlimeModel<Slime>> {
    private static final ResourceLocation f_115942_ = new ResourceLocation("textures/entity/slime/slime.png");

    public SlimeRenderer(EntityRendererProvider.Context p_174391_) {
        super(p_174391_, new SlimeModel(p_174391_.m_174023_(ModelLayers.f_171241_)), 0.25f);
        this.m_115326_(new SlimeOuterLayer<Slime>(this, p_174391_.m_174027_()));
    }

    @Override
    public void m_7392_(Slime p_115976_, float p_115977_, float p_115978_, PoseStack p_115979_, MultiBufferSource p_115980_, int p_115981_) {
        this.f_114477_ = 0.25f * (float)p_115976_.m_33632_();
        super.m_7392_(p_115976_, p_115977_, p_115978_, p_115979_, p_115980_, p_115981_);
    }

    @Override
    protected void m_7546_(Slime p_115983_, PoseStack p_115984_, float p_115985_) {
        float $$3 = 0.999f;
        p_115984_.m_85841_(0.999f, 0.999f, 0.999f);
        p_115984_.m_85837_(0.0, 0.001f, 0.0);
        float $$4 = p_115983_.m_33632_();
        float $$5 = Mth.m_14179_(p_115985_, p_115983_.f_33585_, p_115983_.f_33584_) / ($$4 * 0.5f + 1.0f);
        float $$6 = 1.0f / ($$5 + 1.0f);
        p_115984_.m_85841_($$6 * $$4, 1.0f / $$6 * $$4, $$6 * $$4);
    }

    @Override
    public ResourceLocation m_5478_(Slime p_115974_) {
        return f_115942_;
    }
}

