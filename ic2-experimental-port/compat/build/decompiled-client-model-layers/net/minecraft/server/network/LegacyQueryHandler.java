/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  io.netty.channel.ChannelFutureListener
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.channel.ChannelInboundHandlerAdapter
 *  io.netty.util.concurrent.GenericFutureListener
 *  org.slf4j.Logger
 */
package net.minecraft.server.network;

import com.mojang.logging.LogUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerConnectionListener;
import org.slf4j.Logger;

public class LegacyQueryHandler
extends ChannelInboundHandlerAdapter {
    private static final Logger f_9675_ = LogUtils.getLogger();
    public static final int f_143586_ = 127;
    private final ServerConnectionListener f_9676_;

    public LegacyQueryHandler(ServerConnectionListener p_9679_) {
        this.f_9676_ = p_9679_;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void channelRead(ChannelHandlerContext p_9686_, Object p_9687_) {
        ByteBuf $$2 = (ByteBuf)p_9687_;
        $$2.markReaderIndex();
        boolean $$3 = true;
        try {
            if ($$2.readUnsignedByte() != 254) {
                return;
            }
            InetSocketAddress $$4 = (InetSocketAddress)p_9686_.channel().remoteAddress();
            MinecraftServer $$5 = this.f_9676_.m_9722_();
            int $$6 = $$2.readableBytes();
            switch ($$6) {
                case 0: {
                    f_9675_.debug("Ping: (<1.3.x) from {}:{}", (Object)$$4.getAddress(), (Object)$$4.getPort());
                    String $$7 = String.format(Locale.ROOT, "%s\u00a7%d\u00a7%d", $$5.m_129916_(), $$5.m_7416_(), $$5.m_7418_());
                    this.m_9680_(p_9686_, this.m_9683_($$7));
                    break;
                }
                case 1: {
                    if ($$2.readUnsignedByte() != 1) {
                        return;
                    }
                    f_9675_.debug("Ping: (1.4-1.5.x) from {}:{}", (Object)$$4.getAddress(), (Object)$$4.getPort());
                    String $$8 = String.format(Locale.ROOT, "\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, $$5.m_7630_(), $$5.m_129916_(), $$5.m_7416_(), $$5.m_7418_());
                    this.m_9680_(p_9686_, this.m_9683_($$8));
                    break;
                }
                default: {
                    boolean $$9 = $$2.readUnsignedByte() == 1;
                    $$9 &= $$2.readUnsignedByte() == 250;
                    $$9 &= "MC|PingHost".equals(new String($$2.readBytes($$2.readShort() * 2).array(), StandardCharsets.UTF_16BE));
                    int $$10 = $$2.readUnsignedShort();
                    $$9 &= $$2.readUnsignedByte() >= 73;
                    $$9 &= 3 + $$2.readBytes($$2.readShort() * 2).array().length + 4 == $$10;
                    $$9 &= $$2.readInt() <= 65535;
                    if (!($$9 &= $$2.readableBytes() == 0)) {
                        return;
                    }
                    f_9675_.debug("Ping: (1.6) from {}:{}", (Object)$$4.getAddress(), (Object)$$4.getPort());
                    String $$11 = String.format(Locale.ROOT, "\u00a71\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d", 127, $$5.m_7630_(), $$5.m_129916_(), $$5.m_7416_(), $$5.m_7418_());
                    ByteBuf $$12 = this.m_9683_($$11);
                    try {
                        this.m_9680_(p_9686_, $$12);
                        break;
                    }
                    finally {
                        $$12.release();
                    }
                }
            }
            $$2.release();
            $$3 = false;
        }
        catch (RuntimeException runtimeException) {
        }
        finally {
            if ($$3) {
                $$2.resetReaderIndex();
                p_9686_.channel().pipeline().remove("legacy_query");
                p_9686_.fireChannelRead(p_9687_);
            }
        }
    }

    private void m_9680_(ChannelHandlerContext p_9681_, ByteBuf p_9682_) {
        p_9681_.pipeline().firstContext().writeAndFlush((Object)p_9682_).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
    }

    private ByteBuf m_9683_(String p_9684_) {
        ByteBuf $$1 = Unpooled.buffer();
        $$1.writeByte(255);
        char[] $$2 = p_9684_.toCharArray();
        $$1.writeShort($$2.length);
        for (char $$3 : $$2) {
            $$1.writeChar((int)$$3);
        }
        return $$1;
    }
}

