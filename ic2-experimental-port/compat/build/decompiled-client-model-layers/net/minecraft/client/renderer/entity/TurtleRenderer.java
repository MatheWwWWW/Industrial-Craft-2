/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.TurtleModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Turtle;

public class TurtleRenderer
extends MobRenderer<Turtle, TurtleModel<Turtle>> {
    private static final ResourceLocation f_116231_ = new ResourceLocation("textures/entity/turtle/big_sea_turtle.png");

    public TurtleRenderer(EntityRendererProvider.Context p_174430_) {
        super(p_174430_, new TurtleModel(p_174430_.m_174023_(ModelLayers.f_171260_)), 0.7f);
    }

    @Override
    public void m_7392_(Turtle p_116261_, float p_116262_, float p_116263_, PoseStack p_116264_, MultiBufferSource p_116265_, int p_116266_) {
        if (p_116261_.m_6162_()) {
            this.f_114477_ *= 0.5f;
        }
        super.m_7392_(p_116261_, p_116262_, p_116263_, p_116264_, p_116265_, p_116266_);
    }

    @Override
    public ResourceLocation m_5478_(Turtle p_116259_) {
        return f_116231_;
    }
}

