/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.commons.io.IOUtils
 */
package com.mojang.realmsclient.client;

import com.google.common.collect.Lists;
import com.mojang.realmsclient.dto.RegionPingResult;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.Util;
import org.apache.commons.io.IOUtils;

public class Ping {
    public static List<RegionPingResult> m_87130_(Region ... p_87131_) {
        for (Region $$1 : p_87131_) {
            Ping.m_87126_($$1.f_87142_);
        }
        ArrayList $$2 = Lists.newArrayList();
        for (Region $$3 : p_87131_) {
            $$2.add(new RegionPingResult($$3.f_87141_, Ping.m_87126_($$3.f_87142_)));
        }
        $$2.sort(Comparator.comparingInt(RegionPingResult::m_87652_));
        return $$2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static int m_87126_(String p_87127_) {
        int $$1 = 700;
        long $$2 = 0L;
        Socket $$3 = null;
        for (int $$4 = 0; $$4 < 5; ++$$4) {
            try {
                InetSocketAddress $$5 = new InetSocketAddress(p_87127_, 80);
                $$3 = new Socket();
                long $$6 = Ping.m_87132_();
                $$3.connect($$5, 700);
                $$2 += Ping.m_87132_() - $$6;
                IOUtils.closeQuietly((Socket)$$3);
                continue;
            }
            catch (Exception $$7) {
                $$2 += 700L;
                continue;
            }
            finally {
                IOUtils.closeQuietly($$3);
            }
        }
        return (int)((double)$$2 / 5.0);
    }

    private static long m_87132_() {
        return Util.m_137550_();
    }

    public static List<RegionPingResult> m_87125_() {
        return Ping.m_87130_(Region.values());
    }

    static final class Region
    extends Enum<Region> {
        public static final /* enum */ Region US_EAST_1 = new Region("us-east-1", "ec2.us-east-1.amazonaws.com");
        public static final /* enum */ Region US_WEST_2 = new Region("us-west-2", "ec2.us-west-2.amazonaws.com");
        public static final /* enum */ Region US_WEST_1 = new Region("us-west-1", "ec2.us-west-1.amazonaws.com");
        public static final /* enum */ Region EU_WEST_1 = new Region("eu-west-1", "ec2.eu-west-1.amazonaws.com");
        public static final /* enum */ Region AP_SOUTHEAST_1 = new Region("ap-southeast-1", "ec2.ap-southeast-1.amazonaws.com");
        public static final /* enum */ Region AP_SOUTHEAST_2 = new Region("ap-southeast-2", "ec2.ap-southeast-2.amazonaws.com");
        public static final /* enum */ Region AP_NORTHEAST_1 = new Region("ap-northeast-1", "ec2.ap-northeast-1.amazonaws.com");
        public static final /* enum */ Region SA_EAST_1 = new Region("sa-east-1", "ec2.sa-east-1.amazonaws.com");
        final String f_87141_;
        final String f_87142_;
        private static final /* synthetic */ Region[] $VALUES;

        public static Region[] values() {
            return (Region[])$VALUES.clone();
        }

        public static Region valueOf(String p_87155_) {
            return Enum.valueOf(Region.class, p_87155_);
        }

        private Region(String p_87148_, String p_87149_) {
            this.f_87141_ = p_87148_;
            this.f_87142_ = p_87149_;
        }

        private static /* synthetic */ Region[] m_167236_() {
            return new Region[]{US_EAST_1, US_WEST_2, US_WEST_1, EU_WEST_1, AP_SOUTHEAST_1, AP_SOUTHEAST_2, AP_NORTHEAST_1, SA_EAST_1};
        }

        static {
            $VALUES = Region.m_167236_();
        }
    }
}

