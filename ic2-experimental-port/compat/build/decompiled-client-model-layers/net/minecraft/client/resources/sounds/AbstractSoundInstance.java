/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.sounds;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public abstract class AbstractSoundInstance
implements SoundInstance {
    protected Sound f_119570_;
    protected final SoundSource f_119571_;
    protected final ResourceLocation f_119572_;
    protected float f_119573_ = 1.0f;
    protected float f_119574_ = 1.0f;
    protected double f_119575_;
    protected double f_119576_;
    protected double f_119577_;
    protected boolean f_119578_;
    protected int f_119579_;
    protected SoundInstance.Attenuation f_119580_ = SoundInstance.Attenuation.LINEAR;
    protected boolean f_119582_;
    protected RandomSource f_235066_;

    protected AbstractSoundInstance(SoundEvent p_235072_, SoundSource p_235073_, RandomSource p_235074_) {
        this(p_235072_.m_11660_(), p_235073_, p_235074_);
    }

    protected AbstractSoundInstance(ResourceLocation p_235068_, SoundSource p_235069_, RandomSource p_235070_) {
        this.f_119572_ = p_235068_;
        this.f_119571_ = p_235069_;
        this.f_235066_ = p_235070_;
    }

    @Override
    public ResourceLocation m_7904_() {
        return this.f_119572_;
    }

    @Override
    public WeighedSoundEvents m_6775_(SoundManager p_119591_) {
        WeighedSoundEvents $$1 = p_119591_.m_120384_(this.f_119572_);
        this.f_119570_ = $$1 == null ? SoundManager.f_120344_ : $$1.m_213718_(this.f_235066_);
        return $$1;
    }

    @Override
    public Sound m_5891_() {
        return this.f_119570_;
    }

    @Override
    public SoundSource m_8070_() {
        return this.f_119571_;
    }

    @Override
    public boolean m_7775_() {
        return this.f_119578_;
    }

    @Override
    public int m_7766_() {
        return this.f_119579_;
    }

    @Override
    public float m_7769_() {
        return this.f_119573_ * this.f_119570_.m_235146_().m_214084_(this.f_235066_);
    }

    @Override
    public float m_7783_() {
        return this.f_119574_ * this.f_119570_.m_235147_().m_214084_(this.f_235066_);
    }

    @Override
    public double m_7772_() {
        return this.f_119575_;
    }

    @Override
    public double m_7780_() {
        return this.f_119576_;
    }

    @Override
    public double m_7778_() {
        return this.f_119577_;
    }

    @Override
    public SoundInstance.Attenuation m_7438_() {
        return this.f_119580_;
    }

    @Override
    public boolean m_7796_() {
        return this.f_119582_;
    }

    public String toString() {
        return "SoundInstance[" + this.f_119572_ + "]";
    }
}

