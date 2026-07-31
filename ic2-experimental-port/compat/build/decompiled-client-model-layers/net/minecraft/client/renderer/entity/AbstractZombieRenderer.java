/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;

public abstract class AbstractZombieRenderer<T extends Zombie, M extends ZombieModel<T>>
extends HumanoidMobRenderer<T, M> {
    private static final ResourceLocation f_113757_ = new ResourceLocation("textures/entity/zombie/zombie.png");

    protected AbstractZombieRenderer(EntityRendererProvider.Context p_173910_, M p_173911_, M p_173912_, M p_173913_) {
        super(p_173910_, p_173911_, 0.5f);
        this.m_115326_(new HumanoidArmorLayer(this, p_173912_, p_173913_));
    }

    @Override
    public ResourceLocation m_5478_(Zombie p_113771_) {
        return f_113757_;
    }

    @Override
    protected boolean m_5936_(T p_113773_) {
        return super.m_5936_(p_113773_) || ((Zombie)p_113773_).m_34329_();
    }

    @Override
    protected /* synthetic */ boolean m_5936_(LivingEntity livingEntity) {
        return this.m_5936_((T)((Zombie)livingEntity));
    }
}

