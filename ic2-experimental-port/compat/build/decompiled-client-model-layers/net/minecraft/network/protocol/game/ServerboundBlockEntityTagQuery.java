/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundBlockEntityTagQuery
implements Packet<ServerGamePacketListener> {
    private final int f_133798_;
    private final BlockPos f_133799_;

    public ServerboundBlockEntityTagQuery(int p_133802_, BlockPos p_133803_) {
        this.f_133798_ = p_133802_;
        this.f_133799_ = p_133803_;
    }

    public ServerboundBlockEntityTagQuery(FriendlyByteBuf p_179540_) {
        this.f_133798_ = p_179540_.m_130242_();
        this.f_133799_ = p_179540_.m_130135_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133812_) {
        p_133812_.m_130130_(this.f_133798_);
        p_133812_.m_130064_(this.f_133799_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_133809_) {
        p_133809_.m_6780_(this);
    }

    public int m_133810_() {
        return this.f_133798_;
    }

    public BlockPos m_133813_() {
        return this.f_133799_;
    }
}

