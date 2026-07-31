/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundSetTimePacket
implements Packet<ClientGamePacketListener> {
    private final long f_133345_;
    private final long f_133346_;

    public ClientboundSetTimePacket(long p_133349_, long p_133350_, boolean p_133351_) {
        this.f_133345_ = p_133349_;
        long $$3 = p_133350_;
        if (!p_133351_ && ($$3 = -$$3) == 0L) {
            $$3 = -1L;
        }
        this.f_133346_ = $$3;
    }

    public ClientboundSetTimePacket(FriendlyByteBuf p_179387_) {
        this.f_133345_ = p_179387_.readLong();
        this.f_133346_ = p_179387_.readLong();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133360_) {
        p_133360_.writeLong(this.f_133345_);
        p_133360_.writeLong(this.f_133346_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133357_) {
        p_133357_.m_7885_(this);
    }

    public long m_133358_() {
        return this.f_133345_;
    }

    public long m_133361_() {
        return this.f_133346_;
    }
}

