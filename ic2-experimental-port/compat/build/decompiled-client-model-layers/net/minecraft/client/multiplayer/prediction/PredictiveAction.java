/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.multiplayer.prediction;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

@FunctionalInterface
public interface PredictiveAction {
    public Packet<ServerGamePacketListener> m_233885_(int var1);
}

