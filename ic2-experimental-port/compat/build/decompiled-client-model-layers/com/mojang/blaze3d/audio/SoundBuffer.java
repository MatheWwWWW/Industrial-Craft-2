/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.lwjgl.openal.AL10
 */
package com.mojang.blaze3d.audio;

import com.mojang.blaze3d.audio.OpenAlUtil;
import java.nio.ByteBuffer;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;
import org.lwjgl.openal.AL10;

public class SoundBuffer {
    @Nullable
    private ByteBuffer f_83793_;
    private final AudioFormat f_83794_;
    private boolean f_83795_;
    private int f_83796_;

    public SoundBuffer(ByteBuffer p_83798_, AudioFormat p_83799_) {
        this.f_83793_ = p_83798_;
        this.f_83794_ = p_83799_;
    }

    OptionalInt m_83800_() {
        if (!this.f_83795_) {
            if (this.f_83793_ == null) {
                return OptionalInt.empty();
            }
            int $$0 = OpenAlUtil.m_83789_(this.f_83794_);
            int[] $$1 = new int[1];
            AL10.alGenBuffers((int[])$$1);
            if (OpenAlUtil.m_83787_("Creating buffer")) {
                return OptionalInt.empty();
            }
            AL10.alBufferData((int)$$1[0], (int)$$0, (ByteBuffer)this.f_83793_, (int)((int)this.f_83794_.getSampleRate()));
            if (OpenAlUtil.m_83787_("Assigning buffer data")) {
                return OptionalInt.empty();
            }
            this.f_83796_ = $$1[0];
            this.f_83795_ = true;
            this.f_83793_ = null;
        }
        return OptionalInt.of(this.f_83796_);
    }

    public void m_83801_() {
        if (this.f_83795_) {
            AL10.alDeleteBuffers((int[])new int[]{this.f_83796_});
            if (OpenAlUtil.m_83787_("Deleting stream buffers")) {
                return;
            }
        }
        this.f_83795_ = false;
    }

    public OptionalInt m_83802_() {
        OptionalInt $$0 = this.m_83800_();
        this.f_83795_ = false;
        return $$0;
    }
}

