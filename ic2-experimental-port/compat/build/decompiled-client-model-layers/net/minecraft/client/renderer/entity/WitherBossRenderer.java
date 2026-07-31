/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.WitherBossModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.WitherArmorLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.boss.wither.WitherBoss;

public class WitherBossRenderer
extends MobRenderer<WitherBoss, WitherBossModel<WitherBoss>> {
    private static final ResourceLocation f_116422_ = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
    private static final ResourceLocation f_116423_ = new ResourceLocation("textures/entity/wither/wither.png");

    public WitherBossRenderer(EntityRendererProvider.Context p_174445_) {
        super(p_174445_, new WitherBossModel(p_174445_.m_174023_(ModelLayers.f_171214_)), 1.0f);
        this.m_115326_(new WitherArmorLayer(this, p_174445_.m_174027_()));
    }

    @Override
    protected int m_6086_(WitherBoss p_116443_, BlockPos p_116444_) {
        return 15;
    }

    @Override
    public ResourceLocation m_5478_(WitherBoss p_116437_) {
        int $$1 = p_116437_.m_31502_();
        if ($$1 <= 0 || $$1 <= 80 && $$1 / 5 % 2 == 1) {
            return f_116423_;
        }
        return f_116422_;
    }

    @Override
    protected void m_7546_(WitherBoss p_116439_, PoseStack p_116440_, float p_116441_) {
        float $$3 = 2.0f;
        int $$4 = p_116439_.m_31502_();
        if ($$4 > 0) {
            $$3 -= ((float)$$4 - p_116441_) / 220.0f * 0.5f;
        }
        p_116440_.m_85841_($$3, $$3, $$3);
    }
}

