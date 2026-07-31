/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.PhantomModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.PhantomEyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Phantom;

public class PhantomRenderer
extends MobRenderer<Phantom, PhantomModel<Phantom>> {
    private static final ResourceLocation f_115662_ = new ResourceLocation("textures/entity/phantom.png");

    public PhantomRenderer(EntityRendererProvider.Context p_174338_) {
        super(p_174338_, new PhantomModel(p_174338_.m_174023_(ModelLayers.f_171204_)), 0.75f);
        this.m_115326_(new PhantomEyesLayer<Phantom>(this));
    }

    @Override
    public ResourceLocation m_5478_(Phantom p_115679_) {
        return f_115662_;
    }

    @Override
    protected void m_7546_(Phantom p_115681_, PoseStack p_115682_, float p_115683_) {
        int $$3 = p_115681_.m_33172_();
        float $$4 = 1.0f + 0.15f * (float)$$3;
        p_115682_.m_85841_($$4, $$4, $$4);
        p_115682_.m_85837_(0.0, 1.3125, 0.1875);
    }

    @Override
    protected void m_7523_(Phantom p_115685_, PoseStack p_115686_, float p_115687_, float p_115688_, float p_115689_) {
        super.m_7523_(p_115685_, p_115686_, p_115687_, p_115688_, p_115689_);
        p_115686_.m_85845_(Vector3f.f_122223_.m_122240_(p_115685_.m_146909_()));
    }
}

