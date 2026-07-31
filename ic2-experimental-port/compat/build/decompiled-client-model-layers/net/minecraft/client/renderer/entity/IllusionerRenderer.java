/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.phys.Vec3;

public class IllusionerRenderer
extends IllagerRenderer<Illusioner> {
    private static final ResourceLocation f_114922_ = new ResourceLocation("textures/entity/illager/illusioner.png");

    public IllusionerRenderer(EntityRendererProvider.Context p_174186_) {
        super(p_174186_, new IllagerModel(p_174186_.m_174023_(ModelLayers.f_171191_)), 0.5f);
        this.m_115326_(new ItemInHandLayer<Illusioner, IllagerModel<Illusioner>>((RenderLayerParent)this, p_174186_.m_234598_()){

            @Override
            public void m_6494_(PoseStack p_114989_, MultiBufferSource p_114990_, int p_114991_, Illusioner p_114992_, float p_114993_, float p_114994_, float p_114995_, float p_114996_, float p_114997_, float p_114998_) {
                if (p_114992_.m_33736_() || p_114992_.m_5912_()) {
                    super.m_6494_(p_114989_, p_114990_, p_114991_, p_114992_, p_114993_, p_114994_, p_114995_, p_114996_, p_114997_, p_114998_);
                }
            }
        });
        ((IllagerModel)this.f_115290_).m_102934_().f_104207_ = true;
    }

    @Override
    public ResourceLocation m_5478_(Illusioner p_114950_) {
        return f_114922_;
    }

    @Override
    public void m_7392_(Illusioner p_114952_, float p_114953_, float p_114954_, PoseStack p_114955_, MultiBufferSource p_114956_, int p_114957_) {
        if (p_114952_.m_20145_()) {
            Vec3[] $$6 = p_114952_.m_32939_(p_114954_);
            float $$7 = this.m_6930_(p_114952_, p_114954_);
            for (int $$8 = 0; $$8 < $$6.length; ++$$8) {
                p_114955_.m_85836_();
                p_114955_.m_85837_($$6[$$8].f_82479_ + (double)Mth.m_14089_((float)$$8 + $$7 * 0.5f) * 0.025, $$6[$$8].f_82480_ + (double)Mth.m_14089_((float)$$8 + $$7 * 0.75f) * 0.0125, $$6[$$8].f_82481_ + (double)Mth.m_14089_((float)$$8 + $$7 * 0.7f) * 0.025);
                super.m_7392_(p_114952_, p_114953_, p_114954_, p_114955_, p_114956_, p_114957_);
                p_114955_.m_85849_();
            }
        } else {
            super.m_7392_(p_114952_, p_114953_, p_114954_, p_114955_, p_114956_, p_114957_);
        }
    }

    @Override
    protected boolean m_5933_(Illusioner p_114959_) {
        return true;
    }

    @Override
    protected /* synthetic */ boolean m_5933_(LivingEntity livingEntity) {
        return this.m_5933_((Illusioner)livingEntity);
    }
}

