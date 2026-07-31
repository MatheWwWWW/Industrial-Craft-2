/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import com.mojang.math.Vector3f;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.scores.Team;
import org.slf4j.Logger;

public abstract class LivingEntityRenderer<T extends LivingEntity, M extends EntityModel<T>>
extends EntityRenderer<T>
implements RenderLayerParent<T, M> {
    private static final Logger f_115289_ = LogUtils.getLogger();
    private static final float f_174287_ = 0.1f;
    protected M f_115290_;
    protected final List<RenderLayer<T, M>> f_115291_ = Lists.newArrayList();

    public LivingEntityRenderer(EntityRendererProvider.Context p_174289_, M p_174290_, float p_174291_) {
        super(p_174289_);
        this.f_115290_ = p_174290_;
        this.f_114477_ = p_174291_;
    }

    protected final boolean m_115326_(RenderLayer<T, M> p_115327_) {
        return this.f_115291_.add(p_115327_);
    }

    @Override
    public M m_7200_() {
        return this.f_115290_;
    }

    @Override
    public void m_7392_(T p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
        Direction $$12;
        p_115311_.m_85836_();
        ((EntityModel)this.f_115290_).f_102608_ = this.m_115342_(p_115308_, p_115310_);
        ((EntityModel)this.f_115290_).f_102609_ = ((Entity)p_115308_).m_20159_();
        ((EntityModel)this.f_115290_).f_102610_ = ((LivingEntity)p_115308_).m_6162_();
        float $$6 = Mth.m_14189_(p_115310_, ((LivingEntity)p_115308_).f_20884_, ((LivingEntity)p_115308_).f_20883_);
        float $$7 = Mth.m_14189_(p_115310_, ((LivingEntity)p_115308_).f_20886_, ((LivingEntity)p_115308_).f_20885_);
        float $$8 = $$7 - $$6;
        if (((Entity)p_115308_).m_20159_() && ((Entity)p_115308_).m_20202_() instanceof LivingEntity) {
            LivingEntity $$9 = (LivingEntity)((Entity)p_115308_).m_20202_();
            $$6 = Mth.m_14189_(p_115310_, $$9.f_20884_, $$9.f_20883_);
            $$8 = $$7 - $$6;
            float $$10 = Mth.m_14177_($$8);
            if ($$10 < -85.0f) {
                $$10 = -85.0f;
            }
            if ($$10 >= 85.0f) {
                $$10 = 85.0f;
            }
            $$6 = $$7 - $$10;
            if ($$10 * $$10 > 2500.0f) {
                $$6 += $$10 * 0.2f;
            }
            $$8 = $$7 - $$6;
        }
        float $$11 = Mth.m_14179_(p_115310_, ((LivingEntity)p_115308_).f_19860_, ((Entity)p_115308_).m_146909_());
        if (LivingEntityRenderer.m_194453_(p_115308_)) {
            $$11 *= -1.0f;
            $$8 *= -1.0f;
        }
        if (((Entity)p_115308_).m_217003_(Pose.SLEEPING) && ($$12 = ((LivingEntity)p_115308_).m_21259_()) != null) {
            float $$13 = ((Entity)p_115308_).m_20236_(Pose.STANDING) - 0.1f;
            p_115311_.m_85837_((float)(-$$12.m_122429_()) * $$13, 0.0, (float)(-$$12.m_122431_()) * $$13);
        }
        float $$14 = this.m_6930_(p_115308_, p_115310_);
        this.m_7523_(p_115308_, p_115311_, $$14, $$6, p_115310_);
        p_115311_.m_85841_(-1.0f, -1.0f, 1.0f);
        this.m_7546_(p_115308_, p_115311_, p_115310_);
        p_115311_.m_85837_(0.0, -1.501f, 0.0);
        float $$15 = 0.0f;
        float $$16 = 0.0f;
        if (!((Entity)p_115308_).m_20159_() && ((LivingEntity)p_115308_).m_6084_()) {
            $$15 = Mth.m_14179_(p_115310_, ((LivingEntity)p_115308_).f_20923_, ((LivingEntity)p_115308_).f_20924_);
            $$16 = ((LivingEntity)p_115308_).f_20925_ - ((LivingEntity)p_115308_).f_20924_ * (1.0f - p_115310_);
            if (((LivingEntity)p_115308_).m_6162_()) {
                $$16 *= 3.0f;
            }
            if ($$15 > 1.0f) {
                $$15 = 1.0f;
            }
        }
        ((EntityModel)this.f_115290_).m_6839_(p_115308_, $$16, $$15, p_115310_);
        ((EntityModel)this.f_115290_).m_6973_(p_115308_, $$16, $$15, $$14, $$8, $$11);
        Minecraft $$17 = Minecraft.m_91087_();
        boolean $$18 = this.m_5933_(p_115308_);
        boolean $$19 = !$$18 && !((Entity)p_115308_).m_20177_($$17.f_91074_);
        boolean $$20 = $$17.m_91314_((Entity)p_115308_);
        RenderType $$21 = this.m_7225_(p_115308_, $$18, $$19, $$20);
        if ($$21 != null) {
            VertexConsumer $$22 = p_115312_.m_6299_($$21);
            int $$23 = LivingEntityRenderer.m_115338_(p_115308_, this.m_6931_(p_115308_, p_115310_));
            ((Model)this.f_115290_).m_7695_(p_115311_, $$22, p_115313_, $$23, 1.0f, 1.0f, 1.0f, $$19 ? 0.15f : 1.0f);
        }
        if (!((Entity)p_115308_).m_5833_()) {
            for (RenderLayer<T, M> $$24 : this.f_115291_) {
                $$24.m_6494_(p_115311_, p_115312_, p_115313_, p_115308_, $$16, $$15, p_115310_, $$14, $$8, $$11);
            }
        }
        p_115311_.m_85849_();
        super.m_7392_(p_115308_, p_115309_, p_115310_, p_115311_, p_115312_, p_115313_);
    }

    @Nullable
    protected RenderType m_7225_(T p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
        ResourceLocation $$4 = this.m_5478_(p_115322_);
        if (p_115324_) {
            return RenderType.m_110467_($$4);
        }
        if (p_115323_) {
            return ((Model)this.f_115290_).m_103119_($$4);
        }
        if (p_115325_) {
            return RenderType.m_110491_($$4);
        }
        return null;
    }

    public static int m_115338_(LivingEntity p_115339_, float p_115340_) {
        return OverlayTexture.m_118093_(OverlayTexture.m_118088_(p_115340_), OverlayTexture.m_118096_(p_115339_.f_20916_ > 0 || p_115339_.f_20919_ > 0));
    }

    protected boolean m_5933_(T p_115341_) {
        return !((Entity)p_115341_).m_20145_();
    }

    private static float m_115328_(Direction p_115329_) {
        switch (p_115329_) {
            case SOUTH: {
                return 90.0f;
            }
            case WEST: {
                return 0.0f;
            }
            case NORTH: {
                return 270.0f;
            }
            case EAST: {
                return 180.0f;
            }
        }
        return 0.0f;
    }

    protected boolean m_5936_(T p_115304_) {
        return ((Entity)p_115304_).m_146890_();
    }

    protected void m_7523_(T p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_) {
        if (this.m_5936_(p_115317_)) {
            p_115320_ += (float)(Math.cos((double)((LivingEntity)p_115317_).f_19797_ * 3.25) * Math.PI * (double)0.4f);
        }
        if (!((Entity)p_115317_).m_217003_(Pose.SLEEPING)) {
            p_115318_.m_85845_(Vector3f.f_122225_.m_122240_(180.0f - p_115320_));
        }
        if (((LivingEntity)p_115317_).f_20919_ > 0) {
            float $$5 = ((float)((LivingEntity)p_115317_).f_20919_ + p_115321_ - 1.0f) / 20.0f * 1.6f;
            if (($$5 = Mth.m_14116_($$5)) > 1.0f) {
                $$5 = 1.0f;
            }
            p_115318_.m_85845_(Vector3f.f_122227_.m_122240_($$5 * this.m_6441_(p_115317_)));
        } else if (((LivingEntity)p_115317_).m_21209_()) {
            p_115318_.m_85845_(Vector3f.f_122223_.m_122240_(-90.0f - ((Entity)p_115317_).m_146909_()));
            p_115318_.m_85845_(Vector3f.f_122225_.m_122240_(((float)((LivingEntity)p_115317_).f_19797_ + p_115321_) * -75.0f));
        } else if (((Entity)p_115317_).m_217003_(Pose.SLEEPING)) {
            Direction $$6 = ((LivingEntity)p_115317_).m_21259_();
            float $$7 = $$6 != null ? LivingEntityRenderer.m_115328_($$6) : p_115320_;
            p_115318_.m_85845_(Vector3f.f_122225_.m_122240_($$7));
            p_115318_.m_85845_(Vector3f.f_122227_.m_122240_(this.m_6441_(p_115317_)));
            p_115318_.m_85845_(Vector3f.f_122225_.m_122240_(270.0f));
        } else if (LivingEntityRenderer.m_194453_(p_115317_)) {
            p_115318_.m_85837_(0.0, ((Entity)p_115317_).m_20206_() + 0.1f, 0.0);
            p_115318_.m_85845_(Vector3f.f_122227_.m_122240_(180.0f));
        }
    }

    protected float m_115342_(T p_115343_, float p_115344_) {
        return ((LivingEntity)p_115343_).m_21324_(p_115344_);
    }

    protected float m_6930_(T p_115305_, float p_115306_) {
        return (float)((LivingEntity)p_115305_).f_19797_ + p_115306_;
    }

    protected float m_6441_(T p_115337_) {
        return 90.0f;
    }

    protected float m_6931_(T p_115334_, float p_115335_) {
        return 0.0f;
    }

    protected void m_7546_(T p_115314_, PoseStack p_115315_, float p_115316_) {
    }

    @Override
    protected boolean m_6512_(T p_115333_) {
        boolean $$5;
        float $$2;
        double $$1 = this.f_114476_.m_114471_((Entity)p_115333_);
        float f = $$2 = ((Entity)p_115333_).m_20163_() ? 32.0f : 64.0f;
        if ($$1 >= (double)($$2 * $$2)) {
            return false;
        }
        Minecraft $$3 = Minecraft.m_91087_();
        LocalPlayer $$4 = $$3.f_91074_;
        boolean bl = $$5 = !((Entity)p_115333_).m_20177_($$4);
        if (p_115333_ != $$4) {
            Team $$6 = ((Entity)p_115333_).m_5647_();
            Team $$7 = $$4.m_5647_();
            if ($$6 != null) {
                Team.Visibility $$8 = $$6.m_7470_();
                switch ($$8) {
                    case ALWAYS: {
                        return $$5;
                    }
                    case NEVER: {
                        return false;
                    }
                    case HIDE_FOR_OTHER_TEAMS: {
                        return $$7 == null ? $$5 : $$6.m_83536_($$7) && ($$6.m_6259_() || $$5);
                    }
                    case HIDE_FOR_OWN_TEAM: {
                        return $$7 == null ? $$5 : !$$6.m_83536_($$7) && $$5;
                    }
                }
                return true;
            }
        }
        return Minecraft.m_91404_() && p_115333_ != $$3.m_91288_() && $$5 && !((Entity)p_115333_).m_20160_();
    }

    public static boolean m_194453_(LivingEntity p_194454_) {
        String $$1;
        if ((p_194454_ instanceof Player || p_194454_.m_8077_()) && ("Dinnerbone".equals($$1 = ChatFormatting.m_126649_(p_194454_.m_7755_().getString())) || "Grumm".equals($$1))) {
            return !(p_194454_ instanceof Player) || ((Player)p_194454_).m_36170_(PlayerModelPart.CAPE);
        }
        return false;
    }
}

