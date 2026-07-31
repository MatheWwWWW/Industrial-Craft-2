/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.CodModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Cod;

public class CodRenderer
extends MobRenderer<Cod, CodModel<Cod>> {
    private static final ResourceLocation f_114002_ = new ResourceLocation("textures/entity/fish/cod.png");

    public CodRenderer(EntityRendererProvider.Context p_173954_) {
        super(p_173954_, new CodModel(p_173954_.m_174023_(ModelLayers.f_171278_)), 0.3f);
    }

    @Override
    public ResourceLocation m_5478_(Cod p_114015_) {
        return f_114002_;
    }

    @Override
    protected void m_7523_(Cod p_114017_, PoseStack p_114018_, float p_114019_, float p_114020_, float p_114021_) {
        super.m_7523_(p_114017_, p_114018_, p_114019_, p_114020_, p_114021_);
        float $$5 = 4.3f * Mth.m_14031_(0.6f * p_114019_);
        p_114018_.m_85845_(Vector3f.f_122225_.m_122240_($$5));
        if (!p_114017_.m_20069_()) {
            p_114018_.m_85837_(0.1f, 0.1f, -0.1f);
            p_114018_.m_85845_(Vector3f.f_122227_.m_122240_(90.0f));
        }
    }
}

