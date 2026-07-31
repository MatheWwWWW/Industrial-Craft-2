/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.world.entity.Entity;

public class ServerboundMoveVehiclePacket
implements Packet<ServerGamePacketListener> {
    private final double f_134185_;
    private final double f_134186_;
    private final double f_134187_;
    private final float f_134188_;
    private final float f_134189_;

    public ServerboundMoveVehiclePacket(Entity p_134192_) {
        this.f_134185_ = p_134192_.m_20185_();
        this.f_134186_ = p_134192_.m_20186_();
        this.f_134187_ = p_134192_.m_20189_();
        this.f_134188_ = p_134192_.m_146908_();
        this.f_134189_ = p_134192_.m_146909_();
    }

    public ServerboundMoveVehiclePacket(FriendlyByteBuf p_179700_) {
        this.f_134185_ = p_179700_.readDouble();
        this.f_134186_ = p_179700_.readDouble();
        this.f_134187_ = p_179700_.readDouble();
        this.f_134188_ = p_179700_.readFloat();
        this.f_134189_ = p_179700_.readFloat();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134201_) {
        p_134201_.writeDouble(this.f_134185_);
        p_134201_.writeDouble(this.f_134186_);
        p_134201_.writeDouble(this.f_134187_);
        p_134201_.writeFloat(this.f_134188_);
        p_134201_.writeFloat(this.f_134189_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134198_) {
        p_134198_.m_5659_(this);
    }

    public double m_134199_() {
        return this.f_134185_;
    }

    public double m_134202_() {
        return this.f_134186_;
    }

    public double m_134203_() {
        return this.f_134187_;
    }

    public float m_134204_() {
        return this.f_134188_;
    }

    public float m_134205_() {
        return this.f_134189_;
    }
}

