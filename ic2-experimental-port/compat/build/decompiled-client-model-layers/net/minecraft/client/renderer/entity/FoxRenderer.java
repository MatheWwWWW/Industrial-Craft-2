/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.FoxHeldItemLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Fox;

public class FoxRenderer
extends MobRenderer<Fox, FoxModel<Fox>> {
    private static final ResourceLocation f_114720_ = new ResourceLocation("textures/entity/fox/fox.png");
    private static final ResourceLocation f_114721_ = new ResourceLocation("textures/entity/fox/fox_sleep.png");
    private static final ResourceLocation f_114722_ = new ResourceLocation("textures/entity/fox/snow_fox.png");
    private static final ResourceLocation f_114723_ = new ResourceLocation("textures/entity/fox/snow_fox_sleep.png");

    public FoxRenderer(EntityRendererProvider.Context p_174127_) {
        super(p_174127_, new FoxModel(p_174127_.m_174023_(ModelLayers.f_171148_)), 0.4f);
        this.m_115326_(new FoxHeldItemLayer(this, p_174127_.m_234598_()));
    }

    @Override
    protected void m_7523_(Fox p_114738_, PoseStack p_114739_, float p_114740_, float p_114741_, float p_114742_) {
        super.m_7523_(p_114738_, p_114739_, p_114740_, p_114741_, p_114742_);
        if (p_114738_.m_28557_() || p_114738_.m_28556_()) {
            float $$5 = -Mth.m_14179_(p_114742_, p_114738_.f_19860_, p_114738_.m_146909_());
            p_114739_.m_85845_(Vector3f.f_122223_.m_122240_($$5));
        }
    }

    @Override
    public ResourceLocation m_5478_(Fox p_114736_) {
        if (p_114736_.m_28554_() == Fox.Type.RED) {
            return p_114736_.m_5803_() ? f_114721_ : f_114720_;
        }
        return p_114736_.m_5803_() ? f_114723_ : f_114722_;
    }
}

