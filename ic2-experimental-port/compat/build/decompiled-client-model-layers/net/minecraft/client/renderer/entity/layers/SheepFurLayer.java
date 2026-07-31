/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

public class SheepFurLayer
extends RenderLayer<Sheep, SheepModel<Sheep>> {
    private static final ResourceLocation f_117404_ = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
    private final SheepFurModel<Sheep> f_117405_;

    public SheepFurLayer(RenderLayerParent<Sheep, SheepModel<Sheep>> p_174533_, EntityModelSet p_174534_) {
        super(p_174533_);
        this.f_117405_ = new SheepFurModel(p_174534_.m_171103_(ModelLayers.f_171178_));
    }

    @Override
    public void m_6494_(PoseStack p_117421_, MultiBufferSource p_117422_, int p_117423_, Sheep p_117424_, float p_117425_, float p_117426_, float p_117427_, float p_117428_, float p_117429_, float p_117430_) {
        float $$27;
        float $$26;
        float $$25;
        if (p_117424_.m_29875_()) {
            return;
        }
        if (p_117424_.m_20145_()) {
            Minecraft $$10 = Minecraft.m_91087_();
            boolean $$11 = $$10.m_91314_(p_117424_);
            if ($$11) {
                ((SheepModel)this.m_117386_()).m_102624_(this.f_117405_);
                this.f_117405_.m_6839_(p_117424_, p_117425_, p_117426_, p_117427_);
                this.f_117405_.m_6973_(p_117424_, p_117425_, p_117426_, p_117428_, p_117429_, p_117430_);
                VertexConsumer $$12 = p_117422_.m_6299_(RenderType.m_110491_(f_117404_));
                this.f_117405_.m_7695_(p_117421_, $$12, p_117423_, LivingEntityRenderer.m_115338_(p_117424_, 0.0f), 0.0f, 0.0f, 0.0f, 1.0f);
            }
            return;
        }
        if (p_117424_.m_8077_() && "jeb_".equals(p_117424_.m_7755_().getString())) {
            int $$13 = 25;
            int $$14 = p_117424_.f_19797_ / 25 + p_117424_.m_19879_();
            int $$15 = DyeColor.values().length;
            int $$16 = $$14 % $$15;
            int $$17 = ($$14 + 1) % $$15;
            float $$18 = ((float)(p_117424_.f_19797_ % 25) + p_117427_) / 25.0f;
            float[] $$19 = Sheep.m_29829_(DyeColor.m_41053_($$16));
            float[] $$20 = Sheep.m_29829_(DyeColor.m_41053_($$17));
            float $$21 = $$19[0] * (1.0f - $$18) + $$20[0] * $$18;
            float $$22 = $$19[1] * (1.0f - $$18) + $$20[1] * $$18;
            float $$23 = $$19[2] * (1.0f - $$18) + $$20[2] * $$18;
        } else {
            float[] $$24 = Sheep.m_29829_(p_117424_.m_29874_());
            $$25 = $$24[0];
            $$26 = $$24[1];
            $$27 = $$24[2];
        }
        SheepFurLayer.m_117359_(this.m_117386_(), this.f_117405_, f_117404_, p_117421_, p_117422_, p_117423_, p_117424_, p_117425_, p_117426_, p_117428_, p_117429_, p_117430_, p_117427_, $$25, $$26, $$27);
    }
}

