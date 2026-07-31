/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.player;

import net.minecraft.client.Options;
import net.minecraft.client.player.Input;

public class KeyboardInput
extends Input {
    private final Options f_108578_;

    public KeyboardInput(Options p_108580_) {
        this.f_108578_ = p_108580_;
    }

    private static float m_205577_(boolean p_205578_, boolean p_205579_) {
        if (p_205578_ == p_205579_) {
            return 0.0f;
        }
        return p_205578_ ? 1.0f : -1.0f;
    }

    @Override
    public void m_214106_(boolean p_234118_, float p_234119_) {
        this.f_108568_ = this.f_108578_.f_92085_.m_90857_();
        this.f_108569_ = this.f_108578_.f_92087_.m_90857_();
        this.f_108570_ = this.f_108578_.f_92086_.m_90857_();
        this.f_108571_ = this.f_108578_.f_92088_.m_90857_();
        this.f_108567_ = KeyboardInput.m_205577_(this.f_108568_, this.f_108569_);
        this.f_108566_ = KeyboardInput.m_205577_(this.f_108570_, this.f_108571_);
        this.f_108572_ = this.f_108578_.f_92089_.m_90857_();
        this.f_108573_ = this.f_108578_.f_92090_.m_90857_();
        if (p_234118_) {
            this.f_108566_ *= p_234119_;
            this.f_108567_ *= p_234119_;
        }
    }
}

