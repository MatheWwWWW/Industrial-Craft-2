/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundSetExperiencePacket
implements Packet<ClientGamePacketListener> {
    private final float f_133214_;
    private final int f_133215_;
    private final int f_133216_;

    public ClientboundSetExperiencePacket(float p_133219_, int p_133220_, int p_133221_) {
        this.f_133214_ = p_133219_;
        this.f_133215_ = p_133220_;
        this.f_133216_ = p_133221_;
    }

    public ClientboundSetExperiencePacket(FriendlyByteBuf p_179299_) {
        this.f_133214_ = p_179299_.readFloat();
        this.f_133216_ = p_179299_.m_130242_();
        this.f_133215_ = p_179299_.m_130242_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133230_) {
        p_133230_.writeFloat(this.f_133214_);
        p_133230_.m_130130_(this.f_133216_);
        p_133230_.m_130130_(this.f_133215_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133227_) {
        p_133227_.m_6747_(this);
    }

    public float m_133228_() {
        return this.f_133214_;
    }

    public int m_133231_() {
        return this.f_133215_;
    }

    public int m_133232_() {
        return this.f_133216_;
    }
}

