/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TextureSheetParticle;

public abstract class RisingParticle
extends TextureSheetParticle {
    protected RisingParticle(ClientLevel p_107631_, double p_107632_, double p_107633_, double p_107634_, double p_107635_, double p_107636_, double p_107637_) {
        super(p_107631_, p_107632_, p_107633_, p_107634_, p_107635_, p_107636_, p_107637_);
        this.f_172258_ = 0.96f;
        this.f_107215_ = this.f_107215_ * (double)0.01f + p_107635_;
        this.f_107216_ = this.f_107216_ * (double)0.01f + p_107636_;
        this.f_107217_ = this.f_107217_ * (double)0.01f + p_107637_;
        this.f_107212_ += (double)((this.f_107223_.m_188501_() - this.f_107223_.m_188501_()) * 0.05f);
        this.f_107213_ += (double)((this.f_107223_.m_188501_() - this.f_107223_.m_188501_()) * 0.05f);
        this.f_107214_ += (double)((this.f_107223_.m_188501_() - this.f_107223_.m_188501_()) * 0.05f);
        this.f_107225_ = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
    }
}

