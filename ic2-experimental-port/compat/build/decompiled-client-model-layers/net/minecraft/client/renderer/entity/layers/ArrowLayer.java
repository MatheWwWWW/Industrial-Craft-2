/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;

public class ArrowLayer<T extends LivingEntity, M extends PlayerModel<T>>
extends StuckInBodyLayer<T, M> {
    private final EntityRenderDispatcher f_116562_;

    public ArrowLayer(EntityRendererProvider.Context p_174465_, LivingEntityRenderer<T, M> p_174466_) {
        super(p_174466_);
        this.f_116562_ = p_174465_.m_174022_();
    }

    @Override
    protected int m_7040_(T p_116567_) {
        return ((LivingEntity)p_116567_).m_21234_();
    }

    @Override
    protected void m_5558_(PoseStack p_116569_, MultiBufferSource p_116570_, int p_116571_, Entity p_116572_, float p_116573_, float p_116574_, float p_116575_, float p_116576_) {
        float $$8 = Mth.m_14116_(p_116573_ * p_116573_ + p_116575_ * p_116575_);
        Arrow $$9 = new Arrow(p_116572_.f_19853_, p_116572_.m_20185_(), p_116572_.m_20186_(), p_116572_.m_20189_());
        $$9.m_146922_((float)(Math.atan2(p_116573_, p_116575_) * 57.2957763671875));
        $$9.m_146926_((float)(Math.atan2(p_116574_, $$8) * 57.2957763671875));
        $$9.f_19859_ = $$9.m_146908_();
        $$9.f_19860_ = $$9.m_146909_();
        this.f_116562_.m_114384_($$9, 0.0, 0.0, 0.0, 0.0f, p_116576_, p_116569_, p_116570_, p_116571_);
    }
}

