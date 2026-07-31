/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.handler.codec.MessageToByteEncoder
 *  org.slf4j.Logger
 */
package net.minecraft.network;

import com.mojang.logging.LogUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.SkipPacketException;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import org.slf4j.Logger;

public class PacketEncoder
extends MessageToByteEncoder<Packet<?>> {
    private static final Logger f_130538_ = LogUtils.getLogger();
    private final PacketFlow f_130540_;

    public PacketEncoder(PacketFlow p_130543_) {
        this.f_130540_ = p_130543_;
    }

    protected void encode(ChannelHandlerContext p_130545_, Packet<?> p_130546_, ByteBuf p_130547_) throws Exception {
        ConnectionProtocol $$3 = (ConnectionProtocol)((Object)p_130545_.channel().attr(Connection.f_129461_).get());
        if ($$3 == null) {
            throw new RuntimeException("ConnectionProtocol unknown: " + p_130546_);
        }
        Integer $$4 = $$3.m_129597_(this.f_130540_, p_130546_);
        if (f_130538_.isDebugEnabled()) {
            f_130538_.debug(Connection.f_202555_, "OUT: [{}:{}] {}", new Object[]{p_130545_.channel().attr(Connection.f_129461_).get(), $$4, p_130546_.getClass().getName()});
        }
        if ($$4 == null) {
            throw new IOException("Can't serialize unregistered packet");
        }
        FriendlyByteBuf $$5 = new FriendlyByteBuf(p_130547_);
        $$5.m_130130_($$4);
        try {
            int $$6 = $$5.writerIndex();
            p_130546_.m_5779_($$5);
            int $$7 = $$5.writerIndex() - $$6;
            if ($$7 > 0x800000) {
                throw new IllegalArgumentException("Packet too big (is " + $$7 + ", should be less than 8388608): " + p_130546_);
            }
            int $$8 = ((ConnectionProtocol)((Object)p_130545_.channel().attr(Connection.f_129461_).get())).m_129582_();
            JvmProfiler.f_185340_.m_183508_($$8, $$4, p_130545_.channel().remoteAddress(), $$7);
        }
        catch (Throwable $$9) {
            f_130538_.error("Error receiving packet {}", (Object)$$4, (Object)$$9);
            if (p_130546_.m_6588_()) {
                throw new SkipPacketException($$9);
            }
            throw $$9;
        }
    }

    protected /* synthetic */ void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (Packet)object, byteBuf);
    }
}

