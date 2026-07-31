/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundEntityTagQuery
implements Packet<ServerGamePacketListener> {
    private final int f_134014_;
    private final int f_134015_;

    public ServerboundEntityTagQuery(int p_134018_, int p_134019_) {
        this.f_134014_ = p_134018_;
        this.f_134015_ = p_134019_;
    }

    public ServerboundEntityTagQuery(FriendlyByteBuf p_179594_) {
        this.f_134014_ = p_179594_.m_130242_();
        this.f_134015_ = p_179594_.m_130242_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134028_) {
        p_134028_.m_130130_(this.f_134014_);
        p_134028_.m_130130_(this.f_134015_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134025_) {
        p_134025_.m_7548_(this);
    }

    public int m_134026_() {
        return this.f_134014_;
    }

    public int m_134029_() {
        return this.f_134015_;
    }
}

