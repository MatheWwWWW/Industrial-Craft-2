/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.status;

import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;
import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;

public interface ClientStatusPacketListener
extends PacketListener {
    public void m_6440_(ClientboundStatusResponsePacket var1);

    public void m_7017_(ClientboundPongResponsePacket var1);
}

