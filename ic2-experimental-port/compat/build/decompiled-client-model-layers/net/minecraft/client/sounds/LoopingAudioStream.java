/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.sounds;

import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.sounds.AudioStream;

public class LoopingAudioStream
implements AudioStream {
    private final AudioStreamProvider f_120159_;
    private AudioStream f_120160_;
    private final BufferedInputStream f_120161_;

    public LoopingAudioStream(AudioStreamProvider p_120163_, InputStream p_120164_) throws IOException {
        this.f_120159_ = p_120163_;
        this.f_120161_ = new BufferedInputStream(p_120164_);
        this.f_120161_.mark(Integer.MAX_VALUE);
        this.f_120160_ = p_120163_.m_120169_(new NoCloseBuffer(this.f_120161_));
    }

    @Override
    public AudioFormat m_6206_() {
        return this.f_120160_.m_6206_();
    }

    @Override
    public ByteBuffer m_7118_(int p_120167_) throws IOException {
        ByteBuffer $$1 = this.f_120160_.m_7118_(p_120167_);
        if (!$$1.hasRemaining()) {
            this.f_120160_.close();
            this.f_120161_.reset();
            this.f_120160_ = this.f_120159_.m_120169_(new NoCloseBuffer(this.f_120161_));
            $$1 = this.f_120160_.m_7118_(p_120167_);
        }
        return $$1;
    }

    @Override
    public void close() throws IOException {
        this.f_120160_.close();
        this.f_120161_.close();
    }

    @FunctionalInterface
    public static interface AudioStreamProvider {
        public AudioStream m_120169_(InputStream var1) throws IOException;
    }

    static class NoCloseBuffer
    extends FilterInputStream {
        NoCloseBuffer(InputStream p_120172_) {
            super(p_120172_);
        }

        @Override
        public void close() {
        }
    }
}

