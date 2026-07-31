/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemEntityRenderer
extends EntityRenderer<ItemEntity> {
    private static final float f_174189_ = 0.15f;
    private static final int f_174190_ = 48;
    private static final int f_174191_ = 32;
    private static final int f_174192_ = 16;
    private static final int f_174193_ = 1;
    private static final float f_174194_ = 0.0f;
    private static final float f_174195_ = 0.0f;
    private static final float f_174196_ = 0.09375f;
    private final ItemRenderer f_115019_;
    private final RandomSource f_115020_ = RandomSource.m_216327_();

    public ItemEntityRenderer(EntityRendererProvider.Context p_174198_) {
        super(p_174198_);
        this.f_115019_ = p_174198_.m_174025_();
        this.f_114477_ = 0.15f;
        this.f_114478_ = 0.75f;
    }

    private int m_115042_(ItemStack p_115043_) {
        int $$1 = 1;
        if (p_115043_.m_41613_() > 48) {
            $$1 = 5;
        } else if (p_115043_.m_41613_() > 32) {
            $$1 = 4;
        } else if (p_115043_.m_41613_() > 16) {
            $$1 = 3;
        } else if (p_115043_.m_41613_() > 1) {
            $$1 = 2;
        }
        return $$1;
    }

    @Override
    public void m_7392_(ItemEntity p_115036_, float p_115037_, float p_115038_, PoseStack p_115039_, MultiBufferSource p_115040_, int p_115041_) {
        p_115039_.m_85836_();
        ItemStack $$6 = p_115036_.m_32055_();
        int $$7 = $$6.m_41619_() ? 187 : Item.m_41393_($$6.m_41720_()) + $$6.m_41773_();
        this.f_115020_.m_188584_($$7);
        BakedModel $$8 = this.f_115019_.m_174264_($$6, p_115036_.f_19853_, null, p_115036_.m_19879_());
        boolean $$9 = $$8.m_7539_();
        int $$10 = this.m_115042_($$6);
        float $$11 = 0.25f;
        float $$12 = Mth.m_14031_(((float)p_115036_.m_32059_() + p_115038_) / 10.0f + p_115036_.f_31983_) * 0.1f + 0.1f;
        float $$13 = $$8.m_7442_().m_111808_((ItemTransforms.TransformType)ItemTransforms.TransformType.GROUND).f_111757_.m_122260_();
        p_115039_.m_85837_(0.0, $$12 + 0.25f * $$13, 0.0);
        float $$14 = p_115036_.m_32008_(p_115038_);
        p_115039_.m_85845_(Vector3f.f_122225_.m_122270_($$14));
        float $$15 = $$8.m_7442_().f_111793_.f_111757_.m_122239_();
        float $$16 = $$8.m_7442_().f_111793_.f_111757_.m_122260_();
        float $$17 = $$8.m_7442_().f_111793_.f_111757_.m_122269_();
        if (!$$9) {
            float $$18 = -0.0f * (float)($$10 - 1) * 0.5f * $$15;
            float $$19 = -0.0f * (float)($$10 - 1) * 0.5f * $$16;
            float $$20 = -0.09375f * (float)($$10 - 1) * 0.5f * $$17;
            p_115039_.m_85837_($$18, $$19, $$20);
        }
        for (int $$21 = 0; $$21 < $$10; ++$$21) {
            p_115039_.m_85836_();
            if ($$21 > 0) {
                if ($$9) {
                    float $$22 = (this.f_115020_.m_188501_() * 2.0f - 1.0f) * 0.15f;
                    float $$23 = (this.f_115020_.m_188501_() * 2.0f - 1.0f) * 0.15f;
                    float $$24 = (this.f_115020_.m_188501_() * 2.0f - 1.0f) * 0.15f;
                    p_115039_.m_85837_($$22, $$23, $$24);
                } else {
                    float $$25 = (this.f_115020_.m_188501_() * 2.0f - 1.0f) * 0.15f * 0.5f;
                    float $$26 = (this.f_115020_.m_188501_() * 2.0f - 1.0f) * 0.15f * 0.5f;
                    p_115039_.m_85837_($$25, $$26, 0.0);
                }
            }
            this.f_115019_.m_115143_($$6, ItemTransforms.TransformType.GROUND, false, p_115039_, p_115040_, p_115041_, OverlayTexture.f_118083_, $$8);
            p_115039_.m_85849_();
            if ($$9) continue;
            p_115039_.m_85837_(0.0f * $$15, 0.0f * $$16, 0.09375f * $$17);
        }
        p_115039_.m_85849_();
        super.m_7392_(p_115036_, p_115037_, p_115038_, p_115039_, p_115040_, p_115041_);
    }

    @Override
    public ResourceLocation m_5478_(ItemEntity p_115034_) {
        return TextureAtlas.f_118259_;
    }
}

