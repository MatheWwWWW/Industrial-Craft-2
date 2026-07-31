/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client.server;

import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.DefaultUncaughtExceptionHandler;
import org.slf4j.Logger;

public class LanServerPinger
extends Thread {
    private static final AtomicInteger f_120101_ = new AtomicInteger(0);
    private static final Logger f_120102_ = LogUtils.getLogger();
    public static final String f_174974_ = "224.0.2.60";
    public static final int f_174975_ = 4445;
    private static final long f_174976_ = 1500L;
    private final String f_120103_;
    private final DatagramSocket f_120104_;
    private boolean f_120105_ = true;
    private final String f_120106_;

    public LanServerPinger(String p_120109_, String p_120110_) throws IOException {
        super("LanServerPinger #" + f_120101_.incrementAndGet());
        this.f_120103_ = p_120109_;
        this.f_120106_ = p_120110_;
        this.setDaemon(true);
        this.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_120102_));
        this.f_120104_ = new DatagramSocket();
    }

    @Override
    public void run() {
        String $$0 = LanServerPinger.m_120113_(this.f_120103_, this.f_120106_);
        byte[] $$1 = $$0.getBytes(StandardCharsets.UTF_8);
        while (!this.isInterrupted() && this.f_120105_) {
            try {
                InetAddress $$2 = InetAddress.getByName(f_174974_);
                DatagramPacket $$3 = new DatagramPacket($$1, $$1.length, $$2, 4445);
                this.f_120104_.send($$3);
            }
            catch (IOException $$4) {
                f_120102_.warn("LanServerPinger: {}", (Object)$$4.getMessage());
                break;
            }
            try {
                LanServerPinger.sleep(1500L);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        this.f_120105_ = false;
    }

    public static String m_120113_(String p_120114_, String p_120115_) {
        return "[MOTD]" + p_120114_ + "[/MOTD][AD]" + p_120115_ + "[/AD]";
    }

    public static String m_120111_(String p_120112_) {
        int $$1 = p_120112_.indexOf("[MOTD]");
        if ($$1 < 0) {
            return "missing no";
        }
        int $$2 = p_120112_.indexOf("[/MOTD]", $$1 + "[MOTD]".length());
        if ($$2 < $$1) {
            return "missing no";
        }
        return p_120112_.substring($$1 + "[MOTD]".length(), $$2);
    }

    public static String m_120116_(String p_120117_) {
        int $$1 = p_120117_.indexOf("[/MOTD]");
        if ($$1 < 0) {
            return null;
        }
        int $$2 = p_120117_.indexOf("[/MOTD]", $$1 + "[/MOTD]".length());
        if ($$2 >= 0) {
            return null;
        }
        int $$3 = p_120117_.indexOf("[AD]", $$1 + "[/MOTD]".length());
        if ($$3 < 0) {
            return null;
        }
        int $$4 = p_120117_.indexOf("[/AD]", $$3 + "[AD]".length());
        if ($$4 < $$3) {
            return null;
        }
        return p_120117_.substring($$3 + "[AD]".length(), $$4);
    }
}

