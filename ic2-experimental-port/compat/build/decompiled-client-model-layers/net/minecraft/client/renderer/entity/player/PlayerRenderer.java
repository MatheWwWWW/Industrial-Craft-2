/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.player;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.Objects;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.Deadmau5EarsLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ParrotOnShoulderLayer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;

public class PlayerRenderer
extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public PlayerRenderer(EntityRendererProvider.Context p_174557_, boolean p_174558_) {
        super(p_174557_, new PlayerModel(p_174557_.m_174023_(p_174558_ ? ModelLayers.f_171166_ : ModelLayers.f_171162_), p_174558_), 0.5f);
        this.m_115326_(new HumanoidArmorLayer(this, new HumanoidModel(p_174557_.m_174023_(p_174558_ ? ModelLayers.f_171167_ : ModelLayers.f_171164_)), new HumanoidModel(p_174557_.m_174023_(p_174558_ ? ModelLayers.f_171168_ : ModelLayers.f_171165_))));
        this.m_115326_(new PlayerItemInHandLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>(this, p_174557_.m_234598_()));
        this.m_115326_(new ArrowLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>(p_174557_, this));
        this.m_115326_(new Deadmau5EarsLayer(this));
        this.m_115326_(new CapeLayer(this));
        this.m_115326_(new CustomHeadLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>(this, p_174557_.m_174027_(), p_174557_.m_234598_()));
        this.m_115326_(new ElytraLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>(this, p_174557_.m_174027_()));
        this.m_115326_(new ParrotOnShoulderLayer<AbstractClientPlayer>(this, p_174557_.m_174027_()));
        this.m_115326_(new SpinAttackEffectLayer<AbstractClientPlayer>(this, p_174557_.m_174027_()));
        this.m_115326_(new BeeStingerLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>(this));
    }

    @Override
    public void m_7392_(AbstractClientPlayer p_117788_, float p_117789_, float p_117790_, PoseStack p_117791_, MultiBufferSource p_117792_, int p_117793_) {
        this.m_117818_(p_117788_);
        super.m_7392_(p_117788_, p_117789_, p_117790_, p_117791_, p_117792_, p_117793_);
    }

    @Override
    public Vec3 m_7860_(AbstractClientPlayer p_117785_, float p_117786_) {
        if (p_117785_.m_6047_()) {
            return new Vec3(0.0, -0.125, 0.0);
        }
        return super.m_7860_(p_117785_, p_117786_);
    }

    private void m_117818_(AbstractClientPlayer p_117819_) {
        PlayerModel $$1 = (PlayerModel)this.m_7200_();
        if (p_117819_.m_5833_()) {
            $$1.m_8009_(false);
            $$1.f_102808_.f_104207_ = true;
            $$1.f_102809_.f_104207_ = true;
        } else {
            $$1.m_8009_(true);
            $$1.f_102809_.f_104207_ = p_117819_.m_36170_(PlayerModelPart.HAT);
            $$1.f_103378_.f_104207_ = p_117819_.m_36170_(PlayerModelPart.JACKET);
            $$1.f_103376_.f_104207_ = p_117819_.m_36170_(PlayerModelPart.LEFT_PANTS_LEG);
            $$1.f_103377_.f_104207_ = p_117819_.m_36170_(PlayerModelPart.RIGHT_PANTS_LEG);
            $$1.f_103374_.f_104207_ = p_117819_.m_36170_(PlayerModelPart.LEFT_SLEEVE);
            $$1.f_103375_.f_104207_ = p_117819_.m_36170_(PlayerModelPart.RIGHT_SLEEVE);
            $$1.f_102817_ = p_117819_.m_6047_();
            HumanoidModel.ArmPose $$2 = PlayerRenderer.m_117794_(p_117819_, InteractionHand.MAIN_HAND);
            HumanoidModel.ArmPose $$3 = PlayerRenderer.m_117794_(p_117819_, InteractionHand.OFF_HAND);
            if ($$2.m_102897_()) {
                HumanoidModel.ArmPose armPose = $$3 = p_117819_.m_21206_().m_41619_() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
            }
            if (p_117819_.m_5737_() == HumanoidArm.RIGHT) {
                $$1.f_102816_ = $$2;
                $$1.f_102815_ = $$3;
            } else {
                $$1.f_102816_ = $$3;
                $$1.f_102815_ = $$2;
            }
        }
    }

    private static HumanoidModel.ArmPose m_117794_(AbstractClientPlayer p_117795_, InteractionHand p_117796_) {
        ItemStack $$2 = p_117795_.m_21120_(p_117796_);
        if ($$2.m_41619_()) {
            return HumanoidModel.ArmPose.EMPTY;
        }
        if (p_117795_.m_7655_() == p_117796_ && p_117795_.m_21212_() > 0) {
            UseAnim $$3 = $$2.m_41780_();
            if ($$3 == UseAnim.BLOCK) {
                return HumanoidModel.ArmPose.BLOCK;
            }
            if ($$3 == UseAnim.BOW) {
                return HumanoidModel.ArmPose.BOW_AND_ARROW;
            }
            if ($$3 == UseAnim.SPEAR) {
                return HumanoidModel.ArmPose.THROW_SPEAR;
            }
            if ($$3 == UseAnim.CROSSBOW && p_117796_ == p_117795_.m_7655_()) {
                return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
            }
            if ($$3 == UseAnim.SPYGLASS) {
                return HumanoidModel.ArmPose.SPYGLASS;
            }
            if ($$3 == UseAnim.TOOT_HORN) {
                return HumanoidModel.ArmPose.TOOT_HORN;
            }
        } else if (!p_117795_.f_20911_ && $$2.m_150930_(Items.f_42717_) && CrossbowItem.m_40932_($$2)) {
            return HumanoidModel.ArmPose.CROSSBOW_HOLD;
        }
        return HumanoidModel.ArmPose.ITEM;
    }

    @Override
    public ResourceLocation m_5478_(AbstractClientPlayer p_117783_) {
        return p_117783_.m_108560_();
    }

    @Override
    protected void m_7546_(AbstractClientPlayer p_117798_, PoseStack p_117799_, float p_117800_) {
        float $$3 = 0.9375f;
        p_117799_.m_85841_(0.9375f, 0.9375f, 0.9375f);
    }

    @Override
    protected void m_7649_(AbstractClientPlayer p_117808_, Component p_117809_, PoseStack p_117810_, MultiBufferSource p_117811_, int p_117812_) {
        Scoreboard $$6;
        Objective $$7;
        double $$5 = this.f_114476_.m_114471_(p_117808_);
        p_117810_.m_85836_();
        if ($$5 < 100.0 && ($$7 = ($$6 = p_117808_.m_36329_()).m_83416_(2)) != null) {
            Score $$8 = $$6.m_83471_(p_117808_.m_6302_(), $$7);
            super.m_7649_(p_117808_, Component.m_237113_(Integer.toString($$8.m_83400_())).m_130946_(" ").m_7220_($$7.m_83322_()), p_117810_, p_117811_, p_117812_);
            Objects.requireNonNull(this.m_114481_());
            p_117810_.m_85837_(0.0, 9.0f * 1.15f * 0.025f, 0.0);
        }
        super.m_7649_(p_117808_, p_117809_, p_117810_, p_117811_, p_117812_);
        p_117810_.m_85849_();
    }

    public void m_117770_(PoseStack p_117771_, MultiBufferSource p_117772_, int p_117773_, AbstractClientPlayer p_117774_) {
        this.m_117775_(p_117771_, p_117772_, p_117773_, p_117774_, ((PlayerModel)this.f_115290_).f_102811_, ((PlayerModel)this.f_115290_).f_103375_);
    }

    public void m_117813_(PoseStack p_117814_, MultiBufferSource p_117815_, int p_117816_, AbstractClientPlayer p_117817_) {
        this.m_117775_(p_117814_, p_117815_, p_117816_, p_117817_, ((PlayerModel)this.f_115290_).f_102812_, ((PlayerModel)this.f_115290_).f_103374_);
    }

    private void m_117775_(PoseStack p_117776_, MultiBufferSource p_117777_, int p_117778_, AbstractClientPlayer p_117779_, ModelPart p_117780_, ModelPart p_117781_) {
        PlayerModel $$6 = (PlayerModel)this.m_7200_();
        this.m_117818_(p_117779_);
        $$6.f_102608_ = 0.0f;
        $$6.f_102817_ = false;
        $$6.f_102818_ = 0.0f;
        $$6.m_6973_(p_117779_, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        p_117780_.f_104203_ = 0.0f;
        p_117780_.m_104301_(p_117776_, p_117777_.m_6299_(RenderType.m_110446_(p_117779_.m_108560_())), p_117778_, OverlayTexture.f_118083_);
        p_117781_.f_104203_ = 0.0f;
        p_117781_.m_104301_(p_117776_, p_117777_.m_6299_(RenderType.m_110473_(p_117779_.m_108560_())), p_117778_, OverlayTexture.f_118083_);
    }

    @Override
    protected void m_7523_(AbstractClientPlayer p_117802_, PoseStack p_117803_, float p_117804_, float p_117805_, float p_117806_) {
        float $$5 = p_117802_.m_20998_(p_117806_);
        if (p_117802_.m_21255_()) {
            super.m_7523_(p_117802_, p_117803_, p_117804_, p_117805_, p_117806_);
            float $$6 = (float)p_117802_.m_21256_() + p_117806_;
            float $$7 = Mth.m_14036_($$6 * $$6 / 100.0f, 0.0f, 1.0f);
            if (!p_117802_.m_21209_()) {
                p_117803_.m_85845_(Vector3f.f_122223_.m_122240_($$7 * (-90.0f - p_117802_.m_146909_())));
            }
            Vec3 $$8 = p_117802_.m_20252_(p_117806_);
            Vec3 $$9 = p_117802_.m_20184_();
            double $$10 = $$9.m_165925_();
            double $$11 = $$8.m_165925_();
            if ($$10 > 0.0 && $$11 > 0.0) {
                double $$12 = ($$9.f_82479_ * $$8.f_82479_ + $$9.f_82481_ * $$8.f_82481_) / Math.sqrt($$10 * $$11);
                double $$13 = $$9.f_82479_ * $$8.f_82481_ - $$9.f_82481_ * $$8.f_82479_;
                p_117803_.m_85845_(Vector3f.f_122225_.m_122270_((float)(Math.signum($$13) * Math.acos($$12))));
            }
        } else if ($$5 > 0.0f) {
            super.m_7523_(p_117802_, p_117803_, p_117804_, p_117805_, p_117806_);
            float $$14 = p_117802_.m_20069_() ? -90.0f - p_117802_.m_146909_() : -90.0f;
            float $$15 = Mth.m_14179_($$5, 0.0f, $$14);
            p_117803_.m_85845_(Vector3f.f_122223_.m_122240_($$15));
            if (p_117802_.m_6067_()) {
                p_117803_.m_85837_(0.0, -1.0, 0.3f);
            }
        } else {
            super.m_7523_(p_117802_, p_117803_, p_117804_, p_117805_, p_117806_);
        }
    }
}

