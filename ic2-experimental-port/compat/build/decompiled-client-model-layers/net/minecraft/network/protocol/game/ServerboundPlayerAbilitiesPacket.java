/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.world.entity.player.Abilities;

public class ServerboundPlayerAbilitiesPacket
implements Packet<ServerGamePacketListener> {
    private static final int f_179707_ = 2;
    private final boolean f_134254_;

    public ServerboundPlayerAbilitiesPacket(Abilities p_134257_) {
        this.f_134254_ = p_134257_.f_35935_;
    }

    public ServerboundPlayerAbilitiesPacket(FriendlyByteBuf p_179709_) {
        byte $$1 = p_179709_.readByte();
        this.f_134254_ = ($$1 & 2) != 0;
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134266_) {
        int $$1 = 0;
        if (this.f_134254_) {
            $$1 = (byte)($$1 | 2);
        }
        p_134266_.writeByte($$1);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134263_) {
        p_134263_.m_6828_(this);
    }

    public boolean m_134264_() {
        return this.f_134254_;
    }
}

