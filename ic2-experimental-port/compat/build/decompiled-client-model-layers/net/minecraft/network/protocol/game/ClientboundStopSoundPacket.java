/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;

public class ClientboundStopSoundPacket
implements Packet<ClientGamePacketListener> {
    private static final int f_179423_ = 1;
    private static final int f_179424_ = 2;
    @Nullable
    private final ResourceLocation f_133464_;
    @Nullable
    private final SoundSource f_133465_;

    public ClientboundStopSoundPacket(@Nullable ResourceLocation p_133468_, @Nullable SoundSource p_133469_) {
        this.f_133464_ = p_133468_;
        this.f_133465_ = p_133469_;
    }

    public ClientboundStopSoundPacket(FriendlyByteBuf p_179426_) {
        byte $$1 = p_179426_.readByte();
        this.f_133465_ = ($$1 & 1) > 0 ? p_179426_.m_130066_(SoundSource.class) : null;
        this.f_133464_ = ($$1 & 2) > 0 ? p_179426_.m_130281_() : null;
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133478_) {
        if (this.f_133465_ != null) {
            if (this.f_133464_ != null) {
                p_133478_.writeByte(3);
                p_133478_.m_130068_(this.f_133465_);
                p_133478_.m_130085_(this.f_133464_);
            } else {
                p_133478_.writeByte(1);
                p_133478_.m_130068_(this.f_133465_);
            }
        } else if (this.f_133464_ != null) {
            p_133478_.writeByte(2);
            p_133478_.m_130085_(this.f_133464_);
        } else {
            p_133478_.writeByte(0);
        }
    }

    @Nullable
    public ResourceLocation m_133476_() {
        return this.f_133464_;
    }

    @Nullable
    public SoundSource m_133479_() {
        return this.f_133465_;
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133475_) {
        p_133475_.m_7183_(this);
    }
}

