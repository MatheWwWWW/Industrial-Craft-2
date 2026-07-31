/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.mojang.authlib.GameProfile
 *  com.mojang.logging.LogUtils
 *  io.netty.bootstrap.Bootstrap
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.Unpooled
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelException
 *  io.netty.channel.ChannelFutureListener
 *  io.netty.channel.ChannelHandler
 *  io.netty.channel.ChannelHandlerContext
 *  io.netty.channel.ChannelInitializer
 *  io.netty.channel.ChannelOption
 *  io.netty.channel.EventLoopGroup
 *  io.netty.channel.SimpleChannelInboundHandler
 *  io.netty.channel.socket.nio.NioSocketChannel
 *  io.netty.util.concurrent.GenericFutureListener
 *  org.slf4j.Logger
 */
package net.minecraft.client.multiplayer;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ResolvedServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.multiplayer.resolver.ServerNameResolver;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.status.ClientStatusPacketListener;
import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;
import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.network.protocol.status.ServerboundPingRequestPacket;
import net.minecraft.network.protocol.status.ServerboundStatusRequestPacket;
import net.minecraft.util.Mth;
import org.slf4j.Logger;

public class ServerStatusPinger {
    static final Splitter f_105448_ = Splitter.on((char)'\u0000').limit(6);
    static final Logger f_105449_ = LogUtils.getLogger();
    private static final Component f_171810_ = Component.m_237115_("multiplayer.status.cannot_connect").m_130940_(ChatFormatting.DARK_RED);
    private final List<Connection> f_105450_ = Collections.synchronizedList(Lists.newArrayList());

    public void m_105459_(final ServerData p_105460_, final Runnable p_105461_) throws UnknownHostException {
        ServerAddress $$2 = ServerAddress.m_171864_(p_105460_.f_105363_);
        Optional<InetSocketAddress> $$3 = ServerNameResolver.f_171881_.m_171890_($$2).map(ResolvedServerAddress::m_142641_);
        if (!$$3.isPresent()) {
            this.m_171814_(ConnectScreen.f_169260_, p_105460_);
            return;
        }
        final InetSocketAddress $$4 = $$3.get();
        final Connection $$5 = Connection.m_178300_($$4, false);
        this.f_105450_.add($$5);
        p_105460_.f_105365_ = Component.m_237115_("multiplayer.status.pinging");
        p_105460_.f_105366_ = -1L;
        p_105460_.f_105370_ = null;
        $$5.m_129505_(new ClientStatusPacketListener(){
            private boolean f_105475_;
            private boolean f_105476_;
            private long f_105477_;

            @Override
            public void m_6440_(ClientboundStatusResponsePacket p_105489_) {
                if (this.f_105476_) {
                    $$5.m_129507_(Component.m_237115_("multiplayer.status.unrequested"));
                    return;
                }
                this.f_105476_ = true;
                ServerStatus $$1 = p_105489_.m_134897_();
                p_105460_.f_105365_ = $$1.m_134905_() != null ? $$1.m_134905_() : CommonComponents.f_237098_;
                if ($$1.m_134915_() != null) {
                    p_105460_.f_105368_ = Component.m_237113_($$1.m_134915_().m_134967_());
                    p_105460_.f_105367_ = $$1.m_134915_().m_134968_();
                } else {
                    p_105460_.f_105368_ = Component.m_237115_("multiplayer.status.old");
                    p_105460_.f_105367_ = 0;
                }
                if ($$1.m_134914_() != null) {
                    p_105460_.f_105364_ = ServerStatusPinger.m_105466_($$1.m_134914_().m_134926_(), $$1.m_134914_().m_134923_());
                    ArrayList $$2 = Lists.newArrayList();
                    GameProfile[] $$3 = $$1.m_134914_().m_134927_();
                    if ($$3 != null && $$3.length > 0) {
                        for (GameProfile $$42 : $$3) {
                            $$2.add(Component.m_237113_($$42.getName()));
                        }
                        if ($$3.length < $$1.m_134914_().m_134926_()) {
                            $$2.add(Component.m_237110_("multiplayer.status.and_more", $$1.m_134914_().m_134926_() - $$3.length));
                        }
                        p_105460_.f_105370_ = $$2;
                    }
                } else {
                    p_105460_.f_105364_ = Component.m_237115_("multiplayer.status.unknown").m_130940_(ChatFormatting.DARK_GRAY);
                }
                String $$52 = $$1.m_134916_();
                if ($$52 != null) {
                    try {
                        $$52 = ServerData.m_233808_($$52);
                    }
                    catch (ParseException $$6) {
                        f_105449_.error("Invalid server icon", (Throwable)$$6);
                    }
                }
                if (!Objects.equals($$52, p_105460_.m_105388_())) {
                    p_105460_.m_105383_($$52);
                    p_105461_.run();
                }
                this.f_105477_ = Util.m_137550_();
                $$5.m_129512_(new ServerboundPingRequestPacket(this.f_105477_));
                this.f_105475_ = true;
            }

            @Override
            public void m_7017_(ClientboundPongResponsePacket p_105487_) {
                long $$1 = this.f_105477_;
                long $$2 = Util.m_137550_();
                p_105460_.f_105366_ = $$2 - $$1;
                $$5.m_129507_(Component.m_237115_("multiplayer.status.finished"));
            }

            @Override
            public void m_7026_(Component p_105485_) {
                if (!this.f_105475_) {
                    ServerStatusPinger.this.m_171814_(p_105485_, p_105460_);
                    ServerStatusPinger.this.m_171811_($$4, p_105460_);
                }
            }

            @Override
            public Connection m_6198_() {
                return $$5;
            }
        });
        try {
            $$5.m_129512_(new ClientIntentionPacket($$2.m_171863_(), $$2.m_171866_(), ConnectionProtocol.STATUS));
            $$5.m_129512_(new ServerboundStatusRequestPacket());
        }
        catch (Throwable $$6) {
            f_105449_.error("Failed to ping server {}", (Object)$$2, (Object)$$6);
        }
    }

    void m_171814_(Component p_171815_, ServerData p_171816_) {
        f_105449_.error("Can't ping {}: {}", (Object)p_171816_.f_105363_, (Object)p_171815_.getString());
        p_171816_.f_105365_ = f_171810_;
        p_171816_.f_105364_ = CommonComponents.f_237098_;
    }

    void m_171811_(final InetSocketAddress p_171812_, final ServerData p_171813_) {
        ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)Connection.f_129462_.m_13971_())).handler((ChannelHandler)new ChannelInitializer<Channel>(){

            protected void initChannel(Channel p_105498_) {
                try {
                    p_105498_.config().setOption(ChannelOption.TCP_NODELAY, (Object)true);
                }
                catch (ChannelException channelException) {
                    // empty catch block
                }
                p_105498_.pipeline().addLast(new ChannelHandler[]{new SimpleChannelInboundHandler<ByteBuf>(){

                    /*
                     * WARNING - Removed try catching itself - possible behaviour change.
                     */
                    public void channelActive(ChannelHandlerContext p_105506_) throws Exception {
                        super.channelActive(p_105506_);
                        ByteBuf $$1 = Unpooled.buffer();
                        try {
                            $$1.writeByte(254);
                            $$1.writeByte(1);
                            $$1.writeByte(250);
                            char[] $$2 = "MC|PingHost".toCharArray();
                            $$1.writeShort($$2.length);
                            for (char $$3 : $$2) {
                                $$1.writeChar((int)$$3);
                            }
                            $$1.writeShort(7 + 2 * p_171812_.getHostName().length());
                            $$1.writeByte(127);
                            $$2 = p_171812_.getHostName().toCharArray();
                            $$1.writeShort($$2.length);
                            for (char $$4 : $$2) {
                                $$1.writeChar((int)$$4);
                            }
                            $$1.writeInt(p_171812_.getPort());
                            p_105506_.channel().writeAndFlush((Object)$$1).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
                        }
                        finally {
                            $$1.release();
                        }
                    }

                    protected void channelRead0(ChannelHandlerContext p_105503_, ByteBuf p_105504_) {
                        String $$3;
                        String[] $$4;
                        short $$2 = p_105504_.readUnsignedByte();
                        if ($$2 == 255 && "\u00a71".equals(($$4 = (String[])Iterables.toArray((Iterable)f_105448_.split((CharSequence)($$3 = new String(p_105504_.readBytes(p_105504_.readShort() * 2).array(), StandardCharsets.UTF_16BE))), String.class))[0])) {
                            int $$5 = Mth.m_14059_($$4[1], 0);
                            String $$6 = $$4[2];
                            String $$7 = $$4[3];
                            int $$8 = Mth.m_14059_($$4[4], -1);
                            int $$9 = Mth.m_14059_($$4[5], -1);
                            p_171813_.f_105367_ = -1;
                            p_171813_.f_105368_ = Component.m_237113_($$6);
                            p_171813_.f_105365_ = Component.m_237113_($$7);
                            p_171813_.f_105364_ = ServerStatusPinger.m_105466_($$8, $$9);
                        }
                        p_105503_.close();
                    }

                    public void exceptionCaught(ChannelHandlerContext p_105511_, Throwable p_105512_) {
                        p_105511_.close();
                    }

                    protected /* synthetic */ void channelRead0(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
                        this.channelRead0(channelHandlerContext, (ByteBuf)object);
                    }
                }});
            }
        })).channel(NioSocketChannel.class)).connect(p_171812_.getAddress(), p_171812_.getPort());
    }

    static Component m_105466_(int p_105467_, int p_105468_) {
        return Component.m_237113_(Integer.toString(p_105467_)).m_7220_(Component.m_237113_("/").m_130940_(ChatFormatting.DARK_GRAY)).m_130946_(Integer.toString(p_105468_)).m_130940_(ChatFormatting.GRAY);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void m_105453_() {
        List<Connection> list = this.f_105450_;
        synchronized (list) {
            Iterator<Connection> $$0 = this.f_105450_.iterator();
            while ($$0.hasNext()) {
                Connection $$1 = $$0.next();
                if ($$1.m_129536_()) {
                    $$1.m_129483_();
                    continue;
                }
                $$0.remove();
                $$1.m_129541_();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void m_105465_() {
        List<Connection> list = this.f_105450_;
        synchronized (list) {
            Iterator<Connection> $$0 = this.f_105450_.iterator();
            while ($$0.hasNext()) {
                Connection $$1 = $$0.next();
                if (!$$1.m_129536_()) continue;
                $$0.remove();
                $$1.m_129507_(Component.m_237115_("multiplayer.status.cancelled"));
            }
        }
    }
}

