/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.lwjgl.openal.AL10
 *  org.slf4j.Logger
 */
package com.mojang.blaze3d.audio;

import com.mojang.blaze3d.audio.OpenAlUtil;
import com.mojang.blaze3d.audio.SoundBuffer;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.openal.AL10;
import org.slf4j.Logger;

public class Channel {
    private static final Logger f_83641_ = LogUtils.getLogger();
    private static final int f_166125_ = 4;
    public static final int f_166124_ = 1;
    private final int f_83642_;
    private final AtomicBoolean f_83643_ = new AtomicBoolean(true);
    private int f_83644_ = 16384;
    @Nullable
    private AudioStream f_83645_;

    @Nullable
    static Channel m_83649_() {
        int[] $$0 = new int[1];
        AL10.alGenSources((int[])$$0);
        if (OpenAlUtil.m_83787_("Allocate new source")) {
            return null;
        }
        return new Channel($$0[0]);
    }

    private Channel(int p_83648_) {
        this.f_83642_ = p_83648_;
    }

    public void m_83665_() {
        if (this.f_83643_.compareAndSet(true, false)) {
            AL10.alSourceStop((int)this.f_83642_);
            OpenAlUtil.m_83787_("Stop");
            if (this.f_83645_ != null) {
                try {
                    this.f_83645_.close();
                }
                catch (IOException $$0) {
                    f_83641_.error("Failed to close audio stream", (Throwable)$$0);
                }
                this.m_83684_();
                this.f_83645_ = null;
            }
            AL10.alDeleteSources((int[])new int[]{this.f_83642_});
            OpenAlUtil.m_83787_("Cleanup");
        }
    }

    public void m_83672_() {
        AL10.alSourcePlay((int)this.f_83642_);
    }

    private int m_83683_() {
        if (!this.f_83643_.get()) {
            return 4116;
        }
        return AL10.alGetSourcei((int)this.f_83642_, (int)4112);
    }

    public void m_83677_() {
        if (this.m_83683_() == 4114) {
            AL10.alSourcePause((int)this.f_83642_);
        }
    }

    public void m_83678_() {
        if (this.m_83683_() == 4115) {
            AL10.alSourcePlay((int)this.f_83642_);
        }
    }

    public void m_83679_() {
        if (this.f_83643_.get()) {
            AL10.alSourceStop((int)this.f_83642_);
            OpenAlUtil.m_83787_("Stop");
        }
    }

    public boolean m_166126_() {
        return this.m_83683_() == 4114;
    }

    public boolean m_83680_() {
        return this.m_83683_() == 4116;
    }

    public void m_83654_(Vec3 p_83655_) {
        AL10.alSourcefv((int)this.f_83642_, (int)4100, (float[])new float[]{(float)p_83655_.f_82479_, (float)p_83655_.f_82480_, (float)p_83655_.f_82481_});
    }

    public void m_83650_(float p_83651_) {
        AL10.alSourcef((int)this.f_83642_, (int)4099, (float)p_83651_);
    }

    public void m_83663_(boolean p_83664_) {
        AL10.alSourcei((int)this.f_83642_, (int)4103, (int)(p_83664_ ? 1 : 0));
    }

    public void m_83666_(float p_83667_) {
        AL10.alSourcef((int)this.f_83642_, (int)4106, (float)p_83667_);
    }

    public void m_83681_() {
        AL10.alSourcei((int)this.f_83642_, (int)53248, (int)0);
    }

    public void m_83673_(float p_83674_) {
        AL10.alSourcei((int)this.f_83642_, (int)53248, (int)53251);
        AL10.alSourcef((int)this.f_83642_, (int)4131, (float)p_83674_);
        AL10.alSourcef((int)this.f_83642_, (int)4129, (float)1.0f);
        AL10.alSourcef((int)this.f_83642_, (int)4128, (float)0.0f);
    }

    public void m_83670_(boolean p_83671_) {
        AL10.alSourcei((int)this.f_83642_, (int)514, (int)(p_83671_ ? 1 : 0));
    }

    public void m_83656_(SoundBuffer p_83657_) {
        p_83657_.m_83800_().ifPresent(p_83676_ -> AL10.alSourcei((int)this.f_83642_, (int)4105, (int)p_83676_));
    }

    public void m_83658_(AudioStream p_83659_) {
        this.f_83645_ = p_83659_;
        AudioFormat $$1 = p_83659_.m_6206_();
        this.f_83644_ = Channel.m_83660_($$1, 1);
        this.m_83652_(4);
    }

    private static int m_83660_(AudioFormat p_83661_, int p_83662_) {
        return (int)((float)(p_83662_ * p_83661_.getSampleSizeInBits()) / 8.0f * (float)p_83661_.getChannels() * p_83661_.getSampleRate());
    }

    private void m_83652_(int p_83653_) {
        if (this.f_83645_ != null) {
            try {
                for (int $$1 = 0; $$1 < p_83653_; ++$$1) {
                    ByteBuffer $$2 = this.f_83645_.m_7118_(this.f_83644_);
                    if ($$2 == null) continue;
                    new SoundBuffer($$2, this.f_83645_.m_6206_()).m_83802_().ifPresent(p_83669_ -> AL10.alSourceQueueBuffers((int)this.f_83642_, (int[])new int[]{p_83669_}));
                }
            }
            catch (IOException $$3) {
                f_83641_.error("Failed to read from audio stream", (Throwable)$$3);
            }
        }
    }

    public void m_83682_() {
        if (this.f_83645_ != null) {
            int $$0 = this.m_83684_();
            this.m_83652_($$0);
        }
    }

    private int m_83684_() {
        int $$0 = AL10.alGetSourcei((int)this.f_83642_, (int)4118);
        if ($$0 > 0) {
            int[] $$1 = new int[$$0];
            AL10.alSourceUnqueueBuffers((int)this.f_83642_, (int[])$$1);
            OpenAlUtil.m_83787_("Unqueue buffers");
            AL10.alDeleteBuffers((int[])$$1);
            OpenAlUtil.m_83787_("Remove processed buffers");
        }
        return $$0;
    }
}

