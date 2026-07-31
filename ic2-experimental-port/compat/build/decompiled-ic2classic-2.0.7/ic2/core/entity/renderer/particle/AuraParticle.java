/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.ParticleRenderType
 *  net.minecraft.client.particle.SingleQuadParticle
 *  net.minecraft.client.renderer.texture.TextureAtlas
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.entity.renderer.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public class AuraParticle
extends SingleQuadParticle {
    public static final ResourceLocation LOCATION_PARTICLES = new ResourceLocation("textures/atlas/particles.png");
    TextureAtlasSprite sprite;

    public AuraParticle(ClientLevel world, double x, double y, double z, int maxAge, double[] velocity, float[] colour) {
        super(world, x, y, z, velocity[0], velocity[1], velocity[2]);
        this.f_107227_ = colour[0];
        this.f_107228_ = colour[1];
        this.f_107229_ = colour[2];
        this.m_107250_(0.02f, 0.02f);
        this.f_107663_ *= this.f_107223_.m_188501_() * 0.5f + 0.5f;
        this.f_107216_ *= 0.2;
        this.f_107225_ = (int)((double)maxAge / (Math.random() * 0.8 + 0.2));
        this.sprite = ((TextureAtlas)Minecraft.m_91087_().m_91097_().m_118506_(LOCATION_PARTICLES)).m_118316_(new ResourceLocation("minecraft:particle/generic_2"));
    }

    public void m_5989_() {
        this.f_107209_ = this.f_107212_;
        this.f_107210_ = this.f_107213_;
        this.f_107211_ = this.f_107214_;
        this.f_107212_ += this.f_107215_;
        this.f_107213_ += this.f_107216_;
        this.f_107214_ += this.f_107217_;
        this.f_107215_ *= 0.99;
        this.f_107216_ *= 0.99;
        this.f_107217_ *= 0.99;
        if (this.f_107224_++ >= this.f_107225_) {
            this.m_107274_();
        }
    }

    protected float m_5970_() {
        return this.sprite.m_118409_();
    }

    protected float m_5952_() {
        return this.sprite.m_118410_();
    }

    protected float m_5951_() {
        return this.sprite.m_118411_();
    }

    protected float m_5950_() {
        return this.sprite.m_118412_();
    }

    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107431_;
    }
}

