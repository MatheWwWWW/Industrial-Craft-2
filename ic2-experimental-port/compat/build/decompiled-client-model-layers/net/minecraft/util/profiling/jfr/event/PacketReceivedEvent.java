/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling.jfr.event;

import java.net.SocketAddress;
import jdk.jfr.EventType;
import jdk.jfr.Label;
import jdk.jfr.Name;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.util.profiling.jfr.event.PacketEvent;

@Name(value="minecraft.PacketReceived")
@Label(value="Network Packet Received")
@DontObfuscate
public class PacketReceivedEvent
extends PacketEvent {
    public static final String f_195581_ = "minecraft.PacketReceived";
    public static final EventType f_195582_ = EventType.getEventType(PacketReceivedEvent.class);

    public PacketReceivedEvent(int p_195585_, int p_195586_, SocketAddress p_195587_, int p_195588_) {
        super(p_195585_, p_195586_, p_195587_, p_195588_);
    }
}

