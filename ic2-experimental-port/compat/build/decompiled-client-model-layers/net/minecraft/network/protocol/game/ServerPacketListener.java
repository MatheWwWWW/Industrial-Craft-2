/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketListener;

public interface ServerPacketListener
extends PacketListener {
    @Override
    default public boolean m_201767_() {
        return false;
    }
}

