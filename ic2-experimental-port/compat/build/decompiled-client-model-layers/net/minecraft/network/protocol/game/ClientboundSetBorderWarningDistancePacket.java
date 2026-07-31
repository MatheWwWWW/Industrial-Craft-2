/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.border.WorldBorder;

public class ClientboundSetBorderWarningDistancePacket
implements Packet<ClientGamePacketListener> {
    private final int f_179265_;

    public ClientboundSetBorderWarningDistancePacket(WorldBorder p_179267_) {
        this.f_179265_ = p_179267_.m_61968_();
    }

    public ClientboundSetBorderWarningDistancePacket(FriendlyByteBuf p_179269_) {
        this.f_179265_ = p_179269_.m_130242_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_179271_) {
        p_179271_.m_130130_(this.f_179265_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_179275_) {
        p_179275_.m_142696_(this);
    }

    public int m_179276_() {
        return this.f_179265_;
    }
}

