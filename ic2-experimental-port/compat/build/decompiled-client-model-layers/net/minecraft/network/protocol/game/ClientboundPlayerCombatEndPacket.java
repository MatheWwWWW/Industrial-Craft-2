/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.CombatTracker;

public class ClientboundPlayerCombatEndPacket
implements Packet<ClientGamePacketListener> {
    private final int f_179034_;
    private final int f_179035_;

    public ClientboundPlayerCombatEndPacket(CombatTracker p_179040_) {
        this(p_179040_.m_146699_(), p_179040_.m_19295_());
    }

    public ClientboundPlayerCombatEndPacket(int p_179037_, int p_179038_) {
        this.f_179034_ = p_179037_;
        this.f_179035_ = p_179038_;
    }

    public ClientboundPlayerCombatEndPacket(FriendlyByteBuf p_179042_) {
        this.f_179035_ = p_179042_.m_130242_();
        this.f_179034_ = p_179042_.readInt();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_179044_) {
        p_179044_.m_130130_(this.f_179035_);
        p_179044_.writeInt(this.f_179034_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_179048_) {
        p_179048_.m_142234_(this);
    }
}

