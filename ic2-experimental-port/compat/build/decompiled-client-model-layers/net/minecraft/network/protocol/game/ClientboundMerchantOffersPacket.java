/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.item.trading.MerchantOffers;

public class ClientboundMerchantOffersPacket
implements Packet<ClientGamePacketListener> {
    private final int f_132448_;
    private final MerchantOffers f_132449_;
    private final int f_132450_;
    private final int f_132451_;
    private final boolean f_132452_;
    private final boolean f_132453_;

    public ClientboundMerchantOffersPacket(int p_132456_, MerchantOffers p_132457_, int p_132458_, int p_132459_, boolean p_132460_, boolean p_132461_) {
        this.f_132448_ = p_132456_;
        this.f_132449_ = p_132457_;
        this.f_132450_ = p_132458_;
        this.f_132451_ = p_132459_;
        this.f_132452_ = p_132460_;
        this.f_132453_ = p_132461_;
    }

    public ClientboundMerchantOffersPacket(FriendlyByteBuf p_178985_) {
        this.f_132448_ = p_178985_.m_130242_();
        this.f_132449_ = MerchantOffers.m_45395_(p_178985_);
        this.f_132450_ = p_178985_.m_130242_();
        this.f_132451_ = p_178985_.m_130242_();
        this.f_132452_ = p_178985_.readBoolean();
        this.f_132453_ = p_178985_.readBoolean();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132470_) {
        p_132470_.m_130130_(this.f_132448_);
        this.f_132449_.m_45393_(p_132470_);
        p_132470_.m_130130_(this.f_132450_);
        p_132470_.m_130130_(this.f_132451_);
        p_132470_.writeBoolean(this.f_132452_);
        p_132470_.writeBoolean(this.f_132453_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132467_) {
        p_132467_.m_7330_(this);
    }

    public int m_132468_() {
        return this.f_132448_;
    }

    public MerchantOffers m_132471_() {
        return this.f_132449_;
    }

    public int m_132472_() {
        return this.f_132450_;
    }

    public int m_132473_() {
        return this.f_132451_;
    }

    public boolean m_132474_() {
        return this.f_132452_;
    }

    public boolean m_132475_() {
        return this.f_132453_;
    }
}

