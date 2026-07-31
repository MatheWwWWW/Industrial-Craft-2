/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

public class RidingMinecartSoundInstance
extends AbstractTickableSoundInstance {
    private static final float f_174936_ = 0.0f;
    private static final float f_174937_ = 0.75f;
    private final Player f_119700_;
    private final AbstractMinecart f_119701_;
    private final boolean f_174938_;

    public RidingMinecartSoundInstance(Player p_174940_, AbstractMinecart p_174941_, boolean p_174942_) {
        super(p_174942_ ? SoundEvents.f_144194_ : SoundEvents.f_12069_, SoundSource.NEUTRAL, SoundInstance.m_235150_());
        this.f_119700_ = p_174940_;
        this.f_119701_ = p_174941_;
        this.f_174938_ = p_174942_;
        this.f_119580_ = SoundInstance.Attenuation.NONE;
        this.f_119578_ = true;
        this.f_119579_ = 0;
        this.f_119573_ = 0.0f;
    }

    @Override
    public boolean m_7767_() {
        return !this.f_119701_.m_20067_();
    }

    @Override
    public boolean m_7784_() {
        return true;
    }

    @Override
    public void m_7788_() {
        if (this.f_119701_.m_213877_() || !this.f_119700_.m_20159_() || this.f_119700_.m_20202_() != this.f_119701_) {
            this.m_119609_();
            return;
        }
        if (this.f_174938_ != this.f_119700_.m_5842_()) {
            this.f_119573_ = 0.0f;
            return;
        }
        float $$0 = (float)this.f_119701_.m_20184_().m_165924_();
        this.f_119573_ = $$0 >= 0.01f ? Mth.m_144920_(0.0f, 0.75f, $$0) : 0.0f;
    }
}

