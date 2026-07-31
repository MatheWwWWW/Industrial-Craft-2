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
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.inventory.MenuType;

public class ClientboundOpenScreenPacket
implements Packet<ClientGamePacketListener> {
    private final int f_132611_;
    private final MenuType<?> f_132612_;
    private final Component f_132613_;

    public ClientboundOpenScreenPacket(int p_132616_, MenuType<?> p_132617_, Component p_132618_) {
        this.f_132611_ = p_132616_;
        this.f_132612_ = p_132617_;
        this.f_132613_ = p_132618_;
    }

    public ClientboundOpenScreenPacket(FriendlyByteBuf p_179011_) {
        this.f_132611_ = p_179011_.m_130242_();
        this.f_132612_ = p_179011_.m_236816_(Registry.f_122863_);
        this.f_132613_ = p_179011_.m_130238_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132627_) {
        p_132627_.m_130130_(this.f_132611_);
        p_132627_.m_236818_(Registry.f_122863_, this.f_132612_);
        p_132627_.m_130083_(this.f_132613_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132624_) {
        p_132624_.m_5980_(this);
    }

    public int m_132625_() {
        return this.f_132611_;
    }

    @Nullable
    public MenuType<?> m_132628_() {
        return this.f_132612_;
    }

    public Component m_132629_() {
        return this.f_132613_;
    }
}

