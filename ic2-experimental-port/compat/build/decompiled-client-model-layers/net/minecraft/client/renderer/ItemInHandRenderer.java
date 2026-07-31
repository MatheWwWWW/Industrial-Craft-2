/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.base.MoreObjects
 */
package net.minecraft.client.renderer;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class ItemInHandRenderer {
    private static final RenderType f_109297_ = RenderType.m_110497_(new ResourceLocation("textures/map/map_background.png"));
    private static final RenderType f_109298_ = RenderType.m_110497_(new ResourceLocation("textures/map/map_background_checkerboard.png"));
    private static final float f_172888_ = -0.4f;
    private static final float f_172889_ = 0.2f;
    private static final float f_172890_ = -0.2f;
    private static final float f_172891_ = -0.6f;
    private static final float f_172892_ = 0.56f;
    private static final float f_172893_ = -0.52f;
    private static final float f_172894_ = -0.72f;
    private static final float f_172895_ = 45.0f;
    private static final float f_172896_ = -80.0f;
    private static final float f_172897_ = -20.0f;
    private static final float f_172898_ = -20.0f;
    private static final float f_172899_ = 10.0f;
    private static final float f_172900_ = 90.0f;
    private static final float f_172901_ = 30.0f;
    private static final float f_172902_ = 0.6f;
    private static final float f_172903_ = -0.5f;
    private static final float f_172904_ = 0.0f;
    private static final double f_172905_ = 27.0;
    private static final float f_172906_ = 0.8f;
    private static final float f_172907_ = 0.1f;
    private static final float f_172908_ = -0.3f;
    private static final float f_172909_ = 0.4f;
    private static final float f_172910_ = -0.4f;
    private static final float f_172911_ = 70.0f;
    private static final float f_172842_ = -20.0f;
    private static final float f_172843_ = -0.6f;
    private static final float f_172844_ = 0.8f;
    private static final float f_172845_ = 0.8f;
    private static final float f_172846_ = -0.75f;
    private static final float f_172847_ = -0.9f;
    private static final float f_172848_ = 45.0f;
    private static final float f_172849_ = -1.0f;
    private static final float f_172850_ = 3.6f;
    private static final float f_172851_ = 3.5f;
    private static final float f_172852_ = 5.6f;
    private static final int f_172853_ = 200;
    private static final int f_172854_ = -135;
    private static final int f_172855_ = 120;
    private static final float f_172856_ = -0.4f;
    private static final float f_172857_ = -0.2f;
    private static final float f_172858_ = 0.0f;
    private static final float f_172859_ = 0.04f;
    private static final float f_172860_ = -0.72f;
    private static final float f_172861_ = -1.2f;
    private static final float f_172862_ = -0.5f;
    private static final float f_172863_ = 45.0f;
    private static final float f_172864_ = -85.0f;
    private static final float f_172865_ = 45.0f;
    private static final float f_172866_ = 92.0f;
    private static final float f_172867_ = -41.0f;
    private static final float f_172868_ = 0.3f;
    private static final float f_172869_ = -1.1f;
    private static final float f_172870_ = 0.45f;
    private static final float f_172871_ = 20.0f;
    private static final float f_172872_ = 0.38f;
    private static final float f_172873_ = -0.5f;
    private static final float f_172874_ = -0.5f;
    private static final float f_172875_ = 0.0f;
    private static final float f_172876_ = 0.0078125f;
    private static final int f_172877_ = 7;
    private static final int f_172878_ = 128;
    private static final int f_172879_ = 128;
    private static final float f_172880_ = 0.0f;
    private static final float f_172881_ = 0.0f;
    private static final float f_172882_ = 0.04f;
    private static final float f_172883_ = 0.0f;
    private static final float f_172884_ = 0.004f;
    private static final float f_172885_ = 0.0f;
    private static final float f_172886_ = 0.2f;
    private static final float f_172887_ = 0.1f;
    private final Minecraft f_109299_;
    private ItemStack f_109300_ = ItemStack.f_41583_;
    private ItemStack f_109301_ = ItemStack.f_41583_;
    private float f_109302_;
    private float f_109303_;
    private float f_109304_;
    private float f_109305_;
    private final EntityRenderDispatcher f_109306_;
    private final ItemRenderer f_109307_;

    public ItemInHandRenderer(Minecraft p_234241_, EntityRenderDispatcher p_234242_, ItemRenderer p_234243_) {
        this.f_109299_ = p_234241_;
        this.f_109306_ = p_234242_;
        this.f_109307_ = p_234243_;
    }

    public void m_109322_(LivingEntity p_109323_, ItemStack p_109324_, ItemTransforms.TransformType p_109325_, boolean p_109326_, PoseStack p_109327_, MultiBufferSource p_109328_, int p_109329_) {
        if (p_109324_.m_41619_()) {
            return;
        }
        this.f_109307_.m_174242_(p_109323_, p_109324_, p_109325_, p_109326_, p_109327_, p_109328_, p_109323_.f_19853_, p_109329_, OverlayTexture.f_118083_, p_109323_.m_19879_() + p_109325_.ordinal());
    }

    private float m_109312_(float p_109313_) {
        float $$1 = 1.0f - p_109313_ / 45.0f + 0.1f;
        $$1 = Mth.m_14036_($$1, 0.0f, 1.0f);
        $$1 = -Mth.m_14089_($$1 * (float)Math.PI) * 0.5f + 0.5f;
        return $$1;
    }

    private void m_109361_(PoseStack p_109362_, MultiBufferSource p_109363_, int p_109364_, HumanoidArm p_109365_) {
        RenderSystem.m_157456_(0, this.f_109299_.f_91074_.m_108560_());
        PlayerRenderer $$4 = (PlayerRenderer)this.f_109306_.m_114382_(this.f_109299_.f_91074_);
        p_109362_.m_85836_();
        float $$5 = p_109365_ == HumanoidArm.RIGHT ? 1.0f : -1.0f;
        p_109362_.m_85845_(Vector3f.f_122225_.m_122240_(92.0f));
        p_109362_.m_85845_(Vector3f.f_122223_.m_122240_(45.0f));
        p_109362_.m_85845_(Vector3f.f_122227_.m_122240_($$5 * -41.0f));
        p_109362_.m_85837_($$5 * 0.3f, -1.1f, 0.45f);
        if (p_109365_ == HumanoidArm.RIGHT) {
            $$4.m_117770_(p_109362_, p_109363_, p_109364_, this.f_109299_.f_91074_);
        } else {
            $$4.m_117813_(p_109362_, p_109363_, p_109364_, this.f_109299_.f_91074_);
        }
        p_109362_.m_85849_();
    }

    private void m_109353_(PoseStack p_109354_, MultiBufferSource p_109355_, int p_109356_, float p_109357_, HumanoidArm p_109358_, float p_109359_, ItemStack p_109360_) {
        float $$7 = p_109358_ == HumanoidArm.RIGHT ? 1.0f : -1.0f;
        p_109354_.m_85837_($$7 * 0.125f, -0.125, 0.0);
        if (!this.f_109299_.f_91074_.m_20145_()) {
            p_109354_.m_85836_();
            p_109354_.m_85845_(Vector3f.f_122227_.m_122240_($$7 * 10.0f));
            this.m_109346_(p_109354_, p_109355_, p_109356_, p_109357_, p_109359_, p_109358_);
            p_109354_.m_85849_();
        }
        p_109354_.m_85836_();
        p_109354_.m_85837_($$7 * 0.51f, -0.08f + p_109357_ * -1.2f, -0.75);
        float $$8 = Mth.m_14116_(p_109359_);
        float $$9 = Mth.m_14031_($$8 * (float)Math.PI);
        float $$10 = -0.5f * $$9;
        float $$11 = 0.4f * Mth.m_14031_($$8 * ((float)Math.PI * 2));
        float $$12 = -0.3f * Mth.m_14031_(p_109359_ * (float)Math.PI);
        p_109354_.m_85837_($$7 * $$10, $$11 - 0.3f * $$9, $$12);
        p_109354_.m_85845_(Vector3f.f_122223_.m_122240_($$9 * -45.0f));
        p_109354_.m_85845_(Vector3f.f_122225_.m_122240_($$7 * $$9 * -30.0f));
        this.m_109366_(p_109354_, p_109355_, p_109356_, p_109360_);
        p_109354_.m_85849_();
    }

    private void m_109339_(PoseStack p_109340_, MultiBufferSource p_109341_, int p_109342_, float p_109343_, float p_109344_, float p_109345_) {
        float $$6 = Mth.m_14116_(p_109345_);
        float $$7 = -0.2f * Mth.m_14031_(p_109345_ * (float)Math.PI);
        float $$8 = -0.4f * Mth.m_14031_($$6 * (float)Math.PI);
        p_109340_.m_85837_(0.0, -$$7 / 2.0f, $$8);
        float $$9 = this.m_109312_(p_109343_);
        p_109340_.m_85837_(0.0, 0.04f + p_109344_ * -1.2f + $$9 * -0.5f, -0.72f);
        p_109340_.m_85845_(Vector3f.f_122223_.m_122240_($$9 * -85.0f));
        if (!this.f_109299_.f_91074_.m_20145_()) {
            p_109340_.m_85836_();
            p_109340_.m_85845_(Vector3f.f_122225_.m_122240_(90.0f));
            this.m_109361_(p_109340_, p_109341_, p_109342_, HumanoidArm.RIGHT);
            this.m_109361_(p_109340_, p_109341_, p_109342_, HumanoidArm.LEFT);
            p_109340_.m_85849_();
        }
        float $$10 = Mth.m_14031_($$6 * (float)Math.PI);
        p_109340_.m_85845_(Vector3f.f_122223_.m_122240_($$10 * 20.0f));
        p_109340_.m_85841_(2.0f, 2.0f, 2.0f);
        this.m_109366_(p_109340_, p_109341_, p_109342_, this.f_109300_);
    }

    private void m_109366_(PoseStack p_109367_, MultiBufferSource p_109368_, int p_109369_, ItemStack p_109370_) {
        p_109367_.m_85845_(Vector3f.f_122225_.m_122240_(180.0f));
        p_109367_.m_85845_(Vector3f.f_122227_.m_122240_(180.0f));
        p_109367_.m_85841_(0.38f, 0.38f, 0.38f);
        p_109367_.m_85837_(-0.5, -0.5, 0.0);
        p_109367_.m_85841_(0.0078125f, 0.0078125f, 0.0078125f);
        Integer $$4 = MapItem.m_151131_(p_109370_);
        MapItemSavedData $$5 = MapItem.m_151128_($$4, this.f_109299_.f_91073_);
        VertexConsumer $$6 = p_109368_.m_6299_($$5 == null ? f_109297_ : f_109298_);
        Matrix4f $$7 = p_109367_.m_85850_().m_85861_();
        $$6.m_85982_($$7, -7.0f, 135.0f, 0.0f).m_6122_(255, 255, 255, 255).m_7421_(0.0f, 1.0f).m_85969_(p_109369_).m_5752_();
        $$6.m_85982_($$7, 135.0f, 135.0f, 0.0f).m_6122_(255, 255, 255, 255).m_7421_(1.0f, 1.0f).m_85969_(p_109369_).m_5752_();
        $$6.m_85982_($$7, 135.0f, -7.0f, 0.0f).m_6122_(255, 255, 255, 255).m_7421_(1.0f, 0.0f).m_85969_(p_109369_).m_5752_();
        $$6.m_85982_($$7, -7.0f, -7.0f, 0.0f).m_6122_(255, 255, 255, 255).m_7421_(0.0f, 0.0f).m_85969_(p_109369_).m_5752_();
        if ($$5 != null) {
            this.f_109299_.f_91063_.m_109151_().m_168771_(p_109367_, p_109368_, $$4, $$5, false, p_109369_);
        }
    }

    private void m_109346_(PoseStack p_109347_, MultiBufferSource p_109348_, int p_109349_, float p_109350_, float p_109351_, HumanoidArm p_109352_) {
        boolean $$6 = p_109352_ != HumanoidArm.LEFT;
        float $$7 = $$6 ? 1.0f : -1.0f;
        float $$8 = Mth.m_14116_(p_109351_);
        float $$9 = -0.3f * Mth.m_14031_($$8 * (float)Math.PI);
        float $$10 = 0.4f * Mth.m_14031_($$8 * ((float)Math.PI * 2));
        float $$11 = -0.4f * Mth.m_14031_(p_109351_ * (float)Math.PI);
        p_109347_.m_85837_($$7 * ($$9 + 0.64000005f), $$10 + -0.6f + p_109350_ * -0.6f, $$11 + -0.71999997f);
        p_109347_.m_85845_(Vector3f.f_122225_.m_122240_($$7 * 45.0f));
        float $$12 = Mth.m_14031_(p_109351_ * p_109351_ * (float)Math.PI);
        float $$13 = Mth.m_14031_($$8 * (float)Math.PI);
        p_109347_.m_85845_(Vector3f.f_122225_.m_122240_($$7 * $$13 * 70.0f));
        p_109347_.m_85845_(Vector3f.f_122227_.m_122240_($$7 * $$12 * -20.0f));
        LocalPlayer $$14 = this.f_109299_.f_91074_;
        RenderSystem.m_157456_(0, $$14.m_108560_());
        p_109347_.m_85837_($$7 * -1.0f, 3.6f, 3.5);
        p_109347_.m_85845_(Vector3f.f_122227_.m_122240_($$7 * 120.0f));
        p_109347_.m_85845_(Vector3f.f_122223_.m_122240_(200.0f));
        p_109347_.m_85845_(Vector3f.f_122225_.m_122240_($$7 * -135.0f));
        p_109347_.m_85837_($$7 * 5.6f, 0.0, 0.0);
        PlayerRenderer $$15 = (PlayerRenderer)this.f_109306_.m_114382_($$14);
        if ($$6) {
            $$15.m_117770_(p_109347_, p_109348_, p_109349_, $$14);
        } else {
            $$15.m_117813_(p_109347_, p_109348_, p_109349_, $$14);
        }
    }

    private void m_109330_(PoseStack p_109331_, float p_109332_, HumanoidArm p_109333_, ItemStack p_109334_) {
        float $$4 = (float)this.f_109299_.f_91074_.m_21212_() - p_109332_ + 1.0f;
        float $$5 = $$4 / (float)p_109334_.m_41779_();
        if ($$5 < 0.8f) {
            float $$6 = Mth.m_14154_(Mth.m_14089_($$4 / 4.0f * (float)Math.PI) * 0.1f);
            p_109331_.m_85837_(0.0, $$6, 0.0);
        }
        float $$7 = 1.0f - (float)Math.pow($$5, 27.0);
        int $$8 = p_109333_ == HumanoidArm.RIGHT ? 1 : -1;
        p_109331_.m_85837_($$7 * 0.6f * (float)$$8, $$7 * -0.5f, $$7 * 0.0f);
        p_109331_.m_85845_(Vector3f.f_122225_.m_122240_((float)$$8 * $$7 * 90.0f));
        p_109331_.m_85845_(Vector3f.f_122223_.m_122240_($$7 * 10.0f));
        p_109331_.m_85845_(Vector3f.f_122227_.m_122240_((float)$$8 * $$7 * 30.0f));
    }

    private void m_109335_(PoseStack p_109336_, HumanoidArm p_109337_, float p_109338_) {
        int $$3 = p_109337_ == HumanoidArm.RIGHT ? 1 : -1;
        float $$4 = Mth.m_14031_(p_109338_ * p_109338_ * (float)Math.PI);
        p_109336_.m_85845_(Vector3f.f_122225_.m_122240_((float)$$3 * (45.0f + $$4 * -20.0f)));
        float $$5 = Mth.m_14031_(Mth.m_14116_(p_109338_) * (float)Math.PI);
        p_109336_.m_85845_(Vector3f.f_122227_.m_122240_((float)$$3 * $$5 * -20.0f));
        p_109336_.m_85845_(Vector3f.f_122223_.m_122240_($$5 * -80.0f));
        p_109336_.m_85845_(Vector3f.f_122225_.m_122240_((float)$$3 * -45.0f));
    }

    private void m_109382_(PoseStack p_109383_, HumanoidArm p_109384_, float p_109385_) {
        int $$3 = p_109384_ == HumanoidArm.RIGHT ? 1 : -1;
        p_109383_.m_85837_((float)$$3 * 0.56f, -0.52f + p_109385_ * -0.6f, -0.72f);
    }

    public void m_109314_(float p_109315_, PoseStack p_109316_, MultiBufferSource.BufferSource p_109317_, LocalPlayer p_109318_, int p_109319_) {
        float $$5 = p_109318_.m_21324_(p_109315_);
        InteractionHand $$6 = (InteractionHand)((Object)MoreObjects.firstNonNull((Object)((Object)p_109318_.f_20912_), (Object)((Object)InteractionHand.MAIN_HAND)));
        float $$7 = Mth.m_14179_(p_109315_, p_109318_.f_19860_, p_109318_.m_146909_());
        HandRenderSelection $$8 = ItemInHandRenderer.m_172914_(p_109318_);
        float $$9 = Mth.m_14179_(p_109315_, p_109318_.f_108588_, p_109318_.f_108586_);
        float $$10 = Mth.m_14179_(p_109315_, p_109318_.f_108587_, p_109318_.f_108585_);
        p_109316_.m_85845_(Vector3f.f_122223_.m_122240_((p_109318_.m_5686_(p_109315_) - $$9) * 0.1f));
        p_109316_.m_85845_(Vector3f.f_122225_.m_122240_((p_109318_.m_5675_(p_109315_) - $$10) * 0.1f));
        if ($$8.f_172921_) {
            float $$11 = $$6 == InteractionHand.MAIN_HAND ? $$5 : 0.0f;
            float $$12 = 1.0f - Mth.m_14179_(p_109315_, this.f_109303_, this.f_109302_);
            this.m_109371_(p_109318_, p_109315_, $$7, InteractionHand.MAIN_HAND, $$11, this.f_109300_, $$12, p_109316_, p_109317_, p_109319_);
        }
        if ($$8.f_172922_) {
            float $$13 = $$6 == InteractionHand.OFF_HAND ? $$5 : 0.0f;
            float $$14 = 1.0f - Mth.m_14179_(p_109315_, this.f_109305_, this.f_109304_);
            this.m_109371_(p_109318_, p_109315_, $$7, InteractionHand.OFF_HAND, $$13, this.f_109301_, $$14, p_109316_, p_109317_, p_109319_);
        }
        p_109317_.m_109911_();
    }

    @VisibleForTesting
    static HandRenderSelection m_172914_(LocalPlayer p_172915_) {
        boolean $$4;
        ItemStack $$1 = p_172915_.m_21205_();
        ItemStack $$2 = p_172915_.m_21206_();
        boolean $$3 = $$1.m_150930_(Items.f_42411_) || $$2.m_150930_(Items.f_42411_);
        boolean bl = $$4 = $$1.m_150930_(Items.f_42717_) || $$2.m_150930_(Items.f_42717_);
        if (!$$3 && !$$4) {
            return HandRenderSelection.RENDER_BOTH_HANDS;
        }
        if (p_172915_.m_6117_()) {
            return ItemInHandRenderer.m_172916_(p_172915_);
        }
        if (ItemInHandRenderer.m_172912_($$1)) {
            return HandRenderSelection.RENDER_MAIN_HAND_ONLY;
        }
        return HandRenderSelection.RENDER_BOTH_HANDS;
    }

    private static HandRenderSelection m_172916_(LocalPlayer p_172917_) {
        ItemStack $$1 = p_172917_.m_21211_();
        InteractionHand $$2 = p_172917_.m_7655_();
        if ($$1.m_150930_(Items.f_42411_) || $$1.m_150930_(Items.f_42717_)) {
            return HandRenderSelection.m_172931_($$2);
        }
        return $$2 == InteractionHand.MAIN_HAND && ItemInHandRenderer.m_172912_(p_172917_.m_21206_()) ? HandRenderSelection.RENDER_MAIN_HAND_ONLY : HandRenderSelection.RENDER_BOTH_HANDS;
    }

    private static boolean m_172912_(ItemStack p_172913_) {
        return p_172913_.m_150930_(Items.f_42717_) && CrossbowItem.m_40932_(p_172913_);
    }

    private void m_109371_(AbstractClientPlayer p_109372_, float p_109373_, float p_109374_, InteractionHand p_109375_, float p_109376_, ItemStack p_109377_, float p_109378_, PoseStack p_109379_, MultiBufferSource p_109380_, int p_109381_) {
        if (p_109372_.m_150108_()) {
            return;
        }
        boolean $$10 = p_109375_ == InteractionHand.MAIN_HAND;
        HumanoidArm $$11 = $$10 ? p_109372_.m_5737_() : p_109372_.m_5737_().m_20828_();
        p_109379_.m_85836_();
        if (p_109377_.m_41619_()) {
            if ($$10 && !p_109372_.m_20145_()) {
                this.m_109346_(p_109379_, p_109380_, p_109381_, p_109378_, p_109376_, $$11);
            }
        } else if (p_109377_.m_150930_(Items.f_42573_)) {
            if ($$10 && this.f_109301_.m_41619_()) {
                this.m_109339_(p_109379_, p_109380_, p_109381_, p_109374_, p_109378_, p_109376_);
            } else {
                this.m_109353_(p_109379_, p_109380_, p_109381_, p_109378_, $$11, p_109376_, p_109377_);
            }
        } else if (p_109377_.m_150930_(Items.f_42717_)) {
            int $$14;
            boolean $$12 = CrossbowItem.m_40932_(p_109377_);
            boolean $$13 = $$11 == HumanoidArm.RIGHT;
            int n = $$14 = $$13 ? 1 : -1;
            if (p_109372_.m_6117_() && p_109372_.m_21212_() > 0 && p_109372_.m_7655_() == p_109375_) {
                this.m_109382_(p_109379_, $$11, p_109378_);
                p_109379_.m_85837_((float)$$14 * -0.4785682f, -0.094387f, 0.05731530860066414);
                p_109379_.m_85845_(Vector3f.f_122223_.m_122240_(-11.935f));
                p_109379_.m_85845_(Vector3f.f_122225_.m_122240_((float)$$14 * 65.3f));
                p_109379_.m_85845_(Vector3f.f_122227_.m_122240_((float)$$14 * -9.785f));
                float $$15 = (float)p_109377_.m_41779_() - ((float)this.f_109299_.f_91074_.m_21212_() - p_109373_ + 1.0f);
                float $$16 = $$15 / (float)CrossbowItem.m_40939_(p_109377_);
                if ($$16 > 1.0f) {
                    $$16 = 1.0f;
                }
                if ($$16 > 0.1f) {
                    float $$17 = Mth.m_14031_(($$15 - 0.1f) * 1.3f);
                    float $$18 = $$16 - 0.1f;
                    float $$19 = $$17 * $$18;
                    p_109379_.m_85837_($$19 * 0.0f, $$19 * 0.004f, $$19 * 0.0f);
                }
                p_109379_.m_85837_($$16 * 0.0f, $$16 * 0.0f, $$16 * 0.04f);
                p_109379_.m_85841_(1.0f, 1.0f, 1.0f + $$16 * 0.2f);
                p_109379_.m_85845_(Vector3f.f_122224_.m_122240_((float)$$14 * 45.0f));
            } else {
                float $$20 = -0.4f * Mth.m_14031_(Mth.m_14116_(p_109376_) * (float)Math.PI);
                float $$21 = 0.2f * Mth.m_14031_(Mth.m_14116_(p_109376_) * ((float)Math.PI * 2));
                float $$22 = -0.2f * Mth.m_14031_(p_109376_ * (float)Math.PI);
                p_109379_.m_85837_((float)$$14 * $$20, $$21, $$22);
                this.m_109382_(p_109379_, $$11, p_109378_);
                this.m_109335_(p_109379_, $$11, p_109376_);
                if ($$12 && p_109376_ < 0.001f && $$10) {
                    p_109379_.m_85837_((float)$$14 * -0.641864f, 0.0, 0.0);
                    p_109379_.m_85845_(Vector3f.f_122225_.m_122240_((float)$$14 * 10.0f));
                }
            }
            this.m_109322_(p_109372_, p_109377_, $$13 ? ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !$$13, p_109379_, p_109380_, p_109381_);
        } else {
            boolean $$23;
            boolean bl = $$23 = $$11 == HumanoidArm.RIGHT;
            if (p_109372_.m_6117_() && p_109372_.m_21212_() > 0 && p_109372_.m_7655_() == p_109375_) {
                int $$24 = $$23 ? 1 : -1;
                switch (p_109377_.m_41780_()) {
                    case NONE: {
                        this.m_109382_(p_109379_, $$11, p_109378_);
                        break;
                    }
                    case EAT: 
                    case DRINK: {
                        this.m_109330_(p_109379_, p_109373_, $$11, p_109377_);
                        this.m_109382_(p_109379_, $$11, p_109378_);
                        break;
                    }
                    case BLOCK: {
                        this.m_109382_(p_109379_, $$11, p_109378_);
                        break;
                    }
                    case BOW: {
                        this.m_109382_(p_109379_, $$11, p_109378_);
                        p_109379_.m_85837_((float)$$24 * -0.2785682f, 0.18344387412071228, 0.15731531381607056);
                        p_109379_.m_85845_(Vector3f.f_122223_.m_122240_(-13.935f));
                        p_109379_.m_85845_(Vector3f.f_122225_.m_122240_((float)$$24 * 35.3f));
                        p_109379_.m_85845_(Vector3f.f_122227_.m_122240_((float)$$24 * -9.785f));
                        float $$25 = (float)p_109377_.m_41779_() - ((float)this.f_109299_.f_91074_.m_21212_() - p_109373_ + 1.0f);
                        float $$26 = $$25 / 20.0f;
                        $$26 = ($$26 * $$26 + $$26 * 2.0f) / 3.0f;
                        if ($$26 > 1.0f) {
                            $$26 = 1.0f;
                        }
                        if ($$26 > 0.1f) {
                            float $$27 = Mth.m_14031_(($$25 - 0.1f) * 1.3f);
                            float $$28 = $$26 - 0.1f;
                            float $$29 = $$27 * $$28;
                            p_109379_.m_85837_($$29 * 0.0f, $$29 * 0.004f, $$29 * 0.0f);
                        }
                        p_109379_.m_85837_($$26 * 0.0f, $$26 * 0.0f, $$26 * 0.04f);
                        p_109379_.m_85841_(1.0f, 1.0f, 1.0f + $$26 * 0.2f);
                        p_109379_.m_85845_(Vector3f.f_122224_.m_122240_((float)$$24 * 45.0f));
                        break;
                    }
                    case SPEAR: {
                        this.m_109382_(p_109379_, $$11, p_109378_);
                        p_109379_.m_85837_((float)$$24 * -0.5f, 0.7f, 0.1f);
                        p_109379_.m_85845_(Vector3f.f_122223_.m_122240_(-55.0f));
                        p_109379_.m_85845_(Vector3f.f_122225_.m_122240_((float)$$24 * 35.3f));
                        p_109379_.m_85845_(Vector3f.f_122227_.m_122240_((float)$$24 * -9.785f));
                        float $$30 = (float)p_109377_.m_41779_() - ((float)this.f_109299_.f_91074_.m_21212_() - p_109373_ + 1.0f);
                        float $$31 = $$30 / 10.0f;
                        if ($$31 > 1.0f) {
                            $$31 = 1.0f;
                        }
                        if ($$31 > 0.1f) {
                            float $$32 = Mth.m_14031_(($$30 - 0.1f) * 1.3f);
                            float $$33 = $$31 - 0.1f;
                            float $$34 = $$32 * $$33;
                            p_109379_.m_85837_($$34 * 0.0f, $$34 * 0.004f, $$34 * 0.0f);
                        }
                        p_109379_.m_85837_(0.0, 0.0, $$31 * 0.2f);
                        p_109379_.m_85841_(1.0f, 1.0f, 1.0f + $$31 * 0.2f);
                        p_109379_.m_85845_(Vector3f.f_122224_.m_122240_((float)$$24 * 45.0f));
                        break;
                    }
                }
            } else if (p_109372_.m_21209_()) {
                this.m_109382_(p_109379_, $$11, p_109378_);
                int $$35 = $$23 ? 1 : -1;
                p_109379_.m_85837_((float)$$35 * -0.4f, 0.8f, 0.3f);
                p_109379_.m_85845_(Vector3f.f_122225_.m_122240_((float)$$35 * 65.0f));
                p_109379_.m_85845_(Vector3f.f_122227_.m_122240_((float)$$35 * -85.0f));
            } else {
                float $$36 = -0.4f * Mth.m_14031_(Mth.m_14116_(p_109376_) * (float)Math.PI);
                float $$37 = 0.2f * Mth.m_14031_(Mth.m_14116_(p_109376_) * ((float)Math.PI * 2));
                float $$38 = -0.2f * Mth.m_14031_(p_109376_ * (float)Math.PI);
                int $$39 = $$23 ? 1 : -1;
                p_109379_.m_85837_((float)$$39 * $$36, $$37, $$38);
                this.m_109382_(p_109379_, $$11, p_109378_);
                this.m_109335_(p_109379_, $$11, p_109376_);
            }
            this.m_109322_(p_109372_, p_109377_, $$23 ? ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !$$23, p_109379_, p_109380_, p_109381_);
        }
        p_109379_.m_85849_();
    }

    public void m_109311_() {
        this.f_109303_ = this.f_109302_;
        this.f_109305_ = this.f_109304_;
        LocalPlayer $$0 = this.f_109299_.f_91074_;
        ItemStack $$1 = $$0.m_21205_();
        ItemStack $$2 = $$0.m_21206_();
        if (ItemStack.m_41728_(this.f_109300_, $$1)) {
            this.f_109300_ = $$1;
        }
        if (ItemStack.m_41728_(this.f_109301_, $$2)) {
            this.f_109301_ = $$2;
        }
        if ($$0.m_108637_()) {
            this.f_109302_ = Mth.m_14036_(this.f_109302_ - 0.4f, 0.0f, 1.0f);
            this.f_109304_ = Mth.m_14036_(this.f_109304_ - 0.4f, 0.0f, 1.0f);
        } else {
            float $$3 = $$0.m_36403_(1.0f);
            this.f_109302_ += Mth.m_14036_((this.f_109300_ == $$1 ? $$3 * $$3 * $$3 : 0.0f) - this.f_109302_, -0.4f, 0.4f);
            this.f_109304_ += Mth.m_14036_((float)(this.f_109301_ == $$2 ? 1 : 0) - this.f_109304_, -0.4f, 0.4f);
        }
        if (this.f_109302_ < 0.1f) {
            this.f_109300_ = $$1;
        }
        if (this.f_109304_ < 0.1f) {
            this.f_109301_ = $$2;
        }
    }

    public void m_109320_(InteractionHand p_109321_) {
        if (p_109321_ == InteractionHand.MAIN_HAND) {
            this.f_109302_ = 0.0f;
        } else {
            this.f_109304_ = 0.0f;
        }
    }

    @VisibleForTesting
    static final class HandRenderSelection
    extends Enum<HandRenderSelection> {
        public static final /* enum */ HandRenderSelection RENDER_BOTH_HANDS = new HandRenderSelection(true, true);
        public static final /* enum */ HandRenderSelection RENDER_MAIN_HAND_ONLY = new HandRenderSelection(true, false);
        public static final /* enum */ HandRenderSelection RENDER_OFF_HAND_ONLY = new HandRenderSelection(false, true);
        final boolean f_172921_;
        final boolean f_172922_;
        private static final /* synthetic */ HandRenderSelection[] $VALUES;

        public static HandRenderSelection[] values() {
            return (HandRenderSelection[])$VALUES.clone();
        }

        public static HandRenderSelection valueOf(String p_172934_) {
            return Enum.valueOf(HandRenderSelection.class, p_172934_);
        }

        private HandRenderSelection(boolean p_172928_, boolean p_172929_) {
            this.f_172921_ = p_172928_;
            this.f_172922_ = p_172929_;
        }

        public static HandRenderSelection m_172931_(InteractionHand p_172932_) {
            return p_172932_ == InteractionHand.MAIN_HAND ? RENDER_MAIN_HAND_ONLY : RENDER_OFF_HAND_ONLY;
        }

        private static /* synthetic */ HandRenderSelection[] m_172930_() {
            return new HandRenderSelection[]{RENDER_BOTH_HANDS, RENDER_MAIN_HAND_ONLY, RENDER_OFF_HAND_ONLY};
        }

        static {
            $VALUES = HandRenderSelection.m_172930_();
        }
    }
}

