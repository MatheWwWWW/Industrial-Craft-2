/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Bee;

public abstract class BeeSoundInstance
extends AbstractTickableSoundInstance {
    private static final float f_174917_ = 0.0f;
    private static final float f_174918_ = 1.2f;
    private static final float f_174919_ = 0.0f;
    protected final Bee f_119618_;
    private boolean f_119619_;

    public BeeSoundInstance(Bee p_119621_, SoundEvent p_119622_, SoundSource p_119623_) {
        super(p_119622_, p_119623_, SoundInstance.m_235150_());
        this.f_119618_ = p_119621_;
        this.f_119575_ = (float)p_119621_.m_20185_();
        this.f_119576_ = (float)p_119621_.m_20186_();
        this.f_119577_ = (float)p_119621_.m_20189_();
        this.f_119578_ = true;
        this.f_119579_ = 0;
        this.f_119573_ = 0.0f;
    }

    @Override
    public void m_7788_() {
        boolean $$0 = this.m_7774_();
        if ($$0 && !this.m_7801_()) {
            Minecraft.m_91087_().m_91106_().m_120372_(this.m_5958_());
            this.f_119619_ = true;
        }
        if (this.f_119618_.m_213877_() || this.f_119619_) {
            this.m_119609_();
            return;
        }
        this.f_119575_ = (float)this.f_119618_.m_20185_();
        this.f_119576_ = (float)this.f_119618_.m_20186_();
        this.f_119577_ = (float)this.f_119618_.m_20189_();
        float $$1 = (float)this.f_119618_.m_20184_().m_165924_();
        if ($$1 >= 0.01f) {
            this.f_119574_ = Mth.m_14179_(Mth.m_14036_($$1, this.m_119627_(), this.m_119628_()), this.m_119627_(), this.m_119628_());
            this.f_119573_ = Mth.m_14179_(Mth.m_14036_($$1, 0.0f, 0.5f), 0.0f, 1.2f);
        } else {
            this.f_119574_ = 0.0f;
            this.f_119573_ = 0.0f;
        }
    }

    private float m_119627_() {
        if (this.f_119618_.m_6162_()) {
            return 1.1f;
        }
        return 0.7f;
    }

    private float m_119628_() {
        if (this.f_119618_.m_6162_()) {
            return 1.5f;
        }
        return 1.1f;
    }

    @Override
    public boolean m_7784_() {
        return true;
    }

    @Override
    public boolean m_7767_() {
        return !this.f_119618_.m_20067_();
    }

    protected abstract AbstractTickableSoundInstance m_5958_();

    protected abstract boolean m_7774_();
}

