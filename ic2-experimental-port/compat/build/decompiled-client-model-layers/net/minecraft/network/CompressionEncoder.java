/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.MessageToByteEncoder
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.zip.Deflater;
import net.minecraft.network.FriendlyByteBuf;

public class CompressionEncoder
extends MessageToByteEncoder<ByteBuf> {
    private final byte[] f_129444_ = new byte[8192];
    private final Deflater f_129445_;
    private int f_129446_;

    public CompressionEncoder(int p_129448_) {
        this.f_129446_ = p_129448_;
        this.f_129445_ = new Deflater();
    }

    protected void encode(ChannelHandlerContext p_129452_, ByteBuf p_129453_, ByteBuf p_129454_) {
        int $$3 = p_129453_.readableBytes();
        FriendlyByteBuf $$4 = new FriendlyByteBuf(p_129454_);
        if ($$3 < this.f_129446_) {
            $$4.m_130130_(0);
            $$4.writeBytes(p_129453_);
        } else {
            byte[] $$5 = new byte[$$3];
            p_129453_.readBytes($$5);
            $$4.m_130130_($$5.length);
            this.f_129445_.setInput($$5, 0, $$3);
            this.f_129445_.finish();
            while (!this.f_129445_.finished()) {
                int $$6 = this.f_129445_.deflate(this.f_129444_);
                $$4.writeBytes(this.f_129444_, 0, $$6);
            }
            this.f_129445_.reset();
        }
    }

    public int m_178298_() {
        return this.f_129446_;
    }

    public void m_129449_(int p_129450_) {
        this.f_129446_ = p_129450_;
    }

    protected /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, byteBuf);
    }
}

