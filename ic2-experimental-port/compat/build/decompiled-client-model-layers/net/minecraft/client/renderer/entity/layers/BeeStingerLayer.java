/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class BeeStingerLayer<T extends LivingEntity, M extends PlayerModel<T>>
extends StuckInBodyLayer<T, M> {
    private static final ResourceLocation f_116577_ = new ResourceLocation("textures/entity/bee/bee_stinger.png");

    public BeeStingerLayer(LivingEntityRenderer<T, M> p_116580_) {
        super(p_116580_);
    }

    @Override
    protected int m_7040_(T p_116582_) {
        return ((LivingEntity)p_116582_).m_21235_();
    }

    @Override
    protected void m_5558_(PoseStack p_116584_, MultiBufferSource p_116585_, int p_116586_, Entity p_116587_, float p_116588_, float p_116589_, float p_116590_, float p_116591_) {
        float $$8 = Mth.m_14116_(p_116588_ * p_116588_ + p_116590_ * p_116590_);
        float $$9 = (float)(Math.atan2(p_116588_, p_116590_) * 57.2957763671875);
        float $$10 = (float)(Math.atan2(p_116589_, $$8) * 57.2957763671875);
        p_116584_.m_85837_(0.0, 0.0, 0.0);
        p_116584_.m_85845_(Vector3f.f_122225_.m_122240_($$9 - 90.0f));
        p_116584_.m_85845_(Vector3f.f_122227_.m_122240_($$10));
        float $$11 = 0.0f;
        float $$12 = 0.125f;
        float $$13 = 0.0f;
        float $$14 = 0.0625f;
        float $$15 = 0.03125f;
        p_116584_.m_85845_(Vector3f.f_122223_.m_122240_(45.0f));
        p_116584_.m_85841_(0.03125f, 0.03125f, 0.03125f);
        p_116584_.m_85837_(2.5, 0.0, 0.0);
        VertexConsumer $$16 = p_116585_.m_6299_(RenderType.m_110458_(f_116577_));
        for (int $$17 = 0; $$17 < 4; ++$$17) {
            p_116584_.m_85845_(Vector3f.f_122223_.m_122240_(90.0f));
            PoseStack.Pose $$18 = p_116584_.m_85850_();
            Matrix4f $$19 = $$18.m_85861_();
            Matrix3f $$20 = $$18.m_85864_();
            BeeStingerLayer.m_116592_($$16, $$19, $$20, -4.5f, -1, 0.0f, 0.0f, p_116586_);
            BeeStingerLayer.m_116592_($$16, $$19, $$20, 4.5f, -1, 0.125f, 0.0f, p_116586_);
            BeeStingerLayer.m_116592_($$16, $$19, $$20, 4.5f, 1, 0.125f, 0.0625f, p_116586_);
            BeeStingerLayer.m_116592_($$16, $$19, $$20, -4.5f, 1, 0.0f, 0.0625f, p_116586_);
        }
    }

    private static void m_116592_(VertexConsumer p_116593_, Matrix4f p_116594_, Matrix3f p_116595_, float p_116596_, int p_116597_, float p_116598_, float p_116599_, int p_116600_) {
        p_116593_.m_85982_(p_116594_, p_116596_, p_116597_, 0.0f).m_6122_(255, 255, 255, 255).m_7421_(p_116598_, p_116599_).m_86008_(OverlayTexture.f_118083_).m_85969_(p_116600_).m_85977_(p_116595_, 0.0f, 1.0f, 0.0f).m_5752_();
    }
}

