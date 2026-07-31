/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class EnderEyesLayer<T extends LivingEntity>
extends EyesLayer<T, EndermanModel<T>> {
    private static final RenderType f_116961_ = RenderType.m_110488_(new ResourceLocation("textures/entity/enderman/enderman_eyes.png"));

    public EnderEyesLayer(RenderLayerParent<T, EndermanModel<T>> p_116964_) {
        super(p_116964_);
    }

    @Override
    public RenderType m_5708_() {
        return f_116961_;
    }
}

