/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundPlayerInputPacket
implements Packet<ServerGamePacketListener> {
    private static final int f_179717_ = 1;
    private static final int f_179718_ = 2;
    private final float f_134339_;
    private final float f_134340_;
    private final boolean f_134341_;
    private final boolean f_134342_;

    public ServerboundPlayerInputPacket(float p_134345_, float p_134346_, boolean p_134347_, boolean p_134348_) {
        this.f_134339_ = p_134345_;
        this.f_134340_ = p_134346_;
        this.f_134341_ = p_134347_;
        this.f_134342_ = p_134348_;
    }

    public ServerboundPlayerInputPacket(FriendlyByteBuf p_179720_) {
        this.f_134339_ = p_179720_.readFloat();
        this.f_134340_ = p_179720_.readFloat();
        byte $$1 = p_179720_.readByte();
        this.f_134341_ = ($$1 & 1) > 0;
        this.f_134342_ = ($$1 & 2) > 0;
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134357_) {
        p_134357_.writeFloat(this.f_134339_);
        p_134357_.writeFloat(this.f_134340_);
        byte $$1 = 0;
        if (this.f_134341_) {
            $$1 = (byte)($$1 | 1);
        }
        if (this.f_134342_) {
            $$1 = (byte)($$1 | 2);
        }
        p_134357_.writeByte($$1);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134354_) {
        p_134354_.m_5918_(this);
    }

    public float m_134355_() {
        return this.f_134339_;
    }

    public float m_134358_() {
        return this.f_134340_;
    }

    public boolean m_134359_() {
        return this.f_134341_;
    }

    public boolean m_134360_() {
        return this.f_134342_;
    }
}

