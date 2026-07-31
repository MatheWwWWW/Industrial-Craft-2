/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundPlayerCombatEnterPacket
implements Packet<ClientGamePacketListener> {
    public ClientboundPlayerCombatEnterPacket() {
    }

    public ClientboundPlayerCombatEnterPacket(FriendlyByteBuf p_179051_) {
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_179053_) {
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_179057_) {
        p_179057_.m_142058_(this);
    }
}

