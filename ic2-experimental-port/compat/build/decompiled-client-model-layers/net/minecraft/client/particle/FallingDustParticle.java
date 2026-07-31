/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class FallingDustParticle
extends TextureSheetParticle {
    private final float f_106607_;
    private final SpriteSet f_106608_;

    FallingDustParticle(ClientLevel p_106610_, double p_106611_, double p_106612_, double p_106613_, float p_106614_, float p_106615_, float p_106616_, SpriteSet p_106617_) {
        super(p_106610_, p_106611_, p_106612_, p_106613_);
        this.f_106608_ = p_106617_;
        this.f_107227_ = p_106614_;
        this.f_107228_ = p_106615_;
        this.f_107229_ = p_106616_;
        float $$8 = 0.9f;
        this.f_107663_ *= 0.67499995f;
        int $$9 = (int)(32.0 / (Math.random() * 0.8 + 0.2));
        this.f_107225_ = (int)Math.max((float)$$9 * 0.9f, 1.0f);
        this.m_108339_(p_106617_);
        this.f_106607_ = ((float)Math.random() - 0.5f) * 0.1f;
        this.f_107231_ = (float)Math.random() * ((float)Math.PI * 2);
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    @Override
    public float m_5902_(float p_106631_) {
        return this.f_107663_ * Mth.m_14036_(((float)this.f_107224_ + p_106631_) / (float)this.f_107225_ * 32.0f, 0.0f, 1.0f);
    }

    @Override
    public void m_5989_() {
        this.f_107209_ = this.f_107212_;
        this.f_107210_ = this.f_107213_;
        this.f_107211_ = this.f_107214_;
        if (this.f_107224_++ >= this.f_107225_) {
            this.m_107274_();
            return;
        }
        this.m_108339_(this.f_106608_);
        this.f_107204_ = this.f_107231_;
        this.f_107231_ += (float)Math.PI * this.f_106607_ * 2.0f;
        if (this.f_107218_) {
            this.f_107231_ = 0.0f;
            this.f_107204_ = 0.0f;
        }
        this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
        this.f_107216_ -= (double)0.003f;
        this.f_107216_ = Math.max(this.f_107216_, (double)-0.14f);
    }

    public static class Provider
    implements ParticleProvider<BlockParticleOption> {
        private final SpriteSet f_106632_;

        public Provider(SpriteSet p_106634_) {
            this.f_106632_ = p_106634_;
        }

        @Override
        @Nullable
        public Particle m_6966_(BlockParticleOption p_106636_, ClientLevel p_106637_, double p_106638_, double p_106639_, double p_106640_, double p_106641_, double p_106642_, double p_106643_) {
            BlockState $$8 = p_106636_.m_123642_();
            if (!$$8.m_60795_() && $$8.m_60799_() == RenderShape.INVISIBLE) {
                return null;
            }
            BlockPos $$9 = new BlockPos(p_106638_, p_106639_, p_106640_);
            int $$10 = Minecraft.m_91087_().m_91298_().m_92582_($$8, p_106637_, $$9);
            if ($$8.m_60734_() instanceof FallingBlock) {
                $$10 = ((FallingBlock)$$8.m_60734_()).m_6248_($$8, p_106637_, $$9);
            }
            float $$11 = (float)($$10 >> 16 & 0xFF) / 255.0f;
            float $$12 = (float)($$10 >> 8 & 0xFF) / 255.0f;
            float $$13 = (float)($$10 & 0xFF) / 255.0f;
            return new FallingDustParticle(p_106637_, p_106638_, p_106639_, p_106640_, $$11, $$12, $$13, this.f_106632_);
        }
    }
}

