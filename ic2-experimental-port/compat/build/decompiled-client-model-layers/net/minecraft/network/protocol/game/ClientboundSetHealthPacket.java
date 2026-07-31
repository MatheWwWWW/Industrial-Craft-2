/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundSetHealthPacket
implements Packet<ClientGamePacketListener> {
    private final float f_133233_;
    private final int f_133234_;
    private final float f_133235_;

    public ClientboundSetHealthPacket(float p_133238_, int p_133239_, float p_133240_) {
        this.f_133233_ = p_133238_;
        this.f_133234_ = p_133239_;
        this.f_133235_ = p_133240_;
    }

    public ClientboundSetHealthPacket(FriendlyByteBuf p_179301_) {
        this.f_133233_ = p_179301_.readFloat();
        this.f_133234_ = p_179301_.m_130242_();
        this.f_133235_ = p_179301_.readFloat();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133249_) {
        p_133249_.writeFloat(this.f_133233_);
        p_133249_.m_130130_(this.f_133234_);
        p_133249_.writeFloat(this.f_133235_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133246_) {
        p_133246_.m_5547_(this);
    }

    public float m_133247_() {
        return this.f_133233_;
    }

    public int m_133250_() {
        return this.f_133234_;
    }

    public float m_133251_() {
        return this.f_133235_;
    }
}

