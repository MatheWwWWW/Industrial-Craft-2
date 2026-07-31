/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.ByteToMessageDecoder
 *  org.slf4j.Logger
 */
package net.minecraft.network;

import com.mojang.logging.LogUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import org.slf4j.Logger;

public class PacketDecoder
extends ByteToMessageDecoder {
    private static final Logger f_130528_ = LogUtils.getLogger();
    private final PacketFlow f_130530_;

    public PacketDecoder(PacketFlow p_130533_) {
        this.f_130530_ = p_130533_;
    }

    protected void decode(ChannelHandlerContext p_130535_, ByteBuf p_130536_, List<Object> p_130537_) throws Exception {
        int $$3 = p_130536_.readableBytes();
        if ($$3 == 0) {
            return;
        }
        FriendlyByteBuf $$4 = new FriendlyByteBuf(p_130536_);
        int $$5 = $$4.m_130242_();
        Packet<?> $$6 = ((ConnectionProtocol)((Object)p_130535_.channel().attr(Connection.f_129461_).get())).m_178321_(this.f_130530_, $$5, $$4);
        if ($$6 == null) {
            throw new IOException("Bad packet id " + $$5);
        }
        int $$7 = ((ConnectionProtocol)((Object)p_130535_.channel().attr(Connection.f_129461_).get())).m_129582_();
        JvmProfiler.f_185340_.m_183510_($$7, $$5, p_130535_.channel().remoteAddress(), $$3);
        if ($$4.readableBytes() > 0) {
            throw new IOException("Packet " + ((ConnectionProtocol)((Object)p_130535_.channel().attr(Connection.f_129461_).get())).m_129582_() + "/" + $$5 + " (" + $$6.getClass().getSimpleName() + ") was larger than I expected, found " + $$4.readableBytes() + " bytes extra whilst reading packet " + $$5);
        }
        p_130537_.add($$6);
        if (f_130528_.isDebugEnabled()) {
            f_130528_.debug(Connection.f_202554_, " IN: [{}:{}] {}", new Object[]{p_130535_.channel().attr(Connection.f_129461_).get(), $$5, $$6.getClass().getName()});
        }
    }
}

