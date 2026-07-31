/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.ByteToMessageDecoder
 *  io.netty.handler.codec.CorruptedFrameException
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;

public class Varint21FrameDecoder
extends ByteToMessageDecoder {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void decode(ChannelHandlerContext p_130566_, ByteBuf p_130567_, List<Object> p_130568_) {
        p_130567_.markReaderIndex();
        byte[] $$3 = new byte[3];
        for (int $$4 = 0; $$4 < $$3.length; ++$$4) {
            if (!p_130567_.isReadable()) {
                p_130567_.resetReaderIndex();
                return;
            }
            $$3[$$4] = p_130567_.readByte();
            if ($$3[$$4] < 0) continue;
            FriendlyByteBuf $$5 = new FriendlyByteBuf(Unpooled.wrappedBuffer((byte[])$$3));
            try {
                int $$6 = $$5.m_130242_();
                if (p_130567_.readableBytes() < $$6) {
                    p_130567_.resetReaderIndex();
                    return;
                }
                p_130568_.add(p_130567_.readBytes($$6));
                return;
            }
            finally {
                $$5.release();
            }
        }
        throw new CorruptedFrameException("length wider than 21-bit");
    }
}

