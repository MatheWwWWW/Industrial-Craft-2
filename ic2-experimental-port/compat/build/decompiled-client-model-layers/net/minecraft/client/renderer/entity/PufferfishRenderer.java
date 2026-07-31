/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PufferfishBigModel;
import net.minecraft.client.model.PufferfishMidModel;
import net.minecraft.client.model.PufferfishSmallModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Pufferfish;

public class PufferfishRenderer
extends MobRenderer<Pufferfish, EntityModel<Pufferfish>> {
    private static final ResourceLocation f_115737_ = new ResourceLocation("textures/entity/fish/pufferfish.png");
    private int f_115738_ = 3;
    private final EntityModel<Pufferfish> f_115739_;
    private final EntityModel<Pufferfish> f_115740_;
    private final EntityModel<Pufferfish> f_115741_ = this.m_7200_();

    public PufferfishRenderer(EntityRendererProvider.Context p_174358_) {
        super(p_174358_, new PufferfishBigModel(p_174358_.m_174023_(ModelLayers.f_171171_)), 0.2f);
        this.f_115740_ = new PufferfishMidModel<Pufferfish>(p_174358_.m_174023_(ModelLayers.f_171172_));
        this.f_115739_ = new PufferfishSmallModel<Pufferfish>(p_174358_.m_174023_(ModelLayers.f_171173_));
    }

    @Override
    public ResourceLocation m_5478_(Pufferfish p_115775_) {
        return f_115737_;
    }

    @Override
    public void m_7392_(Pufferfish p_115777_, float p_115778_, float p_115779_, PoseStack p_115780_, MultiBufferSource p_115781_, int p_115782_) {
        int $$6 = p_115777_.m_29631_();
        if ($$6 != this.f_115738_) {
            this.f_115290_ = $$6 == 0 ? this.f_115739_ : ($$6 == 1 ? this.f_115740_ : this.f_115741_);
        }
        this.f_115738_ = $$6;
        this.f_114477_ = 0.1f + 0.1f * (float)$$6;
        super.m_7392_(p_115777_, p_115778_, p_115779_, p_115780_, p_115781_, p_115782_);
    }

    @Override
    protected void m_7523_(Pufferfish p_115784_, PoseStack p_115785_, float p_115786_, float p_115787_, float p_115788_) {
        p_115785_.m_85837_(0.0, Mth.m_14089_(p_115786_ * 0.05f) * 0.08f, 0.0);
        super.m_7523_(p_115784_, p_115785_, p_115786_, p_115787_, p_115788_);
    }
}

