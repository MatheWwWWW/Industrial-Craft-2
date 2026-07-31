/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.RavagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Ravager;

public class RavagerRenderer
extends MobRenderer<Ravager, RavagerModel> {
    private static final ResourceLocation f_115804_ = new ResourceLocation("textures/entity/illager/ravager.png");

    public RavagerRenderer(EntityRendererProvider.Context p_174362_) {
        super(p_174362_, new RavagerModel(p_174362_.m_174023_(ModelLayers.f_171175_)), 1.1f);
    }

    @Override
    public ResourceLocation m_5478_(Ravager p_115811_) {
        return f_115804_;
    }
}

