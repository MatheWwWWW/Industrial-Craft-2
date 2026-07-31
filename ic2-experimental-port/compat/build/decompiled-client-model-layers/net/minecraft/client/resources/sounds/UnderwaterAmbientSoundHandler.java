/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.sounds;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.client.resources.sounds.UnderwaterAmbientSoundInstances;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvents;

public class UnderwaterAmbientSoundHandler
implements AmbientSoundHandler {
    public static final float f_174957_ = 0.01f;
    public static final float f_174958_ = 0.001f;
    public static final float f_174959_ = 1.0E-4f;
    private static final int f_174960_ = 0;
    private final LocalPlayer f_119852_;
    private final SoundManager f_119853_;
    private int f_119854_ = 0;

    public UnderwaterAmbientSoundHandler(LocalPlayer p_119856_, SoundManager p_119857_) {
        this.f_119852_ = p_119856_;
        this.f_119853_ = p_119857_;
    }

    @Override
    public void m_7551_() {
        --this.f_119854_;
        if (this.f_119854_ <= 0 && this.f_119852_.m_5842_()) {
            float $$0 = this.f_119852_.f_19853_.f_46441_.m_188501_();
            if ($$0 < 1.0E-4f) {
                this.f_119854_ = 0;
                this.f_119853_.m_120367_(new UnderwaterAmbientSoundInstances.SubSound(this.f_119852_, SoundEvents.f_12650_));
            } else if ($$0 < 0.001f) {
                this.f_119854_ = 0;
                this.f_119853_.m_120367_(new UnderwaterAmbientSoundInstances.SubSound(this.f_119852_, SoundEvents.f_12649_));
            } else if ($$0 < 0.01f) {
                this.f_119854_ = 0;
                this.f_119853_.m_120367_(new UnderwaterAmbientSoundInstances.SubSound(this.f_119852_, SoundEvents.f_12648_));
            }
        }
    }
}

