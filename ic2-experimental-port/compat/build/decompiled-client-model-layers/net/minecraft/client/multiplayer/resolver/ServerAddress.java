/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.net.HostAndPort
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.client.multiplayer.resolver;

import com.google.common.net.HostAndPort;
import com.mojang.logging.LogUtils;
import java.net.IDN;
import org.slf4j.Logger;

public final class ServerAddress {
    private static final Logger f_171854_ = LogUtils.getLogger();
    private final HostAndPort f_171855_;
    private static final ServerAddress f_171856_ = new ServerAddress(HostAndPort.fromParts((String)"server.invalid", (int)25565));

    public ServerAddress(String p_171861_, int p_171862_) {
        this(HostAndPort.fromParts((String)p_171861_, (int)p_171862_));
    }

    private ServerAddress(HostAndPort p_171859_) {
        this.f_171855_ = p_171859_;
    }

    public String m_171863_() {
        try {
            return IDN.toASCII(this.f_171855_.getHost());
        }
        catch (IllegalArgumentException $$0) {
            return "";
        }
    }

    public int m_171866_() {
        return this.f_171855_.getPort();
    }

    public static ServerAddress m_171864_(String p_171865_) {
        if (p_171865_ == null) {
            return f_171856_;
        }
        try {
            HostAndPort $$1 = HostAndPort.fromString((String)p_171865_).withDefaultPort(25565);
            if ($$1.getHost().isEmpty()) {
                return f_171856_;
            }
            return new ServerAddress($$1);
        }
        catch (IllegalArgumentException $$2) {
            f_171854_.info("Failed to parse URL {}", (Object)p_171865_, (Object)$$2);
            return f_171856_;
        }
    }

    public static boolean m_171867_(String p_171868_) {
        try {
            HostAndPort $$1 = HostAndPort.fromString((String)p_171868_);
            String $$2 = $$1.getHost();
            if (!$$2.isEmpty()) {
                IDN.toASCII($$2);
                return true;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        return false;
    }

    static int m_171869_(String p_171870_) {
        try {
            return Integer.parseInt(p_171870_.trim());
        }
        catch (Exception exception) {
            return 25565;
        }
    }

    public boolean equals(Object p_171872_) {
        if (this == p_171872_) {
            return true;
        }
        if (p_171872_ instanceof ServerAddress) {
            return this.f_171855_.equals((Object)((ServerAddress)p_171872_).f_171855_);
        }
        return false;
    }

    public int hashCode() {
        return this.f_171855_.hashCode();
    }
}

