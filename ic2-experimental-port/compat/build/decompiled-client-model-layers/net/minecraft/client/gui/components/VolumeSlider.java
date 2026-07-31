/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractOptionSliderButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;

public class VolumeSlider
extends AbstractOptionSliderButton {
    private final SoundSource f_94660_;

    public VolumeSlider(Minecraft p_94662_, int p_94663_, int p_94664_, SoundSource p_94665_, int p_94666_) {
        super(p_94662_.f_91066_, p_94663_, p_94664_, p_94666_, 20, (double)p_94662_.f_91066_.m_92147_(p_94665_));
        this.f_94660_ = p_94665_;
        this.m_5695_();
    }

    @Override
    protected void m_5695_() {
        Component $$0 = (float)this.f_93577_ == (float)this.m_7202_(false) ? CommonComponents.f_130654_ : Component.m_237113_((int)(this.f_93577_ * 100.0) + "%");
        this.m_93666_(Component.m_237115_("soundCategory." + this.f_94660_.m_12676_()).m_130946_(": ").m_7220_($$0));
    }

    @Override
    protected void m_5697_() {
        this.f_93377_.m_92149_(this.f_94660_, (float)this.f_93577_);
        this.f_93377_.m_92169_();
    }
}

