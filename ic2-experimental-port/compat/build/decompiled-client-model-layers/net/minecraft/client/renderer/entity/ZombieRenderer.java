/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.Zombie;

public class ZombieRenderer
extends AbstractZombieRenderer<Zombie, ZombieModel<Zombie>> {
    public ZombieRenderer(EntityRendererProvider.Context p_174456_) {
        this(p_174456_, ModelLayers.f_171223_, ModelLayers.f_171226_, ModelLayers.f_171227_);
    }

    public ZombieRenderer(EntityRendererProvider.Context p_174458_, ModelLayerLocation p_174459_, ModelLayerLocation p_174460_, ModelLayerLocation p_174461_) {
        super(p_174458_, new ZombieModel(p_174458_.m_174023_(p_174459_)), new ZombieModel(p_174458_.m_174023_(p_174460_)), new ZombieModel(p_174458_.m_174023_(p_174461_)));
    }
}

