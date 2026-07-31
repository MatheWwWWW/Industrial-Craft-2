/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundBlockDestructionPacket
implements Packet<ClientGamePacketListener> {
    private final int f_131671_;
    private final BlockPos f_131672_;
    private final int f_131673_;

    public ClientboundBlockDestructionPacket(int p_131676_, BlockPos p_131677_, int p_131678_) {
        this.f_131671_ = p_131676_;
        this.f_131672_ = p_131677_;
        this.f_131673_ = p_131678_;
    }

    public ClientboundBlockDestructionPacket(FriendlyByteBuf p_178606_) {
        this.f_131671_ = p_178606_.m_130242_();
        this.f_131672_ = p_178606_.m_130135_();
        this.f_131673_ = p_178606_.readUnsignedByte();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_131687_) {
        p_131687_.m_130130_(this.f_131671_);
        p_131687_.m_130064_(this.f_131672_);
        p_131687_.writeByte(this.f_131673_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_131684_) {
        p_131684_.m_5943_(this);
    }

    public int m_131685_() {
        return this.f_131671_;
    }

    public BlockPos m_131688_() {
        return this.f_131672_;
    }

    public int m_131689_() {
        return this.f_131673_;
    }
}

