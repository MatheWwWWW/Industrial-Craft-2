/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class HuskRenderer
extends ZombieRenderer {
    private static final ResourceLocation f_114892_ = new ResourceLocation("textures/entity/zombie/husk.png");

    public HuskRenderer(EntityRendererProvider.Context p_174180_) {
        super(p_174180_, ModelLayers.f_171188_, ModelLayers.f_171189_, ModelLayers.f_171190_);
    }

    @Override
    protected void m_7546_(Zombie p_114907_, PoseStack p_114908_, float p_114909_) {
        float $$3 = 1.0625f;
        p_114908_.m_85841_(1.0625f, 1.0625f, 1.0625f);
        super.m_7546_(p_114907_, p_114908_, p_114909_);
    }

    @Override
    public ResourceLocation m_5478_(Zombie p_114905_) {
        return f_114892_;
    }
}

