/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.player.Player;

public class ClientboundAddPlayerPacket
implements Packet<ClientGamePacketListener> {
    private final int f_131587_;
    private final UUID f_131588_;
    private final double f_131589_;
    private final double f_131590_;
    private final double f_131591_;
    private final byte f_131592_;
    private final byte f_131593_;

    public ClientboundAddPlayerPacket(Player p_131596_) {
        this.f_131587_ = p_131596_.m_19879_();
        this.f_131588_ = p_131596_.m_36316_().getId();
        this.f_131589_ = p_131596_.m_20185_();
        this.f_131590_ = p_131596_.m_20186_();
        this.f_131591_ = p_131596_.m_20189_();
        this.f_131592_ = (byte)(p_131596_.m_146908_() * 256.0f / 360.0f);
        this.f_131593_ = (byte)(p_131596_.m_146909_() * 256.0f / 360.0f);
    }

    public ClientboundAddPlayerPacket(FriendlyByteBuf p_178570_) {
        this.f_131587_ = p_178570_.m_130242_();
        this.f_131588_ = p_178570_.m_130259_();
        this.f_131589_ = p_178570_.readDouble();
        this.f_131590_ = p_178570_.readDouble();
        this.f_131591_ = p_178570_.readDouble();
        this.f_131592_ = p_178570_.readByte();
        this.f_131593_ = p_178570_.readByte();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_131605_) {
        p_131605_.m_130130_(this.f_131587_);
        p_131605_.m_130077_(this.f_131588_);
        p_131605_.writeDouble(this.f_131589_);
        p_131605_.writeDouble(this.f_131590_);
        p_131605_.writeDouble(this.f_131591_);
        p_131605_.writeByte(this.f_131592_);
        p_131605_.writeByte(this.f_131593_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_131602_) {
        p_131602_.m_6482_(this);
    }

    public int m_131603_() {
        return this.f_131587_;
    }

    public UUID m_131606_() {
        return this.f_131588_;
    }

    public double m_131607_() {
        return this.f_131589_;
    }

    public double m_131608_() {
        return this.f_131590_;
    }

    public double m_131609_() {
        return this.f_131591_;
    }

    public byte m_131610_() {
        return this.f_131592_;
    }

    public byte m_131611_() {
        return this.f_131593_;
    }
}

