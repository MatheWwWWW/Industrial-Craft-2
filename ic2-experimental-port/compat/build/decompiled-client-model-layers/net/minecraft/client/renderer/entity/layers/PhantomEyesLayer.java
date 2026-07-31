/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.PhantomModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Phantom;

public class PhantomEyesLayer<T extends Phantom>
extends EyesLayer<T, PhantomModel<T>> {
    private static final RenderType f_117339_ = RenderType.m_110488_(new ResourceLocation("textures/entity/phantom_eyes.png"));

    public PhantomEyesLayer(RenderLayerParent<T, PhantomModel<T>> p_117342_) {
        super(p_117342_);
    }

    @Override
    public RenderType m_5708_() {
        return f_117339_;
    }
}

