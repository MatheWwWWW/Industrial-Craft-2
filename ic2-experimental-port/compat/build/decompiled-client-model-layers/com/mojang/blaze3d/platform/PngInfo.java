/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.stb.STBIEOFCallback
 *  org.lwjgl.stb.STBIEOFCallbackI
 *  org.lwjgl.stb.STBIIOCallbacks
 *  org.lwjgl.stb.STBIReadCallback
 *  org.lwjgl.stb.STBIReadCallbackI
 *  org.lwjgl.stb.STBISkipCallback
 *  org.lwjgl.stb.STBISkipCallbackI
 *  org.lwjgl.stb.STBImage
 *  org.lwjgl.system.MemoryStack
 *  org.lwjgl.system.MemoryUtil
 */
package com.mojang.blaze3d.platform;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.util.function.Supplier;
import org.lwjgl.stb.STBIEOFCallback;
import org.lwjgl.stb.STBIEOFCallbackI;
import org.lwjgl.stb.STBIIOCallbacks;
import org.lwjgl.stb.STBIReadCallback;
import org.lwjgl.stb.STBIReadCallbackI;
import org.lwjgl.stb.STBISkipCallback;
import org.lwjgl.stb.STBISkipCallbackI;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class PngInfo {
    public final int f_85207_;
    public final int f_85208_;

    public PngInfo(Supplier<String> p_231136_, InputStream p_231137_) throws IOException {
        try (MemoryStack $$2 = MemoryStack.stackPush();
             StbReader $$3 = PngInfo.m_85212_(p_231137_);
             STBIReadCallback $$4 = STBIReadCallback.create($$3::m_85223_);
             STBISkipCallback $$5 = STBISkipCallback.create($$3::m_85220_);
             STBIEOFCallback $$6 = STBIEOFCallback.create($$3::m_6816_);){
            STBIIOCallbacks $$7 = STBIIOCallbacks.mallocStack((MemoryStack)$$2);
            $$7.read((STBIReadCallbackI)$$4);
            $$7.skip((STBISkipCallbackI)$$5);
            $$7.eof((STBIEOFCallbackI)$$6);
            IntBuffer $$8 = $$2.mallocInt(1);
            IntBuffer $$9 = $$2.mallocInt(1);
            IntBuffer $$10 = $$2.mallocInt(1);
            if (!STBImage.stbi_info_from_callbacks((STBIIOCallbacks)$$7, (long)0L, (IntBuffer)$$8, (IntBuffer)$$9, (IntBuffer)$$10)) {
                throw new IOException("Could not read info from the PNG file " + p_231136_.get() + " " + STBImage.stbi_failure_reason());
            }
            this.f_85207_ = $$8.get(0);
            this.f_85208_ = $$9.get(0);
        }
    }

    private static StbReader m_85212_(InputStream p_85213_) {
        if (p_85213_ instanceof FileInputStream) {
            return new StbReaderSeekableByteChannel(((FileInputStream)p_85213_).getChannel());
        }
        return new StbReaderBufferedChannel(Channels.newChannel(p_85213_));
    }

    static abstract class StbReader
    implements AutoCloseable {
        protected boolean f_85214_;

        StbReader() {
        }

        int m_85223_(long p_85224_, long p_85225_, int p_85226_) {
            try {
                return this.m_5835_(p_85225_, p_85226_);
            }
            catch (IOException $$3) {
                this.f_85214_ = true;
                return 0;
            }
        }

        void m_85220_(long p_85221_, int p_85222_) {
            try {
                this.m_5666_(p_85222_);
            }
            catch (IOException $$2) {
                this.f_85214_ = true;
            }
        }

        int m_6816_(long p_85219_) {
            return this.f_85214_ ? 1 : 0;
        }

        protected abstract int m_5835_(long var1, int var3) throws IOException;

        protected abstract void m_5666_(int var1) throws IOException;

        @Override
        public abstract void close() throws IOException;
    }

    static class StbReaderSeekableByteChannel
    extends StbReader {
        private final SeekableByteChannel f_85248_;

        StbReaderSeekableByteChannel(SeekableByteChannel p_85250_) {
            this.f_85248_ = p_85250_;
        }

        @Override
        public int m_5835_(long p_85259_, int p_85260_) throws IOException {
            ByteBuffer $$2 = MemoryUtil.memByteBuffer((long)p_85259_, (int)p_85260_);
            return this.f_85248_.read($$2);
        }

        @Override
        public void m_5666_(int p_85255_) throws IOException {
            this.f_85248_.position(this.f_85248_.position() + (long)p_85255_);
        }

        @Override
        public int m_6816_(long p_85257_) {
            return super.m_6816_(p_85257_) != 0 && this.f_85248_.isOpen() ? 1 : 0;
        }

        @Override
        public void close() throws IOException {
            this.f_85248_.close();
        }
    }

    static class StbReaderBufferedChannel
    extends StbReader {
        private static final int f_166443_ = 128;
        private final ReadableByteChannel f_85230_;
        private long f_85231_ = MemoryUtil.nmemAlloc((long)128L);
        private int f_85232_ = 128;
        private int f_85233_;
        private int f_85234_;

        StbReaderBufferedChannel(ReadableByteChannel p_85236_) {
            this.f_85230_ = p_85236_;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void m_85242_(int p_85243_) throws IOException {
            ByteBuffer $$1 = MemoryUtil.memByteBuffer((long)this.f_85231_, (int)this.f_85232_);
            if (p_85243_ + this.f_85234_ > this.f_85232_) {
                this.f_85232_ = p_85243_ + this.f_85234_;
                $$1 = MemoryUtil.memRealloc((ByteBuffer)$$1, (int)this.f_85232_);
                this.f_85231_ = MemoryUtil.memAddress((ByteBuffer)$$1);
            }
            $$1.position(this.f_85233_);
            while (p_85243_ + this.f_85234_ > this.f_85233_) {
                try {
                    int $$2 = this.f_85230_.read($$1);
                    if ($$2 != -1) continue;
                    break;
                }
                finally {
                    this.f_85233_ = $$1.position();
                }
            }
        }

        @Override
        public int m_5835_(long p_85245_, int p_85246_) throws IOException {
            this.m_85242_(p_85246_);
            if (p_85246_ + this.f_85234_ > this.f_85233_) {
                p_85246_ = this.f_85233_ - this.f_85234_;
            }
            MemoryUtil.memCopy((long)(this.f_85231_ + (long)this.f_85234_), (long)p_85245_, (long)p_85246_);
            this.f_85234_ += p_85246_;
            return p_85246_;
        }

        @Override
        public void m_5666_(int p_85241_) throws IOException {
            if (p_85241_ > 0) {
                this.m_85242_(p_85241_);
                if (p_85241_ + this.f_85234_ > this.f_85233_) {
                    throw new EOFException("Can't skip past the EOF.");
                }
            }
            if (this.f_85234_ + p_85241_ < 0) {
                throw new IOException("Can't seek before the beginning: " + (this.f_85234_ + p_85241_));
            }
            this.f_85234_ += p_85241_;
        }

        @Override
        public void close() throws IOException {
            MemoryUtil.nmemFree((long)this.f_85231_);
            this.f_85230_.close();
        }
    }
}

