/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client.server;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.client.server.LanServer;
import net.minecraft.client.server.LanServerPinger;
import org.slf4j.Logger;

public class LanServerDetection {
    static final AtomicInteger f_120081_ = new AtomicInteger(0);
    static final Logger f_120082_ = LogUtils.getLogger();

    public static class LanServerDetector
    extends Thread {
        private final LanServerList f_120086_;
        private final InetAddress f_120087_;
        private final MulticastSocket f_120088_;

        public LanServerDetector(LanServerList p_120090_) throws IOException {
            super("LanServerDetector #" + f_120081_.incrementAndGet());
            this.f_120086_ = p_120090_;
            this.setDaemon(true);
            this.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_120082_));
            this.f_120088_ = new MulticastSocket(4445);
            this.f_120087_ = InetAddress.getByName("224.0.2.60");
            this.f_120088_.setSoTimeout(5000);
            this.f_120088_.joinGroup(this.f_120087_);
        }

        @Override
        public void run() {
            byte[] $$0 = new byte[1024];
            while (!this.isInterrupted()) {
                DatagramPacket $$1 = new DatagramPacket($$0, $$0.length);
                try {
                    this.f_120088_.receive($$1);
                }
                catch (SocketTimeoutException $$2) {
                    continue;
                }
                catch (IOException $$3) {
                    f_120082_.error("Couldn't ping server", (Throwable)$$3);
                    break;
                }
                String $$4 = new String($$1.getData(), $$1.getOffset(), $$1.getLength(), StandardCharsets.UTF_8);
                f_120082_.debug("{}: {}", (Object)$$1.getAddress(), (Object)$$4);
                this.f_120086_.m_120096_($$4, $$1.getAddress());
            }
            try {
                this.f_120088_.leaveGroup(this.f_120087_);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            this.f_120088_.close();
        }
    }

    public static class LanServerList {
        private final List<LanServer> f_120092_ = Lists.newArrayList();
        private boolean f_120093_;

        public synchronized boolean m_120095_() {
            return this.f_120093_;
        }

        public synchronized void m_120099_() {
            this.f_120093_ = false;
        }

        public synchronized List<LanServer> m_120100_() {
            return Collections.unmodifiableList(this.f_120092_);
        }

        public synchronized void m_120096_(String p_120097_, InetAddress p_120098_) {
            String $$2 = LanServerPinger.m_120111_(p_120097_);
            Object $$3 = LanServerPinger.m_120116_(p_120097_);
            if ($$3 == null) {
                return;
            }
            $$3 = p_120098_.getHostAddress() + ":" + (String)$$3;
            boolean $$4 = false;
            for (LanServer $$5 : this.f_120092_) {
                if (!$$5.m_120079_().equals($$3)) continue;
                $$5.m_120080_();
                $$4 = true;
                break;
            }
            if (!$$4) {
                this.f_120092_.add(new LanServer($$2, (String)$$3));
                this.f_120093_ = true;
            }
        }
    }
}

