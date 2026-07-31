/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public class EntityBoundSoundInstance
extends AbstractTickableSoundInstance {
    private final Entity f_119675_;

    public EntityBoundSoundInstance(SoundEvent p_235080_, SoundSource p_235081_, float p_235082_, float p_235083_, Entity p_235084_, long p_235085_) {
        super(p_235080_, p_235081_, RandomSource.m_216335_(p_235085_));
        this.f_119573_ = p_235082_;
        this.f_119574_ = p_235083_;
        this.f_119675_ = p_235084_;
        this.f_119575_ = (float)this.f_119675_.m_20185_();
        this.f_119576_ = (float)this.f_119675_.m_20186_();
        this.f_119577_ = (float)this.f_119675_.m_20189_();
    }

    @Override
    public boolean m_7767_() {
        return !this.f_119675_.m_20067_();
    }

    @Override
    public void m_7788_() {
        if (this.f_119675_.m_213877_()) {
            this.m_119609_();
            return;
        }
        this.f_119575_ = (float)this.f_119675_.m_20185_();
        this.f_119576_ = (float)this.f_119675_.m_20186_();
        this.f_119577_ = (float)this.f_119675_.m_20189_();
    }
}

