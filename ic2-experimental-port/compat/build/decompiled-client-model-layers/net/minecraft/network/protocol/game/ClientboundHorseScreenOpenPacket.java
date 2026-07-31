/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundHorseScreenOpenPacket
implements Packet<ClientGamePacketListener> {
    private final int f_132190_;
    private final int f_132191_;
    private final int f_132192_;

    public ClientboundHorseScreenOpenPacket(int p_132195_, int p_132196_, int p_132197_) {
        this.f_132190_ = p_132195_;
        this.f_132191_ = p_132196_;
        this.f_132192_ = p_132197_;
    }

    public ClientboundHorseScreenOpenPacket(FriendlyByteBuf p_178867_) {
        this.f_132190_ = p_178867_.readUnsignedByte();
        this.f_132191_ = p_178867_.m_130242_();
        this.f_132192_ = p_178867_.readInt();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132206_) {
        p_132206_.writeByte(this.f_132190_);
        p_132206_.m_130130_(this.f_132191_);
        p_132206_.writeInt(this.f_132192_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132203_) {
        p_132203_.m_6905_(this);
    }

    public int m_132204_() {
        return this.f_132190_;
    }

    public int m_132207_() {
        return this.f_132191_;
    }

    public int m_132208_() {
        return this.f_132192_;
    }
}

