/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundResourcePackPacket
implements Packet<ClientGamePacketListener> {
    public static final int f_179178_ = 40;
    private final String f_132912_;
    private final String f_132913_;
    private final boolean f_179179_;
    @Nullable
    private final Component f_179180_;

    public ClientboundResourcePackPacket(String p_179182_, String p_179183_, boolean p_179184_, @Nullable Component p_179185_) {
        if (p_179183_.length() > 40) {
            throw new IllegalArgumentException("Hash is too long (max 40, was " + p_179183_.length() + ")");
        }
        this.f_132912_ = p_179182_;
        this.f_132913_ = p_179183_;
        this.f_179179_ = p_179184_;
        this.f_179180_ = p_179185_;
    }

    public ClientboundResourcePackPacket(FriendlyByteBuf p_179187_) {
        this.f_132912_ = p_179187_.m_130277_();
        this.f_132913_ = p_179187_.m_130136_(40);
        this.f_179179_ = p_179187_.readBoolean();
        this.f_179180_ = (Component)p_179187_.m_236868_(FriendlyByteBuf::m_130238_);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132926_) {
        p_132926_.m_130070_(this.f_132912_);
        p_132926_.m_130070_(this.f_132913_);
        p_132926_.writeBoolean(this.f_179179_);
        p_132926_.m_236821_(this.f_179180_, FriendlyByteBuf::m_130083_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132923_) {
        p_132923_.m_5587_(this);
    }

    public String m_132924_() {
        return this.f_132912_;
    }

    public String m_132927_() {
        return this.f_132913_;
    }

    public boolean m_179188_() {
        return this.f_179179_;
    }

    @Nullable
    public Component m_179189_() {
        return this.f_179180_;
    }
}

