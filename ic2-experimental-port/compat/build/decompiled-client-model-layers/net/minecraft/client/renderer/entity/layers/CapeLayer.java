/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CapeLayer
extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public CapeLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> p_116602_) {
        super(p_116602_);
    }

    @Override
    public void m_6494_(PoseStack p_116615_, MultiBufferSource p_116616_, int p_116617_, AbstractClientPlayer p_116618_, float p_116619_, float p_116620_, float p_116621_, float p_116622_, float p_116623_, float p_116624_) {
        if (!p_116618_.m_108555_() || p_116618_.m_20145_() || !p_116618_.m_36170_(PlayerModelPart.CAPE) || p_116618_.m_108561_() == null) {
            return;
        }
        ItemStack $$10 = p_116618_.m_6844_(EquipmentSlot.CHEST);
        if ($$10.m_150930_(Items.f_42741_)) {
            return;
        }
        p_116615_.m_85836_();
        p_116615_.m_85837_(0.0, 0.0, 0.125);
        double $$11 = Mth.m_14139_(p_116621_, p_116618_.f_36102_, p_116618_.f_36105_) - Mth.m_14139_(p_116621_, p_116618_.f_19854_, p_116618_.m_20185_());
        double $$12 = Mth.m_14139_(p_116621_, p_116618_.f_36103_, p_116618_.f_36106_) - Mth.m_14139_(p_116621_, p_116618_.f_19855_, p_116618_.m_20186_());
        double $$13 = Mth.m_14139_(p_116621_, p_116618_.f_36104_, p_116618_.f_36075_) - Mth.m_14139_(p_116621_, p_116618_.f_19856_, p_116618_.m_20189_());
        float $$14 = p_116618_.f_20884_ + (p_116618_.f_20883_ - p_116618_.f_20884_);
        double $$15 = Mth.m_14031_($$14 * ((float)Math.PI / 180));
        double $$16 = -Mth.m_14089_($$14 * ((float)Math.PI / 180));
        float $$17 = (float)$$12 * 10.0f;
        $$17 = Mth.m_14036_($$17, -6.0f, 32.0f);
        float $$18 = (float)($$11 * $$15 + $$13 * $$16) * 100.0f;
        $$18 = Mth.m_14036_($$18, 0.0f, 150.0f);
        float $$19 = (float)($$11 * $$16 - $$13 * $$15) * 100.0f;
        $$19 = Mth.m_14036_($$19, -20.0f, 20.0f);
        if ($$18 < 0.0f) {
            $$18 = 0.0f;
        }
        float $$20 = Mth.m_14179_(p_116621_, p_116618_.f_36099_, p_116618_.f_36100_);
        $$17 += Mth.m_14031_(Mth.m_14179_(p_116621_, p_116618_.f_19867_, p_116618_.f_19787_) * 6.0f) * 32.0f * $$20;
        if (p_116618_.m_6047_()) {
            $$17 += 25.0f;
        }
        p_116615_.m_85845_(Vector3f.f_122223_.m_122240_(6.0f + $$18 / 2.0f + $$17));
        p_116615_.m_85845_(Vector3f.f_122227_.m_122240_($$19 / 2.0f));
        p_116615_.m_85845_(Vector3f.f_122225_.m_122240_(180.0f - $$19 / 2.0f));
        VertexConsumer $$21 = p_116616_.m_6299_(RenderType.m_110446_(p_116618_.m_108561_()));
        ((PlayerModel)this.m_117386_()).m_103411_(p_116615_, $$21, p_116617_, OverlayTexture.f_118083_);
        p_116615_.m_85849_();
    }
}

