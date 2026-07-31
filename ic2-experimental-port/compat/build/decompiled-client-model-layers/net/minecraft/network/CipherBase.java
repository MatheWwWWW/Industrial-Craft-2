/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandlerContext
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class CipherBase {
    private final Cipher f_129399_;
    private byte[] f_129400_ = new byte[0];
    private byte[] f_129401_ = new byte[0];

    protected CipherBase(Cipher p_129403_) {
        this.f_129399_ = p_129403_;
    }

    private byte[] m_129404_(ByteBuf p_129405_) {
        int $$1 = p_129405_.readableBytes();
        if (this.f_129400_.length < $$1) {
            this.f_129400_ = new byte[$$1];
        }
        p_129405_.readBytes(this.f_129400_, 0, $$1);
        return this.f_129400_;
    }

    protected ByteBuf m_129409_(ChannelHandlerContext p_129410_, ByteBuf p_129411_) throws ShortBufferException {
        int $$2 = p_129411_.readableBytes();
        byte[] $$3 = this.m_129404_(p_129411_);
        ByteBuf $$4 = p_129410_.alloc().heapBuffer(this.f_129399_.getOutputSize($$2));
        $$4.writerIndex(this.f_129399_.update($$3, 0, $$2, $$4.array(), $$4.arrayOffset()));
        return $$4;
    }

    protected void m_129406_(ByteBuf p_129407_, ByteBuf p_129408_) throws ShortBufferException {
        int $$2 = p_129407_.readableBytes();
        byte[] $$3 = this.m_129404_(p_129407_);
        int $$4 = this.f_129399_.getOutputSize($$2);
        if (this.f_129401_.length < $$4) {
            this.f_129401_ = new byte[$$4];
        }
        p_129408_.writeBytes(this.f_129401_, 0, this.f_129399_.update($$3, 0, $$2, this.f_129401_));
    }
}

