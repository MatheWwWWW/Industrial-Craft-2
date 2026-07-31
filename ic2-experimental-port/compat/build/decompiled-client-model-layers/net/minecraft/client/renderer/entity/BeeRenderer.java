/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.BeeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Bee;

public class BeeRenderer
extends MobRenderer<Bee, BeeModel<Bee>> {
    private static final ResourceLocation f_113887_ = new ResourceLocation("textures/entity/bee/bee_angry.png");
    private static final ResourceLocation f_113888_ = new ResourceLocation("textures/entity/bee/bee_angry_nectar.png");
    private static final ResourceLocation f_113889_ = new ResourceLocation("textures/entity/bee/bee.png");
    private static final ResourceLocation f_113890_ = new ResourceLocation("textures/entity/bee/bee_nectar.png");

    public BeeRenderer(EntityRendererProvider.Context p_173931_) {
        super(p_173931_, new BeeModel(p_173931_.m_174023_(ModelLayers.f_171268_)), 0.4f);
    }

    @Override
    public ResourceLocation m_5478_(Bee p_113897_) {
        if (p_113897_.m_21660_()) {
            if (p_113897_.m_27856_()) {
                return f_113888_;
            }
            return f_113887_;
        }
        if (p_113897_.m_27856_()) {
            return f_113890_;
        }
        return f_113889_;
    }
}

