/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.MessageToMessageDecoder
 */
package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import javax.crypto.Cipher;
import net.minecraft.network.CipherBase;

public class CipherDecoder
extends MessageToMessageDecoder<ByteBuf> {
    private final CipherBase f_129412_;

    public CipherDecoder(Cipher p_129414_) {
        this.f_129412_ = new CipherBase(p_129414_);
    }

    protected void decode(ChannelHandlerContext p_129416_, ByteBuf p_129417_, List<Object> p_129418_) throws Exception {
        p_129418_.add(this.f_129412_.m_129409_(p_129416_, p_129417_));
    }

    protected /* synthetic */ void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (ByteBuf)object, (List<Object>)list);
    }
}

