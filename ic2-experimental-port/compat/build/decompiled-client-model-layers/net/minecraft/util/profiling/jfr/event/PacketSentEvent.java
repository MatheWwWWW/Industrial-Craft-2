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

@Name(value="minecraft.PacketSent")
@Label(value="Network Packet Sent")
@DontObfuscate
public class PacketSentEvent
extends PacketEvent {
    public static final String f_195589_ = "minecraft.PacketSent";
    public static final EventType f_195590_ = EventType.getEventType(PacketSentEvent.class);

    public PacketSentEvent(int p_195593_, int p_195594_, SocketAddress p_195595_, int p_195596_) {
        super(p_195593_, p_195594_, p_195595_, p_195596_);
    }
}

