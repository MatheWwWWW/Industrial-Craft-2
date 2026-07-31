/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PlayerItemInHandLayer<T extends Player, M extends EntityModel<T> & HeadedModel>
extends ItemInHandLayer<T, M> {
    private final ItemInHandRenderer f_234864_;
    private static final float f_174513_ = -0.5235988f;
    private static final float f_174514_ = 1.5707964f;

    public PlayerItemInHandLayer(RenderLayerParent<T, M> p_234866_, ItemInHandRenderer p_234867_) {
        super(p_234866_, p_234867_);
        this.f_234864_ = p_234867_;
    }

    @Override
    protected void m_117184_(LivingEntity p_174525_, ItemStack p_174526_, ItemTransforms.TransformType p_174527_, HumanoidArm p_174528_, PoseStack p_174529_, MultiBufferSource p_174530_, int p_174531_) {
        if (p_174526_.m_150930_(Items.f_151059_) && p_174525_.m_21211_() == p_174526_ && p_174525_.f_20913_ == 0) {
            this.m_174517_(p_174525_, p_174526_, p_174528_, p_174529_, p_174530_, p_174531_);
        } else {
            super.m_117184_(p_174525_, p_174526_, p_174527_, p_174528_, p_174529_, p_174530_, p_174531_);
        }
    }

    private void m_174517_(LivingEntity p_174518_, ItemStack p_174519_, HumanoidArm p_174520_, PoseStack p_174521_, MultiBufferSource p_174522_, int p_174523_) {
        p_174521_.m_85836_();
        ModelPart $$6 = ((HeadedModel)this.m_117386_()).m_5585_();
        float $$7 = $$6.f_104203_;
        $$6.f_104203_ = Mth.m_14036_($$6.f_104203_, -0.5235988f, 1.5707964f);
        $$6.m_104299_(p_174521_);
        $$6.f_104203_ = $$7;
        CustomHeadLayer.m_174483_(p_174521_, false);
        boolean $$8 = p_174520_ == HumanoidArm.LEFT;
        p_174521_.m_85837_(($$8 ? -2.5f : 2.5f) / 16.0f, -0.0625, 0.0);
        this.f_234864_.m_109322_(p_174518_, p_174519_, ItemTransforms.TransformType.HEAD, false, p_174521_, p_174522_, p_174523_);
        p_174521_.m_85849_();
    }
}

