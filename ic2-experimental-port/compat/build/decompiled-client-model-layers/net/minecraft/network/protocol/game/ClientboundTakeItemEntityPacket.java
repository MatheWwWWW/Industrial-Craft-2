/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundTakeItemEntityPacket
implements Packet<ClientGamePacketListener> {
    private final int f_133510_;
    private final int f_133511_;
    private final int f_133512_;

    public ClientboundTakeItemEntityPacket(int p_133515_, int p_133516_, int p_133517_) {
        this.f_133510_ = p_133515_;
        this.f_133511_ = p_133516_;
        this.f_133512_ = p_133517_;
    }

    public ClientboundTakeItemEntityPacket(FriendlyByteBuf p_179435_) {
        this.f_133510_ = p_179435_.m_130242_();
        this.f_133511_ = p_179435_.m_130242_();
        this.f_133512_ = p_179435_.m_130242_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133526_) {
        p_133526_.m_130130_(this.f_133510_);
        p_133526_.m_130130_(this.f_133511_);
        p_133526_.m_130130_(this.f_133512_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133523_) {
        p_133523_.m_8001_(this);
    }

    public int m_133524_() {
        return this.f_133510_;
    }

    public int m_133527_() {
        return this.f_133511_;
    }

    public int m_133528_() {
        return this.f_133512_;
    }
}

