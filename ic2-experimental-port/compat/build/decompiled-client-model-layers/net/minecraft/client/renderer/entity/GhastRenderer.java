/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.GhastModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Ghast;

public class GhastRenderer
extends MobRenderer<Ghast, GhastModel<Ghast>> {
    private static final ResourceLocation f_114743_ = new ResourceLocation("textures/entity/ghast/ghast.png");
    private static final ResourceLocation f_114744_ = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");

    public GhastRenderer(EntityRendererProvider.Context p_174129_) {
        super(p_174129_, new GhastModel(p_174129_.m_174023_(ModelLayers.f_171150_)), 1.5f);
    }

    @Override
    public ResourceLocation m_5478_(Ghast p_114755_) {
        if (p_114755_.m_32756_()) {
            return f_114744_;
        }
        return f_114743_;
    }

    @Override
    protected void m_7546_(Ghast p_114757_, PoseStack p_114758_, float p_114759_) {
        float $$3 = 1.0f;
        float $$4 = 4.5f;
        float $$5 = 4.5f;
        p_114758_.m_85841_(4.5f, 4.5f, 4.5f);
    }
}

