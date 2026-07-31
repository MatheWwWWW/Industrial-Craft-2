/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LightningBolt;

public class LightningBoltRenderer
extends EntityRenderer<LightningBolt> {
    public LightningBoltRenderer(EntityRendererProvider.Context p_174286_) {
        super(p_174286_);
    }

    @Override
    public void m_7392_(LightningBolt p_115266_, float p_115267_, float p_115268_, PoseStack p_115269_, MultiBufferSource p_115270_, int p_115271_) {
        float[] $$6 = new float[8];
        float[] $$7 = new float[8];
        float $$8 = 0.0f;
        float $$9 = 0.0f;
        RandomSource $$10 = RandomSource.m_216335_(p_115266_.f_20859_);
        for (int $$11 = 7; $$11 >= 0; --$$11) {
            $$6[$$11] = $$8;
            $$7[$$11] = $$9;
            $$8 += (float)($$10.m_188503_(11) - 5);
            $$9 += (float)($$10.m_188503_(11) - 5);
        }
        VertexConsumer $$12 = p_115270_.m_6299_(RenderType.m_110502_());
        Matrix4f $$13 = p_115269_.m_85850_().m_85861_();
        for (int $$14 = 0; $$14 < 4; ++$$14) {
            RandomSource $$15 = RandomSource.m_216335_(p_115266_.f_20859_);
            for (int $$16 = 0; $$16 < 3; ++$$16) {
                int $$17 = 7;
                int $$18 = 0;
                if ($$16 > 0) {
                    $$17 = 7 - $$16;
                }
                if ($$16 > 0) {
                    $$18 = $$17 - 2;
                }
                float $$19 = $$6[$$17] - $$8;
                float $$20 = $$7[$$17] - $$9;
                for (int $$21 = $$17; $$21 >= $$18; --$$21) {
                    float $$22 = $$19;
                    float $$23 = $$20;
                    if ($$16 == 0) {
                        $$19 += (float)($$15.m_188503_(11) - 5);
                        $$20 += (float)($$15.m_188503_(11) - 5);
                    } else {
                        $$19 += (float)($$15.m_188503_(31) - 15);
                        $$20 += (float)($$15.m_188503_(31) - 15);
                    }
                    float $$24 = 0.5f;
                    float $$25 = 0.45f;
                    float $$26 = 0.45f;
                    float $$27 = 0.5f;
                    float $$28 = 0.1f + (float)$$14 * 0.2f;
                    if ($$16 == 0) {
                        $$28 *= (float)$$21 * 0.1f + 1.0f;
                    }
                    float $$29 = 0.1f + (float)$$14 * 0.2f;
                    if ($$16 == 0) {
                        $$29 *= ((float)$$21 - 1.0f) * 0.1f + 1.0f;
                    }
                    LightningBoltRenderer.m_115272_($$13, $$12, $$19, $$20, $$21, $$22, $$23, 0.45f, 0.45f, 0.5f, $$28, $$29, false, false, true, false);
                    LightningBoltRenderer.m_115272_($$13, $$12, $$19, $$20, $$21, $$22, $$23, 0.45f, 0.45f, 0.5f, $$28, $$29, true, false, true, true);
                    LightningBoltRenderer.m_115272_($$13, $$12, $$19, $$20, $$21, $$22, $$23, 0.45f, 0.45f, 0.5f, $$28, $$29, true, true, false, true);
                    LightningBoltRenderer.m_115272_($$13, $$12, $$19, $$20, $$21, $$22, $$23, 0.45f, 0.45f, 0.5f, $$28, $$29, false, true, false, false);
                }
            }
        }
    }

    private static void m_115272_(Matrix4f p_115273_, VertexConsumer p_115274_, float p_115275_, float p_115276_, int p_115277_, float p_115278_, float p_115279_, float p_115280_, float p_115281_, float p_115282_, float p_115283_, float p_115284_, boolean p_115285_, boolean p_115286_, boolean p_115287_, boolean p_115288_) {
        p_115274_.m_85982_(p_115273_, p_115275_ + (p_115285_ ? p_115284_ : -p_115284_), p_115277_ * 16, p_115276_ + (p_115286_ ? p_115284_ : -p_115284_)).m_85950_(p_115280_, p_115281_, p_115282_, 0.3f).m_5752_();
        p_115274_.m_85982_(p_115273_, p_115278_ + (p_115285_ ? p_115283_ : -p_115283_), (p_115277_ + 1) * 16, p_115279_ + (p_115286_ ? p_115283_ : -p_115283_)).m_85950_(p_115280_, p_115281_, p_115282_, 0.3f).m_5752_();
        p_115274_.m_85982_(p_115273_, p_115278_ + (p_115287_ ? p_115283_ : -p_115283_), (p_115277_ + 1) * 16, p_115279_ + (p_115288_ ? p_115283_ : -p_115283_)).m_85950_(p_115280_, p_115281_, p_115282_, 0.3f).m_5752_();
        p_115274_.m_85982_(p_115273_, p_115275_ + (p_115287_ ? p_115284_ : -p_115284_), p_115277_ * 16, p_115276_ + (p_115288_ ? p_115284_ : -p_115284_)).m_85950_(p_115280_, p_115281_, p_115282_, 0.3f).m_5752_();
    }

    @Override
    public ResourceLocation m_5478_(LightningBolt p_115264_) {
        return TextureAtlas.f_118259_;
    }
}

