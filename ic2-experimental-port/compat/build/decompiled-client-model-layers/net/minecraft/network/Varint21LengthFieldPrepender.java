/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandler$Sharable
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.MessageToByteEncoder
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.minecraft.network.FriendlyByteBuf;

@ChannelHandler.Sharable
public class Varint21LengthFieldPrepender
extends MessageToByteEncoder<ByteBuf> {
    private static final int f_178385_ = 3;

    protected void encode(ChannelHandlerContext p_130571_, ByteBuf p_130572_, ByteBuf p_130573_) {
        int $$3 = p_130572_.readableBytes();
        int $$4 = FriendlyByteBuf.m_130053_($$3);
        if ($$4 > 3) {
            throw new IllegalArgumentException("unable to fit " + $$3 + " into 3");
        }
        FriendlyByteBuf $$5 = new FriendlyByteBuf(p_130573_);
        $$5.ensureWritable($$4 + $$3);
        $$5.m_130130_($$3);
        $$5.writeBytes(p_130572_, p_130572_.readerIndex(), $$3);
    }

    protected /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)object, byteBuf);
    }
}

