/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

public abstract class MobRenderer<T extends Mob, M extends EntityModel<T>>
extends LivingEntityRenderer<T, M> {
    public static final int f_174302_ = 24;

    public MobRenderer(EntityRendererProvider.Context p_174304_, M p_174305_, float p_174306_) {
        super(p_174304_, p_174305_, p_174306_);
    }

    @Override
    protected boolean m_6512_(T p_115506_) {
        return super.m_6512_(p_115506_) && (((LivingEntity)p_115506_).m_6052_() || ((Entity)p_115506_).m_8077_() && p_115506_ == this.f_114476_.f_114359_);
    }

    @Override
    public boolean m_5523_(T p_115468_, Frustum p_115469_, double p_115470_, double p_115471_, double p_115472_) {
        if (super.m_5523_(p_115468_, p_115469_, p_115470_, p_115471_, p_115472_)) {
            return true;
        }
        Entity $$5 = ((Mob)p_115468_).m_21524_();
        if ($$5 != null) {
            return p_115469_.m_113029_($$5.m_6921_());
        }
        return false;
    }

    @Override
    public void m_7392_(T p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) {
        super.m_7392_(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
        Entity $$6 = ((Mob)p_115455_).m_21524_();
        if ($$6 == null) {
            return;
        }
        this.m_115461_(p_115455_, p_115457_, p_115458_, p_115459_, $$6);
    }

    private <E extends Entity> void m_115461_(T p_115462_, float p_115463_, PoseStack p_115464_, MultiBufferSource p_115465_, E p_115466_) {
        p_115464_.m_85836_();
        Vec3 $$5 = p_115466_.m_7398_(p_115463_);
        double $$6 = (double)(Mth.m_14179_(p_115463_, ((Mob)p_115462_).f_20884_, ((Mob)p_115462_).f_20883_) * ((float)Math.PI / 180)) + 1.5707963267948966;
        Vec3 $$7 = ((Entity)p_115462_).m_7939_();
        double $$8 = Math.cos($$6) * $$7.f_82481_ + Math.sin($$6) * $$7.f_82479_;
        double $$9 = Math.sin($$6) * $$7.f_82481_ - Math.cos($$6) * $$7.f_82479_;
        double $$10 = Mth.m_14139_(p_115463_, ((Mob)p_115462_).f_19854_, ((Entity)p_115462_).m_20185_()) + $$8;
        double $$11 = Mth.m_14139_(p_115463_, ((Mob)p_115462_).f_19855_, ((Entity)p_115462_).m_20186_()) + $$7.f_82480_;
        double $$12 = Mth.m_14139_(p_115463_, ((Mob)p_115462_).f_19856_, ((Entity)p_115462_).m_20189_()) + $$9;
        p_115464_.m_85837_($$8, $$7.f_82480_, $$9);
        float $$13 = (float)($$5.f_82479_ - $$10);
        float $$14 = (float)($$5.f_82480_ - $$11);
        float $$15 = (float)($$5.f_82481_ - $$12);
        float $$16 = 0.025f;
        VertexConsumer $$17 = p_115465_.m_6299_(RenderType.m_110475_());
        Matrix4f $$18 = p_115464_.m_85850_().m_85861_();
        float $$19 = Mth.m_14195_($$13 * $$13 + $$15 * $$15) * 0.025f / 2.0f;
        float $$20 = $$15 * $$19;
        float $$21 = $$13 * $$19;
        BlockPos $$22 = new BlockPos(((Entity)p_115462_).m_20299_(p_115463_));
        BlockPos $$23 = new BlockPos(p_115466_.m_20299_(p_115463_));
        int $$24 = this.m_6086_(p_115462_, $$22);
        int $$25 = this.f_114476_.m_114382_(p_115466_).m_6086_(p_115466_, $$23);
        int $$26 = ((Mob)p_115462_).f_19853_.m_45517_(LightLayer.SKY, $$22);
        int $$27 = ((Mob)p_115462_).f_19853_.m_45517_(LightLayer.SKY, $$23);
        for (int $$28 = 0; $$28 <= 24; ++$$28) {
            MobRenderer.m_174307_($$17, $$18, $$13, $$14, $$15, $$24, $$25, $$26, $$27, 0.025f, 0.025f, $$20, $$21, $$28, false);
        }
        for (int $$29 = 24; $$29 >= 0; --$$29) {
            MobRenderer.m_174307_($$17, $$18, $$13, $$14, $$15, $$24, $$25, $$26, $$27, 0.025f, 0.0f, $$20, $$21, $$29, true);
        }
        p_115464_.m_85849_();
    }

    private static void m_174307_(VertexConsumer p_174308_, Matrix4f p_174309_, float p_174310_, float p_174311_, float p_174312_, int p_174313_, int p_174314_, int p_174315_, int p_174316_, float p_174317_, float p_174318_, float p_174319_, float p_174320_, int p_174321_, boolean p_174322_) {
        float $$15 = (float)p_174321_ / 24.0f;
        int $$16 = (int)Mth.m_14179_($$15, p_174313_, p_174314_);
        int $$17 = (int)Mth.m_14179_($$15, p_174315_, p_174316_);
        int $$18 = LightTexture.m_109885_($$16, $$17);
        float $$19 = p_174321_ % 2 == (p_174322_ ? 1 : 0) ? 0.7f : 1.0f;
        float $$20 = 0.5f * $$19;
        float $$21 = 0.4f * $$19;
        float $$22 = 0.3f * $$19;
        float $$23 = p_174310_ * $$15;
        float $$24 = p_174311_ > 0.0f ? p_174311_ * $$15 * $$15 : p_174311_ - p_174311_ * (1.0f - $$15) * (1.0f - $$15);
        float $$25 = p_174312_ * $$15;
        p_174308_.m_85982_(p_174309_, $$23 - p_174319_, $$24 + p_174318_, $$25 + p_174320_).m_85950_($$20, $$21, $$22, 1.0f).m_85969_($$18).m_5752_();
        p_174308_.m_85982_(p_174309_, $$23 + p_174319_, $$24 + p_174317_ - p_174318_, $$25 - p_174320_).m_85950_($$20, $$21, $$22, 1.0f).m_85969_($$18).m_5752_();
    }
}

