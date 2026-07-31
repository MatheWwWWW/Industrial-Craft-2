/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundServerDataPacket
implements Packet<ClientGamePacketListener> {
    private final Optional<Component> f_237795_;
    private final Optional<String> f_237796_;
    private final boolean f_237797_;
    private final boolean f_242954_;

    public ClientboundServerDataPacket(@Nullable Component p_242977_, @Nullable String p_242969_, boolean p_242973_, boolean p_242974_) {
        this.f_237795_ = Optional.ofNullable(p_242977_);
        this.f_237796_ = Optional.ofNullable(p_242969_);
        this.f_237797_ = p_242973_;
        this.f_242954_ = p_242974_;
    }

    public ClientboundServerDataPacket(FriendlyByteBuf p_237799_) {
        this.f_237795_ = p_237799_.m_236860_(FriendlyByteBuf::m_130238_);
        this.f_237796_ = p_237799_.m_236860_(FriendlyByteBuf::m_130277_);
        this.f_237797_ = p_237799_.readBoolean();
        this.f_242954_ = p_237799_.readBoolean();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_237805_) {
        p_237805_.m_236835_(this.f_237795_, FriendlyByteBuf::m_130083_);
        p_237805_.m_236835_(this.f_237796_, FriendlyByteBuf::m_130070_);
        p_237805_.writeBoolean(this.f_237797_);
        p_237805_.writeBoolean(this.f_242954_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_237809_) {
        p_237809_.m_213672_(this);
    }

    public Optional<Component> m_237810_() {
        return this.f_237795_;
    }

    public Optional<String> m_237811_() {
        return this.f_237796_;
    }

    public boolean m_237812_() {
        return this.f_237797_;
    }

    public boolean m_242957_() {
        return this.f_242954_;
    }
}

