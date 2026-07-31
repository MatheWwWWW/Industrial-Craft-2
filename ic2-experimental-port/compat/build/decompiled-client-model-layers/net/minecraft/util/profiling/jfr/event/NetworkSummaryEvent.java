/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling.jfr.event;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import jdk.jfr.Category;
import jdk.jfr.DataAmount;
import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.Period;
import jdk.jfr.StackTrace;
import net.minecraft.obfuscate.DontObfuscate;

@Name(value="minecraft.NetworkSummary")
@Label(value="Network Summary")
@Category(value={"Minecraft", "Network"})
@StackTrace(value=false)
@Period(value="10 s")
@DontObfuscate
public class NetworkSummaryEvent
extends Event {
    public static final String f_195553_ = "minecraft.NetworkSummary";
    public static final EventType f_195554_ = EventType.getEventType(NetworkSummaryEvent.class);
    @Name(value="remoteAddress")
    @Label(value="Remote Address")
    public final String f_195557_;
    @Name(value="sentBytes")
    @Label(value="Sent Bytes")
    @DataAmount
    public long f_195558_;
    @Name(value="sentPackets")
    @Label(value="Sent Packets")
    public int f_195559_;
    @Name(value="receivedBytes")
    @Label(value="Received Bytes")
    @DataAmount
    public long f_195555_;
    @Name(value="receivedPackets")
    @Label(value="Received Packets")
    public int f_195556_;

    public NetworkSummaryEvent(String p_195562_) {
        this.f_195557_ = p_195562_;
    }

    public static final class SumAggregation {
        private final AtomicLong f_195569_ = new AtomicLong();
        private final AtomicInteger f_195570_ = new AtomicInteger();
        private final AtomicLong f_195571_ = new AtomicLong();
        private final AtomicInteger f_195572_ = new AtomicInteger();
        private final NetworkSummaryEvent f_195573_;

        public SumAggregation(String p_195575_) {
            this.f_195573_ = new NetworkSummaryEvent(p_195575_);
            this.f_195573_.begin();
        }

        public void m_195577_(int p_195578_) {
            this.f_195570_.incrementAndGet();
            this.f_195569_.addAndGet(p_195578_);
        }

        public void m_195579_(int p_195580_) {
            this.f_195572_.incrementAndGet();
            this.f_195571_.addAndGet(p_195580_);
        }

        public void m_195576_() {
            this.f_195573_.f_195558_ = this.f_195569_.get();
            this.f_195573_.f_195559_ = this.f_195570_.get();
            this.f_195573_.f_195555_ = this.f_195571_.get();
            this.f_195573_.f_195556_ = this.f_195572_.get();
            this.f_195573_.commit();
        }
    }

    public static final class Fields {
        public static final String f_195563_ = "remoteAddress";
        public static final String f_195564_ = "sentBytes";
        private static final String f_195566_ = "sentPackets";
        public static final String f_195565_ = "receivedBytes";
        private static final String f_195567_ = "receivedPackets";

        private Fields() {
        }
    }
}

