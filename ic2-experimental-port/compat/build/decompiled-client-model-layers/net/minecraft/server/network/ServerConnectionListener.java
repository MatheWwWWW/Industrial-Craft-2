/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  com.mojang.logging.LogUtils
 *  io.netty.bootstrap.ServerBootstrap
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelException
 *  io.netty.channel.ChannelFuture
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.channel.ChannelInboundHandlerAdapter
 *  io.netty.channel.ChannelInitializer
 *  io.netty.channel.ChannelOption
 *  io.netty.channel.EventLoopGroup
 *  io.netty.channel.epoll.Epoll
 *  io.netty.channel.epoll.EpollEventLoopGroup
 *  io.netty.channel.epoll.EpollServerSocketChannel
 *  io.netty.channel.local.LocalAddress
 *  io.netty.channel.local.LocalServerChannel
 *  io.netty.channel.nio.NioEventLoopGroup
 *  io.netty.channel.socket.nio.NioServerSocketChannel
 *  io.netty.handler.timeout.ReadTimeoutHandler
 *  io.netty.util.HashedWheelTimer
 *  io.netty.util.Timeout
 *  io.netty.util.Timer
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.network;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.logging.LogUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketDecoder;
import net.minecraft.network.PacketEncoder;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.RateKickingConnection;
import net.minecraft.network.Varint21FrameDecoder;
import net.minecraft.network.Varint21LengthFieldPrepender;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.LegacyQueryHandler;
import net.minecraft.server.network.MemoryServerHandshakePacketListenerImpl;
import net.minecraft.server.network.ServerHandshakePacketListenerImpl;
import net.minecraft.util.LazyLoadedValue;
import org.slf4j.Logger;

public class ServerConnectionListener {
    private static final Logger f_9701_ = LogUtils.getLogger();
    public static final LazyLoadedValue<NioEventLoopGroup> f_9698_ = new LazyLoadedValue<NioEventLoopGroup>(() -> new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Server IO #%d").setDaemon(true).build()));
    public static final LazyLoadedValue<EpollEventLoopGroup> f_9699_ = new LazyLoadedValue<EpollEventLoopGroup>(() -> new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build()));
    final MinecraftServer f_9702_;
    public volatile boolean f_9700_;
    private final List<ChannelFuture> f_9703_ = Collections.synchronizedList(Lists.newArrayList());
    final List<Connection> f_9704_ = Collections.synchronizedList(Lists.newArrayList());

    public ServerConnectionListener(MinecraftServer p_9707_) {
        this.f_9702_ = p_9707_;
        this.f_9700_ = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void m_9711_(@Nullable InetAddress p_9712_, int p_9713_) throws IOException {
        List<ChannelFuture> list = this.f_9703_;
        synchronized (list) {
            LazyLoadedValue<NioEventLoopGroup> $$5;
            Class<NioServerSocketChannel> $$4;
            if (Epoll.isAvailable() && this.f_9702_.m_6994_()) {
                Class<EpollServerSocketChannel> $$2 = EpollServerSocketChannel.class;
                LazyLoadedValue<EpollEventLoopGroup> $$3 = f_9699_;
                f_9701_.info("Using epoll channel type");
            } else {
                $$4 = NioServerSocketChannel.class;
                $$5 = f_9698_;
                f_9701_.info("Using default channel type");
            }
            this.f_9703_.add(((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel($$4)).childHandler((ChannelHandler)new ChannelInitializer<Channel>(){

                protected void initChannel(Channel p_9729_) {
                    try {
                        p_9729_.config().setOption(ChannelOption.TCP_NODELAY, (Object)true);
                    }
                    catch (ChannelException channelException) {
                        // empty catch block
                    }
                    p_9729_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("legacy_query", (ChannelHandler)new LegacyQueryHandler(ServerConnectionListener.this)).addLast("splitter", (ChannelHandler)new Varint21FrameDecoder()).addLast("decoder", (ChannelHandler)new PacketDecoder(PacketFlow.SERVERBOUND)).addLast("prepender", (ChannelHandler)new Varint21LengthFieldPrepender()).addLast("encoder", (ChannelHandler)new PacketEncoder(PacketFlow.CLIENTBOUND));
                    int $$1 = ServerConnectionListener.this.f_9702_.m_7032_();
                    Connection $$2 = $$1 > 0 ? new RateKickingConnection($$1) : new Connection(PacketFlow.SERVERBOUND);
                    ServerConnectionListener.this.f_9704_.add($$2);
                    p_9729_.pipeline().addLast("packet_handler", (ChannelHandler)$$2);
                    $$2.m_129505_(new ServerHandshakePacketListenerImpl(ServerConnectionListener.this.f_9702_, $$2));
                }
            }).group((EventLoopGroup)$$5.m_13971_()).localAddress(p_9712_, p_9713_)).bind().syncUninterruptibly());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public SocketAddress m_9708_() {
        void $$1;
        List<ChannelFuture> list = this.f_9703_;
        synchronized (list) {
            ChannelFuture $$0 = ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(LocalServerChannel.class)).childHandler((ChannelHandler)new ChannelInitializer<Channel>(){

                protected void initChannel(Channel p_9734_) {
                    Connection $$1 = new Connection(PacketFlow.SERVERBOUND);
                    $$1.m_129505_(new MemoryServerHandshakePacketListenerImpl(ServerConnectionListener.this.f_9702_, $$1));
                    ServerConnectionListener.this.f_9704_.add($$1);
                    p_9734_.pipeline().addLast("packet_handler", (ChannelHandler)$$1);
                }
            }).group((EventLoopGroup)f_9698_.m_13971_()).localAddress((SocketAddress)LocalAddress.ANY)).bind().syncUninterruptibly();
            this.f_9703_.add($$0);
        }
        return $$1.channel().localAddress();
    }

    public void m_9718_() {
        this.f_9700_ = false;
        for (ChannelFuture $$0 : this.f_9703_) {
            try {
                $$0.channel().close().sync();
            }
            catch (InterruptedException $$1) {
                f_9701_.error("Interrupted whilst closing channel");
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void m_9721_() {
        List<Connection> list = this.f_9704_;
        synchronized (list) {
            Iterator<Connection> $$0 = this.f_9704_.iterator();
            while ($$0.hasNext()) {
                Connection $$1 = $$0.next();
                if ($$1.m_129537_()) continue;
                if ($$1.m_129536_()) {
                    try {
                        $$1.m_129483_();
                    }
                    catch (Exception $$2) {
                        if ($$1.m_129531_()) {
                            throw new ReportedException(CrashReport.m_127521_($$2, "Ticking memory connection"));
                        }
                        f_9701_.warn("Failed to handle packet for {}", (Object)$$1.m_129523_(), (Object)$$2);
                        MutableComponent $$3 = Component.m_237113_("Internal server error");
                        $$1.m_243124_(new ClientboundDisconnectPacket($$3), PacketSendListener.m_243092_(() -> $$1.m_129507_($$3)));
                        $$1.m_129540_();
                    }
                    continue;
                }
                $$0.remove();
                $$1.m_129541_();
            }
        }
    }

    public MinecraftServer m_9722_() {
        return this.f_9702_;
    }

    public List<Connection> m_184193_() {
        return this.f_9704_;
    }

    static class LatencySimulator
    extends ChannelInboundHandlerAdapter {
        private static final Timer f_143587_ = new HashedWheelTimer();
        private final int f_143588_;
        private final int f_143589_;
        private final List<DelayedMessage> f_143590_ = Lists.newArrayList();

        public LatencySimulator(int p_143593_, int p_143594_) {
            this.f_143588_ = p_143593_;
            this.f_143589_ = p_143594_;
        }

        public void channelRead(ChannelHandlerContext p_143601_, Object p_143602_) {
            this.m_143595_(p_143601_, p_143602_);
        }

        private void m_143595_(ChannelHandlerContext p_143596_, Object p_143597_) {
            int $$2 = this.f_143588_ + (int)(Math.random() * (double)this.f_143589_);
            this.f_143590_.add(new DelayedMessage(p_143596_, p_143597_));
            f_143587_.newTimeout(this::m_143598_, (long)$$2, TimeUnit.MILLISECONDS);
        }

        private void m_143598_(Timeout p_143599_) {
            DelayedMessage $$1 = this.f_143590_.remove(0);
            $$1.f_143603_.fireChannelRead($$1.f_143604_);
        }

        static class DelayedMessage {
            public final ChannelHandlerContext f_143603_;
            public final Object f_143604_;

            public DelayedMessage(ChannelHandlerContext p_143606_, Object p_143607_) {
                this.f_143603_ = p_143606_;
                this.f_143604_ = p_143607_;
            }
        }
    }
}

