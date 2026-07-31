/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.login;

import net.minecraft.network.protocol.game.ServerPacketListener;
import net.minecraft.network.protocol.login.ServerboundCustomQueryPacket;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.network.protocol.login.ServerboundKeyPacket;

public interface ServerLoginPacketListener
extends ServerPacketListener {
    public void m_5990_(ServerboundHelloPacket var1);

    public void m_8072_(ServerboundKeyPacket var1);

    public void m_7223_(ServerboundCustomQueryPacket var1);
}

