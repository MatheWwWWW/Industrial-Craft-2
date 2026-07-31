/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.status;

import net.minecraft.network.protocol.game.ServerPacketListener;
import net.minecraft.network.protocol.status.ServerboundPingRequestPacket;
import net.minecraft.network.protocol.status.ServerboundStatusRequestPacket;

public interface ServerStatusPacketListener
extends ServerPacketListener {
    public void m_7883_(ServerboundPingRequestPacket var1);

    public void m_6733_(ServerboundStatusRequestPacket var1);
}

