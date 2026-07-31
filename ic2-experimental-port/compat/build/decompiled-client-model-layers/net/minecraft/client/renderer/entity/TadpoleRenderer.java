/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.TadpoleModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.frog.Tadpole;

public class TadpoleRenderer
extends MobRenderer<Tadpole, TadpoleModel<Tadpole>> {
    private static final ResourceLocation f_234652_ = new ResourceLocation("textures/entity/tadpole/tadpole.png");

    public TadpoleRenderer(EntityRendererProvider.Context p_234655_) {
        super(p_234655_, new TadpoleModel(p_234655_.m_174023_(ModelLayers.f_233549_)), 0.14f);
    }

    @Override
    public ResourceLocation m_5478_(Tadpole p_234659_) {
        return f_234652_;
    }
}

