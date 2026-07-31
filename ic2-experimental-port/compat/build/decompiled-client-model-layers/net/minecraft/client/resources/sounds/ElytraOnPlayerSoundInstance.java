/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.sounds;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;

public class ElytraOnPlayerSoundInstance
extends AbstractTickableSoundInstance {
    public static final int f_174926_ = 20;
    private final LocalPlayer f_119670_;
    private int f_119671_;

    public ElytraOnPlayerSoundInstance(LocalPlayer p_119673_) {
        super(SoundEvents.f_11886_, SoundSource.PLAYERS, SoundInstance.m_235150_());
        this.f_119670_ = p_119673_;
        this.f_119578_ = true;
        this.f_119579_ = 0;
        this.f_119573_ = 0.1f;
    }

    @Override
    public void m_7788_() {
        ++this.f_119671_;
        if (this.f_119670_.m_213877_() || this.f_119671_ > 20 && !this.f_119670_.m_21255_()) {
            this.m_119609_();
            return;
        }
        this.f_119575_ = (float)this.f_119670_.m_20185_();
        this.f_119576_ = (float)this.f_119670_.m_20186_();
        this.f_119577_ = (float)this.f_119670_.m_20189_();
        float $$0 = (float)this.f_119670_.m_20184_().m_82556_();
        this.f_119573_ = (double)$$0 >= 1.0E-7 ? Mth.m_14036_($$0 / 4.0f, 0.0f, 1.0f) : 0.0f;
        if (this.f_119671_ < 20) {
            this.f_119573_ = 0.0f;
        } else if (this.f_119671_ < 40) {
            this.f_119573_ *= (float)(this.f_119671_ - 20) / 20.0f;
        }
        float $$1 = 0.8f;
        this.f_119574_ = this.f_119573_ > 0.8f ? 1.0f + (this.f_119573_ - 0.8f) : 1.0f;
    }
}

