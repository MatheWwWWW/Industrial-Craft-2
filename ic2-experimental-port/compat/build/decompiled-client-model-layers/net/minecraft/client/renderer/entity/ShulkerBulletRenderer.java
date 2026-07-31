/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.ShulkerBulletModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.ShulkerBullet;

public class ShulkerBulletRenderer
extends EntityRenderer<ShulkerBullet> {
    private static final ResourceLocation f_115841_ = new ResourceLocation("textures/entity/shulker/spark.png");
    private static final RenderType f_115842_ = RenderType.m_110473_(f_115841_);
    private final ShulkerBulletModel<ShulkerBullet> f_115843_;

    public ShulkerBulletRenderer(EntityRendererProvider.Context p_174368_) {
        super(p_174368_);
        this.f_115843_ = new ShulkerBulletModel(p_174368_.m_174023_(ModelLayers.f_171181_));
    }

    @Override
    protected int m_6086_(ShulkerBullet p_115869_, BlockPos p_115870_) {
        return 15;
    }

    @Override
    public void m_7392_(ShulkerBullet p_115862_, float p_115863_, float p_115864_, PoseStack p_115865_, MultiBufferSource p_115866_, int p_115867_) {
        p_115865_.m_85836_();
        float $$6 = Mth.m_14201_(p_115862_.f_19859_, p_115862_.m_146908_(), p_115864_);
        float $$7 = Mth.m_14179_(p_115864_, p_115862_.f_19860_, p_115862_.m_146909_());
        float $$8 = (float)p_115862_.f_19797_ + p_115864_;
        p_115865_.m_85837_(0.0, 0.15f, 0.0);
        p_115865_.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14031_($$8 * 0.1f) * 180.0f));
        p_115865_.m_85845_(Vector3f.f_122223_.m_122240_(Mth.m_14089_($$8 * 0.1f) * 180.0f));
        p_115865_.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14031_($$8 * 0.15f) * 360.0f));
        p_115865_.m_85841_(-0.5f, -0.5f, 0.5f);
        this.f_115843_.m_6973_(p_115862_, 0.0f, 0.0f, 0.0f, $$6, $$7);
        VertexConsumer $$9 = p_115866_.m_6299_(this.f_115843_.m_103119_(f_115841_));
        this.f_115843_.m_7695_(p_115865_, $$9, p_115867_, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
        p_115865_.m_85841_(1.5f, 1.5f, 1.5f);
        VertexConsumer $$10 = p_115866_.m_6299_(f_115842_);
        this.f_115843_.m_7695_(p_115865_, $$10, p_115867_, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 0.15f);
        p_115865_.m_85849_();
        super.m_7392_(p_115862_, p_115863_, p_115864_, p_115865_, p_115866_, p_115867_);
    }

    @Override
    public ResourceLocation m_5478_(ShulkerBullet p_115860_) {
        return f_115841_;
    }
}

