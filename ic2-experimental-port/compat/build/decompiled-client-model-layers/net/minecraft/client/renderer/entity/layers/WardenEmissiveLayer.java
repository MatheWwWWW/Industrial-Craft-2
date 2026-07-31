/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;

public class WardenEmissiveLayer<T extends Warden, M extends WardenModel<T>>
extends RenderLayer<T, M> {
    private final ResourceLocation f_234881_;
    private final AlphaFunction<T> f_234882_;
    private final DrawSelector<T, M> f_234883_;

    public WardenEmissiveLayer(RenderLayerParent<T, M> p_234885_, ResourceLocation p_234886_, AlphaFunction<T> p_234887_, DrawSelector<T, M> p_234888_) {
        super(p_234885_);
        this.f_234881_ = p_234886_;
        this.f_234882_ = p_234887_;
        this.f_234883_ = p_234888_;
    }

    @Override
    public void m_6494_(PoseStack p_234902_, MultiBufferSource p_234903_, int p_234904_, T p_234905_, float p_234906_, float p_234907_, float p_234908_, float p_234909_, float p_234910_, float p_234911_) {
        if (((Entity)p_234905_).m_20145_()) {
            return;
        }
        this.m_234889_();
        VertexConsumer $$10 = p_234903_.m_6299_(RenderType.m_234338_(this.f_234881_));
        ((WardenModel)this.m_117386_()).m_7695_(p_234902_, $$10, p_234904_, LivingEntityRenderer.m_115338_(p_234905_, 0.0f), 1.0f, 1.0f, 1.0f, this.f_234882_.m_234919_(p_234905_, p_234908_, p_234909_));
        this.m_234914_();
    }

    private void m_234889_() {
        List<ModelPart> $$0 = this.f_234883_.m_234923_((WardenModel)this.m_117386_());
        ((WardenModel)this.m_117386_()).m_142109_().m_171331_().forEach(p_234918_ -> {
            p_234918_.f_233556_ = true;
        });
        $$0.forEach(p_234916_ -> {
            p_234916_.f_233556_ = false;
        });
    }

    private void m_234914_() {
        ((WardenModel)this.m_117386_()).m_142109_().m_171331_().forEach(p_234913_ -> {
            p_234913_.f_233556_ = false;
        });
    }

    public static interface AlphaFunction<T extends Warden> {
        public float m_234919_(T var1, float var2, float var3);
    }

    public static interface DrawSelector<T extends Warden, M extends EntityModel<T>> {
        public List<ModelPart> m_234923_(M var1);
    }
}

