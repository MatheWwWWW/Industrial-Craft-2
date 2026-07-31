/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class EntityRenderer<T extends Entity> {
    protected static final float f_174006_ = 0.025f;
    protected final EntityRenderDispatcher f_114476_;
    private final Font f_174005_;
    protected float f_114477_;
    protected float f_114478_ = 1.0f;

    protected EntityRenderer(EntityRendererProvider.Context p_174008_) {
        this.f_114476_ = p_174008_.m_174022_();
        this.f_174005_ = p_174008_.m_174028_();
    }

    public final int m_114505_(T p_114506_, float p_114507_) {
        BlockPos $$2 = new BlockPos(((Entity)p_114506_).m_7371_(p_114507_));
        return LightTexture.m_109885_(this.m_6086_(p_114506_, $$2), this.m_114508_(p_114506_, $$2));
    }

    protected int m_114508_(T p_114509_, BlockPos p_114510_) {
        return ((Entity)p_114509_).f_19853_.m_45517_(LightLayer.SKY, p_114510_);
    }

    protected int m_6086_(T p_114496_, BlockPos p_114497_) {
        if (((Entity)p_114496_).m_6060_()) {
            return 15;
        }
        return ((Entity)p_114496_).f_19853_.m_45517_(LightLayer.BLOCK, p_114497_);
    }

    public boolean m_5523_(T p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        if (!((Entity)p_114491_).m_6000_(p_114493_, p_114494_, p_114495_)) {
            return false;
        }
        if (((Entity)p_114491_).f_19811_) {
            return true;
        }
        AABB $$5 = ((Entity)p_114491_).m_6921_().m_82400_(0.5);
        if ($$5.m_82392_() || $$5.m_82309_() == 0.0) {
            $$5 = new AABB(((Entity)p_114491_).m_20185_() - 2.0, ((Entity)p_114491_).m_20186_() - 2.0, ((Entity)p_114491_).m_20189_() - 2.0, ((Entity)p_114491_).m_20185_() + 2.0, ((Entity)p_114491_).m_20186_() + 2.0, ((Entity)p_114491_).m_20189_() + 2.0);
        }
        return p_114492_.m_113029_($$5);
    }

    public Vec3 m_7860_(T p_114483_, float p_114484_) {
        return Vec3.f_82478_;
    }

    public void m_7392_(T p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {
        if (!this.m_6512_(p_114485_)) {
            return;
        }
        this.m_7649_(p_114485_, ((Entity)p_114485_).m_5446_(), p_114488_, p_114489_, p_114490_);
    }

    protected boolean m_6512_(T p_114504_) {
        return ((Entity)p_114504_).m_6052_() && ((Entity)p_114504_).m_8077_();
    }

    public abstract ResourceLocation m_5478_(T var1);

    public Font m_114481_() {
        return this.f_174005_;
    }

    protected void m_7649_(T p_114498_, Component p_114499_, PoseStack p_114500_, MultiBufferSource p_114501_, int p_114502_) {
        double $$5 = this.f_114476_.m_114471_((Entity)p_114498_);
        if ($$5 > 4096.0) {
            return;
        }
        boolean $$6 = !((Entity)p_114498_).m_20163_();
        float $$7 = ((Entity)p_114498_).m_20206_() + 0.5f;
        int $$8 = "deadmau5".equals(p_114499_.getString()) ? -10 : 0;
        p_114500_.m_85836_();
        p_114500_.m_85837_(0.0, $$7, 0.0);
        p_114500_.m_85845_(this.f_114476_.m_114470_());
        p_114500_.m_85841_(-0.025f, -0.025f, 0.025f);
        Matrix4f $$9 = p_114500_.m_85850_().m_85861_();
        float $$10 = Minecraft.m_91087_().f_91066_.m_92141_(0.25f);
        int $$11 = (int)($$10 * 255.0f) << 24;
        Font $$12 = this.m_114481_();
        float $$13 = -$$12.m_92852_(p_114499_) / 2;
        $$12.m_92841_(p_114499_, $$13, $$8, 0x20FFFFFF, false, $$9, p_114501_, $$6, $$11, p_114502_);
        if ($$6) {
            $$12.m_92841_(p_114499_, $$13, $$8, -1, false, $$9, p_114501_, false, 0, p_114502_);
        }
        p_114500_.m_85849_();
    }
}

