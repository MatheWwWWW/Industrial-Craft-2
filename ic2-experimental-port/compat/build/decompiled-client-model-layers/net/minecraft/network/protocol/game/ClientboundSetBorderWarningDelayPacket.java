/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.border.WorldBorder;

public class ClientboundSetBorderWarningDelayPacket
implements Packet<ClientGamePacketListener> {
    private final int f_179253_;

    public ClientboundSetBorderWarningDelayPacket(WorldBorder p_179255_) {
        this.f_179253_ = p_179255_.m_61967_();
    }

    public ClientboundSetBorderWarningDelayPacket(FriendlyByteBuf p_179257_) {
        this.f_179253_ = p_179257_.m_130242_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_179259_) {
        p_179259_.m_130130_(this.f_179253_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_179263_) {
        p_179263_.m_142056_(this);
    }

    public int m_179264_() {
        return this.f_179253_;
    }
}

