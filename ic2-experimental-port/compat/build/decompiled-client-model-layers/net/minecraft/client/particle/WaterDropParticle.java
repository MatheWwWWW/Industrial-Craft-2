/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;

public class WaterDropParticle
extends TextureSheetParticle {
    protected WaterDropParticle(ClientLevel p_108484_, double p_108485_, double p_108486_, double p_108487_) {
        super(p_108484_, p_108485_, p_108486_, p_108487_, 0.0, 0.0, 0.0);
        this.f_107215_ *= (double)0.3f;
        this.f_107216_ = Math.random() * (double)0.2f + (double)0.1f;
        this.f_107217_ *= (double)0.3f;
        this.m_107250_(0.01f, 0.01f);
        this.f_107226_ = 0.06f;
        this.f_107225_ = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    @Override
    public void m_5989_() {
        BlockPos $$0;
        double $$1;
        this.f_107209_ = this.f_107212_;
        this.f_107210_ = this.f_107213_;
        this.f_107211_ = this.f_107214_;
        if (this.f_107225_-- <= 0) {
            this.m_107274_();
            return;
        }
        this.f_107216_ -= (double)this.f_107226_;
        this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
        this.f_107215_ *= (double)0.98f;
        this.f_107216_ *= (double)0.98f;
        this.f_107217_ *= (double)0.98f;
        if (this.f_107218_) {
            if (Math.random() < 0.5) {
                this.m_107274_();
            }
            this.f_107215_ *= (double)0.7f;
            this.f_107217_ *= (double)0.7f;
        }
        if (($$1 = Math.max(this.f_107208_.m_8055_($$0 = new BlockPos(this.f_107212_, this.f_107213_, this.f_107214_)).m_60812_(this.f_107208_, $$0).m_83290_(Direction.Axis.Y, this.f_107212_ - (double)$$0.m_123341_(), this.f_107214_ - (double)$$0.m_123343_()), (double)this.f_107208_.m_6425_($$0).m_76155_(this.f_107208_, $$0))) > 0.0 && this.f_107213_ < (double)$$0.m_123342_() + $$1) {
            this.m_107274_();
        }
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_108490_;

        public Provider(SpriteSet p_108492_) {
            this.f_108490_ = p_108492_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_108503_, ClientLevel p_108504_, double p_108505_, double p_108506_, double p_108507_, double p_108508_, double p_108509_, double p_108510_) {
            WaterDropParticle $$8 = new WaterDropParticle(p_108504_, p_108505_, p_108506_, p_108507_);
            $$8.m_108335_(this.f_108490_);
            return $$8;
        }
    }
}

