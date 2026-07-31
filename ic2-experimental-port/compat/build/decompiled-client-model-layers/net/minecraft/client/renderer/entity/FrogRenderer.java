/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.FrogModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.frog.Frog;

public class FrogRenderer
extends MobRenderer<Frog, FrogModel<Frog>> {
    public FrogRenderer(EntityRendererProvider.Context p_234619_) {
        super(p_234619_, new FrogModel(p_234619_.m_174023_(ModelLayers.f_233546_)), 0.3f);
    }

    @Override
    public ResourceLocation m_5478_(Frog p_234623_) {
        return p_234623_.m_218524_().f_218188_();
    }
}

