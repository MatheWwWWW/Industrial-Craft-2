/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundOpenSignEditorPacket
implements Packet<ClientGamePacketListener> {
    private final BlockPos f_132630_;

    public ClientboundOpenSignEditorPacket(BlockPos p_132633_) {
        this.f_132630_ = p_132633_;
    }

    public ClientboundOpenSignEditorPacket(FriendlyByteBuf p_179013_) {
        this.f_132630_ = p_179013_.m_130135_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132642_) {
        p_132642_.m_130064_(this.f_132630_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132639_) {
        p_132639_.m_8047_(this);
    }

    public BlockPos m_132640_() {
        return this.f_132630_;
    }
}

