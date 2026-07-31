/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.monster.Guardian;

public class GuardianAttackSoundInstance
extends AbstractTickableSoundInstance {
    private static final float f_174927_ = 0.0f;
    private static final float f_174928_ = 1.0f;
    private static final float f_174929_ = 0.7f;
    private static final float f_174930_ = 0.5f;
    private final Guardian f_119688_;

    public GuardianAttackSoundInstance(Guardian p_119690_) {
        super(SoundEvents.f_12001_, SoundSource.HOSTILE, SoundInstance.m_235150_());
        this.f_119688_ = p_119690_;
        this.f_119580_ = SoundInstance.Attenuation.NONE;
        this.f_119578_ = true;
        this.f_119579_ = 0;
    }

    @Override
    public boolean m_7767_() {
        return !this.f_119688_.m_20067_();
    }

    @Override
    public void m_7788_() {
        if (this.f_119688_.m_213877_() || this.f_119688_.m_5448_() != null) {
            this.m_119609_();
            return;
        }
        this.f_119575_ = (float)this.f_119688_.m_20185_();
        this.f_119576_ = (float)this.f_119688_.m_20186_();
        this.f_119577_ = (float)this.f_119688_.m_20189_();
        float $$0 = this.f_119688_.m_32812_(0.0f);
        this.f_119573_ = 0.0f + 1.0f * $$0 * $$0;
        this.f_119574_ = 0.7f + 0.5f * $$0;
    }
}

