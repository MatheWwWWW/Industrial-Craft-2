/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.PointerBuffer
 *  org.lwjgl.stb.STBVorbis
 *  org.lwjgl.stb.STBVorbisInfo
 *  org.lwjgl.system.MemoryStack
 *  org.lwjgl.system.MemoryUtil
 */
package com.mojang.blaze3d.audio;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.util.Mth;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class OggAudioStream
implements AudioStream {
    private static final int f_166130_ = 8192;
    private long f_83746_;
    private final AudioFormat f_83747_;
    private final InputStream f_83748_;
    private ByteBuffer f_83749_ = MemoryUtil.memAlloc((int)8192);

    public OggAudioStream(InputStream p_83751_) throws IOException {
        this.f_83748_ = p_83751_;
        this.f_83749_.limit(0);
        try (MemoryStack $$1 = MemoryStack.stackPush();){
            IntBuffer $$2 = $$1.mallocInt(1);
            IntBuffer $$3 = $$1.mallocInt(1);
            while (this.f_83746_ == 0L) {
                if (!this.m_83765_()) {
                    throw new IOException("Failed to find Ogg header");
                }
                int $$4 = this.f_83749_.position();
                this.f_83749_.position(0);
                this.f_83746_ = STBVorbis.stb_vorbis_open_pushdata((ByteBuffer)this.f_83749_, (IntBuffer)$$2, (IntBuffer)$$3, null);
                this.f_83749_.position($$4);
                int $$5 = $$3.get(0);
                if ($$5 == 1) {
                    this.m_83767_();
                    continue;
                }
                if ($$5 == 0) continue;
                throw new IOException("Failed to read Ogg file " + $$5);
            }
            this.f_83749_.position(this.f_83749_.position() + $$2.get(0));
            STBVorbisInfo $$6 = STBVorbisInfo.mallocStack((MemoryStack)$$1);
            STBVorbis.stb_vorbis_get_info((long)this.f_83746_, (STBVorbisInfo)$$6);
            this.f_83747_ = new AudioFormat($$6.sample_rate(), 16, $$6.channels(), true, false);
        }
    }

    private boolean m_83765_() throws IOException {
        int $$0 = this.f_83749_.limit();
        int $$1 = this.f_83749_.capacity() - $$0;
        if ($$1 == 0) {
            return true;
        }
        byte[] $$2 = new byte[$$1];
        int $$3 = this.f_83748_.read($$2);
        if ($$3 == -1) {
            return false;
        }
        int $$4 = this.f_83749_.position();
        this.f_83749_.limit($$0 + $$3);
        this.f_83749_.position($$0);
        this.f_83749_.put($$2, 0, $$3);
        this.f_83749_.position($$4);
        return true;
    }

    private void m_83767_() {
        boolean $$1;
        boolean $$0 = this.f_83749_.position() == 0;
        boolean bl = $$1 = this.f_83749_.position() == this.f_83749_.limit();
        if ($$1 && !$$0) {
            this.f_83749_.position(0);
            this.f_83749_.limit(0);
        } else {
            ByteBuffer $$2 = MemoryUtil.memAlloc((int)($$0 ? 2 * this.f_83749_.capacity() : this.f_83749_.capacity()));
            $$2.put(this.f_83749_);
            MemoryUtil.memFree((Buffer)this.f_83749_);
            $$2.flip();
            this.f_83749_ = $$2;
        }
    }

    private boolean m_83755_(OutputConcat p_83756_) throws IOException {
        if (this.f_83746_ == 0L) {
            return false;
        }
        try (MemoryStack $$1 = MemoryStack.stackPush();){
            block14: {
                int $$7;
                PointerBuffer $$2 = $$1.mallocPointer(1);
                IntBuffer $$3 = $$1.mallocInt(1);
                IntBuffer $$4 = $$1.mallocInt(1);
                while (true) {
                    int $$5 = STBVorbis.stb_vorbis_decode_frame_pushdata((long)this.f_83746_, (ByteBuffer)this.f_83749_, (IntBuffer)$$3, (PointerBuffer)$$2, (IntBuffer)$$4);
                    this.f_83749_.position(this.f_83749_.position() + $$5);
                    int $$6 = STBVorbis.stb_vorbis_get_error((long)this.f_83746_);
                    if ($$6 == 1) {
                        this.m_83767_();
                        if (this.m_83765_()) continue;
                        break block14;
                    }
                    if ($$6 != 0) {
                        throw new IOException("Failed to read Ogg file " + $$6);
                    }
                    $$7 = $$4.get(0);
                    if ($$7 != 0) break;
                }
                int $$8 = $$3.get(0);
                PointerBuffer $$9 = $$2.getPointerBuffer($$8);
                if ($$8 == 1) {
                    this.m_83757_($$9.getFloatBuffer(0, $$7), p_83756_);
                    boolean bl = true;
                    return bl;
                }
                if ($$8 == 2) {
                    this.m_83760_($$9.getFloatBuffer(0, $$7), $$9.getFloatBuffer(1, $$7), p_83756_);
                    boolean bl = true;
                    return bl;
                }
                throw new IllegalStateException("Invalid number of channels: " + $$8);
            }
            boolean bl = false;
            return bl;
        }
    }

    private void m_83757_(FloatBuffer p_83758_, OutputConcat p_83759_) {
        while (p_83758_.hasRemaining()) {
            p_83759_.m_83775_(p_83758_.get());
        }
    }

    private void m_83760_(FloatBuffer p_83761_, FloatBuffer p_83762_, OutputConcat p_83763_) {
        while (p_83761_.hasRemaining() && p_83762_.hasRemaining()) {
            p_83763_.m_83775_(p_83761_.get());
            p_83763_.m_83775_(p_83762_.get());
        }
    }

    @Override
    public void close() throws IOException {
        if (this.f_83746_ != 0L) {
            STBVorbis.stb_vorbis_close((long)this.f_83746_);
            this.f_83746_ = 0L;
        }
        MemoryUtil.memFree((Buffer)this.f_83749_);
        this.f_83748_.close();
    }

    @Override
    public AudioFormat m_6206_() {
        return this.f_83747_;
    }

    @Override
    public ByteBuffer m_7118_(int p_83754_) throws IOException {
        OutputConcat $$1 = new OutputConcat(p_83754_ + 8192);
        while (this.m_83755_($$1) && $$1.f_83770_ < p_83754_) {
        }
        return $$1.m_83774_();
    }

    public ByteBuffer m_83764_() throws IOException {
        OutputConcat $$0 = new OutputConcat(16384);
        while (this.m_83755_($$0)) {
        }
        return $$0.m_83774_();
    }

    static class OutputConcat {
        private final List<ByteBuffer> f_83768_ = Lists.newArrayList();
        private final int f_83769_;
        int f_83770_;
        private ByteBuffer f_83771_;

        public OutputConcat(int p_83773_) {
            this.f_83769_ = p_83773_ + 1 & 0xFFFFFFFE;
            this.m_83779_();
        }

        private void m_83779_() {
            this.f_83771_ = BufferUtils.createByteBuffer((int)this.f_83769_);
        }

        public void m_83775_(float p_83776_) {
            if (this.f_83771_.remaining() == 0) {
                this.f_83771_.flip();
                this.f_83768_.add(this.f_83771_);
                this.m_83779_();
            }
            int $$1 = Mth.m_14045_((int)(p_83776_ * 32767.5f - 0.5f), Short.MIN_VALUE, Short.MAX_VALUE);
            this.f_83771_.putShort((short)$$1);
            this.f_83770_ += 2;
        }

        public ByteBuffer m_83774_() {
            this.f_83771_.flip();
            if (this.f_83768_.isEmpty()) {
                return this.f_83771_;
            }
            ByteBuffer $$0 = BufferUtils.createByteBuffer((int)this.f_83770_);
            this.f_83768_.forEach($$0::put);
            $$0.put(this.f_83771_);
            $$0.flip();
            return $$0;
        }
    }
}

