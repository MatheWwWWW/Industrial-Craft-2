/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.GuardianModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ElderGuardianRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class MobAppearanceParticle
extends Particle {
    private final Model f_107111_;
    private final RenderType f_107112_ = RenderType.m_110473_(ElderGuardianRenderer.f_114116_);

    MobAppearanceParticle(ClientLevel p_107114_, double p_107115_, double p_107116_, double p_107117_) {
        super(p_107114_, p_107115_, p_107116_, p_107117_);
        this.f_107111_ = new GuardianModel(Minecraft.m_91087_().m_167973_().m_171103_(ModelLayers.f_171140_));
        this.f_107226_ = 0.0f;
        this.f_107225_ = 30;
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107433_;
    }

    @Override
    public void m_5744_(VertexConsumer p_107125_, Camera p_107126_, float p_107127_) {
        float $$3 = ((float)this.f_107224_ + p_107127_) / (float)this.f_107225_;
        float $$4 = 0.05f + 0.5f * Mth.m_14031_($$3 * (float)Math.PI);
        PoseStack $$5 = new PoseStack();
        $$5.m_85845_(p_107126_.m_90591_());
        $$5.m_85845_(Vector3f.f_122223_.m_122240_(150.0f * $$3 - 60.0f));
        $$5.m_85841_(-1.0f, -1.0f, 1.0f);
        $$5.m_85837_(0.0, -1.101f, 1.5);
        MultiBufferSource.BufferSource $$6 = Minecraft.m_91087_().m_91269_().m_110104_();
        VertexConsumer $$7 = $$6.m_6299_(this.f_107112_);
        this.f_107111_.m_7695_($$5, $$7, 0xF000F0, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, $$4);
        $$6.m_109911_();
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle m_6966_(SimpleParticleType p_107140_, ClientLevel p_107141_, double p_107142_, double p_107143_, double p_107144_, double p_107145_, double p_107146_, double p_107147_) {
            return new MobAppearanceParticle(p_107141_, p_107142_, p_107143_, p_107144_);
        }
    }
}

