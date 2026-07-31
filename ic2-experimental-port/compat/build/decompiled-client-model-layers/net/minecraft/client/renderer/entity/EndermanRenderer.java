/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CarriedBlockLayer;
import net.minecraft.client.renderer.entity.layers.EnderEyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EndermanRenderer
extends MobRenderer<EnderMan, EndermanModel<EnderMan>> {
    private static final ResourceLocation f_114302_ = new ResourceLocation("textures/entity/enderman/enderman.png");
    private final RandomSource f_114303_ = RandomSource.m_216327_();

    public EndermanRenderer(EntityRendererProvider.Context p_173992_) {
        super(p_173992_, new EndermanModel(p_173992_.m_174023_(ModelLayers.f_171142_)), 0.5f);
        this.m_115326_(new EnderEyesLayer<EnderMan>(this));
        this.m_115326_(new CarriedBlockLayer(this, p_173992_.m_234597_()));
    }

    @Override
    public void m_7392_(EnderMan p_114339_, float p_114340_, float p_114341_, PoseStack p_114342_, MultiBufferSource p_114343_, int p_114344_) {
        BlockState $$6 = p_114339_.m_32530_();
        EndermanModel $$7 = (EndermanModel)this.m_7200_();
        $$7.f_102576_ = $$6 != null;
        $$7.f_102577_ = p_114339_.m_32531_();
        super.m_7392_(p_114339_, p_114340_, p_114341_, p_114342_, p_114343_, p_114344_);
    }

    @Override
    public Vec3 m_7860_(EnderMan p_114336_, float p_114337_) {
        if (p_114336_.m_32531_()) {
            double $$2 = 0.02;
            return new Vec3(this.f_114303_.m_188583_() * 0.02, 0.0, this.f_114303_.m_188583_() * 0.02);
        }
        return super.m_7860_(p_114336_, p_114337_);
    }

    @Override
    public ResourceLocation m_5478_(EnderMan p_114334_) {
        return f_114302_;
    }
}

