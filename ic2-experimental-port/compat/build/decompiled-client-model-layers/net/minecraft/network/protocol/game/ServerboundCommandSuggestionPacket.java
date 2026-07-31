/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundCommandSuggestionPacket
implements Packet<ServerGamePacketListener> {
    private final int f_133889_;
    private final String f_133890_;

    public ServerboundCommandSuggestionPacket(int p_133893_, String p_133894_) {
        this.f_133889_ = p_133893_;
        this.f_133890_ = p_133894_;
    }

    public ServerboundCommandSuggestionPacket(FriendlyByteBuf p_179565_) {
        this.f_133889_ = p_179565_.m_130242_();
        this.f_133890_ = p_179565_.m_130136_(32500);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133903_) {
        p_133903_.m_130130_(this.f_133889_);
        p_133903_.m_130072_(this.f_133890_, 32500);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_133900_) {
        p_133900_.m_7741_(this);
    }

    public int m_133901_() {
        return this.f_133889_;
    }

    public String m_133904_() {
        return this.f_133890_;
    }
}

