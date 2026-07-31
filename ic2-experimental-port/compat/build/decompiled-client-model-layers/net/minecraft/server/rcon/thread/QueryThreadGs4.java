/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.server.rcon.thread;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.rcon.NetworkDataOutputStream;
import net.minecraft.server.rcon.PktUtils;
import net.minecraft.server.rcon.thread.GenericThread;
import net.minecraft.util.RandomSource;
import org.slf4j.Logger;

public class QueryThreadGs4
extends GenericThread {
    private static final Logger f_11524_ = LogUtils.getLogger();
    private static final String f_144024_ = "SMP";
    private static final String f_144025_ = "MINECRAFT";
    private static final long f_144026_ = 30000L;
    private static final long f_144027_ = 5000L;
    private long f_11525_;
    private final int f_11526_;
    private final int f_11527_;
    private final int f_11528_;
    private final String f_11529_;
    private final String f_11530_;
    private DatagramSocket f_11531_;
    private final byte[] f_11532_ = new byte[1460];
    private String f_11533_;
    private String f_11534_;
    private final Map<SocketAddress, RequestChallenge> f_11535_;
    private final NetworkDataOutputStream f_11536_;
    private long f_11537_;
    private final ServerInterface f_11538_;

    private QueryThreadGs4(ServerInterface p_11541_, int p_11542_) {
        super("Query Listener");
        this.f_11538_ = p_11541_;
        this.f_11526_ = p_11542_;
        this.f_11534_ = p_11541_.m_6866_();
        this.f_11527_ = p_11541_.m_7448_();
        this.f_11529_ = p_11541_.m_6995_();
        this.f_11528_ = p_11541_.m_7418_();
        this.f_11530_ = p_11541_.m_7123_();
        this.f_11537_ = 0L;
        this.f_11533_ = "0.0.0.0";
        if (this.f_11534_.isEmpty() || this.f_11533_.equals(this.f_11534_)) {
            this.f_11534_ = "0.0.0.0";
            try {
                InetAddress $$2 = InetAddress.getLocalHost();
                this.f_11533_ = $$2.getHostAddress();
            }
            catch (UnknownHostException $$3) {
                f_11524_.warn("Unable to determine local host IP, please set server-ip in server.properties", (Throwable)$$3);
            }
        } else {
            this.f_11533_ = this.f_11534_;
        }
        this.f_11536_ = new NetworkDataOutputStream(1460);
        this.f_11535_ = Maps.newHashMap();
    }

    @Nullable
    public static QueryThreadGs4 m_11553_(ServerInterface p_11554_) {
        int $$1 = p_11554_.m_7913_().f_139746_;
        if (0 >= $$1 || 65535 < $$1) {
            f_11524_.warn("Invalid query port {} found in server.properties (queries disabled)", (Object)$$1);
            return null;
        }
        QueryThreadGs4 $$2 = new QueryThreadGs4(p_11554_, $$1);
        if (!$$2.m_7528_()) {
            return null;
        }
        return $$2;
    }

    private void m_11555_(byte[] p_11556_, DatagramPacket p_11557_) throws IOException {
        this.f_11531_.send(new DatagramPacket(p_11556_, p_11556_.length, p_11557_.getSocketAddress()));
    }

    private boolean m_11549_(DatagramPacket p_11550_) throws IOException {
        byte[] $$1 = p_11550_.getData();
        int $$2 = p_11550_.getLength();
        SocketAddress $$3 = p_11550_.getSocketAddress();
        f_11524_.debug("Packet len {} [{}]", (Object)$$2, (Object)$$3);
        if (3 > $$2 || -2 != $$1[0] || -3 != $$1[1]) {
            f_11524_.debug("Invalid packet [{}]", (Object)$$3);
            return false;
        }
        f_11524_.debug("Packet '{}' [{}]", (Object)PktUtils.m_11483_($$1[2]), (Object)$$3);
        switch ($$1[2]) {
            case 9: {
                this.m_11563_(p_11550_);
                f_11524_.debug("Challenge [{}]", (Object)$$3);
                return true;
            }
            case 0: {
                if (!this.m_11560_(p_11550_).booleanValue()) {
                    f_11524_.debug("Invalid challenge [{}]", (Object)$$3);
                    return false;
                }
                if (15 == $$2) {
                    this.m_11555_(this.m_11558_(p_11550_), p_11550_);
                    f_11524_.debug("Rules [{}]", (Object)$$3);
                    break;
                }
                NetworkDataOutputStream $$4 = new NetworkDataOutputStream(1460);
                $$4.m_11472_(0);
                $$4.m_11478_(this.m_11551_(p_11550_.getSocketAddress()));
                $$4.m_11474_(this.f_11529_);
                $$4.m_11474_(f_144024_);
                $$4.m_11474_(this.f_11530_);
                $$4.m_11474_(Integer.toString(this.f_11538_.m_7416_()));
                $$4.m_11474_(Integer.toString(this.f_11528_));
                $$4.m_11476_((short)this.f_11527_);
                $$4.m_11474_(this.f_11533_);
                this.m_11555_($$4.m_11471_(), p_11550_);
                f_11524_.debug("Status [{}]", (Object)$$3);
            }
        }
        return true;
    }

    private byte[] m_11558_(DatagramPacket p_11559_) throws IOException {
        String[] $$4;
        long $$1 = Util.m_137550_();
        if ($$1 < this.f_11537_ + 5000L) {
            byte[] $$2 = this.f_11536_.m_11471_();
            byte[] $$3 = this.m_11551_(p_11559_.getSocketAddress());
            $$2[1] = $$3[0];
            $$2[2] = $$3[1];
            $$2[3] = $$3[2];
            $$2[4] = $$3[3];
            return $$2;
        }
        this.f_11537_ = $$1;
        this.f_11536_.m_11480_();
        this.f_11536_.m_11472_(0);
        this.f_11536_.m_11478_(this.m_11551_(p_11559_.getSocketAddress()));
        this.f_11536_.m_11474_("splitnum");
        this.f_11536_.m_11472_(128);
        this.f_11536_.m_11472_(0);
        this.f_11536_.m_11474_("hostname");
        this.f_11536_.m_11474_(this.f_11529_);
        this.f_11536_.m_11474_("gametype");
        this.f_11536_.m_11474_(f_144024_);
        this.f_11536_.m_11474_("game_id");
        this.f_11536_.m_11474_(f_144025_);
        this.f_11536_.m_11474_("version");
        this.f_11536_.m_11474_(this.f_11538_.m_7630_());
        this.f_11536_.m_11474_("plugins");
        this.f_11536_.m_11474_(this.f_11538_.m_7138_());
        this.f_11536_.m_11474_("map");
        this.f_11536_.m_11474_(this.f_11530_);
        this.f_11536_.m_11474_("numplayers");
        this.f_11536_.m_11474_("" + this.f_11538_.m_7416_());
        this.f_11536_.m_11474_("maxplayers");
        this.f_11536_.m_11474_("" + this.f_11528_);
        this.f_11536_.m_11474_("hostport");
        this.f_11536_.m_11474_("" + this.f_11527_);
        this.f_11536_.m_11474_("hostip");
        this.f_11536_.m_11474_(this.f_11533_);
        this.f_11536_.m_11472_(0);
        this.f_11536_.m_11472_(1);
        this.f_11536_.m_11474_("player_");
        this.f_11536_.m_11472_(0);
        for (String $$5 : $$4 = this.f_11538_.m_7641_()) {
            this.f_11536_.m_11474_($$5);
        }
        this.f_11536_.m_11472_(0);
        return this.f_11536_.m_11471_();
    }

    private byte[] m_11551_(SocketAddress p_11552_) {
        return this.f_11535_.get(p_11552_).m_11578_();
    }

    private Boolean m_11560_(DatagramPacket p_11561_) {
        SocketAddress $$1 = p_11561_.getSocketAddress();
        if (!this.f_11535_.containsKey($$1)) {
            return false;
        }
        byte[] $$2 = p_11561_.getData();
        return this.f_11535_.get($$1).m_11574_() == PktUtils.m_11496_($$2, 7, p_11561_.getLength());
    }

    private void m_11563_(DatagramPacket p_11564_) throws IOException {
        RequestChallenge $$1 = new RequestChallenge(p_11564_);
        this.f_11535_.put(p_11564_.getSocketAddress(), $$1);
        this.m_11555_($$1.m_11577_(), p_11564_);
    }

    private void m_11562_() {
        if (!this.f_11515_) {
            return;
        }
        long $$0 = Util.m_137550_();
        if ($$0 < this.f_11525_ + 30000L) {
            return;
        }
        this.f_11525_ = $$0;
        this.f_11535_.values().removeIf(p_11546_ -> p_11546_.m_11575_($$0));
    }

    @Override
    public void run() {
        f_11524_.info("Query running on {}:{}", (Object)this.f_11534_, (Object)this.f_11526_);
        this.f_11525_ = Util.m_137550_();
        DatagramPacket $$0 = new DatagramPacket(this.f_11532_, this.f_11532_.length);
        try {
            while (this.f_11515_) {
                try {
                    this.f_11531_.receive($$0);
                    this.m_11562_();
                    this.m_11549_($$0);
                }
                catch (SocketTimeoutException $$1) {
                    this.m_11562_();
                }
                catch (PortUnreachableException $$1) {
                }
                catch (IOException $$2) {
                    this.m_11547_($$2);
                }
            }
        }
        finally {
            f_11524_.debug("closeSocket: {}:{}", (Object)this.f_11534_, (Object)this.f_11526_);
            this.f_11531_.close();
        }
    }

    @Override
    public boolean m_7528_() {
        if (this.f_11515_) {
            return true;
        }
        if (!this.m_11565_()) {
            return false;
        }
        return super.m_7528_();
    }

    private void m_11547_(Exception p_11548_) {
        if (!this.f_11515_) {
            return;
        }
        f_11524_.warn("Unexpected exception", (Throwable)p_11548_);
        if (!this.m_11565_()) {
            f_11524_.error("Failed to recover from exception, shutting down!");
            this.f_11515_ = false;
        }
    }

    private boolean m_11565_() {
        try {
            this.f_11531_ = new DatagramSocket(this.f_11526_, InetAddress.getByName(this.f_11534_));
            this.f_11531_.setSoTimeout(500);
            return true;
        }
        catch (Exception $$0) {
            f_11524_.warn("Unable to initialise query system on {}:{}", new Object[]{this.f_11534_, this.f_11526_, $$0});
            return false;
        }
    }

    static class RequestChallenge {
        private final long f_11567_ = new Date().getTime();
        private final int f_11568_;
        private final byte[] f_11569_;
        private final byte[] f_11570_;
        private final String f_11571_;

        public RequestChallenge(DatagramPacket p_11573_) {
            byte[] $$1 = p_11573_.getData();
            this.f_11569_ = new byte[4];
            this.f_11569_[0] = $$1[3];
            this.f_11569_[1] = $$1[4];
            this.f_11569_[2] = $$1[5];
            this.f_11569_[3] = $$1[6];
            this.f_11571_ = new String(this.f_11569_, StandardCharsets.UTF_8);
            this.f_11568_ = RandomSource.m_216327_().m_188503_(0x1000000);
            this.f_11570_ = String.format(Locale.ROOT, "\t%s%d\u0000", this.f_11571_, this.f_11568_).getBytes(StandardCharsets.UTF_8);
        }

        public Boolean m_11575_(long p_11576_) {
            return this.f_11567_ < p_11576_;
        }

        public int m_11574_() {
            return this.f_11568_;
        }

        public byte[] m_11577_() {
            return this.f_11570_;
        }

        public byte[] m_11578_() {
            return this.f_11569_;
        }

        public String m_144028_() {
            return this.f_11571_;
        }
    }
}

