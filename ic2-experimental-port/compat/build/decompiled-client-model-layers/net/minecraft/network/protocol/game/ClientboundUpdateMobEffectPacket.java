/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public class ClientboundUpdateMobEffectPacket
implements Packet<ClientGamePacketListener> {
    private static final short f_238178_ = Short.MAX_VALUE;
    private static final int f_179462_ = 1;
    private static final int f_179463_ = 2;
    private static final int f_179464_ = 4;
    private final int f_133604_;
    private final MobEffect f_237871_;
    private final byte f_133606_;
    private final int f_133607_;
    private final byte f_133608_;
    @Nullable
    private final MobEffectInstance.FactorData f_237872_;

    public ClientboundUpdateMobEffectPacket(int p_133611_, MobEffectInstance p_133612_) {
        this.f_133604_ = p_133611_;
        this.f_237871_ = p_133612_.m_19544_();
        this.f_133606_ = (byte)(p_133612_.m_19564_() & 0xFF);
        this.f_133607_ = p_133612_.m_19557_();
        byte $$2 = 0;
        if (p_133612_.m_19571_()) {
            $$2 = (byte)($$2 | 1);
        }
        if (p_133612_.m_19572_()) {
            $$2 = (byte)($$2 | 2);
        }
        if (p_133612_.m_19575_()) {
            $$2 = (byte)($$2 | 4);
        }
        this.f_133608_ = $$2;
        this.f_237872_ = p_133612_.m_216895_().orElse(null);
    }

    public ClientboundUpdateMobEffectPacket(FriendlyByteBuf p_179466_) {
        this.f_133604_ = p_179466_.m_130242_();
        this.f_237871_ = p_179466_.m_236816_(Registry.f_122823_);
        this.f_133606_ = p_179466_.readByte();
        this.f_133607_ = p_179466_.m_130242_();
        this.f_133608_ = p_179466_.readByte();
        this.f_237872_ = (MobEffectInstance.FactorData)p_179466_.m_236868_(p_237877_ -> p_237877_.m_130057_(MobEffectInstance.FactorData.f_216907_));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133621_) {
        p_133621_.m_130130_(this.f_133604_);
        p_133621_.m_236818_(Registry.f_122823_, this.f_237871_);
        p_133621_.writeByte(this.f_133606_);
        p_133621_.m_130130_(this.f_133607_);
        p_133621_.writeByte(this.f_133608_);
        p_133621_.m_236821_(this.f_237872_, (p_237874_, p_237875_) -> p_237874_.m_130059_(MobEffectInstance.FactorData.f_216907_, p_237875_));
    }

    public boolean m_133619_() {
        return this.f_133607_ >= Short.MAX_VALUE;
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133618_) {
        p_133618_.m_7915_(this);
    }

    public int m_133622_() {
        return this.f_133604_;
    }

    public MobEffect m_237878_() {
        return this.f_237871_;
    }

    public byte m_133624_() {
        return this.f_133606_;
    }

    public int m_133625_() {
        return this.f_133607_;
    }

    public boolean m_133626_() {
        return (this.f_133608_ & 2) == 2;
    }

    public boolean m_133627_() {
        return (this.f_133608_ & 1) == 1;
    }

    public boolean m_133628_() {
        return (this.f_133608_ & 4) == 4;
    }

    @Nullable
    public MobEffectInstance.FactorData m_237879_() {
        return this.f_237872_;
    }
}

