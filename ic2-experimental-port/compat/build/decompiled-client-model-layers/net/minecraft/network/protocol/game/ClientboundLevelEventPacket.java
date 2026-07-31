/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundLevelEventPacket
implements Packet<ClientGamePacketListener> {
    private final int f_132258_;
    private final BlockPos f_132259_;
    private final int f_132260_;
    private final boolean f_132261_;

    public ClientboundLevelEventPacket(int p_132264_, BlockPos p_132265_, int p_132266_, boolean p_132267_) {
        this.f_132258_ = p_132264_;
        this.f_132259_ = p_132265_.m_7949_();
        this.f_132260_ = p_132266_;
        this.f_132261_ = p_132267_;
    }

    public ClientboundLevelEventPacket(FriendlyByteBuf p_178908_) {
        this.f_132258_ = p_178908_.readInt();
        this.f_132259_ = p_178908_.m_130135_();
        this.f_132260_ = p_178908_.readInt();
        this.f_132261_ = p_178908_.readBoolean();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132276_) {
        p_132276_.writeInt(this.f_132258_);
        p_132276_.m_130064_(this.f_132259_);
        p_132276_.writeInt(this.f_132260_);
        p_132276_.writeBoolean(this.f_132261_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132273_) {
        p_132273_.m_7704_(this);
    }

    public boolean m_132274_() {
        return this.f_132261_;
    }

    public int m_132277_() {
        return this.f_132258_;
    }

    public int m_132278_() {
        return this.f_132260_;
    }

    public BlockPos m_132279_() {
        return this.f_132259_;
    }
}

