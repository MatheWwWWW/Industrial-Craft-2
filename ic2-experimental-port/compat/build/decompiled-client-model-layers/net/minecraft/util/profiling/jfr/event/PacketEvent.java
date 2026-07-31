/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling.jfr.event;

import java.net.SocketAddress;
import jdk.jfr.Category;
import jdk.jfr.DataAmount;
import jdk.jfr.Enabled;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.StackTrace;

@Category(value={"Minecraft", "Network"})
@StackTrace(value=false)
@Enabled(value=false)
public abstract class PacketEvent
extends Event {
    @Name(value="protocolId")
    @Label(value="Protocol Id")
    public final int f_185416_;
    @Name(value="packetId")
    @Label(value="Packet Id")
    public final int f_185415_;
    @Name(value="remoteAddress")
    @Label(value="Remote Address")
    public final String f_185417_;
    @Name(value="bytes")
    @Label(value="Bytes")
    @DataAmount
    public final int f_185414_;

    public PacketEvent(int p_185419_, int p_185420_, SocketAddress p_185421_, int p_185422_) {
        this.f_185416_ = p_185419_;
        this.f_185415_ = p_185420_;
        this.f_185417_ = p_185421_.toString();
        this.f_185414_ = p_185422_;
    }

    public static final class Fields {
        public static final String f_185423_ = "remoteAddress";
        public static final String f_185424_ = "protocolId";
        public static final String f_185425_ = "packetId";
        public static final String f_185426_ = "bytes";

        private Fields() {
        }
    }
}

