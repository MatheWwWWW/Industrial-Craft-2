/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.SalmonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Salmon;

public class SalmonRenderer
extends MobRenderer<Salmon, SalmonModel<Salmon>> {
    private static final ResourceLocation f_115813_ = new ResourceLocation("textures/entity/fish/salmon.png");

    public SalmonRenderer(EntityRendererProvider.Context p_174364_) {
        super(p_174364_, new SalmonModel(p_174364_.m_174023_(ModelLayers.f_171176_)), 0.4f);
    }

    @Override
    public ResourceLocation m_5478_(Salmon p_115826_) {
        return f_115813_;
    }

    @Override
    protected void m_7523_(Salmon p_115828_, PoseStack p_115829_, float p_115830_, float p_115831_, float p_115832_) {
        super.m_7523_(p_115828_, p_115829_, p_115830_, p_115831_, p_115832_);
        float $$5 = 1.0f;
        float $$6 = 1.0f;
        if (!p_115828_.m_20069_()) {
            $$5 = 1.3f;
            $$6 = 1.7f;
        }
        float $$7 = $$5 * 4.3f * Mth.m_14031_($$6 * 0.6f * p_115830_);
        p_115829_.m_85845_(Vector3f.f_122225_.m_122240_($$7));
        p_115829_.m_85837_(0.0, 0.0, -0.4f);
        if (!p_115828_.m_20069_()) {
            p_115829_.m_85837_(0.2f, 0.1f, 0.0);
            p_115829_.m_85845_(Vector3f.f_122227_.m_122240_(90.0f));
        }
    }
}

