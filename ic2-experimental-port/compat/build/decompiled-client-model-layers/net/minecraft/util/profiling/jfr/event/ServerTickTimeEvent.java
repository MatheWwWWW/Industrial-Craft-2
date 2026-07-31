/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling.jfr.event;

import jdk.jfr.Category;
import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.Period;
import jdk.jfr.StackTrace;
import jdk.jfr.Timespan;
import net.minecraft.obfuscate.DontObfuscate;

@Name(value="minecraft.ServerTickTime")
@Label(value="Server Tick Time")
@Category(value={"Minecraft", "Ticking"})
@StackTrace(value=false)
@Period(value="1 s")
@DontObfuscate
public class ServerTickTimeEvent
extends Event {
    public static final String f_195597_ = "minecraft.ServerTickTime";
    public static final EventType f_195598_ = EventType.getEventType(ServerTickTimeEvent.class);
    @Name(value="averageTickDuration")
    @Label(value="Average Server Tick Duration")
    @Timespan
    public final long f_195599_;

    public ServerTickTimeEvent(float p_195602_) {
        this.f_195599_ = (long)(1000000.0f * p_195602_);
    }

    public static class Fields {
        public static final String f_195603_ = "averageTickDuration";

        private Fields() {
        }
    }
}

