/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.ByteToMessageDecoder
 *  io.netty.handler.codec.DecoderException
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import java.util.List;
import java.util.zip.Inflater;
import net.minecraft.network.FriendlyByteBuf;

public class CompressionDecoder
extends ByteToMessageDecoder {
    public static final int f_182671_ = 0x200000;
    public static final int f_182672_ = 0x800000;
    private final Inflater f_129434_;
    private int f_129435_;
    private boolean f_182673_;

    public CompressionDecoder(int p_182675_, boolean p_182676_) {
        this.f_129435_ = p_182675_;
        this.f_182673_ = p_182676_;
        this.f_129434_ = new Inflater();
    }

    protected void decode(ChannelHandlerContext p_129441_, ByteBuf p_129442_, List<Object> p_129443_) throws Exception {
        if (p_129442_.readableBytes() == 0) {
            return;
        }
        FriendlyByteBuf $$3 = new FriendlyByteBuf(p_129442_);
        int $$4 = $$3.m_130242_();
        if ($$4 == 0) {
            p_129443_.add($$3.readBytes($$3.readableBytes()));
            return;
        }
        if (this.f_182673_) {
            if ($$4 < this.f_129435_) {
                throw new DecoderException("Badly compressed packet - size of " + $$4 + " is below server threshold of " + this.f_129435_);
            }
            if ($$4 > 0x800000) {
                throw new DecoderException("Badly compressed packet - size of " + $$4 + " is larger than protocol maximum of 8388608");
            }
        }
        byte[] $$5 = new byte[$$3.readableBytes()];
        $$3.readBytes($$5);
        this.f_129434_.setInput($$5);
        byte[] $$6 = new byte[$$4];
        this.f_129434_.inflate($$6);
        p_129443_.add(Unpooled.wrappedBuffer((byte[])$$6));
        this.f_129434_.reset();
    }

    public void m_182677_(int p_182678_, boolean p_182679_) {
        this.f_129435_ = p_182678_;
        this.f_182673_ = p_182679_;
    }
}

