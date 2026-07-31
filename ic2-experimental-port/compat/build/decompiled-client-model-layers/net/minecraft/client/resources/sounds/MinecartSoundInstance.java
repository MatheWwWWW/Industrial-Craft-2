/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

public class MinecartSoundInstance
extends AbstractTickableSoundInstance {
    private static final float f_174931_ = 0.0f;
    private static final float f_174932_ = 0.7f;
    private static final float f_174933_ = 0.0f;
    private static final float f_174934_ = 1.0f;
    private static final float f_174935_ = 0.0025f;
    private final AbstractMinecart f_119693_;
    private float f_119694_ = 0.0f;

    public MinecartSoundInstance(AbstractMinecart p_119696_) {
        super(SoundEvents.f_12070_, SoundSource.NEUTRAL, SoundInstance.m_235150_());
        this.f_119693_ = p_119696_;
        this.f_119578_ = true;
        this.f_119579_ = 0;
        this.f_119573_ = 0.0f;
        this.f_119575_ = (float)p_119696_.m_20185_();
        this.f_119576_ = (float)p_119696_.m_20186_();
        this.f_119577_ = (float)p_119696_.m_20189_();
    }

    @Override
    public boolean m_7767_() {
        return !this.f_119693_.m_20067_();
    }

    @Override
    public boolean m_7784_() {
        return true;
    }

    @Override
    public void m_7788_() {
        if (this.f_119693_.m_213877_()) {
            this.m_119609_();
            return;
        }
        this.f_119575_ = (float)this.f_119693_.m_20185_();
        this.f_119576_ = (float)this.f_119693_.m_20186_();
        this.f_119577_ = (float)this.f_119693_.m_20189_();
        float $$0 = (float)this.f_119693_.m_20184_().m_165924_();
        if ($$0 >= 0.01f) {
            this.f_119694_ = Mth.m_14036_(this.f_119694_ + 0.0025f, 0.0f, 1.0f);
            this.f_119573_ = Mth.m_14179_(Mth.m_14036_($$0, 0.0f, 0.5f), 0.0f, 0.7f);
        } else {
            this.f_119694_ = 0.0f;
            this.f_119573_ = 0.0f;
        }
    }
}

