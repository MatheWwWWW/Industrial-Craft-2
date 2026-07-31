/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.rcon.thread;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.rcon.thread.GenericThread;
import net.minecraft.server.rcon.thread.RconClient;
import org.slf4j.Logger;

public class RconThread
extends GenericThread {
    private static final Logger f_11601_ = LogUtils.getLogger();
    private final ServerSocket f_11602_;
    private final String f_11603_;
    private final List<RconClient> f_11604_ = Lists.newArrayList();
    private final ServerInterface f_11605_;

    private RconThread(ServerInterface p_11608_, ServerSocket p_11609_, String p_11610_) {
        super("RCON Listener");
        this.f_11605_ = p_11608_;
        this.f_11602_ = p_11609_;
        this.f_11603_ = p_11610_;
    }

    private void m_11618_() {
        this.f_11604_.removeIf(p_11612_ -> !p_11612_.m_11523_());
    }

    @Override
    public void run() {
        try {
            while (this.f_11515_) {
                try {
                    Socket $$0 = this.f_11602_.accept();
                    RconClient $$1 = new RconClient(this.f_11605_, this.f_11603_, $$0);
                    $$1.m_7528_();
                    this.f_11604_.add($$1);
                    this.m_11618_();
                }
                catch (SocketTimeoutException $$2) {
                    this.m_11618_();
                }
                catch (IOException $$3) {
                    if (!this.f_11515_) continue;
                    f_11601_.info("IO exception: ", (Throwable)$$3);
                }
            }
        }
        finally {
            this.m_11613_(this.f_11602_);
        }
    }

    @Nullable
    public static RconThread m_11615_(ServerInterface p_11616_) {
        int $$3;
        DedicatedServerProperties $$1 = p_11616_.m_7913_();
        String $$2 = p_11616_.m_6866_();
        if ($$2.isEmpty()) {
            $$2 = "0.0.0.0";
        }
        if (0 >= ($$3 = $$1.f_139748_) || 65535 < $$3) {
            f_11601_.warn("Invalid rcon port {} found in server.properties, rcon disabled!", (Object)$$3);
            return null;
        }
        String $$4 = $$1.f_139749_;
        if ($$4.isEmpty()) {
            f_11601_.warn("No rcon password set in server.properties, rcon disabled!");
            return null;
        }
        try {
            ServerSocket $$5 = new ServerSocket($$3, 0, InetAddress.getByName($$2));
            $$5.setSoTimeout(500);
            RconThread $$6 = new RconThread(p_11616_, $$5, $$4);
            if (!$$6.m_7528_()) {
                return null;
            }
            f_11601_.info("RCON running on {}:{}", (Object)$$2, (Object)$$3);
            return $$6;
        }
        catch (IOException $$7) {
            f_11601_.warn("Unable to initialise RCON on {}:{}", new Object[]{$$2, $$3, $$7});
            return null;
        }
    }

    @Override
    public void m_7530_() {
        this.f_11515_ = false;
        this.m_11613_(this.f_11602_);
        super.m_7530_();
        for (RconClient $$0 : this.f_11604_) {
            if (!$$0.m_11523_()) continue;
            $$0.m_7530_();
        }
        this.f_11604_.clear();
    }

    private void m_11613_(ServerSocket p_11614_) {
        f_11601_.debug("closeSocket: {}", (Object)p_11614_);
        try {
            p_11614_.close();
        }
        catch (IOException $$1) {
            f_11601_.warn("Failed to close socket", (Throwable)$$1);
        }
    }
}

