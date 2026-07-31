/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.sounds;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class UnderwaterAmbientSoundInstances {

    public static class UnderwaterAmbientSoundInstance
    extends AbstractTickableSoundInstance {
        public static final int f_174962_ = 40;
        private final LocalPlayer f_119864_;
        private int f_119865_;

        public UnderwaterAmbientSoundInstance(LocalPlayer p_119867_) {
            super(SoundEvents.f_12643_, SoundSource.AMBIENT, SoundInstance.m_235150_());
            this.f_119864_ = p_119867_;
            this.f_119578_ = true;
            this.f_119579_ = 0;
            this.f_119573_ = 1.0f;
            this.f_119582_ = true;
        }

        @Override
        public void m_7788_() {
            if (this.f_119864_.m_213877_() || this.f_119865_ < 0) {
                this.m_119609_();
                return;
            }
            this.f_119865_ = this.f_119864_.m_5842_() ? ++this.f_119865_ : (this.f_119865_ -= 2);
            this.f_119865_ = Math.min(this.f_119865_, 40);
            this.f_119573_ = Math.max(0.0f, Math.min((float)this.f_119865_ / 40.0f, 1.0f));
        }
    }

    public static class SubSound
    extends AbstractTickableSoundInstance {
        private final LocalPlayer f_119859_;

        protected SubSound(LocalPlayer p_119861_, SoundEvent p_119862_) {
            super(p_119862_, SoundSource.AMBIENT, SoundInstance.m_235150_());
            this.f_119859_ = p_119861_;
            this.f_119578_ = false;
            this.f_119579_ = 0;
            this.f_119573_ = 1.0f;
            this.f_119582_ = true;
        }

        @Override
        public void m_7788_() {
            if (this.f_119859_.m_213877_() || !this.f_119859_.m_5842_()) {
                this.m_119609_();
            }
        }
    }
}

