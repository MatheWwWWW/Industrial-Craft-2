/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.CombatTracker;

public class ClientboundPlayerCombatKillPacket
implements Packet<ClientGamePacketListener> {
    private final int f_179058_;
    private final int f_179059_;
    private final Component f_179060_;

    public ClientboundPlayerCombatKillPacket(CombatTracker p_179066_, Component p_179067_) {
        this(p_179066_.m_19297_().m_19879_(), p_179066_.m_146699_(), p_179067_);
    }

    public ClientboundPlayerCombatKillPacket(int p_179062_, int p_179063_, Component p_179064_) {
        this.f_179058_ = p_179062_;
        this.f_179059_ = p_179063_;
        this.f_179060_ = p_179064_;
    }

    public ClientboundPlayerCombatKillPacket(FriendlyByteBuf p_179069_) {
        this.f_179058_ = p_179069_.m_130242_();
        this.f_179059_ = p_179069_.readInt();
        this.f_179060_ = p_179069_.m_130238_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_179072_) {
        p_179072_.m_130130_(this.f_179058_);
        p_179072_.writeInt(this.f_179059_);
        p_179072_.m_130083_(this.f_179060_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_179076_) {
        p_179076_.m_142747_(this);
    }

    @Override
    public boolean m_6588_() {
        return true;
    }

    public int m_179077_() {
        return this.f_179059_;
    }

    public int m_179078_() {
        return this.f_179058_;
    }

    public Component m_179079_() {
        return this.f_179060_;
    }
}

