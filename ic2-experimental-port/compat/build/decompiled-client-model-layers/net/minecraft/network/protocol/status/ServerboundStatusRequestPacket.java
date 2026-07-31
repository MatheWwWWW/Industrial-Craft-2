/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.status;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.status.ServerStatusPacketListener;

public class ServerboundStatusRequestPacket
implements Packet<ServerStatusPacketListener> {
    public ServerboundStatusRequestPacket() {
    }

    public ServerboundStatusRequestPacket(FriendlyByteBuf p_179840_) {
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_135009_) {
    }

    @Override
    public void m_5797_(ServerStatusPacketListener p_135007_) {
        p_135007_.m_6733_(this);
    }
}

