/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundSetTitlesAnimationPacket
implements Packet<ClientGamePacketListener> {
    private final int f_179400_;
    private final int f_179401_;
    private final int f_179402_;

    public ClientboundSetTitlesAnimationPacket(int p_179404_, int p_179405_, int p_179406_) {
        this.f_179400_ = p_179404_;
        this.f_179401_ = p_179405_;
        this.f_179402_ = p_179406_;
    }

    public ClientboundSetTitlesAnimationPacket(FriendlyByteBuf p_179408_) {
        this.f_179400_ = p_179408_.readInt();
        this.f_179401_ = p_179408_.readInt();
        this.f_179402_ = p_179408_.readInt();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_179410_) {
        p_179410_.writeInt(this.f_179400_);
        p_179410_.writeInt(this.f_179401_);
        p_179410_.writeInt(this.f_179402_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_179414_) {
        p_179414_.m_142185_(this);
    }

    public int m_179415_() {
        return this.f_179400_;
    }

    public int m_179416_() {
        return this.f_179401_;
    }

    public int m_179417_() {
        return this.f_179402_;
    }
}

