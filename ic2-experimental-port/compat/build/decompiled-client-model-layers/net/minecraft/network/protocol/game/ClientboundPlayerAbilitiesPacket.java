/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.player.Abilities;

public class ClientboundPlayerAbilitiesPacket
implements Packet<ClientGamePacketListener> {
    private static final int f_179028_ = 1;
    private static final int f_179029_ = 2;
    private static final int f_179030_ = 4;
    private static final int f_179031_ = 8;
    private final boolean f_132659_;
    private final boolean f_132660_;
    private final boolean f_132661_;
    private final boolean f_132662_;
    private final float f_132663_;
    private final float f_132664_;

    public ClientboundPlayerAbilitiesPacket(Abilities p_132667_) {
        this.f_132659_ = p_132667_.f_35934_;
        this.f_132660_ = p_132667_.f_35935_;
        this.f_132661_ = p_132667_.f_35936_;
        this.f_132662_ = p_132667_.f_35937_;
        this.f_132663_ = p_132667_.m_35942_();
        this.f_132664_ = p_132667_.m_35947_();
    }

    public ClientboundPlayerAbilitiesPacket(FriendlyByteBuf p_179033_) {
        byte $$1 = p_179033_.readByte();
        this.f_132659_ = ($$1 & 1) != 0;
        this.f_132660_ = ($$1 & 2) != 0;
        this.f_132661_ = ($$1 & 4) != 0;
        this.f_132662_ = ($$1 & 8) != 0;
        this.f_132663_ = p_179033_.readFloat();
        this.f_132664_ = p_179033_.readFloat();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132676_) {
        byte $$1 = 0;
        if (this.f_132659_) {
            $$1 = (byte)($$1 | 1);
        }
        if (this.f_132660_) {
            $$1 = (byte)($$1 | 2);
        }
        if (this.f_132661_) {
            $$1 = (byte)($$1 | 4);
        }
        if (this.f_132662_) {
            $$1 = (byte)($$1 | 8);
        }
        p_132676_.writeByte($$1);
        p_132676_.writeFloat(this.f_132663_);
        p_132676_.writeFloat(this.f_132664_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132673_) {
        p_132673_.m_5767_(this);
    }

    public boolean m_132674_() {
        return this.f_132659_;
    }

    public boolean m_132677_() {
        return this.f_132660_;
    }

    public boolean m_132678_() {
        return this.f_132661_;
    }

    public boolean m_132679_() {
        return this.f_132662_;
    }

    public float m_132680_() {
        return this.f_132663_;
    }

    public float m_132681_() {
        return this.f_132664_;
    }
}

