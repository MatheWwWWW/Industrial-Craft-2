/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.login;

import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.login.ClientboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
import net.minecraft.network.protocol.login.ClientboundHelloPacket;
import net.minecraft.network.protocol.login.ClientboundLoginCompressionPacket;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;

public interface ClientLoginPacketListener
extends PacketListener {
    public void m_7318_(ClientboundHelloPacket var1);

    public void m_7056_(ClientboundGameProfilePacket var1);

    public void m_5800_(ClientboundLoginDisconnectPacket var1);

    public void m_5693_(ClientboundLoginCompressionPacket var1);

    public void m_7254_(ClientboundCustomQueryPacket var1);
}

