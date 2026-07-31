/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public abstract class StuckInBodyLayer<T extends LivingEntity, M extends PlayerModel<T>>
extends RenderLayer<T, M> {
    public StuckInBodyLayer(LivingEntityRenderer<T, M> p_117564_) {
        super(p_117564_);
    }

    protected abstract int m_7040_(T var1);

    protected abstract void m_5558_(PoseStack var1, MultiBufferSource var2, int var3, Entity var4, float var5, float var6, float var7, float var8);

    @Override
    public void m_6494_(PoseStack p_117586_, MultiBufferSource p_117587_, int p_117588_, T p_117589_, float p_117590_, float p_117591_, float p_117592_, float p_117593_, float p_117594_, float p_117595_) {
        int $$10 = this.m_7040_(p_117589_);
        RandomSource $$11 = RandomSource.m_216335_(((Entity)p_117589_).m_19879_());
        if ($$10 <= 0) {
            return;
        }
        for (int $$12 = 0; $$12 < $$10; ++$$12) {
            p_117586_.m_85836_();
            ModelPart $$13 = ((PlayerModel)this.m_117386_()).m_233438_($$11);
            ModelPart.Cube $$14 = $$13.m_233558_($$11);
            $$13.m_104299_(p_117586_);
            float $$15 = $$11.m_188501_();
            float $$16 = $$11.m_188501_();
            float $$17 = $$11.m_188501_();
            float $$18 = Mth.m_14179_($$15, $$14.f_104335_, $$14.f_104338_) / 16.0f;
            float $$19 = Mth.m_14179_($$16, $$14.f_104336_, $$14.f_104339_) / 16.0f;
            float $$20 = Mth.m_14179_($$17, $$14.f_104337_, $$14.f_104340_) / 16.0f;
            p_117586_.m_85837_($$18, $$19, $$20);
            $$15 = -1.0f * ($$15 * 2.0f - 1.0f);
            $$16 = -1.0f * ($$16 * 2.0f - 1.0f);
            $$17 = -1.0f * ($$17 * 2.0f - 1.0f);
            this.m_5558_(p_117586_, p_117587_, p_117588_, (Entity)p_117589_, $$15, $$16, $$17, p_117592_);
            p_117586_.m_85849_();
        }
    }
}

