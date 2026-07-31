/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.model.PandaModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.PandaHoldsItemLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Panda;

public class PandaRenderer
extends MobRenderer<Panda, PandaModel<Panda>> {
    private static final Map<Panda.Gene, ResourceLocation> f_115620_ = Util.m_137469_(Maps.newEnumMap(Panda.Gene.class), p_115647_ -> {
        p_115647_.put(Panda.Gene.NORMAL, new ResourceLocation("textures/entity/panda/panda.png"));
        p_115647_.put(Panda.Gene.LAZY, new ResourceLocation("textures/entity/panda/lazy_panda.png"));
        p_115647_.put(Panda.Gene.WORRIED, new ResourceLocation("textures/entity/panda/worried_panda.png"));
        p_115647_.put(Panda.Gene.PLAYFUL, new ResourceLocation("textures/entity/panda/playful_panda.png"));
        p_115647_.put(Panda.Gene.BROWN, new ResourceLocation("textures/entity/panda/brown_panda.png"));
        p_115647_.put(Panda.Gene.WEAK, new ResourceLocation("textures/entity/panda/weak_panda.png"));
        p_115647_.put(Panda.Gene.AGGRESSIVE, new ResourceLocation("textures/entity/panda/aggressive_panda.png"));
    });

    public PandaRenderer(EntityRendererProvider.Context p_174334_) {
        super(p_174334_, new PandaModel(p_174334_.m_174023_(ModelLayers.f_171202_)), 0.9f);
        this.m_115326_(new PandaHoldsItemLayer(this, p_174334_.m_234598_()));
    }

    @Override
    public ResourceLocation m_5478_(Panda p_115639_) {
        return f_115620_.getOrDefault((Object)p_115639_.m_29158_(), f_115620_.get((Object)Panda.Gene.NORMAL));
    }

    @Override
    protected void m_7523_(Panda p_115641_, PoseStack p_115642_, float p_115643_, float p_115644_, float p_115645_) {
        float $$26;
        float $$24;
        super.m_7523_(p_115641_, p_115642_, p_115643_, p_115644_, p_115645_);
        if (p_115641_.f_29072_ > 0) {
            float $$8;
            int $$5 = p_115641_.f_29072_;
            int $$6 = $$5 + 1;
            float $$7 = 7.0f;
            float f = $$8 = p_115641_.m_6162_() ? 0.3f : 0.8f;
            if ($$5 < 8) {
                float $$9 = (float)(90 * $$5) / 7.0f;
                float $$10 = (float)(90 * $$6) / 7.0f;
                float $$11 = this.m_115624_($$9, $$10, $$6, p_115645_, 8.0f);
                p_115642_.m_85837_(0.0, ($$8 + 0.2f) * ($$11 / 90.0f), 0.0);
                p_115642_.m_85845_(Vector3f.f_122223_.m_122240_(-$$11));
            } else if ($$5 < 16) {
                float $$12 = ((float)$$5 - 8.0f) / 7.0f;
                float $$13 = 90.0f + 90.0f * $$12;
                float $$14 = 90.0f + 90.0f * ((float)$$6 - 8.0f) / 7.0f;
                float $$15 = this.m_115624_($$13, $$14, $$6, p_115645_, 16.0f);
                p_115642_.m_85837_(0.0, $$8 + 0.2f + ($$8 - 0.2f) * ($$15 - 90.0f) / 90.0f, 0.0);
                p_115642_.m_85845_(Vector3f.f_122223_.m_122240_(-$$15));
            } else if ((float)$$5 < 24.0f) {
                float $$16 = ((float)$$5 - 16.0f) / 7.0f;
                float $$17 = 180.0f + 90.0f * $$16;
                float $$18 = 180.0f + 90.0f * ((float)$$6 - 16.0f) / 7.0f;
                float $$19 = this.m_115624_($$17, $$18, $$6, p_115645_, 24.0f);
                p_115642_.m_85837_(0.0, $$8 + $$8 * (270.0f - $$19) / 90.0f, 0.0);
                p_115642_.m_85845_(Vector3f.f_122223_.m_122240_(-$$19));
            } else if ($$5 < 32) {
                float $$20 = ((float)$$5 - 24.0f) / 7.0f;
                float $$21 = 270.0f + 90.0f * $$20;
                float $$22 = 270.0f + 90.0f * ((float)$$6 - 24.0f) / 7.0f;
                float $$23 = this.m_115624_($$21, $$22, $$6, p_115645_, 32.0f);
                p_115642_.m_85837_(0.0, $$8 * ((360.0f - $$23) / 90.0f), 0.0);
                p_115642_.m_85845_(Vector3f.f_122223_.m_122240_(-$$23));
            }
        }
        if (($$24 = p_115641_.m_29224_(p_115645_)) > 0.0f) {
            p_115642_.m_85837_(0.0, 0.8f * $$24, 0.0);
            p_115642_.m_85845_(Vector3f.f_122223_.m_122240_(Mth.m_14179_($$24, p_115641_.m_146909_(), p_115641_.m_146909_() + 90.0f)));
            p_115642_.m_85837_(0.0, -1.0f * $$24, 0.0);
            if (p_115641_.m_29165_()) {
                float $$25 = (float)(Math.cos((double)p_115641_.f_19797_ * 1.25) * Math.PI * (double)0.05f);
                p_115642_.m_85845_(Vector3f.f_122225_.m_122240_($$25));
                if (p_115641_.m_6162_()) {
                    p_115642_.m_85837_(0.0, 0.8f, 0.55f);
                }
            }
        }
        if (($$26 = p_115641_.m_29226_(p_115645_)) > 0.0f) {
            float $$27 = p_115641_.m_6162_() ? 0.5f : 1.3f;
            p_115642_.m_85837_(0.0, $$27 * $$26, 0.0);
            p_115642_.m_85845_(Vector3f.f_122223_.m_122240_(Mth.m_14179_($$26, p_115641_.m_146909_(), p_115641_.m_146909_() + 180.0f)));
        }
    }

    private float m_115624_(float p_115625_, float p_115626_, int p_115627_, float p_115628_, float p_115629_) {
        if ((float)p_115627_ < p_115629_) {
            return Mth.m_14179_(p_115628_, p_115625_, p_115626_);
        }
        return p_115625_;
    }
}

