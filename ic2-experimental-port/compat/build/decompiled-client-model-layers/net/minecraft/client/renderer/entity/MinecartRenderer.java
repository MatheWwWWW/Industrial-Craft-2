/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MinecartRenderer<T extends AbstractMinecart>
extends EntityRenderer<T> {
    private static final ResourceLocation f_115402_ = new ResourceLocation("textures/entity/minecart.png");
    protected final EntityModel<T> f_115401_;
    private final BlockRenderDispatcher f_234646_;

    public MinecartRenderer(EntityRendererProvider.Context p_174300_, ModelLayerLocation p_174301_) {
        super(p_174300_);
        this.f_114477_ = 0.7f;
        this.f_115401_ = new MinecartModel(p_174300_.m_174023_(p_174301_));
        this.f_234646_ = p_174300_.m_234597_();
    }

    @Override
    public void m_7392_(T p_115418_, float p_115419_, float p_115420_, PoseStack p_115421_, MultiBufferSource p_115422_, int p_115423_) {
        super.m_7392_(p_115418_, p_115419_, p_115420_, p_115421_, p_115422_, p_115423_);
        p_115421_.m_85836_();
        long $$6 = (long)((Entity)p_115418_).m_19879_() * 493286711L;
        $$6 = $$6 * $$6 * 4392167121L + $$6 * 98761L;
        float $$7 = (((float)($$6 >> 16 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        float $$8 = (((float)($$6 >> 20 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        float $$9 = (((float)($$6 >> 24 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        p_115421_.m_85837_($$7, $$8, $$9);
        double $$10 = Mth.m_14139_(p_115420_, ((AbstractMinecart)p_115418_).f_19790_, ((Entity)p_115418_).m_20185_());
        double $$11 = Mth.m_14139_(p_115420_, ((AbstractMinecart)p_115418_).f_19791_, ((Entity)p_115418_).m_20186_());
        double $$12 = Mth.m_14139_(p_115420_, ((AbstractMinecart)p_115418_).f_19792_, ((Entity)p_115418_).m_20189_());
        double $$13 = 0.3f;
        Vec3 $$14 = ((AbstractMinecart)p_115418_).m_38179_($$10, $$11, $$12);
        float $$15 = Mth.m_14179_(p_115420_, ((AbstractMinecart)p_115418_).f_19860_, ((Entity)p_115418_).m_146909_());
        if ($$14 != null) {
            Vec3 $$16 = ((AbstractMinecart)p_115418_).m_38096_($$10, $$11, $$12, 0.3f);
            Vec3 $$17 = ((AbstractMinecart)p_115418_).m_38096_($$10, $$11, $$12, -0.3f);
            if ($$16 == null) {
                $$16 = $$14;
            }
            if ($$17 == null) {
                $$17 = $$14;
            }
            p_115421_.m_85837_($$14.f_82479_ - $$10, ($$16.f_82480_ + $$17.f_82480_) / 2.0 - $$11, $$14.f_82481_ - $$12);
            Vec3 $$18 = $$17.m_82520_(-$$16.f_82479_, -$$16.f_82480_, -$$16.f_82481_);
            if ($$18.m_82553_() != 0.0) {
                $$18 = $$18.m_82541_();
                p_115419_ = (float)(Math.atan2($$18.f_82481_, $$18.f_82479_) * 180.0 / Math.PI);
                $$15 = (float)(Math.atan($$18.f_82480_) * 73.0);
            }
        }
        p_115421_.m_85837_(0.0, 0.375, 0.0);
        p_115421_.m_85845_(Vector3f.f_122225_.m_122240_(180.0f - p_115419_));
        p_115421_.m_85845_(Vector3f.f_122227_.m_122240_(-$$15));
        float $$19 = (float)((AbstractMinecart)p_115418_).m_38176_() - p_115420_;
        float $$20 = ((AbstractMinecart)p_115418_).m_38169_() - p_115420_;
        if ($$20 < 0.0f) {
            $$20 = 0.0f;
        }
        if ($$19 > 0.0f) {
            p_115421_.m_85845_(Vector3f.f_122223_.m_122240_(Mth.m_14031_($$19) * $$19 * $$20 / 10.0f * (float)((AbstractMinecart)p_115418_).m_38177_()));
        }
        int $$21 = ((AbstractMinecart)p_115418_).m_38183_();
        BlockState $$22 = ((AbstractMinecart)p_115418_).m_38178_();
        if ($$22.m_60799_() != RenderShape.INVISIBLE) {
            p_115421_.m_85836_();
            float $$23 = 0.75f;
            p_115421_.m_85841_(0.75f, 0.75f, 0.75f);
            p_115421_.m_85837_(-0.5, (float)($$21 - 8) / 16.0f, 0.5);
            p_115421_.m_85845_(Vector3f.f_122225_.m_122240_(90.0f));
            this.m_7002_(p_115418_, p_115420_, $$22, p_115421_, p_115422_, p_115423_);
            p_115421_.m_85849_();
        }
        p_115421_.m_85841_(-1.0f, -1.0f, 1.0f);
        this.f_115401_.m_6973_(p_115418_, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        VertexConsumer $$24 = p_115422_.m_6299_(this.f_115401_.m_103119_(this.m_5478_(p_115418_)));
        this.f_115401_.m_7695_(p_115421_, $$24, p_115423_, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
        p_115421_.m_85849_();
    }

    @Override
    public ResourceLocation m_5478_(T p_115416_) {
        return f_115402_;
    }

    protected void m_7002_(T p_115424_, float p_115425_, BlockState p_115426_, PoseStack p_115427_, MultiBufferSource p_115428_, int p_115429_) {
        this.f_234646_.m_110912_(p_115426_, p_115427_, p_115428_, p_115429_, OverlayTexture.f_118083_);
    }
}

