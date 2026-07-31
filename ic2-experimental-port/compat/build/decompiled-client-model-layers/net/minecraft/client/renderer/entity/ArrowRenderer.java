/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;

public abstract class ArrowRenderer<T extends AbstractArrow>
extends EntityRenderer<T> {
    public ArrowRenderer(EntityRendererProvider.Context p_173917_) {
        super(p_173917_);
    }

    @Override
    public void m_7392_(T p_113839_, float p_113840_, float p_113841_, PoseStack p_113842_, MultiBufferSource p_113843_, int p_113844_) {
        p_113842_.m_85836_();
        p_113842_.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(p_113841_, ((AbstractArrow)p_113839_).f_19859_, ((Entity)p_113839_).m_146908_()) - 90.0f));
        p_113842_.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14179_(p_113841_, ((AbstractArrow)p_113839_).f_19860_, ((Entity)p_113839_).m_146909_())));
        boolean $$6 = false;
        float $$7 = 0.0f;
        float $$8 = 0.5f;
        float $$9 = 0.0f;
        float $$10 = 0.15625f;
        float $$11 = 0.0f;
        float $$12 = 0.15625f;
        float $$13 = 0.15625f;
        float $$14 = 0.3125f;
        float $$15 = 0.05625f;
        float $$16 = (float)((AbstractArrow)p_113839_).f_36706_ - p_113841_;
        if ($$16 > 0.0f) {
            float $$17 = -Mth.m_14031_($$16 * 3.0f) * $$16;
            p_113842_.m_85845_(Vector3f.f_122227_.m_122240_($$17));
        }
        p_113842_.m_85845_(Vector3f.f_122223_.m_122240_(45.0f));
        p_113842_.m_85841_(0.05625f, 0.05625f, 0.05625f);
        p_113842_.m_85837_(-4.0, 0.0, 0.0);
        VertexConsumer $$18 = p_113843_.m_6299_(RenderType.m_110452_(this.m_5478_(p_113839_)));
        PoseStack.Pose $$19 = p_113842_.m_85850_();
        Matrix4f $$20 = $$19.m_85861_();
        Matrix3f $$21 = $$19.m_85864_();
        this.m_113825_($$20, $$21, $$18, -7, -2, -2, 0.0f, 0.15625f, -1, 0, 0, p_113844_);
        this.m_113825_($$20, $$21, $$18, -7, -2, 2, 0.15625f, 0.15625f, -1, 0, 0, p_113844_);
        this.m_113825_($$20, $$21, $$18, -7, 2, 2, 0.15625f, 0.3125f, -1, 0, 0, p_113844_);
        this.m_113825_($$20, $$21, $$18, -7, 2, -2, 0.0f, 0.3125f, -1, 0, 0, p_113844_);
        this.m_113825_($$20, $$21, $$18, -7, 2, -2, 0.0f, 0.15625f, 1, 0, 0, p_113844_);
        this.m_113825_($$20, $$21, $$18, -7, 2, 2, 0.15625f, 0.15625f, 1, 0, 0, p_113844_);
        this.m_113825_($$20, $$21, $$18, -7, -2, 2, 0.15625f, 0.3125f, 1, 0, 0, p_113844_);
        this.m_113825_($$20, $$21, $$18, -7, -2, -2, 0.0f, 0.3125f, 1, 0, 0, p_113844_);
        for (int $$22 = 0; $$22 < 4; ++$$22) {
            p_113842_.m_85845_(Vector3f.f_122223_.m_122240_(90.0f));
            this.m_113825_($$20, $$21, $$18, -8, -2, 0, 0.0f, 0.0f, 0, 1, 0, p_113844_);
            this.m_113825_($$20, $$21, $$18, 8, -2, 0, 0.5f, 0.0f, 0, 1, 0, p_113844_);
            this.m_113825_($$20, $$21, $$18, 8, 2, 0, 0.5f, 0.15625f, 0, 1, 0, p_113844_);
            this.m_113825_($$20, $$21, $$18, -8, 2, 0, 0.0f, 0.15625f, 0, 1, 0, p_113844_);
        }
        p_113842_.m_85849_();
        super.m_7392_(p_113839_, p_113840_, p_113841_, p_113842_, p_113843_, p_113844_);
    }

    public void m_113825_(Matrix4f p_113826_, Matrix3f p_113827_, VertexConsumer p_113828_, int p_113829_, int p_113830_, int p_113831_, float p_113832_, float p_113833_, int p_113834_, int p_113835_, int p_113836_, int p_113837_) {
        p_113828_.m_85982_(p_113826_, p_113829_, p_113830_, p_113831_).m_6122_(255, 255, 255, 255).m_7421_(p_113832_, p_113833_).m_86008_(OverlayTexture.f_118083_).m_85969_(p_113837_).m_85977_(p_113827_, p_113834_, p_113836_, p_113835_).m_5752_();
    }
}

