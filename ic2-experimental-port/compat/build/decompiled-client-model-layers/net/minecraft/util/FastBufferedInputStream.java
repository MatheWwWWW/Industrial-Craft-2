/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import java.io.IOException;
import java.io.InputStream;

public class FastBufferedInputStream
extends InputStream {
    private static final int f_196560_ = 8192;
    private final InputStream f_196561_;
    private final byte[] f_196562_;
    private int f_196563_;
    private int f_196564_;

    public FastBufferedInputStream(InputStream p_196566_) {
        this(p_196566_, 8192);
    }

    public FastBufferedInputStream(InputStream p_196568_, int p_196569_) {
        this.f_196561_ = p_196568_;
        this.f_196562_ = new byte[p_196569_];
    }

    @Override
    public int read() throws IOException {
        if (this.f_196564_ >= this.f_196563_) {
            this.m_196572_();
            if (this.f_196564_ >= this.f_196563_) {
                return -1;
            }
        }
        return Byte.toUnsignedInt(this.f_196562_[this.f_196564_++]);
    }

    @Override
    public int read(byte[] p_196576_, int p_196577_, int p_196578_) throws IOException {
        int $$3 = this.m_196570_();
        if ($$3 <= 0) {
            if (p_196578_ >= this.f_196562_.length) {
                return this.f_196561_.read(p_196576_, p_196577_, p_196578_);
            }
            this.m_196572_();
            $$3 = this.m_196570_();
            if ($$3 <= 0) {
                return -1;
            }
        }
        if (p_196578_ > $$3) {
            p_196578_ = $$3;
        }
        System.arraycopy(this.f_196562_, this.f_196564_, p_196576_, p_196577_, p_196578_);
        this.f_196564_ += p_196578_;
        return p_196578_;
    }

    @Override
    public long skip(long p_196580_) throws IOException {
        if (p_196580_ <= 0L) {
            return 0L;
        }
        long $$1 = this.m_196570_();
        if ($$1 <= 0L) {
            return this.f_196561_.skip(p_196580_);
        }
        if (p_196580_ > $$1) {
            p_196580_ = $$1;
        }
        this.f_196564_ = (int)((long)this.f_196564_ + p_196580_);
        return p_196580_;
    }

    @Override
    public int available() throws IOException {
        return this.m_196570_() + this.f_196561_.available();
    }

    @Override
    public void close() throws IOException {
        this.f_196561_.close();
    }

    private int m_196570_() {
        return this.f_196563_ - this.f_196564_;
    }

    private void m_196572_() throws IOException {
        this.f_196563_ = 0;
        this.f_196564_ = 0;
        int $$0 = this.f_196561_.read(this.f_196562_, 0, this.f_196562_.length);
        if ($$0 > 0) {
            this.f_196563_ = $$0;
        }
    }
}

